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
	
	function inputName(val) {
		if(isChinese(val)) {
			util.showMsg('项目名称不能是中文名', 4);
			$('#projectName').addClass('error');
			return;
		} else {
			$('#projectName').removeClass('error');
		}
		
		var url = 'svn://192.168.105.100/' + val;
		$('#projectSvnUrl').val(url);
		$('#projectSvnUrlShow').val(url);
		$('#projectSvnPath').val('D:/svn/repository/' + val);
	}

	function submitForm(){
		if($('#submitForm input.error').length > 0) {
			util.showMsg('请先修正红色提示框内的内容再提交', 4);
			return false;
		}
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