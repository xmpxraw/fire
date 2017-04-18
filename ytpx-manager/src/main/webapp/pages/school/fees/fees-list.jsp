<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<%@include file="/pages/include/base-head.jsp"%>
	<%@include file="/pages/include/jquery-easyui.jsp"%>
	<script type="text/javascript" src="${skin}/js/school/fees.js"></script>
<script type="text/javascript">
	var disablestr = '<i class="red">暂停</i>';
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
   			url:'${contextpath}/school/fees/query', 
   			toolbar:'#toolbar'">
			<thead>
				<tr>
					<th data-options="field:'schemeName',width:50">方案名称</th>
					<th data-options="field:'feesTrain',width:100" formatter="formatPrice">培训费用</th>
					<th data-options="field:'feesMeal',width:150" formatter="formatPrice">餐费</th>
					<th data-options="field:'feesHotel',width:60" formatter="formatPrice">住宿费</th>
					 <th data-options="field:'feesStudy',width:80" formatter="formatPrice">鉴定费</th>	
					  <th data-options="field:'feesTotal',width:80"  formatter="formatPrice">总费用</th>		
					   <th data-options="field:'createTime',width:80">创建时间</th>					
					<th data-options="field:'schemeStatus',width:30,
					 		formatter:function(value,row,index) {
					 			if(value==1) return '启用';
					 			if(value==0) return disablestr;
					 			return '';
					 		}">方案状态</th>		       	
				</tr>
			</thead>
		</table>
  	</div>
  	
  <div id="toolbar" style="padding:2px;">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="school.fees.query();">查询</a> 
		  <w:auth url="/school/fees/add">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="school.fees.add();">添加</a>
		</w:auth>
		 <w:auth url="/school/fees/update/.*">
	    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true,group:'single'" onclick="school.fees.update();">修改</a>
	     </w:auth>
	   <w:auth url="/school/fees/delete/.*">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,group:'single'" onclick="school.fees.remove();">删除</a>
		 </w:auth>
		  <w:auth url="/school/fees/start/.*">
		  	
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-right',plain:true,group:'single'" onclick="school.fees.start();">启用</a>
		  </w:auth>
		  <w:auth url="/school/fees/stop/.*">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-right',plain:true,group:'single'" onclick="school.fees.stop();">停用</a> 
 		</w:auth>
	</div>
  </body>
</html>
