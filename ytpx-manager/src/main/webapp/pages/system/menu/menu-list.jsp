<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<%@include file="/pages/include/base-head.jsp"%>
	<%@include file="/pages/include/jquery-easyui.jsp"%>
	<script type="text/javascript" src="${skin}/js/system/menu.js"></script>
</head>
<body>
	<div class="container">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'west',title:'菜单',split:true,tools:'#tree-toolbar'" style="width:250px;padding:5px;" oncontextmenu="return system.menu.showRootMenu(event);">
				<ul id="tree" data-options="url:'${contextpath}/system/menu/query'"></ul>
			</div>
			<div data-options="region:'center'">
				<div id="tabs" class="easyui-tabs" data-options="fit:true,border:false,plain:false"></div>
			</div>
		</div>
	</div>
	
	<!-- 树面板的工具栏 -->
	<div id="tree-toolbar">
		<a href="javascript:void(0);" class="icon-add" onclick="system.menu.add();" title="添加菜单" group="dir"></a>
		<a href="javascript:void(0);" class="icon-edit" onclick="system.menu.update();" title="修改菜单" group="leaf"></a>
		<a href="javascript:void(0);" class="icon-remove" onclick="system.menu.remove();" title="删除菜单" group="leaf"></a>
		<a href="javascript:void(0);" class="icon-reload" onclick="system.menu.refreshCache();" title="刷新菜单缓存"></a>
	</div>
	
	<!-- 在树节点上右键弹出的菜单 -->
	<div id="tree-menu" class="easyui-menu" style="width:120px;">
		<div onclick="system.menu.add();" data-options="iconCls:'icon-add'" group="dir">添加</div>
		<div onclick="system.menu.update();" data-options="iconCls:'icon-edit'">修改</div>
		<div onclick="system.menu.remove();" data-options="iconCls:'icon-remove'">删除</div>
	</div>
	
	<!-- 在空白处右键弹出的菜单 -->
	<div id="root-menu" class="easyui-menu" style="width:120px;">
		<div onclick="system.menu.addTop();" data-options="iconCls:'icon-add'">添加一级菜单</div>
		<div onclick="system.menu.refresh();" data-options="iconCls:'icon-reload'">刷新菜单缓存</div>
	</div>
</body>
</html>
