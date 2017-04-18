<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@include file="/pages/include/base-head.jsp"%>
	<%@include file="/pages/include/jquery-easyui.jsp"%>
	<link rel="stylesheet" type="text/css" href="${skin}/login/4/login.css" />
	<script type="text/javascript" src="${skin}/login/4/login.js"></script>
</head>
<body>
	<div class="box">
	
		<div class="login-box">
			<form id="login-form" method="post">
				<table class="login-table" border="0" cellspacing="0" cellpadding="0">
					<tbody>
						<tr>
							<td class="login-label" style="width:70px;">用户名：</td>
							<td style="width:130px;">
								<input class="login-input" type="text" name="username" tabindex="1" maxlength="16" placeholder="请输入用户名"/>
							</td>
							<td class="login-label" style="width:70px;">密　码：</td>
							<td style="width:130px;">
								<input class="login-input" type="password" name="password" tabindex="2" maxlength="16" placeholder="请输入密码"/>
							</td>
							<td>
								&nbsp;&nbsp;<input id="login-btn" src="${skin}/login/4/image/btn_login.png" type="button" onclick="login();return false;" value="　登　录　"/>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<div id="system-msg" class="error-msg-tip" style="display: none;"></div>
		<div class="footer">${systemCopyright}</div>
	</div>
</body>
</html>
