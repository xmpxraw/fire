<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<%@include file="/pages/include/base-head.jsp"%>
  </head>
  <body>
  	<div class="easyui-tabs" 
  		data-options="border:false,fit:true,plain:true">
		<div title="学校信息" style="padding:10px">
	  			<form id="data-form" class="dialog-data-form">
		    	<table class="data-table" cellspacing="4">
		    		<tbody>
		    			<tr >	    			
		    				<td class="field">地区</td>
		    				<td>
		    					<w:data code="school.school.district" name="district" cssClass="field" selectedValue="${school.district}"/>
		    				</td>
		    				<td class="field">学校名称</td>
		    				<td>
		    					<input name="schoolName" type="text"  data-options="required:true"  style="width: 100%"  class="field easyui-validatebox" maxlength="16" value="${school.schoolName}"/>
		    				</td>
		    			</tr>
		    			<tr>
		    				<c:if test="${!empty school}">
		    				<td class="field">code</td>
		    				<td  >
		    					<input name="schoolCode" type="text"  style="width: 100%"  data-options="required:true"  class="field-title easyui-validatebox validatebox-text" maxlength="50" value="${school.schoolCode}" readonly="readonly"/>
		    				</td>
		    				</c:if>
		    				<c:if test="${empty school}">
		    					<td class="field">code</td>
		    				<td  >
		    					<input name="schoolCode" type="text"  style="width: 100%"  data-options="required:true"  class="field-title easyui-validatebox validatebox-text" maxlength="50" value="${school.schoolCode}" />
		    				</td>
		    				</c:if>
		    				<td class="field">学校地址</td>
		    				<td >
		    					<input name="schoolAddr" type="text"  data-options="required:true"  style="width: 100%"  class="field-title easyui-validatebox validatebox-text" maxlength="150" value="${school.schoolAddr}"/>
		    				</td>
		    				
		    			</tr>
		    			<tr>
		    			<td class="field">联系方式</td>
		    				<td>
		    					<input name="telephone" type="text" style="width: 100%" data-options="required:true"  class="field easyui-validatebox" maxlength="16" value="${school.telephone}"/>
		    				</td>
		    				<td class="field">交通方式</td>
		    				<td>
		    					<input name="transportation" type="text"  style="width: 100%"  class="field easyui-validatebox" maxlength="16" value="${school.transportation}"/>
		    				</td>
		    			</tr>
		    			<tr >
							<td class="field">学校介绍</td>
								<td colspan="3" >
	    					<script id="content" name="content" type="text/plain" style="width:100%;height:220px;">${school.content}</script>
	    					<script type="text/javascript">
						        var um = UM.getEditor('content');
						    </script>
	    				</td>
						</tr>
						<c:if test="${empty school}">
						<tr>
						<td class="field">用户名</td>
		    				<td>
		    					<input name="username" type="text" class="field easyui-validatebox" maxlength="16"
		    						data-options="required:true,validType:'fn[school.school.checkUser]',invalidMessage:'用户名已存在！'"/>
		    				</td>
						</tr>
						<tr>
		    				<td class="field">密码</td>
		    				<td colspan="3">
		    					<input id="password" name="password" type="password" class="field easyui-validatebox" maxlength="16"
		    						data-options="required:true,validType:'regexp[\'^\\\\w{6,16}$\']',invalidMessage:'密码必须是6-16个字母、数字、下划线！'"/>
		    				</td>
		    			</tr>
		    			<tr>
		    				<td class="field">确认密码</td>
		    				<td colspan="3">
		    					<input type="password" class="field easyui-validatebox" maxlength="16"
		    						data-options="required:true,validType:'equals[\'#password\']',invalidMessage:'两次输入密码不一致！'"/>
		    				</td>
		    			</tr>
						</c:if>
		    		</tbody>
		    	</table>
	    	</form>
   		</div>
	</div>
  </body>
</html>
