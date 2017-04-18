<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
	<%@include file="/pages/include/bootstrap.jsp"%>
	<style type="text/css">
		.info-box {
			cursor: pointer;
		}
		.info-box:hover .info-box-icon {
			opacity:0.6;
		}
		.info-box:hover .info-box-text {
			font-weight: bold;
		}
		a.todo-link:hover {
			text-decoration: underline;
		}
	</style>
</head>
<body style="background-color: #ecf0f5;">
  <div class="content">
  <%-- 	<div class="row">
       <div class="col-md-3 col-sm-6 col-xs-12">
         <div id="todo-box" class="info-box" onclick="top.addTab('','待办合同','${contextpath}/contract/query/todolist');">
           <span class="info-box-icon bg-aqua"><i class="glyphicon glyphicon-align-justify"></i></span>
           <div class="info-box-content">
             <span class="info-box-text">待办合同</span>
             <span class="info-box-number"><img alt="" src="${skin}/image/system/admin/main/loading.gif"></span>
           </div><!-- /.info-box-content -->
         </div><!-- /.info-box -->
       </div><!-- /.col -->
       <div class="col-md-3 col-sm-6 col-xs-12">
         <div id="trace-box" class="info-box" onclick="top.addTab('','我拟稿的合同','${contextpath}/contract/query/tracelist');">
           <span class="info-box-icon bg-red"><i class="glyphicon glyphicon-tags"></i></span>
           <div class="info-box-content">
             <span class="info-box-text">我拟稿的合同</span>
             <span class="info-box-number"><img alt="" src="${skin}/image/system/admin/main/loading.gif"></span>
           </div><!-- /.info-box-content -->
         </div><!-- /.info-box -->
       </div><!-- /.col -->

       <!-- fix for small devices only -->
       <div class="clearfix visible-sm-block"></div>

		<div class="col-md-3 col-sm-6 col-xs-12">
         <div id="my-box" class="info-box" onclick="top.addTab('','已办合同','${contextpath}/contract/query/mylist');">
           <span class="info-box-icon bg-yellow"><i class="glyphicon glyphicon-th"></i></span>
           <div class="info-box-content">
             <span class="info-box-text">已办合同</span>
             <span class="info-box-number"><img alt="" src="${skin}/image/system/admin/main/loading.gif"></span>
           </div><!-- /.info-box-content -->
         </div><!-- /.info-box -->
       </div><!-- /.col -->
       <div class="col-md-3 col-sm-6 col-xs-12">
         <div id="end-box" class="info-box" onclick="top.addTab('','已结束合同','${contextpath}/contract/query/endlist');">
           <span class="info-box-icon bg-green"><i class="glyphicon glyphicon-leaf"></i></span>
           <div class="info-box-content">
             <span class="info-box-text">已结束合同</span>
             <span class="info-box-number"><img alt="" src="${skin}/image/system/admin/main/loading.gif"></span>
           </div><!-- /.info-box-content -->
         </div><!-- /.info-box -->
       </div><!-- /.col -->
   </div><!-- /.row --> --%>
    
  <%--  <div class="row">
       <!-- Left col -->
       <div class="col-md-12 connectedSortable ui-sortable">
       	<div class="box box-info">
            <div class="box-header with-border">
              <i class="ion ion-clipboard"></i>
              <h3 class="box-title">待办合同</h3>
              <div class="box-tools pull-right">
              	  <c:if test="${pagin.pageCount>1}">
		              <a href="javascript:void(0);" class="btn btn-xs btn-info btn-flat pull-left" onclick="top.addTab('','待办合同','${contextpath}/contract/query/todolist');">查看更多</a>
              	  </c:if>
              </div>
            </div><!-- /.box-header -->
            <div class="box-body table-responsive no-padding">
              <table class="table table-hover table-striped">
                <tbody><tr>
                  <th>序号</th>
                  <th>类型</th>
                  <th>标题</th>
                  <th>合同金额</th>
                  <th>呈送日期</th>
                  <th>合同分类</th>
                  <th>经办人</th>
                  <th>经办科室</th>
                </tr>
                <c:forEach var="todo" items="${pagin.dataList}" varStatus="status">
	                <tr>
	                  <td>${status.count}</td>
	                  <td>
                   		<c:if test="${todo.type==1}">
		                   <span class="label label-primary">一般合同</span>
                   		</c:if>
                   		<c:if test="${todo.type==2}">
		                   <span class="label label-warning">重要合同</span>
                   		</c:if>
	                  </td>
	                  <td><a class="todo-link" href="javascript:void(0);" onclick="top.addTab('','${todo.title}','${contextpath}/contract/process/open/${todo.id}');">${todo.title}</a></td>
	                  <td><fmt:formatNumber value="${todo.price}" pattern="￥#,#00.00"/></td>
	                  <td>
	                  	<fmt:formatDate value="${todo.tasktime}" pattern="yyyy-MM-dd"/>
	                  	<c:if test="${todo.expireday<3}">
	                  		<span class="badge bg-aqua">新</span>
	                  	</c:if>
	                  	<c:if test="${todo.expireday>=3 && todo.expireday<=5}">
	                  		<span class="badge bg-orange"><i class="fa fa-clock-o"></i> ${todo.expireday}天</span>
	                  	</c:if>
	                  	<c:if test="${todo.expireday>5}">
	                  		<span class="badge bg-red"><i class="fa fa-clock-o"></i> ${todo.expireday}天</span>
	                  	</c:if>
	                  </td>
	                  <td>${todo.categoryname}</td>
	                  <td>${todo.organname}</td>
	                  <td>${todo.teamname}</td>
	                </tr>
                </c:forEach>
              </tbody></table>
            </div><!-- /.box-body -->
          </div>
         </div>
     </div> --%>
     
     <%-- <div class="row">
       <div class="col-md-4">
       	<div class="box box-success">
            <div class="box-header with-border">
              <h3 class="box-title">最新动态</h3>
			  <div class="box-tools pull-right">
		          <a href="javascript:void(0);" class="btn btn-xs btn-info btn-flat pull-left" onclick="top.addTab('','最新动态','${contextpath}/basic/news/homelist');">更多</a>
              </div>
            </div><!-- /.box-header -->
            <div class="box-body table-responsive no-padding">
              <table class="table table-condensed table-hover">
                <c:forEach var="news" items="${newslist}" varStatus="status">
	                <tr>
	                  <td>
	                  		<c:if test="${news.grade==2}">
	                  			<span class="badge bg-orange">重要</span>
	                  		</c:if>
	                  		<c:if test="${news.grade==3}">
	                  			<span class="badge bg-red">紧急</span>
	                  		</c:if>
	                  		<a class="todo-link" href="javascript:void(0);" 
	                  			onclick="top.addTab('','${news.name}','${contextpath}/basic/news/open/${news.id}');">${news.name}</a>
	                  </td>
	                </tr>
                </c:forEach>
              </tbody></table>
            </div><!-- /.box-body -->
          </div>
         </div>
       <div class="col-md-4">
       	<div class="box box-warning">
            <div class="box-header with-border">
              <h3 class="box-title">意见反馈</h3>
              <div class="box-tools pull-right">
              	  <div class="btn-group">
			          <a href="javascript:void(0);" class="btn btn-xs btn-primary btn-flat pull-left" onclick="top.addTab('','提交意见','${contextpath}/basic/feedback/submit');">提交意见</a>
                  </div>
                  <div class="btn-group">
		          	<a href="javascript:void(0);" class="btn btn-xs btn-info btn-flat pull-left" onclick="top.addTab('','意见反馈','${contextpath}/basic/feedback/homelist');">更多</a>
		          </div>
              </div>
            </div><!-- /.box-header -->
            <div class="box-body table-responsive no-padding">
              <table class="table table-condensed table-hover">
                <c:forEach var="feed" items="${feedlist}" varStatus="status">
	                <tr>
	                  <td>
	                  	<c:choose>
	                  		<c:when test="${feed.resolvestatus==1}">
			                  	<span class="badge bg-aqua">新反馈</span>
	                  		</c:when>
	                  		<c:when test="${feed.resolvestatus==5}">
			                  	<span class="badge bg-purple">已受理</span>
	                  		</c:when>
	                  		<c:when test="${feed.resolvestatus==10}">
			                  	<span class="badge bg-green">已解决</span>
	                  		</c:when>
	                  		<c:when test="${feed.resolvestatus==-1}">
			                  	<span class="badge">未受理</span>
	                  		</c:when>
	                  	</c:choose>
	                  	<a class="todo-link" href="javascript:void(0);" 
	                  		onclick="top.addTab('','${feed.name}','${contextpath}/basic/feedback/open/${feed.id}');">${feed.name}</a>
	                  </td>
	                </tr>
                </c:forEach>
              </tbody></table>
            </div><!-- /.box-body -->
          </div>
         </div>
         
       <div class="col-md-4">
       	<div class="box box-primary">
            <div class="box-header with-border">
              <h3 class="box-title">帮助说明</h3>
              <div class="box-tools pull-right">
		          <a href="javascript:void(0);" class="btn btn-xs btn-info btn-flat pull-left" onclick="top.addTab('','帮助手册','${contextpath}/basic/helptext/homelist');">更多</a>
              </div>
            </div><!-- /.box-header -->
            <div class="box-body table-responsive no-padding">
              <table class="table table-condensed table-hover">
                <c:forEach var="help" items="${helplist}" varStatus="status">
	                <tr>
	                  <td><a class="todo-link" href="javascript:void(0);" 
	                  		onclick="top.addTab('','${help.name}','${contextpath}/basic/helptext/open/${help.id}');">${help.name}</a>
	                  </td>
	                </tr>
                </c:forEach>
              </tbody></table>
            </div><!-- /.box-body -->
          </div>
         </div>
         
     </div> --%>
  </div>
  <!-- <div class="main-footer no-print" style="margin-left: 0px;">
   <div class="pull-right hidden-xs">
     <b>Version</b> 1.0
   </div>
   <strong>Copyright &copy;
 </div> -->
<%@include file="/pages/include/bootstrap-js.jsp"%>
<script src="${skin}/js/system/main.js" type="text/javascript"></script>
</body>
</html>
