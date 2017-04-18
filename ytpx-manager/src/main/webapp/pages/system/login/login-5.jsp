<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
	<meta name="renderer" content="webkit">
	<title>${contextTitle}</title>
	
	<link href="${skin}/contract/css/style.css" rel="stylesheet" type="text/css" />
	<!--[if !IE]><!--> <script type="text/javascript" src="${commonskin}/lib/jquery/jquery-2.1.4.min.js"></script> <!--<![endif]-->
	<!--[if IE 9]> <script type="text/javascript" src="${commonskin}/lib/jquery/jquery-2.1.4.min.js"></script> <![endif]-->
	<!--[if lt IE 9]>
		<script type="text/javascript" src="${commonskin}/lib/jquery/jquery-1.11.2.min.js"></script>
	    <script src="${commonskin}/lib/AdminLTE-2.3.0/js/html5shiv.min.js"></script>
	    <script src="${commonskin}/lib/AdminLTE-2.3.0/js/respond.min.js"></script>
	<![endif]-->
	<script type="text/javascript" src="${commonskin}/lib/jquery/jquery.cookie.js"></script>
	<script type="text/javascript" src="${commonskin}/lib/namespace.js"></script>
	<script type="text/javascript" src="${commonskin}/js/common.js"></script>
	<%@include file="/pages/include/jquery-easyui.jsp"%>
	<script type="text/javascript">var contextpath='${contextpath}';</script>
	<script src="${skin}/contract/js/cloud.js" type="text/javascript"></script>
	<script type="text/javascript" src="${skin}/login/5/login.js"></script>
	<script type="text/javascript" >
		$(function() {
		   /*  $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
			$(window).resize(function() {
			    $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
		    }); */
			$('.loginbox').css({'position':'absolute','right':0});
			$(window).resize(function() {
			    $('.loginbox').css({'position':'absolute','right':0});
		    }); 
		});
	</script>
	<style type="text/css">
		body {
			background-color:#1c77ac; 
			background-image:url('${skin}/contract/images/light.png'); 
			background-repeat:no-repeat; 
			background-position:center top; 
			overflow:hidden;
		},
			
	</style>
</head>
	<body style="">
	
	    <div id="mainBody">
	      <div id="cloud1" class="cloud"></div>
	      <div id="cloud2" class="cloud"></div>
	    </div>  
	
		<div class="logintop">    
		    <span>欢迎使用${domain}</span>    
		    <ul>
			    <li><a href="#">帮助</a></li>
			    <li><a href="#">关于</a></li>
		    </ul>    
		</div>
		<div class="loginbg" style="display: inline-block;"></div>
		<div class="loginbody" style="display: inline-block;">	 
			<c:if test="${!empty logoUrl }">
			<span class="systemlogo" style="background:url('http://${logoUrl}') no-repeat 100%;width:100%; height:71px; margin-top:75px;margin-left:-186px"></span> 
			</c:if>
			<c:if test="${empty logoUrl }">
			   <span class="systemlogo"></span> 
			</c:if>
		    <div class="loginbox">
		    	<form id="login-form" action="">
				    <ul>
					    <li><input name="username" type="text" class="loginuser" placeholder="用户名"/></li>
					    <li><input name="password" type="password" class="loginpwd" placeholder="密码"/></li>
					    <li style="margin-bottom: 10px;"><input name="" type="button" class="loginbtn" value="登录"  onclick="login();" />
					    <label><input id="rem" type="checkbox" />记住我</label>
					    <label><a href="javascript:void(0);" onclick="alert('忘记密码，请联系系统管理员！');">忘记密码？</a></label></li>
					    <li id="system-msg" style="color:red"></li>
				    </ul>
			    </form>
		    </div>
		</div>
	    <div class="loginbm">${systemCopyright}</div>
	</body>
</html>
