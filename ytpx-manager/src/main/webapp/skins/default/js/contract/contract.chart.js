
Namespace('contract.chart',{
	
	//进度统计
	initByProcess:function() {
		//layer.msg('正在努力统计数据,请稍等...', {icon: 16,time:0,shade:0.3});
		this.initProcessByAll();
		this.initProcessByLatestYear();
		this.initProcessByLatestYearMonth();
	},
	initProcessByAll:function() {
		
        $.post(contextpath+'/contract/chart/process/all',function(msg) {
        	if(msg.code!=200) {
        		top.showInfo('统计所有合同数据时出现错误!');
        		return;
        	}
        	
        	var $smallbox=$('.small-box');
        	$smallbox.eq(0).find('h3').html(msg.data.all.count+'<h5 class="inline">份</h5>');
    		$smallbox.eq(0).find('a').text(msg.data.all.pricestr);
    		
    		$smallbox.eq(1).find('h3').html(msg.data.processing.count+'<h5 class="inline">份</h5>');
    		$smallbox.eq(1).find('a').text(msg.data.processing.pricestr);
    		
    		$smallbox.eq(2).find('h3').html(msg.data.complete.count+'<h5 class="inline">份</h5>');
    		$smallbox.eq(2).find('a').text(msg.data.complete.pricestr);
    		
    		$smallbox.eq(3).find('h3').html(msg.data.cancel.count+'<h5 class="inline">份</h5>');
    		$smallbox.eq(3).find('a').text(msg.data.cancel.pricestr);
        });
	},
	initProcessByLatestYear:function() {
		$.post(contextpath+'/contract/chart/process/latestyear',function(msg) {
        	if(msg.code!=200) {
        		top.showInfo('统计近一年合同数据时出现错误!');
        		return;
        	}
        	
        	var $progress=$('.progress-group');
        	var contract=msg.data.all;
        	$progress.eq(0).find('.progress-number').text(contract.pricestr);
        	$progress.eq(0).find('.progress-bar').width('100%');
        	
        	$progress.eq(1).find('.progress-number').text(msg.data.processing.pricestr);
        	$progress.eq(1).find('.progress-bar').width(msg.data.processing.price*100/contract.price+'%');
    		
        	$progress.eq(2).find('.progress-number').text(msg.data.complete.pricestr);
        	$progress.eq(2).find('.progress-bar').width(msg.data.complete.price*100/contract.price+'%');
    		
        	$progress.eq(3).find('.progress-number').text(msg.data.cancel.pricestr);
        	$progress.eq(3).find('.progress-bar').width(msg.data.cancel.price*100/contract.price+'%');
        	
        	$progress.eq(4).find('.progress-number').html(contract.count+'份');
        	$progress.eq(4).find('.progress-bar').width('100%');
        	
        	$progress.eq(5).find('.progress-number').html('<b>'+msg.data.processing.count+'</b>/'+contract.count+'份');
        	$progress.eq(5).find('.progress-bar').width(msg.data.processing.count*100/contract.count+'%');
    		
        	$progress.eq(6).find('.progress-number').html('<b>'+msg.data.complete.count+'</b>/'+contract.count+'份');
        	$progress.eq(6).find('.progress-bar').width(msg.data.complete.count*100/contract.count+'%');
    		
        	$progress.eq(7).find('.progress-number').html('<b>'+msg.data.cancel.count+'</b>/'+contract.count+'份');
        	$progress.eq(7).find('.progress-bar').width(msg.data.cancel.count*100/contract.count+'%');
        });
	},
	
	initProcessByLatestYearMonth:function() {
		
		//近一年合同金额统计图
		var pricecanvas = $("#pricechart").get(0).getContext("2d");
		var pricechart = new Chart(pricecanvas);
		var pricechartOptions = {
		    showScale: true,
		    scaleShowGridLines: true,
		    scaleGridLineColor: "rgba(0,0,0,.05)",
		    scaleGridLineWidth: 1,
		    scaleShowHorizontalLines: true,
		    scaleShowVerticalLines: true,
		    bezierCurve: true,
		    bezierCurveTension: 0.3,
		    pointDot: true,
		    pointDotRadius: 4,
		    pointDotStrokeWidth: 1,
		    pointHitDetectionRadius: 20,
		    datasetStroke: true,
		    datasetStrokeWidth: 2,
		    datasetFill: true,
		    maintainAspectRatio: true,
		    responsive: true,
		    multiTooltipTemplate: "<%= datasetLabel+':'+value %>",
		};

		var pricechartData = {
		    labels: [],
		    datasets: [{
		        label: "起草合同金额",
		        fillColor: "rgb(210, 214, 222)",
		        strokeColor: "rgb(210, 214, 222)",
		        pointColor: "rgb(210, 214, 222)",
		        pointStrokeColor: "#c1c7d1",
		        pointHighlightFill: "#fff",
		        pointHighlightStroke: "rgb(220,220,220)",
		        data: []
		    },{
		        label: "审批通过金额",
		        fillColor: "rgba(60,141,188,0.9)",
		        strokeColor: "rgba(60,141,188,0.8)",
		        pointColor: "#3b8bba",
		        pointStrokeColor: "rgba(60,141,188,1)",
		        pointHighlightFill: "#fff",
		        pointHighlightStroke: "rgba(60,141,188,1)",
		        data: []
		    }]
		};
		
		//近一年合同数量统计图
		var amountcanvas = $("#amountchart").get(0).getContext("2d");
		var amountchart = new Chart(amountcanvas);
		var amountchartOptions = {
		    showScale: true,
		    scaleShowGridLines: true,
		    scaleGridLineColor: "rgba(0,0,0,.05)",
		    scaleGridLineWidth: 1,
		    scaleShowHorizontalLines: true,
		    scaleShowVerticalLines: true,
		    bezierCurve: true,
		    bezierCurveTension: 0.3,
		    pointDot: true,
		    pointDotRadius: 4,
		    pointDotStrokeWidth: 1,
		    pointHitDetectionRadius: 20,
		    datasetStroke: true,
		    datasetStrokeWidth: 2,
		    datasetFill: true,
		    maintainAspectRatio: true,
		    responsive: true,
		    multiTooltipTemplate: "<%= datasetLabel+':'+value %>",
		};

		var amountchartData = {
		    labels: [],
		    datasets: [{
		        label: "起草合同数量",
		        fillColor: "rgb(210, 214, 222)",
		        strokeColor: "rgb(210, 214, 222)",
		        pointColor: "rgb(210, 214, 222)",
		        pointStrokeColor: "#c1c7d1",
		        pointHighlightFill: "#fff",
		        pointHighlightStroke: "rgb(220,220,220)",
		        data: []
		    },{
		        label: "审批通过数量",
		        fillColor: "rgba(60,141,188,0.9)",
		        strokeColor: "rgba(60,141,188,0.8)",
		        pointColor: "#3b8bba",
		        pointStrokeColor: "rgba(60,141,188,1)",
		        pointHighlightFill: "#fff",
		        pointHighlightStroke: "rgba(60,141,188,1)",
		        data: []
		    }]
		};
		
		$.post(contextpath+'/contract/chart/process/latestyear/month',function(msg) {
			//layer.closeAll();
			if (msg.code != 200) {
				top.showInfo('统计近一年各月合同数据时出现错误!');
				return;
			}
			$('#price-chart-title').text(msg.data.title);
			$('#amount-chart-title').text(msg.data.title);
			
			//近一年金额统计图
			pricechartData.labels=msg.data.labels;
			pricechartData.datasets[0].data=msg.data.pricedata[0];
			pricechartData.datasets[1].data=msg.data.pricedata[1];
			pricechart.Line(pricechartData, pricechartOptions);
			
			//近一年数量统计图
			amountchartData.labels=msg.data.labels;
			amountchartData.datasets[0].data=msg.data.amountdata[0];
			amountchartData.datasets[1].data=msg.data.amountdata[1];
			amountchart.Line(amountchartData, amountchartOptions);
		});
	},
	
	//分类统计
	initByCategory:function() {
		
        var priceechart = echarts.init(document.getElementById('price-echart'));
        var amountechart = echarts.init(document.getElementById('amount-echart'));
        priceechart.showLoading({
    		text: '正在统计数据,请稍等...'
    	});
        amountechart.showLoading({
    		text: '正在统计数据,请稍等...'
    	});
        $.post(contextpath+'/contract/chart/category/latestyear',function(msg) {
        	priceechart.hideLoading();
        	amountechart.hideLoading();
        	
			if (msg.code != 200) {
				top.showInfo('统计近一年合同数据时出现错误!');
				return;
			}
			
			//金额统计图
			var option = {
	    	    title : {
	    	        text: '近一年合同金额统计',
	    	        subtext: msg.data.subtitle,
	    	        x:'center'
	    	    },
	    	    tooltip : {
	    	        trigger: 'item',
	    	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    	    },
	    	    legend: {
	    	        orient: 'vertical',
	    	        left: 'left',
	    	        data: msg.data.categoryname
	    	    },
	    	    series : [
	    	        {
	    	            name: '合同金额',
	    	            type: 'pie',
	    	            radius : '55%',
	    	            center: ['50%', '60%'],
	    	            data:msg.data.categorydata,
	    	            itemStyle: {
	    	                emphasis: {
	    	                    shadowBlur: 10,
	    	                    shadowOffsetX: 0,
	    	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
	    	                }
	    	            }
	    	        }
	    	    ]
	    	};
			priceechart.setOption(option);
			
			//数量统计图
			var option = {
		    	    title : {
		    	        text: '近一年合同数量统计',
		    	        subtext: msg.data.subtitle,
		    	        x:'center'
		    	    },
		    	    tooltip : {
		    	        trigger: 'item',
		    	        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    	    },
		    	    legend: {
		    	        orient: 'vertical',
		    	        left: 'left',
		    	        data: msg.data.categoryname
		    	    },
		    	    series : [
		    	        {
		    	            name: '合同数量',
		    	            type: 'pie',
		    	            radius : '55%',
		    	            center: ['50%', '60%'],
		    	            data:$.map(msg.data.categorydata,function(data) {
		    	            	data.value=data.count;
		    	            	return data;
		    	            }),
		    	            itemStyle: {
		    	                emphasis: {
		    	                    shadowBlur: 10,
		    	                    shadowOffsetX: 0,
		    	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		    	                }
		    	            }
		    	        }
		    	    ]
		    	};
				amountechart.setOption(option);
		});
	},
	
	//科室统计
	initByTeam:function() {
		
        var priceechart = echarts.init(document.getElementById('price-echart'));
        var amountechart = echarts.init(document.getElementById('amount-echart'));
        priceechart.showLoading({
    		text: '正在统计数据,请稍等...'
    	});
        amountechart.showLoading({
    		text: '正在统计数据,请稍等...'
    	});
        $.post(contextpath+'/contract/chart/team/latestyear',function(msg) {
        	priceechart.hideLoading();
        	amountechart.hideLoading();
        	
			if (msg.code != 200) {
				top.showInfo('统计近一年合同数据时出现错误!');
				return;
			}
			
			//金额统计图
			var option = {
	    	    title : {
	    	        text: '近一年合同金额统计',
	    	        subtext: msg.data.subtitle,
	    	        x:'center'
	    	    },
	    	    tooltip : {
	    	        trigger: 'item',
	    	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    	    },
	    	    legend: {
	    	        orient: 'vertical',
	    	        left: 'left',
	    	        data: msg.data.teamname
	    	    },
	    	    series : [
	    	        {
	    	            name: '合同金额',
	    	            type: 'pie',
	    	            radius : '55%',
	    	            center: ['50%', '60%'],
	    	            data:msg.data.teamdata,
	    	            itemStyle: {
	    	                emphasis: {
	    	                    shadowBlur: 10,
	    	                    shadowOffsetX: 0,
	    	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
	    	                }
	    	            }
	    	        }
	    	    ]
	    	};
			priceechart.setOption(option);
			
			//数量统计图
			var option = {
		    	    title : {
		    	        text: '近一年合同数量统计',
		    	        subtext: msg.data.subtitle,
		    	        x:'center'
		    	    },
		    	    tooltip : {
		    	        trigger: 'item',
		    	        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    	    },
		    	    legend: {
		    	        orient: 'vertical',
		    	        left: 'left',
		    	        data: msg.data.teamname
		    	    },
		    	    series : [
		    	        {
		    	            name: '合同数量',
		    	            type: 'pie',
		    	            radius : '55%',
		    	            center: ['50%', '60%'],
		    	            data:$.map(msg.data.teamdata,function(data) {
		    	            	data.value=data.count;
		    	            	return data;
		    	            }),
		    	            itemStyle: {
		    	                emphasis: {
		    	                    shadowBlur: 10,
		    	                    shadowOffsetX: 0,
		    	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		    	                }
		    	            }
		    	        }
		    	    ]
		    	};
				amountechart.setOption(option);
		});
	},
	
	//时间统计
	initByDate:function() {
		
        var priceechart = echarts.init(document.getElementById('price-echart'));
        var amountechart = echarts.init(document.getElementById('amount-echart'));
        priceechart.showLoading({
    		text: '正在统计数据,请稍等...'
    	});
        amountechart.showLoading({
    		text: '正在统计数据,请稍等...'
    	});
        $.post(contextpath+'/contract/chart/date/all',function(msg) {
        	priceechart.hideLoading();
        	amountechart.hideLoading();
        	
			if (msg.code != 200) {
				top.showInfo('统计合同数据时出现错误!');
				return;
			}
			
			//金额统计图
			var colorList = [
	                          '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
	                           '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
	                           '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
	                        ];
			var option = {
				    title: {
				    	text: '合同金额统计',
				        x: 'center'
				    },
				    tooltip: {
				        trigger: 'item'
				    },
				    toolbox: {
				        show: true,
				        feature: {
				            mark : {show: true},
				            dataView : {show: true, readOnly: false},
				            magicType: {show: true, type: ['line', 'bar']},
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    },
				    calculable: true,
				    xAxis: [
				        {
				            type: 'category',
				            data: $.map(msg.data,function(data) {
		    	            	return data.name+'年';
		    	            })
				        }
				    ],
				    yAxis: [
				        {
				            type: 'value'
				        }
				    ],
				    series: [
				        {
				            name: '年份',
				            type: 'bar',
				            barMaxWidth:25,
				            itemStyle: {
				                normal: {
				                    color: function(params) {
				                        return colorList[params.dataIndex]
				                    },
				                    label: {
				                        show: true,
				                        position: 'top',
				                        formatter: '{c}'
				                    }
				                }
				            },
				            data: $.map(msg.data,function(data) {
		    	            	return {
		    	            		value:data.price,
		    	            		name:data.name+'年'
		    	            	};
		    	            })
				        }
				    ]
	    	};
			priceechart.setOption(option);
			
			//数量统计图
			var option = {
	    	    title : {
	    	        text: '合同数量统计',
	    	        subtext: '',
	    	        x:'center'
	    	    },
	    	    tooltip : {
	    	        trigger: 'item',
	    	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    	    },
	    	    legend: {
	    	        orient: 'vertical',
	    	        left: 'left',
	    	        data: $.map(msg.data,function(data) {
    	            	return data.name+'年';
    	            })
	    	    },
	    	    series : [
	    	        {
	    	            name: '合同数量',
	    	            type: 'pie',
	    	            radius : '55%',
	    	            center: ['50%', '60%'],
	    	            data: $.map(msg.data,function(data) {
	    	            	return {
	    	            		value:data.count,
	    	            		name:data.name+'年'
	    	            	};
	    	            }),
	    	            itemStyle: {
	    	                emphasis: {
	    	                    shadowBlur: 10,
	    	                    shadowOffsetX: 0,
	    	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
	    	                }
	    	            }
	    	        }
	    	    ]
	    	};
			amountechart.setOption(option);
		});
	}
});