$(function() {
	study.directory.init();
});
Namespace('study.directory',{
	init:function() {
		$('#directory_tree_grid').treegrid({
		    url:contextpath + '/directory/treedata',
		    idField:'id',
		    treeField:'name',
		    columns:[[
		        {field:'code',title:'目录编码', width:100},
		        {field:'name',title:'目录名称', width:180},
		        {field:'tag',title:'目录标签',width:150,align:'left'},
		        {field:'status',title:'状态',width:80,formatter:function(value,row,index) {
		 			if(value==1) return '启用';
		 			if(value==0) return '禁用';
		 			return '';
		 		}},
		        {field:'significance',title:'重要程度',width:80},
		        {field:'createTime',title:'创建时间',width:150}
		    ]]
		});
	},
	//禁用
	disabled:function() {
		changeStatus("0", "禁用");
	},
	//启用
	enabled: function() {
		changeStatus("1", "启用");
	},
	//增加弹出窗
	add: function() {
		getDialog('add-dialog').dialog({
			href:contextpath+'/directory/add',
			title: '添加目录',
		    width: 600,
		    height: 400,
		    resizable:true,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					study.directory.doAdd();
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
	//增加操作
	doAdd: function(id) {
		if(!$('#add-form').form('validate')) return;
		var data=$('#add-form').form('jsonObject');
		$('#add-dialog').dialog('close');
		$('#datagrid').datagrid('loading');
		$.postJson(contextpath+'/directory/add?id=' + id,data,function(result) {
			$('#directory_tree_grid').treegrid('loaded');
			if(result.code==200) {
				top.showInfo('操作成功');
				$('#directory_tree_grid').treegrid('reload');
			} else {
				top.showInfo('添加失败!' + result.msg);
			}
		});
	},
	//编辑功能
	update: function() {
		$row = $("#directory_tree_grid").treegrid("getSelected");
		if ($row == null) {
			top.showInfo('请选择需要编辑的目录!');
			return;
		}
		getDialog('add-dialog').dialog({
			href:contextpath+'/directory/add?id=' + $row.id,
			title: '修改目录',
		    width: 600,
		    height: 400,
		    resizable:true,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					study.directory.doAdd();
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$('#add-dialog').dialog('close');
				}
			}]
		});
	}
	
});

function changeStatus(status, title) {
	$row = $("#directory_tree_grid").treegrid("getSelected");
	if ($row == null) {
		top.showInfo('请选择需要'+title+'的目录!');
		return;
	}
	if (status == $row.status) {
		top.showInfo("当前目录是" + title + "状态");
		return ;
	}
	$.messager.confirm('温馨提示','确定要'+title+'目录吗？',function(sure){
	    if(!sure) return;
	    $('#directory_tree_grid').treegrid('loading');
	    $.post(contextpath+'/directory/change_status?id='+ $row.id + "&status=" + status,function(result) {
			$('#directory_tree_grid').treegrid('loaded');
			if(result.code == 200) {
				$('#directory_tree_grid').treegrid('reload');
			} else {
				top.showInfo('操作失败!');
			}
		},'json');
	});
}