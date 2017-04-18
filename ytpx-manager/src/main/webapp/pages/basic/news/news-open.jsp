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
	              <h3 class="box-title">${news.name}</h3>
	            </div><!-- /.box-header -->
	            <div class="box-body">
		           ${news.content}
	            </div><!-- /.box-body -->
	            <div class="box-footer clearfix">
                  阅读次数：${news.visitcount} 
                  <div class="pull-right">
	                  发布时间：<fmt:formatDate value="${news.createtime}" pattern="yyyy-MM-dd HH:mm"/>
                  </div>
                </div>
          </div>
       </div>
     </div>
  </div>
<%@include file="/pages/include/bootstrap-js.jsp"%>
</body>
</html>
