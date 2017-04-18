$(function() {
	system.user.init();
});
Namespace('system.user',{
	init:function() {
		easyui.util.initDatagrid('#datagrid',{
			onDblClickRow:function() {
				system.user.update();
			}
		});
	},
	query:function() {
		getDialog('query-dialog').dialog({
			href:contextpath+'/system/user/query',
			title: '查询',
		    width: 350,
		    height: 250,
		    buttons:[{
				text:'查询',
				iconCls:'icon-ok',
				handler:function(){
					system.user.doQuery();
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
		
		getDialog('update-dialog').dialog({
			href:contextpath+'/system/user/update/'+row.id,
			title: '修改用户',
		    width: 500,
		    height: 320,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					system.user.doUpdate();
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$('#update-dialog').dialog('close');
				}
			}]
		});
	},
	doUpdate:function() {
		if(!$('#update-form').form('validate')) return;
		var data=$('#update-form').form('jsonObject');
		$('#update-dialog').dialog('close');
		
		$('#datagrid').datagrid('loading');
		var row = $('#datagrid').datagrid('getSelected');
		system.user.setCheckedRole(data,'#update-role-tree');
		$.postJson(contextpath+'/system/user/update/'+row.id,data,function(result) {
			$('#datagrid').datagrid('loaded');
			if(result.code==200) {
				//top.showInfo('修改成功!');
				$('#datagrid').datagrid('reload');
			} else {
				top.showInfo('修改失败!');
			}
		});
	},
	add:function() {
		getDialog('add-dialog').dialog({
			href:contextpath+'/system/user/add',
			title: '添加用户',
		    width: 500,
		    height: 320,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					system.user.doAdd();
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
	doAdd:function() {
		if(!$('#add-form').form('validate')) return;
		var data=$('#add-form').form('jsonObject');
		$('#add-dialog').dialog('close');
		
		$('#datagrid').datagrid('loading');
		system.user.setCheckedRole(data,'#add-role-tree');
		$.postJson(contextpath+'/system/user/add',data,function(result) {
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
		if(row==null) {
			top.showInfo('请选择需要删除的记录!');
			return;
		}
		
		$.messager.confirm('信息提示','确定要删除吗？',function(sure){
		    if(!sure) return;
		    $('#datagrid').datagrid('loading');
		    $.post(contextpath+'/system/user/delete/'+row.id,function(result) {
				$('#datagrid').datagrid('loaded');
				if(result.code==200) {
					//top.showInfo('删除成功!');
					$('#datagrid').datagrid('reload');
				} else {
					top.showInfo('删除失败!');
				}
			},'json');
		});
	},
	checkUser:function(username) {
		var valid=false;
		$.ajax({
			url:contextpath+'/system/user/check_user',
			type:'POST',
			data:{username:username},
			dataType:'json',
			async:false,
			success:function(result) {
				if(result.code==-1) {
					valid=false;
				} else {
					valid=true;
				}
			}
		});
		return valid;
	},
	selectTab:function(title,roletree) {
		if(title!='分配角色') return;
		if($(roletree).data('loaded')) return;	//如果已经加载了角色树,那么无需再加载
	
		$(roletree).tree({
			checkbox: true,
			animate:true,
			onClick:function(node){
				if($(node.target).find('.tree-checkbox0').size()>0) {
					$(this).tree('check', node.target);
				} else {
					$(this).tree('uncheck', node.target);
				}
			},
			onLoadSuccess:function() {
				$(roletree).data('loaded',true);
			}
		});
	},
	
	/**
	 * 设置已选择的角色
	 */
	setCheckedRole:function(user,jqtree) {
		if($(jqtree).data('loaded')) {
			var checkedRoleId=[];
			var checkedNodes=$(jqtree).tree('getChecked');
			for(var i in checkedNodes) {
				checkedRoleId.push(checkedNodes[i].id);
			}
			user.checkedRoleId=checkedRoleId;
		} else {
			user.checkedRoleId=null;
		}
	},
	
	updatePassword:function() {
		var row = $('#datagrid').datagrid('getSelected');
		if(row==null) {
			top.showInfo('请选择需要修改密码的记录!');
			return;
		}
		
		getDialog('password-dialog').dialog({
			href:contextpath+'/system/user/password/'+row.id,
			title: '修改密码',
		    width: 350,
		    height: 250,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					system.user.doUpdatePassword();
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$('#password-dialog').dialog('close');
				}
			}]
		});
	},
	doUpdatePassword:function() {
		if(!$('#password-form').form('validate')) return;
		var data=$('#password-form').form('jsonObject');
		$('#password-dialog').dialog('close');
		
		$('#datagrid').datagrid('loading');
		var row = $('#datagrid').datagrid('getSelected');
		$.post(contextpath+'/system/user/password/'+row.id,data,function(result) {
			$('#datagrid').datagrid('loaded');
			if(result.code==200) {
				top.showInfo('修改密码成功!');
			} else {
				top.showInfo('修改密码失败:'+result.msg);
			}
		},'json');
	},
	login:function() {
		if(!confirm('确定要以此用户登录吗?')) return;
		var row = $('#datagrid').datagrid('getSelected');
		top.location.href=contextpath+'/system/user/login/'+row.id;
	}
});