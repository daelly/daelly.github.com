function spiderSubmit(){
	var titleSelector = $("#spiderForm input[name='titleSelector']").val();
	var contentSelector = $("#spiderForm input[name='contentSelector']").val();
	var baseUrl = $("#spiderForm input[name='baseUrl']").val();
	if(baseUrl!=""&&baseUrl.substring(baseUrl.length-1)!="/")
		baseUrl += "/";
	var bgPage = chrome.extension.getBackgroundPage();
	bgPage.handleRequest(titleSelector,contentSelector,baseUrl);
}
$(function(){
	$("#submitbtn").on("click",function(){
		spiderSubmit();
	});
	$("#spiderForm input").on("blur",function(){
		saveCurrentInput($(this).attr("name"),$(this).val());
	});
	var baseUrl = window.localStorage.getItem("baseUrl")||"http://localhost:8093/cms/";
	window.localStorage.setItem("baseUrl",baseUrl);
	readAllInputValue();
});

function saveCurrentInput(key,value){
	window.localStorage.setItem(key,value);
}

function readAllInputValue(){
	$("#spiderForm input[name='titleSelector']").val(window.localStorage.getItem("titleSelector")||"");
	$("#spiderForm input[name='contentSelector']").val(window.localStorage.getItem("contentSelector")||"");
	$("#spiderForm input[name='baseUrl']").val(window.localStorage.getItem("baseUrl")||"");
}