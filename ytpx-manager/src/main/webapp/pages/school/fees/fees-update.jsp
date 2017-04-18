<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<%@include file="/pages/include/base-head.jsp"%>
  </head>
  <body>
  	<div class="easyui-tabs" 
  		data-options="border:false,fit:true,plain:true">
		<div title="费用方案" style="padding:10px">
	  		<form id="update-form" class="dialog-data-form">
		    	<table class="data-table" cellspacing="4" style="width: 98%;">
		    		<tbody>
		    			<tr >	    			
		    				<td class="field" >方案名称</td>
		    				<td colspan="3">
		    						<input name="schemeName" type="text"  data-options="required:true"  style="width: 100%"  class="field easyui-validatebox" maxlength="16" value="${fees.schemeName}"/>
		    				</td>
		    				
		    			</tr>
		    			<tr>
		    			<td class="field">培训费用</td>
		    				<td>
		    					<input name="feesTrain" type="text" data-options="required:true"  class="field easyui-numberbox custom-js" precision="2" value="${fees.feesTrain}"/>
		    				</td>
		    				<td class="field">餐费</td>
		    				<td >
		    					<input name="feesMeal" type="text" data-options="required:true"   class="field easyui-numberbox custom-js" precision="2" value="${fees.feesMeal}"/>
		    				</td>
		    				
		    			</tr>
		    			<tr>
		    			<td class="field">住宿费</td>
		    				<td>
		    					<input name="feesHotel" type="text"  data-options="required:true"  class="field easyui-numberbox custom-js" precision="2" value="${fees.feesHotel}" />
		    				</td>
		    				<td class="field">鉴定费</td>
		    				<td>
		    					<input  name="feesStudy" type="text"  data-options="required:true"  class="field easyui-numberbox custom-js" precision="2" value="${fees.feesStudy}"/>
		    				</td>
		    			</tr>
		    			<tr>
		    			<td class="field" >总费用</td>
		    				<td >
		    					<input id="feesTotal" data-options="required:true" name="feesTotal" type="text" class="field easyui-numberbox" precision="2"  readonly="readonly" value="${fees.feesTotal}"/>
		    				</td>
		    			</tr>
		    			<tr style="height: 180px;">
							<td class="field">方案说明</td>
							<td colspan="3" >
								<textarea style="width: 100%;height: 100%!important;" class="field-detailDesc easyui-validatebox" name="schemeDesc" 
									data-options="validType:'length[0,520]',invalidMessage:'不能超过520个字符！'">${fees.schemeDesc}</textarea>
							</td>
						</tr>		
		    		</tbody>
		    	</table>
	    	</form>
   		</div>
	</div>
	<script type="text/javascript">
	/* 
	var 	feesTatol=0;
	$("tbody").click(function() {
	
		 feesTatol= parseFloat($('input[name="feesTrain"]').val())+parseFloat($('input[name="feesMeal"]').val())+parseFloat($('input[name="feesHotel"]').val())+parseFloat($('input[name="feesStudy"]').val());
		 $('input[name="feesTotal"]').attr("value",'');//清空内容 
		$('input[name="feesTotal"]').attr("value",feesTatol.toFixed(2));
	}); */
	 $(document).on('change','.custom-js',function(e){
	    	var feesTatol=0;
	        var currentTarget = $(e.currentTarget);
	        console.log(1);
	        $.each($('.custom-js'), function(index, val) {
	         var num = $(val).val();
	         if(num == '') num = 0;
	         feesTatol += parseFloat(num);
	        });
	        $('input[name="feesTotal"]').attr("value",'');//清空内容 
	        $('input[name="feesTotal"]').val(feesTatol.toFixed(2));//填充内容
	        $('#feesTotal').val(feesTatol.toFixed(2));//填充内容
	    });
	</script>
  </body>
</html>
