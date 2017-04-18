$(function() {
	/*school.classes.changeClassType();*/
	school.classes.init();
});
Namespace('school.classes',{
	TYPE_EMP:1,		//班级类型
	init:function() {
		easyui.util.initDatagrid('#datagrid',{
		/*	onDblClickRow:function() {
				school.classes.update();
			}*/
		});
	},
	query:function() {
		getDialog('query-dialog').dialog({
			href:contextpath+'/school/classes/query',
			title: '查询',
		    width: 350,
		    height: 250,
		    buttons:[{
				text:'查询',
				iconCls:'icon-ok',
				handler:function(){
					school.classes.doQuery();
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
	/**
	 * 当改变类型时,执行此方法
	 * @param type
	 */
	changeClassType:function(){
		var classType=$('#data-form')[0].classType.value;

		if(classType==this.TYPE_EMP){
		/*	$('#data-form tr.hidden').show();	*/
		} else {
		/*	$('#data-form tr.hidden').hide();*/
			$('input[id=beginTime]').removeAttr("readonly")
		}
	},
	changeClassesType:function(){
		var classType=$('#add-form')[0].classType.value;

		if(classType==this.TYPE_EMP){
			/*$('#add-form tr.hidden').show();		*/
		//	$('input[name=beginTime]').removeClass("easyui-datebox")
			$('input[name=beginTime]').attr("readonly","readonly")
			$('input[name=period]').attr("readonly","readonly")
			$('input[id=period]').attr("readonly","readonly")
		} else {
			/*$('#add-form tr.hidden').hide();*/
			$('input[id=period]').removeAttr("readonly")
	//		$('input[name=beginTime]').addClass("easyui-datebox")
			$('input[name=beginTime]').removeAttr("readonly")
		
		}
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
			href:contextpath+'/school/classes/update/'+row.id,
			title: '修改学校信息',
			   width: 430,
			    height: 430,
			    resizable:true,
			    maximizable:true,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					school.classes.doUpdate();
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
		if(!$('#data-form').form('validate')) return;
		var data=$('#data-form').form('jsonObject');
		$('#update-dialog').dialog('close');
		
		$('#datagrid').datagrid('loading');
		var row = $('#datagrid').datagrid('getSelected');
		$.postJson(contextpath+'/school/classes/update/'+row.id,data,function(result) {
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
			href:contextpath+'/school/classes/add',
			title: '添加班级',
		    width: 430,
		    height: 430,
		    resizable:true,
		    maximizable:true,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					school.classes.doAdd();
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
		$.postJson(contextpath+'/school/classes/add',data,function(result) {
			$('#datagrid').datagrid('loaded');
			if(result.code==200) {
				//top.showInfo('添加成功!');
				$('#datagrid').datagrid('reload');
			} else {
				top.showInfo('添加失败!');
			}
		});
	},
	viewStudent:function() {
		var row = $('#datagrid').datagrid('getSelected');
		top.addTab('','查看学员',contextpath+'/school/student/viewStudent/'+row.id);
	},
	importScore:function() {
		var row = $('#datagrid').datagrid('getSelected');
		if(row==null) {
			top.showInfo('请选择需要的记录!');
			return;
		}
		getDialog('add-dialog').dialog({
			href:contextpath+'/school/classes/importScore/'+row.id,
			title: '录入成绩',
		    width: 320,
		    height: 240,
		    resizable:true,
		    maximizable:true,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					school.classes.doImportScore();
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
	doImportScore:function() {
		if(!$('#add-form').form('validate')) return;
		$('#datagrid').datagrid('loading');
		var row = $('#datagrid').datagrid('getSelected');
		$.messager.progress();
		$('#add-form').ajaxSubmit({
			url:contextpath+'/school/classes/importScore/'+row.id,
			dataType : 'json',
			success : function(msg) {
				$.messager.progress('close');
				if (msg.code == 200) {
					$('#add-dialog').dialog('close');
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
	},
	
	exportTemplet:function() {
		var row = $('#datagrid').datagrid('getSelected');
		 window.location.href=contextpath+'/school/classes/exportTemplet/'+row.id;
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
		    $.post(contextpath+'/school/classes/delete/'+row.id,function(result) {
				$('#datagrid').datagrid('loaded');
				if(result.code==200) {
					top.showInfo('删除成功!');
					$('#datagrid').datagrid('reload');
				}else if(result.code==-1) {
					top.showInfo('该班级已经有学员报班，无法删除，请先删除学员信息，再执行此操作');
				} else {
					top.showInfo('删除失败!');
				}
			},'json');
		});
	},
	notify:function() {
		var row = $('#datagrid').datagrid('getSelected');
		if(row==null) {
			top.showInfo('请选择需要的记录!');
			return;
		}
		
		$.messager.confirm('信息提示','确定要发送通知吗？',function(sure){
		    if(!sure) return;
		    $('#datagrid').datagrid('loading');
		    $.post(contextpath+'/school/classes/notify/'+row.id,function(result) {
				$('#datagrid').datagrid('loaded');
				if(result.code==200) {
					$('#datagrid').datagrid('reload');
				} else {
					top.showInfo('发送通知失败!');
				}
			},'json');
		});
	}
	
});