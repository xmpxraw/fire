$(function() {
	system.quartz.init();
});
Namespace('system.quartz',{
	init:function() {
		easyui.util.initDatagrid('#datagrid',{
			onDblClickRow:function() {
				system.quartz.update();
			}
		});
	},
	query:function() {
		getDialog('query-dialog').dialog({
			href:contextpath+'/system/quartz/query',
			title: '查询',
		    width: 350,
		    height: 250,
		    buttons:[{
				text:'查询',
				iconCls:'icon-ok',
				handler:function(){
					system.quartz.doQuery();
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
			href:contextpath+'/system/quartz/update/'+row.scheduleJobId,
			title: '修改任务',
		    width: 500,
		    height: 320,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					system.quartz.doUpdate();
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
		$.postJson(contextpath+'/system/quartz/update/'+row.scheduleJobId,data,function(result) {
			$('#datagrid').datagrid('loaded');
			if(result.code==200) {
				if( result.msg=='OK' ){
					//top.showInfo('修改成功!');
					$('#datagrid').datagrid('reload');					
				}else{
					top.showInfo(result.msg);
				}
			} else {
				top.showInfo('修改失败!');
			}
		});
	},
	add:function() {
		getDialog('add-dialog').dialog({
			href:contextpath+'/system/quartz/add',
			title: '添加任务',
		    width: 500,
		    height: 320,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					system.quartz.doAdd();
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
		$.postJson(contextpath+'/system/quartz/add',data,function(result) {
			$('#datagrid').datagrid('loaded');
			if(result.code==200) {
				if( result.msg=='OK' ){
					//top.showInfo('添加成功!');
					$('#datagrid').datagrid('reload');					
				}else{
					top.showInfo('添加失败!' + result.msg);
				}
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
		    $.post(contextpath+'/system/quartz/delete/'+row.scheduleJobId,function(result) {
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
	startJob:function(){
		var row = $('#datagrid').datagrid('getSelected');
		if(row==null) {
			top.showInfo('请选择需要启用的记录!');
			return;
		}
		
		$.messager.confirm('信息提示','确定要启用吗？',function(sure){
		    if(!sure) return;
		    $('#datagrid').datagrid('loading');
		    $.post(contextpath+'/system/quartz/start/'+row.scheduleJobId,function(result) {
				$('#datagrid').datagrid('loaded');
				if(result.code==200) {
					if( result.msg=='OK' ){
						top.showInfo('启用成功!');
						$('#datagrid').datagrid('reload');
					}else{
						top.showInfo(result.msg);
					}
				} else {
					top.showInfo('启用任务失败!');
				}
			},'json');
		});
	},
	stopJob:function(){
		var row = $('#datagrid').datagrid('getSelected');
		if(row==null) {
			top.showInfo('请选择需要停用的记录!');
			return;
		}
		
		$.messager.confirm('信息提示','确定要停用任务吗？',function(sure){
		    if(!sure) return;
		    $('#datagrid').datagrid('loading');
		    $.post(contextpath+'/system/quartz/stop/'+row.scheduleJobId,function(result) {
				$('#datagrid').datagrid('loaded');
				if(result.code==200) {
					if( result.msg=='OK' ){
						top.showInfo('停用任务成功!');
						$('#datagrid').datagrid('reload');
					}else{
						top.showInfo(result.msg);
					}
				} else {
					top.showInfo('停用任务失败!');
				}
			},'json');
		});
	},
	runOnce:function(){
		var row = $('#datagrid').datagrid('getSelected');
		if(row==null) {
			top.showInfo('请选择需要执行的任务!');
			return;
		}
		
		$.messager.confirm('信息提示','确定要执行任务吗？',function(sure){
		    if(!sure) return;
		    $('#datagrid').datagrid('loading');
		    $.post(contextpath+'/system/quartz/runOnce/'+row.scheduleJobId,function(result) {
				$('#datagrid').datagrid('loaded');
				if(result.code==200) {
					if( result.msg=='OK' ){
						top.showInfo('执行任务成功!');
						$('#datagrid').datagrid('reload');
					}else{
						top.showInfo(result.msg);
					}
				} else {
					top.showInfo('执行任务失败!');
				}
			},'json');
		});
	}
});
