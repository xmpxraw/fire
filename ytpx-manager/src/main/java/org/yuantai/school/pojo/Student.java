package org.yuantai.school.pojo;

import java.math.BigDecimal;
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
@Table(name = "sm_student")
public class Student extends Pojo {

	public static final Object STATUS_DELETED = null;

	@Id
	@GeneratedValue(generator = "uuid")
	@Column(name = "id", length = 36)
	private String id;

	@Column(name = "student_no", length = 36)
	private String studentNo;
	
	@Column(name = "studnet_name", length = 32)
	private String studnetName;
	
	@Column(name = "class_name", length = 100)
	private String className;
	
	@Transient
	private String companyName;
	
	@Column(name = "class_code", length = 36)
	private String classCode;
	
	@Column(name = "sex", length = 32)
	private String sex;
	
	@Column(name = "age", length = 2)
	private Integer age;
	
	@Column(name = "idcard", length = 32)
	private String idcard;
	
	@Column(name = "mobile", length = 32)
	private String mobile;
	
	@Column(name = "expiry", length = 32)
	private String expiry;
	
	@Column(name = "education_level", length = 32)
	private String educationLevel;
	
	@Column(name = "student_status", length = 2)
	private Integer studentStatus;
	
	@Column(name = "pass")
	private String pass;
	
	@Column(name = "terminate")
	private Integer terminate;
	
	@Column(name = "graduate")
	private Integer graduate;
	
	@Column(name = "regist_type", length = 32)
	private String registType;
	
	@Column(name = "exam_location", length = 100)
	private String examLocation;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "exam_time")
	private Date examTime;
	
	@Column(name = "pay_real")
	private BigDecimal payReal;
	
	@Column(name = "pay_should")
	private BigDecimal payShould;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "regist_time")
	private Date registTime;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "common_score", length = 32)
	private Float commonScore;
	
	@Column(name = "nation_score", length = 32)
	private Float nationScore;
	
	@Column(name = "weixin_openid", length = 32)
	private String weixinOpenid;
	
	@Column(name = "company_code", length = 36)
	private String companyCode;
	
	@Column(name = "pay_status", length = 32)
	private String payStatus;
	
	@Column(name = "pay_scheme", length = 32)
	private String payScheme;
	
	@Transient
	private String schemeName;
	
	@Column(name = "pay_type", length = 32)
	private String payType;
	
	@Column(name = "fees_meal")
	private BigDecimal feesMeal;
	
	@Column(name = "fees_hotel")
	private BigDecimal feesHotel;
	
	@Column(name = "fees_study")
	private BigDecimal feesStudy;
	
	@Column(name = "fees_total")
	private BigDecimal feesTotal;
	
	@Column(name = "fees_train")
	private BigDecimal feesTrain;
	
	@Column(name = "registe_code", length = 36)
	private String registeCode;
	
	@Column(name = "term_code", length = 36)
	private String termCode;
	
	@Transient
	private String termName;
	
	@Column(name = "remark", length = 1000)
	private String remark;
	
	@Column(name = "sys_code", length = 36)
	private String sysCode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getStudnetName() {
		return studnetName;
	}

	public void setStudnetName(String studnetName) {
		this.studnetName = studnetName;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}



	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getExpiry() {
		return expiry;
	}

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}

	public String getEducationLevel() {
		return educationLevel;
	}

	public void setEducationLevel(String educationLevel) {
		this.educationLevel = educationLevel;
	}

	public Integer getStudentStatus() {
		return studentStatus;
	}

	public void setStudentStatus(Integer studentStatus) {
		this.studentStatus = studentStatus;
	}

	public String getRegistType() {
		return registType;
	}

	public void setRegistType(String registType) {
		this.registType = registType;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	public Date getRegistTime() {
		return registTime;
	}

	public void setRegistTime(Date registTime) {
		this.registTime = registTime;
	}



	public Float getCommonScore() {
		return commonScore;
	}

	public void setCommonScore(Float commonScore) {
		this.commonScore = commonScore;
	}

	public Float getNationScore() {
		return nationScore;
	}

	public void setNationScore(Float nationScore) {
		this.nationScore = nationScore;
	}

	public String getWeixinOpenid() {
		return weixinOpenid;
	}

	public void setWeixinOpenid(String weixinOpenid) {
		this.weixinOpenid = weixinOpenid;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayScheme() {
		return payScheme;
	}

	public void setPayScheme(String payScheme) {
		this.payScheme = payScheme;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	


	public String getRegisteCode() {
		return registeCode;
	}

	public void setRegisteCode(String registeCode) {
		this.registeCode = registeCode;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}



	public BigDecimal getPayReal() {
		return payReal;
	}

	public void setPayReal(BigDecimal payReal) {
		this.payReal = payReal;
	}

	public BigDecimal getPayShould() {
		return payShould;
	}

	public void setPayShould(BigDecimal payShould) {
		this.payShould = payShould;
	}

	public BigDecimal getFeesMeal() {
		return feesMeal;
	}

	public void setFeesMeal(BigDecimal feesMeal) {
		this.feesMeal = feesMeal;
	}

	public BigDecimal getFeesHotel() {
		return feesHotel;
	}

	public void setFeesHotel(BigDecimal feesHotel) {
		this.feesHotel = feesHotel;
	}

	public BigDecimal getFeesStudy() {
		return feesStudy;
	}

	public void setFeesStudy(BigDecimal feesStudy) {
		this.feesStudy = feesStudy;
	}

	public BigDecimal getFeesTotal() {
		return feesTotal;
	}

	public void setFeesTotal(BigDecimal feesTotal) {
		this.feesTotal = feesTotal;
	}

	public BigDecimal getFeesTrain() {
		return feesTrain;
	}

	public void setFeesTrain(BigDecimal feesTrain) {
		this.feesTrain = feesTrain;
	}

	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getTermName() {
		return termName;
	}

	public void setTermName(String termName) {
		this.termName = termName;
	}

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public Integer getTerminate() {
		return terminate;
	}

	public void setTerminate(Integer terminate) {
		this.terminate = terminate;
	}

	public Integer getGraduate() {
		return graduate;
	}

	public void setGraduate(Integer graduate) {
		this.graduate = graduate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getExamLocation() {
		return examLocation;
	}

	public void setExamLocation(String examLocation) {
		this.examLocation = examLocation;
	}

	public Date getExamTime() {
		return examTime;
	}

	public void setExamTime(Date examTime) {
		this.examTime = examTime;
	}

	
}
