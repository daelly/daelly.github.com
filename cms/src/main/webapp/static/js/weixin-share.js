//����д�����߽ӿ����appid
var appid = 'gh_5f58ae0646df';
function shareFriend() {
    WeixinJSBridge.invoke('sendAppMessage',{
		"appid": appid,
		"img_url": imgUrl || cdn_path + '/api/qrcode-' +  qrcode + '.png',
		"img_width": "640",
		"img_height": "640",
		"link": location.href,
		"desc": descContent || document.title,
		"title": document.title
	}, function(res) {
		_report('send_msg', res.err_msg);
	})
}
function shareTimeline() {
    WeixinJSBridge.invoke('shareTimeline',{
		"img_url": imgUrl || cdn_path + '/api/qrcode-' +  qrcode + '.png',
		"img_width": "640",
		"img_height": "640",
		"link": location.href,
		"desc": descContent || document.title,
		"title": document.title
	}, function(res) {
		_report('timeline', res.err_msg);
	});
}
// ��΢���������������ڲ���ʼ����ᴥ��WeixinJSBridgeReady�¼���
document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	// ���͸�����
	WeixinJSBridge.on('menu:share:appmessage', function(argv){
		shareFriend();
	});
	// ��������Ȧ
	WeixinJSBridge.on('menu:share:timeline', function(argv){
		shareTimeline();
	});
}, false);