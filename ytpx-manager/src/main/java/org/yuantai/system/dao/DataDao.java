package org.yuantai.system.dao;

import java.util.List;

import org.yuantai.system.pojo.Data;

/**
 * 基础数据Dao接口
 * @author zhangle
 */
public interface DataDao extends BaseDao<Data> {
	
	/**
	 * 获取子数据
	 * @param parentId	父数据ID,当parentId==null时,返回顶级数据
	 * @param status	状态,当status==null时,返回所有状态的数据,状态值详见Data.STATUS_*属性
	 * @return
	 */
	public List<Data> getChildren(String parentId,Integer status);
	
	/**
	 * 根据父节点的code,获取子数据
	 * 返回状态为默认的子数据
	 * @param code
	 * @return
	 */
	public List<Data> getChildren(String code);
	
	/**
	 * 根据code获取数据
	 * @param code
	 * @return
	 */
	public Data getByCode(String code);
	
	/**
	 * 根据code和value获取数据
	 * @param code
	 * @param value
	 * @return
	 */
	public Data getByCode(String code,String value);
	
	/**
	 * 移动数据
	 * @param targetId		目标数据id
	 * @param sourceId		源数据id
	 * @param point			操作类型	取值范围: append,top,bottom
	 */
	public void move(String targetId,String sourceId,String point);
}
