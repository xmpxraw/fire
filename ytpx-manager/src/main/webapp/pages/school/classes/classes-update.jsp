<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<%@include file="/pages/include/base-head.jsp"%>
  	
  </head>
  <body>
  <script type="text/javascript">
    var TYPE_EMP=1;	//班级类型
	$(function() {
		var classType=$('#data-form')[0].classType.value;
		if(classType==TYPE_EMP){
			$('#data-form tr.hidden').show();
		} else {
			$('#data-form tr.hidden').hide();
		}
});	
	</script>
  	<div class="easyui-tabs" 
  		data-options="border:false,fit:true,plain:true">
		<div title="班级信息" style="padding:10px">
	  		<form id="data-form" class="dialog-data-form">
		    	<table class="data-table" cellspacing="4">
		    		<tbody>
		    		<%-- <tr >	    			
		    				<td class="field">班级名称</td>
		    				<td>
		    					<input name="className" type="text"  style="width: 200px;" data-options="required:true"   class="field easyui-validatebox" maxlength="16" value="${classes.className}"/>
		    				</td>
		    				
		    			</tr> --%>
		    			<tr >	    			
		    				<td class="field">班级类型</td>
		    				<td>
		    					<w:data code="school.classes.classType" name="classType" style="width: 200px;"  selectedValue="${classes.classType}" onchange="school.classes.changeClassType();" />
		    				</td>
		    				
		    			</tr>
		    		<tr class="hidden">
		    			<td class="field">学习期数</td>
		    				<td>
		    					<%-- <input name="termCode" type="text"  style="width: 200px;"  class="field easyui-validatebox" maxlength="16" value="${classes.termCode}"/> --%>
		    					<div class="form-group">
	    							<select id="termCode" name="termCode" class="form-control" style="width: 200px;" >
	    								<option value="${classes.termCode}" selected="selected">${classes.term}</option>
				                        <c:forEach var="c" items="${terms}">
				                        <c:if test="${c.id!=classes.termCode}">
				                        	<option value="${c.termCode}">${c.term}</option>
				                        	</c:if>
				                        </c:forEach>
				                    </select>
			       				</div> 
		    				</td>
		    			</tr>
		    			<tr >	    			
		    				<td class="field">专业</td>
		    				<td>
		    					<w:data code="school.term.specialty" name="specialty" style="width: 200px;"   cssClass="field" selectedValue="${classes.specialty}"/>
		    				</td>
		    				
		    			</tr>
		    			<tr>
		    				<td class="field">班级数量</td>
		    				<td colspan="3" >
		    					<input name="classCount" type="text" style="width: 200px;"  data-options="required:true"  class="field-title easyui-numberbox validatebox-text" maxlength="150" value="${classes.classCount}"/>:(个)
		    				</td>
		    				
		    			</tr>
		    			<tr>
		    			<td class="field">开班时间</td>
		    				<td>
		    					<!-- <input name="beginTime" type="text" style="width: 200px"  class="field easyui-validatebox" maxlength="16"/> -->
		    					<input class="easyui-datebox" name="beginTime"
  								  data-options="required:true" value="${classes.beginTime}" style="width: 200px;" />
		    				</td>
		    				
		    			</tr>
		    			<tr>
		    			<td class="field">学习天数</td>
		    				<td>
		    					<input name="period" type="text"  style="width: 200px;" data-options="required:true"   class="field easyui-numberbox" maxlength="16" value="${classes.period}" readonly="readonly"/>:(天)
		    				</td>
		    			</tr>
		    			<tr>
		    			<td class="field">分班学员数</td>
		    				<td>
		    					<input name="planCount" type="text"  style="width: 200px;" data-options="required:true"   class="field easyui-numberbox" maxlength="16" value="${classes.planCount}" readonly="readonly"/>:(人)
		    				</td>
		    			</tr>
		    		</tbody>
		    	</table>
	    	</form>
   		</div>
	</div>
	
  </body>
</html>
