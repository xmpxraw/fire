$(function() {
	system.menu.init();
});
Namespace('system.menu',{
	init:function() {
		$('#tree').tree({
			dnd:true,
			animate:true,
			onClick:function(node){
				$('#tree').tree('toggle', node.target);
			},
			onDblClick:function(node) {
				system.menu.update();
			},
			onSelect:function(node) {
				system.menu.onSelect(node);
			},
			onContextMenu: function(e,node) {
				system.menu.onContextMenu(e,node);
			},
			onBeforeDrop: function(target,source,point) {
				return system.menu.onBeforeDrop(target,source,point);
			},
			onDrop: function(target,source,point) {
				system.menu.onDrop(target,source,point);
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
			top.showInfo('移动菜单失败!');
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
		$.post(contextpath+'/system/menu/move',{
			targetId:targetNode.id,
			sourceId:source.id,
			point:point
		},function(result) {
			if(result.code==200) {
				if(point=='append') {
					system.menu.refreshNode(targetNode.id);
				} else {
					system.menu.refreshNode(targetNode.attributes.parentId);
				}
			} else {
				top.showInfo('移动菜单失败!');
				system.menu.refreshNode();
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
		var url=contextpath+'/system/menu/add?parentId='+node.id;
		easyui.util.addTab('#tabs','tab_add','添加菜单',url);
	},
	
	/**
	 * 添加一级菜单
	 */
	addTop:function() {
		var url=contextpath+'/system/menu/add';
		easyui.util.addTab('#tabs','tab_add','添加菜单',url);
	},
	
	/**
	 * 修改菜单
	 */
	update:function() {
		var node=$('#tree').tree('getSelected');
		if(node==null) {
			top.showInfo('请选择一条记录!');
			return;
		}
		var url=contextpath+'/system/menu/update/'+node.id;
		easyui.util.addTab('#tabs','tab_'+node.id,'菜单：'+node.text,url);
	},
	
	/**
	 * 删除菜单
	 */
	remove:function(msg) {
		
		var node=$('#tree').tree('getSelected');
		if(node==null) {
			top.showInfo('请选择一条记录!');
			return;
		}
		
		$.messager.confirm('提示信息', '确定要删除吗?', function(sure) {
			if (!sure) return;
			$.post(contextpath+'/system/menu/delete/'+node.id,function(result) {
				if(result.code==200) {
					system.menu.refreshNode(node.attributes.parentId);
					$('#tabs').tabs('close','菜单：'+node.text);
				} else {
					top.showInfo('删除菜单失败!');
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
		system.menu.hideTreeToolbar();
	},

	/**
	 * 隐藏树面板的工具栏按钮
	 */
	hideTreeToolbar:function() {
		$('a[group=dir]').hide();
		$('a[group=leaf]').hide();
	},
	
	/**
	 * 刷新菜单缓存
	 */
	refreshCache:function() {
		$.messager.confirm('提示信息', '确定要刷新菜单缓存吗?', function(sure) {
			if (!sure) return;
			
			$.messager.progress();
			$.post(contextpath+'/system/menu/refresh_cache',function(result) {
				$.messager.progress('close');
				if(result.code==200) {
					top.showInfo('刷新菜单缓存成功!');
				} else {
					top.showInfo('刷新菜单缓存失败!');
				}
			},'json');
		});
	},
	
	/**
	 * 修改当前tab页的标题
	 * @param title
	 */
	updateTabTitle:function(title) {
		easyui.util.updateCurrentTabTitle('#tabs',title);
	}
});