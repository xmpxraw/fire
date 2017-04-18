<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<%@include file="/pages/include/base-head.jsp"%>
  </head>
  <body>
	<div title="基本资料" style="padding:10px">
		<form id="add-form" class="dialog-data-form">
	    	<table class="data-table" cellspacing="2">
	    		<tbody>
	    			<tr>
	    				<td class="field">任务名</td>
	    				<td>
	    					<input name="jobName" type="text" class="field easyui-validatebox" maxlength="64" data-options="required:true"/>
	    				</td>
	    				<td class="field">任务别名</td>
	    				<td>
	    					<input name="aliasName" type="text" class="field easyui-validatebox" maxlength="64" />
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="field">任务分组</td>
	    				<td>
	    					<input name="jobGroup" type="text" class="field easyui-validatebox" maxlength="64" data-options="required:true"/>
	    				</td>
	    				<td class="field">执行方式</td>
	    				<td>
	    					<input name="cronExpression" type="text" class="field easyui-validatebox" data-options="required:true" maxlength="64"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="field">任务Class</td>
	    				<td colspan="3">
	    					<input name="jobClass" style="width: 374px;" type="text" class="field easyui-validatebox" maxlength="128" data-options="required:true"/>
	    				</td>
	    			</tr>
	    			<tr>
						<td class="field">描述</td>
						<td colspan="3">
							<textarea class="field-remark easyui-validatebox" name="description" style="width: 374px;"
								data-options="validType:'length[0,256]',invalidMessage:'不能超过256个字符！'"></textarea>
						</td>
					</tr>
	    		</tbody>
	    	</table>
	   	</form>
	</div>
  </body>
</html>
