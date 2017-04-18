<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@include file="/pages/include/base-head.jsp"%>
	<link href="${skin}/contract/css/style.css" rel="stylesheet" type="text/css" />
</head>
<body>
    
    <div class="formbody">
 
    
    <div class="formtitle"><span>系统设置</span></div>
    <div class="toolsli">
	    <ul class="toollist">
		    <li><a href="#" onclick="top.system.user.setup();"><img src="${skin}/contract/images/setting.png" /></a><h2>个人设置</h2></li>
		    <li><a href="#" onclick="top.addTab('','在线用户','${contextpath}/system/online_user');"><img src="${skin}/contract/images/i07.png" /></a><h2>在线用户</h2></li>
		    <li><a href="#" onclick="top.addTab('','组织机构','${contextpath}/system/organ');"><img src="${skin}/contract/images/organisation.png" /></a><h2>组织机构</h2></li>
		    <li><a href="#" onclick="top.addTab('','用户管理','${contextpath}/system/user');"><img src="${skin}/contract/images/users_64x.png" /></a><h2>用户管理</h2></li>
		    <li><a href="#" onclick="top.addTab('','角色管理','${contextpath}/system/role');"><img src="${skin}/contract/images/role.png" /></a><h2>角色管理</h2></li>
		    <li><a href="#" onclick="top.addTab('','操作日志','${contextpath}/system/logs');"><img src="${skin}/contract/images/i06.png" /></a><h2>操作日志</h2></li>
		    <li><a href="#" onclick="top.addTab('','系统监控','${contextpath}/monitoring');"><img src="${skin}/contract/images/system_monitor.png" /></a><h2>系统监控</h2></li>
		    <li><a href="#" onclick="top.addTab('','数据库监控','${contextpath}/system/druid/');"><img src="${skin}/contract/images/database.png" /></a><h2>数据库监控</h2></li>
	    </ul>
	    <%-- <span class="tooladd"><img src="${skin}/contract/images/add.png" title="添加" /></span>   --%>
    </div>
    
    </div>
</body>
</html>
