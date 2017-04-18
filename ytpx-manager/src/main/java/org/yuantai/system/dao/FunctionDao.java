package org.yuantai.system.dao;

import java.util.List;

import org.yuantai.system.pojo.Function;

/**
 * 功能数据访问接口
 * @author zhangle
 */
public interface FunctionDao extends BaseDao<Function>{
	
	/**
	 * 获取子功能
	 * @param parentId	父功能ID,当parentId==null时,返回顶级功能
	 * @param status	状态,当status==null时,返回所有状态的功能,状态值详见Function.STATUS_*属性
	 * @return
	 */
	public List<Function> getChildren(String parentId,Integer status);
	
	/**
	 * 获取角色关联的子功能
	 * @param roleId		角色ID
	 * @param parentId		父功能ID,当parentId==null时,返回顶级功能
	 * @param status		状态,当status==null时,返回所有状态的功能,状态值详见Function.STATUS_*属性
	 * @return
	 */
	public List<Function> getChildrenForRole(String roleId,String parentId,Integer status);
	
	/**
	 * 获取组织机构关联的子功能
	 * @param organId		组织机构ID
	 * @param parentId		父功能ID,当parentId==null时,返回顶级功能
	 * @param status		状态,当status==null时,返回所有状态的功能,状态值详见Function.STATUS_*属性
	 * @return
	 */
	public List<Function> getChildrenForOrgan(String organId,String parentId,Integer status);
	
	/**
	 * 移动功能
	 * @param targetId		目标功能d
	 * @param sourceId		源功能id
	 * @param point			操作类型	取值范围: append,top,bottom
	 */
	public void move(String targetId,String sourceId,String point);
}
