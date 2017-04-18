package org.yuantai.school.pojo;

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
import org.yuantai.system.pojo.Pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author zamn
 *
 */
@Entity
@Table(name = "sm_class")
public class Classes extends Pojo {

	public static final Object STATUS_DELETED = null;

	@Id
	@GeneratedValue(generator = "uuid")
	@Column(name = "id", length = 36)
	private String id;
	
	@Column(name = "class_code", length = 32)
	private String classCode;
	
	@Column(name = "class_name", length = 32)
	private String className;
	
	@Column(name = "class_status")
	private Integer classStatus;
	
	@Column(name = "notify_log")
	private Integer notifyLog;
	
	@Column(name = "class_type", length = 32)
	private String classType;
	
	@Column(name = "specialty", length = 32)
	private String specialty;
	
	@Column(name = "term_code", length = 2)
	private String termCode;
	
	@Column(name = "class_count")
	private Integer classCount;

	@Temporal(TemporalType.DATE)
	@Column(name = "begin_time")
	private Date beginTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;
	
	@Column(name = "period")
	private Integer period;
	
	@Column(name = "plan_count")
	private Integer planCount;
	
	@Column(name = "registry_count")
	private Integer registryCount;

	@Column(name = "pass_count")
	private Integer passCount;
	
	@Column(name = "pass_rate", length = 255)
	private String passRate;
	
	@Column(name = "sys_code", length = 50)
	private String sysCode;
	
	@Transient
	private String term;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	public Integer getClassCount() {
		return classCount;
	}

	public void setClassCount(Integer classCount) {
		this.classCount = classCount;
	}
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public Integer getPlanCount() {
		return planCount;
	}

	public void setPlanCount(Integer planCount) {
		this.planCount = planCount;
	}

	public Integer getRegistryCount() {
		return registryCount;
	}

	public void setRegistryCount(Integer registryCount) {
		this.registryCount = registryCount;
	}

	public Integer getPassCount() {
		return passCount;
	}

	public void setPassCount(Integer passCount) {
		this.passCount = passCount;
	}

	public String getPassRate() {
		return passRate;
	}

	public void setPassRate(String passRate) {
		this.passRate = passRate;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public Integer getClassStatus() {
		return classStatus;
	}

	public void setClassStatus(Integer classStatus) {
		this.classStatus = classStatus;
	}

	public Integer getNotifyLog() {
		return notifyLog;
	}

	public void setNotifyLog(Integer notifyLog) {
		this.notifyLog = notifyLog;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	

	

}
