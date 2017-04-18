package org.yuantai.system.dao;

import java.util.List;

import org.yuantai.system.pojo.Menu;

/**
 * 菜单数据访问接口
 * @author zhangle
 */
public interface MenuDao extends BaseDao<Menu> {
	
	/**
	 * 获取子菜单
	 * @param parentId	父菜单ID,当parentId==null时,返回顶级菜单
	 * @param status	状态,当status==null时,返回所有状态的菜单,状态值详见Menu.STATUS_*属性
	 * @return
	 */
	public List<Menu> getChildren(String parentId,Integer status);

	/**
	 * 移动菜单
	 * @param targetId		目标菜单id
	 * @param sourceId		源菜单id
	 * @param point			操作类型	取值范围: append,top,bottom
	 */
	public void move(String targetId,String sourceId,String point);
}
