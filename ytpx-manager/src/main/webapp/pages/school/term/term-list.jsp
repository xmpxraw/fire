<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<%@include file="/pages/include/base-head.jsp"%>
	<%@include file="/pages/include/jquery-easyui.jsp"%>
	<script type="text/javascript" src="${skin}/js/school/term.js"></script>
	<script type="text/javascript">
		var disablestr='<i class="red">新建</i>';
	</script>
  </head>
  <body>
  	<div class="container">
	    <table id="datagrid" data-options="
   			url:'${contextpath}/school/term/query', 
   			toolbar:'#toolbar'">
			<thead>
				<tr>
					<th data-options="field:'termCode',width:50">期数编码</th>
					<th data-options="field:'years',width:50">年份</th>
					<th data-options="field:'term',width:100">期数</th>
					<th data-options="field:'specialty',width:100">专业</th>
					<th data-options="field:'beginTime',width:80">开班日期</th>
					 <th data-options="field:'duration',width:80">学习时长</th>				
					 <th data-options="field:'classCount',width:80">班级数量</th>		
					<th data-options="field:'planCount',width:50">计划学员数</th>
					<th data-options="field:'registryCount',width:50">已绑定学员数</th>
		       		<th data-options="field:'createTime',width:100">创建时间</th>
				</tr>
			</thead>
		</table>
  	</div>
  	
  	<div id="toolbar" style="padding:2px;">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="school.term.query();">查询</a>
		 <w:auth url="/school/term/add">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="school.term.add();">添加</a>
		</w:auth>
		 <w:auth url="/school/term/update/.*">
	    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true,group:'single'" onclick="school.term.update();">修改</a>
	   	</w:auth>
	     <w:auth url="/school/term/delete/.*">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,group:'single'" onclick="school.term.remove();">删除</a>
 		</w:auth>
 		 <w:auth url="/school/term/change/.*">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,group:'single'" onclick="school.term.change();">转期</a>
			</w:auth> 
	</div>
  </body>
</html>
