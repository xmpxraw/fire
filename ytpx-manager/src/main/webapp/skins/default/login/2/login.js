$(function() {
	//检查登录页是不是在顶级窗口,如果不是,那么使用顶级窗口重新加载登录页
	if(top!=window) {
		top.location.href=contextpath;
		return;
	}
	$('input[name=username]').focus();
	$('input[name=password]').keydown(function(event) {
    	if(event.keyCode==13) {
    		login();
    	}
    });
});

function login() {
	if(!$('#login-form').form('validate')) return;
	$.post(contextpath+'/login',$('#login-form').serialize(),function(result) {
		if(result.code==200) {
			window.location.href=result.msg;
		} else {
			$('#system-msg').text(result.msg).show();
		}
	},'json');
}

