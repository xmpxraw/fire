<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/pages/include/base-head.jsp"%>
<%@include file="/pages/include/jquery-easyui.jsp"%>
<script type="text/javascript" src="${skin}/js/study/directory.js"></script>
</head>
<body>
	<div id="toolbar" style="padding: 10px;">
		<a href="javascript:void(0);" class="easyui-linkbutton"
			data-options="iconCls:'icon-add',plain:true"
			onclick="study.directory.add();">添加</a> 
		<a href="javascript:void(0);" class="easyui-linkbutton"
			data-options="iconCls:'icon-edit',plain:true,group:'single'"
			onclick="study.directory.update();">修改</a> 
		<a href="javascript:void(0);" class="easyui-linkbutton"
			data-options="iconCls:'icon-edit',plain:true,group:'single'"
			onclick="study.directory.disabled();">禁用</a> 
		<a href="javascript:void(0);" class="easyui-linkbutton"
			data-options="iconCls:'icon-remove',plain:true,group:'single'"
			onclick="study.directory.enabled();">启用</a>
	</div>
	
	<div class="container">
		<table id="directory_tree_grid" class="easyui-treegrid" title="目录管理TreeGrid"
			style="width: 900px; height: 500px"
			data-options="
                rownumbers: true,
                animate: true,
                collapsible: true,
                fitColumns: true,
                url: 'treedata',
                method: 'post',
                idField: 'id',
                treeField: 'name',
                showFooter: true
            ">
			<thead>
				<tr>
					<th data-options="field:'code',width:80,editor:'text'">目录编码</th>
					<th data-options="field:'name',width:160,align:'left'">目录名称</th>
					<th data-options="field:'tag',width:160,editor:'datebox'">目录标签</th>
					<th data-options="field:'status',width:80,formatter:function(value,row,index) {
					 			if(value==1) return '启用';
					 			if(value==0) return '禁用';
					 			return '';
					 		}">状态</th>
					<th data-options="field:'significance',width:100">重要程度</th>
					<th data-options="field:'createTime',width:150">创建时间</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
</html>
