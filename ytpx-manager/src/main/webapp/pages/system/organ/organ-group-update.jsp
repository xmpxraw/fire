<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<%@include file="/pages/include/base-head.jsp"%>
  </head>
  <body>
  	<form id="data-form" class="dialog-data-form">
    	<table>
    		<tbody>
    			<tr>
    				<td class="field">code</td>
    				<td>
    					<input type="hidden" name="id" value="${group.id}"/>
    					<input name="code" type="text" class="field-title easyui-validatebox" data-options="required:true" value="${group.code}"/>
    				</td>
    			</tr>
    			<tr>
    				<td class="field">工作组名称</td>
    				<td>
    					<input name="name" type="text" class="field-title easyui-validatebox" data-options="required:true" value="${group.name}"/>
    				</td>
    			</tr>
    			<tr>
    				<td class="field-long">备注</td>
    				<td>
    					<textarea name="remark" class="field-remark easyui-validatebox" data-options="validType:'length[0,255]'">${group.remark}</textarea>
    				</td>
    			</tr>
    			<tr>
    				<td class="field-long">人员</td>
    				<td>
    					<select name="organid" multiple="multiple" class="easyui-validatebox" data-options="required:true" style="width:300px;height:120px">
    						<c:forEach var="organ" items="${organs}">
    							<option value="${organ.id}">${organ.name}</option>
    						</c:forEach>
    					</select>
						<a class="easyui-linkbutton" data-options="iconCls:'icon-users',plain:true" onclick="system.organ.group.addOrgans();">添加人员</a>
						<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="system.organ.group.delOrgans();">删除人员</a>
    				</td>
    			</tr>
    		</tbody>
    	</table>
   	</form>
  </body>
</html>
