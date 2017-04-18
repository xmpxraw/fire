$(function() {
	//检查登录页是不是在顶级窗口,如果不是,那么使用顶级窗口重新加载登录页
	if(top!=window) {
		top.location.href=contextpath;
		return;
	}
	
	var loginUser=$.cookie('login_user');
	if(loginUser==null || loginUser=='' || loginUser=='null') {
		$('input[name=username]').focus();
	} else {
		$('input[name=username]').val(loginUser);
		$('input[name=password]').focus();
		$('.rem').addClass('selected');
	}
	
    $('.rem').click(function(){
        $(this).toggleClass('selected');
    });
    
    $('input[name=password]').keydown(function(event) {
    	if(event.keyCode==13) {
    		login();
    	}
    });

/*    $('#signup_select').click(function(){
        $('.form_row ul').show();
        event.cancelBubble = true;
    })

    $('body').click(function(){
        $('.form_row ul').hide();
    })

    $('.form_row li').click(function(){
        var v  = $(this).text();
        $('#signup_select').val(v);
        $('.form_row ul').hide();
    })*/
});

function login() {
	if(!$('#login-form').form('validate')) return;
	
	if($('.rem').hasClass('selected')) {
		var cookieUser=$.cookie('login_user');
		var nowUser=$('input[name=username]').val();
		if(nowUser!=cookieUser) {
			$.cookie('login_user', nowUser, {expires: 30, path: '/'});
		}
	} else {
		$.cookie('login_user', '', {expires: -1, path: '/'});
	}
	
	$.post(contextpath+'/login',$('#login-form').serialize(),function(result) {
		if(result.code==200) {
			window.location.href=result.msg;
		} else {
			$('#system-msg').text(result.msg).show();
		}
	},'json');
}