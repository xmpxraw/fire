<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<%@include file="/pages/include/base-head.jsp"%>
	<%@include file="/pages/include/jquery-easyui.jsp"%>
	<script type="text/javascript" src="${skin}/js/system/quartz.js"></script>
	<script type="text/javascript">
		var disablestr='<i class="red">停用</i>';
	</script>
  </head>
  <body>
  	<div class="container">
	    <table id="datagrid" data-options="
   			url:'${contextpath}/system/quartz/query',rownumbers:true,
   			toolbar:'#toolbar'">
			<thead>
				<tr>
					<th data-options="field:'jobName',width:180">任务名</th>
					<th data-options="field:'aliasName',width:180">任务别名</th>
					<th data-options="field:'jobGroup',width:160">任务分组</th>
					<th data-options="field:'jobClass',width:220">任务类Class</th>
					<th data-options="field:'status',width:60,
					 		formatter:function(value,row,index){
					 			if(value==1) return '启用';
					 			if(value==0) return disablestr;
					 			return '';}">状态</th>
					 <th data-options="field:'cronExpression',width:160">执行方式</th>
					 <th data-options="field:'description',width:160">描述</th>
					 <th data-options="field:'createTime',width:120">创建时间</th>
					 <th data-options="field:'updateTime',width:120">修改时间</th>
				</tr>
			</thead>
		</table>
  	</div>
  	
  	<div id="toolbar" style="padding:2px;">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="system.quartz.query();">查询</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="system.quartz.startJob();">启用</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-no',plain:true" onclick="system.quartz.stopJob();">停用</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true" onclick="system.quartz.runOnce();">执行一次</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="system.quartz.add();">添加</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true,group:'single'" onclick="system.quartz.update();">修改</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,group:'single'" onclick="system.quartz.remove();">删除</a>
	</div>
  </body>
</html>
