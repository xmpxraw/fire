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
			<form id="add-form" class="dialog-data-form">
		    	<table class="data-table" style="width: 98%;">
		    		<tbody>
		    			<tr >	    			
		    				<td class="field" >方案名称</td>
		    				<td colspan="3">
		    						<input name="schemeName" type="text"  data-options="required:true"  style="width: 100%"  class="field easyui-validatebox" maxlength="16"/>
		    				</td>
		    				
		    			</tr>
		    			<tr>
		    			<td class="field">培训费用</td>
		    				<td>
		    					<input name="feesTrain" type="text" data-options="required:true"  class="field easyui-numberbox customs-js" precision="2" />
		    				</td>
		    				<td class="field">餐费</td>
		    				<td >
		    					<input name="feesMeal" type="text" data-options="required:true"   class="field easyui-numberbox customs-js" precision="2" />
		    				</td>
		    				
		    			</tr>
		    			<tr>
		    			<td class="field">住宿费</td>
		    				<td>
		    					<input name="feesHotel" type="text"  data-options="required:true"  class="field easyui-numberbox customs-js" precision="2" />
		    				</td>
		    				<td class="field">鉴定费</td>
		    				<td>
		    					<input  name="feesStudy" type="text"  data-options="required:true"  class="field easyui-numberbox customs-js" precision="2" />
		    				</td>
		    			</tr>
		    			<tr>
		    			<td class="field" >总费用</td>
		    				<td >
		    					<input id="feesTotal" data-options="required:true" name="feesTotal" type="text"  class="field easyui-numberbox" precision="2"  readonly="readonly"/>
		    				</td>
		    			</tr>
		    			<tr style="height: 180px;">
							<td class="field">方案说明</td>
							<td colspan="3" >
								<textarea style="width: 100%;height: 100%!important;" class="field-detailDesc easyui-validatebox" name="schemeDesc" 
									data-options="validType:'length[0,520]',invalidMessage:'不能超过520个字符！'"></textarea>
							</td>
						</tr>						
		    		</tbody>
		    	</table>
		   	</form>
		</div>
	</div>
	<script type="text/javascript">  
    $(document).on('change','.customs-js',function(e){
    	var feesTatol=0;
        var currentTarget = $(e.currentTarget);
        console.log(1);
        $.each($('.customs-js'), function(index, val) {
         var num = $(val).val();
         if(num == '') num = 0;
     /*     debugger */
         feesTatol += parseFloat(num);
        });
        $('input[name="feesTotal"]').attr("value",'');//清空内容 
        $('input[name="feesTotal"]').val(feesTatol.toFixed(2));//填充内容
        $('#feesTotal').val(feesTatol.toFixed(2));//填充内容
    });
	</script>
  </body>
</html>
