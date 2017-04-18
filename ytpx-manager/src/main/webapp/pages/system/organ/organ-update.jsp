<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<%@include file="/pages/include/base-head.jsp"%>
  	<%@include file="/pages/include/jquery-easyui.jsp"%>
  	<script type="text/javascript" src="${skin}/js/system/organ-update.js"></script>
  	<script type="text/javascript" src="${skin}/js/system/organ-select.js"></script>
  </head>
  <body>
 	  <form id="data-form" class="dialog-data-form" style="width:550px;">
    	<table cellspacing="4">
    		<tbody>
    			<tr>
					<td class="field">上级组织</td>
					<td id="parent-text" colspan="3">${empty parent?'无':parent.name}</td>
				</tr>
    			<tr>
					<td class="field">名称</td>
					<td>
						<input class="field easyui-validatebox"  data-options="required:true" type="text" name="name"  maxlength="30" value="${organ.name}"/>
					</td>
					<td class="field">简称</td>
					<td>
						<input class="field easyui-validatebox"  type="text" name="shortName" maxlength="30" value="${organ.shortName}"/>
					</td>
				</tr>
				<tr>
					<td class="field" >类型</td>
					<td>
						<w:data code="system.organ.type" name="type" cssClass="field" selectedValue="${empty organ?4:organ.type}" onchange="system.organ.changeOrganType();" /> 
					</td>
					<td class="field">状态</td>
					<td>
						<w:data code="system.organ.status" name="status" cssClass="field" selectedValue="${organ.status}"/>
					</td>
				</tr>
				<tr>
					<td class="field" style="width:100px;">办公电话</td>
					<td>
					    <input class="field easyui-validatebox" type="text" name="officePhone" maxlength="16" value="${organ.officePhone}"/>
					</td>
					<td class="field" style="width:100px;">传真</td>
					<td>
					    <input class="field easyui-validatebox"  type="text" name="fax" maxlength="16" value="${organ.fax}"/>
					</td>
				</tr>
				<tr>
					<td class="field">编码</td>
					<td>
						<input class="field" type="text" name="code" maxlength="32" value="${organ.code}"/>
					</td>
					<td class="field">备忘</td>
					<td>
						<input class="field" type="text" name="memo" maxlength="16" value="${organ.memo}"/>
					</td>
				</tr>
				<tr>
					<td class="field">等级</td>
					<td>
						<w:data code="system.organ.grade" name="grade" cssClass="field" selectedValue="${empty organ?150:organ.grade}" options=":"/>
					</td>
					<td class="field">负责人</td>
					<td>
						<input type="hidden" id="principalId" name="principalId" value="${organ.principalId}"/>
						<input type="text" id="principalName" name="principalName" style="width:95px" maxlength="32" readonly="readonly" value="${principal.name}"/>
						<a class="easyui-linkbutton" data-options="iconCls:'icon-group',plain:true" onclick="system.organ.selectPrincipal();">选择</a>
					</td>
				</tr>
				<tr class="hidden">
					<td class="field" style="width:100px;">工号</td>
					<td>
					    <input class="field easyui-validatebox" type="text" name="empNo" maxlength="32" value="${organ.empNo}"/> 
					</td>
					<td class="field">主要职务</td>
					<td>
						<w:data code="system.organ.main" name="main" cssClass="field" selectedValue="${organ.main}"/>
					</td>
				</tr>
				<tr class="hidden">
					<td class="field" style="width:100px;">出生日期</td>
					<td>
						<fmt:formatDate var="birthday" value="${organ.birthday}" pattern="yyyy-MM-dd"/>
					    <input id="birthday" type="text" class="easyui-datebox" name="birthday" data-options="required:false,validType:'date'" value="${birthday}"/>
					</td>
					<td class="field">性别</td>
					<td>
						<w:data code="system.organ.sex" name="sex" cssClass="field" options=":" selectedValue="${organ.sex}"/>
					</td>
				</tr>
				<tr class="hidden">
					<td class="field">个人电话</td>
					<td>
						<input class="field easyui-validatebox" type="text" name="mobile" maxlength="16" value="${organ.mobile}"/>
					</td>
					<td class="field">邮箱</td>
					<td>
						<input class="field easyui-validatebox"  type="text" name="mail" maxlength="50" value="${organ.mail}"/>
					</td>
				</tr>
				<tr class="hidden">
					<td class="field">QQ</td>
					<td>
					    <input class="field easyui-validatebox" type="text" name="qq" maxlength="50" value="${organ.qq}"/>
					</td>
					<td class="field">用户</td>
					<td>
						<input type="hidden" name="userId" value="${organ.userId}"/>
						<input type="text" name="userName"  style="width:95px" readonly="readonly" value="${user.username}"/>
						<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" onclick="system.organ.selectUser();"></a>
					</td>
				</tr>
				<tr>
					<td class="field">地址</td>
					<td colspan="3">
						<input class="field-title easyui-validatebox" type="text" name="address" maxlength="50"
							data-options="validType:'bytelen[0,100]',invalidMessage:'不能超过100个字节'" value="${organ.address}"/>
					</td>
				</tr>
				<tr>
					<td class="field">备注</td>
					<td colspan="3">
						<textarea class="field-remark easyui-validatebox" name="remark" data-options="validType:'length[0,255]'">${organ.remark}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="4" align="center">
						<input name="id" type="hidden" value="${organ.id}"/>
						<input name="parentId" type="hidden" value="${parent.id}"/>
						<input name="seqNum" type="hidden" value="${organ.seqNum}"/>
						<fmt:formatDate var="createTime" value="${organ.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
						<input name="createTime" type="hidden" value="${createTime}"/>
						<input name="createUserId" type="hidden" value="${organ.createUserId}"/>
						<c:if test="${empty organ}">
							<a class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="system.organ.doAdd();">添加</a>
						</c:if>
						<c:if test="${!empty organ}">
							<a class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="system.organ.doUpdate();">修改</a>
						</c:if>
						<!-- <a class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="var r=system.organ.selectOrgans(0);alert(JSON.stringify(r));">单选组织</a>
						<a class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="var r=system.organ.selectOrgans(1);alert(JSON.stringify(r));">多选组织</a> -->
					</td>
				</tr>
    		</tbody>
    	</table>
   	</form>
  </body>
</html>
