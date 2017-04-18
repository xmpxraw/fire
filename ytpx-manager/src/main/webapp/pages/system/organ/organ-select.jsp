<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<%@include file="/pages/include/base-head.jsp"%>
	<%@include file="/pages/include/jquery-easyui.jsp"%>
</head>
<body>
	<div id="win-dialog" class="easyui-dialog"
		data-options="title:'',fit:true,closed:false,cache:false,modal:false,border:false">
		
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'west',split:true" style="width:350px;">
				<div class="easyui-tabs" data-options="border:false,fit:true,tabPosition:'left'">
					<div data-options="title:'按组织查找'" style="pading:10px;">					
						<ul id="organ-tree" class="easyui-tree"></ul>
					</div>
					<div data-options="title:'按角色查找'" style="pading:10px;">					
						<ul id="role-tree" class="easyui-tree"></ul>
					</div>
				</div>
			</div>
			<div data-options="region:'east',title:'已选择列表',split:true,tools:'#candidate-toolbar'" style="width:200px;">
				<table id="candidate-datagrid" class="easyui-datagrid" 
					data-options="fit:true,fitColumns:true,border:false,rownumbers:true">
					<thead>
						<tr>
							<th data-options="field:'id',hidden:true"></th>
							<th data-options="field:'name',width:125">名称</th>
							<th data-options="field:'type',width:40,
								formatter:function(value,row,index) {
				 					if(value=='enterprice') return '企业';
				 					if(value=='dept') return '部门';
				 					if(value=='emp') return '人员';
				 					if(value=='role') return '角色';
				 					return '';
				 				}">类型</th>
						</tr>
					</thead>
				</table>
			</div>
			<div data-options="region:'center',title:'人员列表'">
				<table id="datagrid" data-options="
					   fit:true,fitColumns:true,border:false,rownumbers:true,
					   pagination:true,pageList:[10,15,20,25,30],pageSize:15,
					   toolbar:'#query-toolbar'">
					<thead>
						<tr>
							<th data-options="field:'id',checkbox:true"></th>
							<th data-options="field:'name',width:180">名称</th>
							<th data-options="field:'shortName',width:120">简称</th>
							<th data-options="field:'type',width:50,
								formatter:function(value,row,index) {
				 					if(value==1) return '企业';
				 					if(value==2) return '部门';
				 					if(value==4) return '人员';
				 					return '';
				 				}">类型</th>
						</tr>
					</thead>
				</table>
			</div>
			
		</div><!-- layout end -->
	</div><!-- dialog end -->

	<div id="query-toolbar">
		<form id="query-form" class="dialog-data-form">
			<table cellspacing="4" style="width:100%;">
				<tbody>
					<tr>
						<td class="field">名称</td>
						<td>
							<input name="name" type="text" class="field-short" />
						</td>
						<td class="field">简称</td>
						<td>
							<input name="shortName" type="text" class="field-short" />
						</td>
						<td>
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" onclick="system.organ.select.query('organ','');"></a>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
			
	<!-- 已选择列表的工具栏 -->
	<div id="candidate-toolbar">
		<a href="javascript:void(0);" class="icon-remove" onclick="system.organ.select.removeCandidate();" title="删除" group="single"></a>
	</div>
	
	<script type="text/javascript" src="${skin}/js/system/organ-select.js"></script>
	<script type="text/javascript">
	$(function() {
		system.organ.select.init(window.dialogArguments);
	});
	</script>
</body>
</html>
