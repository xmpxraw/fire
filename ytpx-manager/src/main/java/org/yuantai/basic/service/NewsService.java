package org.yuantai.basic.service;

import java.util.List;

import org.yuantai.basic.pojo.News;
import org.yuantai.system.service.BaseService;

public interface NewsService extends BaseService<News> {

	public void sort(News[] news);
	
	/**
	 * 分页查询最新动态
	 * @param startrow
	 * @param pagesize
	 * @return
	 */
	public List<News> findForHome(int startrow,int pagesize);
}
