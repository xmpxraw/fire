<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<%@include file="/pages/include/base-head.jsp"%>
  </head>
  <body>
  	<div class="easyui-tabs" 
  		data-options="border:false,fit:true,plain:true">
		<div title="转期信息" style="padding:10px">
	  		<form id="change-form" class="dialog-data-form">
		    	<table class="data-table" cellspacing="4">
		    		<tbody>
		    		<tr >	    			
		    				<td class="field">学生姓名</td>
		    				<td>
		    					<input name="studnetName" type="text" class="field" value="${student.studnetName}" style="width: 200px;" readonly="readonly"/>
		    				</td>	    				
		    			</tr>
		    				<tr >	    			
		    				<td class="field">原来期数</td>
		    				<td>
		    					<input name="termName" type="text" class="field" value="${student.termName}" style="width: 200px;" readonly="readonly"/>
		    				</td>	    				
		    			</tr>	    			
		    			<tr >	    			
		    				<td class="field">转期</td>
		    				<td>
		    					<div class="form-group">
	    							<select id="termCode" name="termCode" class="form-control" style="width: 200px;" >
				                        <c:forEach var="c" items="${terms}">
				                     <%--    <c:if test="${c.termCode!=student.termCode}"> --%>
				                        	<option value="${c.termCode}">${c.term}</option>
				                     <%--    	</c:if> --%>
				                        </c:forEach>
				                    </select>
			       				</div> 
		    				</td>	    				
		    			</tr>			
		    		</tbody>
		    	</table>
	    	</form>
   		</div>
	</div>
  </body>
</html>
