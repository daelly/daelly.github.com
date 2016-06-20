<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<layout:override name="head">
	 <title>${topic.title} - ${x:getValueFromCache("web_name")}</title>
	 <meta name="keywords" content="${topic.keywords}">
	 <meta name="description" content="${topic.description}">
	  <style type="text/css">
			.swiper-container{min-height: 400px;}
			.swiper-slide{position:relative;min-height: 400px;background-position: center center;background-repeat: no-repeat;background-size: cover;}
			.swiper-slide a{display: block;position: absolute;top:0px;bottom: 0px;left:0px;right: 0px;}
			.swiper-slide a em{padding:0px 10px;display: block;position: absolute;bottom: 0px;left: 0px;right: 0px;height: 40px;background-color: rgba(0,0,0,.5);line-height: 40px;color: #fff;font-size: 18px;}
			.swiper-pagination{text-align: right;}
			.swiper-pagination .swiper-pagination-bullet{background-color:#fff;opacity: 0.8;}
			.swiper-pagination .swiper-pagination-bullet-active{background-color: blue;}
			body{background-color: #fff!important;}
	</style> 
	<link rel="stylesheet" type="text/css" href="static/lib/swiper/css/swiper.min.css"/>
	<script src="static/lib/swiper/js/swiper.min.js" type="text/javascript" charset="utf-8"></script>
</layout:override>
<layout:override name="content">
		  <div class="temp-index3-01">
				<div class="temp-index3-01-L1">
					<h1>
						<em>${topic.title}</em>
						<!-- <span>地点：香港   <s> 时间：2015年12月3日～5日</s></span> -->
					</h1>
					<h2>
						<em>专题摘要:</em> ${topic.description }
					</h2>
				</div>
			</div>
			<div class="temp-index3-02">
				<div class="temp-index3-02-R">
					<div class="temp-index3-h1">
						<em><span></span>热点推荐</em>
					</div>
					<div class="temp-index3-Rlist">
						<ul>
							<c:forEach items="${topicList }" var="item">
								<li>
									<img alt="${item.title }" title="${item.title }" class="lazy" data-original="${x:getUrl(item.titleImg) }"/>
									<a title="${item.title }" href="topic/${item.id }.html" target="_blank">${item.title }</a>								
								</li>
							</c:forEach>
						</ul>
					</div>
				</div>
				<div class="temp-index3-02-L">
				<c:if test="${fn:length(bannerData)>0}">
					<div class="temp-index3-h1">
						<em><span></span>图片</em>
					</div>
					<div class="temp-index3-bn">
						    <div class="swiper-container">
						        <div class="swiper-wrapper">
							        <c:forEach items="${bannerData}" var="item">
							            <div class="swiper-slide" style="background-image: url(${x:getUrl(item.image_url)});">
								           	<a title="${item.title }" href="article/${item.id }.html" target="_blank">
								          		 <em>${item.title }</em>
								          	 </a>
										</div>
							        </c:forEach>
						        </div>
						        <div class="swiper-pagination"></div>
						    </div>
					</div>
				</c:if>
					<c:if test="${fn:length(page.list)>0}">
						<div style="height: 40px;">
							<div class="temp-index3-h1" style="margin-top: 20px;">
								<em><span></span>要闻</em>
								<a>更多>></a>
							</div>
						</div>
						<div class="temp-index3-list">
							<ul>
								<c:forEach items="${page.list }" var="item">
									<li>
										<h1><a title="${item.title }" href="article/${item.id }.html" target="_blank">${item.title }</a></h1>
										<h2>
											${x:getContent(item.summary,item.content,140) }
										</h2>
										<h3>
											<span>${item.origin } </span>
											<span>|</span>
											<span>${x:format(item.publish_time,'yyyy-MM-dd') }</span>									
										</h3>								
									</li>
								</c:forEach>
							</ul>
						</div>
					</c:if>
					<div class="index-list-page" style="margin: 20px -20px 0px -20px;">
						<c:if test="${fn:length(page.list)>0}">
								<x:pagination pageNumber="${page.pageNumber}" singlePageShow="false" totalPage="${page.totalPage}" action="topic/${topic.id}" />
						</c:if>
					</div>
					<div class="temp-pinglun" style="min-height: 200px;">
						<!-- 多说评论框 start -->
						<div class="ds-thread" data-thread-key="topic_${topic.id }" data-title="${topic.title}" data-url="<%= request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"%>topic/${topic.id }.html"></div>
						<!-- 多说评论框 end -->
						<!-- 多说公共JS代码 start (一个网页只需插入一次) -->
						<script type="text/javascript">
						var duoshuoQuery = {short_name:"${x:getValueFromCache('duoshuo.short_name')}"};
							(function() {
								var ds = document.createElement('script');
								ds.type = 'text/javascript';ds.async = true;
								ds.src = (document.location.protocol == 'https:' ? 'https:' : 'http:') + '//static.duoshuo.com/embed.js';
								ds.charset = 'UTF-8';
								(document.getElementsByTagName('head')[0] 
								 || document.getElementsByTagName('body')[0]).appendChild(ds);
							})();
					   </script>
					<!-- 多说公共JS代码 end -->
					</div>
				</div>
			</div>
			
</layout:override>
<layout:override name="script">
	<script src="static/js/jquery.lazyload.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
	     var swiper = new Swiper('.swiper-container', {
	        pagination: '.swiper-pagination',
	        paginationClickable: true,
	        prevButton:'.swiper-button-prev',
	        nextButton:'.swiper-button-next',
	        autoplay : 3000,
	        speed:300
	    }); 
		$(function(){
			$("img.lazy").lazyload({effect : "fadeIn"});
		});
	</script>
</layout:override>
<%@ include file="/commons/layout_web.jsp" %>