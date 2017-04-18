package org.yuantai.system.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 角色实体类
 * 
 * @author zhangle
 */
@Entity
@Table(name = "sys_role")
public class Role extends Pojo {

	public static final int STATUS_DEFAULT = 0; // 状态:默认
	public static final int STATUS_DISABLE = 1; // 状态:禁用

	@Id
	@GeneratedValue(generator = "uuid")
	@Column(name = "id", length = 36)
	private String id;

	@Column(name = "name", length = 50)
	private String name;

	@Column(name = "status", precision = 6)
	private Integer status;

	@Column(name = "remark", length = 255)
	private String remark;

	@Column(name = "seq_num", precision = 16)
	private Long seqNum;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "create_user_id", length = 32)
	private String createUserId;
	
	@Column(name = "type", length = 32)
	private String type;

	@Transient
	private String[] checkedFunctionId; // 修改角色时,选中的功能ID

	@Transient
	private String[] uncheckdFunctionId; // 修改角色时,未选中的功能ID
	
	@Transient
	private String school;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getSeqNum() {
		return this.seqNum;
	}

	public void setSeqNum(Long seqNum) {
		this.seqNum = seqNum;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String[] getCheckedFunctionId() {
		return checkedFunctionId;
	}

	public void setCheckedFunctionId(String[] checkedFunctionId) {
		this.checkedFunctionId = checkedFunctionId;
	}

	public String[] getUncheckdFunctionId() {
		return uncheckdFunctionId;
	}

	public void setUncheckdFunctionId(String[] uncheckdFunctionId) {
		this.uncheckdFunctionId = uncheckdFunctionId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	
	
}
