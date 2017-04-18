package org.yuantai.system.job;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.yuantai.system.service.LogsService;

@Component
public class DeleteLogsJob {
	
	private static final Log logger = LogFactory.getLog(DeleteLogsJob.class);

	@Resource private LogsService logsService;
	
	public void execute() {
		
		logger.debug("删除操作日志记录!");
		//删除三个月之前的日志
		Date date=DateUtils.addMonths(new Date(), -3);
		logsService.deleteBefore(date);
	}
}
