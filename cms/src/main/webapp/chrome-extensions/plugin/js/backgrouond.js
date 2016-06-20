function handleRequest(titleSelector,contentSelector,baseUrl){
	chrome.tabs.getSelected(null, function(tab) {
	    chrome.tabs.sendRequest(tab.id, {greeting: "spider","titleSeletor":titleSelector,"contentSelector":contentSelector,"baseUrl":baseUrl}, function(response) {
	    	console.log(response.farewell);
	    });
	});
}