<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<%@include file="/pages/include/base-head.jsp"%>
	<%@include file="/pages/include/jquery-easyui.jsp"%>
	<script type="text/javascript" src="${skin}/js/system/user-online.js"></script>
  </head>
  <body>
  	<div class="container">
	    <table id="datagrid" data-options="
   			url:'${contextpath}/system/online_user/query', 
   			toolbar:'#toolbar'">
			<thead>
				<tr>
					<th data-options="field:'username',width:150">用户名</th>
					<th data-options="field:'nickname',width:150">昵称</th>
					<th data-options="field:'ip',width:150">登录IP</th>
					<th data-options="field:'loginNum',width:80">登录次数</th>
					<th data-options="field:'loginTime',width:150">登录时间</th>
		            <th data-options="field:'remark',width:300">备注</th>
				</tr>
			</thead>
		</table>
  	</div>
  	
  	<div id="toolbar" style="padding:2px;">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,group:'single'" onclick="system.user.logout();">注销</a>
	</div>
  </body>
</html>
