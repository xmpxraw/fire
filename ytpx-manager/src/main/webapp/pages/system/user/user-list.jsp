<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<%@include file="/pages/include/base-head.jsp"%>
	<%@include file="/pages/include/jquery-easyui.jsp"%>
	<script type="text/javascript" src="${skin}/js/system/user.js"></script>
	<script type="text/javascript">
		var disablestr='<i class="red">锁定</i>';
	</script>
  </head>
  <body>
  	<div class="container">
	    <table id="datagrid" data-options="
   			url:'${contextpath}/system/user/query', 
   			toolbar:'#toolbar'">
			<thead>
				<tr>
					<th data-options="field:'username',width:150">用户名</th>
					<th data-options="field:'nickname',width:150">昵称</th>
					<th data-options="field:'status',width:60,
					 		formatter:function(value,row,index) {
					 			if(value==0) return '启用';
					 			if(value==1) return disablestr;
					 			return '';
					 		}">状态</th>
					 <th data-options="field:'loginNum',width:80">登录次数</th>
					 <th data-options="field:'loginTime',width:150">登录时间</th>
		             <th data-options="field:'remark',width:300">备注</th>
		             	<th data-options="field:'sysCode',width:150">所在学校</th>
		            <!-- <th data-options="field:'sysCode',width:80,
					 		formatter:function(value,row,index) {
					 			if(value==null\\value=='') return '';
					 			if(value!=null&&value!='') return disablestr;
					 			return '';
					 		}">所在学校</th> -->
		             
		            
				</tr>
			</thead>
		</table>
  	</div>
  	
  	<div id="toolbar" style="padding:2px;">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="system.user.query();">查询</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="system.user.add();">添加</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true,group:'single'" onclick="system.user.update();">修改</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true,group:'single'" onclick="system.user.updatePassword();">设置密码</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,group:'single'" onclick="system.user.remove();">删除</a>
		<w:auth code="system.user.loginall">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-login',plain:true,group:'single'" onclick="system.user.login();">登入</a>
		</w:auth>
	</div>
  </body>
</html>
