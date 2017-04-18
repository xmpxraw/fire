<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<%@include file="/pages/include/base-head.jsp"%>
  </head>
  <body>
  	<form id="query-form" class="dialog-data-form">
    	<table cellspacing="4">
    		<tbody>
    			<tr>
    				<td class="field">用户名</td>
    				<td>
    					<input name="username" type="text" class="field" value=""/>
    				</td>
    			</tr>
    			<tr>
    				<td class="field">昵称</td>
    				<td>
    					<input name="nickname" type="text" class="field" value=""/>
    				</td>
    			</tr>
    			<tr>
    				<td class="field">状态</td>
    				<td>
    					<w:data code="system.user.status" name="status" cssClass="field" options=":全部"/>
    				</td>
    			</tr>
    		</tbody>
    	</table>
   	</form>
  </body>
</html>
