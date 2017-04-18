<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<%@include file="/pages/include/base-head.jsp"%>
  	<%@include file="/pages/include/jquery-easyui.jsp"%>
	<script type="text/javascript" src="${skin}/js/system/organ-group.js"></script>
  	<script type="text/javascript" src="${skin}/js/system/organ-select.js"></script>
  </head>
  <body>
  	<table id="datagrid" data-options="
  		url:'${contextpath}/system/organ/group/query',
  		toolbar:'#group-toolbar'">
		<thead>
			<tr>
				<th data-options="field:'code',width:30">code</th>
			    <th data-options="field:'name',width:30">工作组名称</th>
	            <th data-options="field:'remark',width:70">备注</th>
			</tr>
		</thead>
	</table>
	<div id="group-toolbar" style="padding:2px;">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="system.organ.group.add();">添加工作组</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true,group:'single'" onclick="system.organ.group.update();">修改工作组</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,group:'single'" onclick="system.organ.group.remove();">删除工作组</a>
	</div>
  </body>
</html>
