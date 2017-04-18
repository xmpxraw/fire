$(function() {
	system.organ.init();
});
Namespace('system.organ',{
	
	TYPE_EMP:4,		//员工类型
	
	init:function() {
		$('#tree').tree({
			dnd:true,
			animate:true,
			onClick:function(node){
				$('#tree').tree('toggle', node.target);
			},
			onDblClick:function(node) {
				system.organ.update();
			},
			onSelect:function(node) {
				system.organ.onSelect(node);
			},
			onContextMenu: function(e,node) {
				system.organ.onContextMenu(e,node);
			},
			onBeforeDrop: function(target,source,point) {
				return system.organ.onBeforeDrop(target,source,point);
			},
			onDrop: function(target,source,point) {
				system.organ.onDrop(target,source,point);
			},
			onLoadSuccess:function(node,data) {
				system.organ.onLoadSuccess(node,data);
			}
		});
		easyui.util.initTabsHeaderMenu('#tabs');
		$(window).resize(function() {
			$('div.easyui-layout').layout('resize');
		});
	},

	/**
	 * 选中树节点时,执行此方法
	 * @param node
	 */
	onSelect:function(node) {
		if(node.attributes.type==1) {
			$('a[group=dir]').show();
			$('a[group=leaf]').show();
		} else {
			$('a[group=dir]').hide();
			$('a[group=leaf]').show();
		}
	},
	
	/**
	 * 在树节点上点右键时,执行此方法
	 * @param e
	 * @param node
	 */
	onContextMenu:function(e, node) {
		
		e.preventDefault();		//取消默认动作
		$('#tree').tree('select', node.target);
		
		var type=node.attributes.type;
		if(type==this.TYPE_EMP) {
			$('#tree-menu').find('div[group=dir]').hide();
		} else {
			$('#tree-menu').find('div[group=dir]').show();
		}
		
		$('#tree-menu').menu('show',{
			left: e.pageX,
			top: e.pageY
		});
	},
	
	/**
	 * 移动树节点之前,执行此方法
	 * @param target
	 * @param source
	 * @param point
	 */
	onBeforeDrop:function(target,source,point) {
		var targetNode=$('#tree').tree('getNode',target);
		if(source.attributes.type<targetNode.attributes.type){
			top.showInfo('不能移动到更低级别的节点类型下,移动失败!','error');
			return false;
		}
		if(targetNode.attributes.type==this.TYPE_EMP && (point==undefined || point=='append')) {
			top.showInfo('移动失败:人员下面不能再有其它组织!','error');
			return false;
		}
		
		var t={
			top:'上面',
			bottom:'下面',
			append:'里面',
			undefined:'里面'
		};
		var msg='确定要将"'+source.text+'"移动到"'+targetNode.text+'"'+t[point]+'吗?';
		return confirm(msg);
	},
	/**
	 * 移动树节点后,执行此方法
	 * @param target
	 * @param source
	 * @param point
	 * @returns {Boolean}
	 */
	onDrop:function(target,source,point) {
		
		var targetNode=$('#tree').tree('getNode',target);
		$.post(contextpath+'/system/organ/move',{
			targetId:targetNode.id,
			sourceId:source.id,
			point:point
		},function(result) {
			if(result.code==200) {
				if(point=='append') {
					system.organ.refreshNode(targetNode.id);
				} else {
					system.organ.refreshNode(targetNode.attributes.parentId);
				}
			} else {
				top.showInfo('移动失败!');
				system.organ.refreshNode();
			}
		},'json');
	},
	
	/**
	 * 显示根节点的菜单(根节点是个虚节点,实际不存在)
	 * @param e
	 * @returns {Boolean}
	 */
	showRootMenu:function(e) {
		$('#root-menu').menu('show',{
			left: e.pageX,
			top: e.pageY
		});
		return false;
	},
	
	/**
	 * 添加
	 */
	add:function() {
		var node=$('#tree').tree('getSelected');
		if(node==null) {
			top.showInfo('请选择一条记录!');
			return;
		}
		var url=contextpath+'/system/organ/add?parentId='+node.id;
		easyui.util.addTab('#tabs','tab_add','添加组织',url);
	},
	
	/**
	 * 添加一级组织
	 */
	addTop:function() {
		var url=contextpath+'/system/organ/add';
		easyui.util.addTab('#tabs','tab_add','添加组织',url);
	},
	
	/**
	 * 修改组织
	 */
	update:function() {
		var node=$('#tree').tree('getSelected');
		if(node==null) {
			top.showInfo('请选择一条记录!');
			return;
		}
		var url=contextpath+'/system/organ/update/'+node.id;
		easyui.util.addTab('#tabs','tab_'+node.id,node.text,url);
	},
	
	/**
	 * 删除组织
	 */
	remove:function(msg) {
		
		var node=$('#tree').tree('getSelected');
		if(node==null) {
			top.showInfo('请选择一条记录!');
			return;
		}
		
		$.messager.confirm('提示信息', '确定要删除吗?', function(sure) {
			if (!sure) return;
			$.post(contextpath+'/system/organ/delete/'+node.id,function(result) {
				if(result.code==200) {
					system.organ.refreshNode(node.attributes.parentId);
					$('#tabs').tabs('close',node.text);
				} else {
					top.showInfo('删除组织失败!');
				}
			},'json');
		});
	},
	
	/**
	 * 刷新树节点
	 * @param {Object} treeNodeId
	 */
	refreshNode:function(treeNodeId) {
		if(treeNodeId==null || treeNodeId=='') {
			$('#tree').tree('reload');
		} else {
			var node=$('#tree').tree('find',treeNodeId);
			$('#tree').tree('reload',node.target);
		}
		system.organ.hideTreeToolbar();
	},

	/**
	 * 隐藏树面板的工具栏按钮
	 */
	hideTreeToolbar:function() {
		$('a[group=dir]').hide();
		$('a[group=leaf]').hide();
	},
	
	/**
	 * 修改当前tab页的标题
	 * @param title
	 */
	updateTabTitle:function(title) {
		easyui.util.updateCurrentTabTitle('#tabs',title);
	},
	
	//====高级查询组织代码开始====
	query:function() {
		easyui.util.addTab('#tabs','tab_query','高级查询',contextpath+'/system/organ/query');
	},
	onLoadSuccess:function(node,data) {
		
		try {
			//获取selectedId属性后,便可定位到此节点
			var selectedId=data[0].attributes.selectedId;
			if(!selectedId) return;
		} catch(e) { return; }
		
		var node=$('#tree').tree('find',selectedId);
		if(node!=null) {
			$('#tree').tree('expandTo', node.target);
			$('#tree').tree('select', node.target);
		}
	},
	expandTreeNode:function(organId){
		var node=$('#tree').tree('find',organId);
		if(node!=null) {
			$('#tree').tree('expandTo', node.target);
			$('#tree').tree('select', node.target);
			return;
		}
		var treeOpts=$('#tree').tree('options');
		treeOpts.url=contextpath+'/system/organ/query?selectedId='+organId;
		$('#tree').tree('reload');
	},
	//====查询组织代码结束====
	
	//====排序代码开始====
	sort:function() {
		var node=$('#tree').tree('getSelected');
		getDialog('sort-dialog').dialog({
			href:contextpath+'/system/organ/sort?parentId='+(node.attributes.parentId||''),
			title: '排序',
		    width: 400,
		    height: 400,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					system.organ.doSort();
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
		var rows=$('#sort-datagrid').datagrid('getRows');
		$.postJson(contextpath+'/system/organ/sort',rows,function(result) {
			if(result.code==200) {
				var node=$('#tree').tree('getSelected');
				system.organ.refreshNode(node.parentId);
			} else {
				top.showInfo('排序失败:'+result.msg);
			}
		});
	},
	
	/**
	 * 向上移动
	 */
	sortUp:function() {

		var row = $('#sort-datagrid').datagrid('getSelected');
		if(row==null) {
			top.showInfo('请选择一条记录!');
			return;
		}
		var index=$('#sort-datagrid').datagrid('getRowIndex',row);
		if(index==0) {
			top.showInfo('已经是第一条记录,不能再向上移动!');
			return;
		}
		var rows=$('#sort-datagrid').datagrid('getRows');
		var _id=rows[index-1].id;
		var _name=rows[index-1].name;
		rows[index-1].id=row.id;
		rows[index-1].name=row.name;
		rows[index].id=_id;
		rows[index].name=_name;
		
		$('#sort-datagrid').datagrid('loadData',rows);
		$('#sort-datagrid').datagrid('selectRow',index-1);
	},
	
	/**
	 * 向下移动
	 */
	sortDown:function() {
		var row = $('#sort-datagrid').datagrid('getSelected');
		if(row==null) {
			top.showInfo('请选择一条记录!');
			return;
		}
		var index=$('#sort-datagrid').datagrid('getRowIndex',row);
		var rows=$('#sort-datagrid').datagrid('getRows');
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
		
		$('#sort-datagrid').datagrid('loadData',rows);
		$('#sort-datagrid').datagrid('selectRow',index+1);
	},
	//====排序代码结束
	
	//====权限设置代码开始====
	functionSetup:function() {
		var node=$('#tree').tree('getSelected');
		getDialog('function-dialog').dialog({
			href:contextpath+'/system/organ/function_setup?organId='+node.id,
			title: '权限设置',
		    width: 500,
		    height: 400,
		    buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					system.organ.doFunctionSetup();
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$('#function-dialog').dialog('close');
				}
			}]
		});
	},
	doFunctionSetup:function() {
		
		var node=$('#tree').tree('getSelected');
		var checkedFuncId=this.getFunction('checked');
		var uncheckFuncId=this.getFunction('unchecked');
		
		$.post(contextpath+'/system/organ/function_setup/'+node.id,{
			checkedFuncId:checkedFuncId,
			uncheckFuncId:uncheckFuncId
		},function(result) {
			if(result.code==200) {
				$('#function-dialog').dialog('close');
				top.showInfo('设置权限成功!');
			} else {
				top.showInfo('设置权限失败:'+result.msg);
			}
		},'json');
	},
	/**
	 * 获取功能节点
	 * @param jqtree
	 */
	getFunction:function(state) {
		var nodes=$('#function-tree').tree('getChecked',state);
		var funcId=$.map(nodes,function(node) {
			return node.id;
		});
		return funcId;
	},
	//====权限设置代码结束====
	
	//====数据绑定代码开始====
	/**
	 * 数据绑定
	 */
	dataBinding:function() {
		var node=$('#tree').tree('getSelected');
		easyui.util.addTab('#tabs','tab_data_binding','数据绑定',contextpath+'/system/organ/data_binding?organId='+node.id);
	},
	
	//====工作组管理代码开始====
	/**
	 * 打开工作组管理
	 */
	group:function() {
		easyui.util.addTab('#tabs','workgroup','工作组管理',contextpath+'/system/organ/group');
	}
});