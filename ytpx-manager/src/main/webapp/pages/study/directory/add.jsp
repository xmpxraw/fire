<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<%@include file="/pages/include/base-head.jsp"%>
  </head>
  <body>
		<div title="目录新增" style="padding:10px">
			<ul id="dir_tree"></ul>
			<form id="add-form" class="dialog-data-form">
		    	<table class="data-table" cellspacing="2" width="550" height="300">
		    		<tbody>
		    			<tr>
		    				<input type="hidden" name="id" value="${directory.id }" />
		    				<input type="hidden" name="code" value="${directory.code }" />
		    				<input type="hidden" name="createTime" value="${directory.createTime }" />
		    				<td class="field">目录名称</td>
		    				<td colspan="5">
		    					<input name="name" value="${directory.name }" type="text" class="tr_input easyui-validatebox" maxlength="255"
		    						data-options="required:true" style="width:250px;"/>
		    				</td>
		    			</tr>
		    			<tr>
		    				<td class="field">排序</td>
		    				<td>
		    					<input name="sortNo" type="text" value="${directory.sortNo }" class="tr_input easyui-validatebox" maxlength="16" style="width:250px;"/>
		    				</td>
		    			</tr>
		    			<tr>
		    				<td class="field">上级目录</td>
		    				<td>
		    				 	<input id="combo_tree_parent" class="easyui-combotree" name="parentId"  style="width:250px;">
		    				 	<script type="text/javascript">
		    				 	$("#combo_tree_parent").combotree({
		    				 		url:'tree_select_data',
		    				 		method:'post',
		    				 		value: '${directory.parentId}',
		    				 		label:'选择父级:',
		    				 		labelPosition:'top'
		    				 	});		    				 	
		    				 	</script>
		    			 	</td>
		    			</tr>
		    			<tr>
		    				<td class="field">重要程度</td>
		    				<td>
		    					<c:forEach var = "s" items="1,2,3,4,5">
		    						<input type="radio" ${directory.significance==null || directory.significance==s? "checked=\"checked\"":"" } style="margin-left: 20px" name="significance" value="${s }">${s }星</input>
		    					</c:forEach>
		    				</td>
		    			</tr>
		    			<tr>
		    				<td class="field">状态</td>
		    				<td>
		    					<input type="radio" ${directory.status == null || directory.status==1? "checked=\"checked\"":""} style="margin-left: 20px" name="status" value="1">启用</input>
		    					<input type="radio" ${directory.status==0? "checked=\"checked\"":""} style="margin-left: 20px" name="status" value="0">禁用</input>
		    				</td>
		    			</tr>
		    			<tr>
							<td class="field">目录标签</td>
							<td width="85%">
								<c:forEach var='t' varStatus="loop" items="通用目录,章节练习目录,在线读书目录,教学视频目录">
									<input type="checkbox" onchange="tagChange();" ${fn:contains(directory.tag,t) || loop.index == 0 ? "checked=\"checked\"":"" } style="margin-left: 20px" name="tags" value="${t }">${t }</input>
								</c:forEach>
								<c:choose>
									<c:when test="${directory.tag == null}">
										<input type="hidden" name="tag" value="通用目录"/>
									</c:when>
									<c:otherwise>
										<input type="hidden" name="tag" value="${directory.tag }"/>
									</c:otherwise>
								</c:choose>
		    					<script type="text/javascript">
		    						function tagChange() {
		    							var chkIds = "";  
		    							$("input:checkbox:checked").each(function(i){  
		    							    chkIds += $(this).val() + ",";  
		    							});
		    							if (chkIds == "") {
		    								top.showInfo('至少保留一个目录标签');
		    							}
		    							$("input[name='tag']").val(chkIds);
		    						}
		    					</script>
		    				</td>
						</tr>
		    		</tbody>
		    	</table>
		   	</form>
		</div>
  </body>
</html>
