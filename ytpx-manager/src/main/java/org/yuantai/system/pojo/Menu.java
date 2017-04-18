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
 * 菜单实体类
 * @author zhangle
 */
@Entity
@Table(name = "sys_menu")
public class Menu extends Pojo implements Comparable<Menu> {

	public static final int TYPE_DIRECTORY = 1; // 类型:目录
	public static final int TYPE_LEAF = 2; // 类型:叶子

	public static final int EXPAND_FALSE = 0; // 是否展开:否
	public static final int EXPAND_TRUE = 1; // 是否展开:是

	public static final int STATUS_DEFAULT = 0; // 状态:默认
	public static final int STATUS_DISABLE = 1; // 状态:禁用

	@Id
	@GeneratedValue(generator = "uuid")
	@Column(name = "id", length = 36)
	private String id;

	@Column(name = "name", length = 32)
	private String name;

	@Column(name = "url", length = 100)
	private String url;

	@Column(name = "tooltips", length = 32)
	private String tooltips;

	@Column(name = "target", length = 16)
	private String target;

	@Column(name = "parent_id", length = 32)
	private String parentId;

	@Column(name = "type", precision = 6)
	private Integer type;

	@Column(name = "seq_num", precision = 16)
	private Long seqNum;

	@Column(name = "icon", length = 50)
	private String icon;

	@Column(name = "icon_open", length = 50)
	private String iconOpen;

	@Column(name = "expand", precision = 6)
	private Integer expand;

	@Column(name = "status", precision = 6)
	private Integer status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "create_user_id", length = 32)
	private String createUserId;

	@Column(name = "remark", length = 255)
	private String remark;
	
	@Column(name = "sys_code", length = 32)
	private String sysCode;

	public String getId() {
		return this.id;
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

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTooltips() {
		return this.tooltips;
	}

	public void setTooltips(String tooltips) {
		this.tooltips = tooltips;
	}

	public String getTarget() {
		return this.target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getSeqNum() {
		return this.seqNum;
	}

	public void setSeqNum(Long seqNum) {
		this.seqNum = seqNum;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIconOpen() {
		return this.iconOpen;
	}

	public void setIconOpen(String iconOpen) {
		this.iconOpen = iconOpen;
	}

	public Integer getExpand() {
		return this.expand;
	}

	public void setExpand(Integer expand) {
		this.expand = expand;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public int compareTo(Menu m) {
		if(m==null) return -1;
		return this.getSeqNum().compareTo(m.getSeqNum());
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
	
	
}