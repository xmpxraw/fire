$(function() {
	school.registe.init();
});
Namespace('school.registe',{
	init:function() {
		easyui.util.initDatagrid('#datagrid',{
			/*onDblClickRow:function() {
				school.registe.update();
			}*/
		});
	},
	query:function() {
		getDialog('query-dialog').dialog({
			href:contextpath+'/school/registe/query',
			title: '查询',
		    width: 350,
		    height: 250,
		    buttons:[{
				text:'查询',
				iconCls:'icon-ok',
				handler:function(){
					school.registe.doQuery();
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
			href:contextpath+'/school/registe/update/'+row.id,
			title: '修改学校信息',
		    width: 550,
		    height: 450,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					school.registe.doUpdate();
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
		$.postJson(contextpath+'/school/registe/update/'+row.id,data,function(result) {
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
			href:contextpath+'/school/registe/add',
			title: '报名',
		    width: 320,
		    height: 380,
		    resizable:true,
		    maximizable:true,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					school.registe.doAdd();
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
		
		$.messager.progress();
		$('#add-form').ajaxSubmit({
			url:contextpath+'/school/registe/add',
			dataType : 'json',
			success : function(msg) {
				$.messager.progress('close');
				if (msg.code == 200) {
					$('#add-dialog').dialog('close');
					$('#datagrid').datagrid('reload');
				} else {
					showInfo(msg.msg);
				}
			},
			error : function(xhr,status,e) {
				console.log(status+' '+e);
				$.messager.progress('close');
				showInfo(msg.msg);
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
		    $.post(contextpath+'/school/registe/delete/'+row.id,function(result) {
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
	viewStudent:function() {
		var row = $('#datagrid').datagrid('getSelected');
		/*if(!row.template) {
			top.showInfo('未上传合同模板!');
			return;
		}*/
		top.addTab('','查看学员',contextpath+'/school/student/query/'+row.id);
	},
	discounte:function() {
		
		var row = $('#datagrid').datagrid('getSelected');
		var status=row.payStatus;
		var discounte=0;
//		debugger
		if(status>discounte) {
			top.showInfo('已经有学员缴费、不可以优惠!');
			return;
		}
		
		getDialog('update-dialog').dialog({
			href:contextpath+'/school/registe/discounte/'+row.id,
			title: '优惠',
		    width: 350,
		    height: 450,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					school.registe.doDiscounte();
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
	doDiscounte:function() {
		if(!$('#update-form').form('validate')) return;
		var data=$('#update-form').form('jsonObject');
		$('#update-dialog').dialog('close');
		
		$('#datagrid').datagrid('loading');
		var row = $('#datagrid').datagrid('getSelected');
		$.postJson(contextpath+'/school/registe/discounte/'+row.id,data,function(result) {
			$('#datagrid').datagrid('loaded');
			if(result.code==200) {
				//top.showInfo('修改成功!');
				$('#datagrid').datagrid('reload');
			} else {
				top.showInfo('优惠失败!');
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
			href:contextpath+'/school/registe/pay/'+row.id,
			title: '确认缴费',
		    width: 350,
		    height: 450,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					school.registe.doPay();
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$('#pay-dialog').dialog('close');
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
		$.postJson(contextpath+'/school/registe/pay/'+row.id,data,function(result) {
			$('#datagrid').datagrid('loaded');
			if(result.code==200) {
				//top.showInfo('修改成功!');
				$('#datagrid').datagrid('reload');
			} else {
				top.showInfo('确认缴费失败!');
			}
		});
	},
	exportTemplet:function() {
		 window.location.href=contextpath+'/fileUpload/signUpTemplet.xlsx';
	}
	
	
});