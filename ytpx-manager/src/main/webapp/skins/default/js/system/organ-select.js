
Namespace('system.organ',{
	
	/**
	 * 选择组织机构|人员|角色
	 * @param checkbox		1:多选,0:单选  默认:1
	 * @returns
	 */
	selectOrgans:function(options){
		
		var _options={};
		if(options!=null) {
			if(typeof options=='number') _options.checkbox=options;
			if(typeof options=='object') _options=options;
		}
		
		var url = contextpath+'/system/organ/select';
		var value = window.showModalDialog(url,_options,'dialogTop=70px; dialogWidth=1000px; dialogHeight=550px; help:no; scroll:no');
		return value;
	}
});

Namespace('system.organ.select',{
	
	_options:{
		checkbox:1,
		role:false,			//是否角色可选,默认不选
		enterprice:true,	//是否企业可选,默认可选
		dept:true,			//是否部门可选,默认可选
		team:true,			//是否科室可选,默认可选
		emp:true,			//是否人员可选,默认可选
		win:window
	},
	organType:['','enterprice','dept','team','emp'],
	
	init:function(options) {
		
		$.extend(this._options, options);
		//$('#query-form')[0].name.value=JSON.stringify(window.dialogArguments);
		
		//初始化窗口
		$('#win-dialog').dialog({
			buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function(){
					var candidates=system.organ.select.getCandidates();
					if(candidates==null || candidates.length==0) {
						alert('无选择记录,请先选择!');
						return;
					}
					window.returnValue=candidates;
					window.close();
				}
			},{
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function(){
					window.close();
				}
			}]
		});
		
		//初始化组织树
		$('#organ-tree').tree({
			checkbox: false,
			dnd:false,
			animate:true,
			url: contextpath+'/system/organ/select/organ_tree',
			onClick:function(node){
				system.organ.select.query('organ',node.id);
				if(node.attributes.parentId!=null) {
					$(this).tree('toggle', node.target);
				}
			}
		});
		
		//初始化角色树
		$('#role-tree').tree({
			checkbox: system.organ.select._options.role,
			dnd:false,
			animate:true,
			url: contextpath+'/system/organ/select/role_tree',
			onClick:function(node){
				system.organ.select.query('role',node.id);
			},
			onCheck:function(node,checked) {
				if(checked) {
					system.organ.select.addCandidate({
						type:'role',
						id:node.id,
						name:node.text,
						role:node
					});
				} else {
					system.organ.select.removeCandidate({
						type:'role',
						id:node.id
					});
				}
			}
		});
		
		//初始化人员列表
		$('#datagrid').datagrid({
			url:contextpath+'/system/organ/select/query',
			onCheck:function(rowIndex,rowData) {
				system.organ.select.addCandidate({
					type:system.organ.select.organType[rowData.type],
					id:rowData.id,
					name:rowData.name,
					organ:rowData
				});
			},
			onUncheck:function(rowIndex,rowData) {
				system.organ.select.removeCandidate({
					type:system.organ.select.organType[rowData.type],
					id:rowData.id
				});
			},
			onLoadSuccess:function(data) {
				
				var candidates=$('#candidate-datagrid').datagrid('getRows');
				if(data.total==0 || candidates==null || candidates.length==0) return;
				
				for(var i in candidates) {
					if(candidates[i].type=='role') continue;
					for(var j in data.rows) {
						if(candidates[i].id==data.rows[j].id) {
							$('#datagrid').datagrid('checkRow',j);
							break;
						}
					}
				}
			}
		});
		
		//初始化候选人列表
		$('#candidate-datagrid').datagrid({
			onDblClickRow:function(rowIndex,rowData) {
				system.organ.select.removeCandidate(rowData);
			},
			onSelect:function() {
				$('a[group=single]').show();
			},
			onUnselect:function() {
				system.organ.select.hideCandidateToolbar();
			},
			onBeforeLoad:function() {
				$('a[group=single]').hide();
			}
		});
	},
	
	/**
	 * 查询组织
	 * @param queryType
	 * @param id
	 */
	query:function(queryType,id) {
		var param=id?{queryType:queryType,id:id}:$('#query-form').form('jsonObject');
		$('#datagrid').datagrid('load',param);
	},
	
	/**
	 * 添加候选人
	 * @param candidate
	 */
	addCandidate:function(candidate) {
		var $dg=$('#candidate-datagrid');
		var rows=$dg.datagrid('getRows');
		
		for(var i in rows) {
			if(rows[i].type==candidate.type && rows[i].id==candidate.id) {
				return;
			}
		}
		
		if(!this._options[candidate.type]) {
			alert('不能选择此候选人!');
			window.setTimeout(function() {
				system.organ.select._uncheckCandidate(candidate);
			},50);
			return;
		}
		
		//如果是单选,那么验证只能选择一个候选人
		if(this._options.checkbox==0 && rows.length>0) {
			alert('只能选择一个候选人!');
			window.setTimeout(function() {
				system.organ.select._uncheckCandidate(candidate);
			},50);
			return;
		}
		
		$dg.datagrid('appendRow',candidate);
	},
	
	/**
	 * 删除候选人
	 * @param candidate
	 */
	removeCandidate:function(candidate) {
		
		var $dg=$('#candidate-datagrid');
		
		if(candidate==null) {	//从工具栏上的删除按钮,执行删除候选人
			var rows=$dg.datagrid('getSelections');
			if(rows==null || rows.length==0) {
				alert('请选择需要删除的记录!');
				return;
			}
			for(var i in rows) {
				this._removeCandidate(rows[i]);
			}
		} else if(typeof candidate=='object') {	//从人员列表,取消选择人员时,删除候选人
			var rows=$dg.datagrid('getRows');
			for(var i in rows) {
				if(rows[i].type==candidate.type && rows[i].id==candidate.id) {
					this._removeCandidate(rows[i]);
					break;
				}
			}
		}
		system.organ.select.hideCandidateToolbar();
	},
	
	/**
	 * 删除候选人
	 */
	_removeCandidate:function(row) {
		if(row==null) return;
		var $dg=$('#candidate-datagrid');
		var index=$dg.datagrid('getRowIndex',row);
		$dg.datagrid('deleteRow',index);
		
		var rows=$dg.datagrid('getRows');
		$dg.datagrid('loadData',rows);
		
		this._uncheckCandidate(row);
	},
	
	/**
	 * 将左边选中的候选人设置为'未选'
	 * @param row
	 */
	_uncheckCandidate:function(row) {
		
		if(row.type=='role') {
			var node=$('#role-tree').tree('find',row.id);
			$('#role-tree').tree('uncheck',node.target);
		} else {
			var organs=$('#datagrid').datagrid('getRows');
			for(var i in organs) {
				if(organs[i].id==row.id) {
					$('#datagrid').datagrid('uncheckRow',i);
					break;
				}
			}
		}
	},
	
	/**
	 * 隐藏候选人工具栏按钮
	 */
	hideCandidateToolbar:function() {
		var rows=$('#candidate-datagrid').datagrid('getSelections');
		if(rows==null || rows.length==0) {
			$('a[group=single]').hide();
		}
	},
	
	/**
	 * 获取候选人
	 */
	getCandidates:function() {
		var rows=$('#candidate-datagrid').datagrid('getRows');
		return rows;
	}
});