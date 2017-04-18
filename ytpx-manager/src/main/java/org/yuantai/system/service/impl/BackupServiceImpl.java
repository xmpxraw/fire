package org.yuantai.system.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yuantai.system.aop.Logging;
import org.yuantai.system.dao.BackupDao;
import org.yuantai.system.pojo.Backup;
import org.yuantai.system.service.BackupService;

@Logging
@Service
public class BackupServiceImpl extends BaseServiceImpl<Backup> implements BackupService {
	@Autowired private BackupDao backupDao;
	
	@Override
	@Logging
	public void deleteBefore(Date date) {
		backupDao.deleteBefore(date);
	}
}
