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
					<td class="field">上级功能</td>
					<td id="parent-text" colspan="3">${empty parent?'无':parent.name}</td>
				</tr>
    			<tr>
					<td class="field">功能名</td>
					<td>
						<input class="field easyui-validatebox"  data-options="required:true" type="text" name="name" value="${function.name}" maxlength="15"/>
					</td>
					<td class="field">Code</td>
					<td>
						<input class="field easyui-validatebox" type="text" name="code" value="${function.code}" maxlength="32"
							data-options="validType:'bytelen[0,32]',invalidMessage:'不能超过32个字节'"/>
					</td>
				</tr>
				<tr>
					<td class="field">类型</td>
					<td>
						<w:data code="system.function.type" name="type" cssClass="field" selectedValue="${empty function?2:function.type}"/>
					</td>
					<td class="field">状态</td>
					<td colspan="3">
						<w:data code="system.function.status" name="status" cssClass="field" selectedValue="${function.status}"/>
					</td>
				</tr>
    			<tr>
					<td class="field">URL</td>
					<td colspan="3">
						<input class="field-title easyui-validatebox" type="text" name="url" value="${function.url}" maxlength="100"
							data-options="validType:'bytelen[0,100]',invalidMessage:'不能超过100个字节'"/>
					</td>
				</tr>
				<tr>
					<td class="field">备注</td>
					<td colspan="3">
						<textarea class="field-remark easyui-validatebox" name="remark" 
							data-options="validType:'bytelen[0,100]',invalidMessage:'不能超过100个字节'">${function.remark}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="4" align="center">
						<input name="id" type="hidden" value="${function.id}"/>
						<input name="parentId" type="hidden" value="${parent.id}"/>
						<c:if test="${empty function}">
							<a class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="system.func.doAdd();">添加</a>
						</c:if>
						<c:if test="${!empty function}">
							<a class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="system.func.doUpdate();">修改</a>
						</c:if>
					</td>
				</tr>
    		</tbody>
    	</table>
   	</form>
   	<script type="text/javascript">
   		Namespace('system.func',{
   			doAdd:function() {
   				if(!$('#data-form').form('validate')) return;
				var data=$('#data-form').form('jsonObject');
   				$.post(contextpath+'/system/function/add',data,function(result) {
					if(result.code==200) {
						parent.system.func.refreshNode(data.parentId);
						$('#data-form')[0].reset();
					} else {
						top.showInfo('添加功能失败:'+result.msg);
					}
				},'json');
   			},
   			doUpdate:function() {
   				if(!$('#data-form').form('validate')) return;
				var data=$('#data-form').form('jsonObject');
   				$.post(contextpath+'/system/function/update/'+data.id,data,function(result) {
					if(result.code==200) {
						parent.system.func.refreshNode(data.parentId);
						parent.system.func.updateTabTitle('功能：'+data.name);
					} else {
						top.showInfo('修改功能失败:'+result.msg);
					}
				},'json');
   			}
   		});
   	</script>
  </body>
</html>
