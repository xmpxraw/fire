package org.yuantai.system.service;

import java.util.List;

import org.yuantai.system.pojo.Role;

/**
 * 角色服务接口
 * @author zhangle
 */
public interface RoleService extends BaseService<Role> {

	/**
	 * 根据角色名查找角色对象
	 * @param name
	 * @return
	 */
	public Role getRoleByName(String name);
	
	/**
	 * 根据角色类型查找角色对象
	 * @param name
	 * @return
	 */
	public Role getRoleByType(String type);
	
	/**
	 * 获取用户关联的角色
	 * @param userId
	 * @param status	状态,当status==null时,返回所有状态的角色,状态值详见Role.STATUS_*属性
	 * @return
	 */
	public List<Role> findRoleByUserId(String userId,Integer status);
	
	/**
	 * 排序
	 * 传进来的role对象中,只有id,name,seqNum三个属性是正确的,其它的都不正常,请匆使用其它属性
	 * @param roles
	 */
	public void sort(Role[] roles);
	
	/**
	 * 查找所有未禁用的角色
	 * @return
	 */
	public List<Role> findAll();
}
