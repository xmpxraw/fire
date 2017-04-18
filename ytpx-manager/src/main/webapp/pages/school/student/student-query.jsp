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
    				<td class="field">学员姓名</td>
    				<td>
    					<input name="studnetName" type="text" class="field"  style="width: 200px;"  value=""/>
    				</td>
    			</tr>
    			
    			<tr>
    				<td class="field">手机号码</td>
    				<td>
    					<input name="mobile" type="text" class="field"  style="width: 200px;"  value=""/>
    				</td>
    			</tr>
    			<tr>
    				<td class="field">身份证号</td>
    				<td>
    					<input name="idcard" type="text" class="field"  style="width: 200px;"  value=""/>
    				</td>
    			</tr>
    			<tr>
    				<td class="field">报名日期</td>
    				<td>
    					<!-- <input name="registTime" type="text" class="field" style="width: 200px;"  value=""/> -->
    					<input class="easyui-datebox" name="registTime"
  								  data-options="required:false" value="" style="width: 200px;" />
    				</td>
    			</tr>
    		<tr>
    				<td class="field">所在班级</td>
    				<td>
    					<div class="form-group">
	    							<select id="classCode" name="classCode" class="form-control" style="width: 200px;" >
				                        	<option ></option>
				                        <c:forEach var="c" items="${classes}">

				                        	<option value="${c.id}">${c.classCode}</option>

				                        </c:forEach>
				                    </select>
			       				</div> 
    				</td>
    			</tr>
    			<tr>
    				<td class="field">所在单位</td>
    				<td>
    				<div class="form-group">
	    							<select id="companyCode" name="companyCode" class="form-control" style="width: 200px;" >
										<option ></option>
				                        <c:forEach var="c" items="${companys}">			                      
				                        	<option value="${c.id}">${c.companyName}</option>
				                        </c:forEach>
				                    </select>
			       				</div> 
    				</td>
    			</tr>
    			
    			
    		</tbody>
    	</table>
   	</form>
  </body>
</html>
