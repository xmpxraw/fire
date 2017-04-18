<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@include file="/pages/include/base-head.jsp"%>
	<%@include file="/pages/include/jquery-easyui.jsp"%>
	<script type="text/javascript" src="${commonskin}/lib/jquery/jquery.cookie.js"></script>
	<link rel="stylesheet" type="text/css" href="${skin}/login/3/login.css" />
	<script type="text/javascript" src="${skin}/login/3/login.js"></script>
</head>
<body>
<div class='signup_container'>

    <h1 class='signup_title'>用户登陆</h1>
    <img src='${skin}/login/3/image/people.png' id='admin'/>
    <div id="signup_forms" class="signup_forms clearfix">
            <form class="signup_form_form" id="login-form" method="post" action="">
                    <div class="form_row first_row">
                        <label for="signup_email">请输入用户名</label>
                        <input type="text" name="username" placeholder="请输入用户名" id="signup_name" class="easyui-validatebox"
							 tabindex="1" maxlength="16" data-options="required:true,missingMessage:'请输入用户名！'"/>
                    </div>
                    <div class="form_row">
                        <label for="signup_password">请输入密码</label>
                        <input type="password" name="password" placeholder="请输入密码" id="signup_password" class="easyui-validatebox"
							 tabindex="2" maxlength="16" data-options="required:true,missingMessage:'请输入密码！'"/>
                    </div>
                    <%-- <div class="form_row">
                            <input type="text" name="user[password]" placeholder="" id="signup_select" value='' data-required="required">
                            <img src='${skin}/login/3/image/d.png' id='d'/>
                            <ul>
                                <li>管理员</li>
                                <li>用户1</li>
                                <li>用户2</li>
                            </ul>
                    </div> --%>
           </form>
    </div>

    <div class="login-btn-set"><div class='rem'>记住我</div> <a href='javascript:void(0)' class='login-btn' onclick='login();'></a></div>
    <div id="system-msg" class="error-msg-tip" style="display: none;"></div>
    <p class='copyright'>${systemCopyright}</p>
</div>
</body>
</html>