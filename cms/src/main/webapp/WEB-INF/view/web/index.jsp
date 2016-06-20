<%@page import="com.jfinal.kit.PropKit"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<layout:override name="head">
	<title>${x:getValueFromCache("web_name")} - 专业的社保门户,提供最全面社保新政策</title>
	<meta name="keywords" content="社保政策,社保查询,社保新闻，社保新政策,社保计算器,社保查询个人账户"/>
	<meta name="description" content="社保云是一个专门针对社保信息的门户网站，为广大网民提供最新的社保政策、社保资讯、汇集全国各地的社保查询、办事指南等。"/> 
		<link rel="stylesheet" type="text/css" href="static/lib/swiper/css/swiper.min.css"/>
		<style type="text/css">
			.swiper-container{min-height: 370px;}
			.swiper-slide{position:relative;min-height: 370px;background-position: center center;background-repeat: no-repeat;background-size: cover;}
			.swiper-slide a{display: block;position: absolute;top:0px;bottom: 0px;left:0px;right: 0px;}
			.swiper-slide a em{padding:0px 10px;display: block;position: absolute;bottom: 0px;left: 0px;right: 0px;height: 40px;background-color: rgba(0,0,0,.5);line-height: 40px;color: #fff;font-size: 18px;}
			.swiper-pagination{text-align: right;}
			.swiper-pagination .swiper-pagination-bullet{background-color:#fff;opacity: 0.8;}
			.swiper-pagination .swiper-pagination-bullet-active{background-color: blue;}
	</style>
</layout:override>
<layout:override name="content">
		<div class="index-bd">			
			<div class="index-R">
				<div class="index-R-list-1">
					<h1 class="index-list-t"><s><i class="iconfont icon-shanhuo"></i><span>最新推荐</span></s><em></em></h1>
					<ul>
						<c:forEach items="${list_tuijian }" var="item">
							<li><a title="${item.title }" target="_blank" href="article/${item.id}.html">${item.title}</a></li>
						</c:forEach>
					</ul>					
				</div>
				<div class="index-R-list-1">
					<h1 class="index-list-t"><s><i class="iconfont icon-shanhuo"></i><span>热文榜单</span></s><em></em></h1>
					<ul>
						<c:forEach items="${list_hot }" var="item">
							<li><a title="${item.title }" target="_blank" href="article/${item.id}.html">${item.title}</a></li>
						</c:forEach>
					</ul>					
				</div>
				<div class="index-R-list-1" style="margin-top: 10px;">
					<h1 class="index-list-t"><s><i class="iconfont icon-zuixingonggao "></i><span>最新快报</span></s><em></em></h1>
					<ul>
						<c:forEach items="${list_new }" var="item">
							<li><a title="${item.title }" target="_blank" href="article/${item.id }.html">${item.title }</a></li>
						</c:forEach>
					</ul>					
				</div>
				<div class="index-R-list-2" style="margin-top: 10px;">
					<h1 class="index-list-t"><s><i class="iconfont icon-zuixingonggao "></i><span>热门话题</span></s><em></em></h1>
					<ul>
						<c:forEach items="${allTags }" var="item">
							<li><a title="${item.tag_name }" target="_blank" href="tags/${item.id }.html">${ item.tag_name}</a></li>
						</c:forEach>
					</ul>					
				</div>
				
			</div>
			<div class="index-L">
				<c:if test="${fn:length(bannerData)>0}">
					<div class="banner">
					    <div class="swiper-container">
					        <div class="swiper-wrapper">
					        	 <c:forEach items="${bannerData }" var="item">
						            <div class="swiper-slide" style="background-image: url(${x:getUrl(item.image_url) });">
							           	<a title="${item.title }" target="_blank" href="article/${item.id }.html">
							          		 <em>${item.title }</em>
							          	 </a>
									</div>
					        	</c:forEach> 
					        </div>
					         <div class="swiper-button-prev"></div>
	   						 <div class="swiper-button-next"></div>
					         <div class="swiper-pagination"></div>
					    </div>
					</div>
				</c:if>
				<div class="index-list-3" style="margin-top: 10px;">
					<ul id="content">
						<c:forEach items="${page.list }" var="item">
							<c:set var="articleImg" value="${x:getArticleImg2(item.image_url,item.content)}" scope="page"></c:set>
							<li>
								<dl <c:if test="${articleImg=='' }">class="noimg"</c:if>>
									<c:if test="${articleImg!='' }">
										<dt><a target="_blank" href="article/${item.id }.html"><img onerror="this.src='static/images/no_img.png'" alt="${item.title }" title="${item.title }" class="lazy" data-original="${articleImg}" /></a></dt>
									</c:if>
									<dd>
										<h1><a href="${item.path }"><em>${item.folderName }</em></a><a title="${item.title }" target="_blank" href="article/${item.id }.html">${item.title }</a></h1>
										<h2>${x:getContent(item.summary,item.content,120) }  </h2>
										<h3>${item.publish_user } / ${item.folderName } / ${x:format(item.publish_time,'yyyy-MM-dd HH:mm')}</h3>
									</dd>
								</dl>
							</li>
						</c:forEach>
					</ul>
					<c:if test="${page.lastPage==false }">
						<div class="index-list-page">
							<a class="next-page"  data-url="indexPage/" data-pageNo="1">更多文章</a>
						</div>
					</c:if>	
				</div>
			</div>
		</div>
		<script type="text/template" id="listTemplate">
			<li>
				<dl @【showDl(item.content)】>
					<dt @【showDt(item.content)】><a target="_blank" href="article/【id】.html"><img title="【title】"  src="@【getImg(item.content,item.image_url)】"></a></dt>
					<dd>
							<h1><a href="【path】"><em>【folderName】</em></a><a title="【title】" target="_blank" href="article/【id】.html">【title】</a></h1>
							<h2>@【getContent(item.content)】</h2>
							<h3>【publish_user】 / 【folderName】 / 【publish_time】</h3>
					</dd>
				</dl>
			</li>			
	</script>
</layout:override>
<layout:override name="script">
	<script src="static/js/jquery.lazyload.js" type="text/javascript" charset="utf-8"></script>
	<script src="static/lib/swiper/js/swiper.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="static/js/pubfunction.js" type="text/javascript" charset="utf-8"></script>
	<script src="static/web/js/index.js" type="text/javascript" charset="utf-8"></script>
 	<script type="text/javascript">
 		var basePath='<%=PropKit.get("basePath")%>'; 
	</script>
</layout:override>
<%@ include file="/commons/layout_web.jsp" %>