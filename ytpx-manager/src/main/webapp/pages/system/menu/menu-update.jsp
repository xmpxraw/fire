<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<%@include file="/pages/include/base-head.jsp"%>
  	<%@include file="/pages/include/jquery-easyui.jsp"%>
  </head>
  <body>
  	<form id="data-form" class="dialog-data-form" style="width:500px;">
    	<table cellspacing="4">
    		<tbody>
    			<tr>
					<td class="field">上级菜单</td>
					<td id="parent-text" colspan="3">${empty parent?'无':parent.name}</td>
				</tr>
    			<tr>
					<td class="field">菜单名</td>
					<td><input class="field easyui-validatebox"  data-options="required:true" type="text" name="name" value="${menu.name}" maxlength="15"/></td>
					<td class="field">类型</td>
					<td>
						<w:data code="system.menu.type" name="type" cssClass="field" selectedValue="${empty menu?2:menu.type}"/>
					</td>
				</tr>
				<tr>
					<td class="field">状态</td>
					<td>
						<w:data code="system.menu.status" name="status" cssClass="field" selectedValue="${menu.status}"/>
					</td>
					<td class="field">扩展/收缩</td>
					<td>
						<w:data code="system.menu.expand" name="expand" cssClass="field" selectedValue="${empty menu?1:menu.expand}"/>
					</td>
				</tr>
    			<tr>
					<td class="field">URL</td>
					<td colspan="3">
						<input class="field-title easyui-validatebox" type="text" name="url" value="${menu.url}" maxlength="100"
							data-options="validType:'bytelen[0,100]',invalidMessage:'不能超过100个字节'"/>
					</td>
				</tr>
				<tr>
					<td class="field">图标</td>
					<td colspan="3">
						<input class="field-title easyui-validatebox" type="text" name="icon" value="${menu.icon}" maxlength="50"
							data-options="validType:'bytelen[0,50]',invalidMessage:'不能超过50个字节'"/>
					</td>
				</tr>
				<tr>
					<td class="field">打开图标</td>
					<td colspan="3">
						<input class="field-title easyui-validatebox" type="text" name="iconOpen" value="${menu.iconOpen}" maxlength="50"
							data-options="validType:'bytelen[0,50]',invalidMessage:'不能超过50个字节'"/>
					</td>
				</tr>
				<tr>
					<td class="field">备注</td>
					<td colspan="3">
						<textarea class="field-remark easyui-validatebox" name="remark" data-options="validType:'length[0,255]'">${menu.remark}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="4" align="center">
						<input name="id" type="hidden" value="${menu.id}"/>
						<input name="parentId" type="hidden" value="${parent.id}"/>
						<c:if test="${empty menu}">
							<a class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="system.menu.doAdd();">添加</a>
						</c:if>
						<c:if test="${!empty menu}">
							<a class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="system.menu.doUpdate();">修改</a>
						</c:if>
					</td>
				</tr>
    		</tbody>
    	</table>
   	</form>
   	<script type="text/javascript">
   		Namespace('system.menu',{
   			doAdd:function() {
   				if(!$('#data-form').form('validate')) return;
				var data=$('#data-form').form('jsonObject');
   				$.post(contextpath+'/system/menu/add',data,function(result) {
					if(result.code==200) {
						parent.system.menu.refreshNode(data.parentId);
						$('#data-form')[0].reset();
					} else {
						top.showInfo('添加菜单失败:'+result.msg);
					}
				},'json');
   			},
   			doUpdate:function() {
   				if(!$('#data-form').form('validate')) return;
				var data=$('#data-form').form('jsonObject');
   				$.post(contextpath+'/system/menu/update/'+data.id,data,function(result) {
					if(result.code==200) {
						parent.system.menu.refreshNode(data.parentId);
						parent.system.menu.updateTabTitle('菜单：'+data.name);
					} else {
						top.showInfo('修改菜单失败:'+result.msg);
					}
				},'json');
   			}
   		});
   	</script>
  </body>
</html>
