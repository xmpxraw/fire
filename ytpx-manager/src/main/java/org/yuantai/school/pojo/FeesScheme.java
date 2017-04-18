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

import org.springframework.format.annotation.DateTimeFormat;
import org.yuantai.system.pojo.Pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author zamn
 *
 */
@Entity
@Table(name = "sm_fees_scheme")
public class FeesScheme extends Pojo {

	public static final Object STATUS_DELETED = null;

	@Id
	@GeneratedValue(generator = "uuid")
	@Column(name = "id", length = 36)
	private String id;

	@Column(name = "scheme_name", length = 32)
	private String schemeName;
	
	@Column(name = "fees_train")
	private BigDecimal feesTrain;
	
	@Column(name = "fees_meal")
	private BigDecimal feesMeal;
	
	@Column(name = "fees_hotel")
	private BigDecimal feesHotel;
	
	@Column(name = "fees_study")
	private BigDecimal feesStudy;
	
	@Column(name = "fees_total")
	private BigDecimal feesTotal;
	
	@Column(name = "scheme_status", length = 2)
	private Integer schemeStatus;

	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	@Temporal(TemporalType.DATE)
	@Column(name = "create_time")
	private Date createTime;


	@Column(name = "scheme_desc", length = 255)
	private String schemeDesc;
	
	@Column(name = "sys_code", length = 32)
	private String sysCode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	

	public BigDecimal getFeesTrain() {
		return feesTrain;
	}

	public void setFeesTrain(BigDecimal feesTrain) {
		this.feesTrain = feesTrain;
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

	public Integer getSchemeStatus() {
		return schemeStatus;
	}

	public void setSchemeStatus(Integer schemeStatus) {
		this.schemeStatus = schemeStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getSchemeDesc() {
		return schemeDesc;
	}

	public void setSchemeDesc(String schemeDesc) {
		this.schemeDesc = schemeDesc;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	
}
