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
              <h3 class="box-title">最新动态</h3>
            </div><!-- /.box-header -->
            <div class="box-body table-responsive no-padding">
              <table class="table table-hover table-striped">
                <tbody><tr>
                  <th>序号</th>
                  <th>标题</th>
                  <th>发布人</th>
                  <th>发布时间</th>
                </tr>
                <c:forEach var="news" items="${pagin.dataList}" varStatus="status">
	                <tr>
	                  <td>${status.count}</td>
	                  <td><a class="news-link" href="javascript:void(0);" onclick="top.addTab('','${news.name}','${contextpath}/basic/news/open/${news.id}');">${news.name}</a></td>
	                  <td>${news.username}</td>
	                  <td>
	                  	<fmt:formatDate value="${news.createtime}" pattern="yyyy-MM-dd HH:mm"/>
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
