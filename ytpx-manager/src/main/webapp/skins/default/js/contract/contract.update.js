$(function() {
	contract.process.init();
});

Namespace('contract.process',{
	
	init:function() {
		
		$('#budgetid').change(function() {
			var option=this.options[this.selectedIndex];
			$('#budgetname').val(option.text);
			$('#budgetprice').val($(option).attr('data-price'));
		});
		$('#categoryid').change(function() {
			var option=this.options[this.selectedIndex];
			$('#categoryname').val(option.text);
		});
		$('.startdate').datepicker({
			language: 'zh-CN',
			format:'yyyy-mm-dd',
			autoclose: true,
			clearBtn: true
		});
		$('textarea[autoheight]').autoHeight();
		
		this.inifscroll();
		this.initform();
		this.initAdjunct();
	},
	
	inifscroll:function() {
		
		$(window).scroll(function() {
			if($(window).scrollTop()>15) {
				if(!$('.process-nav').hasClass('fixed-nav')) {
					var width=$('.process-nav').width();
					var height=$('.process-nav').height();
					$('#header-space').height(height+15);
					$('.process-nav').addClass('fixed-nav').width(width);
				}
			} else {
				if($('.process-nav').hasClass('fixed-nav')) {
					$('#header-space').height(0);
					$('.process-nav').removeClass('fixed-nav');
				}
			}
		});
	},
	
	initform:function() {
		$('#data-form').validate({
			rules : {
				party1 : 'required',
				party2 : 'required',
				title : 'required',
				summary : 'required',
				price : {required:true,number:true},
				bidprice : {required:false,number:true},
				budgetid : 'required',
				tel : 'required',
				categoryid : 'required'
			},
			messages: {
				budgetid:'这是必填字段，找不到预算项目时，请联系系统管理员',
				categoryid:'这是必填字段，找不到分类时，请联系系统管理员'
			},
			errorPlacement: function(err, ele) {
				ele.closest('.form-group').removeClass('has-success').addClass('has-error');
				err.addClass('control-label')
					.prepend('<i class="fa fa-times-circle-o"></i>')
					.appendTo(ele.closest('.form-group'));
			},
			success: function(label) {
				label.closest('.form-group').removeClass('has-error').addClass('has-success');
				label.remove();
			},
			submitHandler: function(form) {
				contract.process.dosave();
			}
		});
	},
	
	initAdjunct:function() {
		
		var uploader = new plupload.Uploader({
			runtimes : 'html5,flash,silverlight,html4',
			browse_button : 'uploadfile',
			drop_element : 'filebox',
			container: $('filebox')[0],
			url : contextpath+'/contract/process/adjunct/upload',
			flash_swf_url : commonskin+'/lib/plupload-2.1.8/js/Moxie.swf',
			silverlight_xap_url : commonskin+'/lib/plupload-2.1.8/js/Moxie.xap',
			filters : {
				max_file_size : '200mb',
				prevent_duplicates:false
			},
			multi_selection:true,
			unique_names:true,
			file_data_name:'file',
			preinit : {
		        Init: function(up, info) {
		            console.log('[Init]', 'Info:', info, 'Features:', up.features);
		        },
		        UploadFile: function(up, file) {
		            //此处可以覆盖默认设置
		            up.setOption('multipart_params',
		            	{
			            	contractid:$('#data-form')[0].id.value,
			            	filesizestr:plupload.formatSize(file.size)
			            });
		        }
		    },
			init: {
				PostInit: function() {},
				FilesAdded: function(up, files) {
					plupload.each(files, function(file) {
						var sample=$('#adjunct-sample').clone();
						var $td=sample.attr('id',file.id).find('td');
						$td.eq(0).text($('#adjunct-table tr').length-1);
						$td.eq(1).find('a').text(file.name);
						$td.eq(2).text(plupload.formatSize(file.size));
						$td.eq(5).find('.btn-del').hide();
						sample.appendTo($('#adjunct-table tbody')).show('slow');
					});
					$(window).scrollTop($(document).height());
					uploader.start();
				},
				UploadProgress: function(up, file) {
					$('#'+file.id).find('.progress-bar').width(file.percent+'%');
					$('#'+file.id).find('.badge').text(file.percent+'%');
				},
				FileUploaded: function(up, file, res) {
					console.log('fileuploaded:'+res.response);
					var msg=JSON.parse(res.response);
					if(msg.code!=200) {
						console.log('上传文件出现错误');
						showInfo('上传文件出现错误!');
						$('#'+file.id).remove('slow');
					} else {
						$td=$('#'+file.id).attr('id',msg.data.id).find('td');
						$td.eq(3).text(msg.data.organname);
						$td.eq(4).text(msg.data.createtime);
						$td.eq(5).find('.btn-del').show('slow');
					}
				},
				Error: function(up, err) {
					console.log('上传文件出现错误:'+err.code+':'+err.message);
					showInfo('上传文件出现错误:'+err.code+':'+err.message);
					$('#'+err.file.id).remove('slow');
				}
			}
		});
		uploader.init();
		
		$(window).unload(function() {
			uploader.destroy();
		});
	},
	download:function(a) {
		var $tr=$(a).closest('tr');
		var adjunctid=$tr.attr('id');
		window.location.href=contextpath+'/contract/process/adjunct/download/'+adjunctid;
	},
	openAdjunct:function(a) {
		var $tr=$(a).closest('tr');
		var adjunctid=$tr.attr('id');
		top.addTab('',$(a).text(),contextpath+'/contract/process/adjunct/open/'+adjunctid);
	},
	removeAdjunct:function(delbtn) {
		
		var index=layer.confirm('确定要删除吗？', {
			title :'合同管理系统',
		    btn: ['确定','取消']
		}, function() {
			var $tr=$(delbtn).closest('tr');
			var adjunctid=$tr.attr('id');
			$.postJson('../adjunct/delete/'+adjunctid,{},function(msg) {
				if(msg.code==200) {
					$tr.hide('slow',function() {
						$tr.remove();
					});
				} else {
					console.log(JSON.stringify(msg));
					showInfo('删除失败!');
				}
			});
			layer.close(index);
		});
	},
	
	//关闭当前页面
	close:function() {
		$('#tab-basic-header a').click();
		var index=layer.confirm('确定要关闭本页面吗？', {
			title :'合同管理系统',
		    btn: ['确定','取消']
		}, function() {
			top.closeTab('active');
		});
	},
	
	updatesend:function(contractid) {
		
		$('#tab-basic-header a').click();
		$('#data-form').data({
			action:'发送',
			url:contextpath+'/contract/process/updatesend/'+contractid
		}).submit();
	},
	
	update:function(contractid) {
		$('#tab-basic-header a').click();
		$('#data-form').data({
			action:'修改',
			url:contextpath+'/contract/process/update/'+contractid
		}).submit();
	},
	
	dosave:function() {
		
		var action=$('#data-form').data('action');
		var url=$('#data-form').data('url');
		if(action=='保存') {
			layer.confirm('确定要'+action+'吗？', {
				title :'合同管理系统',
				btn: ['确定','取消'] //按钮
			},function(){
				var index=layer.msg('正在努力'+action+'合同,请稍等...', {icon: 16,time:0,shade:0.3});
				contract.process.saveContract();
			});
		} else if(action=='发送') {
			
			if($('#data-form')[0].organgrade.value<150) {
				
				layer.confirm('确定要'+action+'吗？', {
					title :'合同管理系统',
					btn: ['确定','取消'] //按钮
				},function(){
					$('#data-form').data({url:url+'/fukezhang'});
					var index=layer.msg('正在努力'+action+'合同,请稍等...', {icon: 16,time:0,shade:0.3});
					contract.process.saveContract();
				});
			} else {
				
				layer.confirm('是否送副科长审批？', {
					title:'信息提示',
					btn: ['送副科长','送科长','取消'] //按钮
				}, function(){
					$('#data-form').data({url:url+'/fukezhang'});
					var index=layer.msg('正在努力'+action+'合同,请稍等...', {icon: 16,time:0,shade:0.3});
					contract.process.saveContract();
				}, function(){
					$('#data-form').data({url:url+'/kezhang'});
					var index=layer.msg('正在努力'+action+'合同,请稍等...', {icon: 16,time:0,shade:0.3});
					contract.process.saveContract();
				}, function(){});
			}
		}
	},
	
	saveContract:function() {
		
		var $form = $('#data-form');
		var form=$form[0];
		
		//如果有加载weboffice控件,那么先保存合同正文
		var contractid=form.id.value;
		if($('#weboffice').length==1) {
			$('#weboffice')[0].SaveToURL(
					contextpath+'/contract/process/adjunct/uptext',
					'file',
					'contractid='+contractid
			);
		}
		
		//保存合同表单数据
		var companyxs=[];
		for(var i=1;form['party'+i+'id'] && form['party'+i+'id'].value;i++) {
			companyxs.push(form['party'+i+'id'].value);
		}
		
		var action=$('#data-form').data('action');
        $form.ajaxSubmit({
            url: $form.data('url'),
            type:'post',
            data:{'companyxs':companyxs.join(',')},
            dataType: 'json',
            success: function(result, statusText, xhr, $form) {
            	
            	layer.closeAll();
                if(result.code==200) {
            		if(action=='修改') {
            			top.showInfo('合同修改成功!');
            			window.location.reload();
            		} else {
            			window.location.href=contextpath+'/contract/process/success?type=send';
            		}
                } else {
                	layer.alert('合同'+action+'失败:'+result.msg,{
                		title:'合同管理系统',
                		icon:2
                	});
                }
            },
            error:function(e){
            	layer.alert('合同'+action+'失败:'+result.msg,{
            		title:'合同管理系统',
            		icon:2
            	});
            }
        });
	},
	
	//选择合同主体
	selectParty:function(party) {
		var url = contextpath+'/contract/process/company';
		var company = window.showModalDialog(url,{},'dialogTop=70px; dialogWidth=800px; dialogHeight=550px; help:no; scroll:no');
		if(company==null) return;
		
		$('#data-form')[0][party+'id'].value+='|'+company.id;
		$('#data-form')[0][party].value=company.name+'，法定代表人：'+company.faren;
	},

	//查看合同主体信息
	viewcompanyx:function(companyxid) {
		top.layer.open({
		    type: 2,
		    title: '查看合同主体',
		    shadeClose: true,
		    shade: 0,
		    area: ['600px', '500px'],
		    content: contextpath+'/contract/process/companyx/'+companyxid
		});
	},
	
	remove:function(contractid) {
		layer.confirm('确定要删除吗？', {
			title :'合同管理系统',
		    btn: ['确定','取消'] //按钮
		},function(){
	        var index=layer.msg('正在努力删除合同,请稍等...', {icon: 16,time:0,shade:0.3});
	        $.post(contextpath+'/contract/process/delete/'+contractid,function(msg) {
	        	layer.close(index);
	        	if(msg.code==200) {
	        		window.location.href=contextpath+'/contract/process/success?type=delete';
	        	} else {
	        		top.showInfo('删除失败!');
	        	}
	        });
		});
	},
	
	cancel:function(contractid) {
		layer.confirm('确定要取消吗？', {
			title :'合同管理系统',
		    btn: ['确定','取消'] //按钮
		},function(){
	        var index=layer.msg('正在努力取消合同,请稍等...', {icon: 16,time:0,shade:0.3});
	        $.post(contextpath+'/contract/process/cancel/'+contractid,function(msg) {
	        	layer.close(index);
	        	if(msg.code==200) {
	        		window.location.href=contextpath+'/contract/process/success?type=cancel';
	        	} else {
	        		top.showInfo('取消失败!');
	        	}
	        });
		});
	},
	
	//审批合同
	approval:function(contractid,activity) {
		
		top.layer.returnvalue=null;
		top.layer.open({
		    type: 2,
		    title: '合同审批',
		    shadeClose: true,
		    shade: 0,
		    area: ['700px', '600px'],
		    content: contextpath+'/contract/process/approval/'+contractid+'/'+activity,
		    end:function() {
		    	
		    	if(top.layer.returnvalue && top.layer.returnvalue.code==200) {
		    		window.location.href=contextpath+'/contract/process/success?type=approval';
		    	}
		    }
		});
	},
	
	//由法规科办理人直接审批合同
	approval2:function(contractid,activity) {
		
		top.layer.returnvalue=null;
		top.layer.open({
		    type: 2,
		    title: '合同审批',
		    shadeClose: true,
		    shade: 0,
		    area: ['700px', '600px'],
		    content: contextpath+'/contract/process/approval2/'+contractid+'/'+activity,
		    end:function() {
		    	
		    	if(top.layer.returnvalue && top.layer.returnvalue.code==200) {
		    		window.location.href=contextpath+'/contract/process/success?type=approval';
		    	}
		    }
		});
	},
	
	//由法规科办理人发送给法规科长
	sendtofaguike:function(contractid,activity) {
		
		top.layer.returnvalue=null;
		top.layer.open({
		    type: 2,
		    title: '合同审批',
		    shadeClose: true,
		    shade: 0,
		    area: ['700px', '600px'],
		    content: contextpath+'/contract/process/sendtofaguike/'+contractid+'/'+activity,
		    end:function() {
		    	
		    	if(top.layer.returnvalue && top.layer.returnvalue.code==200) {
		    		window.location.href=contextpath+'/contract/process/success?type=sendtofaguike';
		    	}
		    }
		});
	},
	
	//完成合同
	complete:function(contractid,activity) {
		$('#tab-basic-header a').click();
		layer.confirm('确定要完成合同审批流程吗？', {
			title :'合同管理系统',
		    btn: ['确定','取消'] //按钮
		},function(){
	        var index=layer.msg('正在处理,请稍等...', {icon: 16,time:0,shade:0.3});
	        $.post(contextpath+'/contract/process/sendto/'+contractid+'/'+activity,function(msg) {
	        	layer.close(index);
	        	if(msg.code==200) {
	        		window.location.href=contextpath+'/contract/process/success?type=complete';
	        	} else {
	        		top.showInfo('操作失败!');
	        	}
	        });
		});
	},
	
	//打印内部审批表
	printApprovalTable:function(contractid) {
		top.addTab('','合同审查内部审批表',contextpath+'/contract/process/print/approval/'+contractid);
	},
	
	//由起草人收回合同
	shouhui:function(contractid) {
		$('#tab-basic-header a').click();
		layer.confirm('确定要收回合同审批流程吗？', {
			title :'合同管理系统',
		    btn: ['确定','取消'] //按钮
		},function(){
	        var index=layer.msg('正在处理,请稍等...', {icon: 16,time:0,shade:0.3});
	        $.post(contextpath+'/contract/process/shouhui/'+contractid,function(msg) {
	        	layer.close(index);
	        	if(msg.code==200) {
	        		window.location.href=contextpath+'/contract/process/success?type=shouhui';
	        	} else {
	        		top.showInfo('操作失败!');
	        	}
	        });
		});
	},
	
	//法规科委派办理人
	delegation:function(contractid,activity) {
		$('#tab-basic-header a').click();
		
		var organs=system.organ.selectOrgans({
			checkbox:0,
			role:false,	
			enterprice:false,
			dept:false,	
			team:false,
			emp:true
		});
		if(!organs || organs.length==0) {
			return;
		}
		if(organs[0].type!='emp') {
			top.showInfo('请选择委派办理人!');
			return;
		}
		
		var index=layer.msg('正在处理,请稍等...', {icon: 16,time:0,shade:0.3});
        $.post(contextpath+'/contract/process/delegation/'+contractid+'/'+activity,{organid:organs[0].id},function(msg) {
        	layer.close(index);
        	if(msg.code==200) {
        		window.location.href=contextpath+'/contract/process/success?type=delegation';
        	} else {
        		top.showInfo('操作失败!');
        	}
        });
	}
});