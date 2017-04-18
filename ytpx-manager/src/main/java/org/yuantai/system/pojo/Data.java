package org.yuantai.system.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="sys_data")
public class Data extends Pojo {
	
	private static final long serialVersionUID = 1L;

	public static final int TYPE_DIRECTORY=1;		//类型:目录
	public static final int TYPE_LEAF=2;			//类型:叶子
	 
	public static final int STATUS_DEFAULT=0;		//状态:默认
	public static final int STATUS_DISABLE=1;		//状态:禁用
	 
	@Id 
	@GeneratedValue(generator = "uuid")
	@Column(name="id",length=36)
	private String id;

	@Column(name="code",length=32)
	private String code;

	@Column(name="value",length=100)
	private String value;
	
	@Column(name="text",length=100)
	private String text;
	
	@Column(name="status",precision=8)
	private Integer status;

	@Column(name="type",precision=8)
	private Integer type;
	
	@Column(name="parent_id",length=32)
	private String parentId;

	@Column(name="seq_num",precision=16)
	private Long seqNum;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="create_user_id",length=32)
	private String createUserId;

	@Column(name="remark",length=255)
	private String remark;

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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Long getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Long seqNum) {
		this.seqNum = seqNum;
	}

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getCreateTime() {
		return createTime;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}