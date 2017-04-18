package org.yuantai.study.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yuantai.common.TreeNode;
import org.yuantai.study.dao.DirectoryDao;
import org.yuantai.study.pojo.Directory;
import org.yuantai.study.service.DirectoryService;
import org.yuantai.system.service.impl.BaseServiceImpl;

/**
 * DirectoryServiceImpl
 * 
 * @author cola
 * @version 2016年12月23日
 */
@Service
@Transactional(readOnly = true)
public class DirectoryServiceImpl extends BaseServiceImpl<Directory> implements DirectoryService {

	@Autowired
	private DirectoryDao directoryDao;

	@Override
	public List<Directory> getAllChildrenDirectory(String parentId, Integer status) {
		List<Directory> treeNodes = directoryDao.getChildren(parentId, status);
		if (CollectionUtils.isNotEmpty(treeNodes)) {
			for (Directory dir : treeNodes) {
				buildChildrenTree(dir, status);
			}
		} else {
			treeNodes = new ArrayList<Directory>();
		}
		return treeNodes;
	}

	/**
	 * 递归构建目录树
	 * 
	 * @param directory
	 * @return
	 */
	private void buildChildrenTree(Directory directory, Integer status) {
		if (directory == null) {
			return;
		}
		directory.getTreeNode().setId(directory.getId());
		directory.getTreeNode().setText(directory.getName());
		List<Directory> children = directoryDao.getChildren(directory.getId(), status);
		if (CollectionUtils.isNotEmpty(children)) {
			for (Directory child : children) {
				TreeNode tn = new TreeNode();
				tn.setId(child.getId());
				tn.setText(child.getName());
				directory.getTreeNode().getChildren().add(tn);
			}
			directory.setChildren(children);
			// 递归
			for (Directory dir : directory.getChildren()) {
				buildChildrenTree(dir, status);
			}
		}

	}

	/**
	 * 保存目录
	 */
	@Override
	@Transactional(readOnly = false)
	public String save(Directory dir, String userId) {
		String msg = "OK";
		if (StringUtils.isEmpty(dir.getId()) && directoryDao.countByName(dir.getName()) > 0) {
			return "目录名已存在";
		}

		Directory parent = get(dir.getParentId());

		if (parent == null) {
			dir.setParentId("0");
			dir.setTreePath("0" + ",");
		}

		String tag = dir.getTag();
		if (StringUtils.isEmpty(tag)) {
			return "目录标签不能为空";
		}
		if (tag.endsWith(",")) {
			tag = tag.substring(0, tag.length() - 1);
		}
		dir.setTag(tag);

		if (StringUtils.isEmpty(dir.getId())) {

			// 新增
			dir.setCreateTime(new Date());
			dir.setCreator(userId);
			String code = directoryDao.getMaxDirCode();
			if (StringUtils.isEmpty(code)) {
				code = "0";
			} else {
				code = Integer.valueOf(code) + 1 + "";
			}
			dir.setCode(code);
			if (parent != null) {
				dir.setTreePath(parent.getTreePath() + dir.getCode() + ",");
			}
			add(dir);
		} else {
			// 修改
			if (parent != null) {
				dir.setTreePath(parent.getTreePath() + dir.getCode() + ",");
			}
			update(dir);
		}
		return msg;
	}
}
