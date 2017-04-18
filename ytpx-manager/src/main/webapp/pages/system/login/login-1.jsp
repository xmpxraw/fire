<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@include file="/pages/include/base-head.jsp"%>
	<%@include file="/pages/include/jquery-easyui.jsp"%>
	<link rel="stylesheet" type="text/css" href="${skin}/login/1/login.css" />
	<script type="text/javascript" src="${skin}/login/1/login.js"></script>
</head>
<body>
	<div class="box">
	
		<div class="header">
			<img id="logo" src="${skin}/login/1/image/login-logo.png" width="460" height="40"/>
		</div>
		
		<div class="login-box">
			<form id="login-form" method="post">
				<table class="login-table" border="0" cellspacing="0" cellpadding="0">
					<tbody>
						<tr>
							<td class="login-label">用户名：</td>
							<td>
								<input class="username easyui-validatebox" type="text" name="username" tabindex="1" maxlength="16"
									data-options="required:true,missingMessage:'请输入用户名！'"/>
							</td>
						</tr>
						<tr>
							<td class="login-label">密&nbsp;&nbsp;&nbsp;&nbsp;码：</td>
							<td align="left">
								<input class="password easyui-validatebox" type="password" name="password" tabindex="2" maxlength="16"
									data-options="required:true,missingMessage:'请输入密码！'"/>
							</td>
						</tr>
						<tr>
							<td class="login-label"></td>
							<td align="left" valign="bottom" style="height:60px;">
								<input id="login-btn" src="${skin}/login/1/image/btn_login.png" type="image" onclick="login();return false;"/>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div id="system-msg" class="error-msg-tip" style="display: none;"></div>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		
		<div class="footer">${systemCopyright}</div>
	</div>
</body>
</html>
