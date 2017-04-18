package org.yuantai.system.web;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yuantai.common.JsonResult;
import org.yuantai.common.Paginator;
import org.yuantai.common.SystemConfig;
import org.yuantai.common.TreeNode;
import org.yuantai.common.util.StringUtil;
import org.yuantai.system.pojo.Data;
import org.yuantai.system.pojo.Function;
import org.yuantai.system.pojo.Organ;
import org.yuantai.system.pojo.OrganData;
import org.yuantai.system.pojo.OrganGroup;
import org.yuantai.system.pojo.Role;
import org.yuantai.system.pojo.User;
import org.yuantai.system.service.DataService;
import org.yuantai.system.service.FunctionService;
import org.yuantai.system.service.OrganService;
import org.yuantai.system.service.RoleService;
import org.yuantai.system.service.UserService;

@Controller
@RequestMapping(value="/system/organ")
public class OrganAction extends ModelAction<Organ>{
	
	@Autowired private OrganService organService;
	@Autowired private FunctionService functionService;
	@Autowired private UserService userService;
	@Autowired private RoleService roleService;
	@Autowired private DataService dataService;
	
	private Map<String,String> grademap;
	
	@Value("${organ.auto_add_user}") String autoAddUser;
	@Value("${organ.default_user_password}") String defaultpwd;
	
	@RequestMapping(method=RequestMethod.GET)
	public String list() {
		return "system/organ/organ-list";
	}
    
    @RequestMapping(value="/query",method=RequestMethod.GET)
    public String query() {
    	return "system/organ/organ-query";
    }
    
    @RequestMapping(value="/query",method=RequestMethod.POST)
    @ResponseBody
	public List<TreeNode> query(Organ param) {
		String id=getId();
		String selectedId=param("selectedId");
		
		if(id!=null || (id==null && selectedId==null)) {
			return expandChildren(id);
		} else if(id==null && selectedId!=null) {
			return expandParents(selectedId);
		}
		return null;
	}
    
    /**
     * 高级查询
     * @param param
     */
    @RequestMapping(value="/doquery",method=RequestMethod.POST)
	public String doQuery(Organ param) {
    	DetachedCriteria criteria=getPaginator().getCriteria();
		if(!StringUtils.isBlank(param.getName())) {
			criteria.add(Restrictions.like("name", param.getName(),MatchMode.ANYWHERE));
		}
		if(!StringUtils.isBlank(param.getShortName())) {
			criteria.add(Restrictions.like("shortName", param.getShortName(),MatchMode.ANYWHERE));
		}
		if(param.getType()!=null) {
		    criteria.add(Restrictions.eq("type", param.getType()));
		}
		if(param.getMain()!=null) {
		    criteria.add(Restrictions.eq("main", param.getMain()));
		}
		if(!StringUtils.isBlank(param.getParentId())) {
		    criteria.add(Restrictions.eq("parentId", param.getParentId()));
		}
		criteria.add(Restrictions.eq("status", Organ.STATUS_DEFAULT));
		criteria.addOrder(Order.desc("seqNum"));
		organService.find(getPaginator());
		return pagin(getPaginator());
	}

	/**
	 * 扩展显示下级子节点
	 * @param parentId
	 */
	private List<TreeNode> expandChildren(String parentId) {
		
		List<TreeNode> tree=getTreeNodeByParentId(parentId);
		if(StringUtils.isBlank(parentId)) {	//如果是顶级节点,那么继续加载下面的二级节点
			for(TreeNode node:tree) {
				String _pid=node.getId();
				List<TreeNode> branch=getTreeNodeByParentId(_pid);
				node.setState(TreeNode.STATE_OPEN);
				node.setChildren(branch);
			}
		}
		return tree;
	}
	
	/**
	 * 获取父节点下的所有子节点(TreeNode对象)
	 * @param parentId
	 * @return
	 */
	private List<TreeNode> getTreeNodeByParentId(String parentId) {
		List<Organ> organs=organService.getChildren(parentId,null);
		List<TreeNode> tree=new ArrayList<TreeNode>(organs.size());
		
		for(Organ o:organs) {
			tree.add(convert(o));
		}
		return tree;
	}

	/**
	 * 扩展显示所有级别的父节点
	 * @param organId
	 */
	private List<TreeNode> expandParents(String organId) {
		
		List<TreeNode> tree=null;
		
		List<Organ> parents=organService.getParents(organId);		//获取所有父节点,包括本节点
		for(Organ p:parents) {		//从本节点开始,逐级往上查询父节点
			List<TreeNode> branch=getTreeNodeByParentId(p.getParentId());
			if(tree==null) {
				tree=branch;
			} else {
				for(TreeNode node:branch) {
					if(node.getId().equals(p.getId())) {
						node.setChildren(tree);
						node.setState(TreeNode.STATE_OPEN);
						tree=branch;
						break;
					}
				}
			}
		}
		
		if(!tree.isEmpty()) {		//给树的第一个节点设置属性selectedId,用于前端获取后,将此节点选中
			tree.get(0).getAttributes().put("selectedId", organId);
		}
		return tree;
	}
	
	/**
	 * 将organ对象转换为treenode对象
	 * @param organ
	 * @return
	 */
	private TreeNode convert(Organ organ) {
		
		TreeNode node=new TreeNode();
		node.setId(organ.getId());
		node.setText(organ.getName());
		if(organ.getType()==Organ.TYPE_ENTERPRISE) {
			node.setState(TreeNode.STATE_CLOSED);
			node.setIconCls("icon-organ");
		} else if(organ.getType()==Organ.TYPE_DEPARTMENT) {
			node.setState(TreeNode.STATE_CLOSED);
			node.setIconCls("icon-dept");
		} else if(organ.getType()==Organ.TYPE_TEAM) {
			node.setState(TreeNode.STATE_CLOSED);
			node.setIconCls("icon-team");
		} else {
			node.setText(empname(organ));
			node.setState(TreeNode.STATE_OPEN);
			if(StringUtils.isBlank(organ.getSex()) || organ.getSex().equals(Organ.SEX_MALE)) {
				node.setIconCls("icon-emp");
			} else {
				node.setIconCls("icon-emp-woman");
			}
		}
		node.getAttributes().put("parentId", organ.getParentId());
		node.getAttributes().put("type", organ.getType());
		
		return node;
	}
	
	/**
	 * 获取职员名称
	 * @param emp
	 * @return
	 */
	private String empname(Organ emp) {
		
		if(emp.getGrade()==Organ.GRADE_BANSHIYUANG) {
			return emp.getName();
		}
		
		String grade=grade(emp.getGrade());
		StringBuilder sb=new StringBuilder();
		sb.append(emp.getName()).append(" (").append(grade).append(")");
		return sb.toString();
	}
	
	/**
	 * 获取等级字符描述
	 * @param grade
	 * @return
	 */
	private String grade(int grade) {
		
		if(grademap==null) {
			grademap=new HashMap<String, String>();
			List<Data> grades=dataService.getChildren("system.organ.grade");
			for(Data data:grades) {
				grademap.put(data.getValue(), data.getText());
			}
		}
		return grademap.get(grade+"");
	}
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
   	public String add(@RequestParam(required=false) String parentId) {
    	if(!StringUtils.isBlank(parentId)) {
    		Organ parent=organService.get(parentId);
    		attr("parent", parent);
    	}
   		return "system/organ/organ-update";
   	}
   	
   	@RequestMapping(value="/add",method=RequestMethod.POST)
   	@ResponseBody
   	public JsonResult doAdd(Organ organ) {
   		organ.setCreateUserId(loginuser().getId());
   		try {
   			organService.add(organ);
   			autoAddUser(organ);
   		} catch(Exception e) {
   			return result(-1, e.getMessage());
   		}
   		return result(200, "OK");
   	}
   	
   	/**
	 * 自动添加用户
	 * @param organ
	 */
	private void autoAddUser(Organ organ) {
		
		if(!"true".equals(autoAddUser)) return;
		if(organ==null || organ.getType()!=Organ.TYPE_EMPLOYEE) return;
		if(!StringUtil.isEmpty(organ.getUserId())) return;
		
		//如果已存在此名称的用户,则不再自动添加用户
		if(userService.getUserByName(organ.getName())!=null) return;
		
		User user=new User();
		user.setUsername(organ.getName());
		user.setNickname(organ.getName());
		user.setPassword(defaultpwd);
		user.setStatus(User.STATUS_DEFAULT);
		User loginUser=loginuser();
		user.setCreateUserId(loginUser.getId());
		user.setCreateTime(new Timestamp(System.currentTimeMillis()));
		userService.add(user);
		
		//查出最新organ对象,为新增用户关联organ,
		Organ emp=organService.get(organ.getId());
		emp.setUserId(user.getId());
		emp.setUserName(user.getUsername());
		organService.update(emp);
	}
   	
   	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
   	public String update(@PathVariable String id) {
   		Organ organ=organService.get(id);
   		attr("organ", organ);
   		if(!StringUtils.isBlank(organ.getParentId())) {
    		Organ parent=organService.get(organ.getParentId());
    		attr("parent", parent);
    	}
   		if(!StringUtils.isBlank(organ.getUserId())) {
    		User user=userService.get(organ.getUserId());
    		attr("user", user);
    	}
   		if(!StringUtils.isBlank(organ.getPrincipalId())) {
    		Organ principal=organService.get(organ.getPrincipalId());
    		attr("principal", principal);
    	}
   		return "system/organ/organ-update";
   	}
   	
   	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
   	@ResponseBody
   	public JsonResult doUpdate(@PathVariable String id,Organ param) {
   		try {
   			organService.update(param);
   			return result(200, "OK");
   		} catch(Exception e) {
   			return result(-1, e.getMessage());
   		}
   	}
   	
   	@RequestMapping(value="/delete/{id}",method=RequestMethod.POST)
   	@ResponseBody
   	public JsonResult doDelete(@PathVariable String id) {
   		organService.delete(id);
   		return result(200, "OK");
   	}
	
   	/**
	 * 移动节点
	 * @param targetId		目标节点id
	 * @param sourceId		源节点id
	 * @param point			操作类型	取值范围: append,top,bottom
	 */
	@RequestMapping(value="/move",method=RequestMethod.POST)
   	@ResponseBody
	public JsonResult move(@RequestParam String targetId,@RequestParam String sourceId,@RequestParam String point) {
		organService.move(targetId, sourceId, point);
		return result(200, "OK");
	}
	
	//====排序代码====
	@RequestMapping(value="/sort",method=RequestMethod.GET)
   	public String sort() {
   		return "system/organ/organ-sort";
   	}
	
	@RequestMapping(value="/sort_data",method=RequestMethod.POST)
	@ResponseBody
	public List<Organ> sortData(@RequestParam(required=false) String parentId) {
		List<Organ> organs=organService.getChildren(parentId,null);
		return organs;
	}
   	
   	@RequestMapping(value="/sort",method=RequestMethod.POST)
   	@ResponseBody
   	public JsonResult doSort(@RequestBody Organ[] organs) {
   		organService.sort(organs);
   		return result(200, "OK");
   	}
   	
   	//====选择用户代码====
   	@RequestMapping(value="/select_user",method=RequestMethod.GET)
   	public String selectUser() {
   		return "system/organ/organ-user";
   	}
    @RequestMapping(value="/select_user",method=RequestMethod.POST)
	public String selectUser(User param) {
    	Paginator<User> pagin=getPaginator(User.class);
		DetachedCriteria criteria=pagin.getCriteria();
		if(!StringUtils.isEmpty(param.getUsername())) {
			criteria.add(Restrictions.like("username",param.getUsername(),MatchMode.ANYWHERE));
		}
		if(!StringUtils.isEmpty(param.getNickname())) {
			criteria.add(Restrictions.like("nickname", param.getNickname(),MatchMode.ANYWHERE));
		}
		if(param.getStatus()!=null) {
			criteria.add(Restrictions.eq("status", param.getStatus()));
		}
		criteria.addOrder(Order.desc("createTime"));
		userService.find(pagin);
		return pagin(pagin);
	}
    
    //====权限设置代码====
  	@RequestMapping(value="/function_setup",method=RequestMethod.GET)
 	public String functionSetup() {
 		return "system/organ/organ-function";
 	}
  	
  	@RequestMapping(value="/function_setup/{organId}",method=RequestMethod.GET)
  	@ResponseBody
  	public List<TreeNode> queryFunction(@PathVariable String organId,@RequestParam(required=false) String id) {

		List<Function> functions=functionService.getChildren(id, null);
		List<TreeNode> tree=new ArrayList<TreeNode>(functions.size());
		
    	List<Function> selectedFunctions=Collections.EMPTY_LIST;
    	if(!StringUtils.isBlank(organId)) {
    		selectedFunctions=functionService.getChildrenForOrgan(organId, id, null);
    	}
		
		for(Function f:functions) {
			TreeNode node=new TreeNode();
			node.setId(f.getId());
			node.setText(f.getName());
			if(f.getType()==Function.TYPE_DIRECTORY) {
				node.setState(TreeNode.STATE_CLOSED);
				node.setIconCls("icon-folder");
			} else {
				node.setState(TreeNode.STATE_OPEN);
				node.setIconCls("icon-star");
			}
			node.setChecked(selectedFunctions.contains(f));
			node.getAttributes().put("parentId", f.getParentId());
			node.getAttributes().put("type", f.getType());
			tree.add(node);
		}
		return tree;
  	}
    
  	@RequestMapping(value="/function_setup/{organId}",method=RequestMethod.POST)
 	@ResponseBody
 	public JsonResult doFunctionSetup(@PathVariable String organId,
 			@RequestParam("checkedFuncId[]") String[] checkedFuncId,
 			@RequestParam("uncheckFuncId[]")String[] uncheckFuncId) {
 		organService.functionSetup(organId, checkedFuncId, uncheckFuncId);
 		return result(200, "OK");
 	}
 	
 	 //====数据绑定代码====
  	@RequestMapping(value="/data_binding",method=RequestMethod.GET)
 	public String dataBinding() {
 		return "system/organ/organ-data";
 	}
  	@RequestMapping(value="/data_binding/{organId}",method=RequestMethod.GET)
  	@ResponseBody
  	public List<OrganData> queryDataBinding(@PathVariable String organId) {
		List<OrganData> organDatas = organService.getOrganDataByOrganId(organId);
		return organDatas;
 	}
  	@RequestMapping(value="/data_binding/{organId}",method=RequestMethod.POST)
  	@ResponseBody
 	public JsonResult doDataBinding(@PathVariable String organId,@RequestBody OrganData[] datas) {
  		
  		if(datas==null) return null;
		User loginUser=loginuser();
		for(OrganData data:datas) {
			if(StringUtils.isBlank(data.getId())) {
				data.setCreateUserId(loginUser.getId());
			}
		}
		organService.addOrganDatas(organId,datas);
 		return result(200, "OK");
 	}
  	
  	//====选择组织代码====
  	@RequestMapping(value="/select",method=RequestMethod.GET)
 	public String select() {
 		return "system/organ/organ-select";
 	}
  	/**
	 * 选择组织机构-按组织查询
	 * 去掉了人员类型的节点
	 */
  	@RequestMapping(value="/select/organ_tree",method=RequestMethod.POST)
  	@ResponseBody
	public List<TreeNode> organTree(@RequestParam(required=false) String id) {
		
		List<TreeNode> tree=getTreeNodeByParentId(id,Organ.TYPE_EMPLOYEE);
		if(StringUtils.isBlank(id)) {	//如果是顶级节点,那么继续加载下面的二级节点
			for(TreeNode node:tree) {
				String parentId=node.getId();
				List<TreeNode> branch=getTreeNodeByParentId(parentId,Organ.TYPE_EMPLOYEE);
				node.setState(TreeNode.STATE_OPEN);
				node.setChildren(branch);
			}
		}
		return tree;
	}
	/**
	 * 选择组织机构-按角色查询
	 */
  	@RequestMapping(value="/select/role_tree",method=RequestMethod.POST)
  	@ResponseBody
	public List<TreeNode> roleTree() {
		
		List<Role> roles=roleService.findAll();
    	List<TreeNode> tree=new ArrayList<TreeNode>(roles.size());

    	String guestRoleName=SystemConfig.getProperty("system.role.guest");
		for(Role r:roles) {
			if(r.getName().equals(guestRoleName)) continue;
			TreeNode node=new TreeNode();
			node.setId(r.getId());
			node.setText(r.getName());
			node.setChecked(false);
			node.setIconCls("icon-star");
			tree.add(node);
		}
		return tree;
	}
	/**
	 * 为选择组织窗口执行查询
	 */
  	@RequestMapping(value="/select/query",method=RequestMethod.POST)
	public void queryOrgan(HttpServletResponse response,@RequestParam(required=false) String queryType,@RequestParam(required=false) String id) {
  		
		if(StringUtils.isBlank(queryType) || queryType.equals("organ")) {
			DetachedCriteria criteria=getPaginator().getCriteria();
			if(!StringUtils.isBlank(param("name"))) {
				criteria.add(Restrictions.like("name", param("name"),MatchMode.ANYWHERE));
			}
			if(!StringUtils.isBlank(param("shortName"))) {
				criteria.add(Restrictions.like("shortName", param("shortName"),MatchMode.ANYWHERE));
			}
			if(!StringUtils.isBlank(id)) {
			    criteria.add(Restrictions.eq("parentId", id));
			}
			criteria.add(Restrictions.eq("status", Organ.STATUS_DEFAULT));
			criteria.addOrder(Order.desc("seqNum"));
			
			organService.find(getPaginator());
			printjson(response,getPaginator());
		} else if(queryType.equals("role")){
			List<Organ> organs = organService.getOrgansByRoleId(id);
			printjson(response,organs);
		}
	}
	
	/**
	 * 获取父节点下的子节点(TreeNode对象)
	 * @param parentId			父节点id
	 * @param excludeType		排除的节点类型
	 * @return
	 */
	private List<TreeNode> getTreeNodeByParentId(String parentId,int excludeType) {
		List<Organ> organs=organService.getChildren(parentId,null);
		List<TreeNode> tree=new ArrayList<TreeNode>(organs.size());
		
		for(Organ o:organs) {
			if(o.getType()==excludeType) continue;
			tree.add(convert(o));
		}
		return tree;
	}
  	
	//====工作组管理====
	@RequestMapping(value="/group",method=RequestMethod.GET)
   	public String group() {
   		return "system/organ/organ-group";
   	}
	
	@RequestMapping(value="/group/query",method=RequestMethod.POST)
   	public String queryGroup() {
		Paginator<OrganGroup> pagin=new Paginator<OrganGroup>(getPage(),getPageSize());
		pagin.buildCriteria(OrganGroup.class);
		DetachedCriteria criteria=pagin.getCriteria();
		criteria.addOrder(Order.asc("seqnum"));
		organService.findGroups(pagin);
   		return pagin(pagin);
   	}
	
	@RequestMapping(value="/group/delete/{groupid}",method=RequestMethod.POST)
	public @ResponseBody JsonResult deleteGroup(@PathVariable String groupid) {
		organService.deleteGroup(groupid);
		return result(200, "OK");
	}
	
	@RequestMapping(value="/group/add",method=RequestMethod.GET)
   	public String addGroup() {
   		return "system/organ/organ-group-update";
   	}
	
	@RequestMapping(value="/group/add",method=RequestMethod.POST)
	public @ResponseBody JsonResult addGroup(OrganGroup group,@RequestParam(name="organid[]") String[] organid) {
		organService.addGroup(group, organid);
		return result(200, "OK");
	}
	
	@RequestMapping(value="/group/update/{groupid}",method=RequestMethod.GET)
   	public String updateGroup(@PathVariable String groupid) {
		OrganGroup group=organService.getGroup(groupid);
		List<Organ> organs=organService.findOrganByGroupid(groupid);
		attr("group",group);
		attr("organs",organs);
   		return "system/organ/organ-group-update";
   	}
	
	@RequestMapping(value="/group/update/{groupid}",method=RequestMethod.POST)
	public @ResponseBody JsonResult updateGroup(@PathVariable String groupid,OrganGroup group,@RequestParam(name="organid[]") String[] organid) {
		organService.updateGroup(group, organid);
		return result(200, "OK");
	}
}
