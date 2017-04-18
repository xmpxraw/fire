$(function() {
	system.organ.group.init();
});
Namespace('system.organ.group',{
	init:function() {
		easyui.util.initDatagrid('#datagrid',{
			onDblClickRow:function() {
				system.organ.group.update();
			}
		});
	},
	add:function() {
		getDialog('data-dialog').dialog({
			href:contextpath+'/system/organ/group/add',
			title: '添加工作组',
		    width: 500,
		    height: 350,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					system.organ.group.doAdd();
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
		data.organid=this.getOrganid();
		$('#data-dialog').dialog('close');
		
		$('#datagrid').datagrid('loading');
		$.post(contextpath+'/system/organ/group/add',data,function(result) {
			$('#datagrid').datagrid('loaded');
			if(result.code==200) {
				$('#datagrid').datagrid('reload');
			} else {
				top.showInfo('添加失败!');
			}
		});
	},
	update:function() {
		
		var row = $('#datagrid').datagrid('getSelected');
		getDialog('data-dialog').dialog({
			href:contextpath+'/system/organ/group/update/'+row.id,
			title: '修改工作组',
		    width: 500,
		    height: 350,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					system.organ.group.doUpdate();
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
		data.organid=this.getOrganid();
		$('#data-dialog').dialog('close');
		
		$('#datagrid').datagrid('loading');
		var row = $('#datagrid').datagrid('getSelected');
		$.post(contextpath+'/system/organ/group/update/'+row.id,data,function(result) {
			$('#datagrid').datagrid('loaded');
			if(result.code==200) {
				$('#datagrid').datagrid('reload');
			} else {
				top.showInfo('修改失败!');
			}
		});
	},
	remove:function() {
		var row = $('#datagrid').datagrid('getSelected');
		$.messager.confirm('信息提示','确定要删除吗？',function(sure){
		    if(!sure) return;
		    $('#datagrid').datagrid('loading');
		    $.post(contextpath+'/system/organ/group/delete/'+row.id,function(result) {
				$('#datagrid').datagrid('loaded');
				if(result.code==200) {
					$('#datagrid').datagrid('reload');
				} else {
					top.showInfo('删除失败!');
				}
			},'json');
		});
	},
	getOrganid:function() {
		var form=$('#data-form')[0];
		var organid=[];
		for(var i=0;i<form.organid.options.length;i++) {
			var opt=form.organid.options[i];
			organid.push(opt.value);
		}
		return organid;
	},
	addOrgans:function() {
		var organs=system.organ.selectOrgans({
			checkbox:1,
			role:false,	
			enterprice:false,
			dept:false,	
			emp:true
		});
		if(organs==null) return;
		
		var form=$('#data-form')[0];
		var organid=form.organid;
		for(var i in organs) {
			var organ=organs[i];
			var opt=new Option(organ.name,organ.id);
			organid.options.add(opt);
		}
	},
	delOrgans:function() {
		var form=$('#data-form')[0];
		var organid=form.organid;
		
		for(var i=organid.options.length-1;i>=0;i--) {
			var opt=organid.options[i];
			if(opt.selected) {
				organid.options.remove(i);
			}
		}
	}
});