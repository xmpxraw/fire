<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<%@include file="/pages/include/base-head.jsp"%>
	<%@include file="/pages/include/jquery-easyui.jsp"%>
	<%@include file="/pages/include/umeditor.jsp"%>
	<script type="text/javascript" src="${skin}/js/basic/feedback.js"></script>
	<script type="text/javascript">
		function resolvestatusfmt(value) {
			var statusmap=<w:data code="basic.feedback.resolvestatus" output="json" />;
			return statusmap[value]?statusmap[value].text:'';
		}
	</script>
  </head>
  <body>
  	<div class="container">
	    <table id="treegrid" data-options="
   			url:'${contextpath}/basic/feedback/query',
   			idField:'id',
		    treeField:'name',
   			toolbar:'#toolbar'">
			<thead>
				<tr>
					<th data-options="field:'name',width:200">标题</th>
					<th data-options="field:'resolvestatus',width:100,formatter:function(value) {
						return resolvestatusfmt(value);
					}">解决状态</th>
		            <th data-options="field:'username',width:100">反馈人</th>
		            <th data-options="field:'createtime',width:100">反馈时间</th>
		            <th data-options="field:'status',width:80,formatter:function(value) {
		            	return value==0?'未公开':'公开';
		            }">状态</th>
		            <th data-options="field:'visitcount',width:80">访问次数</th>
				</tr>
			</thead>
		</table>
  	</div>
  	
  	<div id="toolbar" style="padding:2px;">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="basic.feedback.query();">查询</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="basic.feedback.add();">添加</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true,group:'single'" onclick="basic.feedback.update();">修改</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,group:'single'" onclick="basic.feedback.remove();">删除</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-sort',plain:true" onclick="basic.feedback.sort();">排序</a>
	</div>
  </body>
</html>
