<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<%@include file="/pages/include/base-head.jsp"%>
	<%@include file="/pages/include/jquery-easyui.jsp"%>
	<script type="text/javascript" src="${skin}/js/school/student.js"></script>
	<script type="text/javascript">
		var disablestr='<i class="red">未缴费</i>';
		function formatPrice(val,row){
			val = val+"".replace(/\,/g, "");
			if(isNaN(val+"") || val == "")return "";
			val = Math.round(val * 100) / 100;
			    if (val < 0)
			        return '-' + outputdollars(Math.floor(Math.abs(val) - 0) + '') + outputcents(Math.abs(val) - 0);
			    else
			        return outputdollars(Math.floor(val - 0) + '') + outputcents(val - 0);
			} 
			//格式化金额
			function outputdollars(val) {
			    if (val.length <= 3)
			        return (val == '' ? '0' : val);
			    else {
			        var mod = val.length % 3;
			        var output = (mod == 0 ? '' : (val.substring(0, mod)));
			        for (i = 0; i < Math.floor(val.length / 3); i++) {
			            if ((mod == 0) && (i == 0))
			                output += val.substring(mod + 3 * i, mod + 3 * i + 3);
			            else
			                output += ',' + val.substring(mod + 3 * i, mod + 3 * i + 3);
			        }
			        return (output);
			    }
			}
			function outputcents(amount) {
			    amount = Math.round(((amount) - Math.floor(amount)) * 100);
			    return (amount < 10 ? '.0' + amount : '.' + amount);
			}
	</script>
  </head>
  <body>
  	<div class="container">
	    <table id="datagrid" data-options="
   			url:'${contextpath}/school/student/query', 
   			toolbar:'#toolbar'">
			<thead>
				<tr>
					<th data-options="field:'studnetName',width:60">学员姓名</th>
					<th data-options="field:'classCode',width:60">所在班级</th>
					<th data-options="field:'termCode',width:60">所在期数</th>
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
					 		}">报名状态</th>
					 		<th data-options="field:'studentStatus',width:60,
					 		formatter:function(value,row,index) {
					 		if(value=='9') return '已退学';
					 		if(value=='8') return '已领证';
					 		if(value=='6') return '国考不合格';
					 		if(value=='5') return '国考合格';
					 		if(value=='4') return '关闭';
					 		if(value=='3') return '待复考';
					 		if(value=='2') return '通过考试';
					 			if(value=='1') return '学习中';
					 			if(value=='0') return '已报名';


					 		}">学员状态</th>
					<th data-options="field:'commonScore',width:60" >考试成绩</th>
					<th data-options="field:'nationScore',width:60" >国考成绩</th>
					<th data-options="field:'createTime',width:100">创建时间</th>
		       	
				</tr>
			</thead>
		</table>
  	</div>
  	
  	<div id="toolbar" style="padding:2px;">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="school.student.query();">查询</a>
		<%-- <w:auth url="/school/student/add">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="school.student.add();">添加</a>
		</w:auth> --%>
		 <w:auth url="/school/student/delete/.*">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,group:'single'" onclick="school.student.remove();">删除</a>
		</w:auth>
		<w:auth url="/school/student/update/.*">
	    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true,group:'single'" onclick="school.student.update();">修改</a>
			</w:auth> 
		 <w:auth url="/school/student/viewDetail/.*">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,group:'single'" onclick="school.student.viewDetail();">详情</a>
			</w:auth>  
			 <w:auth url="/school/student/changeTerm/.*">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,group:'single'" onclick="school.student.changeTerm();">转期</a>
			</w:auth> 
			<%--  <w:auth url="/school/student/toFees/.*">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,group:'single'" onclick="school.student.toFees();">催费</a>
			</w:auth>  --%>
			 <w:auth url="/school/student/pay/.*">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,group:'single'" onclick="school.student.pay();">缴费</a>
			</w:auth> 
 	<%-- 	<w:auth url="/school/student/notify/.*">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,group:'single'" onclick="school.student.notify();">消息</a>
			</w:auth>  --%>
			<w:auth url="/school/student/terminate/.*">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,group:'single'" onclick="school.student.terminate();">退学</a>
			</w:auth> 
			<w:auth url="/school/student/graduate/.*">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,group:'single'" onclick="school.student.graduate();">领证</a>
			</w:auth> 
	</div>
  </body>
</html>
