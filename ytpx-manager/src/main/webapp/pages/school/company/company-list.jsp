<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<%@include file="/pages/include/base-head.jsp"%>
	<%@include file="/pages/include/jquery-easyui.jsp"%>
	<script type="text/javascript" src="${skin}/js/school/company.js"></script>
	<script type="text/javascript">
		var disablestr='<i class="red">新建</i>';
	</script>
  </head>
  <body>
  	<div class="container">
	    <table id="datagrid" data-options="
   			url:'${contextpath}/school/company/query', 
   			toolbar:'#toolbar'">
			<thead>
				<tr>
					<th data-options="field:'province',width:50">省</th>
					<th data-options="field:'city',width:50">市</th>
					<th data-options="field:'district',width:50">区</th>
					<th data-options="field:'companyType',width:70">单位类型</th>
					<th data-options="field:'companyName',width:100">单位名称</th>
					<th data-options="field:'companyAddr',width:150">单位地址</th>
					<th data-options="field:'contact',width:60">单位联系人</th>
					<th data-options="field:'telephone',width:60">联系电话</th>
					<th data-options="field:'invoiceTitle',width:60">发票抬头</th>
					<th data-options="field:'invoiceType',width:60">发票类型</th>
					<th data-options="field:'taxpayerNo',width:60">纳税人识别号</th>
					 <th data-options="field:'invoiceTaken',width:80">发票领取方式</th>
					 <th data-options="field:'bank',width:80">开户行</th>	
					 <th data-options="field:'bankAccount',width:80">银行帐号</th>	
					 <th data-options="field:'registCount',width:80">报名次数</th>	
					 <th data-options="field:'studentTotal',width:80">学员人数</th>
					<th data-options="field:'createTime',width:100">创建时间</th>
					<!-- <th data-options="field:'status',width:30,
					 		formatter:function(value,row,index) {
					 			if(value==2) return '下线';
					 			if(value==1) return '上线';
					 			if(value==0) return disablestr;
					 			return '';
					 		}">学校状态</th> -->
		       	
				</tr>
			</thead>
		</table>
  	</div>
  	
  	<div id="toolbar" style="padding:2px;">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="school.company.query();">查询</a>
		 <w:auth url="/school/company/add">	
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="school.company.add();">添加</a>
		</w:auth>
		 <w:auth url="/school/company/update/.*">
	    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true,group:'single'" onclick="school.company.update();">修改</a>
		</w:auth>
		 <w:auth url="/school/company/delete/.*">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,group:'single'" onclick="school.company.remove();">删除</a>
		</w:auth>
<!-- 		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-right',plain:true,group:'single'" onclick="school.school.online();">上线</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-right',plain:true,group:'single'" onclick="school.school.offline;">下线</a> -->
 
	</div>
  </body>
</html>
