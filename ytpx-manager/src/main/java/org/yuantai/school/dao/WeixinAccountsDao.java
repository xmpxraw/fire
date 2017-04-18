package org.yuantai.school.dao;

import java.util.List;

import org.yuantai.school.pojo.WeixinAccounts;
import org.yuantai.system.dao.BaseDao;

public interface WeixinAccountsDao extends BaseDao<WeixinAccounts> {
	
	public List<WeixinAccounts> findAll();
	public List<WeixinAccounts> checkBySysCode(String sysCode);
}
