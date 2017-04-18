<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<%@include file="/pages/include/base-head.jsp"%>
	<%@include file="/pages/include/jquery-easyui.jsp"%>
	<script type="text/javascript" src="${skin}/js/system/organ.js"></script>
</head>
<body>
	<div class="container">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'west',title:'组织机构',split:true,tools:'#tree-toolbar'" style="width:250px;padding:5px;" oncontextmenu="return system.organ.showRootMenu(event);">
				<ul id="tree" data-options="url:'${contextpath}/system/organ/query'"></ul>
			</div>
			<div data-options="region:'center'">
				<div id="tabs" class="easyui-tabs" data-options="fit:true,border:false,plain:false"></div>
			</div>
		</div>
	</div>
	
	<!-- 树面板的工具栏 -->
	<div id="tree-toolbar">
		<a href="javascript:void(0);" class="icon-search" onclick="system.organ.query();" title="查询组织"></a>
		<a href="javascript:void(0);" class="icon-add" onclick="system.organ.add();" title="添加组织" group="dir"></a>
		<a href="javascript:void(0);" class="icon-edit" onclick="system.organ.update();" title="修改组织" group="leaf"></a>
		<a href="javascript:void(0);" class="icon-remove" onclick="system.organ.remove();" title="删除组织" group="leaf"></a>
		<a href="javascript:void(0);" class="icon-sort" onclick="system.organ.sort();" title="同级排序" group="leaf"></a>
		<a href="javascript:void(0);" class="icon-group" onclick="system.organ.group();" title="工作组管理"></a>
	</div>
	
	<!-- 组织机构树节点的菜单 -->
	<div id="tree-menu" class="easyui-menu" style="width:120px;">
		<div onclick="system.organ.add();" data-options="iconCls:'icon-add'" group="dir">添加</div>
		<div onclick="system.organ.update();" data-options="iconCls:'icon-edit'">修改</div>
		<div onclick="system.organ.remove();" data-options="iconCls:'icon-remove'">删除</div>
		<div onclick="system.organ.sort();" data-options="iconCls:'icon-sort'">同级排序</div> 
		<div onclick="system.organ.dataBinding();" data-options="iconCls:'icon-edit'">数据绑定</div>
		<div onclick="system.organ.functionSetup();" data-options="iconCls:'icon-func'">权限设置</div>
	</div>
	
	<!-- 组织机构树非节点的菜单 -->
	<div id="root-menu" class="easyui-menu" style="width:120px;">
		<div onclick="system.organ.query();" data-options="iconCls:'icon-search'">查询</div>
		<div onclick="system.organ.addTop();" data-options="iconCls:'icon-add'">添加</div>
	</div>
</body>
</html>
