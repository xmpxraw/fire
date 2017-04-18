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
    				<td class="field">年份</td>
    				<td>
    					<w:data code="school.term.years" name="years" cssClass="field" options=""/>
    				</td>
    			</tr>		
    			
    		</tbody>
    	</table>
   	</form>
  </body>
</html>
