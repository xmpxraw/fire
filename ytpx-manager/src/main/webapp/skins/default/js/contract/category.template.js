$(function() {
	contract.category.init();
});
Namespace('contract.category',{
	init:function() {
		easyui.util.initDatagrid('#datagrid',{
			pageSize:20,
			onDblClickRow:function() {
				contract.category.getCategory();
			}
		});
	},
	query:function() {
		getDialog('query-dialog').dialog({
			href:contextpath+'/contract/category/query',
			title: '查询分类',
		    width: 350,
		    height: 250,
		    buttons:[{
				text:'查询',
				iconCls:'icon-ok',
				handler:function(){
					contract.category.doQuery();
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
	
	getCategory:function() {
		var row = $('#datagrid').datagrid('getSelected');
		if(row==null) {
			top.showInfo('请选择合同分类!');
			return;
		}
		if(!row.template) {
			top.showInfo('此分类没有上传模板,请先上传合同模板!');
			return;
		}
		top.contract_category=row;
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
});