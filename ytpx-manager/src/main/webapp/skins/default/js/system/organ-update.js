$(function() {
	system.organ.changeOrganType();
});
Namespace('system.organ',{
	TYPE_EMP:4,		//员工类型
	doAdd:function() {
		if(!$('#data-form').form('validate')) return;
		var data=$('#data-form').form('jsonObject');
		$.post(contextpath+'/system/organ/add',data,function(result) {
			if(result.code==200) {
				parent.system.organ.refreshNode(data.parentId);
				$('#data-form')[0].reset();
			} else {
				top.showInfo('添加组织失败:'+result.msg);
			}
		},'json');
	},
	doUpdate:function() {
		if(!$('#data-form').form('validate')) return;
		var data=$('#data-form').form('jsonObject');
		$.post(contextpath+'/system/organ/update/'+data.id,data,function(result) {
			if(result.code==200) {
				parent.system.organ.refreshNode(data.parentId);
				parent.system.organ.updateTabTitle(data.name);
			} else {
				top.showInfo('修改组织失败:'+result.msg);
			}
		},'json');
	},
	
	/**
	 * 当改变类型时,执行此方法
	 * @param type
	 */
	changeOrganType:function(){
		var type=$('#data-form')[0].type.value;
		if(type==this.TYPE_EMP){
			$('#data-form tr.hidden').show();
		} else {
			$('#data-form tr.hidden').hide();
		}
	},
	
	//选择用户功能开始=================
	selectUser:function(){
		getDialog('query-user-dialog').dialog({
			href:contextpath+'/system/organ/select_user',
			title: '选择用户',
			width: 720,   
		    height: 450,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					system.organ.doSelectUser();
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$('#query-user-dialog').dialog('close');
				}
			}]
		});
	},
	doSelectUser:function(){
		var row = $('#query-user-datagrid').datagrid('getSelected');
		if(row==null) {
			top.showInfo('请选择一个用户!');
			return;
		}
		var form=$('#data-form')[0];
		form.userId.value=row.id;
		form.userName.value=row.username;
		$('#query-user-dialog').dialog('close');
	},
	queryUser:function(){
		var data=$('#query-user-form').form('jsonObject');
		$('#query-user-datagrid').datagrid('load',data);
	},
	//选择用户功能结束=================
	
	selectPrincipal:function() {
		var principal=system.organ.selectOrgans({checkbox:0,enterprice:false,dept:false,emp:true});
		if(!principal) return;
		$('#principalId').val(principal[0].id);
		$('#principalName').val(principal[0].name);
	}
});