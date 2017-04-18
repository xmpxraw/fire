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
@Table(name = "tbl_wx_accounts")
public class WeixinAccounts extends Pojo {

	public static final Object STATUS_DELETED = null;

	@Id
	@GeneratedValue(generator = "uuid")
	@Column(name = "id", length = 36)
	private String id;

	@Column(name = "system_id", length = 64)
	private String systemId;
	
	@Column(name = "name", length = 64)
	private String name;
	
	@Column(name = "app_id", length = 128)
	private String appId;
	
	@Column(name = "secret", length = 128)
	private String secret;
	
	@Column(name = "token", length = 128)
	private String token;
	
	@Column(name = "encoding_aes_key", length = 128)
	private String encodingAesKey;
	
	@Column(name = "encrypt_type", length = 16)
	private String encryptType;
	
	@Column(name = "account_type", length = 16)
	private String accountType;
	
	@Column(name = "is_auth", length = 1)
	private Boolean isAuth;
	
	@Column(name = "is_init", length = 1)
	private Boolean isInit;
	
	@Column(name = "is_enabled", length = 1)
	private Boolean isEnabled;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time")
	private Date updateTime;
	
	@Column(name = "sys_code", length = 36)
	private String sysCode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getEncodingAesKey() {
		return encodingAesKey;
	}

	public void setEncodingAesKey(String encodingAesKey) {
		this.encodingAesKey = encodingAesKey;
	}

	public String getEncryptType() {
		return encryptType;
	}

	public void setEncryptType(String encryptType) {
		this.encryptType = encryptType;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	

	public Boolean getIsAuth() {
		return isAuth;
	}

	public void setIsAuth(Boolean isAuth) {
		this.isAuth = isAuth;
	}

	public Boolean getIsInit() {
		return isInit;
	}

	public void setIsInit(Boolean isInit) {
		this.isInit = isInit;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}




	
}
