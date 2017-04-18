package org.yuantai.system.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.yuantai.system.dao.LogsDao;
import org.yuantai.system.pojo.Logs;
import org.yuantai.system.service.LogsService;

@Service
public class LogsServiceImpl extends BaseServiceImpl<Logs> implements LogsService {
	@Autowired private LogsDao logsDao;
	
	@Override
	public void deleteBefore(Date date) {
		logsDao.deleteBefore(date);
	}
}
