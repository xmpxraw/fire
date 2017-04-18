package org.yuantai.system.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.stereotype.Repository;
import org.yuantai.system.dao.RoleDao;
import org.yuantai.system.pojo.Role;
import org.yuantai.system.pojo.RoleFunction;
import org.yuantai.system.pojo.UserRole;

/**
 * 角色数据访问实现类
 * @author zhangle
 */
@Repository
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {

	@Override
	public void add(Role obj) {
		obj.setSeqNum(getNextSeqNum());
		obj.setCreateTime(new Date());
		getSession().save(obj);
		
		//添加角色与功能的关联
		addRoleFunction(obj.getId(), obj.getCheckedFunctionId());
	}
	
	@Override
	public void update(Role obj) {
		getSession().update(obj);
		deleteRoleFunction(obj.getId(),obj.getUncheckdFunctionId());
		deleteRoleFunction(obj.getId(),obj.getCheckedFunctionId());
		addRoleFunction(obj.getId(), obj.getCheckedFunctionId());
	}
	
	@Override
	public void delete(String id) {
		executeUpdate("delete UserRole where roleId=?",id);
		executeUpdate("delete RoleFunction where roleId=?",id);
		super.delete(id);
	}

	@Override
	public List<Role> findRoleByUserId(String userId,Integer status) {
		
		Criteria criteria=getCriteria();
		if(status!=null) {
			criteria.add(Restrictions.eq("status", status));
		}
		
		DetachedCriteria subquery=DetachedCriteria.forClass(UserRole.class);
		subquery.add(Restrictions.eq("userId", userId));
		subquery.setProjection(Projections.property("roleId"));
		criteria.add(Subqueries.propertyIn("id", subquery));
		criteria.addOrder(Order.asc("seqNum"));
		
		return criteria.list();
	}

	@Override
	public Role getRoleByName(String name) {
		return super.get(Role.class,"name",name);
	}
	
	@Override
	public Role getRoleByType(String type) {
		return super.get(Role.class,"type",type);
	}

	@Override
	public void sort(Role[] roles) {
		if(roles==null || roles.length==0) return;
		
		Query sortQuery=createQuery("update Role set seqNum=:seqNum where id=:id");
		for(Role r:roles) {
			Role po=get(r.getId());
			if(po.getSeqNum().equals(r.getSeqNum())) continue;
			sortQuery.setLong("seqNum", r.getSeqNum());
			sortQuery.setString("id", r.getId());
			sortQuery.executeUpdate();
		}
	}
	
	/**
	 * 添加角色与功能的关联
	 * @param userId	关联的角色ID
	 * @param roleId	关联的功能ID数组
	 */
	public void addRoleFunction(String roleId, String[] functionId) {
		if(roleId==null || functionId==null || functionId.length==0) return;
		for(String _fid:functionId) {
			RoleFunction rf=new RoleFunction();
			rf.setRoleId(roleId);
			rf.setFunctionId(_fid);
			getSession().save(rf);
		}
	}

	/**
	 * 删除角色与功能的所有关联
	 * @param userId	需要删除关联的角色ID
	 */
	public void deleteRoleFunction(String roleId,String[] functionId) {
		if(roleId==null || functionId==null || functionId.length==0) return;
		Query query=createQuery("delete RoleFunction where roleId=:roleId and functionId in(:functionId)");
		query.setString("roleId", roleId);
		query.setParameterList("functionId", functionId);
		query.executeUpdate();
	}

	@Override
	public List<Role> findAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Role.class)
    			.add(Restrictions.eq("status", Role.STATUS_DEFAULT))
    			.addOrder(Order.asc("seqNum"));
		return super.find(criteria);
	}
}
