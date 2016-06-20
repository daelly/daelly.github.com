<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<layout:override name="head">
	 <title>${topic.title} - ${x:getValueFromCache("web_name")}</title>
	  <meta name="keywords" content="${topic.keywords}">
	  <meta name="description" content="${topic.description}">
</layout:override>
<layout:override name="content">
		<div class="index-bd">			
			<div class="index-R">
				<div class="index-R-list-1">
					<h1 class="index-list-t"><s><i class="iconfont icon-shanhuo"></i><span>最新推荐</span></s><em></em></h1>
					<ul>
						<c:forEach items="${list_tuijian }" var="item">
							<li><a title="${item.title }" href="article/${item.id}.html">${item.title}</a></li>
						</c:forEach>
					</ul>					
				</div>
				<div class="index-R-list-1">
					<h1 class="index-list-t"><s><i class="iconfont icon-shanhuo"></i><span>热文榜单</span></s><em></em></h1>
					<ul>
						<c:forEach items="${list_hot }" var="item">
							<li><a title="${item.title }" href="article/${item.id}.html">${item.title}</a></li>
						</c:forEach>
					</ul>					
				</div>
				<div class="index-R-list-1" style="margin-top: 10px;">
					<h1 class="index-list-t"><s><i class="iconfont icon-zuixingonggao "></i><span>最新快报</span></s><em></em></h1>
					<ul>
						<c:forEach items="${list_new }" var="item">
							<li><a title="${item.title }" href="article/${item.id }.html">${item.title }</a></li>
						</c:forEach>
					</ul>					
				</div>
				
				<div class="index-R-list-2" style="margin-top: 10px;">
					<h1 class="index-list-t"><s><i class="iconfont icon-zuixingonggao "></i><span>热门话题</span></s><em></em></h1>
					<ul>
						<c:forEach items="${allTags }" var="item">
							<li><a title="${ item.tag_name}" href="tags/${item.id }.html">${ item.tag_name}</a></li>
						</c:forEach>
					</ul>					
				</div>
			</div>
			<div class="index-L">
					<c:choose>
						<c:when test="${topic.contentImg!=''&&topic.contentImg!=null }">
							<div class="special-list-3-1" style="margin-top: -30px;">
								<img src="${x:getUrl(topic.contentImg) }" style="width: 840px;max-height: 250px;"/>
							</div>
							<div class="special-list-3-2">
								<em>专题摘要 </em>
									${topic.description }
							</div>
						</c:when>
						<c:otherwise>
							 <div class="special-list-3-3">
								<h1>${topic.title }</h1>
								<!-- <h2>时间：2015年6月1日21时地点：长江中游湖北监利水域</h2> -->
							</div>
							<div class="special-list-3-2">
								<em>专题摘要 </em>
								${topic.description }
							</div> 
						</c:otherwise>
					</c:choose>
					<div class="index-list-3" style="margin-top: 10px;">
						<h1 class="index-list-t"><s><i class="iconfont icon-yijianfankui"></i><span>媒体聚焦</span></s><em></em></h1>
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
													<h1><a title="${item.title }" href="article/${item.id }.html">${item.title }</a></h1>
													<h2>${x:filterSubText(item.content,120) } </h2>
													<h3>${item.publish_user }  / ${x:getDateDiff(item.publish_time)}</h3>
												</dd>
											</dl>
										</c:when>
										<c:otherwise>
											<dl>
												<dt><a href="article/${item.id }.html"><img class="lazy" alt="${item.title }" title="${item.title }" data-original="${articleImg }"/></a></dt>
												<dd>
													<h1><a title="${item.title }" href="article/${item.id }.html">${item.title }</a></h1>
													<h2>${x:filterSubText(item.content,120) } </h2>
													<h3>${item.publish_user }  / ${x:getDateDiff(item.publish_time)}</h3>
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
							<%-- <jsp:include page="/commons/include/web/pagination.jsp"><jsp:param value="${action}" name="action"/></jsp:include> --%>
						</c:if>
					</div>
				</div>
		</div>
</layout:override>
<layout:override name="script">
	<script src="static/js/jquery.lazyload.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
	    /* var swiper = new Swiper('.swiper-container', {
	        pagination: '.swiper-pagination',
	        paginationClickable: true,
	        prevButton:'.swiper-button-prev',
	        nextButton:'.swiper-button-next',
	        autoplay : 3000,
	        speed:300
	    }); */
		$(function(){
			$("img.lazy").lazyload({effect : "fadeIn"});
		});
	</script>
</layout:override>
<%@ include file="/commons/layout_web.jsp" %>