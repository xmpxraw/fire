package org.yuantai.system.service;

import java.util.Date;

import org.yuantai.system.pojo.Logs;

public interface LogsService extends BaseService<Logs> {

	public void deleteBefore(Date date);
}
