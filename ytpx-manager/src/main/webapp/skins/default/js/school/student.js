$(function() {
	school.student.init();
});
Namespace('school.student',{
	init:function() {
		easyui.util.initDatagrid('#datagrid',{
			onDblClickRow:function() {
				school.student.update();
			}
		});
	},
	query:function() {
		getDialog('query-dialog').dialog({
			href:contextpath+'/school/student/query',
			title: '查询',
		    width: 350,
		    height: 350,
		    buttons:[{
				text:'查询',
				iconCls:'icon-ok',
				handler:function(){
					school.student.doQuery();
				}
			},{
				text:'重置',
				iconCls:'icon-undo',
				handler:function(){
					$('#query-form').form('reset');
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$('#query-dialog').dialog('close');
				}
			}]
		});
	},
	doQuery:function() {
		if(!$('#query-form').form('validate')) return;
		var data=$('#query-form').form('jsonObject');
		$('#datagrid').datagrid('load',data);
		$('#query-dialog').dialog('close');
	},
	update:function() {
		
		var row = $('#datagrid').datagrid('getSelected');
		if(row==null) {
			top.showInfo('请选择需要修改的记录!');
			return;
		}
		
		getDialog('update-dialog').dialog({
			href:contextpath+'/school/student/update/'+row.id,
			title: '修改学员信息',
		    width: 350,
		    height: 350,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					school.student.doUpdate();
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$('#update-dialog').dialog('close');
				}
			}]
		});
	},
	doUpdate:function() {
		if(!$('#update-form').form('validate')) return;
		var data=$('#update-form').form('jsonObject');
		$('#update-dialog').dialog('close');
		
		$('#datagrid').datagrid('loading');
		var row = $('#datagrid').datagrid('getSelected');
		$.postJson(contextpath+'/school/student/update/'+row.id,data,function(result) {
			$('#datagrid').datagrid('loaded');
			if(result.code==200) {
				//top.showInfo('修改成功!');
				$('#datagrid').datagrid('reload');
			} else {
				top.showInfo('修改失败!');
			}
		});
	},
	add:function() {
		getDialog('add-dialog').dialog({
			href:contextpath+'/school/student/add',
			title: '添加',
		    width: 630,
		    height: 530,
		    resizable:true,
		    maximizable:true,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					school.student.doAdd();
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$('#add-dialog').dialog('close');
				}
			}]
		});
	},
	
	doAdd:function() {
		if(!$('#add-form').form('validate')) return;
		var data=$('#add-form').form('jsonObject');
		$('#add-dialog').dialog('close');
		
		$('#datagrid').datagrid('loading');
		$.postJson(contextpath+'/school/student/add',data,function(result) {
			$('#datagrid').datagrid('loaded');
			if(result.code==200) {
				//top.showInfo('添加成功!');
				$('#datagrid').datagrid('reload');
			} else {
				top.showInfo('添加失败!');
			}
		});
	},
	remove:function() {
		var row = $('#datagrid').datagrid('getSelected');
		if(row==null) {
			top.showInfo('请选择需要删除的记录!');
			return;
		}
		
		$.messager.confirm('信息提示','确定要删除吗？',function(sure){
		    if(!sure) return;
		    $('#datagrid').datagrid('loading');
		    $.post(contextpath+'/school/student/delete/'+row.id,function(result) {
				$('#datagrid').datagrid('loaded');
				if(result.code==200) {
					//top.showInfo('删除成功!');
					$('#datagrid').datagrid('reload');
				} else {
					top.showInfo('删除失败!');
				}
			},'json');
		});
	},
	terminate:function() {
		var row = $('#datagrid').datagrid('getSelected');
		
		if(row.studentStatus==9) {
			top.showInfo('已经退学!');
			return;
		}
		
		$.messager.confirm('信息提示','确定要退学吗？',function(sure){
		    if(!sure) return;
		    $('#datagrid').datagrid('loading');
		    $.post(contextpath+'/school/student/terminate/'+row.id,function(result) {
				$('#datagrid').datagrid('loaded');
				if(result.code==200) {
					$('#datagrid').datagrid('reload');
				} else {
					top.showInfo('退学失败!');
				}
			},'json');
		});
	},
	graduate:function() {
		var row = $('#datagrid').datagrid('getSelected');
		if(row.studentStatus==8) {
			top.showInfo('已经领证!');
			return;
		}
		if(row.payStatus==0) {
			top.showInfo('未缴费、不可以领证!');
			return;
		}
		
		$.messager.confirm('信息提示','确定要领证吗？',function(sure){
		    if(!sure) return;
		    $('#datagrid').datagrid('loading');
		    $.post(contextpath+'/school/student/graduate/'+row.id,function(result) {
				$('#datagrid').datagrid('loaded');
				if(result.code==200) {
					$('#datagrid').datagrid('reload');
				} else {
					top.showInfo('领证失败!');
				}
			},'json');
		});
	},
	changeTerm:function() {
		
		var row = $('#datagrid').datagrid('getSelected');		
		var status=row.studentStatus;
	//	debugger
		if(status!=0 && status!=4 && status!=6) {
			top.showInfo('该学员不可以转期!!');
			return;
		}
		getDialog('change-dialog').dialog({
			href:contextpath+'/school/student/changeTerm/'+row.id,
			title: '确认转期',
		    width: 350,
		    height: 250,
		    resizable:true,
		    maximizable:true,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					school.student.doChangeTerm();
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$('#change-dialog').dialog('close');
				}
			}]
		});
	},
	doChangeTerm:function() {
		if(!$('#change-form').form('validate')) return;
		var data=$('#change-form').form('jsonObject');
		$('#change-dialog').dialog('close');
		
		$('#datagrid').datagrid('loading');
		var row = $('#datagrid').datagrid('getSelected');
		$.postJson(contextpath+'/school/student/changeTerm/'+row.id,data,function(result) {
			$('#datagrid').datagrid('loaded');
			if(result.code==200) {
				top.showInfo('转期成功!');
				$('#datagrid').datagrid('reload');
			}else if(result.code==-1) {
				top.showInfo(result.msg);
			} else {
				top.showInfo('转期失败!');
			}
		});
	},
	pay:function() {
		
		var row = $('#datagrid').datagrid('getSelected');
		var status=row.payStatus;
		var pay=1;
//		debugger
		if(status==pay) {
			top.showInfo('已缴费、不可重复缴费!');
			return;
		}
		
		getDialog('pay-dialog').dialog({
			href:contextpath+'/school/student/pay/'+row.id,
			title: '确认缴费',
		    width: 350,
		    height: 580,
		    resizable:true,
		    maximizable:true,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
				/*	var d = $(this).closest('.window-body');
					d.dialog('destroy');*/
					school.student.doPay();
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$('#pay-dialog').dialog('close');
					//$('#pay-dialog').remove();
				}
			}]
		});
	},
	doPay:function() {
		if(!$('#pay-form').form('validate')) return;
		var data=$('#pay-form').form('jsonObject');
		$('#pay-dialog').dialog('close');
		
		$('#datagrid').datagrid('loading');
		var row = $('#datagrid').datagrid('getSelected');
		$.postJson(contextpath+'/school/student/pay/'+row.id,data,function(result) {
			$('#datagrid').datagrid('loaded');
			if(result.code==200) {
				//top.showInfo('修改成功!');
				$('#datagrid').datagrid('reload');
			} else {
				top.showInfo('确认缴费失败!');
			}
		});
	},
	viewDetail:function() {
		var row = $('#datagrid').datagrid('getSelected');
		if(row==null) {
			top.showInfo('请选择需要的记录!');
			return;
		}
		getDialog('data-dialog').dialog({
			href:contextpath+'/school/student/viewDetail/'+row.id,
			title: '详细',
		    width: 630,
		    height: 530,
		    resizable:true,
		    maximizable:true,
		    buttons:[{
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function(){
					$('#data-dialog').dialog('close');
				}
			}]
		});
	},
	
	
});