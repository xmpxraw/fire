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
       	<div class="box box-info">
            <div class="box-header with-border">
              <i class="fa fa-list"></i>
              <h3 class="box-title">意见反馈</h3>
              <div class="box-tools pull-right">
              	  <div class="btn-group">
			          <a href="javascript:void(0);" class="btn btn-xs btn-primary btn-flat pull-left" onclick="top.addTab('','提交意见','${contextpath}/basic/feedback/submit');">提交意见</a>
                  </div>
              </div>
            </div><!-- /.box-header -->
            <div class="box-body table-responsive no-padding">
              <table class="table table-hover table-striped">
                <tbody><tr>
                  <th>序号</th>
                  <th>标题</th>
                  <th>状态</th>
                  <th>反馈人</th>
                  <th>反馈时间</th>
                </tr>
                <c:forEach var="feedback" items="${pagin.dataList}" varStatus="status">
	                <tr>
	                  <td>${status.count}</td>
	                  <td><a class="feedback-link" href="javascript:void(0);" onclick="top.addTab('','${feedback.name}','${contextpath}/basic/feedback/open/${feedback.id}');">${feedback.name}</a></td>
	                  <td>
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
	                  </td>
	                  <td>${feedback.username}</td>
	                  <td>
	                  	<fmt:formatDate value="${feedback.createtime}" pattern="yyyy-MM-dd HH:mm"/>
	                  </td>
	                </tr>
                </c:forEach>
              </tbody></table>
            </div><!-- /.box-body -->
            <div class="box-footer clearfix no-border">
				<div class="box-tools pull-right">
				<c:if test="${pagin.pageCount>1}">
                  <ul class="pagination pagination-sm inline">
                    <li><a href="./homelist?page=1&rows=20">«</a></li>
                    <c:forEach var="page" begin="1" end="${pagin.pageCount}">
	                    <li><a href="./homelist?page=${page}&rows=20">${page}</a></li>
                    </c:forEach>
                    <li><a href="./homelist?page=${pagin.pageCount}&rows=20">»</a></li>
                  </ul>
				</c:if>
                </div>
            </div>
          </div>
         </div>
     </div>
  </div>
<%@include file="/pages/include/bootstrap-js.jsp"%>
</body>
</html>
