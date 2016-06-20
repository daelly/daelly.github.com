//返回顶部
$(window).scroll(function (){
		var scroll_height = $(document).scrollTop();		
		if( scroll_height > 100){
			$(".kf-001").css("bottom","50px");
			$("#gotop").show();			
		}else{
			$(".kf-001").css("bottom","-10px");
			$("#gotop").hide();		
		}
	});
function goTop(){
	$('body,html').animate({scrollTop:0},300); 
}

