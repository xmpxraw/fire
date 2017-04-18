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
		var classType=$('#add-form')[0].classType.value;
		if(classType==TYPE_EMP){
			$('#add-form tr.hidden').show();
		} else {
			$('#add-form tr.hidden').hide();
		}
		var period=0;
    	var beginTime="0";
    	var id=0;
    	var termSelected=$('.addTerm option:selected').val();
    	var periods=new Array();
    	var beginTimes=new Array();
    	var ids=new Array();
		var str="${terms}";
	    var strArray=str.split('],');
     	for(var i=0;i<strArray.length;i++){
     		id=strArray[i].substring(strArray[i].indexOf("termCode=")+9,strArray[i].indexOf(",years"));
    		ids.push(id)
     	 	period=strArray[i].substring(strArray[i].indexOf("duration=")+9,strArray[i].indexOf(",classCount"));
    		periods.push(period)
    		beginTime=strArray[i].substring(strArray[i].indexOf("beginTime=")+10,strArray[i].indexOf(",createTime"));
    		beginTimes.push(beginTime)  		
    	}
     	var periodStr=null;
     	var beginTimeStr=null;
    	for(var i=0;i<ids.length;i++){
    		if(termSelected==ids[i]){
    			periodStr=periods[i];
    			beginTimeStr=beginTimes[i];
    		}

    	}
     
    	$('input[name=beginTime]').attr("readonly","readonly") 
  //  	$(".easyui-datebox :text").attr("readonly","readonly");
    	$('input[name=period]').attr("readonly","readonly")
        $('input[name="period"]').attr("value",'');//清空内容 
        $('input[name="period"]').val(periodStr);//填充内容
        $('#period').val(periodStr);//填充内容
        $('input[name="beginTime"]').attr("value",'');//清空内容 
        $('input[name="beginTime"]').val(beginTimeStr);//填充内容
        $('#beginTime').val(beginTimeStr);//填充内容
});	
	</script>
  	<div class="easyui-tabs"
  			data-options="border:false,fit:true,plain:true">
		<div title="班级信息" style="padding:10px">
			<form id="add-form" class="dialog-data-form">
		    	<table class="data-table" style="width: 98%;">
		    		<tbody>
		    		<%-- <tr >	    			
		    				<td class="field">班级名称</td>
		    				<td >
		    					 <input name="className" type="text"  style="width: 200px;"  data-options="required:true"  class="field easyui-validatebox" maxlength="16"   value="${classes.className}"/>
		    				</td>
		    				
		    			</tr> --%>
		    			<tr >	    			
		    				<td class="field">班级类型</td>
		    				<td >
		    					<w:data code="school.classes.classType" style="width: 200px;" name="classType" cssClass="field" selectedValue="" onchange="school.classes.changeClassesType();" />
		    				</td>
		    				
		    			</tr>
		    			<tr class="">
		    			<td class="field">学习期数</td>
		    				<td>
		    					<!-- <input name="termCode" type="text"  style="width: 100%"  class="field easyui-validatebox" maxlength="16"/> -->
		    					<div class="form-group">
	    							<select id="termCode" name="termCode" class="form-control addTerm" data-options="required:true"   style="width: 200px;" >
	    							
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
		    					<w:data code="school.term.specialty" name="specialty" style="width: 200px;"   cssClass="field" selectedValue=""/>
		    				</td>
		    				
		    			</tr>
		    			<tr>
		    				<td class="field">班级数量</td>
		    				<td >
		    					<input name="classCount" type="text" style="width: 200px;"  data-options="required:true"   class="field easyui-validatebox easyui-numberbox" />:(个)
		    				</td>
		    				
		    			</tr>
		    			<tr>
		    			<td class="field">开班时间</td>
		    				<td id="">
		    					<input type="text"  class="field " name="beginTime" 
  								style="width: 200px;"/>
		    				</td>
		    				
		    			</tr>
		    			<tr>
		    			<td class="field">学习天数</td>
		    				<td>
		    					<input id="period" name="period" type="text" data-options="required:true"  style="width: 200px;"   class="field easyui-validatebox easyui-numberbox" maxlength="16" />:(天)
		    				</td>
		    			</tr>
		    			<tr>
		    			<td class="field">分班学员数</td>
		    				<td>
		    					<input id="planCount" name="planCount" type="text"   data-options="required:true"  style="width: 200px;" class="field easyui-validatebox easyui-numberbox" maxlength="16" />:(人)
		    				</td>
		    			</tr>
		    		</tbody>
		    	</table>
		   	</form>
		</div>
	</div>
	<script type="text/javascript">  
	  $(document).on('change','.addTerm',function(e){
		  var period=0;
	    	var beginTime="0";
	    	var id=0;
	    	var termSelected=$('.addTerm option:selected').val();
	    	var periods=new Array();
	    	var beginTimes=new Array();
	    	var ids=new Array();
			var str="${terms}";
		    var strArray=str.split('],');

	     	for(var i=0;i<strArray.length;i++){
	     		id=strArray[i].substring(strArray[i].indexOf("termCode=")+9,strArray[i].indexOf(",years"));
	    		ids.push(id)
	     	 	period=strArray[i].substring(strArray[i].indexOf("duration=")+9,strArray[i].indexOf(",classCount"));
	    		periods.push(period)
	    		beginTime=strArray[i].substring(strArray[i].indexOf("beginTime=")+10,strArray[i].indexOf(",createTime"));
	    		beginTimes.push(beginTime)  		
	    	} 
	    	for(var i=0;i<ids.length;i++){
	    		if(termSelected==ids[i]){
	        		period=periods[i];
	        		beginTime=beginTimes[i];
	    		}

	    	}
	     
	    	
	        $('input[name="period"]').attr("value",'');//清空内容 
	        $('input[name="period"]').val(period);//填充内容
	        $('#period').val(period);//填充内容
	        $('input[name="beginTime"]').attr("value",'');//清空内容 
	        $('input[name="beginTime"]').val(beginTime);//填充内容
	        $('#beginTime').val(beginTime);//填充内容
    }); 
	</script>
  </body>
</html>
