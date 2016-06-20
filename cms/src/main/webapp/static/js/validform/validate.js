/**
 * 自定义validate方法
 */
jQuery.fn.validate=function(url,callback){
	var valid = $("#fillContentForm").Validform({
		ajaxPost:true,
		tiptype:1,
		callback:callback
	});
	valid.ajaxPost(false,true,url);
};