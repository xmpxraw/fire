<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<%@include file="/pages/include/base-head.jsp"%>
	<%@include file="/pages/include/jquery-easyui.jsp"%>
	<script type="text/javascript" src="${skin}/js/system/logs.js"></script>
  </head>
  <body>
  	<div class="container">
	    <table id="datagrid" data-options="
   			url:'${contextpath}/system/logs/query',
   			toolbar:'#toolbar'">
			<thead>
				<tr>
					<th data-options="field:'username',width:100">用户名</th>
					<th data-options="field:'host',width:100">主机</th>
					<th data-options="field:'module',width:100">模块</th>
					<th data-options="field:'entity',width:120">实体</th>
					<th data-options="field:'type',width:100,
						formatter:function(value,row,index) {
				 			return types[value]?types[value].text:value;
					 	}">操作类型</th>
					<th data-options="field:'createTime',width:150">记录时间</th>
		            <th data-options="field:'remark',width:400">备注</th>
				</tr>
			</thead>
		</table>
  	</div>
  	<script type="text/javascript">
  		var types=<w:data code="system.logs.type" name="type" cssClass="field" output="json"/>;
  	</script>
  	<div id="toolbar" style="padding:2px;">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="system.logs.query();">查询</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-view',plain:true,group:'single'" onclick="system.logs.view();">查看</a>
	</div>
  </body>
</html>
