package org.yuantai.system.dao.hibernate;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.yuantai.common.SystemConfig;
import org.yuantai.common.util.Md5Util;
import org.yuantai.school.pojo.School;
import org.yuantai.system.dao.UserDao;
import org.yuantai.system.pojo.Role;
import org.yuantai.system.pojo.User;
import org.yuantai.system.pojo.UserRole;

/**
 * 用户数据访问实现类
 * @author zhangle
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{

	@Override
	public void add(User obj) {
		obj.setCreateTime(new Date());
		obj.setLoginNum(0);
		obj.setLoginTime(null);
		obj.setNickname(StringUtils.isBlank(obj.getNickname())?obj.getUsername():obj.getNickname());
		obj.setSalt(Md5Util.randomUUID());	//生成32位UUID
		obj.setPassword(Md5Util.makePassword(obj.getPassword(), obj.getSalt()));
		getSession().save(obj);
		
		//添加用户与角色的关联
		addUserRole(obj.getId(), obj.getCheckedRoleId());
		addPublicRole(obj.getId());
	}
	
	/**
	 * 默认为用户关联public角色
	 * 如果存在public角色则关联,如果不存在,则不关联
	 * @param userId
	 */
	private void addPublicRole(String userId) {
		
		String publicRoleName=SystemConfig.getProperty("system.role.public");
		if(StringUtils.isBlank(publicRoleName)) return;
		
		Role publicRole=super.get(Role.class, "name", publicRoleName);
		if(publicRole==null) return;
		
		Criteria criteria=getCriteria(UserRole.class)
				.add(Restrictions.eq("userId", userId))
				.add(Restrictions.eq("roleId", publicRole.getId()));
				
		if(countRow(criteria)==0) {		//如果没有关联public角色,则关联
			addUserRole(userId, new String[]{publicRole.getId()});
		}
	}
	
	/**
	 * 更新用户
	 * 此方法不允许修改用户密码,因此从数据库查询一次,覆盖新密码
	 */
	@Override
	public void update(User obj) {
		User temp=get(obj.getId());
		obj.setPassword(temp.getPassword());
		obj.setSalt(temp.getSalt());
		getSession().merge(obj);
		
		//添加用户与角色的关联
		if(obj.getCheckedRoleId()!=null) {
			deleteUserRole(obj.getId());
			addUserRole(obj.getId(), obj.getCheckedRoleId());
		}
	}
	
	@Override
	public void delete(String id) {
		executeUpdate("delete UserRole where userId=?",id);
		super.delete(id);
	}
	
	@Override
	public User getUserByName(String username) {
		return super.get(User.class, "username", username);
	}
	
	/*@Override
	public List<User> getUserBySysCode(String sysCode) {
		return super.get(User.class, "sysCode", sysCode);
	}*/
	@Override
	public List<User> getUserBySysCode(String sysCode) {
		
		Query query=null;
		if(sysCode!=null) {
			query=getNamedQuery("system.findBySysCode");
			query.setString("sysCode", sysCode);
		} 

		return query.list();
	}

	@Override
	public void updatePassword(String userId, String password) {
		
		User user=get(userId);
		String _tmppwd=Md5Util.makePassword(password, user.getSalt());
		if(user.getPassword().equals(_tmppwd)) {
			throw new RuntimeException("新密码不能与旧密码相同!");
		}
		
		String salt=Md5Util.randomUUID();	//生成32位UUID
		String newpwd=Md5Util.makePassword(password, salt);
		
		Query updateQuery=createQuery("update User set password=:password,salt=:salt where id=:userId");
		updateQuery.setString("password", newpwd);
		updateQuery.setString("salt", salt);
		updateQuery.setString("userId", user.getId());
		updateQuery.executeUpdate();
	}

	@Override
	public void updateAllPassword(String password) {

		Query updateQuery=createQuery("update User set password=:password where id=:userId");
		Iterator<User> it=getSession().createQuery("from User").iterate();
		while(it.hasNext()) {
			User user=it.next();
			String newpd=Md5Util.makePassword(password, user.getSalt());
			updateQuery.setString("password", newpd);
			updateQuery.setString("userId", user.getId());
			updateQuery.executeUpdate();
		}
	}

	/**
	 * 添加用户与角色的关联
	 * @param userId	关联的用户ID
	 * @param roleId	关联的角色ID数组
	 */
	public void addUserRole(String userId, String[] roleId) {
		if(userId==null || roleId==null || roleId.length==0) return;
		for(String _rid:roleId) {
			UserRole ur=new UserRole();
			ur.setUserId(userId);
			ur.setRoleId(_rid);
			getSession().save(ur);
		}
	}
	
	/**
	 * 删除用户与角色的所有关联
	 * @param userId	需要删除关联的用户ID
	 */
	public void deleteUserRole(String userId) {
		if(userId==null) return;
		executeUpdate("delete UserRole where userId=?",userId);
	}

	@Override
	public void updateLoginInfo(User user) {
		Query query=createQuery("update User set loginNum=:loginNum,loginTime=:loginTime where id=:userId");
		query.setInteger("loginNum", user.getLoginNum());
		query.setTimestamp("loginTime", user.getLoginTime());
		query.setString("userId", user.getId());
		query.executeUpdate();
	}
}

