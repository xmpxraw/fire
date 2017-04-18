package org.yuantai.basic.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.yuantai.system.pojo.Pojo;

/**
 * 更新日志
 * @ClassName: Helptext
 * @Description:
 * @author zhangle
 * @email  zhanngle@163.com
 * @date 2016年1月18日 下午5:57:50
 */
@Entity
@Table(name="basic_changelog")
public class Changelog extends Pojo {
	
	public static final int STATUS_DEFAULT=1;
	public static final int STATUS_DELETED=-1;
	
	@Id
	@GeneratedValue(generator = "uuid")
	private String id;
	private String name;
	private String content;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createtime;
	@Column(name="`status`")
	private Integer status;
	private String username;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
