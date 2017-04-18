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
					system.role.selectTab(title,'#function-tree');
				}">
		<div title="基本资料" style="padding:10px">
	  		<form id="data-form" class="dialog-data-form">
		    	<table class="data-table" cellspacing="4">
		    		<tbody>
		    			<tr>
		    				<td class="field">角色名</td>
		    				<td>
		    					<c:if test="${empty role}">
			    					<input name="name" type="text" class="easyui-validatebox field" maxlength="16" value="${role.name}"
			    						data-options="required:true,validType:'fn[system.role.checkRole]',invalidMessage:'角色名已存在！'"/>
		    					</c:if>
		    					<c:if test="${!empty role}">
			    					<input name="name" type="text" class="field" maxlength="16" value="${role.name}" readonly="readonly"/>
		    					</c:if>
		    				</td>
		    			</tr>
		    			 <tr>
		    				<td class="field">角色类型</td>
		    				<td>
		    					<w:data code="system.role.type" name="type" cssClass="field" selectedValue="${role.type}"/>
		    				</td>
		    			</tr> 
		    			<tr>
		    				<td class="field">状态</td>
		    				<td>
		    					<w:data code="system.role.status" name="status" cssClass="field" selectedValue="${role.status}"/>
		    				</td>
		    			</tr>
		    			<tr>
		    				<td class="field">描述</td>
		    				<td>
		    					<textarea name="remark" class="field-remark easyui-validatebox" data-options="validType:'length[0,255]'">${role.remark}</textarea>
		    				</td>
		    			</tr>
		    		</tbody>
		    	</table>
	    	</form>
	    </div>
		<div title="分配功能" style="padding:10px">
			<ul id="function-tree" data-options="url:'${contextpath}/system/role/func_tree?roleId=${role.id}'"></ul>
		</div>
	</div>
  </body>
</html>
