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
       		<div class="box box-info">
                <div class="box-header with-border">
                  <h3 class="box-title">意见反馈</h3>
                </div><!-- /.box-header -->
                <!-- form start -->
                <form id="data-form" class="form-horizontal">
                  <div class="box-body">
                    <div class="form-group">
                      <label class="col-sm-1 control-label">标题</label>
                      <div class="col-sm-11">
                        <input type="text" class="form-control" name="name" placeholder="请输入意见标题" />
                      </div>
                    </div>
                    <div class="form-group">
                      <label class="col-sm-1 control-label">内容</label>
                      <div class="col-sm-11">
                      	<script id="content" name="content" type="text/plain" style="width:99%;height:220px;"></script>
                      </div>
                    </div>
                  </div><!-- /.box-body -->
                  <div class="box-footer">
                    <button type="button" class="btn btn-default" onclick="top.closeTab('active');">关闭</button>
                    <button type="button" class="btn btn-info pull-right" onclick="$('#data-form').submit();">提交意见</button>
                  </div><!-- /.box-footer -->
                </form>
              </div>
       </div>
     </div>
  </div>
	<%@include file="/pages/include/bootstrap-js.jsp"%>
	<%@include file="/pages/include/umeditor.jsp"%>
	<script type="text/javascript" src="${commonskin}/lib/jquery/validate/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${commonskin}/lib/jquery/validate/additional-methods.min.js"></script>
	<script type="text/javascript" src="${commonskin}/lib/jquery/validate/localization/messages_zh.min.js"></script>
	<script type="text/javascript">
	
	$(function() {
		$('#data-form').validate({
			rules : {
				name : 'required'
			},
			errorPlacement: function(err, ele) {
				ele.closest('.form-group').removeClass('has-success').addClass('has-error');
				err.addClass('control-label')
					.prepend('<i class="fa fa-times-circle-o"></i>')
					.appendTo(ele.parent());
			},
			success: function(label) {
				label.closest('.form-group').removeClass('has-error').addClass('has-success');
				label.remove();
			},
			submitHandler: function(form) {
				submitFeedback();
			}
		});
		
		var um = UM.getEditor('content',{
			autoHeight: false
		});
	});
	function submitFeedback() {
		
		var form=$('#data-form')[0];
		var data={
				name:form.name.value,
				content:UM.getEditor('content').getContent(),
				status:1,
				resolvestatus:1
		};
		$.postJson('${contextpath}/basic/feedback/add',data,function(msg) {
			if(msg.code!=200) {
				showInfo('提交失败,请联系管理员!');
				return;
			}
			top.showInfo('提交意见成功!');
			window.location.href='${contextpath}/basic/feedback/homelist';
		});
	}
	</script>
</body>
</html>
