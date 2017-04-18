<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<%@include file="/pages/include/base-head.jsp"%>
	<%@include file="/pages/include/jquery-easyui.jsp"%>
	<script type="text/javascript" src="${skin}/js/school/student.js"></script>
	<script type="text/javascript">
		var disablestr='<i class="red">未缴费</i>';
	</script>
  </head>
  <body>
  	<div class="container">
	    <table id="datagrid" data-options="
   			url:'${contextpath}/school/student/query/${id}', 
   			toolbar:'#toolbar'">
			<thead>
				<tr>
					<th data-options="field:'studnetName',width:60">学员姓名</th>
					<th data-options="field:'className',width:60">所在班级</th>
					<th data-options="field:'studentNo',width:60">本期学号</th>
					<th data-options="field:'idcard',width:100">身份证号</th>
					<th data-options="field:'mobile',width:100">手机号码</th>
					<th data-options="field:'registTime',width:100">报名日期</th>
					<th data-options="field:'companyName',width:100">所在单位</th>
					<th data-options="field:'weixinOpenid',width:100">微信OpenId</th>
					<th data-options="field:'registType',width:100">报名方式</th>			
					<th data-options="field:'payStatus',width:60,
					 		formatter:function(value,row,index) {
					 			if(value==1) return '已缴费';
					 			if(value==0) return disablestr;
					 		}">缴费状态</th>
					 		<th data-options="field:'studentStatus',width:60,
					 		formatter:function(value,row,index) {
					 		if(value=='6') return '国考不合格';
					 		if(value=='5') return '国考合格';
					 		if(value=='4') return '关闭';
					 		if(value=='3') return '待复考';
					 		if(value=='2') return '通过考试';
					 			if(value=='1') return '学习中';
					 			if(value=='0') return '已报名';

					 		}">学员状态</th>
					<th data-options="field:'payType',width:60">缴费方式</th>	
					<th data-options="field:'payShould',width:60">应缴费用</th>
					<th data-options="field:'payReal',width:60">实缴费用</th>
		       		<th data-options="field:'createTime',width:100">创建时间</th>
				</tr>
			</thead>
		</table>
  	</div>
  	
  	<div id="toolbar" style="padding:2px;">
		<!-- <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="school.student.query();">查询</a> -->
	</div>
  </body>
</html>
