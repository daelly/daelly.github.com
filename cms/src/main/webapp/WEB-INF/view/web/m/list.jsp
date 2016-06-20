<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/global.jsp" %>
<layout:override name="head">
	 <title>${folderName} - ${x:getValueFromCache("web_name")}</title>
</layout:override>
<layout:override name="content">
	<div data-am-widget="list_news" class="am-list-news am-list-news-default" >
		  <div class="am-list-news-bd">
		  <ul class="am-list" id="content">
			 <c:forEach items="${page.list }" var="item">
			 	<c:set var="articleImg" value="${x:getArticleImg2(item.image_url,item.content) }" scope="page"></c:set>
			      	<c:choose>
							<c:when test="${articleImg=='' }">
						      <li class="am-g am-list-item-desced">
						        <div class=" am-list-main">
						            <h3 class="am-list-item-hd"><a href="article/${item.id }.html" class="">${item.title }</a></h3>
						            <div class="am-list-item-text">${x:getContent(item.summary,item.content,120) } </div>
						        </div>
						      </li>
							</c:when>
							<c:otherwise>
							      <li class="am-g am-list-item-desced am-list-item-thumbed am-list-item-thumb-left">
							        <div class="am-u-sm-4 am-list-thumb">
							          <a href="article/${item.id }.html" class="">
							            <img class="lazy" data-original="${articleImg }" alt="${item.title }"/>
							          </a>
							        </div>
							        <div class=" am-u-sm-8 am-list-main">
							            <h3 class="am-list-item-hd"><a href="article/${item.id }.html" class="">${item.title }</a></h3>
							            <div class="am-list-item-text">${x:getContent(item.summary,item.content,120) } </div>
							        </div>
							      </li>
							</c:otherwise>
					</c:choose>
			  </c:forEach>
		    </ul>
		    <c:if test="${page.lastPage==false}">
			     	<a href="javascript:void(0);"  data-url="${action }" data-pageNo="1" class="am-list-news-more next-page">
			 			 <span class="am-list-more-txt">点击加载更多<i class="am-icon-angle-double-down"></i></span>
				  </a>
		    </c:if>
		  </div>
    </div>
  <script type="text/template" id="listTemplate">
	{{if image_url!='' && image_url!=null}}	
	<li class="am-g am-list-item-desced am-list-item-thumbed am-list-item-thumb-left">
		<div class="am-u-sm-4 am-list-thumb">
			<a href="article/{{:id}}.html" class="">
				<img class="lazy" src="{{getImg content image_url /}}" alt="{{:title}}" onerror="this.src='static/web/img/nodata.png'"/>
            </a>
		</div>
 		<div class=" am-u-sm-8 am-list-main">
			<h3 class="am-list-item-hd"><a href="article/{{:id}}.html"  class="">{{:title}}</a></h3>
			<div class="am-list-item-text">{{getContent content /}}</div>
		</div>
	</li>
	{{else}}
		<li class="am-g am-list-item-desced">
			<div class=" am-list-main">
				<h3 class="am-list-item-hd"><a href="article/{{:id}}.html" class="">{{:title}}</a></h3>
				<div class="am-list-item-text">{{getContent content /}}</div>
			</div>
		</li>
	{{/if}}				
  </script>

</layout:override>
<layout:override name="script">
 	 <script src="static/js/pubfunction.js" type="text/javascript" charset="utf-8"></script>
	 <script src="static/lib/jsrender/jsrender.min.js" type="text/javascript" charset="utf-8"></script>
	 <script src="static/web/js/wap.js" type="text/javascript" charset="utf-8"></script>
	 <script src="static/js/jquery.lazyload.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		$(function(){
			$("img.lazy").lazyload({effect : "fadeIn"});
		});
	</script>
</layout:override>
<%@ include file="/commons/layout_head_wap.jsp" %>