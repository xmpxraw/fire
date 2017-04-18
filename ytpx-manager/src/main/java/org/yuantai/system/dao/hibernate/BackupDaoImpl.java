package org.yuantai.system.dao.hibernate;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.yuantai.common.SystemConfig;
import org.yuantai.common.util.DateUtil;
import org.yuantai.common.util.FileUtil;
import org.yuantai.common.util.RuntimeUtil;
import org.yuantai.system.dao.BackupDao;
import org.yuantai.system.pojo.Backup;

@Repository
public class BackupDaoImpl extends BaseDaoImpl<Backup> implements BackupDao {

	@Override
	public void add(Backup po) {
		try {
			String cmd=SystemConfig.getProperty("system.backup.exec");
			String sqlpath=SystemConfig.getProperty("system.backup.path");
			FileUtil.mkdir(sqlpath);
			sqlpath+="/"+DateUtil.getCurrentDateStr("yyyyMMddHHmmss-")+(new Random().nextInt(10000))+".sql";
			RuntimeUtil.exec(cmd+" > "+sqlpath);
			
			po.setSqlPath(sqlpath);
			po.setCreateTime(new Date());
			super.add(po);
		} catch (Exception e) {
			throw new RuntimeException("数据备份失败!",e);
		}
	}
	
	@Override
	public void delete(String id) {
		try {
			Backup backup=get(id);
			File bakfile=new File(backup.getSqlPath());
			bakfile.delete();
		} catch (Exception e) {}
		super.delete(id);
	}

	@Override
	public void deleteBefore(Date date) {
		Criteria criteria=getCriteria();
		criteria.add(Restrictions.lt("createTime", date));
		List<Backup> backups=criteria.list();
		
		for(Backup b:backups) {
			this.delete(b.getId());
		}
	}
}
