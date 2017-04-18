$(function() {
	school.school.init();
});
Namespace('school.school',{
	init:function() {
		easyui.util.initDatagrid('#datagrid',{
			pageSize:20,
			onDblClickRow:function() {
				school.school.update();
			}
		});
	},
	query:function() {
		getDialog('query-dialog').dialog({
			href:contextpath+'/school/school/query',
			title: '查询学校',
			width: 450,
		    height: 250,
		    buttons:[{
				text:'查询',
				iconCls:'icon-ok',
				handler:function(){
					school.school.doQuery();
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
			href:contextpath+'/school/school/update/'+row.id,
			title: '修改学校',
			width: 750,
		    height: 450,
		    resizable:true,
		    maximizable:true,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					school.school.doUpdate();
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$('#data-dialog').dialog('close');
				}
			}],
			onClose:function() {
				UM.getEditor('content').destroy();
			}
		});
	},
	doUpdate:function() {
		if(!$('#data-form').form('validate')) return;
		var data=$('#data-form').form('jsonObject');
		data.content=um.getContent();
		$('#data-dialog').dialog('close');
		
		$('#datagrid').datagrid('loading');
		var row = $('#datagrid').datagrid('getSelected');
		$.messager.progress();
		$('#data-form').ajaxSubmit({
			url:contextpath+'/school/school/update/'+row.id,
			dataType : 'json',
			success : function(msg) {
				$.messager.progress('close');
				if (msg.code == 200) {
					$('#data-dialog').dialog('close');
					$('#datagrid').datagrid('reload');
				} else {
					showInfo(msg.msg);
				}
			},
			error : function(xhr,status,e) {
				console.log(status+' '+e);
				$.messager.progress('close');
				showInfo(msg.msg);
			}
		});
		/*$.postJson(contextpath+'/school/school/update/'+row.id,data,function(result) {
			$('#datagrid').datagrid('loaded');
			if(result.code==200) {
				$('#datagrid').datagrid('reload');
			} else {
				top.showInfo('修改失败!');
			}
		});*/
	},
	add:function() {
		getDialog('data-dialog').dialog({
			href:contextpath+'/school/school/add',
			title: '添加学校',
			width: 750,
		    height: 450,
		    resizable:true,
		    maximizable:true,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					school.school.doAdd();
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$('#data-dialog').dialog('close');
				}
			}],
			onClose:function() {
				UM.getEditor('content').destroy();
			}
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
	check:function(id) {
		var valid=false;
		$.ajax({
			url:contextpath+'/school/school/check_status',
			type:'POST',
			data:{id:id},
			dataType:'json',
			async:false,
			success:function(result) {
				if(result.code==2||result.code==1) {
					valid=false;
				} else {
					valid=true;
				}
			}
		});
		return valid;
	},
	doAdd:function() {
		if(!$('#data-form').form('validate')) return;
		var data=$('#data-form').form('jsonObject');
		data.content=um.getContent();
		$('#data-dialog').dialog('close');
		
		$('#datagrid').datagrid('loading');
		$.messager.progress();
		$('#data-form').ajaxSubmit({
			url:contextpath+'/school/school/add',
			dataType : 'json',
			success : function(msg) {
				$.messager.progress('close');
				if (msg.code == 200) {
					$('#data-dialog').dialog('close');
					$('#datagrid').datagrid('reload');
				} else {
					showInfo(msg.msg);
				}
			},
			error : function(xhr,status,e) {
				console.log(status+' '+e);
				$.messager.progress('close');
				showInfo(msg.msg);
			}
		});
		/*$.postJson(contextpath+'/school/school/add',data,function(result) {
			$('#datagrid').datagrid('loaded');
			if(result.code==200) {
				//top.showInfo('添加成功!');
				$('#datagrid').datagrid('reload');
			} else {
				top.showInfo('添加失败!');
			}
		});*/
	},
	remove:function() {
		var row = $('#datagrid').datagrid('getSelected');
		if(row==null) {
			top.showInfo('请选择需要删除的记录!');
			return;
		}
		
		$.messager.confirm('信息提示','确定要删除吗？',function(sure){
		    if(!sure) return;
		    var online=school.school.check(row.id); 
		    if(!online) {
		    	top.showInfo('已使用、不能删除!');
		    	return;
		    }   
		    $('#datagrid').datagrid('loading');
		    $.post(contextpath+'/school/school/delete/'+row.id,function(result) {
				$('#datagrid').datagrid('loaded');
				if(result.code==200) {
					$('#datagrid').datagrid('reload');
				} else {
					top.showInfo('删除失败!');
				}
			},'json');
		});
	},
	checkOnline:function(id) {
		var valid=false;
		$.ajax({
			url:contextpath+'/school/school/check_status',
			type:'POST',
			data:{id:id},
			dataType:'json',
			async:false,
			success:function(result) {
				if(result.code==2) {
					valid=true;
				} else {
					valid=false;
				}
			}
		});
		return valid;
	},
	checkOffline:function(id) {
		var valid=false;
		$.ajax({
			url:contextpath+'/school/school/check_status',
			type:'POST',
			data:{id:id},
			dataType:'json',
			async:false,
			success:function(result) {
				if(result.code==1) {
					valid=true;
				} else {
					valid=false;
				}
			}
		});
		return valid;
	},
	offline:function() {
		var row = $('#datagrid').datagrid('getSelected');
		if(row==null) {
			top.showInfo('请选择需要的记录!');
			return;
		}
		
		$.messager.confirm('信息提示','确定要下线吗？',function(sure){
		    if(!sure) return;
		    var online=school.school.checkOffline(row.id); 
		    if(online) {
		    	top.showInfo('已下线');
		    	return;
		    }  
		    $('#datagrid').datagrid('loading');
		    var status=1;
		    $.post(contextpath+'/school/school/offline/'+row.id,status,function(result) {
				$('#datagrid').datagrid('loaded');
				if(result.code==200) {
					$('#datagrid').datagrid('reload');
				} else {
					top.showInfo('下线失败!');
				}
			},'json');
		});
	},
	online:function() {
		var row = $('#datagrid').datagrid('getSelected');
		if(row==null) {
			top.showInfo('请选择需要的记录!');
			return;
		}
		
		$.messager.confirm('信息提示','确定要上线吗？',function(sure){
		    if(!sure) return;
		    var online=school.school.checkOnline(row.id); 
		    if(online) {
		    	top.showInfo('已上线');
		    	return;
		    }   
		    
		    $('#datagrid').datagrid('loading');
		    var status=2;
		    $.post(contextpath+'/school/school/online/'+row.id,status,function(result) {
				$('#datagrid').datagrid('loaded');
				if(result.code==200) {
					$('#datagrid').datagrid('reload');
				} else {
					top.showInfo('上线失败!');
				}
			},'json');
		});
	}
});