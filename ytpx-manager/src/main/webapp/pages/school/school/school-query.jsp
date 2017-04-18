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
    				<td class="field">学校名称</td>
    				<td>
    					<input name="schoolName" type="text"   style="width: 100%"  class="field" value=""/>
    				</td>
    			</tr>
    			<tr>
    				<td class="field">地区</td>
    				<td>
    					<%-- <w:data code="school.school.district" name="district" cssClass="field" options=":全部"/> --%>
    					<select class="select" name="province" id="a1">
									<option></option>
							</select> <select class="select" name="city" id="a2">
									<option></option>
							</select> <select class="select" name="district" id="a3">
									<option></option>
							</select>
    				</td>
    			</tr>		
    		</tbody>
    	</table>
   	</form>
   		<script type="text/javascript" src="${commonskin}/lib/jQuery.cxSelect-1.4.0/company_query.js"></script>
   	<script type="text/javascript">

 $(function(){  
	  onload=setup();preselect('广东省');promptinfo();
	}); 
  function promptinfo()
  {
  }
</script>
  </body>
</html>
