/**
 * 系统初始化
 */
var TAB_HOMEPAGE_ID='homepage';		//首页的tab id
var TAB_HOMEPAGE_TITLE='首页';		//首页的tab title
var HOMEPAGE_URL=contextpath+'/admin/main';		//首页url

$(function() {
	addHomepageTab();	//添加首页
});

function resetMenu(menuId,name,a) {
	$('.nav a').removeClass('selected');
	$(a).addClass('selected');
	
	if(name=='首页') {
		refreshHomepageTab(true);
	} else if(name=='统计图表') {
		addTab('chart',name, contextpath+'/admin/chart');
	} else if(name=='常用工具') {
		addTab('tools',name, contextpath+'/admin/tools');
	} else {
		
		var panel=$('#leftmenu').accordion('getPanel',0);
		panel.panel('setTitle',name);
		
		$('#menu-tree').tree({
			url: contextpath+'/admin/leftmenu/'+menuId,
			animate: true,
			onClick: openTab,
			onContextMenu: function(e,node){
				e.preventDefault();
				$(this).tree('select',node.target);
			}
		});
	}
}

/**
 * 选择抽屉菜单时,执行此方法
 * @param title		菜单名
 * @param index		菜单索引
 */
function selectAccordion(title,index) {
	var $panel=$(this).accordion('getPanel', index);
	var menuId=$panel.attr('menu-id');
	var $tree=$('#menu-tree-'+menuId);
	if($tree.data('loaded')) return;
	
	$tree.tree({
		url: contextpath+'/admin/leftmenu/'+menuId,
		animate: true,
		onClick: openTab,
		onContextMenu: function(e,node){
			e.preventDefault();
			$(this).tree('select',node.target);
		}
	});
	$tree.data('loaded',true);
}

/**
 * 在tabpanel在头部点击右键时,弹出右键菜单
 */
function tabpanelContextMenu(e,title) {
	e.preventDefault();
	e.stopPropagation();
	if(title==TAB_HOMEPAGE_TITLE) return false;
	
	$('#tabpanel').data('title',title);
	$('#tabpanel-menu').menu('show',{
		left: e.pageX,
		top: e.pageY
	});
	return false;
}

/**
 * 从左边菜单,打开tab页
 */
function openTab() {
	var node=$(this).tree('getSelected');
	if(node.attributes.type==1) {		//如果是目录节点,那么扩展/收缩子节点,且不打开tab页
		$(this).tree('toggle',node.target);
		return;		
	}
	var url=contextpath+node.attributes.url;
	addTab('tab-'+node.id,node.text,url);
}

/**
 * 添加tab页
 */
function addTab(id,title, url, closedCallback){
	easyui.util.addTab('#tabpanel',id,title, url, closedCallback);
}

/**
 * 添加首页tab
 */
function addHomepageTab() {
	var content='<iframe src="'+HOMEPAGE_URL+'" frameborder="0" scrolling="auto" style="width:100%;height:100%"></iframe>';
	$('#tabpanel').tabs('add',{
		id:TAB_HOMEPAGE_ID,
		title:TAB_HOMEPAGE_TITLE,
		content:content,
		iconCls:'icon-home',
		closable:false
	});
}

/**
 * 刷新首页
 * @param {Object} selected 是否选中(激活)首页
 */
function refreshHomepageTab(selected) {

	if(selected) {	
		$('#tabpanel').tabs('select',TAB_HOMEPAGE_TITLE);
	}
	
	var tab = $('#tabpanel').tabs('getTab',TAB_HOMEPAGE_TITLE);
	var content='<iframe src="'+HOMEPAGE_URL+'" frameborder="0" scrolling="auto" style="width:100%;height:100%"></iframe>';
	$('#tabpanel').tabs('update', {
		tab: tab,
		options:{
			id:TAB_HOMEPAGE_ID,
			title:TAB_HOMEPAGE_TITLE,
			content:content,
			iconCls:'icon-home',
			closable:false
		}
	});
}

function getTabTitle() {
	var tab=$('#tabpanel').tabs('getSelected');
	return tab.panel('options').title;
}

/**
 * 关闭tab页
 * @param action	
 * 				action取值范围:
 * 				close:关闭title指定的tab
 * 				active:关闭当前激活的tab,首页除外
 * 				all:关闭所有tab,首页除外
 * 				other:关闭除title以外的tab,首页除外
 * 				left:关闭title左边的tab,首页除外
 * 				right:关闭title右边的tab,首页除外
 * @param title		tab title
 */
function closeTab(action,title) {
	
	var $tabpanel=$('#tabpanel');
	var tabs=$tabpanel.tabs('tabs');
	if(action==null) action='close';
	if(title==null) title=$tabpanel.data('title');
	
	if(action=='close') {
		
		$tabpanel.tabs('close',title);
	} else if(action=='active') {
		
		var p=$tabpanel.tabs('getSelected');
		var _title=p.panel('options').title;
		if(_title!=TAB_HOMEPAGE_TITLE) {
			$tabpanel.tabs('close',_title);
		}
	} else if(action=='all') {
		
		for(var i=tabs.length-1;i>=0;i--) {
			var _title=tabs[i].panel('options').title;
			if(_title==TAB_HOMEPAGE_TITLE) continue;
			$tabpanel.tabs('close',_title);
		}
	} else if(action=='other') {
		
		for(var i=tabs.length-1;i>=0;i--) {
			var _title=tabs[i].panel('options').title;
			if(_title==TAB_HOMEPAGE_TITLE || _title==title) continue;
			$tabpanel.tabs('close',_title);
		}
	} else if(action=='left') {
		
		var left=false;
		for(var i=tabs.length-1;i>=0;i--) {
			var _title=tabs[i].panel('options').title;
			if(_title==TAB_HOMEPAGE_TITLE) continue;
			if(_title==title) {
				left=true;
				continue;
			}
			if(left) {
				$tabpanel.tabs('close',_title);
			}
		}
	} else if(action=='right') {
		
		for(var i=tabs.length-1;i>=0;i--) {
			var _title=tabs[i].panel('options').title;
			if(_title==TAB_HOMEPAGE_TITLE) continue;
			if(_title==title) {
				break;
			}
			$tabpanel.tabs('close',_title);
		}
	}
}