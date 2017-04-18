<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<%@include file="/pages/include/base-head.jsp"%>
  </head>
  <body>
  	<ul id="function-tree" class="easyui-tree"  style="margin:5px;"
  		data-options="
			url: contextpath+'/system/organ/function_setup/${param.organId}',
			method:'get',
  			checkbox: true,
			animate:true,
			onlyLeafCheck:true,
			onClick:function(node){
				if(node.attributes.type==1) {
					$(this).tree('toggle', node.target);
				} else {
					if($(node.target).find('.tree-checkbox0').size()>0) {
						$(this).tree('check', node.target);
					} else {
						$(this).tree('uncheck', node.target);
					}
				}
			}"></ul>
  </body>
</html>
