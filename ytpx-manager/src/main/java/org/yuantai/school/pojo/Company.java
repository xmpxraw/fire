package org.yuantai.school.pojo;

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
 * @author zamn
 *
 */
@Entity
@Table(name = "sm_company")
public class Company extends Pojo {

	public static final Object STATUS_DELETED = null;

	@Id
	@GeneratedValue(generator = "uuid")
	@Column(name = "id", length = 36)
	private String id;

	@Column(name = "company_code", length = 32)
	private String companyCode;
	
	@Column(name = "company_name", length = 32)
	private String companyName;
	
	@Column(name = "company_type", length = 32)
	private String companyType;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "contact", length = 32)
	private String contact;
	
	@Column(name = "telephone", length = 32)
	private String telephone;
	
	@Column(name = "company_addr", length = 255)
	private String companyAddr;
	
	@Column(name = "province", length = 32)
	private String province;

	@Column(name = "city", length = 32)
	private String city;
	
	@Column(name = "district", length = 32)
	private String district;
	
	@Column(name = "invoice_title", length = 32)
	private String invoiceTitle;
	
	@Column(name = "invoice_type", length = 32)
	private String invoiceType;
	
	@Column(name = "taxpayer_no", length = 32)
	private String taxpayerNo;
	
	@Column(name = "invoice_taken", length = 32)
	private String invoiceTaken;
	
	
	@Column(name = "bank", length = 32)
	private String bank;
	
	@Column(name = "bank_account", length = 32)
	private String bankAccount;
	
	@Column(name = "regist_count")
	private Integer registCount;
	
	@Column(name = "student_total")
	private Integer studentTotal;
	
	@Column(name = "school_code", length = 32)
	private String schoolCode;
	
	@Column(name = "status", length = 11)
	private Integer status;
	
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

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getCompanyAddr() {
		return companyAddr;
	}

	public void setCompanyAddr(String companyAddr) {
		this.companyAddr = companyAddr;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getTaxpayerNo() {
		return taxpayerNo;
	}

	public void setTaxpayerNo(String taxpayerNo) {
		this.taxpayerNo = taxpayerNo;
	}

	public String getInvoiceTaken() {
		return invoiceTaken;
	}

	public void setInvoiceTaken(String invoiceTaken) {
		this.invoiceTaken = invoiceTaken;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public Integer getRegistCount() {
		return registCount;
	}

	public void setRegistCount(Integer registCount) {
		this.registCount = registCount;
	}

	public Integer getStudentTotal() {
		return studentTotal;
	}

	public void setStudentTotal(Integer studentTotal) {
		this.studentTotal = studentTotal;
	}

	public String getSchoolCode() {
		return schoolCode;
	}

	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
