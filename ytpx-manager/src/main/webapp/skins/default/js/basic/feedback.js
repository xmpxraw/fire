$(function() {
	basic.feedback.init();
});
Namespace('basic.feedback',{
	init:function() {
		easyui.util.initTreegrid('#treegrid',{
			pageSize:20,
			onDblClickRow:function() {
				basic.feedback.update();
			},
			loadFilter:function(data,pid) {
				return pid?data.rows:data;
			}
		});
	},
	query:function() {
		getDialog('query-dialog').dialog({
			href:contextpath+'/basic/feedback/query',
			title: '查询意见反馈',
			width: 350,
		    height: 250,
		    buttons:[{
				text:'查询',
				iconCls:'icon-ok',
				handler:function(){
					basic.feedback.doQuery();
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
		$('#treegrid').treegrid('load',data);
		$('#query-dialog').dialog('close');
	},
	update:function() {
		
		var row = $('#treegrid').treegrid('getSelected');
		if(row==null) {
			top.showInfo('请选择需要修改的记录!');
			return;
		}
		
		getDialog('data-dialog').dialog({
			href:contextpath+'/basic/feedback/update/'+row.id,
			title: '修改意见反馈',
			width: 650,
		    height: 450,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					basic.feedback.doUpdate();
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
		
		$('#treegrid').treegrid('loading');
		var row = $('#treegrid').treegrid('getSelected');
		$.postJson(contextpath+'/basic/feedback/update/'+row.id,data,function(result) {
			$('#treegrid').treegrid('loaded');
			if(result.code==200) {
				$('#treegrid').treegrid('reload');
			} else {
				top.showInfo('修改失败!');
			}
		});
	},
	add:function() {
		getDialog('data-dialog').dialog({
			href:contextpath+'/basic/feedback/add',
			title: '添加意见反馈',
			width: 650,
		    height: 450,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					basic.feedback.doAdd();
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
	doAdd:function() {
		if(!$('#data-form').form('validate')) return;
		var data=$('#data-form').form('jsonObject');
		data.content=um.getContent();
		$('#data-dialog').dialog('close');
		
		$('#treegrid').treegrid('loading');
		$.postJson(contextpath+'/basic/feedback/add',data,function(result) {
			$('#treegrid').treegrid('loaded');
			if(result.code==200) {
				//top.showInfo('添加成功!');
				$('#treegrid').treegrid('reload');
			} else {
				top.showInfo('添加失败!');
			}
		});
	},
	remove:function() {
		var row = $('#treegrid').treegrid('getSelected');
		if(row==null) {
			top.showInfo('请选择需要删除的记录!');
			return;
		}
		
		$.messager.confirm('信息提示','确定要删除吗？',function(sure){
		    if(!sure) return;
		    $('#treegrid').treegrid('loading');
		    $.post(contextpath+'/basic/feedback/delete/'+row.id,function(result) {
				$('#treegrid').treegrid('loaded');
				if(result.code==200) {
					$('#treegrid').treegrid('reload');
				} else {
					top.showInfo('删除失败!');
				}
			},'json');
		});
	},
	sort:function() {
		getDialog('sort-dialog').dialog({
			href:contextpath+'/basic/feedback/sort',
			title: '排序',
		    width: 400,
		    height: 400,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					basic.feedback.doSort();
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
		$('#treegrid').treegrid('loading');
		var rows=$('#sort-treegrid').treegrid('getRows');
		$.postJson(contextpath+'/basic/feedback/sort',rows,function(result) {
			$('#treegrid').treegrid('loaded');
			if(result.code==200) {
				$('#treegrid').treegrid('reload');
			} else {
				top.showInfo('排序失败:'+result.msg);
			}
		});
	},
	
	/**
	 * 向上移动
	 */
	sortUp:function() {

		var row = $('#sort-treegrid').treegrid('getSelected');
		if(row==null) {
			top.showInfo('请选择一条记录!');
			return;
		}
		
		var index=$('#sort-treegrid').treegrid('getRowIndex',row);
		if(index==0) {
			top.showInfo('已经是第一条记录,不能再向上移动!');
			return;
		}
		
		var rows=$('#sort-treegrid').treegrid('getRows');
		var _id=rows[index-1].id;
		var _name=rows[index-1].name;
		rows[index-1].id=row.id;
		rows[index-1].name=row.name;
		rows[index].id=_id;
		rows[index].name=_name;
		
		$('#sort-treegrid').treegrid('loadData',rows);
		$('#sort-treegrid').treegrid('selectRow',index-1);
	},
	
	/**
	 * 向下移动
	 */
	sortDown:function() {
		var row = $('#sort-treegrid').treegrid('getSelected');
		if(row==null) {
			top.showInfo('请选择一条记录!');
			return;
		}
		
		var index=$('#sort-treegrid').treegrid('getRowIndex',row);
		var rows=$('#sort-treegrid').treegrid('getRows');
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
		
		$('#sort-treegrid').treegrid('loadData',rows);
		$('#sort-treegrid').treegrid('selectRow',index+1);
	}
});