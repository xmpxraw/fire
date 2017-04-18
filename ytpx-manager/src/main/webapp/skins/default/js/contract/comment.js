$(function() {
	contract.comment.init();
});
Namespace('contract.comment',{
	init:function() {
		easyui.util.initDatagrid('#datagrid',{
			pageSize:20,
			onDblClickRow:function() {
				contract.comment.update();
			}
		});
	},
	query:function() {
		getDialog('query-dialog').dialog({
			href:contextpath+'/contract/comment/query',
			title: '查询审批意见',
		    width: 350,
		    height: 250,
		    buttons:[{
				text:'查询',
				iconCls:'icon-ok',
				handler:function(){
					contract.comment.doQuery();
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
		getDialog('data-dialog').dialog({
			href:contextpath+'/contract/comment/update/'+row.id,
			title: '查看审批意见',
		    width: 500,
		    height: 300,
		    buttons:[{
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function(){
					$('#data-dialog').dialog('close');
				}
			}]
		});
	},
	remove:function() {
		var row = $('#datagrid').datagrid('getSelected');
		$.messager.confirm('信息提示','确定要删除吗？',function(sure){
		    if(!sure) return;
		    $('#datagrid').datagrid('loading');
		    $.post(contextpath+'/contract/comment/delete/'+row.id,function(result) {
				$('#datagrid').datagrid('loaded');
				if(result.code==200) {
					$('#datagrid').datagrid('reload');
				} else {
					top.showInfo('删除失败!');
				}
			},'json');
		});
	}
});