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
@Table(name = "sm_registe")
public class Registe extends Pojo {

	public static final Object STATUS_DELETED = null;

	@Id
	@GeneratedValue(generator = "uuid")
	@Column(name = "id", length = 36)
	private String id;

	@Column(name = "registe_code", length = 32)
	private String registeCode;
	
	@Column(name = "company_code", length = 32)
	private String companyCode;
	
	@Transient
	private String companyName;
	
	@Transient
	private String schemeName;
	
	@Transient
	private String termCode;
	
	@Column(name = "pay_status", length = 2)
	private Integer payStatus;
	
	@Column(name = "registe_type", length = 32)
	private String registeType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "registe_time")
	private Date registeTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "pay_scheme", length = 32)
	private String payScheme;
	
	@Column(name = "pay_type", length = 32)
	private String payType;
	
	@Column(name = "pay_delay", length = 32)
	private String payDelay;
	
	@Column(name = "template")
	private String template;
	
	@Column(name = "pay_should")
	private BigDecimal payShould;
	
	@Column(name = "pay_real")
	private BigDecimal payReal;
	
	@Column(name = "pay_already")
	private BigDecimal payAlready;
	
	@Column(name = "registe_count")
	private Integer registeCount;
	
	
	@Column(name = "discount_type", length = 32)
	private String discountType;
	
	@Column(name = "discount")
	private BigDecimal discount;
	
	@Column(name = "remark", length = 1000)
	private String remark;
	
	@Column(name = "sys_code", length = 32)
	private String sysCode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRegisteCode() {
		return registeCode;
	}

	public void setRegisteCode(String registeCode) {
		this.registeCode = registeCode;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public String getRegisteType() {
		return registeType;
	}

	public void setRegisteType(String registeType) {
		this.registeType = registeType;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	public Date getRegisteTime() {
		return registeTime;
	}

	public void setRegisteTime(Date registeTime) {
		this.registeTime = registeTime;
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

	public String getPayDelay() {
		return payDelay;
	}

	public void setPayDelay(String payDelay) {
		this.payDelay = payDelay;
	}

	


	public BigDecimal getPayShould() {
		return payShould;
	}

	public void setPayShould(BigDecimal payShould) {
		this.payShould = payShould;
	}

	public BigDecimal getPayReal() {
		return payReal;
	}

	public void setPayReal(BigDecimal payReal) {
		this.payReal = payReal;
	}

	public BigDecimal getPayAlready() {
		return payAlready;
	}

	public void setPayAlready(BigDecimal payAlready) {
		this.payAlready = payAlready;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public Integer getRegisteCount() {
		return registeCount;
	}

	public void setRegisteCount(Integer registeCount) {
		this.registeCount = registeCount;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}



	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	
	

}
