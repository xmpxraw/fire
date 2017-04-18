<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<%@include file="/pages/include/base-head.jsp"%>
	<%@include file="/pages/include/jquery-easyui.jsp"%>
	<script type="text/javascript" src="${skin}/js/system/data.js"></script>
</head>
<body>
	<div class="container">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'west',title:'基础数据',split:true,tools:'#tree-toolbar'" style="width:250px;padding:5px;" oncontextmenu="return system.data.showRootMenu(event);">
				<ul id="tree" data-options="url:'${contextpath}/system/data/query'"></ul>
			</div>
			<div data-options="region:'center'">
				<div id="tabs" class="easyui-tabs" data-options="fit:true,border:false,plain:false"></div>
			</div>
		</div>
	</div>
	
	<!-- 树面板的工具栏 -->
	<div id="tree-toolbar">
		<a href="javascript:void(0);" class="icon-add" onclick="system.data.add();" title="添加数据" group="dir"></a>
		<a href="javascript:void(0);" class="icon-edit" onclick="system.data.update();" title="修改数据" group="leaf"></a>
		<a href="javascript:void(0);" class="icon-remove" onclick="system.data.remove();" title="删除数据" group="leaf"></a>
	</div>
	
	<!-- 在树节点上右键弹出的菜单 -->
	<div id="tree-menu" class="easyui-menu" style="width:120px;">
		<div onclick="system.data.add();" data-options="iconCls:'icon-add'" group="dir">添加</div>
		<div onclick="system.data.update();" data-options="iconCls:'icon-edit'">修改</div>
		<div onclick="system.data.remove();" data-options="iconCls:'icon-remove'">删除</div>
	</div>
	
	<!-- 在空白处右键弹出的菜单 -->
	<div id="root-menu" class="easyui-menu" style="width:120px;">
		<div onclick="system.data.addTop();" data-options="iconCls:'icon-add'">添加</div>
	</div>
</body>
</html>
