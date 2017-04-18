$(function() {
	initString();
	initDate();
	
	if(!window.console) {
		//修复ie8没有
		window.console={log:function(){}}
	}
});

//提交json对象数据
$.postJson=function(url,data,callback) {
	$.ajax({
		url:url,
		type:'POST',
		contentType:'application/json',
		data:JSON.stringify(data||{}),
		dataType:'json',
		success:callback||$.noop
	});
};

//自适应调度
$.fn.autoHeight = function() {
	
	function autoHeight(elem) {
		elem.style.overflow='hidden';
		elem.style.height = elem.scrollHeight + 'px';
	}
	
	this.each(function(){
		autoHeight(this);
		$(this).on('keyup', function(){
			autoHeight(this);
		});
	});
}


/**
 * 显示提示信息--在右下角从下往上弹出信息窗口
 * @param {} text
 */
function showInfo(text,width,height) {
	var w=width||300;
	var h=height||130;
	layer.msg(text);
	/*$.messager.show({
		title:'提示信息',
		msg:text,
		width:w,
		height:h,
		timeout:5000,
		showType:'slide'
	});*/
}

/**
 * 初始化字符串功能
 */
function initString() {
	
	// 判断是否为空字符串
	String.isEmpty=function(str) {		//这是一个静态方法
		if (str == null) return true;
		return /^\s*$/.test(str);
	};
	
	//----以下是实例方法
	// 判断是否以指定字符串开始
	String.prototype.startWith = function(str) {
		var reg = new RegExp("^" + str);
		return reg.test(this);
	};
	// 判断是否以指定字符串结束
	String.prototype.endWith = function(str) {
		var reg = new RegExp(str + "$");
		return reg.test(this);
	};
	// 截取字符串前后空格
	String.prototype.trim = function() {
		return this.replace(/^\s+|\s+$/g, "");
	};
	// 判断是否整数
	String.prototype.isNumber = function() {
		if (String.isEmpty(this)) return false;
		return /^\d+$/.test(this);
	};
	// 判断是否汉字
	String.prototype.isChinese = function() {
		if (String.isEmpty(this)) return false;
		return /^[\u4E00-\u9FBF]+$/.test(this);
	};
	// 获取字符串的字节数
	String.prototype.getByteNum = function() {
		if (String.isEmpty(this)) return 0;
		return this.replace(/[^\x00-\xff]/g, "**").length;
	};
}

/**
 * 初始化日期处理功能
 */
function initDate() {
	
	/**
	 * 初始化Date对象,为其增加pattern方法,使它具有转换为指定格式字符串的功能
	 * 用法:(new Date()).pattern("yyyy-MM-dd hh:mm:ss") ==> 2013-10-24 11:50:20 
	 */
	Date.prototype.pattern=function(fmt) {
	    var o = {
	    "M+" : this.getMonth()+1, //月份
	    "d+" : this.getDate(), //日
	    "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时
	    "H+" : this.getHours(), //小时
	    "m+" : this.getMinutes(), //分
	    "s+" : this.getSeconds(), //秒
	    "q+" : Math.floor((this.getMonth()+3)/3), //季度
	    "S" : this.getMilliseconds() //毫秒
	    };
	    var week = {
	    "0" : "/u65e5",
	    "1" : "/u4e00",
	    "2" : "/u4e8c",
	    "3" : "/u4e09",
	    "4" : "/u56db",
	    "5" : "/u4e94",
	    "6" : "/u516d"
	    };
	    if(/(y+)/.test(fmt)){
	        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
	    }
	    if(/(E+)/.test(fmt)){
	        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);
	    }
	    for(var k in o){
	        if(new RegExp("("+ k +")").test(fmt)){
	            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
	        }
	    }
	    return fmt;
	};
	
	/**
	 * 获取指定月份的最后一天
	 * @param year
	 * @param month
	 * @returns
	 */
	Date.getLastDayInMonth=function(year,month){
	      month = parseInt(month,10)+1;
	      var temp = new Date(year+"/"+month+"/0");
	      return temp.getDate();
	};
}