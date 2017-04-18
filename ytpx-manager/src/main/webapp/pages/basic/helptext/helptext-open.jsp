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
	              <h3 class="box-title">${helptext.name}</h3>
	            </div><!-- /.box-header -->
	            <div class="box-body">
		           ${helptext.content}
	            </div><!-- /.box-body -->
	            <div class="box-footer clearfix">
                  阅读次数：${helptext.visitcount} 
                  <div class="pull-right">
	                  发布时间：<fmt:formatDate value="${helptext.createtime}" pattern="yyyy-MM-dd HH:mm"/>
                  </div>
                </div>
          </div>
       </div>
     </div>
  </div>
<%@include file="/pages/include/bootstrap-js.jsp"%>
</body>
</html>
