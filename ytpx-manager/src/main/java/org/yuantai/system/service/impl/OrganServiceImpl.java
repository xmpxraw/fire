package org.yuantai.system.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yuantai.common.Paginator;
import org.yuantai.common.util.StringUtil;
import org.yuantai.system.aop.Logging;
import org.yuantai.system.dao.OrganDao;
import org.yuantai.system.dao.OrganDataDao;
import org.yuantai.system.dao.UserDao;
import org.yuantai.system.dao.hibernate.DaoUtil;
import org.yuantai.system.pojo.Organ;
import org.yuantai.system.pojo.OrganData;
import org.yuantai.system.pojo.OrganGroup;
import org.yuantai.system.pojo.OrganGroupX;
import org.yuantai.system.pojo.User;
import org.yuantai.system.service.OrganService;

@Logging
@Service
public class OrganServiceImpl extends BaseServiceImpl<Organ> implements OrganService {

	@Autowired private OrganDao organDao;
	@Autowired private OrganDataDao organDataDao;
	@Autowired private UserDao userDao;
	@Autowired private DaoUtil daoutil;

	@Override
	public List<Organ> getChildren(String parentId,Integer type) {
		return organDao.getChildren(parentId,type,Organ.STATUS_DEFAULT);
	}

	@Override
	public void sort(Organ[] organs) {
		organDao.sort(organs);
	}
	
	@Override
	public void move(String targetId, String sourceId, String point) {
		organDao.move(targetId, sourceId, point);
	}

	@Override
	public void functionSetup(String organId, String[] checkedFuncId,String[] uncheckedFuncId){
		organDao.functionSetup(organId, checkedFuncId, uncheckedFuncId);
	}

	@Override
	public Organ getMainEmployee(String userId) {
		return organDao.getMainEmployee(userId);
	}

	@Override
	public Organ getDepartment(String empId) {
		return organDao.getDepartment(empId);
	}
	
	@Override
	public Organ getMainDepartment(String userId) {
		return organDao.getMainDepartment(userId);
	}

	@Override
	public Organ getTeam(String empId) {
		return organDao.getTeam(empId);
	}
	
	@Override
	public List<Organ> getParents(String organId) {
		return organDao.getParents(organId);
	}
	
	/**
	 * 根据userId获取用户的所有职位信息
	 * @param userId
	 * @return
	 */
	public List<Organ> getOrgansByUserId(String userId) {
		return organDao.getOrgansByUserId(userId);
	}

	/**
	 * 设置用户的主要员工
	 * @param userId
	 */
	public void setMainEmployee(String userId,String organId) {
		organDao.setMainEmployee(userId, organId);
	}
	
	/**
	 * 根据roleId获取用户的所有职位信息
	 * 获取拥有该角色的所有用户,并在组织机构表中找到该用户设置为主要的职位
	 * @param roleId
	 * @return
	 */
	public List<Organ> getOrgansByRoleId(String roleId) {
		return organDao.getOrgansByRoleId(roleId);
	}

	@Override
	public List<OrganData> getOrganDataByOrganId(String organId) {
		return organDataDao.getDataByOrganId(organId);
	}

	@Override
	public void addOrganDatas(String organId, OrganData[] datas) {
		organDataDao.add(organId, datas);
	}

	@Override
	public Organ getByCode(String code) {
		return organDao.getByCode(code);
	}

	@Override
	public void findGroups(Paginator<OrganGroup> pagin) {
		daoutil.find(pagin);
	}

	@Override
	public OrganGroup getGroup(String groupid) {
		return daoutil.get(OrganGroup.class, groupid);
	}
	
	@Override
	public OrganGroup getGroupByName(String groupname) {
		return daoutil.get(OrganGroup.class, "name", groupname);
	}
	
	@Override
	public List<Organ> findOrganByGroupid(String groupid) {
		List<Organ> organs=daoutil.executeQuery(
				"select o from OrganGroupX x,Organ o where x.groupid=? and o.id=x.organid order by o.seqNum asc", 
				groupid);
		return organs;
	}
	
	@Override
	public List<Organ> findOrganByGroupCode(String groupcode) {
		List<Organ> organs=daoutil.executeQuery(
				"select o from OrganGroup g,OrganGroupX x,Organ o where g.code=? and x.groupid=g.id and o.id=x.organid order by o.seqNum asc", 
				groupcode);
		return organs;
	}
	
	@Override
	public List<Organ> findOrganByGroupName(String groupname) {
		List<Organ> organs=daoutil.executeQuery(
				"select o from OrganGroup g,OrganGroupX x,Organ o where g.name=? and x.groupid=g.id and o.id=x.organid order by o.seqNum asc", 
				groupname);
		return organs;
	}
	
	@Override
	public void deleteGroup(String groupid) {
		daoutil.executeUpdate("delete OrganGroupX where groupid=?", groupid);
		daoutil.executeUpdate("delete OrganGroup where id=?", groupid);
	}

	@Override
	public void addGroup(OrganGroup group, String[] organid) {
		
		if(getGroupByName(group.getName())!=null) {
			throw new RuntimeException("工作组名已存在");
		}
		
		group.setSeqnum((int)daoutil.nextSeqnum(OrganGroup.class, "seqnum"));
		group.setCreatetime(new Date());
		daoutil.add(group);
		addGroupX(group.getId(), organid);
	}

	private void addGroupX(String groupid, String[] organid) {
		if(organid==null) return;
		
		int seqnum=1;
		for(String oid:organid) {
			OrganGroupX groupx=new OrganGroupX();
			groupx.setGroupid(groupid);
			groupx.setOrganid(oid);
			groupx.setSeqnum(seqnum++);
			daoutil.add(groupx);
		}
	}

	@Override
	public void updateGroup(OrganGroup param, String[] organid) {
		
		OrganGroup temp = getGroupByName(param.getName());
		if(temp!=null && !temp.getId().equals(param.getId())) {
			throw new RuntimeException("工作组名已存在");
		}
		
		OrganGroup group=daoutil.get(OrganGroup.class, param.getId());
		group.setCode(param.getCode());
		group.setName(param.getName());
		group.setRemark(param.getRemark());
		daoutil.update(group);
		daoutil.executeUpdate("delete OrganGroupX where groupid=?", group.getId());
		addGroupX(group.getId(), organid);
	}

	@Override
	public List<Organ> getOrganByData(String code, String value) {
		
		List<OrganData> dataList=organDataDao.getOrganByCode(code, value);
		List<Organ> organList=new ArrayList<Organ>(dataList.size());
		for(OrganData data:dataList) {
			Organ organ=organDao.get(data.getOrganId());
			if(organ==null) continue;
			organList.add(organ);
		}
		return organList;
	}

	@Override
	public Organ getPrincipal(String organId) {
		Organ organ=(Organ) daoutil.uniqueResult("select o from Organ o where o.id=(select principalId from Organ where id=?)", organId);
		return organ;
	}

	@Override
	public String getMobile(String organId) {
		
		Organ organ=get(organId);
		if(organ==null) return "";
		
		//先获取用户个人设置的手机号
		if(!StringUtil.isEmpty(organ.getUserId())) {
			User user=userDao.get(organ.getUserId());
			if(!StringUtil.isEmpty(user.getMobile())) {
				return user.getMobile();
			}
		}
		
		//其次再获取组织机构中的个人号码,或办公号码
		if(!StringUtil.isEmpty(organ.getMobile())) {
			return organ.getMobile();
		} else {
			return StringUtil.isEmpty(organ.getOfficePhone())?"":organ.getOfficePhone();
		}
	}

	@Override
	public List<Organ> getChangwu(String deptId) {
		List<Organ> organs=daoutil.executeQuery("select o from Organ o where o.grade=? and o.type=? and o.status=?", 
				Organ.GRADE_CHANGWU,Organ.TYPE_EMPLOYEE,Organ.STATUS_DEFAULT);
		
		List<Organ> changwu=new ArrayList<Organ>();
		for(Organ organ:organs) {
			List<Organ> parents=getParents(organ.getId());
			if(contains(parents,deptId)) {
				changwu.add(parents.get(0));
			}
		}
		return changwu;
	}
	
	private boolean contains(List<Organ> organs,String deptId) {
		
		for(Organ organ:organs) {
			if(deptId.equals(organ.getId())) return true;
		}
		return false;
	}

	@Override
	public List<Organ> getFujuzhang(String deptId) {
		
		List<Organ> organs=daoutil.executeQuery("select o from Organ o where o.grade=? and o.type=? and o.status=?", 
				Organ.GRADE_FUJUZHANG,Organ.TYPE_EMPLOYEE,Organ.STATUS_DEFAULT);
		
		List<Organ> fujuzhang=new ArrayList<Organ>();
		for(Organ organ:organs) {
			List<Organ> parents=getParents(organ.getId());
			if(contains(parents,deptId)) {
				fujuzhang.add(parents.get(0));
			}
		}
		return fujuzhang;
	}
	
	@Override
	public List<Organ> getPrincipalForTeam(String teamId) {
		
		Organ principal=getPrincipal(teamId);
		if(principal==null) return Collections.emptyList();
		
		List<Organ> organs=new ArrayList<Organ>(1);
		organs.add(principal);
		return organs;
	}

	@Override
	public List<Organ> getTeamForPrincipal(String principalId) {
		
		List<Organ> organs=daoutil.executeQuery("select o from Organ o where o.principalId=? and o.type=? and o.status=?", 
				principalId,Organ.TYPE_TEAM,Organ.STATUS_DEFAULT);
		return organs;
	}

	@Override
	public List<Organ> getKezhang(String teamId) {
		
		List<Organ> organs=daoutil.executeQuery("select o from Organ o where o.parentId=? and o.grade=? and o.type=? and o.status=?", 
				teamId,Organ.GRADE_KEZHANG,Organ.TYPE_EMPLOYEE,Organ.STATUS_DEFAULT);
		return organs;
	}

	@Override
	public List<Organ> getFukezhang(String teamId) {
		List<Organ> organs=daoutil.executeQuery("select o from Organ o where o.parentId=? and o.grade=? and o.type=? and o.status=?", 
				teamId,Organ.GRADE_FUKEZHANG,Organ.TYPE_EMPLOYEE,Organ.STATUS_DEFAULT);
		return organs;
	}

}
