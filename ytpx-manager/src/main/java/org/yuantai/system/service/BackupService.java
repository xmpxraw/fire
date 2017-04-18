package org.yuantai.system.service;

import java.util.Date;

import org.yuantai.system.pojo.Backup;

public interface BackupService extends BaseService<Backup> {

	/**
	 * 删除指定日期之前的所有备份
	 * @param date
	 */
	public void deleteBefore(Date date);
}
