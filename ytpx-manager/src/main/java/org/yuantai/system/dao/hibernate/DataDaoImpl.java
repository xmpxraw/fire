package org.yuantai.system.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.stereotype.Repository;
import org.yuantai.system.dao.DataDao;
import org.yuantai.system.pojo.Data;

@Repository
public class DataDaoImpl extends BaseDaoImpl<Data> implements DataDao {

	@Override
	public void add(Data obj) {
		obj.setSeqNum(getNextSeqNum());
		if(StringUtils.isBlank(obj.getParentId())) {
			obj.setParentId(null);
		}
		obj.setCreateTime(new Date());
		super.add(obj);
	}
	
	@Override
	public void update(Data obj) {
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
		
		List<Data> datas=getChildren(id, null);
		for(Data m:datas) {
			if(m.getType()==Data.TYPE_LEAF) {	//如果是叶子节点,那么直接删除,否则是目录节点,需要递归删除所有子节点
				super.delete(m.getId());
			} else {
				delete(m.getId());
			}
		}
		super.delete(id);
	}
	
	public List<Data> getChildren(String parentId,Integer status) {
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
	public List<Data> getChildren(String code) {
		
		DetachedCriteria subquery=DetachedCriteria.forClass(Data.class)
				.add(Restrictions.eq("code", code))
				.add(Restrictions.eq("status", Data.STATUS_DEFAULT))
				.setProjection(Projections.property("id"));
		
		Criteria criteria=getCriteria()
				.add(Restrictions.eq("status", Data.STATUS_DEFAULT))
				.add(Subqueries.propertyEq("parentId", subquery))
				.addOrder(Order.asc("seqNum"));
		
		return criteria.list();
	}

	@Override
	public Data getByCode(String code) {
		if(StringUtils.isBlank(code)) return null;
		return super.get(Data.class, "code", code);
	}
	
	/**
	 * 根据code和value获取数据
	 * @param code
	 * @param value
	 * @return
	 */
	public Data getByCode(String code,String value) {
		
		Data data=getByCode(code);
		if(data==null)	return null;
		
		Criteria criteria=getCriteria()
			.add(Restrictions.eq("parentId", data.getId()))
			.add(Restrictions.eq("value", value));
		List<Data> datas=criteria.list();
		
		if(datas.isEmpty()) return null;
		return datas.get(0);
	}

	@Override
	public void move(String targetId, String sourceId, String point) {
		Data target=get(targetId);
		Data source=get(sourceId);
		
		if(target.getType()==Data.TYPE_LEAF && point.equals("append")) {
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
	private void sortForMove(Data target, Data source,String point) {
		List<Data> children=getChildren(target.getParentId(), null);
		int index=0;
		for(;index<children.size();index++) {
			Data m=children.get(index);
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
			Data m=children.get(index);
			if(index<children.size()-1) {
				m.setSeqNum(children.get(index+1).getSeqNum());
			} else {
				m.setSeqNum(_seqNum);
			}
		}
	}

}
