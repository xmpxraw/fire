<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<%@include file="/pages/include/base-head.jsp"%>
  </head>
  <body>
  	<table id="query-user-datagrid" class="easyui-datagrid" data-options="
    	url:'${contextpath}/system/organ/select_user', 
    	toolbar:'#query-user-dialog-toolbar',
  		fit:true,
  		fitColumns:true,
  		border:false,
		rownumbers:true,
		singleSelect:true,
		autoRowHeight:true,
		pagination:true,
		pageList:[10,15,20,25,30],
		pageSize:15,
    	onDblClickRow:function(){
    		system.organ.doSelectUser();
    	}">
		<thead>
			<tr>
				<th data-options="field:'id',hidden:true">编号</th>
				<th data-options="field:'username',width:150">用户名</th>
				<th data-options="field:'nickname',width:150">昵称</th>
				<th data-options="field:'status',width:60,
				 		formatter:function(value,row,index) {
				 			if(value==0) return '启用';
				 			if(value==1) return '锁定';
				 			return '';
				 		}">状态</th>
	             <th data-options="field:'remark',width:300">描述</th>
			</tr>
		</thead>
	</table>
	<!-- 查询用户窗口的工具栏 -->
	<div id="query-user-dialog-toolbar">     
    	<form id="query-user-form" class="dialog-data-form">
	    	<table cellspacing="4">
	    		<tbody>
	    			<tr>
	    				<td class="field">用户名</td>
	    				<td>
	    					<input name="username" type="text" class="field" />
	    				</td>
	    				<td class="field">昵称</td>
	    				<td>  
	    					<input name="nickname" type="text" class="field" />
	    				</td>
            			<td>
                			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" onclick="system.organ.queryUser();" title="查询用户"></a>
                			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-undo" onclick="$('#query-user-form')[0].reset();" title="重置"></a>
            			</td>
	    			</tr>
	    		</tbody>
	    	</table>
    	</form>    
    </div>
  </body>
</html>
