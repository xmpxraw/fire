package org.yuantai.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yuantai.system.aop.Logging;
import org.yuantai.system.dao.RoleDao;
import org.yuantai.system.pojo.Role;
import org.yuantai.system.service.RoleService;

/**
 * 角色服务实现类
 * @author zhangle
 */
@Logging
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
	
	@Autowired private RoleDao roleDao;
	
	public List<Role> findRoleByUserId(String userId,Integer status) {
		return roleDao.findRoleByUserId(userId,status);
	}

	@Override
	public Role getRoleByName(String name) {
		return roleDao.getRoleByName(name);
	}
	
	@Override
	public Role getRoleByType(String type) {
		return roleDao.getRoleByType(type);
	}

	@Override
	public void sort(Role[] roles) {
		roleDao.sort(roles);
	}

	@Override
	public List<Role> findAll() {
		return roleDao.findAll();
	}
}
