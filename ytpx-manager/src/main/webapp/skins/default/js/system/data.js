$(function() {
	system.data.init();
});
Namespace('system.data',{
	init:function() {
		$('#tree').tree({
			dnd:true,
			animate:true,
			onClick:function(node){
				$('#tree').tree('toggle', node.target);
			},
			onDblClick:function(node) {
				system.data.update();
			},
			onSelect:function(node) {
				system.data.onSelect(node);
			},
			onContextMenu: function(e,node) {
				system.data.onContextMenu(e,node);
			},
			onBeforeDrop: function(target,source,point) {
				return system.data.onBeforeDrop(target,source,point);
			},
			onDrop: function(target,source,point) {
				system.data.onDrop(target,source,point);
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
		if(type==1) {		//如果是目录,那么启用'添加'和'排序'按钮,否则禁用
			$('#tree-menu').find('div[group=dir]').show();
		} else {
			$('#tree-menu').find('div[group=dir]').hide();
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
		if(targetNode.attributes.type==2 && (point==undefined || point=='append')) {
			top.showInfo('移动基础数据失败!');
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
		$.post(contextpath+'/system/data/move',{
			targetId:targetNode.id,
			sourceId:source.id,
			point:point
		},function(result) {
			if(result.code==200) {
				if(point=='append') {
					system.data.refreshNode(targetNode.id);
				} else {
					system.data.refreshNode(targetNode.attributes.parentId);
				}
			} else {
				top.showInfo('移动基础数据失败!');
				system.data.refreshNode();
			}
		},'json');
	},
	
	/**
	 * 显示根节点的基础数据(根节点是个虚节点,实际不存在)
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
		var url=contextpath+'/system/data/add?parentId='+node.id;
		easyui.util.addTab('#tabs','tab_add','添加数据',url);
	},
	
	/**
	 * 添加一级基础数据
	 */
	addTop:function() {
		var url=contextpath+'/system/data/add';
		easyui.util.addTab('#tabs','tab_add','添加数据',url);
	},
	
	/**
	 * 修改基础数据
	 */
	update:function() {
		var node=$('#tree').tree('getSelected');
		if(node==null) {
			top.showInfo('请选择一条记录!');
			return;
		}
		var url=contextpath+'/system/data/update/'+node.id;
		easyui.util.addTab('#tabs','tab_'+node.id,'数据：'+node.text,url);
	},
	
	/**
	 * 删除基础数据
	 */
	remove:function(msg) {
		
		var node=$('#tree').tree('getSelected');
		if(node==null) {
			top.showInfo('请选择一条记录!');
			return;
		}
		
		$.messager.confirm('提示信息', '确定要删除吗?', function(sure) {
			if (!sure) return;
			$.post(contextpath+'/system/data/delete/'+node.id,function(result) {
				if(result.code==200) {
					system.data.refreshNode(node.attributes.parentId);
					$('#tabs').tabs('close','数据：'+node.text);
				} else {
					top.showInfo('删除基础数据失败!');
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
		system.data.hideTreeToolbar();
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
	}
});