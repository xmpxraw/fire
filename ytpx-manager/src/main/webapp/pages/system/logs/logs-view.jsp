<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<%@include file="/pages/include/base-head.jsp"%>
  </head>
  <body>
  	<form id="view-form" class="dialog-data-form">
    	<table class="data-table" cellspacing="4">
    		<tbody>
    			<tr>
    				<td class="field">用户名</td>
    				<td>
    					<input name="username" type="text" class="field" value="${logs.username}"/>
    				</td>
    			</tr>
    			<tr>
    				<td class="field">主机</td>
    				<td>
    					<input name="host" type="text" class="field" value="${logs.host}"/>
    				</td>
    			</tr>
    			<tr>
    				<td class="field">模块</td>
    				<td>
    					<input name="module" type="text" class="field" value="${logs.module}"/>
    				</td>
    			</tr>
    			<tr>
    				<td class="field">实体</td>
    				<td>
    					<input name="entity" type="text" class="field" value="${logs.entity}"/>
    				</td>
    			</tr>
    			<tr>
    				<td class="field">操作类型</td>
    				<td>
    					<input name="type" type="text" class="field" value="${logs.type}"/>
    				</td>
    			</tr>
    			<tr>
    				<td class="field">记录时间</td>
    				<td>
    					<fmt:formatDate var="createTime" value="${logs.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
    					<input name="createTime" type="text" class="field" value="${createTime}"/>
    				</td>
    			</tr>
    			<tr>
    				<td class="field">备注</td>
    				<td>
    					<textarea name="remark" class="field-remark" style="height:100px;">${logs.remark}</textarea>
    				</td>
    			</tr>
    		</tbody>
    	</table>
   	</form>
   	<script type="text/javascript">
   		$('#view-form').form('readonly');
   	</script>
  </body>
</html>
