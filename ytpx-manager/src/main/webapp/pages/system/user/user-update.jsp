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
				system.user.selectTab(title,'#update-role-tree');
			}">
		<div title="基本资料" style="padding:10px">
	  		<form id="update-form" class="dialog-data-form">
		    	<table class="data-table" cellspacing="4">
		    		<tbody>
		    			<tr>
		    				<td class="field">用户名</td>
		    				<td>
		    					<input name="username" type="text" class="field" readonly="readonly" maxlength="16" value="${user.username}"/>
		    				</td>
		    				<td class="field">昵称</td>
		    				<td>
		    					<input name="nickname" type="text" class="field easyui-validatebox" maxlength="16" value="${user.nickname}"/>
		    				</td>
		    			</tr>
		    			<tr>
		    			<td class="field">选择学校</td>
		    			<td colspan="3">
		    				<div class="form-group">
	    							<select id="sysCode" name="sysCode" class="form-control" style="width: 100%"  >
	    								<option value="${school.sysCode}" selected="selected">${school.schoolName}</option>
				                        <c:forEach var="c" items="${schools}">
				                        <c:if test="${c.sysCode!=school.sysCode}">
				                        	<option value="${c.sysCode}">${c.schoolName}</option>
				                        </c:if>
				                        </c:forEach>
				                    </select>
				           <%--   <input id="sysCode" type="hidden" name="sysCode" value="${user.status}"/> --%>
			       				</div> 
		    				</td>
		    			</tr>
		    			<tr>
		    				<td class="field">状态</td>
		    				<td>
		    					<w:data code="system.user.status" name="status" cssClass="field" selectedValue="${user.status}"/>
		    				</td>
		    				<td class="field">手机</td>
		    				<td>
		    					<input name="mobile" type="text" class="field easyui-validatebox" maxlength="16" value="${user.mobile}"/>
		    				</td>
		    			</tr>
		    			<tr>
							<td class="field">备注</td>
							<td colspan="3">
								<textarea class="field-remark easyui-validatebox" name="remark" 
									data-options="validType:'length[0,120]',invalidMessage:'不能超过120个字符！'">${user.remark}</textarea>
							</td>
						</tr>
		    		</tbody>
		    	</table>
	    	</form>
   		</div>
		<div title="分配角色" style="padding:10px">
			<form class="dialog-data-form">
				<ul id="update-role-tree" data-options="url:'${contextpath}/system/user/role_tree?userId=${user.id}'"></ul>
			</form>
		</div>
	</div>
  </body>
</html>
