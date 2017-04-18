<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
	<%@include file="/pages/include/bootstrap.jsp"%>
</head>
<body style="background-color: #ecf0f5;">
  <div class="content-header">
     <h1>
       合同管理系统帮助手册
       <small>版本 1.0.0</small>
     </h1>
   </div>
  <div class="content">
	  	<div>
		  <h2 class="page-header" style="border-bottom: 1px solid #d2d6de;"><a href="javascript:void(0);">目录</a></h2>
		  <p class="lead">
		  	  <c:forEach var="help" items="${helplist}" varStatus="status">
				  <div><a href="#${help.id}">${status.count}.${help.name}</a></div>
			  </c:forEach>
		  </p>
		</div>
	  <br/>
	  <c:forEach var="help" items="${helplist}" varStatus="status">
	  	<div id="${help.id}">
		  <h2 class="page-header" style="border-bottom: 1px solid #d2d6de;"><a href="javascript:void(0);">${status.count}.${help.name}</a></h2>
		  <p class="lead">
		    ${help.content}
		  </p>
		</div>
	  </c:forEach>
  </div>
  <div class="main-footer no-print" style="margin-left: 0px;">
   <div class="pull-right hidden-xs">
     <b>Version</b> 1.0
   </div>
   <strong>Copyright &copy; 2016-2017 合同管理系统</strong> 版权所有&nbsp;&nbsp;服务电话：13631414080
 </div>
<%@include file="/pages/include/bootstrap-js.jsp"%>
</body>
</html>
