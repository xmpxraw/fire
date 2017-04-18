package org.yuantai.system.dao.hibernate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.yuantai.common.SystemConfig;
import org.yuantai.system.dao.AuthDao;
import org.yuantai.system.dao.RoleDao;
import org.yuantai.system.pojo.Function;
import org.yuantai.system.pojo.Menu;
import org.yuantai.system.pojo.MenuCache;
import org.yuantai.system.pojo.OnlineUser;
import org.yuantai.system.pojo.Role;

/**
 * 基于角色的权限控制-数据库操作实现类
 * @author zhangle
 */
@Repository
public class RoleAuthDaoImpl extends BaseDaoImpl implements AuthDao {

	@Autowired private RoleDao roleDao;
	@Autowired private TransactionTemplate transactionTemplate;
	
	private Role guest;
	private Role developer;
	
	private Map<String,Pattern> patternMap=new HashMap<String,Pattern>(200);		//正则匹配模式缓存
	private Map<String,Boolean> menuCache= new HashMap<String,Boolean>(200);		//角色与菜单的缓存
	
	/**
	 * 初始化获取特殊角色对象
	 */
	@PostConstruct
	public void init() {
		
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				String guestRole=SystemConfig.getProperty("system.role.guest");
				if(!StringUtils.isBlank(guestRole)) {
					guest=roleDao.getRoleByName(guestRole);
				}
				String developerRole=SystemConfig.getProperty("system.role.developer");
				if(!StringUtils.isBlank(developerRole)) {
					developer=roleDao.getRoleByName(developerRole);
				}
			}
		});
	}
	
	@Override
	public boolean checkUrl(OnlineUser loginUser, String url) {
		return checkUrl(getRoles(loginUser), url);
	}
	
	private boolean checkUrl(List<Role> roles, String url) {
		
		if(roles==null || roles.isEmpty() || StringUtils.isBlank(url)) return false;
		if(developer!=null && roles.contains(developer)) return true;		//如果有developer角色,那么直接通过,不需要验证
		
		Query query=getNamedQuery("system.checkUrlForRole");
		query.setParameterList("roleId", toIdArray(roles));
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
		return checkCode(getRoles(loginUser),functionCode);
	}
	
	private boolean checkCode(List<Role> roles, String functionCode) {
		
		if(roles==null || roles.isEmpty() || StringUtils.isBlank(functionCode)) return false;
		if(developer!=null && roles.contains(developer)) return true;		//如果有developer角色,那么直接通过,不需要验证
		
		Query query=getNamedQuery("system.checkCodeForRole");
		query.setString("code", functionCode);
		query.setParameterList("roleId", toIdArray(roles));
		try {
			Long count=(Long)query.uniqueResult();
			return count>0;
		} catch(Exception e) {
			return false;
		}
	}
	
	@Override
	public List<Menu> checkMenu(OnlineUser loginUser, String parentId) {
		return checkMenu(getRoles(loginUser),parentId);
	}
	
	private List<Menu> checkMenu(List<Role> roles, String parentId) {
		
		if(roles==null || roles.isEmpty()) return Collections.EMPTY_LIST;
		
		Query query=null;
		if(parentId!=null) {
			query=getNamedQuery("system.checkMenuForRole");
			query.setString("parentId", parentId);
		} else {
			query=getNamedQuery("system.checkTopMenuForRole");
		}
		query.setParameterList("roleId", toIdArray(roles));
		
		return query.list();
	}

	/**
	 * 刷新角色权限缓存
	 * 目前只有为角色与菜单的关联建缓存
	 */
	@Override
	public void refreshCache() {
		clearMenuCache();
		List<Menu> menus=getCriteria(Menu.class).list();
		List<Role> roles=getCriteria(Role.class).list();
		
		List<Role> rs=new ArrayList<Role>(1);
		for(Menu m:menus) {
			if(m.getType()==Menu.TYPE_DIRECTORY || m.getStatus()==Menu.STATUS_DISABLE) {
				continue;
			}
			for(Role r:roles) {
				rs.add(r);
				if(checkUrl(rs, m.getUrl())) {
					addMenuCache(r,m);
				}
				rs.clear();
			}
		}
	}
	
	/**
	 * 添加角色与菜单的缓存关联
	 * @param role
	 * @param menu
	 */
	private void addMenuCache(Role role,Menu menu) {
		
		//如果未缓存,那么加入一条缓存记录
		if(!hasCache(role,menu)) {
			MenuCache cache=new MenuCache();
			cache.setMenuId(menu.getId());
			cache.setDataId(role.getId());
			cache.setType(MenuCache.TYPE_ROLE);
			getSession().save(cache);
		}
		
		//递归缓存父菜单
		if(menu.getParentId()!=null) {
			Menu parent=(Menu) getSession().get(Menu.class, menu.getParentId());
			addMenuCache(role, parent);
		}
	}
	
	/**
	 * 判断角色与菜单的关联是否已缓存
	 * @param role
	 * @param menu
	 * @return
	 */
	private boolean hasCache(Role role,Menu menu) {
		
		String key=role.getId()+"|"+menu.getId();
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
		executeUpdate("delete MenuCache where type=?",MenuCache.TYPE_ROLE);
	}

	/**
	 * 将角色集合转换为只保留id的数组
	 * @param roles
	 * @return
	 */
	private String[] toIdArray(List<Role> roles) {
		String[] id=new String[roles.size()];
		for(int i=0;i<roles.size();i++) {
			id[i]=roles.get(i).getId();
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
	
	/**
	 * 获取需要权限验证的角色,如果有存在来宾角色,那么也加入用户角色
	 * @param loginUser
	 * @return
	 */
	private List<Role> getRoles(OnlineUser loginUser) {
		
		List<Role> roles=null;
		
		if(loginUser==null) {				//如果用户未登录
			roles=new ArrayList<Role>(1);
			if(guest!=null) {				//如果有来宾角色,那么加入来宾角色,否则返回空集合
				roles.add(guest);
			}
		} else {							//否则用户已登录
			if(guest!=null) {				//有来宾角色,则加入来宾角色,否则不加
				roles=new ArrayList<Role>(loginUser.getRoles());
				roles.add(guest);
			} else {
				roles=loginUser.getRoles();
			}
		}
		return roles;
	}
}
