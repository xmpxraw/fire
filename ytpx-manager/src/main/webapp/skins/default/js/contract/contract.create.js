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
				if(form.id.value=='' && $('#weboffice').size()==0) {
					showInfo('请填写合同正文');
					return;
				}
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
					console.log('fileuploaded:'+JSON.stringify(res));
					try {
						var msg = JSON.parse(res.response);
						if (msg.code != 200) {
							console.log('上传文件出现错误');
							showInfo('上传文件出现错误!');
							$('#' + file.id).remove('slow');
						} else {
							$td = $('#' + file.id).attr('id', msg.data.id)
									.find('td');
							$td.eq(3).text(msg.data.organname);
							$td.eq(4).text(msg.data.createtime);
							$td.eq(5).find('.btn-del').show('slow');
						}
					} catch (e) {
						console.log('上传文件出现错误');
						showInfo('上传文件出现错误!');
						$('#' + file.id).remove('slow');
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
	
	savesend:function() {
		$('#tab-basic-header a').click();
		$('#data-form').data({
			action:'发送',
			url:contextpath+'/contract/process/savesend/fukezhang'
		}).submit();
	},
	save:function() {
		$('#tab-basic-header a').click();
		$('#data-form').data({
			action:'保存',
			url:contextpath+'/contract/process/save'
		}).submit();
	},
	
	dosave:function() {
		
		var action=$('#data-form').data('action');
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
					var index=layer.msg('正在努力'+action+'合同,请稍等...', {icon: 16,time:0,shade:0.3});
					contract.process.saveContract();
				});
				
			} else {
				
				layer.confirm('是否送副科长审批？', {
					title:'信息提示',
					btn: ['送副科长','送科长','取消'] //按钮
				}, function(){
					$('#data-form').data({url:contextpath+'/contract/process/savesend/fukezhang'});
					var index=layer.msg('正在努力'+action+'合同,请稍等...', {icon: 16,time:0,shade:0.3});
					contract.process.saveContract();
				}, function(){
					$('#data-form').data({url:contextpath+'/contract/process/savesend/kezhang'});
					var index=layer.msg('正在努力'+action+'合同,请稍等...', {icon: 16,time:0,shade:0.3});
					contract.process.saveContract();
				}, function(){});
			}
		}
	},
	
	saveContract:function() {
		
		var adjuncts=$('#adjunct-table tbody tr').map(function() {
			return $(this).attr('id');
		}).get().join(',');
		
		var $form = $('#data-form');
		var form=$form[0];
		
		var companys=[];
		for(var i=1;form['party'+i+'id'] && form['party'+i+'id'].value;i++) {
			companys.push(form['party'+i+'id'].value);
		}
		
		var action=$('#data-form').data('action');
        $form.ajaxSubmit({
            url: $form.data('url'),
            type:'post',
            data:{'adjuncts':adjuncts,'companys':companys.join(',')},
            dataType: 'json',
            success: function(result, statusText, xhr, $form) {
                if(result.code==200) {
                	$form[0].id.value=result.data.id;
                	contract.process.uploadWebOffice(result.data.id);
                } else {
                	layer.alert('合同'+action+'失败:'+result.msg,{
                		title:'合同管理系统',
                		icon:2
                	});
                }
            },
            error:function(e){
            	layer.closeAll();
            	showInfo('合同'+action+'失败!');
            }
        });
	},
	
	uploadWebOffice:function(contractid) {
		
		$('#weboffice')[0].SaveToURL(
				contextpath+'/contract/process/adjunct/uptext',
				'file',
				'contractid='+contractid
		);
		layer.closeAll();
		
		var action=$('#data-form').data('action');
		if(action=='保存') {
			top.showInfo('合同保存成功!');
			window.location.href=contextpath+'/contract/process/update/'+contractid;
		} else {
			window.location.href=contextpath+'/contract/process/success?type=send';
		}
	},
	
	selectParty:function(party) {
		var url = contextpath+'/contract/process/company';
		var company = window.showModalDialog(url,{},'dialogTop=70px; dialogWidth=800px; dialogHeight=550px; help:no; scroll:no');
		if(company==null) return;
		
		$('#data-form')[0][party+'id'].value=company.id;
		$('#data-form')[0][party].value=company.name+'，法定代表人：'+company.faren;
	},
	
	//增加其他方
	showOtherParty:function() {
		
		$('#tab-basic-header a').click();
		var $party=$('#basic-table tr.other-party:hidden').first();
		if($party.size()==1) {
			$party.show();
		} else {
			top.showInfo('不能再增加其它方!');
		}
	},
	
	//删除其他方
	hideOtherParty:function() {
		
		$('#tab-basic-header a').click();
		var $party=$('#basic-table tr.other-party:visible').last();
		if($party.size()==1) {
			$party.find('input').val('');
			$party.hide();
		} else {
			top.showInfo('不能再删除其它方!');
		}
	},
	
	//集成weboffice
	opendoc:function() {
		$('#weboffice')[0].ShowDialog(1);
	},
	savedoc:function() {
		$('#weboffice')[0].ShowDialog(3);
	},
	printdoc:function() {
		$('#weboffice')[0].PrintOut(true);
	},
	previewdoc:function() {
		$('#weboffice')[0].PrintPreview();
	}
});