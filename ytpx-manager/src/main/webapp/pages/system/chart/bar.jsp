<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<%@include file="/pages/include/bootstrap.jsp"%>
<title>柱状图</title>
</head>
<body>
<div class="content">

      <!-- Main row -->
      <div class="row">
      	 <div class="col-md-12">
	      	 <div class="box box-warning">
	            <div class="box-header with-border"></div><!-- /.box-header -->
	            <div class="box-body table-responsive no-padding">
	      	 		<div id="bar-echart" style="height:450px"></div>
	      	 	</div>
	      	 </div>
      	 </div>
      </div><!-- /.row -->
    </div><!-- /.content -->


<%@include file="/pages/include/bootstrap-js.jsp"%>
	<script src="${commonskin}/lib/echarts-3.0.0/dist/echarts.min.js"></script>
	<script src="${skin}/js/system/echarts.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(function() {
			system.chart.initByBar();
		});
	</script>
</body>
</html>