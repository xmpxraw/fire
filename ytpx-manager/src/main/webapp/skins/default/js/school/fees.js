$(function() {
	school.fees.init();
});
Namespace('school.fees',{
	init:function() {
		easyui.util.initDatagrid('#datagrid',{
			onDblClickRow:function() {
				school.fees.update();
			}
		});
	},
	query:function() {
		getDialog('query-dialog').dialog({
			href:contextpath+'/school/fees/query',
			title: '查询',
		    width: 350,
		    height: 250,
		    buttons:[{
				text:'查询',
				iconCls:'icon-ok',
				handler:function(){
					school.fees.doQuery();
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
			href:contextpath+'/school/fees/update/'+row.id,
			title: '修改费用方案',
			 width: 630,
			 height: 530,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					school.fees.doUpdate();
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
		$.postJson(contextpath+'/school/fees/update/'+row.id,data,function(result) {
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
			href:contextpath+'/school/fees/add',
			title: '添加费用方案',
		    width: 630,
		    height: 530,
		    resizable:true,
		    maximizable:true,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					school.fees.doAdd();
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
		$.postJson(contextpath+'/school/fees/add',data,function(result) {
			$('#datagrid').datagrid('loaded');
			if(result.code==200) {
				//top.showInfo('添加成功!');
				$('#datagrid').datagrid('reload');
			} else {
				top.showInfo('添加失败!');
			}
		});
	},
	check:function(id) {
		var valid=0;
		$.ajax({
			url:contextpath+'/school/fees/check_status',
			type:'POST',
			data:{id:id},
			dataType:'json',
			async:false,
			success:function(result) {
				if(result.code==1) {//启用状态
					valid=1;
				} else {
					valid=0;
				}
			}
		});
		return valid;
	},
	start:function() {
		var row = $('#datagrid').datagrid('getSelected');
		if(row==null) {
			top.showInfo('请选择需要的记录!');
			return;
		}
		
		$.messager.confirm('信息提示','确定要启用吗？',function(sure){
		    if(!sure) return;
		    var status=school.fees.check(row.id); 
		    if(status==1) {
		    	top.showInfo('已启用');
		    	return;
		    }  
		    $('#datagrid').datagrid('loading');
		    var status=1;
		    $.post(contextpath+'/school/fees/start/'+row.id,status,function(result) {
				$('#datagrid').datagrid('loaded');
				if(result.code==200) {
					$('#datagrid').datagrid('reload');
				} else {
					top.showInfo('启用失败!');
				}
			},'json');
		});
	},
	stop:function() {
		var row = $('#datagrid').datagrid('getSelected');
		if(row==null) {
			top.showInfo('请选择需要的记录!');
			return;
		}
		
		$.messager.confirm('信息提示','确定要停用吗？',function(sure){
		    if(!sure) return;
		    var status=school.fees.check(row.id); 
		    if(status==0) {
		    	top.showInfo('已停用');
		    	return;
		    }  
		    $('#datagrid').datagrid('loading');
		    var status=0;
		    $.post(contextpath+'/school/fees/stop/'+row.id,status,function(result) {
				$('#datagrid').datagrid('loaded');
				if(result.code==200) {
					$('#datagrid').datagrid('reload');
				} else {
					top.showInfo('停用失败!');
				}
			},'json');
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
		    $.post(contextpath+'/school/fees/delete/'+row.id,function(result) {
				$('#datagrid').datagrid('loaded');
				if(result.code==200) {
					//top.showInfo('删除成功!');
					$('#datagrid').datagrid('reload');
				} else {
					top.showInfo('删除失败!');
				}
			},'json');
		});
	}/**/
	
});