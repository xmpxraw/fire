$(function() {
	contract.query.init();
});
Namespace('contract.query',{
	init:function() {
		easyui.util.initDatagrid('#datagrid',{
			pageSize:20,
			pageList:[10,15,20,25,30,50,100,200,300],
			onDblClickRow:function() {
				contract.query.open();
			}
		});
	},
	queryall:function() {
		getDialog('query-dialog').dialog({
			href:contextpath+'/contract/query/all/querypage',
			title: '查询合同',
		    width: 500,
		    height: 350,
		    buttons:[{
				text:'查询',
				iconCls:'icon-ok',
				handler:function(){
					contract.query.doQueryAll();
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
	doQueryAll:function() {
		if(!$('#query-form').form('validate')) return;
		var data=$('#query-form').form('jsonObject');
		$('#datagrid').datagrid('load',data);
		$('#query-dialog').dialog('close');
	},
	
	//我的合同查询
	mylist:function() {
		getDialog('query-dialog').dialog({
			href:contextpath+'/contract/query/mylist/querypage',
			title: '查询合同',
		    width: 500,
		    height: 350,
		    buttons:[{
				text:'查询',
				iconCls:'icon-ok',
				handler:function(){
					contract.query.doMyList();
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
	doMyList:function() {
		if(!$('#query-form').form('validate')) return;
		var data=$('#query-form').form('jsonObject');
		$('#datagrid').datagrid('load',data);
		$('#query-dialog').dialog('close');
	},
	
	//待办合同查询
	todolist:function() {
		getDialog('query-dialog').dialog({
			href:contextpath+'/contract/query/todolist/querypage',
			title: '查询待办合同',
		    width: 500,
		    height: 350,
		    buttons:[{
				text:'查询',
				iconCls:'icon-ok',
				handler:function(){
					contract.query.doTodoList();
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
	doTodoList:function() {
		if(!$('#query-form').form('validate')) return;
		var data=$('#query-form').form('jsonObject');
		$('#datagrid').datagrid('load',data);
		$('#query-dialog').dialog('close');
	},
	
	//跟踪合同查询
	tracelist:function() {
		getDialog('query-dialog').dialog({
			href:contextpath+'/contract/query/tracelist/querypage',
			title: '查询跟踪合同',
		    width: 500,
		    height: 350,
		    buttons:[{
				text:'查询',
				iconCls:'icon-ok',
				handler:function(){
					contract.query.doTraceList();
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
	doTraceList:function() {
		if(!$('#query-form').form('validate')) return;
		var data=$('#query-form').form('jsonObject');
		$('#datagrid').datagrid('load',data);
		$('#query-dialog').dialog('close');
	},
	
	//已结束合同查询
	endlist:function() {
		getDialog('query-dialog').dialog({
			href:contextpath+'/contract/query/endlist/querypage',
			title: '查询已结束合同',
		    width: 500,
		    height: 350,
		    buttons:[{
				text:'查询',
				iconCls:'icon-ok',
				handler:function(){
					contract.query.doEndList();
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
	doEndList:function() {
		if(!$('#query-form').form('validate')) return;
		var data=$('#query-form').form('jsonObject');
		$('#datagrid').datagrid('load',data);
		$('#query-dialog').dialog('close');
	},
	//打开合同
	open:function() {
		var row = $('#datagrid').datagrid('getSelected');
		top.addTab('contract-'+row.id,row.title,contextpath+'/contract/process/open/'+row.id);
	},
	//查看合同
	view:function() {
		var row = $('#datagrid').datagrid('getSelected');
		top.addTab('contract-'+row.id,row.title,contextpath+'/contract/process/view/'+row.id);
	},
	//修改合同
	update:function() {
		var row = $('#datagrid').datagrid('getSelected');
		top.addTab('contract-'+row.id,row.title,contextpath+'/contract/process/update/'+row.id);
	},
	//删除合同
	remove:function() {
		var row = $('#datagrid').datagrid('getSelected');
		$.messager.confirm('信息提示','确定要删除吗？',function(sure){
		    if(!sure) return;
		    $('#datagrid').datagrid('loading');
		    $.post(contextpath+'/contract/process/delete/'+row.id,function(result) {
				$('#datagrid').datagrid('loaded');
				if(result.code==200) {
					$('#datagrid').datagrid('reload');
				} else {
					showInfo('删除失败!');
				}
			},'json');
		});
	},
	
	exportxls:function(querytype) {
		 
		$.messager.confirm('信息提示','确定要导出Excel文档吗？<br/>注意:请使用IE浏览器导出',function(sure){
		    if(!sure) return;
		    var $form=$('exportxls-form');
		    if($form.size()==0) {
		    	$form=$('<form/>',{
		    		id:'exportxls-form',
		    		method:'post',
		    		action:contextpath+'/contract/query/exportxls'
		    	}).appendTo($('body'));
		    }
		    $form.form('submit', {
		    	onSubmit: function(param){
		    		var queryparams=$('#datagrid').datagrid('options').queryParams;
		    		$.extend(param,queryparams);
		    		param.querytype=querytype;
		    	}
		    });
		});
	}
});