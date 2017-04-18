<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<%@include file="/pages/include/base-head.jsp"%>
  </head>
  <body>
  	<div id="setup-tabs" class="easyui-tabs" data-options="border:false,fit:true,tabPosition:'left'">
		<div style="padding:10px" data-options="id:'setup-basic',title:'基本资料'">
			<form id="basic-form" class="dialog-data-form">
		    	<table class="data-table" cellspacing="4">
		    		<tbody>
		    			<tr>
		    				<td class="field">用户名</td>
		    				<td>
		    					<input name="username" type="text" class="field-short" readonly="readonly" maxlength="16" value="${loginUser.username}"/>
		    				</td>
		    				<td class="field">昵称</td>
		    				<td>
		    					<input name="nickname" type="text" class="field-short easyui-validatebox" maxlength="16" value="${loginUser.nickname}"/>
		    				</td>
		    			</tr>
		    			<tr>
		    				<td class="field">状态</td>
		    				<td>
		    					<select name="status" class="field-short" disabled="disabled">
		    						<option value="0" ${loginUser.status==0?'selected':''}>默认</option>
		    						<option value="1" ${loginUser.status==1?'selected':''}>锁定</option>
		    					</select>
		    				</td>
		    				<td class="field">手机</td>
		    				<td>
		    					<input name="mobile" type="text" class="field-short easyui-validatebox" maxlength="16" value="${loginUser.mobile}"/>
		    				</td>
		    			</tr>
		    			<tr>
							<td class="field">备注</td>
							<td colspan="3">
								<textarea class="field-remark easyui-validatebox" name="remark" 
									data-options="validType:'length[0,255]',invalidMessage:'不能超过255个字符！'">${loginUser.remark}</textarea>
							</td>
						</tr>
		    		</tbody>
		    	</table>
		   	</form>
		</div>
		<div style="padding:10px" data-options="id:'setup-password',title:'修改密码'">
			<form id="password-form" class="dialog-data-form">
		    	<table class="data-table" cellspacing="4">
		    		<tbody>
		    			<tr>
		    				<td class="field">原密码</td>
		    				<td colspan="3">
		    					<input name="oldpwd" type="password" class="field easyui-validatebox" 
		    						data-options="required:true,validType:'regexp[\'^\\\\w{6,16}$\']',invalidMessage:'密码必须是6-16个字母、数字、下划线！'" maxlength="16"/>
		    				</td>
		    			</tr>
		    			<tr>
		    				<td class="field">新密码</td>
		    				<td colspan="3">
		    					<input id="newpwd" name="newpwd" type="password" class="field easyui-validatebox" 
		    						data-options="required:true,validType:'regexp[\'^\\\\w{6,16}$\']',invalidMessage:'密码必须是6-16个字母、数字、下划线！'" maxlength="16"/>
		    				</td>
		    			</tr>
		    			<tr>
		    				<td class="field">确认密码</td>
		    				<td colspan="3">
		    					<input type="password" class="field easyui-validatebox" 
		    						data-options="required:true,validType:'equals[\'#newpwd\']',invalidMessage:'两次输入密码不一致！'" maxlength="16"/>
		    				</td>
		    			</tr>
		    		</tbody>
		    	</table>
		   	</form>
		</div>
		<%-- <div data-options="id:'setup-main-emp',title:'身份切换'">
		    <table id="emp-datagrid" class="easyui-datagrid" data-options="
				url:'${contextpath}/system/user_setup/emps',
				fit:true,
				border:false,
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:true,
				onLoadSuccess:function(data) {
					for(var i in data.rows) {
						if(data.rows[i].main==1) {
							$('#emp-datagrid').datagrid('selectRow',i);
							break;
						}
					}
				}">
				<thead>
					<tr>
						<th data-options="field:'id',checkbox:true"></th>
						<th data-options="field:'name',width:300,
								formatter:function(value,row,index) {
									return '<a href=\'javascript:void(0);\' title=\''+value+'\' style=\'color:black;\'>'+value+'</a>';
								}">员工</th>
             			<th data-options="field:'main',width:60,
			 		            formatter:function(value,row,index) {
			 						if(value==0) return '否';
			 						if(value==1) return '是';
			 					return '';
			 			}">主要职务</th>
					</tr>
				</thead>
			</table>
		</div> --%>
	</div>
  </body>
</html>
