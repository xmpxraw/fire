package org.yuantai.system.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yuantai.common.JsonResult;
import org.yuantai.common.util.DateUtil;
import org.yuantai.common.util.StringUtil;
import org.yuantai.system.pojo.Backup;
import org.yuantai.system.service.BackupService;

@Controller
@RequestMapping(value="/system/backup")
public class BackupAction extends ModelAction<Backup> {
	@Autowired private BackupService backupService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String list() {
		return "system/backup/backup-list";
	}
    
    @RequestMapping(value="/query",method=RequestMethod.GET)
    public String query() {
    	return "system/backup/backup-query";
    }
    
    @RequestMapping(value="/query",method=RequestMethod.POST)
	public String query(String name,String fromDate,String toDate) {
		
		DetachedCriteria criteria=getPaginator().getCriteria();
		if(!StringUtil.isEmpty(name)) {
			criteria.add(Restrictions.like("name", name,MatchMode.ANYWHERE));
		}
		if(!StringUtil.isEmpty(fromDate)) {
			Date fd=DateUtil.parse(fromDate, "yyyy-MM-dd");
			criteria.add(Restrictions.ge("createTime", fd));
		}
		if(!StringUtil.isEmpty(toDate)) {
			Date td=DateUtil.parse(toDate+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
			criteria.add(Restrictions.le("createTime", td));
		}
		
		criteria.addOrder(Order.desc("createTime"));
		backupService.find(getPaginator());
		return pagin(getPaginator());
	}
    
    @RequestMapping(value="/add",method=RequestMethod.GET)
	public String add() {
		return "system/backup/backup-update";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doAdd(@RequestBody Backup backup) {
		backup.setCreator(loginuser().getUsername());
		backupService.add(backup);
		return result(200, "OK");
	}
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
	public String update(@PathVariable String id) {
		Backup backup=backupService.get(id);
		attr("backup", backup);
		return "system/backup/backup-update";
	}
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doUpdate(@PathVariable String id,@RequestBody Backup param) {
		Backup backup=backupService.get(id);
		backup.setName(param.getName());
		backup.setRemark(param.getRemark());
		backupService.update(backup);
		return result(200, "OK");
	}
	
	@RequestMapping(value="/delete/{id}",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult doDelete(@PathVariable String id) {
		backupService.delete(id);
		return result(200, "OK");
	}
	
	/**
	 * 下载备份文件
	 */
	@RequestMapping(value="/download/{id}")
	public void download(HttpServletResponse res,@PathVariable String id) {
		
		Backup backup=backupService.get(id);
		if(backup==null) return;
		
		FileInputStream fis=null;
		try {
			File bakfile=new File(backup.getSqlPath());
			fis = new FileInputStream(bakfile);
			
			res.reset();
	        res.addHeader("Content-Disposition", "attachment;filename="+new String(bakfile.getName().getBytes("gbk"),"iso-8859-1"));
	        res.addHeader("Content-Length", "" + bakfile.length());
	        res.setContentType("application/octet-stream; charset=utf-8");
	        IOUtils.copy(fis, res.getOutputStream());
		} catch (Exception e) {
			throw new RuntimeException("下载文件出错!",e);
		} finally {
			if(fis!=null) {
				try {
					fis.close();
				} catch (IOException e) {}
			}
		}
	}
}
