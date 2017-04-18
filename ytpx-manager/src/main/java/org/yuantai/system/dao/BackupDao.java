package org.yuantai.system.dao;

import java.util.Date;

import org.yuantai.system.pojo.Backup;

public interface BackupDao extends BaseDao<Backup> {

	/**
	 * 删除指定日期之前的所有备份
	 * @param date
	 */
	public void deleteBefore(Date date);
}
