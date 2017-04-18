<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<%@include file="/pages/include/base-head.jsp"%>
  </head>
  <body>
  	<div class="easyui-tabs" 
  		data-options="border:false,fit:true,plain:true">
		<div title="学员详细信息" style="padding:10px">
	  		<form id="data-form" class="dialog-data-form">
		    	<table class="data-table" cellspacing="4">
		    		<tbody>
		    			<tr>
		    				<td class="field">学生姓名</td>
		    				<td>
		    					<input name="schoolName" type="text" class="field easyui-validatebox"  style="width: 190px" maxlength="18" value="${student.studnetName}" readonly="readonly"/>
		    				</td>
		    				<td class="field">学号</td>
		    				<td>
		    					<input name="studentNo" type="text" class="field easyui-validatebox"  style="width: 190px" maxlength="18" value="${student.studentNo}" readonly="readonly"/>
		    				</td>
		    			
		    			</tr>
		    			<tr>	  
		    				<td class="field">身份证号</td>
		    				<td>
		    					<input name="idcard" type="text" class="field easyui-validatebox" maxlength="18" style="width: 190px" value="${student.idcard}" readonly="readonly"/>
		    				</td>
		    				<td class="field">手机号码</td>
		    				<td>
		    					<input name="mobile" type="text" class="field easyui-validatebox " style="width: 190px" maxlength="11" value="${student.mobile}" readonly="readonly"/>
		    				</td>
		    			</tr>
		    			<tr>	  
		    				<td class="field">学员状态</td>
		    				<td>
		    					<input name="status" type="text" class="field easyui-validatebox" maxlength="18" style="width: 190px" value="${status}" readonly="readonly"/>
		    				</td>
		    				<td class="field">所在班级</td>
		    				<td>
		    					<input name="classCode" type="text" class="field easyui-validatebox " style="width: 190px" maxlength="11" value="${student.classCode}" readonly="readonly"/>
		    				</td>
		    			</tr>
		    			<tr>	  
		    				<td class="field">报名日期</td>
		    				<td>
		    					<input name="registTime" type="text" class="field easyui-validatebox " style="width: 190px" maxlength="11" value="${student.registTime}" readonly="readonly"/>
		    				</td>
		    				<td class="field">所属单位</td>
		    				<td>
		    					<input name="company" type="text" class="field easyui-validatebox " style="width: 190px" maxlength="11" value=" ${company}" readonly="readonly"/>
		    				</td>
		    			</tr>	
		    			<tr>	  
		    				<td class="field">费用方案</td>
		    				<td>
		    					<input name="payScheme" type="text" class="field easyui-validatebox " style="width: 190px" maxlength="11" value="${payScheme}" readonly="readonly"/>
		    				</td>
		    				<td class="field">缴费状态</td>
		    				<td>
		    					<input name="payStatus" type="text" class="field easyui-validatebox " style="width: 190px" maxlength="11" value="${payStatus}" readonly="readonly"/>
		    				</td>
		    			</tr>
		    			<tr>	  
		    			<td class="field">应缴费用</td>
		    				<td>
		    					<input name="payShould" type="text" class="field easyui-validatebox " style="width: 190px" maxlength="11" value="${student.payShould}" readonly="readonly"/>
		    				</td>
		    				<td class="field">实缴费用</td>
		    				<td>
		    					<input name="payReal" type="text" class="field easyui-validatebox " style="width: 190px" maxlength="11" value="${student.payReal}" readonly="readonly"/>
		    				</td>	    				
		    			</tr>
		  				<tr>	  
		    			<td class="field">考试成绩</td>
		    				<td>
		    					<input name="commonScore" type="text" class="field easyui-validatebox " style="width: 190px" maxlength="11" value="${student.commonScore}" readonly="readonly"/>
		    				</td>
		    				<td class="field">国考成绩</td>
		    				<td>
		    					<input name="nationScore" type="text" class="field easyui-validatebox " style="width: 190px" maxlength="11" value="${student.nationScore}" readonly="readonly"/>
		    				</td>	    				
		    			</tr>
		    			
		    			<c:forEach var="studentScore" items="${studentScores}" varStatus="index">
		    			<th colspan="4"> [第${index.count}次成绩]</th>
		    			<tr>	  
		    				<td class="field">理论成绩</td>
		    				<td>
		    					<input name="scoreLl" type="text" class="field easyui-validatebox " style="width: 190px" maxlength="11" value="${studentScore.scoreLl}" readonly="readonly"/>
		    				</td>
		    				<td class="field">水系统成绩</td>
		    				<td>
		    					<input name="scoreSxt" type="text" class="field easyui-validatebox " style="width: 190px" maxlength="11" value="${studentScore.scoreSxt}" readonly="readonly"/>
		    				</td>	    				
		    			</tr>
		    			<tr>	  
		    				<td class="field">控制室成绩</td>
		    				<td>
		    					<input name="scoreKzs" type="text" class="field easyui-validatebox " style="width: 190px" maxlength="11" value="${studentScore.scoreKzs}" readonly="readonly"/>
		    				</td>
		    				<td class="field">防火巡查成绩</td>
		    				<td>
		    					<input name="scoreFhxc" type="text" class="field easyui-validatebox " style="width: 190px" maxlength="11" value="${studentScore.scoreFhxc}" readonly="readonly"/>
		    				</td>	    				
		    			</tr>
		    			<tr>	  
		    				<td class="field">气体灭火成绩</td>
		    				<td>
		    					<input name="scoreQtmh" type="text" class="field easyui-validatebox " style="width: 190px" maxlength="11" value="${studentScore.scoreQtmh}" readonly="readonly"/>
		    				</td>
		    				<td class="field">总成绩</td>
		    				<td>
		    					<input name="scoreTotal" type="text" class="field easyui-validatebox " style="width: 190px" maxlength="11" value="${studentScore.scoreTotal}" readonly="readonly"/>
		    				</td>	    				
		    			</tr>				                    
				                        	
				        </c:forEach>
		    		</tbody>
		    	</table>
	    	</form>
   		</div>
	</div>
  </body>
</html>
