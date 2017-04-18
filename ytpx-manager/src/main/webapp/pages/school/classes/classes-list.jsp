<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<%@include file="/pages/include/base-head.jsp"%>
	<%@include file="/pages/include/jquery-easyui.jsp"%>
	<script type="text/javascript" src="${commonskin}/lib/jquery/jquery.form.min.js"></script> 
	<script type="text/javascript" src="${skin}/js/school/classes.js"></script>
	<script type="text/javascript">
		var disablestr='<i class="red">新建</i>';
	</script>
  </head>
  <body>
  	<div class="container">
	    <table id="datagrid" data-options="
   			url:'${contextpath}/school/classes/query', 
   			toolbar:'#toolbar'">
			<thead>
				<tr>
					<th data-options="field:'classCode',width:100">班号</th>
					<th data-options="field:'term',width:100">期数名称</th>
					<th data-options="field:'classType',width:60,formatter:function(value,row,index) {
					 			if(value==1) return '学习班';
					 			if(value==2) return  '突击班'
					 			return '';
					 		}">班级类型</th>
					<th data-options="field:'specialty',width:100">专业</th>
					<th data-options="field:'beginTime',width:80">开班时间</th>
					 <th data-options="field:'period',width:50">学习天数</th>				
					<th data-options="field:'planCount',width:50">分班学员数</th>
					<th data-options="field:'registryCount',width:50">已报班学员数</th>
					<th data-options="field:'passCount',width:50">通过人数</th>
					<th data-options="field:'passRate',width:50">通过率</th>
					<th data-options="field:'notifyLog',width:50">通知次数</th>
		       		<th data-options="field:'createTime',width:100">创建时间</th>
				</tr>
			</thead>
		</table>
  	</div>
  	
  	<div id="toolbar" style="padding:2px;">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="school.classes.query();">查询</a>
		 <w:auth url="/school/classes/add">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="school.classes.add();">添加</a>
	   </w:auth>
	   <%-- <w:auth url="/school/classes/update/.*">
	    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true,group:'single'" onclick="school.classes.update();">修改</a>
		</w:auth> --%>
		 <w:auth url="/school/classes/delete/.*">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,group:'single'" onclick="school.classes.remove();">删除</a>
		</w:auth>
		 <w:auth url="/school/classes/notify/.*">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,group:'single'" onclick="school.classes.notify();">通知</a>
		</w:auth>
		 <w:auth url="/school/student/viewStudent/.*">	
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,group:'single'" onclick="school.classes.viewStudent();">查看学员</a>
		</w:auth>
		 <w:auth url="/school/classes/importScore/.*">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,group:'single'" onclick="school.classes.importScore();">录入成绩</a>
		</w:auth>
		<w:auth url="/school/classes/exportTemplet/.*">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,group:'single'" onclick="school.classes.exportTemplet();">导出模版</a>
		</w:auth>
	</div>
  </body>
</html>
