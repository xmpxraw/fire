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
@Table(name = "sm_student_score")
public class StudentScore extends Pojo {

	public static final Object STATUS_DELETED = null;

	@Id
	@GeneratedValue(generator = "uuid")
	@Column(name = "id", length = 36)
	private String id;
	
	@Column(name = "idcard")
	private String idcard;

	@Column(name = "student_no", length = 32)
	private String studentNo;
	
	@Column(name = "studnet_name", length = 32)
	private String studnetName;
	
	@Column(name = "class", length = 32)
	private String classes;
	
	@Column(name = "type", length = 32)
	private String type;
	
	@Column(name = "score_ll")
	private Float scoreLl;
	
	@Column(name = "score_sxt")
	private Float scoreSxt;
	
	@Column(name = "score_kzs")
	private Float scoreKzs;
	
	@Column(name = "score_fhxc")
	private Float scoreFhxc;

	@Column(name = "score_qtmh")
	private Float scoreQtmh;
	
	@Column(name = "score_total")
	private Float scoreTotal;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;
	
	@Column(name = "pass")
	private String pass;

	@Column(name = "sys_code", length = 36)
	private String sysCode;
	
	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
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

	public String getClasses() {
		return classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Float getScoreLl() {
		return scoreLl;
	}

	public void setScoreLl(Float scoreLl) {
		this.scoreLl = scoreLl;
	}

	public Float getScoreSxt() {
		return scoreSxt;
	}

	public void setScoreSxt(Float scoreSxt) {
		this.scoreSxt = scoreSxt;
	}

	public Float getScoreKzs() {
		return scoreKzs;
	}

	public void setScoreKzs(Float scoreKzs) {
		this.scoreKzs = scoreKzs;
	}

	public Float getScoreFhxc() {
		return scoreFhxc;
	}

	public void setScoreFhxc(Float scoreFhxc) {
		this.scoreFhxc = scoreFhxc;
	}

	public Float getScoreQtmh() {
		return scoreQtmh;
	}

	public void setScoreQtmh(Float scoreQtmh) {
		this.scoreQtmh = scoreQtmh;
	}

	public Float getScoreTotal() {
		return scoreTotal;
	}

	public void setScoreTotal(Float scoreTotal) {
		this.scoreTotal = scoreTotal;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}


	

}
