$(function() {
	contract.category.init();
});
Namespace('contract.category',{
	init:function() {
		easyui.util.initDatagrid('#datagrid',{
			pageSize:20,
			onDblClickRow:function() {
				contract.category.update();
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
	update:function() {
		
		var row = $('#datagrid').datagrid('getSelected');
		if(row==null) {
			top.showInfo('请选择需要修改的记录!');
			return;
		}
		
		getDialog('data-dialog').dialog({
			href:contextpath+'/contract/category/update/'+row.id,
			title: '修改分类',
		    width: 500,
		    height: 250,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					contract.category.doUpdate();
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
		
		var row = $('#datagrid').datagrid('getSelected');
		$.messager.progress();
		$('#data-form').ajaxSubmit({
			url:contextpath+'/contract/category/update/'+row.id,
			dataType : 'json',
			success : function(msg) {
				$.messager.progress('close');
				if (msg.code == 200) {
					$('#data-dialog').dialog('close');
					$('#datagrid').datagrid('reload');
				} else {
					showInfo('修改分类失败!');
				}
			},
			error : function(xhr,status,e) {
				console.log(status+' '+e);
				$.messager.progress('close');
				showInfo('修改分类失败!');
			}
		});
	},
	add:function() {
		getDialog('data-dialog').dialog({
			href:contextpath+'/contract/category/add',
			title: '添加分类',
		    width: 500,
		    height: 250,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					contract.category.doAdd();
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
		
		$.messager.progress();
		$('#data-form').ajaxSubmit({
			url:contextpath+'/contract/category/add',
			dataType : 'json',
			success : function(msg) {
				$.messager.progress('close');
				if (msg.code == 200) {
					$('#data-dialog').dialog('close');
					$('#datagrid').datagrid('reload');
				} else {
					showInfo('添加分类失败!');
				}
			},
			error : function(xhr,status,e) {
				console.log(status+' '+e);
				$.messager.progress('close');
				showInfo('添加分类失败!');
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
		    $.post(contextpath+'/contract/category/delete/'+row.id,function(result) {
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
			href:contextpath+'/contract/category/sort',
			title: '排序',
		    width: 400,
		    height: 400,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					contract.category.doSort();
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
		$.postJson(contextpath+'/contract/category/sort',rows,function(result) {
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
	 * 检查分类名是否重复
	 */
	check:function(name) {
		var valid=false;
		$.ajax({
			url:contextpath+'/contract/category/check',
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
	},
	
	//查看合同模板
	viewtemplate:function() {
		var row = $('#datagrid').datagrid('getSelected');
		if(!row.template) {
			top.showInfo('未上传合同模板!');
			return;
		}
		top.addTab('',row.name+'-合同模板',contextpath+'/contract/category/template/view/'+row.id);
	}
});