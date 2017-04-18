<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@include file="/pages/include/base-head.jsp"%>
	<%@include file="/pages/include/jquery-easyui.jsp"%>

	<link rel="stylesheet" type="text/css" href="${skin}/css/system/admin.css"/>
    <link rel="stylesheet" type="text/css" href="${skin}/css/system/admin-c.css"/>

	<script type="text/javascript" src="${skin}/js/system/admin.js"></script>
	<script type="text/javascript" src="${skin}/js/system/user-setup.js"></script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',border:false,split:false" class="header-bg">
			<div class="header" style="background:url('${skin}/contract/images/topbg.gif') repeat-x;">
				<div class="topleft">
				<c:if test="${!empty logoUrl }">	<a href="javascript:void(0);" target="_parent"><img src="http://${logoUrl}" title="系统首页" /></a></c:if>
			    <c:if test="${empty logoUrl }">	<a href="javascript:void(0);" target="_parent"><img src="${skin}/contract/images/logo.png" title="系统首页" /></a></c:if>
			    </div>

			    <ul class="nav">
			    	<li><a href="javascript:void(0);" class="selected" onclick="resetMenu('home','首页',this);"><h2><i class="icon-home none-bgi"></i> 首页</h2></a></li>
			    	<c:forEach var="m" items="${menus}" varStatus="state">
					    <li><a href="javascript:void(0);" onclick="resetMenu('${m.id}','${m.name}',this);"><h2><i class="${m.icon} none-bgi"></i> ${m.name}</h2></a></li>
						<c:if test="${state.count==1}">
							<script type="text/javascript">
								$(function() {
									resetMenu('${m.id}','${m.name}',this);
								});
							</script>
						</c:if>
					</c:forEach>
				<!-- 	<li><a href="javascript:void(0);" onclick="resetMenu('tools','常用工具',this);"><h2><i class="icon-wrench none-bgi"></i>  常用工具</h2></a></li> -->
				<!-- 	<li><a href="javascript:void(0);" onclick="resetMenu('chart','统计图表',this);"><h2><i class="icon-pie-chart none-bgi"></i>  统计图表</h2></a></li> -->
			    </ul>

			    <div class="topright">
				    <ul>
					   <%--  <li><span><img src="${skin}/contract/images/help.png" class="helpimg"/></span><a href="javascript:void(0);" onclick="addTab('','帮助手册','${contextpath}/basic/helptext/homelist');">帮助</a></li> --%>
					    <%-- <li><a href="javascript:void(0);" onclick="addTab('','关于我们','${contextpath}/basic/changelog/aboutme');">关于</a></li> --%>
					    <li><a href="${contextpath}/logout">退出</a></li>
				    </ul>
				    <div class="user">
					    <span><a class="userinfo" href="javascript:void(0);" onclick="system.user.setup();">${loginUser.nickname}</a></span>
					    <!-- <i>消息</i>
					    <b>5</b> -->
				    </div>
			    </div>
			</div>
		</div>
		<div data-options="region:'west',split:false" style="width:200px;">
			<div id="leftmenu" class="easyui-accordion" data-options="fit:true,border:false" style="background-color:#d7ebfa;">
				<div class="menubox" data-options="title:'docker管理',iconCls:'icon-menu-title'">
					<ul id="menu-tree"></ul>
				</div>
			</div>
		</div>
		<div data-options="region:'center'">
			<div id="tabpanel" class="easyui-tabs" data-options="fit:true,border:false,plain:false,onContextMenu:tabpanelContextMenu"></div>
		</div>
	</div>

	<!-- 在tabpanel头右键弹出的菜单 -->
	<div id="tabpanel-menu" class="easyui-menu" style="width:120px;display:none;">
		<div onclick="closeTab('close');" data-options="iconCls:'icon-cancel'">关闭</div>
		<div onclick="closeTab('other');">关闭其它</div>
		<div onclick="closeTab('all');">关闭全部</div>
		<div onclick="closeTab('left');">关闭左边</div>
		<div onclick="closeTab('right');">关闭右边</div>
	</div>
</body>
</html>
