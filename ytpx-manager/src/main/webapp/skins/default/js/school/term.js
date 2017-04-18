$(function() {
	school.term.init();
});
Namespace('school.term',{
	init:function() {
		easyui.util.initDatagrid('#datagrid',{
			onDblClickRow:function() {
				school.term.update();
			}
		});
	},
	query:function() {
		getDialog('query-dialog').dialog({
			href:contextpath+'/school/term/query',
			title: '查询',
		    width: 350,
		    height: 250,
		    buttons:[{
				text:'查询',
				iconCls:'icon-ok',
				handler:function(){
					school.term.doQuery();
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
			href:contextpath+'/school/term/update/'+row.id,
			title: '修改期数信息',
		    width: 350,
		    height: 370,
		    resizable:true,
		    maximizable:true,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					school.term.doUpdate();
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
		$.postJson(contextpath+'/school/term/update/'+row.id,data,function(result) {
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
			href:contextpath+'/school/term/add',
			title: '添加期数',
		    width: 350,
		    height: 350,
		    resizable:true,
		    maximizable:true,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					school.term.doAdd();
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
		$.postJson(contextpath+'/school/term/add',data,function(result) {
			$('#datagrid').datagrid('loaded');
			if(result.code==200) {
				//top.showInfo('添加成功!');
				$('#datagrid').datagrid('reload');
			} else {
				top.showInfo('添加失败!');
			}
		});
	},
	checkTerm:function(term) {
		var valid=false;
		$.ajax({
			url:contextpath+'/school/term/check_term',
			type:'POST',
			data:{term:term},
			dataType:'json',
			async:false,
			success:function(result) {
				if(result.code==-1) {
					valid=false;
				} else {
					valid=true;
				}
			}
		});
		return valid;
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
		    $.post(contextpath+'/school/term/delete/'+row.id,function(result) {
				$('#datagrid').datagrid('loaded');
				if(result.code==200) {
					//top.showInfo('删除成功!');
					$('#datagrid').datagrid('reload');
				}else if(result.code==-1) {
					top.showInfo('已经使用中!');
				} 	else {
					top.showInfo('删除失败!');
				}
			},'json');
		});
	},
	change:function() {
		
		var row = $('#datagrid').datagrid('getSelected');		
		getDialog('change-dialog').dialog({
			href:contextpath+'/school/term/change/'+row.id,
			title: '批量转期',
		    width: 350,
		    height: 250,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					school.term.doChange();
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
	doChange:function() {
		if(!$('#change-form').form('validate')) return;
		var data=$('#change-form').form('jsonObject');
		$('#change-dialog').dialog('close');
		
		$('#datagrid').datagrid('loading');
		var row = $('#datagrid').datagrid('getSelected');
		$.postJson(contextpath+'/school/term/change/'+row.id,data,function(result) {
			$('#datagrid').datagrid('loaded');
			if(result.code==200) {
				top.showInfo('批量转期成功!');
				$('#datagrid').datagrid('reload');
			}else if(result.code==-1) {
				top.showInfo(result.msg);
			} else {
				top.showInfo('批量转期失败!');
			}
		});
	}
	
});