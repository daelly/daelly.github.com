    //获取图片标签
	$.views.tags("getImg",function(content,image_url){
		if(image_url!=null&&image_url!=''){
			   return getUrl(image_url);
		}
		var obj= document.createElement("body");
		obj.innerHTML=content;
		if(obj.getElementsByTagName("img").length>0){
			return  getUrl(obj.getElementsByTagName("img")[0].src);
		}else{
			return "static/web/img/nodata.png";
		}
	});
	
	$.views.tags("getContent",function(content){
		 var content2=delHtmlTag(content);
		 return displayPart(content2,200);
	});

	$(".next-page").click(function(){
	   $(this).attr("data-pageNo",parseInt($(this).attr("data-pageNo"))+1);
	   var pageNo=$(this).attr("data-pageNo");
	   var dataUrl=$(this).attr("data-url");
	   jQuery.ajax({
			url : dataUrl+pageNo ,
			type : "post",
			dataType:"json",
			data : {},
			success : function(result) {
				var html = $("#listTemplate").render(result.list);
				$("#content").append(html);
//				rendListByTemplate($("#listTemplate"), $("#content"), result.list);
				if(result.lastPage==true){
					$(".next-page").hide();
				}
			},error :function(result){
		}
		});
   }); 
   function getUrl(url){
	   if(url.indexOf("http")>=0){
		   return url;
	   }else{
		  return basePath+url; 
	   }
   }
   function getImg(content,image_url){
	   if(image_url!=null&&image_url!=''){
		   return getUrl(image_url);
	   }
	   var obj= document.createElement("body");
	   obj.innerHTML=content;
	   if(obj.getElementsByTagName("img").length>0){
		  return  getUrl(obj.getElementsByTagName("img")[0].src);
	   }else{
		   return "static/web/img/nodata.png";
	   }
   }
   function showDt(content){
	   var obj= document.createElement("body");
	   obj.innerHTML=content;
	   if(obj.getElementsByTagName("img").length<=0){
		   return "style='display:none'";
	   }
   }
   function showDl(content){
	   var obj= document.createElement("body");
	   obj.innerHTML=content;
	   if(obj.getElementsByTagName("img").length<=0){
		   return "class='noimg'";
	   }
   }
   function getContent(content){
	   var content2=delHtmlTag(content);
	   return displayPart(content2,200);
   }
   function delHtmlTag(str){  
        return str.replace(/<[^>]+>/g,"");//去掉所有的html标记  
    } 