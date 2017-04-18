package org.yuantai.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

/**
 * 分页器
 * 页号从1开始,page==1就是第一页
 * 行号从0开始
 * @author zhangle
 */
public class Paginator<T> {
	
	public static final int DEFAULT_PAGE_SIZE=20;		//默认分页行数
	
	private int page;					//当前页号,从1开始
	private int pageSize;				//每页多少行
	private long totalCount;			//总共有多少行
	private List<T> dataList;			//数据列表
	private DetachedCriteria criteria; //hibernate查询条件
	private Map<String,Object> conditions;	//自定义条件

	public Paginator(int page) {
		this(page,DEFAULT_PAGE_SIZE);
	}
	public Paginator(int page,int pageSize) {
		this.page=page;
		this.pageSize=pageSize;
	}
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public List<T> getDataList() {
		return dataList;
	}
	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
	
	/**
	 * 获得首页页号
	 * @return
	 */
	public int getFirstPage() {
		return 1;
	}
	/**
	 * 获得上一页页号
	 * @return
	 */
	public int getPrevPage() {
		return page<=getFirstPage()?getFirstPage():page-1;
	}
	/**
	 * 获得下一页页号
	 * @return
	 */
	public int getNextPage() {
		return page>=getLastPage()?getLastPage():page+1;
	}
	/**
	 * 获得最后一页页号
	 * @return
	 */
	public int getLastPage() {
		return ((int)Math.ceil(((double)totalCount)/((double)pageSize)));
	}
	/**
	 * 判断是否第一页
	 * @return
	 */
	public boolean getIsFirstPage() {
		return page<=getFirstPage();
	}
	/**
	 * 判断是否最后一页
	 * @return
	 */
	public boolean getIsLastPage() {
		return page>=getLastPage();
	}
	/**
	 * 获取当前页的起始行号
	 * @return
	 */
	public int getStartResult() {
		return (page-1)*pageSize;
	}
	/**
	 * 获取当前页的结束行号
	 * @return
	 */
	public long getEndResult() {
		int endResult=page*pageSize;
		if(endResult>=totalCount) return totalCount;
		return endResult;
	}
	/**
	 * 获取总共多少页
	 * @return
	 */
	public int getPageCount() {
		return getLastPage();
	}
	
	/**
	 * 获取查询条件对象
	 * @return
	 */
	public DetachedCriteria getCriteria() {
		return criteria;
	}
	/**
	 * 设置查询条件对象
	 * @param criteria
	 */
	public void setCriteria(DetachedCriteria criteria) {
		this.criteria = criteria;
	}
	/**
	 * 构造一个查询条件对象,并返回
	 * @param clazz
	 * @return
	 */
	public DetachedCriteria buildCriteria(Class<T> clazz) {
		criteria=DetachedCriteria.forClass(clazz);
		return criteria;
	}
	/**
	 * 获取自定义条件
	 * @return
	 */
	public Map<String, Object> getConditions() {
		if(conditions==null) {
			conditions=new HashMap<String, Object>();
		}
		return conditions;
	}
	/**
	 * 添加自定义条件
	 * @param field
	 * @param value
	 */
	public void addCondition(String field,Object value) {
		getConditions().put(field, value);
	}
}
