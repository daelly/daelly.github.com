var baseUrl = "http://localhost:8093/cms/";
chrome.extension.onRequest.addListener(
	function(request, sender, sendResponse) {
		console.log(sender.tab ?
                "from a content script:" + sender.tab.url :
                "from the extension");
    if (request.greeting == "spider"){
//    	//处理采集
//    	var url = window.location.href;
//    	var data = {"url":url,"article.title":request.titleSeletor,"article.content":request.contentSelector};
////    	spider(request.titleSeletor,request.contentSelector);
//    	submitSpider(data);
//    	sendResponse({farewell: "goodbye"});
    	var title = "",content = "";
    	baseUrl = request.baseUrl;
    	if($(request.titleSeletor).length>0)
    		title = $(request.titleSeletor).first().text();
    	if($(request.contentSelector).length>0)
    		content = $(request.contentSelector).first().html();
    	if(title==""){
    		alert("采集到标题为空！");
    		return;
    	}
    	if(content==""){
    		alert("采集到内容为空！");
    		return;
    	}
    	saveSpiderData(title,content);
    }else
      sendResponse({}); // snub them.
});

function submitSpider(data){
	$.ajax({
		url : baseUrl+"ext/spider/singlePage",
		type : 'post',
		dataType : "json",
		data : data,
		success : function(rs) {
			if (rs.code == 0) {
				/*layer.confirm("文章采集成功，是否前往编辑或预览？",{
					btn:["编辑","预览","取消"]},
					function(i){
						window.open(baseUrl+"system/article/edit/"+actile.id,"_blank");
						layer.close(i);
					},function(i){
						window.open(baseUrl+"view/"+actile.id,"_blank");
						layer.close(i);
				});*/
				alert("文章采集成功!");
			} else {
				alert(result.message);
			}
		}, 
		error : function(rs) {
			alert('系统繁忙，请与管理员联系!');
		}
	});
}

function saveSpiderData(title,content){
	$.ajax({
		url : baseUrl+"system/article/add/",
		type : 'post',
		dataType : "json",
		data : {"article.title":title,"article.content":content,"article.origin_url":window.location.href},
		success : function(rs) {
			if (rs.code == 0) {
				alert("文章采集成功!");
				var actile = rs.data;
				window.open(baseUrl+"view/"+actile.id,"_blank");
			} else {
				alert(rs.message);
			}
		}, 
		error : function(rs) {
			alert('系统繁忙，请与管理员联系!');
		}
	});
}

