<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<%@include file="/pages/include/base-head.jsp"%>
	<%@include file="/pages/include/jquery-easyui.jsp"%>
	 <script type="text/javascript" src="${commonskin}/lib/jquery/jquery.form.min.js"></script> 
	<script type="text/javascript" src="${skin}/js/school/registe.js"></script>
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
   			url:'${contextpath}/school/registe/query', 
   			toolbar:'#toolbar'">
			<thead>
				<tr>
					<th data-options="field:'registeCode',width:50">报名编码</th>
					<th data-options="field:'registeTime',width:100">报名日期</th>
					<th data-options="field:'companyName',width:150">报名单位</th>
					<th data-options="field:'registeType',width:60">报名类型</th>
					 <th data-options="field:'registeCount',width:60">报名人数</th>		
					  <th data-options="field:'schemeName',width:100">缴费方案</th>		
				<!-- 	   <th data-options="field:'payShould',width:80" formatter="formatPrice">培训费用</th>	 -->	
					    <th data-options="field:'payShould',width:80" formatter="formatPrice">应缴费用</th>
					     <th data-options="field:'payReal',width:80" formatter="formatPrice">实缴费用</th>	
					    <th data-options="field:'payAlready',width:80" formatter="formatPrice">已缴费用</th>	
					  
					<th data-options="field:'payStatus',width:50,
					 		formatter:function(value,row,index) {
					 			if(value==2) return '部分缴费';
					 			if(value==1) return '已缴费';
					 			if(value==0) return disablestr;
					 			return '';
					 		}">缴费状态</th>
					 <th data-options="field:'payType',width:60">缴费方式</th>				
					<th data-options="field:'payDelay',width:50,
					 		formatter:function(value,row,index) {
					 			if(value==1) return '是';
					 			if(value==0) return  '否';
					 			return '';
					 		}">延时缴费</th>
					<th data-options="field:'createTime',width:100">创建时间</th>
		       	
				</tr>
			</thead>
		</table>
  	</div>
  	
  	<div id="toolbar" style="padding:2px;">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="school.registe.query();">查询</a>
		 <w:auth url="/school/registe/add">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="school.registe.add();">添加</a>
	   </w:auth>
	    <w:auth url="/school/student/query/.*">
	    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true,group:'single'" onclick="school.registe.viewStudent();">学员信息</a> 
	     </w:auth>
	    <w:auth url="/school/registe/discounte/.*">
	    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true,group:'single'" onclick="school.registe.discounte();">优惠</a> 
	     </w:auth>
	     <w:auth url="/school/registe/pay/.*">
	    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true,group:'single'" onclick="school.registe.pay();">确认缴费</a> 
	     </w:auth>
		 <w:auth url="/school/registe/delete/.*">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,group:'single'" onclick="school.registe.remove();">删除</a>
		</w:auth>
 
	</div>
  </body>
</html>
