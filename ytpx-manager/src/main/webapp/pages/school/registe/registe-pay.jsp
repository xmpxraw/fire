<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<%@include file="/pages/include/base-head.jsp"%>
  </head>
  <body>
  	<div class="easyui-tabs" 
  		data-options="border:false,fit:true,plain:true">
		<div title="确认缴费信息" style="padding:10px">
	  		<form id="pay-form" class="dialog-data-form">
		    	<table class="data-table" cellspacing="4">
		    		<tbody>
		    		<tr >	    			
		    				<td class="field">报名编码</td>
		    				<td>
		    					<input name="registeCode" type="text" class="field" value="${registe.registeCode}" readonly="readonly"/>
		    				</td>	    				
		    			</tr>
		    				<tr >	    			
		    				<td class="field">报名单位</td>
		    				<td>
		    					<input name="companyName" type="text" class="field" value="${registe.companyName}" readonly="readonly"/>
		    					<input name="companyCode" type="hidden" class="field" value="${registe.companyCode}" />
		    				</td>	    				
		    			</tr>
		    			<tr >	    			
		    				<td class="field">学员人数</td>
		    				<td>
		    					<input name="registeCount" type="text" class="field" class="field easyui-validatebox easyui-numberbox" maxlength="10" value="${registe.registeCount}" readonly="readonly"/>
		    				</td>	    				
		    			</tr>
		    			<tr >	    			
		    				<td class="field">缴费方案</td>
		    				<td>
		    					<input name="schemeName" type="text" class="field" value="${registe.schemeName}" readonly="readonly"/>
		    					<input name="payScheme" type="hidden" class="field" value="${registe.payScheme}" readonly="readonly"/>
		    				</td>	    				
		    			</tr>
		    			<tr>
		    			<td class="field">应缴费用</td>
		    				<td>
		    					<input  name="payShould" type="text"  data-options="required:true"  class="field easyui-validatebox" maxlength="16" readonly="readonly" value="${registe.payShould}"/>
		    				</td>	    				
		    			</tr>
		    			<%-- <tr>
		    			<td class="field">优惠方案</td>
		    				<td>
		    						<w:data code="school.registe.discountType" id="discountType" name="discountType" style="width: 150px;"  cssClass="field " selectedValue="${registe.discountType}"/>
		    				</td>
		    			</tr> --%>
		    			<%-- <tr>
		    			<td class="field">优惠额度</td>
		    				<td>
		    					<input name="discount" type="text"   data-options="required:true"  style="width: 150px;"  validtype="number"  value="${registe.discount}" class="field discounte-js easyui-numberbox" precision="2"  maxlength="16"/>
		    				</td>
		    			</tr>	 --%>
		    			<tr>
		    			<td class="field">实缴费用</td>
		    				<td>
		    					<input name="payReal" type="text"   data-options="required:true"  style="width: 150px;"  validtype="number"  value="${registe.payReal}" class="field easyui-numberbox" precision="2"  readonly="readonly" maxlength="16"/>
		    				</td>
		    			</tr>	
		    			<tr>
		    			<td class="field">缴费方式</td>
		    				<td>
		    						<w:data code="school.registe.payType" name="payType" style="width: 150px;"  cssClass="field " selectedValue="${registe.payType}"/>
		    				</td>
		    			</tr> 				
		    		</tbody>
		    	</table>
	    	</form>
   		</div>
	</div>
  </body>
</html>
