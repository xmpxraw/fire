<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
	<%@include file="/pages/include/bootstrap.jsp"%>
</head>
<body style="background-color: #ecf0f5;">
      <div class="content">
        
       <div class="box box-primary">
           <div class="box-header with-border">
             <h3 class="box-title">关于我们</h3>
           </div><!-- /.box-header -->
           <div class="box-body table-responsive no-padding">
             <div class="pad margin no-print">
	          <div class="callout callout-info" style="margin-bottom: 0!important;">
	            <h4><i class="fa fa-phone"></i> 服务电话</h4>
	            13631414080
	          </div>
	        </div>
           </div><!-- /.box-body -->
        </div>
          
        <div class="box box-primary">
            <div class="box-header with-border">
              <h3 class="box-title">更新日志</h3>
            </div><!-- /.box-header -->
            <div class="box-body">
              <ul class="timeline timeline-inverse">
		    	<c:forEach var="entry" items="${logmap}" varStatus="status">
		          <!-- timeline time label -->
		          <li class="time-label">
		            <span class="${status.first?'bg-red':'bg-green'}">
		              ${entry.key}
		            </span>
		          </li>
		          <!-- /.timeline-label -->
		          
		          <c:set var="log" value="${entry.value}" />
		          <li>
		            <i class="fa fa-history bg-aqua"></i>
		            <div class="timeline-item">
		              <span class="time"><i class="fa fa-clock-o"></i> <fmt:formatDate value="${log.createtime}" pattern="HH:mm"/></span>
		              <h3 class="timeline-header"><a href="#">${log.name}</a> </h3>
		              <div class="timeline-body">
		                ${log.content}
		              </div>
		              <div class="timeline-footer">
		              	<span style="font-family:'楷体'">${log.username} <fmt:formatDate value="${log.createtime}" pattern="yyyy-MM-dd HH:mm"/></span>
		              </div>
		            </div>
		          </li>
		    	</c:forEach>
	        </ul>
            </div><!-- /.box-body -->
          </div>
      </div>
      <div class="main-footer no-print" style="margin-left: 0px;">
        <div class="pull-right hidden-xs">
          <b>Version</b> 1.0
        </div>
        <strong>Copyright &copy; 2016-2017 合同管理系统</strong> 版权所有&nbsp;&nbsp;服务电话：13631414080
      </div>
</body>
</html>
