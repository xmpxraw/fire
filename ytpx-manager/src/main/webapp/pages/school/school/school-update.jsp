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
	  			<form id="data-form" class="dialog-data-form" method="post" enctype="multipart/form-data">
		    	<table class="data-table" cellspacing="4">
		    		<tbody>
		    			<tr >	  
		    				<c:if test="${!empty school}">
		    				<td class="field">code(二级域名前缀)</td>
		    				<td  >
		    					<input name="schoolCode" type="text"  style="width: 100%"  data-options="required:true"  class="field-title easyui-validatebox " maxlength="16" value="${school.schoolCode}" />
		    				</td>
		    				</c:if>
		    				<c:if test="${empty school}">
		    					<td class="field">code(二级域名前缀)</td>
		    				<td  >
		    					<input name="schoolCode" type="text"  style="width: 100%"  data-options="required:true"  class="field-title easyui-validatebox " maxlength="16" value="${school.schoolCode}" placeholder="code将会作为二级域名"/>
		    				</td>
		    				</c:if>  			    				
		    				<td class="field">学校名称</td>
		    				<td>
		    					<input id="schoolName" name="schoolName" type="text"  data-options="required:true"  style="width: 100%"  class="field easyui-validatebox" maxlength="32" value="${school.schoolName}"/>
		    				</td>
		    			</tr>
		    			<tr>
		    				<td class="field">地区</td>
		    				<td>
		    					<%-- <w:data code="school.school.district" name="district" cssClass="field" selectedValue="${school.district}"/> --%>
		    					<select class="select changes" name="province" id="t1">
									<option value="${school.province}" selected="selected">${school.province}</option>
								</select> <select class="select changes" name="city" id="t2">
									<option value="${school.city}" selected="selected">${school.city}</option>
								</select> <select class="select changes" name="district" id="t3">
									<option value="${school.district}" selected="selected">${school.district}</option>
								</select>
							</td>
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
		    			<td class="field">上传学校logo图片(尺寸:281x71)</td>
		    			<td>
		    			<input name="file" type="file" class="field-title easyui-validatebox" />
		    			</td>
		    			<tr>
		    			
		    			</tr>
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
		 <script type="text/javascript" src="${commonskin}/lib/jQuery.cxSelect-1.4.0/company_update.js"></script>
	<script type="text/javascript">
	 $(function(){ 
		 if($('#schoolName').val()==""){//添加
			  onload=setup();preselect('广东省');
		 }	 
		  $(document).on('click','.changes',function(e){
				 $("select").removeClass("changes");
				  onload=setup();preselect('广东省');
			    });  
		}); 
	 
</script>
  </body>
</html>
