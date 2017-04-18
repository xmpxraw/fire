<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<%@include file="/pages/include/base-head.jsp"%>
  </head>
  <body>
  	<div class="easyui-tabs"
  			data-options="border:false,fit:true,plain:true,
					onSelect:function(title) {
						system.user.selectTab(title,'#add-role-tree');
					}">
		<div title="基本资料" style="padding:10px">
			<form id="add-form" class="dialog-data-form">
		    	<table class="data-table" cellspacing="4">
		    		<tbody>
		    			<tr>
		    				<td class="field">用户名</td>
		    				<td>
		    					<input name="username" type="text" class="field easyui-validatebox" maxlength="16"
		    						data-options="required:true,validType:'fn[system.user.checkUser]',invalidMessage:'用户名已存在！'"/>
		    				</td>
		    				<td class="field">昵称</td>
		    				<td>
		    					<input name="nickname" type="text" class="field easyui-validatebox" maxlength="16"/>
		    				</td>
		    			</tr>
		    			<tr>
		    				<td class="field">密码</td>
		    				<td >
		    					<input id="password" name="password" type="password" class="field easyui-validatebox" maxlength="16"
		    						data-options="required:true,validType:'regexp[\'^\\\\w{6,16}$\']',invalidMessage:'密码必须是6-16个字母、数字、下划线！'"/>
		    				</td>
		    				<td class="field">确认密码</td>
		    				<td >
		    					<input type="password" class="field easyui-validatebox" maxlength="16"
		    						data-options="required:true,validType:'equals[\'#password\']',invalidMessage:'两次输入密码不一致！'"/>
		    				</td>
		    			</tr>
		    			<tr>
		    				<td class="field">选择学校</td>
		    				<td colspan="3">
		    				<div class="form-group">
	    							<select id="sysCode" name="sysCode" class="form-control" style="width: 100%"  >
	    							<!-- 	<option></option> -->
				                        <c:forEach var="c" items="${school}">
				                        	<option value="${c.sysCode}">${c.schoolName}</option>
				                        </c:forEach>
				                    </select>
				           <%--          <input id="sysCode" type="hidden" name="sysCode" value="${school.sysCode}"/> --%>
			       				</div> 
		    				</td>
		    			</tr>
		    			<tr>
		    				<td class="field">状态</td>
		    				<td>
		    					<w:data code="system.user.status" name="status" cssClass="field" selectedValue="0"/>
		    				</td>
		    				<td class="field">手机</td>
		    				<td>
		    					<input name="mobile" type="text" class="field easyui-validatebox " maxlength="16"/>
		    				</td>
		    			</tr>
		    			<tr>
							<td class="field">备注</td>
							<td colspan="3">
								<textarea class="field-remark easyui-validatebox" name="remark" 
									data-options="validType:'length[0,120]',invalidMessage:'不能超过120个字符！'"></textarea>
							</td>
						</tr>
		    		</tbody>
		    	</table>
		   	</form>
		</div>
		<div title="分配角色" style="padding:10px">
			<form class="dialog-data-form">
				<ul id="add-role-tree" data-options="url:'${contextpath}/system/user/role_tree'"></ul>
			</form>
		</div>
	</div>
  </body>
</html>
