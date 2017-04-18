<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
	<%@include file="/pages/include/bootstrap.jsp"%>
</head>
<body style="background-color: #ecf0f5;">
  <div class="content">
  	<div class="row">
       <div class="col-md-12">
       		<div class="box box-primary">
	            <div class="box-header with-border">
	              <h3 class="box-title">
	              	<c:choose>
                  		<c:when test="${feedback.resolvestatus==1}">
		                  	<span class="badge bg-aqua">新反馈</span>
                  		</c:when>
                  		<c:when test="${feedback.resolvestatus==5}">
		                  	<span class="badge bg-purple">已受理</span>
                  		</c:when>
                  		<c:when test="${feedback.resolvestatus==10}">
		                  	<span class="badge bg-green">已解决</span>
                  		</c:when>
                  		<c:when test="${feedback.resolvestatus==-1}">
		                  	<span class="badge">未受理</span>
                  		</c:when>
                  	</c:choose>
	              	${feedback.name}
	              </h3>
	            </div><!-- /.box-header -->
	            <div class="box-body">
		           ${feedback.content}
	            </div><!-- /.box-body -->
	            <div class="box-footer clearfix">
                  阅读次数：${feedback.visitcount} 
                  <div class="pull-right">
	                  反馈时间：<fmt:formatDate value="${feedback.createtime}" pattern="yyyy-MM-dd HH:mm"/>
                  </div>
                </div>
          </div>
          
          
          <div class="box box-info">
            <div class="box-header with-border">
              <h3 class="box-title">讨论区</h3>
            </div><!-- /.box-header -->
            <div class="box-body">
	             <ul class="timeline timeline-inverse">
			    	<c:forEach var="comment" items="${comments}" varStatus="status">
			          <li>
			            <i class="fa fa-user bg-purple"></i>
			            <div class="timeline-item">
			              <div class="timeline-body">
			                ${comment.content}
			              </div>
			              <div class="timeline-footer">
			              	<span style="font-family:'楷体'">${comment.username} <fmt:formatDate value="${comment.createtime}" pattern="yyyy-MM-dd HH:mm"/></span>
			              </div>
			            </div>
			          </li>
			    	</c:forEach>
		        </ul>
            </div><!-- /.box-body -->
            <div class="box-footer">
              <div class="input-group">
                <input class="form-control" placeholder="请输入讨论内容" id="content">
                <div class="input-group-btn">
                  <button class="btn btn-success" onclick="addComment('${feedback.id}');"><i class="fa fa-plus"></i></button>
                </div>
              </div>
            </div>
          </div>
          
       </div>
     </div>
  </div>
<%@include file="/pages/include/bootstrap-js.jsp"%>
<script type="text/javascript">
function addComment(feedbackid) {
	if(String.isEmpty($('#content').val())) {
		showInfo('请输入讨论内容!');
		return;
	}
	$.post('${contextpath}/basic/feedback/addcomment/'+feedbackid,{content:$('#content').val()},function(msg) {
		if(msg.code!=200) {
			showInfo('提交失败,请联系管理员!');
			return;
		}
		window.location.reload();
	});
}
</script>
</body>
</html>
