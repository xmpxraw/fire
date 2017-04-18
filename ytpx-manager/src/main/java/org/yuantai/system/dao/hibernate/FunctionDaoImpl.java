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
import org.yuantai.system.dao.FunctionDao;
import org.yuantai.system.pojo.Function;
import org.yuantai.system.pojo.OrganFunction;
import org.yuantai.system.pojo.RoleFunction;

/**
 * 功能数据访问实现类
 * @author zhangle
 */
@Repository
public class FunctionDaoImpl extends BaseDaoImpl<Function> implements FunctionDao{
	
	@Override
	public void add(Function obj) {
		
		if(getChild(obj.getParentId(), obj.getName())!=null) {
			throw new RuntimeException("重复的功能名!");
		}
		obj.setSeqNum(getNextSeqNum());
		if(StringUtils.isBlank(obj.getParentId())) {
			obj.setParentId(null);
		}
		obj.setCreateTime(new Date());
		super.add(obj);
	}
	
	@Override
	public void update(Function obj) {
		
		if(StringUtils.isBlank(obj.getParentId())) {
			obj.setParentId(null);
		}
		Function function=getChild(obj.getParentId(), obj.getName());
		if(function==null) {
			super.update(obj);
			return;
		}
		
		if(function.getId().equals(obj.getId())) {
			getSession().merge(obj);
		} else {
			throw new RuntimeException("重复的功能名!");
		}
		
	}
	
	/**
	 * 获取子节点
	 * @param parentId	父节点id
	 * @param name		子节点名称
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Function getChild(String parentId,String name) {
		List<Function> functions=getCriteria()
			.add(StringUtils.isBlank(parentId)?Restrictions.isNull("parentId"):Restrictions.eq("parentId", parentId))
			.add(Restrictions.eq("name", name))
			.add(Restrictions.eq("status", Function.STATUS_DEFAULT))
			.list();
		if(functions.isEmpty()) return null;
		return functions.get(0);
	}
	
	@Override
	public void delete(String id) {
		
		List<Function> functions=getChildren(id, null);
		for(Function f:functions) {
			if(f.getType()==Function.TYPE_LEAF) {	//如果是叶子节点,那么直接删除,否则是目录节点,需要递归删除所有子节点
				super.delete(f.getId());
			} else {
				delete(f.getId());
			}
			executeUpdate("delete RoleFunction where functionId=?",f.getId());
			executeUpdate("delete OrganFunction where functionId=?",f.getId());
		}
		executeUpdate("delete RoleFunction where functionId=?",id);
		executeUpdate("delete OrganFunction where functionId=?",id);
		super.delete(id);
	}
	
	public List<Function> getChildren(String parentId,Integer status) {
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
	public List<Function> getChildrenForRole(String roleId, String parentId, Integer status) {
		
		Criteria criteria=getCriteria();
		if(StringUtils.isBlank(parentId)) {
			criteria.add(Restrictions.isNull("parentId"));
		} else {
			criteria.add(Restrictions.eq("parentId", parentId));
		}
		if(status!=null) {
			criteria.add(Restrictions.eq("status", status));
		}
		
		DetachedCriteria subquery=DetachedCriteria.forClass(RoleFunction.class);
		subquery.add(Restrictions.eq("roleId", roleId));
		subquery.setProjection(Projections.property("functionId"));
		criteria.add(Subqueries.propertyIn("id", subquery));
		
		criteria.addOrder(Order.asc("seqNum"));
		return criteria.list();
	}
	
	@Override
	public List<Function> getChildrenForOrgan(String organId, String parentId,Integer status) {
		
		Criteria criteria=getCriteria();
		if(StringUtils.isBlank(parentId)) {
			criteria.add(Restrictions.isNull("parentId"));
		} else {
			criteria.add(Restrictions.eq("parentId", parentId));
		}
		if(status!=null) {
			criteria.add(Restrictions.eq("status", status));
		}
		
		DetachedCriteria subquery=DetachedCriteria.forClass(OrganFunction.class);
		subquery.add(Restrictions.eq("organId", organId));
		subquery.setProjection(Projections.property("functionId"));
		criteria.add(Subqueries.propertyIn("id", subquery));
		
		criteria.addOrder(Order.asc("seqNum"));
		return criteria.list();
	}

	@Override
	public void move(String targetId, String sourceId, String point) {
		Function target=get(targetId);
		Function source=get(sourceId);
		
		if(target.getType()==Function.TYPE_LEAF && point.equals("append")) {
			throw new RuntimeException("移动功能失败:叶子节点不允许追加子节点!");
		}
		
		if(point.equals("append")) {					//如果是追加操作,那么直接追加到父节点下
			source.setParentId(target.getId());
			source.setSeqNum(getNextSeqNum());
			update(source);
		} else {										//否则是排序操作,那么移动为与目标节点同级,再执行同级排序
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
	private void sortForMove(Function target, Function source,String point) {
		List<Function> children=getChildren(target.getParentId(), null);
		int index=0;
		for(;index<children.size();index++) {
			Function f=children.get(index);
			if(target.equals(f)) {
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
			Function f=children.get(index);
			if(index<children.size()-1) {
				f.setSeqNum(children.get(index+1).getSeqNum());
			} else {
				f.setSeqNum(_seqNum);
			}
		}
	}

}
