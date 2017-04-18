package org.yuantai.school.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yuantai.school.dao.WeixinAccountsDao;
import org.yuantai.school.pojo.WeixinAccounts;
import org.yuantai.school.service.WeixinAccountsService;
import org.yuantai.system.aop.Logging;
import org.yuantai.system.service.impl.BaseServiceImpl;

@Logging
@Service
public class WeixinAccountsServiceImpl extends BaseServiceImpl<WeixinAccounts> implements WeixinAccountsService {
	
@Autowired private WeixinAccountsDao weixinAccountsDao;

	
	@Override
	public List<WeixinAccounts> checkBySysCode(String sysCode) {
		return weixinAccountsDao.checkBySysCode(sysCode);
	}

	@Override
	public List<WeixinAccounts> findAll() {
		return weixinAccountsDao.findAll();
	}
	
}
