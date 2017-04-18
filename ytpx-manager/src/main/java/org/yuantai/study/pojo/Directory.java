package org.yuantai.study.pojo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.yuantai.common.TreeNode;
import org.yuantai.system.pojo.Pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 目录实体，树形结构
 * 
 * @author cola
 * @version 2016年12月23日
 */
@Entity
@Table(name = "sm_directory")
public class Directory extends Pojo implements Comparator<Directory> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7875498199287979706L;

	@Id
	@GeneratedValue(generator = "uuid")
	@Column(name = "id", length = 64)
	private String id;

	@Column(name = "code", nullable = false, length = 64)
	private String code;

	@Column(name = "name", nullable = false, length = 128)
	private String name;

	@Column(name = "tag", nullable = false, length = 255)
	private String tag;

	@Column(name = "parent_id", length = 64)
	private String parentId;

	@Column(name = "tree_path", length = 255)
	private String treePath;

	// 状态： 1启用， 0禁用
	@Column(name = "status")
	private Integer status = 1;

	@Column(name = "significance")
	private Integer significance;

	@Column(name = "sort_no")
	private Integer sortNo;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "creator", length = 255)
	private String creator;

	@Column(name = "sys_code", length = 64)
	private String sysCode;

	@Transient
	private List<Directory> children = new ArrayList<Directory>();

	// easyui comtree 节点
	@Transient
	private TreeNode treeNode = new TreeNode();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getTreePath() {
		return treePath;
	}

	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSignificance() {
		return significance;
	}

	public void setSignificance(Integer significance) {
		this.significance = significance;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	@Override
	public int compare(Directory o1, Directory o2) {
		if (o1 == null || o2 == null || o2.getSortNo() == null) {
			return -1;
		}
		return o2.getSortNo().compareTo(o1.getSortNo());
	}

	public List<Directory> getChildren() {
		return children;
	}

	public void setChildren(List<Directory> children) {
		this.children = children;
	}

	public TreeNode getTreeNode() {
		return treeNode;
	}

	public void setTreeNode(TreeNode treeNode) {
		this.treeNode = treeNode;
	}

}
