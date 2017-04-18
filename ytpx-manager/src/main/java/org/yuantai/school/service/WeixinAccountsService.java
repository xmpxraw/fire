package org.yuantai.school.service;

import java.util.List;

import org.yuantai.school.pojo.WeixinAccounts;
import org.yuantai.system.service.BaseService;

public interface WeixinAccountsService extends BaseService<WeixinAccounts> {
	public  List<WeixinAccounts> findAll() ;
	public List<WeixinAccounts> checkBySysCode(String sysCode);
}
