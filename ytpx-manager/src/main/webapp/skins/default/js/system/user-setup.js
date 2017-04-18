Namespace('system.user',{
	
	/**
	 * 个人设置
	 */
	setup:function() {
		getDialog('setup-dialog').dialog({
			href:contextpath+'/system/user_setup',
		    title: '个人设置',
		    width: 600,
		    height: 320,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					system.user.doSetup();
				}
			},{
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function(){
					$('#setup-dialog').dialog('close');
				}
			}]
		});
	},
	
	doSetup:function() {
		var tab=$('#setup-tabs').tabs('getSelected');
		var tabId=tab.attr('id');
		if(tabId=='setup-basic') {
			system.user.setupUser();
		} else if(tabId=='setup-password') {
			system.user.setupPassword();
		} else if(tabId=='setup-main-emp') {
			system.user.setupMainEmployee();
		}
	},
	
	/**
	 * 修改基本资料
	 */
	setupUser:function() {
		var valid=$('#basic-form').form('validate');
		if(!valid) return;
		
		var user={
			nickname:$('#basic-form')[0].nickname.value,
			remark:$('#basic-form')[0].remark.value,
			mobile:$('#basic-form')[0].mobile.value
		};
		$.post(contextpath+'/system/user_setup/basic',user,function(result) {
			if(result.code==200) {
				top.showInfo('修改基本资料成功!');
			} else {
				top.showInfo('修改基本资料失败!');
			}
		});
	},
	
	/**
	 * 修改个人密码
	 */
	setupPassword:function() {
		var valid=$('#password-form').form('validate');
		if(!valid) return;
		
		var oldpwd=$('#password-form')[0].oldpwd.value;
		var newpwd=$('#password-form')[0].newpwd.value;
		$.post(contextpath+'/system/user_setup/password',{oldpwd:oldpwd,newpwd:newpwd},function(result) {
			if(result.code==200) {
				$('#password-form')[0].reset();
				top.showInfo('修改密码成功!');
			} else {
				top.showInfo('修改密码失败:'+result.msg);
			}
		});
	},
	
	/**
	 * 请选择主要身份
	 */
	setupMainEmployee:function() {
		var row = $('#emp-datagrid').datagrid('getSelected');
		if(row==null) {
			top.showInfo('请选择主要身份!');
			return;
		}
		
		$.post(contextpath+'/system/user_setup/emp',{empId:row.id},function(result) {
			if(result.code==200) {
				top.showInfo('切换身份成功!');
			} else {
				top.showInfo('切换身份失败!');
			}
		});
	}
});