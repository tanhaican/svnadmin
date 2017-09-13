/**
 * 创建项目
 * @author Hpboys
 * @version 1.0.0
 */
define(function(require, exports, module){
	//引入模块
	require('$');
	var util = require('util');

	$('.del-proj').click(function() {
		var $btn = $(this);
		util.confirm({msg:'确定要删除这个项目吗？',
			confirm: function(){//点击确定的回调函数
				var load_index = util.loading(),
				    pj = $btn.attr('data-pj');
				pj = encodeURIComponent(pj);
				
				$.post('pjDelete', {"pj": pj}, function(data){
					util.showMsg(data.info);
					if(data.status){
						util.closeLayer(load_index);
						setTimeout(function(){
							location.href = data.attr.url;
						}, 1500);
					}
				});
			 }
		});
	});
	
});