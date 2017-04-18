package org.yuantai.study.service;

import java.util.List;

import org.yuantai.study.pojo.Directory;
import org.yuantai.system.service.BaseService;

/**
 * DirectoryService
 * 
 * @author cola
 * @version 2016年12月23日
 */
public interface DirectoryService extends BaseService<Directory> {

	/**
	 * 获取指定目录下的所有目录
	 * 
	 * @param parenId，父级目录，为0时候查询根结点
	 * @param status，查询禁用，启用状态的目录，为空时候查询所有
	 * @return
	 */
	public List<Directory> getAllChildrenDirectory(String parenId, Integer status);

	/**
	 * 保存目录，新增或修改
	 * 
	 * @param dir
	 * @param userId: 操作人
	 */
	public String save(Directory dir, String userId);
}
