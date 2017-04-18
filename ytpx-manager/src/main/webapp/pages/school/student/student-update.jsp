<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<%@include file="/pages/include/base-head.jsp"%>
  </head>
  <body>
  	<div class="easyui-tabs" 
  		data-options="border:false,fit:true,plain:true">
		<div title="学员信息" style="padding:10px">
	  		<form id="update-form" class="dialog-data-form">
		    	<table class="data-table" cellspacing="4">
		    		<tbody>
		    			<tr>
		    				<td class="field">学生姓名</td>
		    				<td>
		    					<input name="studnetName" type="text" class="field easyui-validatebox"  style="width: 190px" maxlength="18" value="${student.studnetName}"/>
		    				</td>
		    			
		    			</tr>
		    			<tr>	  
		    				<td class="field">身份证号</td>
		    				<td>
		    					<input name="idcard" type="text" class="field easyui-validatebox" maxlength="18" style="width: 190px" value="${student.idcard}"/>
		    				</td>
		    			</tr>
		    			<tr>	  
		    				<td class="field">手机号码</td>
		    				<td>
		    					<input name="mobile" type="text" class="field easyui-validatebox easyui-numberbox" style="width: 190px" maxlength="11" value="${student.mobile}"/>
		    				</td>
		    			</tr>	
		    			
		    			<tr>	  
		    				<td class="field">OPenId</td>
		    				<td>
		    					<input name="weixinOpenid" type="text" class="field easyui-validatebox" style="width: 190px" value="${student.weixinOpenid}"/>
		    				</td>
		    			</tr>
		  
		    		</tbody>
		    	</table>
	    	</form>
   		</div>
	</div>
  </body>
</html>
