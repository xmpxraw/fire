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

/**
 * 功能实体类
 * 
 * @author zhangle
 */
@Entity
@Table(name = "sys_function")
public class Function extends Pojo {

	public static final int TYPE_DIRECTORY = 1; // 类型:目录
	public static final int TYPE_LEAF = 2; // 类型:叶子

	public static final int STATUS_DEFAULT = 0; // 状态:默认
	public static final int STATUS_DISABLE = 1; // 状态:禁用

	@Id
	@GeneratedValue(generator = "uuid")
	@Column(name = "id", length = 36)
	private String id; // 编号(主键)

	@Column(name = "name", length = 50)
	private String name; // 功能名

	@Column(name = "code", length = 32)
	private String code; // 功能code

	@Column(name = "url", length = 100)
	private String url; // 资源路径

	@Column(name = "type", precision = 6)
	private Integer type; // 类型(1:目录 2:叶子节点)

	@Column(name = "parent_id", length = 32)
	private String parentId; // 父id

	@Column(name = "status", precision = 6)
	private Integer status; // 状态(0:默认 1:禁用)

	@Column(name = "seq_num", precision = 16)
	private Long seqNum; // 排序号

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime; // 创建时间

	@Column(name = "create_user_id", length = 32)
	private String createUserId; // 创建用户ID

	@Column(name = "remark", length = 255)
	private String remark; // 备注

	public String getId() {
		return id;
	}

	public void setId(String functionId) {
		this.id = functionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Long seqNum) {
		this.seqNum = seqNum;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
