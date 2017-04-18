package org.yuantai.system.web;

import org.springframework.core.ResolvableType;
import org.yuantai.common.Paginator;

public abstract class ModelAction<T> extends BaseAction {
	
	private Class<T> clazz;
	public ModelAction() {
		ResolvableType resolvableType = ResolvableType.forClass(getClass());
		this.clazz=(Class<T>) resolvableType.getSuperType().getGeneric(0).resolve();
	}
	
	/**
	 * 获取页号
	 * @return
	 */
	public int getPage() {
		return intparam("page", 1);
	}
	
	/**
	 * 获取每页查询多少行
	 * @return
	 */
	public int getPageSize() {
		return intparam("rows", Paginator.DEFAULT_PAGE_SIZE);
	}

	/**
	 * 获取分页器
	 * @return
	 */
	public Paginator<T> getPaginator() {
		Paginator<T> pagin=(Paginator<T>) attr("pagin");
		if(pagin==null) {
			pagin=new Paginator<T>(getPage(),getPageSize());
			pagin.buildCriteria(clazz);
			attr("pagin", pagin);
		}
		return pagin;
	}
	
	/**
	 * 获取分页器
	 * @return
	 */
	public <E> Paginator<E> getPaginator(Class<E> e) {
		Paginator<E> pagin=new Paginator<E>(getPage(),getPageSize());
		pagin.buildCriteria(e);
		return pagin;
	}
	
	/**
	 * 快速获取id参数
	 * @return
	 */
	protected String getId() {
		return param("id");
	}
	
}
