<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<layout:override name="head">
	  <title>社保专题_社保新闻_社保查询_社保查询个人账户 - ${x:getValueFromCache("web_name")}</title>
	  <meta name="keywords" content="社保查询,社保查询个人账户,社保计算器2016,社保网上办事大厅,社保网上服务平台,社保办理,社保代办,社保代理公司,社保补缴,社保代缴" />
      <meta name="description" content="社保专题，社保云专题频道为您全方位报道社保查询、社保查询个人账户、社保计算器2016、社保网上办事大厅、社保网上服务平台、社保办理、社保代办、社保代理公司、社保补缴、社保代缴等有关专题的最新最全的新闻信息" />
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
							<c:set var="articleImg" value="${x:getUrl(item.titleImg) }" scope="page"></c:set>
								<c:choose>
									<c:when test="${articleImg=='' }">
										<dl class="noimg">
											<dd>
												<h1><a title="${item.title }" href="topic/${item.id }.html">${item.title }</a></h1>
												<h2>${x:filterSubText(item.description,120) } </h2>
												<h3>${item.publish_user }  / ${x:format(item.publish_time,'yyyy-MM-dd HH:mm')}</h3>
											</dd>
										</dl>
									</c:when>
									<c:otherwise>
										<dl>
											<dt><a href="topic/${item.id }.html"><img alt="${item.title }" class="lazy" title="${item.title }" data-original="${articleImg }"/></a></dt>
											<dd>
												<h1><a title="${item.title }" href="topic/${item.id }.html">${item.title }</a></h1>
												<h2>${x:filterSubText(item.description,120) } </h2>
												<h3>${item.publish_user }  / ${x:format(item.publish_time,'yyyy-MM-dd HH:mm')}</h3>
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
	<script src="static/js/jquery.lazyload.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		$(function(){
			$("img.lazy").lazyload({effect : "fadeIn"});
		});
	</script>
</layout:override>
<%@ include file="/commons/layout_web.jsp" %>