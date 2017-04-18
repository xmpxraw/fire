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

@Entity
@Table(name = "sys_organ")
public class Organ extends Pojo {

	public static final int TYPE_ENTERPRISE = 1; // 类型:企业
	public static final int TYPE_DEPARTMENT = 2; // 类型:部门
	public static final int TYPE_TEAM = 3; 		// 类型:团队
	public static final int TYPE_EMPLOYEE = 4; // 类型:员工

	// 是否主要:当type为4(即类型为人员)时才使用
	public static final int MAIN_NO = 0; 	// 是否主要:否
	public static final int MAIN_YES = 1; 	// 是否主要:是

	public static final int STATUS_DEFAULT = 0; // 状态:默认
	public static final int STATUS_DELETED = 1; // 状态:删除

	public static final String SEX_MALE = "M"; 		// 性别:男
	public static final String SEX_FEMALE = "F"; 	// 性别:女

	public static final int GRADE_JUZHANG=100;		//局长
	public static final int GRADE_CHANGWU=110;		//常务副局长
	public static final int GRADE_FUJUZHANG=120;	//副局长
	public static final int GRADE_KEZHANG=130;		//科长
	public static final int GRADE_FUKEZHANG=140;	//副科长
	public static final int GRADE_BANSHIYUANG=150;	//办事员
	 
	@Id
	@GeneratedValue(generator = "uuid")
	@Column(name = "id", length = 36)
	private String id; // 主键

	@Column(name = "name", length = 50)
	private String name;// 机构名

	@Column(name = "short_name", length = 32)
	private String shortName;// 机构简称

	@Column(name = "type", precision = 6)
	private Integer type;// 类型

	@Column(name = "parent_id", length = 32)
	private String parentId;// 父节点编号

	@Column(name = "seq_num", precision = 16)
	private Long seqNum;// 排序号

	@Column(name = "address", length = 100)
	private String address;// 地址

	@Column(name = "main", precision = 6)
	private Integer main;// 是否主要

	@Column(name = "emp_no", length = 32)
	private String empNo;// 工号

	@Column(name = "status", precision = 6)
	private Integer status;// 状态

	@Column(name = "user_id", length = 32)
	private String userId;// 用户ID

	@Transient
	private String userName;// 用户姓名

	@Column(name = "mobile", length = 16)
	private String mobile;// 个人电话

	@Column(name = "office_phone", length = 16)
	private String officePhone;// 办公电话

	@Column(name = "fax", length = 16)
	private String fax;// 传真

	@Column(name = "mail", length = 50)
	private String mail;// 邮箱

	@Column(name = "qq", length = 32)
	private String qq;// QQ号

	@Column(name = "sex", length = 1)
	private String sex;// 性别

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "birthday")
	private Date birthday;// 出生日期

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;// 创建时间

	@Column(name = "create_user_id", length = 32)
	private String createUserId;// 创建用户ID

	@Column(name = "remark", length = 255)
	private String remark;// 备注
	
	@Column(name = "grade")
  	private Integer grade;		//等级
  	 
	@Column(name = "principal_id")
  	private String principalId;	 //负责人id
	
	@Transient
  	private String principalName;	//负责人name
  	
	@Column(name = "memo")
	private String memo;
	
	@Column(name = "`code`")
	private String code;

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

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getMain() {
		return main;
	}

	public void setMain(Integer main) {
		this.main = main;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
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

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}

	public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
