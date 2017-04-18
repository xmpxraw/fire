package org.yuantai.system.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色与功能的中间关联实体类
 * @author zhangle
 */
@Entity
@Table(name="sys_role_function")
public class RoleFunction implements Serializable {

	@Id 
	@GeneratedValue(generator = "uuid")
	@Column(name="id",length=36)
	private String id;

	@Column(name="function_id",length=32)
	private String functionId;

	@Column(name="role_id",length=32)
	private String roleId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}