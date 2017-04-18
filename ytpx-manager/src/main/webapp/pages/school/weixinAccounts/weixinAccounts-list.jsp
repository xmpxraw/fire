<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<%@include file="/pages/include/base-head.jsp"%>
	<%@include file="/pages/include/jquery-easyui.jsp"%>
	<script type="text/javascript" src="${skin}/js/school/weixinAccounts.js"></script>
  </head>
  <body>
  	<div class="container">
	    <table id="datagrid" data-options="
   			url:'${contextpath}/school/weixinAccounts/query', 
   			toolbar:'#toolbar'">
			<thead>
				<tr>
					<th data-options="field:'appId',width:50">appId</th>
					<th data-options="field:'secret',width:50">secret</th>
					<th data-options="field:'token',width:100">token</th>
					<th data-options="field:'systemId',width:50">systemId</th>
					<th data-options="field:'encodingAesType',width:100">encodingAesType</th>
					<th data-options="field:'encryptType',width:80">encryptType</th>
					 <th data-options="field:'accountType',width:80">accountType</th>				
					     	
				</tr>
			</thead>
		</table>
  	</div>
  	
  	<div id="toolbar" style="padding:2px;">
		 <w:auth url="/school/weixinAccounts/add">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="school.weixinAccounts.add();">添加</a>
		</w:auth>
		 <w:auth url="/school/weixinAccounts/update/.*">
	    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true,group:'single'" onclick="school.weixinAccounts.update();">修改</a>
	   	</w:auth>
	     <w:auth url="/school/weixinAccounts/delete/.*">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,group:'single'" onclick="school.weixinAccounts.remove();">删除</a>
 		</w:auth>
 	
	</div>
  </body>
</html>
