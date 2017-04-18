package org.yuantai.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * easyui tree组件节点实体类
 * @ClassName: TreeNode
 * @Description:
 * @author zhangle
 * @email  lezhang@isoftstone.com
 * @date 2013年9月21日 下午9:31:11
 */
public class TreeNode {

	public static final String STATE_OPEN="open";
	public static final String STATE_CLOSED="closed";
	
	private String id;
	private String text;
	private String state;		//节点状态 open, close
	private boolean checked;
	private String iconCls;
	private Map<String,Object> attributes;
	private List<TreeNode> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Map<String, Object> getAttributes() {
		if (attributes == null) attributes = new HashMap<String, Object>();
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public List<TreeNode> getChildren() {
		if (children == null) children = new ArrayList<TreeNode>();
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean getChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
}
