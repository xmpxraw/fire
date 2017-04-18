<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<%@include file="/pages/include/base-head.jsp"%>
  </head>
  <body>
  	<div class="float-left" style="width:250px;height:100%;">
    	<table id="sort-datagrid" class="easyui-datagrid" data-options="
   			url:'${contextpath}/basic/helptext/query?rows=10000',
   			fit:true,
   			fitColumns:true,
			rownumbers:true,
			singleSelect:true,
			striped:true">
			<thead>
				<tr>
					<th data-options="field:'name',width:250">标题</th>
				</tr>
			</thead>
		</table>
    </div>
    <div class="float-right" style="width:100px;margin-top:100px;">
    	<a class="easyui-linkbutton" onclick="basic.helptext.sortUp();">向上</a> 
    	<br /><br />
    	<a class="easyui-linkbutton" onclick="basic.helptext.sortDown();">向下</a> 
    </div>
    <div class="clear"></div>
  </body>
</html>
