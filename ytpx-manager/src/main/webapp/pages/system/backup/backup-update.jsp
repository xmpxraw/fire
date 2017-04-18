<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<%@include file="/pages/include/base-head.jsp"%>
  </head>
  <body>
 	  <form id="data-form" class="dialog-data-form">
    	<table class="data-table" cellspacing="4">
    		<tbody>
    			<tr>
    				<td class="field">备份名</td>
    				<td>
    					<input name="name" type="text" class="easyui-validatebox field" maxlength="25" data-options="required:true" value="${backup.name}"/>
    				</td>
    			</tr>
    			<tr>
    				<td class="field">备份时间</td>
    				<td>
    					<fmt:formatDate var="createTime" value="${backup.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
    					${createTime}
    				</td>
    			</tr>
    			<tr>
    				<td class="field">备份文件</td>
    				<td>
    					${backup.sqlPath}
    				</td>
    			</tr>
    			<tr>
    				<td class="field">描述</td>
    				<td>
    					<textarea name="remark" class="field-remark easyui-validatebox" data-options="validType:'length[0,255]'">${backup.remark}</textarea>
    				</td>
    			</tr>
    		</tbody>
    	</table>
   	</form>
  </body>
</html>
