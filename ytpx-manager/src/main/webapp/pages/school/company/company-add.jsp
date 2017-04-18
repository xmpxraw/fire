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
			<form id="add-form" class="dialog-data-form">
		    	<table class="data-table" style="width: 98%;">
		    		<tbody>
		    			<tr >	    			
		    				<td class="field">所在区域</td>
		    				<td colspan="3">
		    				<%-- 	<w:data code="school.company.province" name="province" cssClass="field" selectedValue=""/> --%>
								<select class="select" name="province" id="s1">
									<option></option>
							</select> <select class="select" name="city" id="s2">
									<option></option>
							</select> <select class="select" name="district" id="s3">
									<option></option>
							</select>
							</td>			
		    			</tr>
		    			<tr>
		    			<td class="field">单位名称</td>
		    				<td>
		    					<input name="companyName" type="text"  style="width: 100%"  class="field easyui-validatebox" maxlength="16" data-options="required:true"  />
		    				</td>
		    			<td class="field">单位类型</td>
		    				<td>
		    					<w:data code="school.company.companyType"    style="width: 100%" name="companyType" cssClass="field" selectedValue=""/>
		    				</td>    				
		    			</tr>
		    		 			
		    			<tr>
		    			<td class="field">联系人</td>
		    				<td>
		    					<input name="contact" type="text" style="width: 100%"  data-options="required:true"   class="field easyui-validatebox" maxlength="16"/>
		    				</td>
		    				<td class="field">联系电话</td>
		    				<td>
		    					<input name="telephone" type="text"  style="width: 100%" data-options="required:true"   class="field easyui-validatebox" maxlength="16"/>
		    				</td>
		    			</tr>
		    				<tr>
		    				<td class="field">单位地址</td>
		    				<td colspan="3" >
		    					<input name="companyAddr" type="text"  style="width: 100%" data-options="required:true"   class="field-title easyui-validatebox validatebox-text" maxlength="150""/>
		    				</td>    				
		    			</tr>	   
		    		<!-- 	发票信息 -->
						<tr style="width: 100%">
						</tr>
						<tr>
						<td class="field">发票抬头</td>
		    				<td  >
		    					<input name="invoiceTitle" type="text"  style="width: 100%" data-options="required:true"   class="field easyui-validatebox" maxlength="32"/>
		    				</td>
		    				<td class="field">纳税人识别号</td>
		    				<td >
		    					<input name="taxpayerNo" type="text"  style="width: 100%" data-options="required:true"    class="field easyui-validatebox" maxlength="32"/>
		    				</td>
						</tr>
					
						<tr >	    			
		    				<td class="field">发票类型</td>
		    				<td >
		    					<w:data code="school.company.invoiceType"  style="width: 100%" name="invoiceType" cssClass="field" selectedValue=""/>
		    				</td>	
		    				<td  style="width: auto"  class="field">发票领取方式</td>
		    				<td>
		    					<w:data code="school.company.invoiceTaken"   style="width: 100%" name="invoiceTaken" cssClass="field" selectedValue=""/>
		    				</td>		
		    			</tr>
		    			<tr >	    			
		    				<td class="field">开户行</td>
		    				<td >
		    					<w:data code="school.company.bank"  style="width: 100%" name="bank" cssClass="field" selectedValue=""/>
		    				</td>	
		    				<td  style="width: auto"  class="field">银行帐号</td>
		    				<td>
		    				<input name="bankAccount" type="text" data-options="required:true" data-options="required:true"    style="width: 195px"  class="field easyui-validatebox easyui-numberbox" maxlength="21"/>
		    				</td>		
		    			</tr>
		    		</tbody>
		    	</table>
		   	</form>
		</div>
	</div>
	<script type="text/javascript" src="${commonskin}/lib/jQuery.cxSelect-1.4.0/company_add.js"></script>
	<script type="text/javascript">

 $(function(){  

	  onload=setup();preselect('广东省'); /* promptinfo(); */
	
	}); 
  /*  function promptinfo()
  {
      var address = document.getElementById('address');
      var s1 = document.getElementById('s1');
      var s2 = document.getElementById('s2');
      var s3 = document.getElementById('s3');

  }  */
</script>
  </body>
</html>
