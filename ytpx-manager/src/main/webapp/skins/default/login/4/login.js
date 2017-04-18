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
	if($('#system-msg').is(":visible")) {
		$('#system-msg').fadeOut('normal',doLogin);
	} else {
		doLogin();
	}
}

function doLogin() {
	var form=$('#login-form')[0];
	if(form.username.value=='') {
		$('#system-msg').text('请输入用户名！').show();
		$('input[name=username]').show();
		return;
	}
	if(form.password.value=='') {
		$('#system-msg').text('请输入密码！').show();
		$('input[name=password]').show();
		return;
	}
	$.post(contextpath+'/login',$('#login-form').serialize(),function(result) {
		if(result.code==200) {
			window.location.href=result.msg;
		} else {
			$('#system-msg').text(result.msg).show();
		}
	},'json');
}
