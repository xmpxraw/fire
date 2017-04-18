<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<%@include file="/pages/include/base-head.jsp"%>
		<%@include file="/pages/include/jquery-easyui.jsp"%>
	<%@include file="/pages/include/umeditor.jsp"%>
		 <script type="text/javascript" src="${commonskin}/lib/jquery/jquery.form.min.js"></script> 
	<script type="text/javascript" src="${skin}/js/school/school.js"></script>
	<script type="text/javascript">
		var disablestr='<i class="red">新建</i>';
	</script>
  </head>
  <body>
  	<div class="container">
	    <table id="datagrid" data-options="
   			url:'${contextpath}/school/school/query', 
   			toolbar:'#toolbar'">
			<thead>
				<tr>
					<!-- <th data-options="field:'district',width:50">地区</th> -->
					<th data-options="field:'province',width:50">省</th>
					<th data-options="field:'city',width:50">市</th>
					<th data-options="field:'district',width:50">区</th>
					<th data-options="field:'schoolCode',width:50">学校编码</th>
					<th data-options="field:'schoolName',width:100">学校名称</th>
					<th data-options="field:'schoolAddr',width:150">学校地址</th>
					<th data-options="field:'telephone',width:60">联系手机</th>
					 <th data-options="field:'createTime',width:80">创建时间</th>				
					<th data-options="field:'status',width:30,
					 		formatter:function(value,row,index) {
					 			if(value==2) return '上线';
					 			if(value==1) return '下线';
					 			if(value==0) return disablestr;
					 			return '';
					 		}">学校状态</th>
					<th data-options="field:'total',width:50">总学员人数</th>
					<th data-options="field:'currentTotal',width:50">当前学员数</th>
		       	
				</tr>
			</thead>
		</table>
  	</div>
  	
  	<div id="toolbar" style="padding:2px;">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="school.school.query();">查询</a>
		  <w:auth url="/school/school/add">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="school.school.add();">添加</a>
		</w:auth>
		  <w:auth url="/school/school/update/.*">
	    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true,group:'single'" onclick="school.school.update();">修改</a>
	     </w:auth>
	    <w:auth url="/school/school/delete/.*">
	    	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,group:'single'" onclick="school.school.remove();">删除</a>
	    </w:auth>
	     <w:auth url="/school/school/online/.*">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-right',plain:true,group:'single'" onclick="school.school.online();">上线</a>
		    </w:auth>
	   <w:auth url="/school/school/offline/.*">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-right',plain:true,group:'single'" onclick="school.school.offline();">下线</a>
		 </w:auth>
 
	</div>
  </body>
</html>
