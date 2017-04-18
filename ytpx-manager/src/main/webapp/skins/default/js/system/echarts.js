
Namespace('system.chart',{
	
	//折线图
	initByLine:function() {
		
        var linechart = echarts.init(document.getElementById('line-echart'));
        linechart.showLoading({
    		text: '正在统计数据,请稍等...'
    	});
    //	linechart.hideLoading();
        $.post(contextpath+'/system/chart/data',{type:'line'},function(msg) {
        	linechart.hideLoading();
        	
			if (msg.code != 200) {
				top.showInfo('加载数据时出现错误!');
				return;
			}
			
			option = {
				    title : {
				        text: 'xxx',
				        subtext: '纯属虚构'
				    },
				    tooltip : {
				        trigger: 'axis'
				    },
				    legend: {
				        data:['最高','最低']
				    },
				    toolbox: {
				        show : true,
				        feature : {
				            mark : {show: true},
				            dataView : {show: true, readOnly: false},
				            magicType : {show: true, type: ['line', 'bar']},
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    },
				    calculable : true,
				    xAxis : [
				        {
				            type : 'category',
				            boundaryGap : false,
				       //     data : ['周一','周二','周三','周四','周五','周六','周日']
				            data : msg.data.x_data
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value',
				            axisLabel : {
				                formatter: '{value} °C'
				            }
				        }
				    ],
				    series : [
				        {
				            name:'最高',
				            type:'line',
				            data : msg.data.y_data,
				         //   data:[11, 11, 15, 13, 12, 13, 10],
				            markPoint : {
				                data : [
				                    {type : 'max', name: '最大值'},
				                    {type : 'min', name: '最小值'}
				                ]
				            },
				            markLine : {
				                data : [
				                    {type : 'average', name: '平均值'}
				                ]
				            }
				        },
				        {
				            name:'最低',
				            type:'line',
				            data : msg.data.y_data2,
				            //data:[1, -2, 2, 5, 3, 2, 0],
				            markPoint : {
				                data : [
				                    {name : '周最低', value : -2, xAxis: 1, yAxis: -1.5}
				                ]
				            },
				            markLine : {
				                data : [
				                    {type : 'average', name : '平均值'}
				                ]
				            }
				        }
				    ]
				};
				                    
			linechart.setOption(option);
		});
	},
	//柱状图
	initByBar:function() {
		
        var linechart = echarts.init(document.getElementById('bar-echart'));
        linechart.showLoading({
    		text: '正在统计数据,请稍等...'
    	});
  //  	linechart.hideLoading();
    	   $.post(contextpath+'/system/chart/data',{type:'bar'},function(msg) {
        	linechart.hideLoading();
        	
			if (msg.code != 200) {
				top.showInfo('加载数据时出现错误!');
				return;
			}
			
    	option = {
    		    title : {
    		        text: 'xxx',
    		        subtext: '纯属虚构'
    		    },
    		    tooltip : {
    		        trigger: 'axis'
    		    },
    		    legend: {
    		        data:['xxx','xxxx']
    		    },
    		    toolbox: {
    		        show : true,
    		        feature : {
    		            mark : {show: true},
    		            dataView : {show: true, readOnly: false},
    		            magicType : {show: true, type: ['line', 'bar']},
    		            restore : {show: true},
    		            saveAsImage : {show: true}
    		        }
    		    },
    		    calculable : true,
    		    xAxis : [
    		        {
    		            type : 'category',
    		            data : msg.data.x_data
    		         //   data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
    		        }
    		    ],
    		    yAxis : [
    		        {
    		            type : 'value'
    		        }
    		    ],
    		    series : [
    		        {
    		            name:'蒸发量',
    		            type:'bar',
    		            data : msg.data.y_data,
    		          //  data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3],
    		            markPoint : {
    		                data : [
    		                    {type : 'max', name: '最大值'},
    		                    {type : 'min', name: '最小值'}
    		                ]
    		            },
    		            markLine : {
    		                data : [
    		                    {type : 'average', name: '平均值'}
    		                ]
    		            }
    		        },
    		        {
    		            name:'降水量',
    		            type:'bar',
    		            data : msg.data.y_data2,
    		         //   data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
    		          /*  markPoint : {
    		                data : [
    		                    {name : '年最高', value : 182.2, xAxis: 7, yAxis: 183, symbolSize:18},
    		                    {name : '年最低', value : 2.3, xAxis: 11, yAxis: 3}
    		                ]
    		            },*/
    		            markLine : {
    		                data : [
    		                    {type : 'average', name : '平均值'}
    		                ]
    		            }
    		        }
    		    ]
    		};
    		                    
				                    
			linechart.setOption(option);
		});
	},
	//饼图
	initByPie:function() {
		
        var chart = echarts.init(document.getElementById('pie-echart'));
        chart.showLoading({
    		text: '正在统计数据,请稍等...'
    	});
    //	chart.hideLoading();
        $.post(contextpath+'/system/chart/data',{type:'pie'},function(msg) {
        	chart.hideLoading();
        	
			if (msg.code != 200) {
				top.showInfo('加载数据时出现错误!');
				return;
			}
			
    	option = {
    		    title : {
    		        text: '某站点用户访问来源',
    		        subtext: '纯属虚构',
    		        x:'center'
    		    },
    		    tooltip : {
    		        trigger: 'item',
    		        formatter: "{a} <br/>{b} : {c} ({d}%)"
    		    },
    		    legend: {
    		        orient : 'vertical',
    		        x : 'left',
    		        data : msg.data
    		      //  data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
    		    },
    		    toolbox: {
    		        show : true,
    		        feature : {
    		            mark : {show: true},
    		            dataView : {show: true, readOnly: false},
    		            magicType : {
    		                show: true, 
    		                type: ['pie', 'funnel'],
    		                option: {
    		                    funnel: {
    		                        x: '25%',
    		                        width: '50%',
    		                        funnelAlign: 'left',
    		                        max: 1548
    		                    }
    		                }
    		            },
    		            restore : {show: true},
    		            saveAsImage : {show: true}
    		        }
    		    },
    		    calculable : true,
    		    series : [
    		        {
    		            name:'访问来源',
    		            type:'pie',
    		            radius : '55%',
    		            center: ['50%', '60%'],
    		            data:msg.data
    		          /*  data:[
    		                {value:335, name:'直接访问'},
    		                {value:310, name:'邮件营销'},
    		                {value:234, name:'联盟广告'},
    		                {value:135, name:'视频广告'},
    		                {value:1548, name:'搜索引擎'}
    		            ]*/
    		        }
    		    ]
    		};
    		                    
				                    
			chart.setOption(option);
		});
	},
	//K线图
	initByK:function() {
		
        var chart = echarts.init(document.getElementById('k-echart'));
        chart.showLoading({
    		text: '正在统计数据,请稍等...'
    	});
    	chart.hideLoading();
       /* $.post(contextpath+'/system/chart/bar',function(msg) {
        	linechart.hideLoading();
        	
			if (msg.code != 200) {
				top.showInfo('加载数据时出现错误!');
				return;
			}*/
			
    	option = {
    		    title : {
    		        text: '2013年上半年上证指数'
    		    },
    		    tooltip : {
    		        trigger: 'axis',
    		        formatter: function (params) {
    		            var res = params[0].seriesName + ' ' + params[0].name;
    		            res += '<br/>  开盘 : ' + params[0].value[0] + '  最高 : ' + params[0].value[3];
    		            res += '<br/>  收盘 : ' + params[0].value[1] + '  最低 : ' + params[0].value[2];
    		            return res;
    		        }
    		    },
    		    legend: {
    		        data:['上证指数']
    		    },
    		    toolbox: {
    		        show : true,
    		        feature : {
    		            mark : {show: true},
    		            dataZoom : {show: true},
    		            dataView : {show: true, readOnly: false},
    		            magicType: {show: true, type: ['line', 'bar']},
    		            restore : {show: true},
    		            saveAsImage : {show: true}
    		        }
    		    },
    		    dataZoom : {
    		        show : true,
    		        realtime: true,
    		        start : 50,
    		        end : 100
    		    },
    		    xAxis : [
    		        {
    		            type : 'category',
    		            boundaryGap : true,
    		            axisTick: {onGap:false},
    		            splitLine: {show:false},
    		            data : [
    		                "2013/1/24", "2013/1/25", "2013/1/28", "2013/1/29", "2013/1/30",
    		                "2013/1/31", "2013/2/1", "2013/2/4", "2013/2/5", "2013/2/6", 
    		                "2013/2/7", "2013/2/8", "2013/2/18", "2013/2/19", "2013/2/20", 
    		                "2013/2/21", "2013/2/22", "2013/2/25", "2013/2/26", "2013/2/27", 
    		                "2013/2/28", "2013/3/1", "2013/3/4", "2013/3/5", "2013/3/6", 
    		                "2013/3/7", "2013/3/8", "2013/3/11", "2013/3/12", "2013/3/13", 
    		                "2013/3/14", "2013/3/15", "2013/3/18", "2013/3/19", "2013/3/20", 
    		                "2013/3/21", "2013/3/22", "2013/3/25", "2013/3/26", "2013/3/27", 
    		                "2013/3/28", "2013/3/29", "2013/4/1", "2013/4/2", "2013/4/3", 
    		                "2013/4/8", "2013/4/9", "2013/4/10", "2013/4/11", "2013/4/12", 
    		                "2013/4/15", "2013/4/16", "2013/4/17", "2013/4/18", "2013/4/19", 
    		                "2013/4/22", "2013/4/23", "2013/4/24", "2013/4/25", "2013/4/26", 
    		                "2013/5/2", "2013/5/3", "2013/5/6", "2013/5/7", "2013/5/8", 
    		                "2013/5/9", "2013/5/10", "2013/5/13", "2013/5/14", "2013/5/15", 
    		                "2013/5/16", "2013/5/17", "2013/5/20", "2013/5/21", "2013/5/22", 
    		                "2013/5/23", "2013/5/24", "2013/5/27", "2013/5/28", "2013/5/29", 
    		                "2013/5/30", "2013/5/31", "2013/6/3", "2013/6/4", "2013/6/5", 
    		                "2013/6/6", "2013/6/7", "2013/6/13"
    		            ]
    		        }
    		    ],
    		    yAxis : [
    		        {
    		            type : 'value',
    		            scale:true,
    		            boundaryGap: [0.01, 0.01]
    		        }
    		    ],
    		    series : [
    		        {
    		            name:'上证指数',
    		            type:'k',
    		           /* data:msg.data // list<List<Integer>>  格式类型*/
    		            data:[ // 开盘，收盘，最低，最高
    		                [2320.26,2302.6,2287.3,2362.94],
    		                [2300,2291.3,2288.26,2308.38],
    		                [2295.35,2346.5,2295.35,2346.92],
    		                [2347.22,2358.98,2337.35,2363.8],
    		                [2360.75,2382.48,2347.89,2383.76],
    		                [2383.43,2385.42,2371.23,2391.82],
    		                [2377.41,2419.02,2369.57,2421.15],
    		                [2425.92,2428.15,2417.58,2440.38],
    		                [2411,2433.13,2403.3,2437.42],
    		                [2432.68,2434.48,2427.7,2441.73],
    		                [2430.69,2418.53,2394.22,2433.89],
    		                [2416.62,2432.4,2414.4,2443.03],
    		                [2441.91,2421.56,2415.43,2444.8],
    		                [2420.26,2382.91,2373.53,2427.07],
    		                [2383.49,2397.18,2370.61,2397.94],
    		                [2378.82,2325.95,2309.17,2378.82],
    		                [2322.94,2314.16,2308.76,2330.88],
    		                [2320.62,2325.82,2315.01,2338.78],
    		                [2313.74,2293.34,2289.89,2340.71],
    		                [2297.77,2313.22,2292.03,2324.63],
    		                [2322.32,2365.59,2308.92,2366.16],
    		                [2364.54,2359.51,2330.86,2369.65],
    		                [2332.08,2273.4,2259.25,2333.54],
    		                [2274.81,2326.31,2270.1,2328.14],
    		                [2333.61,2347.18,2321.6,2351.44],
    		                [2340.44,2324.29,2304.27,2352.02],
    		                [2326.42,2318.61,2314.59,2333.67],
    		                [2314.68,2310.59,2296.58,2320.96],
    		                [2309.16,2286.6,2264.83,2333.29],
    		                [2282.17,2263.97,2253.25,2286.33],
    		                [2255.77,2270.28,2253.31,2276.22],
    		                [2269.31,2278.4,2250,2312.08],
    		                [2267.29,2240.02,2239.21,2276.05],
    		                [2244.26,2257.43,2232.02,2261.31],
    		                [2257.74,2317.37,2257.42,2317.86],
    		                [2318.21,2324.24,2311.6,2330.81],
    		                [2321.4,2328.28,2314.97,2332],
    		                [2334.74,2326.72,2319.91,2344.89],
    		                [2318.58,2297.67,2281.12,2319.99],
    		                [2299.38,2301.26,2289,2323.48],
    		                [2273.55,2236.3,2232.91,2273.55],
    		                [2238.49,2236.62,2228.81,2246.87],
    		                [2229.46,2234.4,2227.31,2243.95],
    		                [2234.9,2227.74,2220.44,2253.42],
    		                [2232.69,2225.29,2217.25,2241.34],
    		                [2196.24,2211.59,2180.67,2212.59],
    		                [2215.47,2225.77,2215.47,2234.73],
    		                [2224.93,2226.13,2212.56,2233.04],
    		                [2236.98,2219.55,2217.26,2242.48],
    		                [2218.09,2206.78,2204.44,2226.26],
    		                [2199.91,2181.94,2177.39,2204.99],
    		                [2169.63,2194.85,2165.78,2196.43],
    		                [2195.03,2193.8,2178.47,2197.51],
    		                [2181.82,2197.6,2175.44,2206.03],
    		                [2201.12,2244.64,2200.58,2250.11],
    		                [2236.4,2242.17,2232.26,2245.12],
    		                [2242.62,2184.54,2182.81,2242.62],
    		                [2187.35,2218.32,2184.11,2226.12],
    		                [2213.19,2199.31,2191.85,2224.63],
    		                [2203.89,2177.91,2173.86,2210.58],
    		                [2170.78,2174.12,2161.14,2179.65],
    		                [2179.05,2205.5,2179.05,2222.81],
    		                [2212.5,2231.17,2212.5,2236.07],
    		                [2227.86,2235.57,2219.44,2240.26],
    		                [2242.39,2246.3,2235.42,2255.21],
    		                [2246.96,2232.97,2221.38,2247.86],
    		                [2228.82,2246.83,2225.81,2247.67],
    		                [2247.68,2241.92,2231.36,2250.85],
    		                [2238.9,2217.01,2205.87,2239.93],
    		                [2217.09,2224.8,2213.58,2225.19],
    		                [2221.34,2251.81,2210.77,2252.87],
    		                [2249.81,2282.87,2248.41,2288.09],
    		                [2286.33,2299.99,2281.9,2309.39],
    		                [2297.11,2305.11,2290.12,2305.3],
    		                [2303.75,2302.4,2292.43,2314.18],
    		                [2293.81,2275.67,2274.1,2304.95],
    		                [2281.45,2288.53,2270.25,2292.59],
    		                [2286.66,2293.08,2283.94,2301.7],
    		                [2293.4,2321.32,2281.47,2322.1],
    		                [2323.54,2324.02,2321.17,2334.33],
    		                [2316.25,2317.75,2310.49,2325.72],
    		                [2320.74,2300.59,2299.37,2325.53],
    		                [2300.21,2299.25,2294.11,2313.43],
    		                [2297.1,2272.42,2264.76,2297.1],
    		                [2270.71,2270.93,2260.87,2276.86],
    		                [2264.43,2242.11,2240.07,2266.69],
    		                [2242.26,2210.9,2205.07,2250.63],
    		                [2190.1,2148.35,2126.22,2190.1]
    		            ]
    		        }
    		    ]
    		};
    		                    
    		                    
				                    
			chart.setOption(option);
	//	});
	},
	//雷达图
	initByRadar:function() {
		
        var chart = echarts.init(document.getElementById('radar-echart'));
        chart.showLoading({
    		text: '正在统计数据,请稍等...'
    	});
    	chart.hideLoading();
       /* $.post(contextpath+'/system/chart/bar',function(msg) {
        	linechart.hideLoading();
        	
			if (msg.code != 200) {
				top.showInfo('加载数据时出现错误!');
				return;
			}*/
			

    	option = {
    	    title : {
    	        text: '预算 vs 开销（Budget vs spending）',
    	        subtext: '纯属虚构'
    	    },
    	    tooltip : {
    	        trigger: 'axis'
    	    },
    	    legend: {
    	        orient : 'vertical',
    	        x : 'right',
    	        y : 'bottom',
    	        data:['预算分配（Allocated Budget）','实际开销（Actual Spending）']
    	    },
    	    toolbox: {
    	        show : true,
    	        feature : {
    	            mark : {show: true},
    	            dataView : {show: true, readOnly: false},
    	            restore : {show: true},
    	            saveAsImage : {show: true}
    	        }
    	    },
    	    polar : [
    	       {
    	           indicator : [
    	               { text: '销售（sales）', max: 6000},
    	               { text: '管理（Administration）', max: 16000},
    	               { text: '信息技术（Information Techology）', max: 30000},
    	               { text: '客服（Customer Support）', max: 38000},
    	               { text: '研发（Development）', max: 52000},
    	               { text: '市场（Marketing）', max: 25000}
    	            ]
    	        }
    	    ],
    	    calculable : true,
    	    series : [
    	        {
    	            name: '预算 vs 开销（Budget vs spending）',
    	            type: 'radar',
    	            data : [
    	                {
    	                    value : [4300, 10000, 28000, 35000, 50000, 19000],
    	                    name : '预算分配（Allocated Budget）'
    	                },
    	                 {
    	                    value : [5000, 14000, 28000, 31000, 42000, 21000],
    	                    name : '实际开销（Actual Spending）'
    	                }
    	            ]
    	        }
    	    ]
    	};
    	                    
    		                    
				                    
			chart.setOption(option);
	//	});
	},
	//地图
	initByMap:function() {
		
        var chart = echarts.init(document.getElementById('map-echart'));
        chart.showLoading({
    		text: '正在统计数据,请稍等...'
    	});
    	chart.hideLoading();
       /* $.post(contextpath+'/system/chart/bar',function(msg) {
        	linechart.hideLoading();
        	
			if (msg.code != 200) {
				top.showInfo('加载数据时出现错误!');
				return;
			}*/
			
    	option = {
    		    title : {
    		        text: 'iphone销量',
    		        subtext: '纯属虚构',
    		        x:'center'
    		    },
    		    tooltip : {
    		        trigger: 'item'
    		    },
    		    legend: {
    		        orient: 'vertical',
    		        x:'left',
    		        data:['iphone3','iphone4','iphone5']
    		    },
    		    dataRange: {
    		        min: 0,
    		        max: 2500,
    		        x: 'left',
    		        y: 'bottom',
    		        text:['高','低'],           // 文本，默认为数值文本
    		        calculable : true
    		    },
    		    toolbox: {
    		        show: true,
    		        orient : 'vertical',
    		        x: 'right',
    		        y: 'center',
    		        feature : {
    		            mark : {show: true},
    		            dataView : {show: true, readOnly: false},
    		            restore : {show: true},
    		            saveAsImage : {show: true}
    		        }
    		    },
    		    roamController: {
    		        show: true,
    		        x: 'right',
    		        mapTypeControl: {
    		            'china': true
    		        }
    		    },
    		    series : [
    		        {
    		            name: 'iphone3',
    		            type: 'map',
    		            mapType: 'china',
    		            roam: false,
    		            itemStyle:{
    		                normal:{label:{show:true}},
    		                emphasis:{label:{show:true}}
    		            },
    		            data:[
    		                {name: '北京',value: Math.round(Math.random()*1000)},
    		                {name: '天津',value: Math.round(Math.random()*1000)},
    		                {name: '上海',value: Math.round(Math.random()*1000)},
    		                {name: '重庆',value: Math.round(Math.random()*1000)},
    		                {name: '河北',value: Math.round(Math.random()*1000)},
    		                {name: '河南',value: Math.round(Math.random()*1000)},
    		                {name: '云南',value: Math.round(Math.random()*1000)},
    		                {name: '辽宁',value: Math.round(Math.random()*1000)},
    		                {name: '黑龙江',value: Math.round(Math.random()*1000)},
    		                {name: '湖南',value: Math.round(Math.random()*1000)},
    		                {name: '安徽',value: Math.round(Math.random()*1000)},
    		                {name: '山东',value: Math.round(Math.random()*1000)},
    		                {name: '新疆',value: Math.round(Math.random()*1000)},
    		                {name: '江苏',value: Math.round(Math.random()*1000)},
    		                {name: '浙江',value: Math.round(Math.random()*1000)},
    		                {name: '江西',value: Math.round(Math.random()*1000)},
    		                {name: '湖北',value: Math.round(Math.random()*1000)},
    		                {name: '广西',value: Math.round(Math.random()*1000)},
    		                {name: '甘肃',value: Math.round(Math.random()*1000)},
    		                {name: '山西',value: Math.round(Math.random()*1000)},
    		                {name: '内蒙古',value: Math.round(Math.random()*1000)},
    		                {name: '陕西',value: Math.round(Math.random()*1000)},
    		                {name: '吉林',value: Math.round(Math.random()*1000)},
    		                {name: '福建',value: Math.round(Math.random()*1000)},
    		                {name: '贵州',value: Math.round(Math.random()*1000)},
    		                {name: '广东',value: Math.round(Math.random()*1000)},
    		                {name: '青海',value: Math.round(Math.random()*1000)},
    		                {name: '西藏',value: Math.round(Math.random()*1000)},
    		                {name: '四川',value: Math.round(Math.random()*1000)},
    		                {name: '宁夏',value: Math.round(Math.random()*1000)},
    		                {name: '海南',value: Math.round(Math.random()*1000)},
    		                {name: '台湾',value: Math.round(Math.random()*1000)},
    		                {name: '香港',value: Math.round(Math.random()*1000)},
    		                {name: '澳门',value: Math.round(Math.random()*1000)}
    		            ]
    		        },
    		        {
    		            name: 'iphone4',
    		            type: 'map',
    		            mapType: 'china',
    		            itemStyle:{
    		                normal:{label:{show:true}},
    		                emphasis:{label:{show:true}}
    		            },
    		            data:[
    		                {name: '北京',value: Math.round(Math.random()*1000)},
    		                {name: '天津',value: Math.round(Math.random()*1000)},
    		                {name: '上海',value: Math.round(Math.random()*1000)},
    		                {name: '重庆',value: Math.round(Math.random()*1000)},
    		                {name: '河北',value: Math.round(Math.random()*1000)},
    		                {name: '安徽',value: Math.round(Math.random()*1000)},
    		                {name: '新疆',value: Math.round(Math.random()*1000)},
    		                {name: '浙江',value: Math.round(Math.random()*1000)},
    		                {name: '江西',value: Math.round(Math.random()*1000)},
    		                {name: '山西',value: Math.round(Math.random()*1000)},
    		                {name: '内蒙古',value: Math.round(Math.random()*1000)},
    		                {name: '吉林',value: Math.round(Math.random()*1000)},
    		                {name: '福建',value: Math.round(Math.random()*1000)},
    		                {name: '广东',value: Math.round(Math.random()*1000)},
    		                {name: '西藏',value: Math.round(Math.random()*1000)},
    		                {name: '四川',value: Math.round(Math.random()*1000)},
    		                {name: '宁夏',value: Math.round(Math.random()*1000)},
    		                {name: '香港',value: Math.round(Math.random()*1000)},
    		                {name: '澳门',value: Math.round(Math.random()*1000)}
    		            ]
    		        },
    		        {
    		            name: 'iphone5',
    		            type: 'map',
    		            mapType: 'china',
    		            itemStyle:{
    		                normal:{label:{show:true}},
    		                emphasis:{label:{show:true}}
    		            },
    		            data:[
    		                {name: '北京',value: Math.round(Math.random()*1000)},
    		                {name: '天津',value: Math.round(Math.random()*1000)},
    		                {name: '上海',value: Math.round(Math.random()*1000)},
    		                {name: '广东',value: Math.round(Math.random()*1000)},
    		                {name: '台湾',value: Math.round(Math.random()*1000)},
    		                {name: '香港',value: Math.round(Math.random()*1000)},
    		                {name: '澳门',value: Math.round(Math.random()*1000)}
    		            ]
    		        }
    		    ]
    		};
    		                          
				                    
			chart.setOption(option);
	//	});
	},
	//事件河流图
	initByEventRiver:function() {
		
        var chart = echarts.init(document.getElementById('eventRiver-echart'));
        chart.showLoading({
    		text: '正在统计数据,请稍等...'
    	});
    	chart.hideLoading();
       /* $.post(contextpath+'/system/chart/bar',function(msg) {
        	linechart.hideLoading();
        	
			if (msg.code != 200) {
				top.showInfo('加载数据时出现错误!');
				return;
			}*/
			
    	option = {
    		    title : {
    		        text: 'Event River',
    		        subtext: '纯属虚构'
    		    },
    		    tooltip : {
    		        trigger: 'item',
    		        enterable: true
    		    },
    		    legend: {
    		        data:['财经事件', '政治事件']
    		    },
    		    toolbox: {
    		        show : true,
    		        feature : {
    		            mark : {show: true},
    		            restore : {show: true},
    		            saveAsImage : {show: true}
    		        }
    		    },
    		    xAxis : [
    		        {
    		            type : 'time',
    		            boundaryGap: [0.05,0.1]
    		        }
    		    ],
    		    series : [
    		        {
    		            "name": "财经事件", 
    		            "type": "eventRiver", 
    		            "weight": 123, 
    		            "data": [
    		                {
    		                    "name": "阿里巴巴上市", 
    		                    "weight": 123, 
    		                    "evolution": [
    		                        {
    		                            "time": "2014-05-01", 
    		                            "value": 14, 
    		                            "detail": {
    		                                "link": "http://www.baidu.com", 
    		                                "text": "百度指数", 
    		                                "img": '../asset/ico/favicon.png'
    		                            }
    		                        }, 
    		                        {
    		                            "time": "2014-05-02", 
    		                            "value": 34, 
    		                            "detail": {
    		                                "link": "http://www.baidu.com", 
    		                                "text": "百度指数", 
    		                                "img": '../asset/ico/favicon.png'
    		                            }
    		                        }, 
    		                        {
    		                            "time": "2014-05-03", 
    		                            "value": 60, 
    		                            "detail": {
    		                                "link": "http://www.baidu.com", 
    		                                "text": "百度指数", 
    		                                "img": '../asset/ico/favicon.png'
    		                            }
    		                        }, 
    		                        {
    		                            "time": "2014-05-04", 
    		                            "value": 40, 
    		                            "detail": {
    		                                "link": "http://www.baidu.com", 
    		                                "text": "百度指数", 
    		                                "img": '../asset/ico/favicon.png'
    		                            }
    		                        }, 
    		                        {
    		                            "time": "2014-05-05", 
    		                            "value": 10, 
    		                            "detail": {
    		                                "link": "http://www.baidu.com", 
    		                                "text": "百度指数", 
    		                                "img": '../asset/ico/favicon.png'
    		                            }
    		                        }
    		                    ]
    		                }, 
    		                {
    		                    "name": "阿里巴巴上市2", 
    		                    "weight": 123, 
    		                    "evolution": [
    		                        {
    		                            "time": "2014-05-02", 
    		                            "value": 10, 
    		                            "detail": {
    		                                "link": "www.baidu.com", 
    		                                "text": "百度指数", 
    		                                "img": '../asset/ico/favicon.png'
    		                            }
    		                        }, 
    		                        {
    		                            "time": "2014-05-03", 
    		                            "value": 34, 
    		                            "detail": {
    		                                "link": "http://www.baidu.com", 
    		                                "text": "百度指数", 
    		                                "img": '../asset/ico/favicon.png'
    		                            }
    		                        }, 
    		                        {
    		                            "time": "2014-05-04", 
    		                            "value": 40, 
    		                            "detail": {
    		                                "link": "http://www.baidu.com", 
    		                                "text": "百度指数", 
    		                                "img": '../asset/ico/favicon.png'
    		                            }
    		                        }, 
    		                        {
    		                            "time": "2014-05-05", 
    		                            "value": 10, 
    		                            "detail": {
    		                                "link": "http://www.baidu.com", 
    		                                "text": "百度指数", 
    		                                "img": '../asset/ico/favicon.png'
    		                            }
    		                        }
    		                    ]
    		                }, 
    		                {
    		                    "name": "三星业绩暴跌", 
    		                    "weight": 123, 
    		                    "evolution": [
    		                        {
    		                            "time": "2014-05-03", 
    		                            "value": 24, 
    		                            "detail": {
    		                                "link": "www.baidu.com", 
    		                                "text": "百度指数", 
    		                                "img": '../asset/ico/favicon.png'
    		                            }
    		                        }, 
    		                        {
    		                            "time": "2014-05-04", 
    		                            "value": 34, 
    		                            "detail": {
    		                                "link": "http://www.baidu.com", 
    		                                "text": "百度指数", 
    		                                "img": '../asset/ico/favicon.png'
    		                            }
    		                        }, 
    		                        {
    		                            "time": "2014-05-05", 
    		                            "value": 50, 
    		                            "detail": {
    		                                "link": "http://www.baidu.com", 
    		                                "text": "百度指数", 
    		                                "img": '../asset/ico/favicon.png'
    		                            }
    		                        }, 
    		                        {
    		                            "time": "2014-05-06", 
    		                            "value": 30, 
    		                            "detail": {
    		                                "link": "http://www.baidu.com", 
    		                                "text": "百度指数", 
    		                                "img": '../asset/ico/favicon.png'
    		                            }
    		                        }, 
    		                        {
    		                            "time": "2014-05-07", 
    		                            "value": 20, 
    		                            "detail": {
    		                                "link": "http://www.baidu.com", 
    		                                "text": "百度指数", 
    		                                "img": '../asset/ico/favicon.png'
    		                            }
    		                        }
    		                    ]
    		                }
    		            ]
    		        }, 
    		        {
    		            "name": "政治事件", 
    		            "type": "eventRiver", 
    		            "weight": 123, 
    		            "data": [
    		                {
    		                    "name": "Apec峰会", 
    		                    "weight": 123, 
    		                    "evolution": [
    		                        {
    		                            "time": "2014-05-06", 
    		                            "value": 14, 
    		                            "detail": {
    		                                "link": "www.baidu.com", 
    		                                "text": "百度指数", 
    		                                "img": '../asset/ico/favicon.png'
    		                            }
    		                        }, 
    		                        {
    		                            "time": "2014-05-07", 
    		                            "value": 34, 
    		                            "detail": {
    		                                "link": "http://www.baidu.com", 
    		                                "text": "百度指数", 
    		                                "img": '../asset/ico/favicon.png'
    		                            }
    		                        }, 
    		                        {
    		                            "time": "2014-05-08", 
    		                            "value": 60, 
    		                            "detail": {
    		                                "link": "http://www.baidu.com", 
    		                                "text": "百度指数", 
    		                                "img": '../asset/ico/favicon.png'
    		                            }
    		                        }, 
    		                        {
    		                            "time": "2014-05-09", 
    		                            "value": 40, 
    		                            "detail": {
    		                                "link": "http://www.baidu.com", 
    		                                "text": "百度指数", 
    		                                "img": '../asset/ico/favicon.png'
    		                            }
    		                        }, 
    		                        {
    		                            "time": "2014-05-10", 
    		                            "value": 20, 
    		                            "detail": {
    		                                "link": "http://www.baidu.com", 
    		                                "text": "百度指数", 
    		                                "img": '../asset/ico/favicon.png'
    		                            }
    		                        }
    		                    ]
    		                }, 
    		                {
    		                    "name": "运城官帮透视", 
    		                    "weight": 123, 
    		                    "evolution": [
    		                        {
    		                            "time": "2014-05-08", 
    		                            "value": 4, 
    		                            "detail": {
    		                                "link": "www.baidu.com", 
    		                                "text": "百度指数", 
    		                                "img": '../asset/ico/favicon.png'
    		                            }
    		                        }, 
    		                        {
    		                            "time": "2014-05-09", 
    		                            "value": 14, 
    		                            "detail": {
    		                                "link": "http://www.baidu.com", 
    		                                "text": "百度指数", 
    		                                "img": '../asset/ico/favicon.png'
    		                            }
    		                        }, 
    		                        {
    		                            "time": "2014-05-10", 
    		                            "value": 30, 
    		                            "detail": {
    		                                "link": "http://www.baidu.com", 
    		                                "text": "百度指数", 
    		                                "img": '../asset/ico/favicon.png'
    		                            }
    		                        }, 
    		                        {
    		                            "time": "2014-05-11", 
    		                            "value": 20, 
    		                            "detail": {
    		                                "link": "http://www.baidu.com", 
    		                                "text": "百度指数", 
    		                                "img": '../asset/ico/favicon.png'
    		                            }
    		                        }, 
    		                        {
    		                            "time": "2014-05-12", 
    		                            "value": 10, 
    		                            "detail": {
    		                                "link": "http://www.baidu.com", 
    		                                "text": "百度指数", 
    		                                "img": '../asset/ico/favicon.png'
    		                            }
    		                        }
    		                    ]
    		                }, 
    		                {
    		                    "name": "底层公务员收入超过副部长", 
    		                    "weight": 123, 
    		                    "evolution": [
    		                        {
    		                            "time": "2014-05-11", 
    		                            "value": 4, 
    		                            "detail": {
    		                                "link": "www.baidu.com", 
    		                                "text": "百度指数", 
    		                                "img": '../asset/ico/favicon.png'
    		                            }
    		                        }, 
    		                        {
    		                            "time": "2014-05-12", 
    		                            "value": 24, 
    		                            "detail": {
    		                                "link": "http://www.baidu.com", 
    		                                "text": "百度指数", 
    		                                "img": '../asset/ico/favicon.png'
    		                            }
    		                        }, 
    		                        {
    		                            "time": "2014-05-13", 
    		                            "value": 40, 
    		                            "detail": {
    		                                "link": "http://www.baidu.com", 
    		                                "text": "百度指数", 
    		                                "img": '../asset/ico/favicon.png'
    		                            }
    		                        }, 
    		                        {
    		                            "time": "2014-05-14", 
    		                            "value": 20, 
    		                            "detail": {
    		                                "link": "http://www.baidu.com", 
    		                                "text": "百度指数", 
    		                                "img": '../asset/ico/favicon.png'
    		                            }
    		                        }, 
    		                        {
    		                            "time": "2014-05-15", 
    		                            "value": 15, 
    		                            "detail": {
    		                                "link": "http://www.baidu.com", 
    		                                "text": "百度指数", 
    		                                "img": '../asset/ico/favicon.png'
    		                            }
    		                        }, 
    		                        {
    		                            "time": "2014-05-16", 
    		                            "value": 10, 
    		                            "detail": {
    		                                "link": "http://www.baidu.com", 
    		                                "text": "百度指数", 
    		                                "img": '../asset/ico/favicon.png'
    		                            }
    		                        }
    		                    ]
    		                }
    		            ]
    		        }
    		    ]
    		};		                           
				                    
			chart.setOption(option);
	//	});
	},
	
	//散点图
	initByScatter:function() {
		
        var chart = echarts.init(document.getElementById('scatter-echart'));
        chart.showLoading({
    		text: '正在统计数据,请稍等...'
    	});
    	chart.hideLoading();
       /* $.post(contextpath+'/system/chart/bar',function(msg) {
        	linechart.hideLoading();
        	
			if (msg.code != 200) {
				top.showInfo('加载数据时出现错误!');
				return;
			}*/
			
    	option = {
    		    title : {
    		        text: '男性女性身高体重分布',
    		        subtext: '抽样调查来自: Heinz  2003'
    		    },
    		    tooltip : {
    		        trigger: 'axis',
    		        showDelay : 0,
    		        formatter : function (params) {
    		            if (params.value.length > 1) {
    		                return params.seriesName + ' :<br/>'
    		                   + params.value[0] + 'cm ' 
    		                   + params.value[1] + 'kg ';
    		            }
    		            else {
    		                return params.seriesName + ' :<br/>'
    		                   + params.name + ' : '
    		                   + params.value + 'kg ';
    		            }
    		        },  
    		        axisPointer:{
    		            show: true,
    		            type : 'cross',
    		            lineStyle: {
    		                type : 'dashed',
    		                width : 1
    		            }
    		        }
    		    },
    		    legend: {
    		        data:['女性','男性']
    		    },
    		    toolbox: {
    		        show : true,
    		        feature : {
    		            mark : {show: true},
    		            dataZoom : {show: true},
    		            dataView : {show: true, readOnly: false},
    		            restore : {show: true},
    		            saveAsImage : {show: true}
    		        }
    		    },
    		    xAxis : [
    		        {
    		            type : 'value',
    		            scale:true,
    		            axisLabel : {
    		                formatter: '{value} cm'
    		            }
    		        }
    		    ],
    		    yAxis : [
    		        {
    		            type : 'value',
    		            scale:true,
    		            axisLabel : {
    		                formatter: '{value} kg'
    		            }
    		        }
    		    ],
    		    series : [
    		        {
    		            name:'女性',
    		            type:'scatter',
    		       /*     data:List<List<Integer>>//格式*/    		            
    		            data: [[161.2, 51.6], [167.5, 59.0], [159.5, 49.2], [157.0, 63.0], [155.8, 53.6],
    		                [170.0, 59.0], [159.1, 47.6], [166.0, 69.8], [176.2, 66.8], [160.2, 75.2],
    		                [172.5, 55.2], [170.9, 54.2], [172.9, 62.5], [153.4, 42.0], [160.0, 50.0],
    		                [147.2, 49.8], [168.2, 49.2], [175.0, 73.2], [157.0, 47.8], [167.6, 68.8],
    		                [159.5, 50.6], [175.0, 82.5], [166.8, 57.2], [176.5, 87.8], [170.2, 72.8],
    		                [174.0, 54.5], [173.0, 59.8], [179.9, 67.3], [170.5, 67.8], [160.0, 47.0],
    		                [154.4, 46.2], [162.0, 55.0], [176.5, 83.0], [160.0, 54.4], [152.0, 45.8],
    		                [162.1, 53.6], [170.0, 73.2], [160.2, 52.1], [161.3, 67.9], [166.4, 56.6],
    		                [168.9, 62.3], [163.8, 58.5], [167.6, 54.5], [160.0, 50.2], [161.3, 60.3],
    		                [167.6, 58.3], [165.1, 56.2], [160.0, 50.2], [170.0, 72.9], [157.5, 59.8],
    		                [167.6, 61.0], [160.7, 69.1], [163.2, 55.9], [152.4, 46.5], [157.5, 54.3],
    		                [168.3, 54.8], [180.3, 60.7], [165.5, 60.0], [165.0, 62.0], [164.5, 60.3],
    		                [156.0, 52.7], [160.0, 74.3], [163.0, 62.0], [165.7, 73.1], [161.0, 80.0],
    		                [162.0, 54.7], [166.0, 53.2], [174.0, 75.7], [172.7, 61.1], [167.6, 55.7],
    		                [151.1, 48.7], [164.5, 52.3], [163.5, 50.0], [152.0, 59.3], [169.0, 62.5],
    		                [164.0, 55.7], [161.2, 54.8], [155.0, 45.9], [170.0, 70.6], [176.2, 67.2],
    		                [170.0, 69.4], [162.5, 58.2], [170.3, 64.8], [164.1, 71.6], [169.5, 52.8],
    		                [163.2, 59.8], [154.5, 49.0], [159.8, 50.0], [173.2, 69.2], [170.0, 55.9],
    		                [161.4, 63.4], [169.0, 58.2], [166.2, 58.6], [159.4, 45.7], [162.5, 52.2],
    		                [159.0, 48.6], [162.8, 57.8], [159.0, 55.6], [179.8, 66.8], [162.9, 59.4],
    		                [161.0, 53.6], [151.1, 73.2], [168.2, 53.4], [168.9, 69.0], [173.2, 58.4],
    		                [171.8, 56.2], [178.0, 70.6], [164.3, 59.8], [163.0, 72.0], [168.5, 65.2],
    		                [166.8, 56.6], [172.7, 105.2], [163.5, 51.8], [169.4, 63.4], [167.8, 59.0],
    		                [159.5, 47.6], [167.6, 63.0], [161.2, 55.2], [160.0, 45.0], [163.2, 54.0],
    		                [162.2, 50.2], [161.3, 60.2], [149.5, 44.8], [157.5, 58.8], [163.2, 56.4],
    		                [172.7, 62.0], [155.0, 49.2], [156.5, 67.2], [164.0, 53.8], [160.9, 54.4],
    		                [162.8, 58.0], [167.0, 59.8], [160.0, 54.8], [160.0, 43.2], [168.9, 60.5],
    		                [158.2, 46.4], [156.0, 64.4], [160.0, 48.8], [167.1, 62.2], [158.0, 55.5],
    		                [167.6, 57.8], [156.0, 54.6], [162.1, 59.2], [173.4, 52.7], [159.8, 53.2],
    		                [170.5, 64.5], [159.2, 51.8], [157.5, 56.0], [161.3, 63.6], [162.6, 63.2],
    		                [160.0, 59.5], [168.9, 56.8], [165.1, 64.1], [162.6, 50.0], [165.1, 72.3],
    		                [166.4, 55.0], [160.0, 55.9], [152.4, 60.4], [170.2, 69.1], [162.6, 84.5],
    		                [170.2, 55.9], [158.8, 55.5], [172.7, 69.5], [167.6, 76.4], [162.6, 61.4],
    		                [167.6, 65.9], [156.2, 58.6], [175.2, 66.8], [172.1, 56.6], [162.6, 58.6],
    		                [160.0, 55.9], [165.1, 59.1], [182.9, 81.8], [166.4, 70.7], [165.1, 56.8],
    		                [177.8, 60.0], [165.1, 58.2], [175.3, 72.7], [154.9, 54.1], [158.8, 49.1],
    		                [172.7, 75.9], [168.9, 55.0], [161.3, 57.3], [167.6, 55.0], [165.1, 65.5],
    		                [175.3, 65.5], [157.5, 48.6], [163.8, 58.6], [167.6, 63.6], [165.1, 55.2],
    		                [165.1, 62.7], [168.9, 56.6], [162.6, 53.9], [164.5, 63.2], [176.5, 73.6],
    		                [168.9, 62.0], [175.3, 63.6], [159.4, 53.2], [160.0, 53.4], [170.2, 55.0],
    		                [162.6, 70.5], [167.6, 54.5], [162.6, 54.5], [160.7, 55.9], [160.0, 59.0],
    		                [157.5, 63.6], [162.6, 54.5], [152.4, 47.3], [170.2, 67.7], [165.1, 80.9],
    		                [172.7, 70.5], [165.1, 60.9], [170.2, 63.6], [170.2, 54.5], [170.2, 59.1],
    		                [161.3, 70.5], [167.6, 52.7], [167.6, 62.7], [165.1, 86.3], [162.6, 66.4],
    		                [152.4, 67.3], [168.9, 63.0], [170.2, 73.6], [175.2, 62.3], [175.2, 57.7],
    		                [160.0, 55.4], [165.1, 104.1], [174.0, 55.5], [170.2, 77.3], [160.0, 80.5],
    		                [167.6, 64.5], [167.6, 72.3], [167.6, 61.4], [154.9, 58.2], [162.6, 81.8],
    		                [175.3, 63.6], [171.4, 53.4], [157.5, 54.5], [165.1, 53.6], [160.0, 60.0],
    		                [174.0, 73.6], [162.6, 61.4], [174.0, 55.5], [162.6, 63.6], [161.3, 60.9],
    		                [156.2, 60.0], [149.9, 46.8], [169.5, 57.3], [160.0, 64.1], [175.3, 63.6],
    		                [169.5, 67.3], [160.0, 75.5], [172.7, 68.2], [162.6, 61.4], [157.5, 76.8],
    		                [176.5, 71.8], [164.4, 55.5], [160.7, 48.6], [174.0, 66.4], [163.8, 67.3]
    		            ],
    		            markPoint : {
    		                data : [
    		                    {type : 'max', name: '最大值'},
    		                    {type : 'min', name: '最小值'}
    		                ]
    		            },
    		            markLine : {
    		                data : [
    		                    {type : 'average', name: '平均值'}
    		                ]
    		            }
    		        },
    		        {
    		            name:'男性',
    		            type:'scatter',
    		       /*     data:msg.data*/
    		            data: [[174.0, 65.6], [175.3, 71.8], [193.5, 80.7], [186.5, 72.6], [187.2, 78.8],
    		                [181.5, 74.8], [184.0, 86.4], [184.5, 78.4], [175.0, 62.0], [184.0, 81.6],
    		                [180.0, 76.6], [177.8, 83.6], [192.0, 90.0], [176.0, 74.6], [174.0, 71.0],
    		                [184.0, 79.6], [192.7, 93.8], [171.5, 70.0], [173.0, 72.4], [176.0, 85.9],
    		                [176.0, 78.8], [180.5, 77.8], [172.7, 66.2], [176.0, 86.4], [173.5, 81.8],
    		                [178.0, 89.6], [180.3, 82.8], [180.3, 76.4], [164.5, 63.2], [173.0, 60.9],
    		                [183.5, 74.8], [175.5, 70.0], [188.0, 72.4], [189.2, 84.1], [172.8, 69.1],
    		                [170.0, 59.5], [182.0, 67.2], [170.0, 61.3], [177.8, 68.6], [184.2, 80.1],
    		                [186.7, 87.8], [171.4, 84.7], [172.7, 73.4], [175.3, 72.1], [180.3, 82.6],
    		                [182.9, 88.7], [188.0, 84.1], [177.2, 94.1], [172.1, 74.9], [167.0, 59.1],
    		                [169.5, 75.6], [174.0, 86.2], [172.7, 75.3], [182.2, 87.1], [164.1, 55.2],
    		                [163.0, 57.0], [171.5, 61.4], [184.2, 76.8], [174.0, 86.8], [174.0, 72.2],
    		                [177.0, 71.6], [186.0, 84.8], [167.0, 68.2], [171.8, 66.1], [182.0, 72.0],
    		                [167.0, 64.6], [177.8, 74.8], [164.5, 70.0], [192.0, 101.6], [175.5, 63.2],
    		                [171.2, 79.1], [181.6, 78.9], [167.4, 67.7], [181.1, 66.0], [177.0, 68.2],
    		                [174.5, 63.9], [177.5, 72.0], [170.5, 56.8], [182.4, 74.5], [197.1, 90.9],
    		                [180.1, 93.0], [175.5, 80.9], [180.6, 72.7], [184.4, 68.0], [175.5, 70.9],
    		                [180.6, 72.5], [177.0, 72.5], [177.1, 83.4], [181.6, 75.5], [176.5, 73.0],
    		                [175.0, 70.2], [174.0, 73.4], [165.1, 70.5], [177.0, 68.9], [192.0, 102.3],
    		                [176.5, 68.4], [169.4, 65.9], [182.1, 75.7], [179.8, 84.5], [175.3, 87.7],
    		                [184.9, 86.4], [177.3, 73.2], [167.4, 53.9], [178.1, 72.0], [168.9, 55.5],
    		                [157.2, 58.4], [180.3, 83.2], [170.2, 72.7], [177.8, 64.1], [172.7, 72.3],
    		                [165.1, 65.0], [186.7, 86.4], [165.1, 65.0], [174.0, 88.6], [175.3, 84.1],
    		                [185.4, 66.8], [177.8, 75.5], [180.3, 93.2], [180.3, 82.7], [177.8, 58.0],
    		                [177.8, 79.5], [177.8, 78.6], [177.8, 71.8], [177.8, 116.4], [163.8, 72.2],
    		                [188.0, 83.6], [198.1, 85.5], [175.3, 90.9], [166.4, 85.9], [190.5, 89.1],
    		                [166.4, 75.0], [177.8, 77.7], [179.7, 86.4], [172.7, 90.9], [190.5, 73.6],
    		                [185.4, 76.4], [168.9, 69.1], [167.6, 84.5], [175.3, 64.5], [170.2, 69.1],
    		                [190.5, 108.6], [177.8, 86.4], [190.5, 80.9], [177.8, 87.7], [184.2, 94.5],
    		                [176.5, 80.2], [177.8, 72.0], [180.3, 71.4], [171.4, 72.7], [172.7, 84.1],
    		                [172.7, 76.8], [177.8, 63.6], [177.8, 80.9], [182.9, 80.9], [170.2, 85.5],
    		                [167.6, 68.6], [175.3, 67.7], [165.1, 66.4], [185.4, 102.3], [181.6, 70.5],
    		                [172.7, 95.9], [190.5, 84.1], [179.1, 87.3], [175.3, 71.8], [170.2, 65.9],
    		                [193.0, 95.9], [171.4, 91.4], [177.8, 81.8], [177.8, 96.8], [167.6, 69.1],
    		                [167.6, 82.7], [180.3, 75.5], [182.9, 79.5], [176.5, 73.6], [186.7, 91.8],
    		                [188.0, 84.1], [188.0, 85.9], [177.8, 81.8], [174.0, 82.5], [177.8, 80.5],
    		                [171.4, 70.0], [185.4, 81.8], [185.4, 84.1], [188.0, 90.5], [188.0, 91.4],
    		                [182.9, 89.1], [176.5, 85.0], [175.3, 69.1], [175.3, 73.6], [188.0, 80.5],
    		                [188.0, 82.7], [175.3, 86.4], [170.5, 67.7], [179.1, 92.7], [177.8, 93.6],
    		                [175.3, 70.9], [182.9, 75.0], [170.8, 93.2], [188.0, 93.2], [180.3, 77.7],
    		                [177.8, 61.4], [185.4, 94.1], [168.9, 75.0], [185.4, 83.6], [180.3, 85.5],
    		                [174.0, 73.9], [167.6, 66.8], [182.9, 87.3], [160.0, 72.3], [180.3, 88.6],
    		                [167.6, 75.5], [186.7, 101.4], [175.3, 91.1], [175.3, 67.3], [175.9, 77.7],
    		                [175.3, 81.8], [179.1, 75.5], [181.6, 84.5], [177.8, 76.6], [182.9, 85.0],
    		                [177.8, 102.5], [184.2, 77.3], [179.1, 71.8], [176.5, 87.9], [188.0, 94.3],
    		                [174.0, 70.9], [167.6, 64.5], [170.2, 77.3], [167.6, 72.3], [188.0, 87.3],
    		                [174.0, 80.0], [176.5, 82.3], [180.3, 73.6], [167.6, 74.1], [188.0, 85.9],
    		                [180.3, 73.2], [167.6, 76.3], [183.0, 65.9], [183.0, 90.9], [179.1, 89.1],
    		                [170.2, 62.3], [177.8, 82.7], [179.1, 79.1], [190.5, 98.2], [177.8, 84.1],
    		                [180.3, 83.2], [180.3, 83.2]
    		            ],
    		            markPoint : {
    		                data : [
    		                    {type : 'max', name: '最大值'},
    		                    {type : 'min', name: '最小值'}
    		                ]
    		            },
    		            markLine : {
    		                data : [
    		                    {type : 'average', name: '平均值'}
    		                ]
    		            }
    		        }
    		    ]
    		};
    		                                    
				                    
			chart.setOption(option);
	//	});
	},
	
	//韦恩图
	initByVenn:function() {
		
        var chart = echarts.init(document.getElementById('venn-echart'));
        chart.showLoading({
    		text: '正在统计数据,请稍等...'
    	});
    //	chart.hideLoading();
        $.post(contextpath+'/system/chart/data',{type:'venn'},function(msg) {
        	chart.hideLoading();
        	
			if (msg.code != 200) {
				top.showInfo('加载数据时出现错误!');
				return;
			}
    	option = {
    		    title : {
    		        text: '访问 vs 咨询',
    		        subtext: '各个数据的集合'
    		    },
    		    tooltip : {
    		        trigger: 'item',
    		        formatter: "{b}: {c}"
    		    },
    		    toolbox: {
    		        show : true,
    		        feature : {
    		            mark : {show: true},
    		            dataView : {show: true, readOnly: false},
    		            restore : {show: true},
    		            saveAsImage : {show: true}
    		        }
    		    },
    		    calculable : false,
    		    series : [
    		        {
    		            name:'韦恩图',
    		            type:'venn',
    		            itemStyle: {
    		                normal: {
    		                    label: {
    		                        show: true,
    		                        textStyle: {
    		                            fontFamily: 'Arial, Verdana, sans-serif',
    		                            fontSize: 16,
    		                            fontStyle: 'italic',
    		                            fontWeight: 'bolder'
    		                        }
    		                    },
    		                    labelLine: {
    		                        show: false,
    		                        length: 10,
    		                        lineStyle: {
    		                            // color: 各异,
    		                            width: 1,
    		                            type: 'solid'
    		                        }
    		                    }
    		                },
    		                emphasis: {
    		                    color: '#cc99cc',
    		                    borderWidth: 3,
    		                    borderColor: '#996699'
    		                }
    		            },
    		            data:msg.data
    		          /*  data:[
    		                {value:100, name:'访问'},
    		                {value:50, name:'咨询'},
    		                {value:20, name:'公共'}
    		            ]*/
    		        }
    		    ]
    		};
    		                                     
			chart.setOption(option);
		});
	},
	
	
	//仪表图
	initByGauge:function() {
		
        var chart = echarts.init(document.getElementById('gauge-echart'));
        chart.showLoading({
    		text: '正在统计数据,请稍等...'
    	});
    	chart.hideLoading();
       /* $.post(contextpath+'/system/chart/bar',function(msg) {
        	linechart.hideLoading();
        	
			if (msg.code != 200) {
				top.showInfo('加载数据时出现错误!');
				return;
			}*/
    	option = {
    		    tooltip : {
    		        formatter: "{a} <br/>{b} : {c}%"
    		    },
    		    toolbox: {
    		        show : true,
    		        feature : {
    		            mark : {show: true},
    		            restore : {show: true},
    		            saveAsImage : {show: true}
    		        }
    		    },
    		    series : [
    		        {
    		            name:'业务指标',
    		            type:'gauge',
    		            detail : {formatter:'{value}%'},
    		            data:[{value: 95, name: '完成率'}]
    		        }
    		    ]
    		};

    /*		clearInterval(timeTicket);
    		timeTicket = setInterval(function (){
    		    option.series[0].data[0].value = (Math.random()*100).toFixed(2) - 0;
    		    myChart.setOption(option, true);
    		},2000);*/
    		                    
    		                                     
			chart.setOption(option);
	//	});
	},
	
	//漏斗图
	initByFunnel:function() {
		
        var chart = echarts.init(document.getElementById('funnel-echart'));
        chart.showLoading({
    		text: '正在统计数据,请稍等...'
    	});
    //	chart.hideLoading();
    	 $.post(contextpath+'/system/chart/data',{type:'pie'},function(msg) {
    		 chart.hideLoading();
        	
			if (msg.code != 200) {
				top.showInfo('加载数据时出现错误!');
				return;
			}
    	option = {
    		    title : {
    		        text: '漏斗图',
    		        subtext: '纯属虚构'
    		    },
    		    tooltip : {
    		        trigger: 'item',
    		        formatter: "{a} <br/>{b} : {c}%"
    		    },
    		    toolbox: {
    		        show : true,
    		        feature : {
    		            mark : {show: true},
    		            dataView : {show: true, readOnly: false},
    		            restore : {show: true},
    		            saveAsImage : {show: true}
    		        }
    		    },
    		    legend: {
    		        data : ['展现','点击','访问','咨询','订单']
    		    },
    		    calculable : true,
    		    series : [
    		        {
    		            name:'漏斗图',
    		            type:'funnel',
    		            width: '50%',		         
    		            x: '25%',
    		            data:msg.data
    		           /* data:[
    		                {value:60, name:'访问'},
    		                {value:40, name:'咨询'},
    		                {value:20, name:'订单'},
    		                {value:80, name:'点击'},
    		                {value:100, name:'展现'}
    		            ]*/
    		        }
    		    ]
    		};
    		                    
    		                    
    		                                     
			chart.setOption(option);
		});
	}

});