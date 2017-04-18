<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<%@include file="/pages/include/base-head.jsp"%>

  </head>
  <body>
  	<div class="easyui-tabs" 
  		data-options="border:false,fit:true,plain:true">
		<div title="单位信息" style="padding:10px">
	  		<form id="update-form" class="dialog-data-form">
		    	<table class="data-table" cellspacing="4">
		    		<tbody>
		    			<tr class="ss">	    			
		    				<td class="field">所在区域</td>
		    				<td colspan="3">
		    					<%-- <w:data code="school.company.province" name="province" cssClass="field" selectedValue="${company.province}"/> --%>
		    					<select class="select changes" name="province" id="t1">
									<option value="${company.province}" selected="selected">${company.province}</option> 
									
							</select> <select class="select changes" name="city" id="t2">
									 <option value="${company.city}" selected="selected">${company.city}</option> 
								
							</select> <select class="select changes" name="district" id="t3">
									 <option value="${company.district}" selected="selected">${company.district}</option> 
									<!-- <option></option> -->
							</select>
		    				</td>			
		    			</tr>
		    			<tr>
		    			<td class="field">单位类型</td>
		    				<td>
		    					<%-- <input name="companyType" type="text"  style="width: 100%"  class="field easyui-validatebox" maxlength="16"  value="${company.companyType}"/> --%>
		    					<w:data code="school.company.companyType"  style="width: 100%" name="companyType" cssClass="field" selectedValue="${company.companyType}"/>
		    				</td>
		    				<td class="field">单位名称</td>
		    				<td>
		    					<input name="companyName" type="text"  style="width: 100%" data-options="required:true"    class="field easyui-validatebox" maxlength="16" value="${company.companyName}" />
		    				</td>
		    			</tr>
		    			<tr>
		    				<td class="field">单位地址</td>
		    				<td colspan="3" >
		    					<input name="companyAddr" type="text"  style="width: 100%" data-options="required:true"   class="field-title easyui-validatebox validatebox-text" maxlength="150" value="${company.companyAddr}" />
		    				</td>    				
		    			</tr>	    			
		    			<tr>
		    			<td class="field">联系人</td>
		    				<td>
		    					<input name="contact" type="text" style="width: 100%"  data-options="required:true"   class="field easyui-validatebox" maxlength="16" value="${company.contact}" />
		    				</td>
		    				<td class="field">联系电话</td>
		    				<td>
		    					<input name="telephone" type="text"  style="width: 100%" data-options="required:true"    class="field easyui-validatebox" maxlength="16" value="${company.telephone}" />
		    				</td>
		    			</tr>
		    		<!-- 	发票信息 -->
						<tr>
						<td class="field">发票抬头</td>
		    				<td>
		    					<input name="invoiceTitle" type="text"  style="width: 100%"  data-options="required:true"   class="field easyui-validatebox" maxlength="16" value="${company.invoiceTitle}" />
		    				</td>
		    				<td class="field">纳税人识别号</td>
		    				<td>
		    					<input name="taxpayerNo" type="text"  style="width: 100%" data-options="required:true"   class="field easyui-validatebox" maxlength="16"  value="${company.taxpayerNo}" />
		    				</td>
						</tr>					
						<tr >	    			
		    				<td class="field">发票类型</td>
		    				<td >
		    					<w:data code="school.company.invoiceType" style="width: 100%"  name="invoiceType" cssClass="field" selectedValue="${company.invoiceType}"/>
		    				</td>	
		    				<td class="field">发票领取方式</td>
		    				<td>
		    					<w:data code="school.company.invoiceTaken" style="width: 100%"  name="invoiceTaken" cssClass="field" selectedValue="${company.invoiceTaken}"/>
		    				</td>		
		    			</tr>
		    			<tr >	    			
		    				<td class="field">开户行</td>
		    				<td >
		    					<w:data code="school.company.bank"  style="width: 100%" name="bank" cssClass="field" selectedValue="${company.bank}"/>
		    				</td>	
		    				<td  style="width: auto"  class="field">银行帐号</td>
		    				<td>
		    				<input name="bankAccount" type="text" data-options="required:true"  style="width: 195%"  value="${company.bankAccount}"  class="field easyui-validatebox easyui-numberbox" maxlength="21"/>
		    				</td>		
		    			</tr>
		    		</tbody>
		    	</table>
	    	</form>
   		</div>
	</div>
	  	<script type="text/javascript" src="${commonskin}/lib/jQuery.cxSelect-1.4.0/company_update.js"></script>
	<script type="text/javascript">
	 $(document).on('click','.changes',function(e){
		 $("select").removeClass("changes");
		 onload=setup();preselect('广东省');

	    });
/*  $(function(){  
	  setup();preselect('广东省'); promptinfo();
	}); 
 function promptinfo()
  {

  }  */
  
</script>
  </body>
</html>
