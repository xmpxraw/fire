package org.yuantai.study.dao;

import java.util.List;

import org.yuantai.study.pojo.Directory;
import org.yuantai.system.dao.BaseDao;

/**
 * 目录DAO
 * 
 * @author cola
 * @version 2016年12月23日
 */
public interface DirectoryDao extends BaseDao<Directory> {

	/**
	 * 获取子目录
	 * 
	 * @param parentId
	 *            父菜单ID,当parentId==0时,返回顶级目录
	 * @param status
	 *            状态,当status==null时,返回所有状态的目录
	 * @return
	 */
	public List<Directory> getChildren(String parentId, Integer status);

	/**
	 * 获取目录最大的code
	 * 
	 * @return
	 */
	public String getMaxDirCode();

	/**
	 * 统计相同名字的目录个数
	 * 
	 * @param name
	 * @return
	 */
	public Long countByName(String name);
}
