<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<%@include file="/pages/include/base-head.jsp"%>
  </head>
  <body>
  		<form id="data-form" class="dialog-data-form">
	    	<table class="data-table" cellspacing="4">
	    		<tbody>
	    			<tr>
	    				<td class="field">标题</td>
	    				<td>
	    					<input name="name" type="text" class="easyui-validatebox field-title" maxlength="100" value="${changelog.name}"
		    						data-options="required:true"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="field">发布内容</td>
	    				<td>
	    					<script id="content" name="content" type="text/plain" style="width:100%;height:250px;">${changelog.content}</script>
	    					<script type="text/javascript">
						        var um = UM.getEditor('content');
						    </script>
	    				</td>
	    			</tr>
	    		</tbody>
	    	</table>
    	</form>
  </body>
</html>
