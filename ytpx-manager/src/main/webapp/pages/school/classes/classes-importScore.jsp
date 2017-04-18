<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<%@include file="/pages/include/base-head.jsp"%>
  </head>
  <body>
  	<div class="easyui-tabs"
  			data-options="border:false,fit:true,plain:true">
		<div title="导入成绩" style="padding:10px">
			<form id="add-form" class="dialog-data-form" method="post" enctype="multipart/form-data">
		    	<table class="data-table" style="width: 98%;">
		    		<tbody>
		    			<tr >	    			

		    				<td>
		    					<input name="id" type="hidden"  style="width: 90%" value="${classes.id}"  class="field easyui-validatebox" maxlength="16" />
		    				</td>
		    				
		    			</tr>	    			
		    			<tr>
		    			<td class="field">班级</td>
		    				<td>
		    					<input name="classCode" type="text" data-options="required:true" style="width: 193px"   value="${classes.classCode}"  readonly="readonly" class="field easyui-validatebox" maxlength="16"/>
		    				</td>    				
		    			</tr>
		    			<tr>
		    			<td class="field">导入成绩</td>
		    				<td>
		    				<input name="file" type="file" class="field-title easyui-validatebox" data-options="required:true" style="width: 193px" />
		    				</td>    				
		    			</tr>				 			
		    		</tbody>
		    	</table>
		   	</form>
		</div>
	</div>
  </body>
</html>
