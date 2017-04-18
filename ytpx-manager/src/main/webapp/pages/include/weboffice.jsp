<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--
参数列表:
readonly:	是否只读
dataurl:	word文档url路径,使用此参数时module,type,outerId不能再设置
height:		word文档的高度,默认700px,如果等于100%,那么取整个body的高度
filetype:	文件类型
--%>
<script type="text/javascript">
	var actionType = "";
	var dataurl = null;
	//在线打开url
	/* $(function() {
		var fileName = "${param.fileName}";
		dataurl = "${param.dataurl}";
		document.getElementById("office-box").innerHTML = "正在加载word...";
		projectDwr.downloadPrintTemplate(fileName, {
			callback : function(data) {
				if (data == null) {
					document.getElementById("office-box").innerHTML = "";
					top.showInfo('加载word失败!', 'error');
					return;
				}
				openUrlWebOffice(data);
			},
			exceptionHandler : function(e) {
				top.showInfo('加载word失败!', 'error');
			}
		});
	}); */
	//先初始weboffice控件,再初始化document对象
	//初始化weboffice
	function initWebOffice() {
		var weboffice = document.getElementById("weboffice");
		if (weboffice == null) {
			var height = "${param.height}";
			if (height == "")
				height = "700px";
			else if (height == "100%")
				height = $(document).height() + "px";
			var str = "";
			str += "<object id='weboffice' style='width:100%;height:"+ height+ "' "
					+ "classid='clsid:C9BC4DFF-4248-4a3c-8A49-63A7D317F404' "
					+ "codebase='${commonskin}/lib/ntko/OfficeControl.cab#Version=5,0,2,1'>"
					+ "<span style='color:red'>浏览器无法装载所需要的文档控件，请使用IE9浏览器，并且检查浏览器的安全设置。</span>"
					+ "</object>";
			document.getElementById("office-box").innerHTML = str;
			//初始化document
			initDocument();
		}
	}
	//初始化document
	function initDocument() {
		var weboffice = document.getElementById("weboffice");
		//增加pdf文档打开功能
		if ("${param.filetype}" == "pdf") {
			weboffice.AddDocTypePlugin(".pdf", "PDF.NtkoDocument", "4.0.0.2",
				"${commonskin}/lib/ntko/ntkooledocall.cab", 51, true);
		}
		//weboffice.DefaultOpenDocType=6;			//默认操作wps文件类型
		weboffice.TitleBar = false; //隐藏控件标题栏
		weboffice.Menubar = false; //显示自定义菜单栏
		weboffice.IsShowNetErrorMsg = false; //隐藏显示网络传输错误
		var readonly = false; //是否只读方式打开文件 
		if ("${param.readonly}" == "true") {
			readonly = true;
			weboffice.ToolBars = false;
		} else {
			readonly = false;
		}
		
		if (actionType == "openUrl") {
			weboffice.BeginOpenFromURL(dataurl, false, readonly);
		} else if (actionType == "open") {
			weboffice.ShowDialog(1);
			if (weboffice.DocType == 0) {
				closeWebOffice();
			}
		} else if (actionType == "new") {
			if(weboffice.GetWPSVer()!=100) {
				weboffice.CreateNew("WPS.Document");
			} else if(weboffice.GetOfficeVer()!=100) {
				weboffice.CreateNew("Word.Document");
			}
		} else if (actionType == "openAndNew") {
			weboffice.ShowDialog(1);
			if (weboffice.DocType == 0) {
				if (confirm("打开文档失败,是否要新建文档?")) {
					if(weboffice.GetWPSVer()!=100) {
						weboffice.CreateNew("WPS.Document");
					} else if(weboffice.GetOfficeVer()!=100) {
						weboffice.CreateNew("Word.Document");
					}
				} else {
					closeWebOffice();
				}
			}
		}

		$(window).unload(function() {
			try {
				weboffice.Close();
			} catch (e) {}
		});
	}
	//从url打开weboffice文档
	function openUrlWebOffice(url) {
		if (url) {
			dataurl = url;
		}
		actionType = "openUrl";
		initWebOffice();
	}
	//从本地打开weboffice文档
	function openWebOffice() {
		actionType = "open";
		initWebOffice();
	}
	//新建weboffice文档
	function newWebOffice() {
		actionType = "new";
		initWebOffice();
	}
	//先打开,打开失败,则新建weboffice文档
	function openAndNewWebOffice() {
		actionType = "openAndNew";
		initWebOffice();
	}
	function setupWebOffice() {
		actionType = "setup";
		initWebOffice();
	}
	//关闭weboffice文档
	function closeWebOffice() {
		var weboffice = document.getElementById("weboffice");
		if (weboffice != null) {
			try {
				weboffice.Close();
			} catch (e) {}
			document.getElementById("office-box").innerHTML = "";
		}
	}
	
	function openEmptyDoc() {
		var weboffice = document.getElementById("weboffice");
		if(weboffice.GetWPSVer()!=100) {
			weboffice.CreateNew("WPS.Document");
		} else if(weboffice.GetOfficeVer()!=100) {
			weboffice.CreateNew("Word.Document");
		}
	}
	function openLocalDoc() {
		$('#weboffice')[0].ShowDialog(1);
	}
	function savedoc() {
		$('#weboffice')[0].ShowDialog(3);
	}
	function printdoc() {
		$('#weboffice')[0].PrintOut(true);
	}
	function previewdoc() {
		$('#weboffice')[0].PrintPreview();
	}
	function openTemplateDoc() {
		$('#office-box').hide();
		top.contract_category=null;
		top.layer.open({
		    type: 2,
		    title: '选择合同分类模板',
		    shadeClose: true,
		    shade: 0.3,
		    area: ['600px', '500px'],
		    content: contextpath+'/contract/category/template',
		    end:function() {
		    	$('#office-box').show();
		    	if(!top.contract_category) return;
		    	var cate=top.contract_category;
		    	$('#weboffice')[0].BeginOpenFromURL('${contextpath}/contract/category/template/downtext/'+cate.id, false, false);
		    }
		});
	}
</script>
<script type="text/javascript" for="weboffice" event="OnDocumentOpened(url,dispatch)">
	$('#office-toolbar').show();
	if ("${param.readonly}" == "true") {
		var weboffice = document.getElementById("weboffice");
		weboffice.SetReadOnly(true, "13631414080"); //只读保护文档
	}
</script>
<div class="weboffice-box">
	<div id="office-toolbar" style="display:none;margin-bottom: 5px;">
		<c:if test="${param.readonly!='true'}">
			<button class="btn btn-primary btn-xs open-btn" onclick="openEmptyDoc();"><i class="fa fa-fw fa-file-text"></i>打开空白合同</button>
			<button class="btn btn-primary btn-xs open-btn" onclick="openLocalDoc();"><i class="fa fa-fw fa-file-text"></i>打开本地合同</button>
			<button class="btn btn-primary btn-xs open-btn" onclick="openTemplateDoc();"><i class="fa fa-fw fa-file-text"></i>打开合同模板</button>
		</c:if>
		<button class="btn btn-info btn-xs" onclick="savedoc();"><i class="fa fa-fw fa-save"></i>另存为</button>
		<button class="btn btn-info btn-xs" onclick="printdoc();"><i class="fa fa-fw fa-print"></i>打印</button>
		<button class="btn btn-info btn-xs" onclick="previewdoc();"><i class="fa fa-fw fa-print"></i>打印预览</button>
	</div>
	<div id="office-box"></div>
	<p></p>
</div>