<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<%@include file="/pages/include/base-head.jsp"%>
  	<%@include file="/pages/include/jquery-easyui.jsp"%>
  </head>
  <body>
  	<table id="query-datagrid" class="easyui-datagrid" data-options="
   		url:'${contextpath}/system/organ/doquery',
   		toolbar:'#query-dialog-toolbar',
  		fit:true,
  		fitColumns:true,
  		border:false,
		rownumbers:true,
		singleSelect:true,
		autoRowHeight:true,
		pagination:true,
		pageList:[10,15,20,25,30],
		pageSize:15,
		onClickCell:function(rowIndex, field, value) {
			if(field=='dingwei') {
				$('#query-datagrid').datagrid('selectRow',rowIndex);
				system.organ.expandTreeNode();
			}
		},
		onDblClickRow:function() {
			system.organ.expandTreeNode();
		}">
		<thead>
			<tr>
				<th data-options="field:'id',hidden:true"></th>
			    <th data-options="field:'parentId',hidden:true"></th>
				<th data-options="field:'name',width:150">名称</th>
				<th data-options="field:'shortName',width:150">简称</th>
				<th data-options="field:'type',width:40,
						formatter:function(value,row,index) {
				 			if(value==1) return '企业';
				 			if(value==2) return '部门';
				 			if(value==4) return '人员';
				 			return '';
				 		}">类型</th>
				 <th data-options="field:'dingwei',width:40,
						formatter:function(value,row,index) {
				 			return '<a href=\'javascript:void(0);\'>定位</a>';
				 		}"></th>
			</tr>
		</thead>
	</table>
	<!-- 查询窗口的工具栏 -->
    <div id="query-dialog-toolbar">
    	<form id="query-form" class="dialog-data-form">
	    	<table cellspacing="4" style="width:100%;">
	    		<tbody>
	    			<tr>
	    				<td class="field"  style="width:100px;">名称</td>
	    				<td>
	    					<input name="name" type="text" class="field" />
	    				</td>
	    				<td class="field" style="width:100px;">简称</td>
	    				<td>
	    					<input name="shortName" type="text" class="field" />
	    				</td>
	    				<td rowspan="2">
	    					<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" onclick="system.organ.doQuery();">查询</a>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="field" >类型</td>
	    				<td>
	    					<w:data code="system.organ.type" name="type" cssClass="field" options=":全部"/>
	    				</td>
	    				<td class="field">主要职务</td>
						<td>
							<w:data code="system.organ.main" name="main" cssClass="field" options=":全部"/>
						</td>
	    			</tr>
	    		</tbody>
	    	</table>
    	</form>    
    </div>
    <script type="text/javascript">
    Namespace('system.organ',{
		doQuery:function(){
			var data=$('#query-form').form('jsonObject');
			$('#query-datagrid').datagrid('load',data);
		},
		expandTreeNode:function(){
			var row = $('#query-datagrid').datagrid('getSelected');
			if(row==null){
				top.showInfo('请选择需要查看的记录!');
				return;
			}
			parent.system.organ.expandTreeNode(row.id);
		},
	});
    </script>
  </body>
</html>
