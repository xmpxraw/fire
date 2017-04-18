package org.yuantai.system.dao.hibernate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Subqueries;
import org.springframework.stereotype.Repository;
import org.yuantai.system.dao.AuthDao;
import org.yuantai.system.pojo.Function;
import org.yuantai.system.pojo.Menu;
import org.yuantai.system.pojo.MenuCache;
import org.yuantai.system.pojo.OnlineUser;
import org.yuantai.system.pojo.Organ;
import org.yuantai.system.pojo.OrganFunction;

/**
 * 基于组织机构的权限控制-数据库操作实现类
 * @author zhangle
 */
@Repository
public class OrganAuthDaoImpl extends BaseDaoImpl implements AuthDao {

	private Map<String,Pattern> patternMap=new HashMap<String,Pattern>(200);		//正则匹配模式缓存
	private Map<String,Boolean> menuCache= new HashMap<String,Boolean>(200);		//组织机构与菜单的缓存
	
	@Override
	public boolean checkUrl(OnlineUser loginUser, String url) {
		return checkUrl(getOrgans(loginUser),url);
	}
	
	private boolean checkUrl(List<Organ> organs, String url) {
		
		if(organs==null || organs.isEmpty() || StringUtils.isBlank(url)) return false;
		
		Query query=getNamedQuery("system.checkUrlForOrgan");
		query.setParameterList("organId", toIdArray(organs));
		List<Function> functions=query.list();
		
		for(Function f:functions) {
			if(StringUtils.isBlank(f.getUrl())) continue;
			Pattern p=getPattern(f);
			if(p.matcher(url).matches()) return true;
		}
		
		return false;
	}

	@Override
	public boolean checkCode(OnlineUser loginUser, String functionCode) {
		return checkCode(getOrgans(loginUser),functionCode);
	}
	
	private boolean checkCode(List<Organ> organs, String functionCode) {
		
		if(organs==null || organs.isEmpty() || StringUtils.isBlank(functionCode)) return false;
		
		Query query=getNamedQuery("system.checkCodeForOrgan");
		query.setString("code", functionCode);
		query.setParameterList("organId", toIdArray(organs));
		try {
			Long count=(Long)query.uniqueResult();
			return count>0;
		} catch(Exception e) {
			return false;
		}
	}

	@Override
	public List<Menu> checkMenu(OnlineUser loginUser, String parentId) {
		return checkMenu(getOrgans(loginUser),parentId);
	}
	
	private List<Menu> checkMenu(List<Organ> organs, String parentId) {
		
		if(organs==null || organs.isEmpty()) return Collections.EMPTY_LIST;
		
		Query query=null;
		if(parentId!=null) {
			query=getNamedQuery("system.checkMenuForOrgan");
			query.setString("parentId", parentId);
		} else {
			query=getNamedQuery("system.checkTopMenuForOrgan");
		}
		query.setParameterList("organId", toIdArray(organs));
		
		return query.list();
	}
	
	/**
	 * 刷新组织机构权限缓存
	 * 目前只有为组织机构与菜单的关联建缓存
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void refreshCache() {
		
		clearMenuCache();
		List<Menu> menus=getCriteria(Menu.class).list();
		
		DetachedCriteria subquery=DetachedCriteria.forClass(OrganFunction.class);
		subquery.setProjection(Projections.distinct(Projections.property("organId")));
		List<Organ> organs=getCriteria(Organ.class)
				.add(Subqueries.propertyIn("id", subquery))
				.list();
		
		List<Organ> os=new ArrayList<Organ>(1);
		for(Menu m:menus) {
			if(m.getType()==Menu.TYPE_DIRECTORY || m.getStatus()==Menu.STATUS_DISABLE) {
				continue;
			}
			for(Organ o:organs) {
				os.add(o);
				if(checkUrl(os, m.getUrl())) {
					addMenuCache(o,m);
				}
				os.clear();
			}
		}
	}
	
	/**
	 * 添加组织与菜单的缓存关联
	 */
	private void addMenuCache(Organ organ,Menu menu) {
		
		//如果未缓存,那么加入一条缓存记录
		if(!hasCache(organ,menu)) {
			MenuCache cache=new MenuCache();
			cache.setMenuId(menu.getId());
			cache.setDataId(organ.getId());
			cache.setType(MenuCache.TYPE_ORGAN);
			getSession().save(cache);
		}
		
		//递归缓存父菜单
		if(menu.getParentId()!=null) {
			Menu parent=(Menu) getSession().get(Menu.class, menu.getParentId());
			addMenuCache(organ, parent);
		}
	}
	
	/**
	 * 判断组织与菜单的关联是否已缓存
	 * @param organ
	 * @param menu
	 * @return
	 */
	private boolean hasCache(Organ organ,Menu menu) {
		
		String key=organ.getId()+"|"+menu.getId();
		if(menuCache.containsKey(key)) {
			return true;
		}
		menuCache.put(key, true);
		return false;
	}
	
	/**
	 * 清除与菜单有关的缓存
	 */
	private void clearMenuCache() {
		menuCache.clear();
		executeUpdate("delete MenuCache where type=?",MenuCache.TYPE_ORGAN);
	}
	
	/**
	 * 获取权限验证需要的组织节点
	 * @param loginUser
	 * @return
	 */
	private List<Organ> getOrgans(OnlineUser loginUser) {
		if(loginUser==null || loginUser.getParents()==null) return null;
		return loginUser.getParents();
	}

	/**
	 * 将组织机构集合转换为只保留id的数组
	 * @param organs
	 * @return
	 */
	private String[] toIdArray(List<Organ> organs) {
		String[] id=new String[organs.size()];
		for(int i=0;i<organs.size();i++) {
			id[i]=organs.get(i).getId();
		}
		return id;
	}
	
	/**
	 * 获取功能的正则表达式匹配模式
	 * @param function
	 * @return
	 */
	private Pattern getPattern(Function function) {
		Pattern p=patternMap.get(function.getUrl());
		if(p==null) {
			p=Pattern.compile(function.getUrl());
			patternMap.put(function.getUrl(), p);
		}
		return p;
	}
}
