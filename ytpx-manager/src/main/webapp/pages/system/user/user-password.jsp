<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<%@include file="/pages/include/base-head.jsp"%>
  </head>
  <body>
  	<form id="password-form" class="dialog-data-form">
    	<table class="data-table" cellspacing="4">
    		<tbody>
    			<tr>
    				<td class="field">用户名</td>
    				<td>
    					<input name="username" type="text" class="field" readonly="readonly" maxlength="16" value="${user.username}" />
    				</td>
    			</tr>
    			<tr>
    				<td class="field">密码</td>
    				<td>
    					<input id="update-password" name="password" type="password" class="field easyui-validatebox" maxlength="16"
    						data-options="required:true,validType:'regexp[\'^\\\\w{6,16}$\']',invalidMessage:'密码必须是6-16个字母、数字、下划线！'"/>
    				</td>
    			</tr>
    			<tr>
    				<td class="field">确认密码</td>
    				<td>
    					<input type="password" class="field easyui-validatebox" maxlength="16"
    						data-options="required:true,validType:'equals[\'#update-password\']',invalidMessage:'两次输入密码不一致！'"/>
    				</td>
    			</tr>
    		</tbody>
    	</table>
   	</form>
  </body>
</html>
