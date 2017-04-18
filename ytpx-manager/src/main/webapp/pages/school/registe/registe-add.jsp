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
			<form id="add-form" class="dialog-data-form" method="post" enctype="multipart/form-data">
		    	<table class="data-table" style="width: 98%;">
		    		<tbody>
		    			<tr >	    			
		    				<td class="field" >报名编码</td>
		    				<td>
		    					<input name="registeCode" type="text"  style="width: 90%" value="${registCode}"  readonly="readonly"  class="field easyui-validatebox" maxlength="16" />
		    				</td>
		    				
		    			</tr>
		    			<tr>
		    				<td class="field">报名单位</td>
		    				<td colspan="3" >
		    				<div class="form-group">
	    							<select id="companyCode" name="companyCode" class="form-control" style="width: 90%" >   						
				                        <c:forEach var="c" items="${companys}">		                    
				                        	<option value="${c.id}">${c.companyName}</option>
				                        </c:forEach>
				                    </select>
			       				</div> 
		    				</td>	    				
		    			</tr>
		    			<tr>
		    				<td class="field">所在期数</td>
		    				<td colspan="3" >
		    					<div class="form-group">
	    							<select id="termCode" name="termCode" class="form-control" style="width: 90%" >   						
				                        <c:forEach var="c" items="${terms}">		                    
				                        	<option value="${c.termCode}">${c.term}</option>
				                        </c:forEach>
				                    </select>
				                    </div>  
		    				</td>	    				
		    			</tr>
		    			<tr>
		    			<td class="field">学员人数</td>
		    				<td>
		    					<input name="registeCount" type="text" data-options="required:true" style="width: 193px"  class="field easyui-validatebox easyui-numberbox" maxlength="16"/>
		    				</td>    				
		    			</tr>
		    			<tr>
		    			<td class="field">学员信息</td>
		    				<td>
		    				<input name="file" type="file" class="field-title easyui-validatebox" data-options="required:true"/>
		    				<!-- <input id="uploadExcel" name="uploadExcel"  type="file" class="easyui-filebox" style="width:150px" data-options="prompt:'请选择文件...'"/>   -->
     					　<!-- <a href="#" class="" style="width:8px" onclick="uploadExcel()" >上传</a>   -->
		    				<!-- <input class="easyui-filebox" name="file1" data-options="onChange:function(){alert($(this).filebox('getValue'))},prompt:'Choose a file...'" style="width:100%"/> -->
		    				</td>    				
		    			</tr>
						<tr>
		    				<td class="field">缴费方案</td>
		    				<td colspan="3" >
		    				<div class="form-group">
	    							<select id="payScheme" name="payScheme" class="form-control" style="width: 90%"  >   						
				                        <c:forEach var="c" items="${fees}">		                    
				                        	<option value="${c.id}">${c.schemeName}</option>
				                        </c:forEach>
				                    </select>
			       				</div> 
		    				</td>	    				
		    			</tr>
		    			<tr>
		    			<td class="field">延时缴费</td>
		    				<td>
		    				
		    					<input name="payDelay" type="checkbox"  value="1" style="width: 20%"  class="field easyui-validatebox" />
		    				</td>    				
		    			</tr>
		    		</tbody>
		    	</table>
		   	</form>
		</div>
	</div>
  </body>
</html>
