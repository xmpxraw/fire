package org.yuantai.system.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.yuantai.system.dao.OrganDao;
import org.yuantai.system.pojo.Organ;
import org.yuantai.system.pojo.OrganFunction;

@Repository
public class OrganDaoImpl extends BaseDaoImpl<Organ> implements OrganDao{

	@Override
	public void add(Organ obj) {
		
		if(getChild(obj.getParentId(), obj.getName())!=null) {
			throw new RuntimeException("重复的名称!");
		}
		
		obj.setSeqNum(getNextSeqNum());
		if(StringUtils.isBlank(obj.getParentId())) {
			obj.setParentId(null);
		}
		if(StringUtils.isBlank(obj.getShortName())) {		//简称未填时默认与名称相同
			obj.setShortName(obj.getName());
		}
		if(!StringUtils.isBlank(obj.getUserId())){         //当uesrId不为空时进行相关业务逻辑处理
			if(countOrganByUser(obj.getUserId())){			//如果是第一设置,默认为主要职务
				obj.setMain(Organ.MAIN_YES);
			} else if(obj.getMain()==Organ.MAIN_YES) {		//如果当前员工为主要职务,那么将其它员工设置为"非主要职务"
				setMainEmployee(obj.getUserId(), null);
			}
		}
		obj.setCreateTime(new Date());
		super.add(obj);
	}
	
	@Override
	public void update(Organ obj) {

		if(StringUtils.isBlank(obj.getShortName())) {		//简称未填时默认与名称相同
			obj.setShortName(obj.getName());
		}
		if(StringUtils.isBlank(obj.getParentId())) {
			obj.setParentId(null);
		}
		if(obj.getUserId()!=null){                         //当uesrId不为空时进行相关业务逻辑处理
			if(countOrganByUser(obj.getUserId())){			//如果是第一设置,默认为主要职务
				obj.setMain(Organ.MAIN_YES);
			} else if(obj.getMain()==Organ.MAIN_YES) {		//如果当前员工为主要职务,那么将其它员工设置为"非主要职务"
				setMainEmployee(obj.getUserId(), null);
			}
		}
		
		Organ organ=getChild(obj.getParentId(), obj.getName());
		if(organ==null) {
			super.update(obj);
			return;
		}
		
		if(organ.getId().equals(obj.getId())) {
			getSession().merge(obj);
		} else {
			throw new RuntimeException("重复的名称!");
		}
	}
	
	/**
	 * 获取子节点
	 * @param parentId	父节点id
	 * @param name		子节点名称
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Organ getChild(String parentId,String name) {
		List<Organ> organs=getCriteria()
			.add(StringUtils.isBlank(parentId)?Restrictions.isNull("parentId"):Restrictions.eq("parentId", parentId))
			.add(Restrictions.eq("name", name))
			.add(Restrictions.eq("status", Organ.STATUS_DEFAULT))
			.list();
		if(organs.isEmpty()) return null;
		return organs.get(0);
	}
	
	/**
	 * 统计用户已关联几个员工
	 * @param userId
	 * @return
	 */
	private boolean countOrganByUser(String userId){
		Criteria criteria=getCriteria();
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("status", Organ.STATUS_DEFAULT));
		return countRow(criteria)==0;
	}
	
	/**
	 * 递归删除菜单
	 */
	@Override
	public void delete(String id) {
		
		List<Organ> organs=getChildren(id, null, null);
		for(Organ o:organs) {
			//删除机构数据表的关联数据
			executeUpdate("delete OrganData where organId=?",o.getId());
			executeUpdate("delete OrganFunction where organId=?",o.getId());
			if(o.getType()==Organ.TYPE_EMPLOYEE) {	//如果是叶子节点,那么直接删除,否则是目录节点,需要递归删除所有子节点
				this.logicDelete(o.getId());
			} else {
				delete(o.getId());
			}
		}
		//删除机构数据表的关联数据
		executeUpdate("delete OrganData where organId=?",id);
		executeUpdate("delete OrganFunction where organId=?",id);
		this.logicDelete(id);
	}
	
	/**
	 * 改变节点状态,做逻辑删除
	 * @param id
	 */
	private void logicDelete(String id) {
		executeUpdate("update Organ set status=? where id=?",Organ.STATUS_DELETED,id);
	}
	
	public List<Organ> getChildren(String parentId,Integer type,Integer status) {
		Criteria criteria=getCriteria();
		if(StringUtils.isBlank(parentId)) {
			criteria.add(Restrictions.isNull("parentId"));
		} else {
			criteria.add(Restrictions.eq("parentId", parentId));
		}
		if(type!=null) {
			criteria.add(Restrictions.eq("type", type));
		}
		if(status!=null) {
			criteria.add(Restrictions.eq("status", status));
		}
		criteria.addOrder(Order.asc("seqNum"));
		
		return criteria.list();
	}

	@Override
	public void sort(Organ[] organs) {
		if(organs==null || organs.length==0) return;
		
		Query sortQuery=createQuery("update Organ set seqNum=:seqNum where id=:id");
		
		for(Organ o:organs) {
			Organ po=get(o.getId());
			if(po.getSeqNum().equals(o.getSeqNum())) {
				continue;
			} else {
				sortQuery.setLong("seqNum", o.getSeqNum());
				sortQuery.setString("id", o.getId());
				sortQuery.executeUpdate();
			}
		}
	}
	
	@Override
	public void move(String targetId, String sourceId, String point) {
		Organ target=get(targetId);
		Organ source=get(sourceId);
		
		if(target.getType()==Organ.TYPE_EMPLOYEE && point.equals("append")) {
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
	private void sortForMove(Organ target, Organ source,String point) {
		List<Organ> children=getChildren(target.getParentId(), null, null);
		int index=0;
		for(;index<children.size();index++) {
			Organ f=children.get(index);
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
			Organ f=children.get(index);
			if(index<children.size()-1) {
				f.setSeqNum(children.get(index+1).getSeqNum());
			} else {
				f.setSeqNum(_seqNum);
			}
		}
	}

	@Override
	public void functionSetup(String organId, String[] checkedFuncId,String[] uncheckedFuncId) {
		
		if(checkedFuncId==null || uncheckedFuncId==null || uncheckedFuncId.length==0) return;
		
		//先删除未选与已选的原有关联
		Query query=createQuery("delete OrganFunction where organId=:organId and functionId in(:functionId)");
		query.setString("organId", organId);
		query.setParameterList("functionId", uncheckedFuncId);
		query.executeUpdate();
		if(checkedFuncId.length>0) {
			query.setParameterList("functionId", checkedFuncId);
			query.executeUpdate();
		}
		
		//再重新添加已选关联
		for(String id:checkedFuncId) {
			OrganFunction of=new OrganFunction();
			of.setOrganId(organId);
			of.setFunctionId(id);
			getSession().save(of);
		}
	}

	@Override
	public Organ getMainEmployee(String userId) {
		
		List<Organ> organList=getOrgansByUserId(userId);
		if(!organList.isEmpty()) return organList.get(0);
		return null;
	}

	@Override
	public Organ getMainDepartment(String userId) {
		
		Organ emp=getMainEmployee(userId);
		if(emp==null) return null;
		return getDepartment(emp.getId());
	}
	
	@Override
	public Organ getDepartment(String empId) {
		List<Organ> organList=getParents(empId);
		for(Organ organ:organList) {
			if(organ.getType()==Organ.TYPE_DEPARTMENT) return organ;
		}
		return null;
	}

	@Override
	public List<Organ> getParents(String organId) {
		
		List<Organ> organList=new ArrayList<Organ>();
		while(organId!=null) {
			Organ organ=get(organId);
			if(organ==null) break;
			organList.add(organ);
			organId=organ.getParentId();
		}
		return organList;
	}
	
	/**
	 * 根据userId获取用户的所有职位信息
	 * @param userId
	 * @return
	 */
	public List<Organ> getOrgansByUserId(String userId) {
		Criteria criteria=getCriteria()
				.add(Restrictions.eq("status", Organ.STATUS_DEFAULT))
				.add(Restrictions.eq("userId", userId))
				.add(Restrictions.eq("type", Organ.TYPE_EMPLOYEE))
				.addOrder(Order.desc("main"))
				.addOrder(Order.asc("seqNum"));
		
		return criteria.list();
	}

	@Override
	public void setMainEmployee(String userId, String organId) {
		executeUpdate("update Organ set main=? where userId=?",Organ.MAIN_NO,userId);
		if(organId!=null) {
			executeUpdate("update Organ set main=? where id=?", Organ.MAIN_YES, organId);
		}
	}
	
	/**
	 * 根据roleId获取用户的所有职位信息
	 * 获取拥有该角色的所有用户,并在组织机构表中找到该用户设置为主要的职位
	 * @param roleId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Organ> getOrgansByRoleId(String roleId) {
		return executeQuery("select o from Organ o where main=? and status =? and userId in (select userId from UserRole where roleId = ? )" , 
				Organ.MAIN_YES,Organ.STATUS_DEFAULT, roleId); 
	}
	
	@Override
	public Organ getByCode(String code) {
		return super.get(Organ.class, "code", code);
	}

	@Override
	public Organ getTeam(String empId) {
		List<Organ> organList=getParents(empId);
		for(Organ organ:organList) {
			if(organ.getType()==Organ.TYPE_TEAM) return organ;
		}
		return null;
	}
	
	@Override
	public List<Organ> recursiveChildren(String organId, Integer type,Integer grade) {
		
		List<Organ> organList=new ArrayList<Organ>();
		
		List<Organ> children=getChildren(organId, null, null);
		for(Organ child:children) {
			
			boolean match=true;	//标识是否匹配
			if(type==null && child.getType()!=null) {
				match=false;
			}
			if(type!=null && !type.equals(child.getType())) {
				match=false;
			}
			if(grade==null && child.getGrade()!=null) {
				match=false;
			}
			if(grade!=null && !grade.equals(child.getGrade())) {
				match=false;
			}
			
			if(match) {	//如果匹配,那么加入结果列表
				organList.add(child);
			}
			
			if(child.getType()==Organ.TYPE_EMPLOYEE) {
				continue;
			}
			List<Organ> _children=recursiveChildren(child.getId(),type,grade);
			organList.addAll(_children);
		}
		return organList;
	}
}
