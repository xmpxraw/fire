<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
	<%@include file="/pages/include/bootstrap.jsp"%>
	<link href="${skin}/css/contract/contract.css" rel="stylesheet" type="text/css" />
</head>
<body style="background-color:white;">
	<br/>
	<div class="nav-tabs-custom">
		<ul class="nav nav-tabs">
		  <li id="tab-process-header" class="active"><a href="#tab-process" data-toggle="tab"><i class="fa fa-fw fa-line-chart"></i>折线图</a></li>
		  <li id="tab-bar-header"><a href="#tab-category" data-toggle="tab"><i class="fa fa-fw fa-bar-chart"></i>柱状图</a></li>
		  <li id="tab-pie-header"><a href="#tab-team" data-toggle="tab"><i class="fa fa-fw fa-pie-chart"></i>饼图</a></li>
		  <li id="tab-k-header"><a href="#tab-date" data-toggle="tab"><i class="fa fa-fw fa-clock-o"></i>K线图</a></li>
		  <li id="tab-leidatu-header"><a href="#tab-leidatu" data-toggle="tab"><i class="fa fa-fw fa-clock-o"></i>雷达图</a></li>
		  <li id="tab-ditu-header"><a href="#tab-ditu" data-toggle="tab"><i class="fa fa-fw fa-area-chart"></i>地图</a></li>
		  <li id="tab-heliutu-header"><a href="#tab-heliutu" data-toggle="tab"><i class="fa fa-fw fa-clock-o"></i>事件河流图</a></li>
		  <li id="tab-sandiantu-header"><a href="#tab-sandiantu" data-toggle="tab"><i class="fa fa-fw fa-clock-o"></i>散点图</a></li>
		  <li id="tab-weientu-header"><a href="#tab-weientu" data-toggle="tab"><i class="fa fa-fw fa-clock-o"></i>韦恩图</a></li>
		  <li id="tab-yibiaotu-header"><a href="#tab-yibiaotu" data-toggle="tab"><i class="fa fa-fw fa-clock-o"></i>仪表图</a></li>
		  <li id="tab-loudoutu-header"><a href="#tab-loudoutu" data-toggle="tab"><i class="fa fa-fw fa-clock-o"></i>漏斗图</a></li>
		</ul>
		<div class="tab-content">
		  <div class="tab-pane active" id="tab-process">
		  	<iframe id="line-iframe" src="${contextpath}/system/chart/line" frameborder="0" scrolling="no" style="width:100%;height:460px;"></iframe>
		  </div>
		  <div class="tab-pane" id="tab-category">
		  	<iframe id="bar-iframe" src="" frameborder="0" scrolling="no" style="width:100%;height:460px;"></iframe>
		  </div>
		  <div class="tab-pane" id="tab-team">
		  	<iframe id="pie-iframe" src="" frameborder="0" scrolling="no" style="width:100%;height:460px;"></iframe>
		  </div>
		  <div class="tab-pane" id="tab-date">
		  	<iframe id="k-iframe" src="" frameborder="0" scrolling="no" style="width:100%;height:460px;"></iframe>
		  </div>
		  <div class="tab-pane" id="tab-leidatu">
		  	<iframe id="leidatu-iframe" src="" frameborder="0" scrolling="no" style="width:100%;height:460px;"></iframe>
		  </div>
		  <div class="tab-pane" id="tab-ditu">
		  	<iframe id="ditu-iframe" src="" frameborder="0" scrolling="no" style="width:100%;height:460px;"></iframe>
		  </div>
		  <div class="tab-pane" id="tab-heliutu">
		  	<iframe id="heliutu-iframe" src="" frameborder="0" scrolling="no" style="width:100%;height:460px;"></iframe>
		  </div>
		  <div class="tab-pane" id="tab-sandiantu">
		  	<iframe id="sandiantu-iframe" src="" frameborder="0" scrolling="no" style="width:100%;height:460px;"></iframe>
		  </div>
		  <div class="tab-pane" id="tab-weientu">
		  	<iframe id="weientu-iframe" src="" frameborder="0" scrolling="no" style="width:100%;height:460px;"></iframe>
		  </div>
		  <div class="tab-pane" id="tab-yibiaotu">
		  	<iframe id="yibiaotu-iframe" src="" frameborder="0" scrolling="no" style="width:100%;height:460px;"></iframe>
		  </div>
		  <div class="tab-pane" id="tab-loudoutu">
		  	<iframe id="loudoutu-iframe" src="" frameborder="0" scrolling="no" style="width:100%;height:460px;"></iframe>
		  </div>
		</div>
	</div>
    
    <%@include file="/pages/include/bootstrap-js.jsp"%>
    <script type="text/javascript">
	    $('#tab-bar-header').click(function() {
			if(!$('#bar-iframe').data('load')) {
				$('#bar-iframe').data('load',true);
				$('#bar-iframe')[0].src='${contextpath}/system/chart/bar';
			}
		});
	    $('#tab-pie-header').click(function() {
			if(!$('#pie-iframe').data('load')) {
				$('#pie-iframe').data('load',true);
				$('#pie-iframe')[0].src='${contextpath}/system/chart/pie';
			}
		});
	    $('#tab-k-header').click(function() {
			if(!$('#k-iframe').data('load')) {
				$('#k-iframe').data('load',true);
				$('#k-iframe')[0].src='${contextpath}/system/chart/k';
			}
		});
	    $('#tab-leidatu-header').click(function() {
			if(!$('#leidatu-iframe').data('load')) {
				$('#leidatu-iframe').data('load',true);
				$('#leidatu-iframe')[0].src='${contextpath}/system/chart/radar';
			}
		});
	    $('#tab-ditu-header').click(function() {
			if(!$('#ditu-iframe').data('load')) {
				$('#ditu-iframe').data('load',true);
				$('#ditu-iframe')[0].src='${contextpath}/system/chart/map';
			}
		});
	    $('#tab-heliutu-header').click(function() {
			if(!$('#heliutu-iframe').data('load')) {
				$('#heliutu-iframe').data('load',true);
				$('#heliutu-iframe')[0].src='${contextpath}/system/chart/eventRiver';
			}
		});
	    $('#tab-sandiantu-header').click(function() {
			if(!$('#sandiantu-iframe').data('load')) {
				$('#sandiantu-iframe').data('load',true);
				$('#sandiantu-iframe')[0].src='${contextpath}/system/chart/scatter';
			}
		});
	    $('#tab-weientu-header').click(function() {
			if(!$('#weientu-iframe').data('load')) {
				$('#weientu-iframe').data('load',true);
				$('#weientu-iframe')[0].src='${contextpath}/system/chart/venn';
			}
		});
	    $('#tab-yibiaotu-header').click(function() {
			if(!$('#yibiaotu-iframe').data('load')) {
				$('#yibiaotu-iframe').data('load',true);
				$('#yibiaotu-iframe')[0].src='${contextpath}/system/chart/gauge';
			}
		});
	    $('#tab-loudoutu-header').click(function() {
			if(!$('#loudoutu-iframe').data('load')) {
				$('#loudoutu-iframe').data('load',true);
				$('#loudoutu-iframe')[0].src='${contextpath}/system/chart/funnel';
			}
		});
	</script>
</body>
</html>
