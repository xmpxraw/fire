<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<%@include file="/pages/include/base-head.jsp"%>
  </head>
  <body>
  	<div class="easyui-tabs"
  			data-options="border:false,fit:true,plain:true">
		<div title="期数信息" style="padding:10px">
			<form id="add-form" class="dialog-data-form">
		    	<table class="data-table" style="width: 98%;">
		    		<tbody>
		    			<tr >	    			
		    				<td class="field">年份</td>
		    				<td>
		    					<w:data code="school.term.years" name="years" cssClass="field" selectedValue=""/>
		    				</td>
		    				
		    			</tr>
		    			<tr>
		    			<td class="field">期数</td>
		    				<td>
		    					<input name="term" type="text"   data-options="required:true,validType:'fn[school.term.checkTerm]',invalidMessage:'期数已存在！'" class="field easyui-validatebox" maxlength="20"/>
		    				</td>
		    			</tr>
		    			<tr >	    			
		    				<td class="field">专业</td>
		    				<td>
		    					<w:data code="school.term.specialty" name="specialty" cssClass="field" selectedValue=""/>
		    				</td>
		    				
		    			</tr>
		    			<tr>
		    				<td class="field">开班日期</td>
		    				<td>
		    					<!-- <input name="beginTime" type="text"  class="field easyui-validatebox" maxlength="16"/> -->
		    					<input class="easyui-datebox" name="beginTime"
  								  data-options="required:true" value="" style="width:150px"/>
		    				</td>		
		    			</tr>
		    			<tr>
		    			<td class="field">学习时长</td>
		    				<td>
		    					<input name="duration" type="text"  data-options="required:true"    style="width: 150px;" class="field easyui-validatebox easyui-numberbox"  maxlength="16"/>:(天)
		    				</td>	    				
		    			</tr>
		    			<tr>
		    			<td class="field">计划学员数</td>
		    				<td>
		    					<input name="planCount" type="text"   data-options="required:true"  style="width: 150px;"  validtype="number" class="field easyui-validatebox easyui-numberbox" maxlength="16"/>:(人)
		    				</td>
		    			</tr>				
		    		</tbody>
		    	</table>
		   	</form>
		</div>
	</div>
  </body>
</html>
