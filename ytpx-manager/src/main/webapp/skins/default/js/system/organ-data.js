Namespace('system.organ',{
	save:function(organId) {
		$('#organ-data-datagrid').datagrid('acceptChanges');
		var rows=$('#organ-data-datagrid').datagrid('getRows');
		for(var i=0; i<rows.length; i++){
			if(rows[i].code=='' || rows[i].value==''){
				top.showInfo('第'+(i+1)+'行的数据标识或数据值不能为空,请修正!');
				return false;
			}
		}
		
		$('#organ-data-datagrid').datagrid('loading');
		$.postJson(contextpath+'/system/organ/data_binding/'+organId,rows,function(result) {
			$('#organ-data-datagrid').datagrid('loaded');
			if(result.code==200) {
				$('#organ-data-datagrid').datagrid('reload');
				top.showInfo('绑定数据成功!');
			} else {
				top.showInfo('绑定数据失败!');
			}
		});
	},
	addDataRow:function() {
		$('#organ-data-datagrid').datagrid('appendRow',{
			id:'',
			organId:'',
			code:'',
			value:'',
			remark:''
		});
		
		var rows=$('#organ-data-datagrid').datagrid('getRows');
		$('#organ-data-datagrid').datagrid('selectRow',rows.length-1);
		this.updateDataRow();
	},
	updateDataRow:function() {
		var row = $('#organ-data-datagrid').datagrid('getSelected');
		if(row==null) {
			top.showInfo('请选择需要修改的记录!');
			return;
		}
		var index=$('#organ-data-datagrid').datagrid('getRowIndex',row);
		$('#organ-data-datagrid').datagrid('beginEdit', index);
		
		//创建一个闭包,用于编辑框获取光标时,选中本行
		var fn=function() {
			var rowIndex=$('#organ-data-datagrid').datagrid('getRowIndex',row);
			$('#organ-data-datagrid').datagrid('selectRow',rowIndex);
		};
		var editors = $('#organ-data-datagrid').datagrid('getEditors',index);
		for(var ed in editors) {
			$(editors[ed].target).focus(fn);
		}
	},
	removeDataRow:function() {
		var row = $('#organ-data-datagrid').datagrid('getSelected');
		if(row==null) {
			top.showInfo('请选择需要删除的记录!');
			return;
		}
		var index=$('#organ-data-datagrid').datagrid('getRowIndex',row);
		$('#organ-data-datagrid').datagrid('deleteRow',index);
	}
});