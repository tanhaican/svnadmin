/**
 * 创建项目
 * @author Hpboys
 * @version 1.0.0
 */
define(function(require, exports, module){
	//引入模块
	require('$');
	var util = require('util');
	
	function isChinese(str) {
        if(/[\u3220-\uFA29]+/.test(str)){
            return true;
        }else{
            return false;
        }
    }
	
	function validateSvnName(str) {
        if(/^[\w|\d]+$/.test(str)){
            return true;
        }else{
            return false;
        }
    }
	
	function inputName(val) {
		if(!validateSvnName(val)) {
			util.showMsg('请输入英文、数字或下划线', 4);
			$('#projectSvnName').addClass('error');
			return;
		} else {
			$('#projectSvnName').removeClass('error');
		}
		
		var url = 'svn://' + $('#serverName').val() + '/' + val;
		$('#projectSvnUrl').val(url);
		$('#projectSvnUrlShow').val(url);
		var svnRepoPath = $('#svnRepoPath').val();
		$('#projectSvnPath').val(svnRepoPath + val);
	}

	function submitForm(){
		if($('#submitForm input.error').length > 0) {
			util.showMsg('请先修正红色提示框内的内容再提交', 4);
			return false;
		}
		var $pjName = $('#projectName'),
			pjName = $pjName.val();
		pjName = pjName && pjName.trim();
		$pjName.val(pjName);
		var form = util.serializeObject($('#submitForm'));
		var load_index = util.loading();
		$.post('pjCreateHandler', form, function(data){
			util.showMsg(data.info);
			if(data.status){
				util.closeLayer(load_index);
				setTimeout(function(){
					location.href = data.attr.url;
				},1500);
			}
		});
		return false;
	}
	
	window.submitForm = submitForm;
	window.inputName = inputName;
	
});