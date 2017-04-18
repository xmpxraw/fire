$(function() {
	contract.company.init();
});
Namespace('contract.company',{
	init:function() {
		//初始化窗口
		$('#win-dialog').dialog({
			buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function(){
					contract.company.ok();
				}
			},{
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function(){
					window.close();
				}
			}]
		});
		
		easyui.util.initDatagrid('#datagrid',{
			pageSize:20,
			onDblClickRow:function() {
				contract.company.ok();
			}
		});
	},
	ok:function() {
		var row = $('#datagrid').datagrid('getSelected');
		if(row==null) {
			showInfo('无选择记录,请先选择!');
			return;
		}
		window.returnValue=row;
		window.close();
	},
	query:function() {
		var data={searchkey:$('#searchkey').val()};
		$('#datagrid').datagrid('load',data);
	},
	add:function() {
		getDialog('data-dialog').dialog({
			href:contextpath+'/contract/company/add',
			title: '添加企业法人',
		    width: 500,
		    height: 450,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					contract.company.doAdd();
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
		$.postJson(contextpath+'/contract/company/add',data,function(result) {
			$('#datagrid').datagrid('loaded');
			if(result.code==200) {
				$('#datagrid').datagrid('reload');
			} else {
				top.showInfo('添加失败!');
			}
		});
	},
	
	/**
	 * 检查企业法人名是否重复
	 */
	check:function(name) {
		var valid=false;
		$.ajax({
			url:contextpath+'/contract/company/check',
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