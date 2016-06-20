<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<layout:override name="head">
	 <title>${folderName } - ${x:getValueFromCache("web_name")}</title>
	 <meta name="Keywords" content="社保政策,社保查询,社保新闻，社保新政策,社保计算器,社保查询个人账户"/>
	 <meta name="Description" content="社保云是一个专门针对社保信息的门户网站，为广大网民提供最新的社保政策、社保资讯、汇集全国各地的社保查询、办事指南等。"/>
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
				<div class="index-list-3" style="margin-top: 10px;">
				<c:if test="${folderName!=null&&folderName!='' }">
					<h1 class="index-list-t"><s><i class="iconfont icon-yijianfankui"></i><span>${folderName }</span></s><em></em></h1>
				</c:if>
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
												<h1><em>${item.folderName}</em><a target="_blank" title="${x:filterSubText(item.title,250)}" href="article/${item.id }.html">${item.title}</a></h1>
												<h2>${x:filterText(item.content,250) } </h2>
												<h3>${item.publish_user } / ${item.folderName } / ${x:getDateDiff(item.publish_time)}</h3>
											</dd>
										</dl>
									</c:when>
									<c:otherwise>
										<dl>
											<dt><a target="_blank" href="article/${item.id }.html"><img class="lazy" title="${item.title }" data-original="${articleImg }"/></a></dt>
											<dd>
												<h1><em>${item.folderName }</em><a title="${item.title }" href="article/${item.id }.html">${item.title }</a></h1>
												<h2>${x:filterText(item.content,250) }</h2>
												<h3>${item.publish_user } / ${item.folderName } / ${x:getDateDiff(item.publish_time)}</h3>
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
		$(function(){
			$("img.lazy").lazyload({effect : "fadeIn"});
		});
	</script>
</layout:override>
<%@ include file="/commons/layout_web.jsp" %>