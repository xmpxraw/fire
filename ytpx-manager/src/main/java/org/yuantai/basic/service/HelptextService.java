package org.yuantai.basic.service;

import java.util.List;

import org.yuantai.basic.pojo.Helptext;
import org.yuantai.system.service.BaseService;

public interface HelptextService extends BaseService<Helptext> {

	public void sort(Helptext[] helptexts);
	
	/**
	 * 查找首页的帮助说明记录
	 * @param startrow
	 * @param pagesize
	 * @return
	 */
	public List<Helptext> findForHome(int startrow, int pagesize);
}
