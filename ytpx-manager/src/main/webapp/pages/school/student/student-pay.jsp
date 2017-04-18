<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<%@include file="/pages/include/base-head.jsp"%>
  </head>
  <body>
<script type="text/javascript">  
	var feesMeal =0;
	var feesHotel =0;
	var total =0;
	$(function() {
		 feesMeal= $('input[name="feesMeal"]').val();//餐费
		 feesHotel= $('input[name="feesHotel"]').val();
		 total = $('input[name="feesTotal"]').val();//填充内容

	});	
	 $('#pay-form').on('change','#feesMealCK',function(e){
		  if ($('#feesMealCK').is(':checked')) {
			    $("#feesMeal").removeAttr("disabled");
			    total = $('input[name="feesTotal"]').val();	   
			    $('input[name="feesTotal"]').attr("value",'');
			    $('input[name="feesTotal"]').val(parseFloat(total)+parseFloat(feesMeal));
			    $('#feesTotal').val(parseFloat(total)+parseFloat(feesMeal));
			   
			}if(!$('#feesMealCK').is(':checked')){	
				$("#feesMeal").attr("disabled","disabled");
				 total = $('input[name="feesTotal"]').val();			
				 $('input[name="feesTotal"]').attr("value",'');
			    $('input[name="feesTotal"]').val((total-feesMeal).toFixed(2));	   
			    $('#feesTotal').val((total-feesMeal).toFixed(2));
			}	  
    }); 
	 $('#pay-form').on('change','#feesHotelCK',function(e){
		  if ($('#feesHotelCK').is(':checked')) {
			    $("#feesHotel").removeAttr("disabled");
			    total = $('input[name="feesTotal"]').val();
			    $('input[name="feesTotal"]').attr("value",'');
			    $('input[name="feesTotal"]').val(parseFloat(total)+parseFloat(feesHotel));
			    $('#feesTotal').val(parseFloat(total)+parseFloat(feesHotel));
			    
			}else if (!$('#feesHotelCK').is(':checked')){	
				$("#feesHotel").attr("disabled","disabled");
				 total = $('input[name="feesTotal"]').val();
				 $('input[name="feesTotal"]').attr("value",'');
				 $('input[name="feesTotal"]').val((total-feesHotel).toFixed(2));			
				 $('#feesTotal').val((total-feesHotel).toFixed(2));
			}	  
    }); 
	</script>
  	<div class="easyui-tabs" 
  		data-options="border:false,fit:true,plain:true">
		<div title="学员缴费信息" style="padding:10px">
	  		<form id="pay-form" class="dialog-data-form">
		    	<table class="data-table" cellspacing="4">
		    		<tbody>
		    		<tr >	    			
		    				<td class="field">报名编码</td>
		    				<td>
		    					<input name="registeCode" type="text" class="field" value="${student.registeCode}" readonly="readonly"/>
		    				</td>	    				
		    			</tr>
		    				<tr >	    			
		    				<td class="field">报名单位</td>
		    				<td>
		    					<input name="companyName" type="text" class="field" value="${student.companyName}" readonly="readonly"/>
		    					<input name="companyCode" type="hidden" class="field" value="${student.companyCode}" />
		    				</td>	    				
		    			</tr>
		    			<tr >	    			
		    				<td class="field">学员姓名</td>
		    				<td>
		    					<input name="studnetName" type="text" class="field" class="field easyui-validatebox easyui-numberbox" maxlength="10" value="${student.studnetName}" readonly="readonly"/>
		    				</td>	    				
		    			</tr>
		    			<tr >	    			
		    				<td class="field">身份证号</td>
		    				<td>
		    					<input name="idcard" type="text" class="field" class="field easyui-validatebox easyui-numberbox" maxlength="10" value="${student.idcard}" readonly="readonly"/>
		    				</td>	    				
		    			</tr>
		    			<tr >	    			
		    				<td class="field">缴费方案</td>
		    				<td>
		    					<input name="schemeName" type="text" class="field" value="${student.schemeName}" readonly="readonly"/>
		    					<input name="payScheme" type="hidden" class="field" value="${student.payScheme}" readonly="readonly"/>
		    				</td>	    				
		    			</tr>
		    			<tr>
		    			<td class="field">培训费用</td>
		    				<td>
		    					<input  name="feesTrain" type="text"  data-options="required:true"  class="field easyui-validatebox" maxlength="16" readonly="readonly" value="${student.feesTrain}"/>
		    				</td>	    				
		    			</tr>
						<tr id="meal">
							<td class="field">餐费 <input id="feesMealCK" 
								name="feesMealCK" type="checkbox" checked="checked" />
							</td>
							<td><input name="feesMeal" id="feesMeal" type="text"
								data-options="required:true" class="field easyui-validatebox"
								maxlength="16" readonly="readonly" value="${student.feesMeal}" />
							</td>
						</tr>
						<tr id="hotel">
							<td class="field">住宿费 <input id="feesHotelCK"
								name="feesHotelCK" type="checkbox" checked="checked" />
							</td>
							<td><input name="feesHotel" id="feesHotel" type="text"
								data-options="required:true" class="field easyui-validatebox"
								maxlength="16" readonly="readonly" value="${student.feesHotel}" />
							</td>
						</tr>
						<tr>
		    			<td class="field">学杂费</td>
		    				<td>
		    					<input  name="feesStudy" type="text"  data-options="required:true"  class="field easyui-validatebox" maxlength="16" readonly="readonly" value="${student.feesStudy}"/>
		    				</td>	    				
		    			</tr>
		    			
		    			<tr id="total">
		    			<td class="field">总费用</td>
		    				<td>
		    					<input name="feesTotal"  id="feesTotal" type="text"   data-options="required:true"  style="width: 150px;"  validtype="number"  value="${student.feesTotal}" class="field easyui-numberbox" precision="2"  readonly="readonly" maxlength="16"/>
		    				</td>
		    			</tr>	
		    			<tr>
		    			<td class="field">缴费方式</td>
		    				<td>
		    						<w:data code="school.registe.payType" name="payType" style="width: 150px;"  cssClass="field " selectedValue="${student.payType}"/>
		    				</td>
		    			</tr> 				
		    		</tbody>
		    	</table>
	    	</form>
   		</div>
	</div>
  </body>
</html>
