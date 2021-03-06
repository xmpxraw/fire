$(function() {
	contract.budget.init();
});
Namespace('contract.budget',{
	init:function() {
		easyui.util.initDatagrid('#datagrid',{
			pageSize:20,
			onDblClickRow:function() {
				contract.budget.update();
			}
		});
	},
	query:function() {
		getDialog('query-dialog').dialog({
			href:contextpath+'/contract/budget/query',
			title: '查询预算项目',
		    width: 350,
		    height: 250,
		    buttons:[{
				text:'查询',
				iconCls:'icon-ok',
				handler:function(){
					contract.budget.doQuery();
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
			href:contextpath+'/contract/budget/update/'+row.id,
			title: '修改预算项目',
		    width: 500,
		    height: 250,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					contract.budget.doUpdate();
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$('#data-dialog').dialog('close');
				}
			}]
		});
	},
	doUpdate:function() {
		if(!$('#data-form').form('validate')) return;
		var data=$('#data-form').form('jsonObject');
		$('#data-dialog').dialog('close');
		
		$('#datagrid').datagrid('loading');
		var row = $('#datagrid').datagrid('getSelected');
		$.postJson(contextpath+'/contract/budget/update/'+row.id,data,function(result) {
			$('#datagrid').datagrid('loaded');
			if(result.code==200) {
				$('#datagrid').datagrid('reload');
			} else {
				top.showInfo('修改失败!');
			}
		});
	},
	add:function() {
		getDialog('data-dialog').dialog({
			href:contextpath+'/contract/budget/add',
			title: '添加预算项目',
		    width: 500,
		    height: 250,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					contract.budget.doAdd();
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$('#data-dialog').dialog('close');
				}
			}]
		});
	},
	doAdd:function() {
		if(!$('#data-form').form('validate')) return;
		var data=$('#data-form').form('jsonObject');
		$('#data-dialog').dialog('close');
		
		$('#datagrid').datagrid('loading');
		$.postJson(contextpath+'/contract/budget/add',data,function(result) {
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
		$.messager.confirm('信息提示','确定要删除吗？',function(sure){
		    if(!sure) return;
		    $('#datagrid').datagrid('loading');
		    $.post(contextpath+'/contract/budget/delete/'+row.id,function(result) {
				$('#datagrid').datagrid('loaded');
				if(result.code==200) {
					$('#datagrid').datagrid('reload');
				} else {
					top.showInfo('删除失败!');
				}
			},'json');
		});
	},
	sort:function() {
		getDialog('sort-dialog').dialog({
			href:contextpath+'/contract/budget/sort',
			title: '排序',
		    width: 400,
		    height: 400,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					contract.budget.doSort();
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$('#sort-dialog').dialog('close');
				}
			}]
		});
	},
	doSort:function() {
		$('#sort-dialog').dialog('close');
		$('#datagrid').datagrid('loading');
		var rows=$('#sort-datagrid').datagrid('getRows');
		$.postJson(contextpath+'/contract/budget/sort',rows,function(result) {
			$('#datagrid').datagrid('loaded');
			if(result.code==200) {
				$('#datagrid').datagrid('reload');
			} else {
				top.showInfo('排序失败:'+result.msg);
			}
		});
	},
	
	/**
	 * 向上移动
	 */
	sortUp:function() {

		var row = $('#sort-datagrid').datagrid('getSelected');
		if(row==null) {
			top.showInfo('请选择一条记录!');
			return;
		}
		
		var index=$('#sort-datagrid').datagrid('getRowIndex',row);
		if(index==0) {
			top.showInfo('已经是第一条记录,不能再向上移动!');
			return;
		}
		
		var rows=$('#sort-datagrid').datagrid('getRows');
		var _id=rows[index-1].id;
		var _name=rows[index-1].name;
		rows[index-1].id=row.id;
		rows[index-1].name=row.name;
		rows[index].id=_id;
		rows[index].name=_name;
		
		$('#sort-datagrid').datagrid('loadData',rows);
		$('#sort-datagrid').datagrid('selectRow',index-1);
	},
	
	/**
	 * 向下移动
	 */
	sortDown:function() {
		var row = $('#sort-datagrid').datagrid('getSelected');
		if(row==null) {
			top.showInfo('请选择一条记录!');
			return;
		}
		
		var index=$('#sort-datagrid').datagrid('getRowIndex',row);
		var rows=$('#sort-datagrid').datagrid('getRows');
		if(index==rows.length-1) {
			top.showInfo('已经是最后一条记录,不能再向下移动!');
			return;
		}
		
		var _id=rows[index+1].id;
		var _name=rows[index+1].name;
		rows[index+1].id=row.id;
		rows[index+1].name=row.name;
		rows[index].id=_id;
		rows[index].name=_name;
		
		$('#sort-datagrid').datagrid('loadData',rows);
		$('#sort-datagrid').datagrid('selectRow',index+1);
	},
	
	/**
	 * 检查预算项目名是否重复
	 */
	check:function(name) {
		var valid=false;
		$.ajax({
			url:contextpath+'/contract/budget/check',
			type:'POST',
			data:{name:name},
			dataType:'json',
			async:false,
			success:function(result) {
				if(result.code==200) {
					valid=true;
				} else {
					valid=false;
				}
			}
		});
		return valid;
	}
});