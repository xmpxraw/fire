<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<%@include file="/pages/include/base-head.jsp"%>
  	<%@include file="/pages/include/jquery-easyui.jsp"%>
	<script type="text/javascript" src="${skin}/js/system/organ-data.js"></script>
  </head>
  <body>
  	<table id="organ-data-datagrid" class="easyui-datagrid" data-options="
  		url:'${contextpath}/system/organ/data_binding/${param.organId}',
  		method:'get',
  		toolbar:'#data-toolbar',
  		fit:true,
   		fitColumns:true,
  		border:false,
		rownumbers:true,
		singleSelect:true,
		autoRowHeight:true,
		pagination:false">
		<thead>
			<tr>
				<th data-options="field:'id',hidden:true">编号</th>
			    <th data-options="field:'organId',hidden:true">机构编号</th>
				<th data-options="field:'code',width:100,editor:'text'">数据标识</th>
				<th data-options="field:'value',width:100,editor:'text'">数据值</th>
	            <th data-options="field:'remark',width:150,editor:'text'">备注</th>
			</tr>
		</thead>
	</table>
	<div id="data-toolbar" style="padding:2px;">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="system.organ.addDataRow();">添加数据</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true,group:'single'" onclick="system.organ.updateDataRow();">修改数据</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,group:'single'" onclick="system.organ.removeDataRow();">删除数据</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true,group:'single'" onclick="system.organ.save('${param.organId}');">保存数据</a>
	</div>
  </body>
</html>
