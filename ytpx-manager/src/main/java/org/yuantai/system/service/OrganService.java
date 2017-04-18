package org.yuantai.system.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.yuantai.common.Paginator;
import org.yuantai.system.pojo.Organ;
import org.yuantai.system.pojo.OrganData;
import org.yuantai.system.pojo.OrganGroup;

/**
 * 组织机构服务接口
 * 
 * 特殊参数名约定	
 * organId:表示可以使用所有类型的organ对象
 * empId:表示只能使用员工类型的organ对象
 * deptId:表示只能使用部门类型的organ对象
 * teamId:表示只能使用科室类型的organ对象
 * 
 * @ClassName: OrganService
 * @Description:
 * @author zhangle
 * @email  zhanngle@163.com
 * @date 2015年12月29日 下午11:15:15
 */
@Service
public interface OrganService extends BaseService<Organ>{
	
	/**
	 * 获取子机构
	 * @param parentId	父机构ID,当parentId==null时,返回顶级机构
	 * @return
	 */
	public List<Organ> getChildren(String parentId,Integer type);
	
	/**
	 * 排序
	 * 传进来的Organ对象中,只有id,name,seqNum三个属性是正确的,其它的都不正常,请匆使用其它属性
	 * @param menus
	 */
	public void sort(Organ[] organs);
	
	/**
	 * 移动功能
	 * @param targetId		目标机构id
	 * @param sourceId		源机构id
	 * @param point			操作类型	取值范围: append,top,bottom
	 */
	public void move(String targetId,String sourceId,String point);
	
	/**
	 * 权限设置
	 * @param organId
	 * @param checkedFuncId			已选中的功能ID
	 * @param uncheckedFuncId		未选中的功能ID
	 */
	public void functionSetup(String organId, String[] checkedFuncId,String[] uncheckedFuncId);
	
	/**
	 * 获取用户的主要员工对象
	 * @param userId
	 * @return
	 */
	public Organ getMainEmployee(String userId);
	
	/**
	 * 获取用户的主要部门对象
	 * @param userId
	 * @return
	 */
	public Organ getMainDepartment(String userId);

	/**
	 * 获取员工的部门对象
	 * @param empId
	 * @return
	 */
	public Organ getDepartment(String empId);
	
	/**
	 * 获取员工的科室(团队)对象
	 * @param empId
	 * @return
	 */
	public Organ getTeam(String empId);
	
	/**
	 * 获取节点的所有父节点,包括本节点,从内到外排序,本节点在最前面
	 * @param organId
	 * @return
	 */
	public List<Organ> getParents(String organId);
	
	/**
	 * 根据userId获取用户的所有职位信息
	 * @param userId
	 * @return
	 */
	public List<Organ> getOrgansByUserId(String userId);
	
	/**
	 * 设置用户的主要员工
	 * 先清除用户的主要员工,再根据organId设置主要员工
	 * @param userId		
	 * @param empId		当为null时,不设置主要员工
	 */
	public void setMainEmployee(String userId,String empId);
	
	/**
	 * 根据roleId获取用户的所有职位信息
	 * 获取拥有该角色的所有用户,并在组织机构表中找到该用户设置为主要的职位
	 * @param roleId
	 * @return
	 */
	public List<Organ> getOrgansByRoleId(String roleId);
	
	//======== 数据绑定相关 =======================
	/**
	 * 获取组织节点的绑定数据
	 * @param organId
	 * @return
	 */
	public List<OrganData> getOrganDataByOrganId(String organId);
	
	/**
	 * 批量更新组织数据
	 * @return
	 */
	public void addOrganDatas(String organId,OrganData[] organDatas);
	
	/**
	 * 获取绑定了指定数据的organ对象
	 * 相当于为organ动态扩展了字段,现在需要通过该动态字段获取出organ对象
	 * @param code
	 * @param value
	 * @return
	 */
	public List<Organ> getOrganByData(String code,String value);
	
	//======= 工作组相关 ========================
	/**
	 * 查找工作组
	 * @param pagin
	 * @return
	 */
	public void findGroups(Paginator<OrganGroup> pagin);
	
	/**
	 * 获取工作组详情
	 * @param groupid
	 * @return
	 */
	public OrganGroup getGroup(String groupid);
	
	/**
	 * 根据名称获取工作组
	 * @param groupname
	 * @return
	 */
	public OrganGroup getGroupByName(String groupname);
	
	/**
	 * 获取组中成员
	 * @param groupid
	 * @return
	 */
	public List<Organ> findOrganByGroupid(String groupid);
	
	/**
	 * 根据组code获取组成员
	 * @param groupname
	 * @return
	 */
	public List<Organ> findOrganByGroupCode(String groupcode);
	
	/**
	 * 根据组名获取组成员
	 * @param groupname
	 * @return
	 */
	public List<Organ> findOrganByGroupName(String groupname);
	
	/**
	 * 删除工作组
	 * @param groupid
	 */
	public void deleteGroup(String groupid);
	
	/**
	 * 添加工作组
	 * @param group
	 * @param organid
	 */
	public void addGroup(OrganGroup group,String[] organid);
	
	/**
	 * 修改工作组
	 * @param group
	 * @param organid
	 */
	public void updateGroup(OrganGroup group,String[] organid);
	
	//======== 工作流相关 =====================
	
	/**
	 * 获取手机号码
	 * @param organId
	 * @return
	 */
	public String getMobile(String organId);
	
	/**
	 * 通过code获取organ对象
	 * @param no
	 * @return
	 */
	public Organ getByCode(String code);
	
	/**
	 * 获取节点负责人
	 * @param organId
	 * @return
	 */
	public Organ getPrincipal(String organId);
	
	/**
	 * 获取常务
	 * @param deptId
	 * @return
	 */
	public List<Organ> getChangwu(String deptId);
	
	/**
	 * 获取所有副局长(不包括常务)
	 * @param deptId
	 * @return
	 */
	public List<Organ> getFujuzhang(String deptId);
	
	/**
	 * 获取科室分管领导(一般都是副局长,但有些特殊科室直接由常务分管)
	 * @param teamId
	 * @return
	 */
	public List<Organ> getPrincipalForTeam(String teamId);
	
	/**
	 * 获取领导分管的科室
	 * @param fujuzhangId
	 * @return
	 */
	public List<Organ> getTeamForPrincipal(String principalId);
	
	/**
	 * 获取科长
	 * @param teamId
	 * @return
	 */
	public List<Organ> getKezhang(String teamId);
	
	/**
	 * 获取副科长
	 * @param teamId
	 * @return
	 */
	public List<Organ> getFukezhang(String teamId);
	
}
