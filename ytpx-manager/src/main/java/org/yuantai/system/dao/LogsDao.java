package org.yuantai.system.dao;

import java.util.Date;

import org.yuantai.system.pojo.Logs;

/**
 * 日志数据访问接口
 * @author zhangle
 */
public interface LogsDao extends BaseDao<Logs> {

	public void deleteBefore(Date date);
}
