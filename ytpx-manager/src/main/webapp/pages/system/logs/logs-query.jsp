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
    					<input name="username" type="text" class="field" value="" maxlength="25"/>
    				</td>
    			</tr>
    			<tr>
    				<td class="field">主机</td>
    				<td>
    					<input name="host" type="text" class="field" value="" maxlength="25"/>
    				</td>
    			</tr>
    			<tr>
    				<td class="field">模块</td>
    				<td>
    					<input name="module" type="text" class="field" value="" maxlength="25"/>
    				</td>
    			</tr>
    			<tr>
    				<td class="field">实体</td>
    				<td>
    					<input name="entity" type="text" class="field" value="" maxlength="25"/>
    				</td>
    			</tr>
    			<tr>
    				<td class="field">操作类型</td>
    				<td>
    					<w:data id="logtype" code="system.logs.type" name="type" cssClass="field" output="combobox" options=":全部"/>
    				</td>
    			</tr>
    			<tr>
    				<td class="field">记录时间</td>
    				<td>
    					从<input name="fromDate" type="text" class="easyui-datebox" style="width:100px;" data-options="required:false,validType:'date'"/>
    					到<input name="toDate" type="text" class="easyui-datebox" style="width:100px;" data-options="required:false,validType:'date'"/>
    				</td>
    			</tr>
    		</tbody>
    	</table>
   	</form>
  </body>
</html>
