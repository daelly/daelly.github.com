<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
	<layout:override name="head">
		<title>${folder.title} - ${x:getValueFromCache("web_name")}</title>
		<meta name="Keywords" content="${folder.keywords }"/>
	 	<meta name="Description" content="${folder.description }"/> 
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
				        	<c:forEach items="${banner }" var="item">
					            <div class="swiper-slide" style="background-image: url(${x:getUrl(item.image_url)});">
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
					<div class="temp-01-list-01" style="height: 335px;overflow: hidden;">
						 <h1 style="text-align: center;color: #347DD4;font-size: 18px;border-bottom:none;"><a target="_blank" href="article/${article.id }.html">${article.title }</a></h1> 
						 <p style="font-size: 14px;color: #1a638c;margin-top: 6px;margin-bottom: -5px;line-height: 23px;">&nbsp;&nbsp;&nbsp;&nbsp;${x:filterSubText(article.summary,150)}<a target="_blank" href="article/${article.id }.html" style="color: #E95E02">[详细]</a></p>
						<ul id="newskey">
						    <c:forEach items="${recommendArticleList }" var="item">
								<li><a title="${item.title }" <c:if test="${!empty item.color && item.color!=''}">style="color:${item.color }"</c:if> target="_blank" href="article/${item.id }.html">${item.title }</a> </li>
							</c:forEach> 
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="index-3">
				<div class="index-3-r">
					<div class="index3-R-list-1" style="margin-top: 10px;">
						<h1 class="index-list-t"><s><i class="iconfont icon-zuixingonggao "></i><span>阅读排名</span></s><em></em></h1>
						<ul>
							<c:forEach items="${readingRanking}" var="item" varStatus="status">
								<li><a href="article/${item.id}.html" target="_blank"><i class="iconfont icon-${status.index+1 }"></i>${item.title }</a></li>
							</c:forEach>
						</ul>					
					</div>
					<div class="index-R-list-2" style="margin-top: 10px;">
						<h1 class="index-list-t"><s><i class="iconfont icon-zuixingonggao "></i><span>热议话题</span></s><em></em></h1>
						<ul>
							<c:forEach items="${allTags }" var="item">
								<li><a title="${ item.tag_name}" href="tags/${item.id }.html">${ item.tag_name}</a></li>
							</c:forEach>
						</ul>					
					</div>
				</div>
				<div class="index-3-l">
					<div class="index-list-3" style="margin-top: 10px;">
							<h1 class="index-list-t"><s><i class="iconfont icon-yijianfankui"></i><span>内容列表</span></s><em></em></h1>
							<c:if test="${fn:length(page.list)<=0}">
								<p align="center">暂无数据</p>
							</c:if>
								<ul>
									<c:forEach items="${page.list }" var="item">
										<li>
										<c:set var="articleImg" value="${x:getArticleImg2(item.image_url,item.content) }" scope="page"></c:set>
											<c:choose>
												<c:when test="${articleImg=='' }">
													<dl class="noimg">
														<dd>
															<h1><em>${item.folderName }</em><a title="${item.title }" target="_blank" href="article/${item.id }.html">${item.title }</a></h1>
															<h2>${x:getContent(item.summary,item.content,150) } </h2>
															<h3>${item.origin } / ${item.folderName } / ${x:getDateDiff(item.publish_time)}</h3>
														</dd>
													</dl>
												</c:when>
												<c:otherwise>
													<dl>
														<dt><a href="article/${item.id }.html"  target="_blank"><img alt="${item.title }" class="lazy" title="${item.title }" data-original="${articleImg }"/></a></dt>
														<dd>
															<h1><em>${item.folderName }</em><a title="${item.title }" target="_blank" href="article/${item.id }.html">${item.title }</a></h1>
															<h2>${x:getContent(item.summary,item.content,120) } </h2>
															<h3>${item.origin } / ${item.folderName } / ${x:getDateDiff(item.publish_time)}</h3>
														</dd>
													</dl>
												</c:otherwise>
											</c:choose>
										</li>
									</c:forEach>
								</ul>	
								<c:if test="${fn:length(page.list)>0}">
									<div class="index-list-page">
										<x:pagination pageNumber="${page.pageNumber}" totalPage="${page.totalPage}" action="${action}" />
									</div>
								</c:if>
							</div>
				</div>
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
