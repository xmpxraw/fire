package org.yuantai.system.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.yuantai.system.dao.MenuDao;
import org.yuantai.system.pojo.Menu;

/**
 * 菜单数据访问实现类
 * @author zhangle
 */
@Repository
public class MenuDaoImpl extends BaseDaoImpl<Menu> implements MenuDao {

	@Override
	public void add(Menu obj) {
		obj.setSeqNum(getNextSeqNum());
		obj.setTooltips(StringUtils.isBlank(obj.getTooltips())?obj.getName():obj.getTooltips());
		if(StringUtils.isBlank(obj.getTarget())) {
			obj.setTarget("_tab");
		}
		if(StringUtils.isBlank(obj.getParentId())) {
			obj.setParentId(null);
		}
		obj.setCreateTime(new Date());
		super.add(obj);
	}
	
	@Override
	public void update(Menu obj) {
		if(StringUtils.isBlank(obj.getParentId())) {
			obj.setParentId(null);
		}
		super.update(obj);
	}
	
	/**
	 * 递归删除菜单
	 */
	@Override
	public void delete(String id) {
		
		List<Menu> menus=getChildren(id, null);
		for(Menu m:menus) {
			if(m.getType()==Menu.TYPE_LEAF) {	//如果是叶子节点,那么直接删除,否则是目录节点,需要递归删除所有子节点
				super.delete(m.getId());
			} else {
				delete(m.getId());
			}
		}
		super.delete(id);
	}
	
	public List<Menu> getChildren(String parentId,Integer status) {
		Criteria criteria=getCriteria();
		if(StringUtils.isBlank(parentId)) {
			criteria.add(Restrictions.isNull("parentId"));
		} else {
			criteria.add(Restrictions.eq("parentId", parentId));
		}
		if(status!=null) {
			criteria.add(Restrictions.eq("status", status));
		}
		criteria.addOrder(Order.asc("seqNum"));
		
		return criteria.list();
	}
	
	@Override
	public void move(String targetId, String sourceId, String point) {
		Menu target=get(targetId);
		Menu source=get(sourceId);
		
		if(target.getType()==Menu.TYPE_LEAF && point.equals("append")) {
			throw new RuntimeException("移动菜单失败:叶子节点不允许追加子节点!");
		}
		
		if(point.equals("append")) {				//如果是追加操作,那么直接追加到父节点下
			source.setParentId(target.getId());
			source.setSeqNum(getNextSeqNum());
			update(source);
		} else {									//否则是排序操作,那么移动为与目标节点同级,再执行同级排序
			source.setParentId(target.getParentId());
			source.setSeqNum(getNextSeqNum());
			update(source);
			
			sortForMove(target, source, point);
		}
	}

	/**
	 * 为移动操作排序
	 * @param target
	 * @param source
	 * @param point
	 */
	private void sortForMove(Menu target, Menu source,String point) {
		List<Menu> children=getChildren(target.getParentId(), null);
		int index=0;
		for(;index<children.size();index++) {
			Menu m=children.get(index);
			if(target.equals(m)) {
				if(point.equals("top")) {			//插入源节点到适当的位置
					children.add(index,source);
				} else if(point.equals("bottom")) {
					children.add(index+1,source);
					index=index+1;					//保存源节点的索引号
				}
				break;
			}
		}
		children.remove(children.size()-1);			//删除重复的源节点(固定为最后一个节点),因为前面循环中,插入了一个源节点
		
		long _seqNum=source.getSeqNum();			//保存源节点的排序号
		for(;index<children.size();index++) {		//重新设置源节点和之后的节点排序号
			Menu m=children.get(index);
			if(index<children.size()-1) {
				m.setSeqNum(children.get(index+1).getSeqNum());
			} else {
				m.setSeqNum(_seqNum);
			}
		}
	}
}
