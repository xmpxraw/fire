<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<%@include file="/pages/include/base-head.jsp"%>
  </head>
  <body>
  	<div class="easyui-tabs" 
  		data-options="border:false,fit:true,plain:true">
		<div title="打折信息" style="padding:10px">
	  		<form id="update-form" class="dialog-data-form">
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
		    					<input name="companyCode" type="hidden" class="field" value="${registe.companyCode}" readonly="readonly"/>
		    				</td>	    				
		    			</tr>
		    			<tr >	    			
		    				<td class="field">学员人数</td>
		    				<td>
		    					<input id="registeCount" name="registeCount" type="text" class="field" class="field easyui-validatebox easyui-numberbox" maxlength="10" value="${registe.registeCount}" readonly="readonly"/>
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
		    					<input id="payShould" name="payShould" type="text"  data-options="required:true"  class="field easyui-validatebox" maxlength="16" readonly="readonly" value="${registe.payShould}"/>
		    				</td>	    				
		    			</tr>
		    			<tr>
		    			<td class="field">优惠方案</td>
		    				<td>
		    						<w:data code="school.registe.discountType" id="discountType" name="discountType" style="width: 150px;"  cssClass="field " selectedValue="${registe.discountType}"/>
		    				</td>
		    			</tr>
		    			<tr>
		    			<td class="field">优惠额度</td>
		    				<td>
		    					<input name="discount" type="text"   data-options="required:true"  style="width: 150px;"  validtype="number"  value="${registe.discount}" class="field discounte-js easyui-numberbox" precision="2"  maxlength="16"/>
		    				</td>
		    			</tr>	
		    			<tr>
		    			<td class="field">实缴</td>
		    				<td>
		    					<input id="payReal" name="payReal" type="text"   data-options="required:true"  style="width: 150px;"  validtype="number"  value="${registe.payReal}" class="field easyui-numberbox" precision="2"  readonly="readonly" maxlength="16"/>
		    				</td>
		    			</tr>					
		    		</tbody>
		    	</table>
	    	</form>
   		</div>
	</div>
	<script type="text/javascript">  
    $(document).on('change','.discounte-js',function(e){
		var realTatol=0;
	 
	 	var discounte= $('.discounte-js').val();
		var registeCount= $('#registeCount').val();
		var payShould= $('#payShould').val();
		var discountType=$('#discountType option:selected').val();
		if(discountType==1){
			realTatol=payShould*discounte;
		}
		if(discountType==0){
			realTatol=payShould-(registeCount*discounte);
		}	
	//	debugger
   		 $('input[name="payReal"]').attr("value",'');//清空内容 
        $('input[name="payReal"]').val(realTatol.toFixed(2));//填充内容
        $('#payReal').val(realTatol.toFixed(2));//填充内容   
    });
    $(document).on('change','#discountType',function(e){
		var realTatol=0;
	 
	 	var discounte= $('.discounte-js').val();
		var registeCount= $('#registeCount').val();
		var payShould= $('#payShould').val();
		var discountType=$('#discountType option:selected').val();
		if(discountType==1){
			realTatol=payShould*discounte;//折扣
		}
		if(discountType==0){
			realTatol=payShould-(registeCount*discounte);//减免
		}	
	//	debugger
   		 $('input[name="payReal"]').attr("value",'');//清空内容 
        $('input[name="payReal"]').val(realTatol.toFixed(2));//填充内容
        $('#payReal').val(realTatol.toFixed(2));//填充内容   
    });
	</script> 
  </body>
</html>
