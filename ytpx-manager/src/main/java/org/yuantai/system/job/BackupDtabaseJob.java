package org.yuantai.system.job;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.yuantai.system.pojo.Backup;
import org.yuantai.system.service.BackupService;

@Component
public class BackupDtabaseJob {

	private static final Log logger = LogFactory.getLog(BackupDtabaseJob.class);

	@Resource private BackupService backupService;
	
	public void execute() {
		logger.info("正在执行系统备份...");
		
		//删除三个月之前的备份
		Date date=DateUtils.addMonths(new Date(), -3);
		backupService.deleteBefore(date);
		
		//备份当前数据
		Backup backup=new Backup();
		backup.setName("系统自动备份");
		backup.setRemark(null);
		backupService.add(backup);
		
		logger.info("系统备份结束!");
	}
}
