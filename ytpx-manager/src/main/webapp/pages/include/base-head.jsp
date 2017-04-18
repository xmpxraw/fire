<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="w" uri="/yuantai-tags"%>
<!-- 以下是jsp页面的基本头部信息,所有jsp页面必须include此文件 -->
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" />
<meta name="renderer" content="webkit">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>${contextTitle}</title>
<link href="${commonskin}/css/common.css" rel="stylesheet" type="text/css"/>

    <link rel="stylesheet" type="text/css" href="${skin}/css/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css" href="${skin}/css/simple-line-icons.css"/>
    <link rel="stylesheet" type="text/css" href="${skin}/css/system/admin-c.css"/>

<!--[if !IE]><!--> <script type="text/javascript" src="${commonskin}/lib/jquery/jquery-2.1.4.min.js"></script> <!--<![endif]-->
<!--[if IE 9]> <script type="text/javascript" src="${commonskin}/lib/jquery/jquery-2.1.4.min.js"></script> <![endif]-->
<!--[if lt IE 9]>
	<script type="text/javascript" src="${commonskin}/lib/jquery/jquery-1.11.2.min.js"></script>
    <script src="${commonskin}/lib/AdminLTE-2.3.0/js/html5shiv.min.js"></script>
    <script src="${commonskin}/lib/AdminLTE-2.3.0/js/respond.min.js"></script>
<![endif]-->
<script type="text/javascript" src="${commonskin}/lib/namespace.js"></script>
<script type="text/javascript" src="${commonskin}/lib/layer-2.1/layer.js"></script>
<script type="text/javascript" src="${commonskin}/js/common.js"></script>
<script type="text/javascript" src="${commonskin}/lib/jQuery.cxSelect-1.4.0/js/jquery.cxselect.js"></script>

<script type="text/javascript">
var contextpath='${contextpath}',commonskin='${commonskin}',skin='${skin}',domain='${domain}';

</script>