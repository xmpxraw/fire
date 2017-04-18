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
	  		<form id="update-form" class="dialog-data-form">
		    	<table class="data-table" cellspacing="4">
		    		<tbody>
		    			<tr>
		    				<td class="field">学校名称</td>
		    				<td>
		    					<input name="schoolName" type="text" class="field easyui-validatebox"  maxlength="16" value="${school.schoolName}"/>
		    				</td>
		    			
		    			</tr>
		    			<tr>	  
		    				<td class="field">学校地址</td>
		    				<td>
		    					<input name="schoolAddr" type="text" class="field easyui-validatebox" maxlength="16" value="${school.schoolAddr}"/>
		    				</td>
		    			</tr>
		    			<tr>	  
		    				<td class="field">联系方式</td>
		    				<td>
		    					<input name="telephone" type="text" class="field easyui-validatebox" maxlength="16" value="${school.telephone}"/>
		    				</td>
		    				<td class="field">交通方式</td>
		    				<td>
		    					<input name="transportation" type="text" class="field easyui-validatebox" maxlength="16" value="${school.transportation}"/>
		    				</td>
		    			</tr>
		    			<tr>
		    			<td class="field">学校介绍</td>
							<td colspan="3" >
								<textarea style="width: 100%;height: 100%!important;" class="field-detailDesc easyui-validatebox" name="description" 
									data-options="validType:'length[0,500]',invalidMessage:'不能超过500个字符！'">${school.description}</textarea>
							</td>
							<%-- <td class="field">详细描述</td>
							<td colspan="3">
								<textarea class="field-detailDesc easyui-validatebox" name="detailDesc" 
									data-options="validType:'length[0,120]',invalidMessage:'不能超过120个字符！'">${images.detailDesc}</textarea>
							</td> --%>
						</tr>
		    		</tbody>
		    	</table>
	    	</form>
   		</div>
	</div>
  </body>
</html>
