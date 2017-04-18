$(function() {
	system.logs.init();
});
Namespace('system.logs',{
	init:function() {
		easyui.util.initDatagrid('#datagrid',{
			onDblClickRow:function() {
				system.logs.view();
			}
		});
	},
	query:function() {
		getDialog('query-dialog').dialog({
			href:contextpath+'/system/logs/query',
			title: '查询',
		    width: 400,
		    height: 300,
		    buttons:[{
				text:'查询日志',
				iconCls:'icon-ok',
				handler:function(){
					system.logs.doQuery();
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
		
		var type=$('#logtype').combobox('getValue');
		if(type===undefined) {
			type=$('#logtype').combobox('getText');
		}
		data.type=type;
		$('#datagrid').datagrid('load',data);
		$('#query-dialog').dialog('close');
	},
	view:function() {
		
		var row = $('#datagrid').datagrid('getSelected');
		if(row==null) {
			top.showInfo('请选择需要修改的记录!');
			return;
		}
		
		getDialog('view-dialog').dialog({
			href:contextpath+'/system/logs/view/'+row.id,
			title: '查看日志',
		    width: 500,
		    height: 400,
		    buttons:[{
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function(){
					$('#view-dialog').dialog('close');
				}
			}]
		});
	}
});