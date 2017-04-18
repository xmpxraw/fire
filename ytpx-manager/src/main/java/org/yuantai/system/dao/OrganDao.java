package org.yuantai.system.dao;

import java.util.List;

import org.yuantai.system.pojo.Organ;

public interface OrganDao extends BaseDao<Organ>{
	
	/**
	 * 获取子菜单
	 * @param parentId	父菜单ID,当parentId==null时,返回顶级菜单
	 * @param status	状态,当status==null时,返回所有状态的菜单,状态值详见Organ.STATUS_*属性
	 * @return
	 */
	public List<Organ> getChildren(String parentId,Integer type,Integer status);
	
	/**
	 * 排序
	 * 传进来的Organ对象中,只有id,name,seqNum三个属性是正确的,其它的都不正常,请匆使用其它属性
	 * @param Organ
	 */
	public void sort(Organ[] organs);
	
	/**
	 * 移动功能
	 * @param targetId		目标机构id
	 * @param sourceId		源机构id
	 * @param point			操作类型	取值范围: append,top,bottom
	 */
	public void move(String targetId,String sourceId,String point);
	
	/**
	 * 权限设置
	 * @param organId
	 * @param checkedFuncId			已选中的功能ID
	 * @param uncheckedFuncId		未选中的功能ID
	 */
	public void functionSetup(String organId, String[] checkedFuncId,String[] uncheckedFuncId);
	
	/**
	 * 获取用户的主要员工对象
	 * @param userId
	 * @return
	 */
	public Organ getMainEmployee(String userId);
	
	/**
	 * 获取用户的主要部门对象
	 * @param userId
	 * @return
	 */
	public Organ getMainDepartment(String userId);

	/**
	 * 获取员工的部门对象
	 * @param empOrganId
	 * @return
	 */
	public Organ getDepartment(String empOrganId);
	
	/**
	 * 获取员工的团队对象
	 * @param empOrganId
	 * @return
	 */
	public Organ getTeam(String empOrganId);
	
	/**
	 * 获取节点的所有父节点,包括本节点
	 * @param organId
	 * @return
	 */
	public List<Organ> getParents(String organId);
	
	/**
	 * 根据userId获取用户的所有职位信息
	 * @param userId
	 * @return
	 */
	public List<Organ> getOrgansByUserId(String userId);
	
	/**
	 * 设置用户的主要员工
	 * 先清除用户的主要员工,再根据organId设置主要员工
	 * @param userId		
	 * @param organId		当为null时,不设置主要员工
	 */
	public void setMainEmployee(String userId,String organId);
	
	/**
	 * 根据roleId获取用户的所有职位信息
	 * 获取拥有该角色的所有用户,并在组织机构表中找到该用户设置为主要的职位
	 * @param roleId
	 * @return
	 */
	public List<Organ> getOrgansByRoleId(String roleId);
	
	/**
	 * 通过工号获取organ对象
	 * @param no
	 * @return
	 */
	public Organ getByCode(String no);
	
	/**
	 * 递归查询所有子节点
	 * @param organId		此节点下的所有子节点(包括本节点)
	 * @param type			只查出此类型的子节点
	 * @param grade			只查出只等级的子节点
	 * @return
	 */
	public List<Organ> recursiveChildren(String organId,Integer type,Integer grade);
}
