<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
	<layout:override name="head">
		<title>${x:getValueFromCache("web_name")} - 专业的社保门户,提供最全面社保新政策</title>
		<meta name="baidu-site-verification" content="zIq9dV7nWW" />
		<meta name="sogou_site_verification" content="56x8y5g1KB"/>
		<meta name="shenma-site-verification" content="d59c657629467cbaf06757da36e0f1ac_1464260367">
		<meta name="keywords" content="社保政策,社保查询,社保新闻，社保新政策,社保计算器,公积金,公积金贷款,社保余额,12333,公积金查询,社保查询个人账户"/>
		<meta name="description" content="社保云是一个专门针对社保信息的门户网站，为广大网民提供最新的社保政策、社保新闻、社保常见问题、社保案例、社保资讯、汇集全国各地的社保查询、办事指南等。"/> 
		<link rel="stylesheet" type="text/css" href="static/lib/swiper/css/swiper.min.css"/>
		<style type="text/css">
			.swiper-container{min-height: 345px;}
			.swiper-slide{position:relative;min-height: 345px;background-position: center center;background-repeat: no-repeat;background-size: cover;}
			.swiper-slide a{display: block;position: absolute;top:0px;bottom: 0px;left:0px;right: 0px;}
			.swiper-slide a em{padding:0px 10px;display: block;position: absolute;bottom: 0px;left: 0px;right: 0px;height: 40px;background-color: rgba(0,0,0,.5);line-height: 40px;color: #fff;font-size: 18px;}
			.swiper-pagination{text-align: right;}
			.swiper-pagination .swiper-pagination-bullet{background-color:#fff;opacity: 0.8;}
			.swiper-pagination .swiper-pagination-bullet-active{background-color: blue;}
		</style>
	</layout:override>
	<layout:override name="content">
		<div class="index-bd2" style="padding: 10px;">			
			<div class="temp-01-L1">
				<div class="temp-01-L1-L">
				    <div class="swiper-container">
				        <div class="swiper-wrapper">
				        	<c:forEach items="${data.list2 }" var="item">
					            <div class="swiper-slide" title="${item.title }" style="background-image: url(${x:getUrl(item.image_url)});">
						           	 <a href="article/${item.id }.html" title="${item.title }" target="_blank">
						          	 	<em>${item.title }</em>
						          	 </a>
								</div>
				        	</c:forEach>
				        </div>
				        <div class="swiper-pagination"></div>
				    </div>
				</div>
				<div class="temp-01-L1-R">
					<div class="temp-01-list-01" style="overflow: hidden;height: 335px;">
						<h1 style="text-align: center;color: #347DD4;font-size: 18px;border-bottom:none;"><a target="_blank" href="topic/${data.topic.id }.html">${data.topic.title }</a><em><a target="_blank" href="topic.html">查看更多<i class="iconfont icon-jiantouyou"></i></a></em></h1>
						<p style="font-size: 14px;color: #1a638c;margin-top: 3px;margin-bottom: -8px;line-height: 23px;">&nbsp;&nbsp;&nbsp;&nbsp;${x:filterSubText(data.topic.description,150)}<a target="_blank" href="topic/${data.topic.id }.html" style="color: #E95E02">&nbsp;[详细]</a></p>
						<ul id="newskey">
						    <c:forEach items="${data.list1 }" var="item">
								<li><a title="${item.title }" <c:if test="${!empty item.color && item.color!=''}">style="color:${item.color }"</c:if> target="_blank" href="article/${item.id }.html">${item.title }</a> </li>
							</c:forEach> 
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="index-bd2" style="background-color: transparent;padding: 0px;margin-bottom: -20px;">	
			<!--页面列表-->
			<c:forEach items="${data.list3 }" var="item" varStatus="varStatus">
				<div class="temp-01-list-02">
					<h1><em>${item.name }</em><a href="${item.path }" target="_blank">查看更多<i class="iconfont icon-jiantouyou"></i></a></h1>
					<c:if test="${item.firstObj!=null }">
						<h2>
							<dl>
								<dt><img alt="${item.firstObj.title }" class="lazy" data-original="${x:getUrl(item.firstObj.image_url) }"/></dt>
								<dd><a href="article/${item.firstObj.id }.html" target="_blank" title="${item.firstObj.title }">${item.firstObj.title }</a></dd>
							</dl>
						</h2>
					</c:if>
					<h3>
						<ul>
							<c:forEach items="${item.list }" var="item2">
								<li><a href="article/${item2.id }.html" title="${item2.title }" target="_blank">${item2.title }</a> <span>${x:format(item2.publish_time,'MM-dd')}</span></li>
							</c:forEach>
						</ul>
					</h3>				
				</div>	
			</c:forEach>
		</div>
	</layout:override>
 <layout:override name="script">
		<script src="static/lib/swiper/js/swiper.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="static/js/jquery.lazyload.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
		    var swiper = new Swiper('.swiper-container', {
		        pagination: '.swiper-pagination',
		        paginationClickable: true,
		        autoplay : 2000,
		        speed:300
		    });
			$(function(){
				$("img.lazy").lazyload({effect : "fadeIn"});
			});
	    </script>
</layout:override>
<%@ include file="/commons/layout_web.jsp" %>
