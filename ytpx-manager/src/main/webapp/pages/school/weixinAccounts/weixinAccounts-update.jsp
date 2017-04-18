<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<%@include file="/pages/include/base-head.jsp"%>
  </head>
  <body>
  	<div class="easyui-tabs" 
  		data-options="border:false,fit:true,plain:true">
		<div title="微信参数" style="padding:10px">
	  		<form id="update-form" class="dialog-data-form">
		    	<table class="data-table" cellspacing="4">
		    		<tbody>
		    		    			
		    			<tr >	    			
		    				<td class="field">appId</td>
		    				<td>
		    					<input name="appId" type="text"  data-options="required:true" style="width: 150px;" class="field easyui-validatebox " maxlength="128" value="${weixin.appId}"/>
		    				</td>
		    				
		    			</tr>
		    		
		    			<tr>
		    			<td class="field">secret</td>
		    				<td>
		    					<input name="secret" type="text"  data-options="required:true" style="width: 150px;" class="field easyui-validatebox " maxlength="128" value="${weixin.secret}"/>
		    				</td>	    				
		    			</tr>	
		    			
		    				<tr>
		    			<td class="field">token</td>
		    				<td>
		    					<input name="token" type="text"   data-options=""  style="width: 150px;" value="${weixin.token}" class="field easyui-validatebox " maxlength="128"/>
		    				</td>
		    			</tr>	
		    			<tr >	    			
		    				<td class="field">accountType</td>
		    				<td>
		    					<w:data code="school.weixin.accountType" name="accountType" cssClass="field" selectedValue="${weixin.accountType}"/>
		    				</td>
		    				
		    			</tr>
		    			<tr >	    			
		    				<td class="field">encryptType</td>
		    				<td>
		    					<w:data code="school.weixin.encryptType" name="encryptType" cssClass="field" selectedValue="${weixin.encryptType}"/>
		    				</td>    				
		    			</tr>
		    			<tr>
		    			<td class="field">encodingAesKey</td>
		    				<td>
		    					<input name="encodingAesKey" type="text"  data-options="" style="width: 150px;" class="field easyui-validatebox " maxlength="56" value="${weixin.encodingAesKey}"/>
		    				</td>	    				
		    			</tr>				
		    		</tbody>
		    	</table>
	    	</form>
   		</div>
	</div>
  </body>
</html>
