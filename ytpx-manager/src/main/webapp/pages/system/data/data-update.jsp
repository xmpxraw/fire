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
					<td class="field">上级数据</td>
					<td id="parent-text">${empty parent?'无':parent.text}</td>
				</tr>
	   			<tr>
					<td class="field">数据文本</td>
					<td>
						<input class="field-title easyui-validatebox"  data-options="required:true" type="text" name="text" value="${data.text}" maxlength="50"/>
					</td>
				</tr>
				<tr>
					<td class="field">数据值</td>
					<td>
						<input class="field-title" type="text" name="value" value="${data.value}" maxlength="50"/>
					</td>
				</tr>
				<tr>
					<td class="field">Code</td>
					<td>
						<input class="field-title easyui-validatebox" type="text" name="code" value="${data.code}" maxlength="32"
							data-options="required:false,validType:'fn[system.data.checkCode]',invalidMessage:'code已存在！'"/>
					</td>
				</tr>
				<tr>
					<td class="field">状态</td>
					<td>
						<w:data code="system.data.status" name="status" cssClass="field" selectedValue="${data.status}"/>
					</td>
				</tr>
				<tr>
					<td class="field">类型</td>
					<td>
						<w:data code="system.data.type" name="type" cssClass="field" selectedValue="${empty data?2:data.type}"/>
					</td>
				</tr>
				<tr>
					<td class="field">备注</td>
					<td>
						<textarea class="field-remark easyui-validatebox" name="remark" data-options="validType:'length[0,255]'">${data.remark}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="4" align="center">
						<input name="id" type="hidden" value="${data.id}"/>
						<input name="parentId" type="hidden" value="${parent.id}"/>
						<c:if test="${empty data}">
							<a class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="system.data.doAdd();">添加</a>
						</c:if>
						<c:if test="${!empty data}">
							<a class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="system.data.doUpdate();">修改</a>
						</c:if>
					</td>
				</tr>
	   		</tbody>
	   	</table>
	</form>
   	<script type="text/javascript">
   		Namespace('system.data',{
   			doAdd:function() {
   				if(!$('#data-form').form('validate')) return;
				var data=$('#data-form').form('jsonObject');
   				$.post(contextpath+'/system/data/add',data,function(result) {
					if(result.code==200) {
						parent.system.data.refreshNode(data.parentId);
						$('#data-form')[0].reset();
					} else {
						top.showInfo('添加基础数据失败:'+result.msg);
					}
				},'json');
   			},
   			doUpdate:function() {
   				if(!$('#data-form').form('validate')) return;
				var data=$('#data-form').form('jsonObject');
   				$.post(contextpath+'/system/data/update/'+data.id,data,function(result) {
					if(result.code==200) {
						parent.system.data.refreshNode(data.parentId);
						parent.system.data.updateTabTitle('数据：'+data.text);
					} else {
						top.showInfo('修改基础数据失败:'+result.msg);
					}
				},'json');
   			},
   			/**
			 * 检查code是否存在
			 * @param rolename
			 */
			checkCode:function() {
				var id=$('#data-form')[0].id.value;
				var code=$('#data-form')[0].code.value;
				var valid=false;
				$.ajax({
					url:contextpath+'/system/data/check_code',
					type:'POST',
					data:{id:id,code:code},
					dataType:'json',
					async:false,
					success:function(result) {
						if(result.code==200) {
							valid=true;
						} else {
							valid=false;
						}
					}
				});
				return valid;
			}
   		});
   	</script>
  </body>
</html>
