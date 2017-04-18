<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>HTTP API测试接口</title>
	<script type="text/javascript" src="${commonskin}/lib/jquery/jquery-2.1.4.min.js"></script>
</head>
<body>
	<h2>HTTP接口测试页面</h2>
	<hr />
	<form id="data-form" action="">
		<table>
			<tr>
				<td>URL</td>
				<td>
					<input type="text" name="server" style="width:200px;" 
						value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}"/>
					<input type="text" name="url" style="width:500px;" value=""/>
				</td>
			</tr>
			<tr>
				<td>Method</td>
				<td>
					<select name="m" style="width:200px;" >
						<option value="GET">GET</option>
						<option value="POST">POST</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>Content-Type</td>
				<td>
					<select name="contentType" style="width:200px;" >
						<option value="application/json; charset=UTF-8">application/json</option>
						<option value="application/x-www-form-urlencoded; charset=UTF-8">application/x-www-form-urlencoded</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>Data</td>
				<td>
					<textarea name="data" rows="10" cols="100"></textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;">
					<input type="button" value="提交" onclick="send();"/>
				</td>
			</tr>
			<tr>
				<td>Response</td>
				<td>
					<textarea name="response" rows="15" cols="100"></textarea>
				</td>
			</tr>
		</table>
	</form>
	
	<script type="text/javascript">
		function send() {
			var form=$('#data-form')[0];
			var url=form.server.value+form.url.value;
			
			try {
				form.response.value='';
				if(form.m.value=='GET') {
					$.get(url,form.data.value,function(data) {
						form.response.value=data;
					},'text')
				} else { 
					$.ajax({
						type : "POST",
						url : url,
						dataType : 'text',
						data : form.data.value,
					    contentType : form.contentType.value, //设置请求头信息
						success : function(data) {
							form.response.value=data;
						},
						error : function(req, textStatus, e) {
							form.response.value=e;
						}
					});
				}
			} catch(e) {
				form.response.value=e;
			}
		}
	</script>	
</body>
</html>
