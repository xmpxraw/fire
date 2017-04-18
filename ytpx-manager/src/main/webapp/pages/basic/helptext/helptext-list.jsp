<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<%@include file="/pages/include/base-head.jsp"%>
	<%@include file="/pages/include/jquery-easyui.jsp"%>
	<%@include file="/pages/include/umeditor.jsp"%>
	<script type="text/javascript" src="${skin}/js/basic/helptext.js"></script>
  </head>
  <body>
  	<div class="container">
	    <table id="datagrid" data-options="
   			url:'${contextpath}/basic/helptext/query',
   			toolbar:'#toolbar'">
			<thead>
				<tr>
					<th data-options="field:'name',width:200">标题</th>
					<th data-options="field:'username',width:100">发布人</th>
		            <th data-options="field:'createtime',width:100">发布时间</th>
		            <th data-options="field:'status',width:80,formatter:function(value) {
		            	return value==0?'未公开':(value==1?'公开':'放到首页')
		            }">状态</th>
		            <th data-options="field:'visitcount',width:80">访问次数</th>
				</tr>
			</thead>
		</table>
  	</div>
  	
  	<div id="toolbar" style="padding:2px;">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="basic.helptext.query();">查询</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="basic.helptext.add();">添加</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true,group:'single'" onclick="basic.helptext.update();">修改</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,group:'single'" onclick="basic.helptext.remove();">删除</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-sort',plain:true" onclick="basic.helptext.sort();">排序</a>
	</div>
  </body>
</html>
