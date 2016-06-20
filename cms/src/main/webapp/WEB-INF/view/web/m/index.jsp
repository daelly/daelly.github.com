<%@page import="com.redsea.model.Folder"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%
	request.setAttribute("list", Folder.dao.getFolderByCache());
%>
<layout:override name="head">
	<title>${x:getValueFromCache("web_name")} - 专业的社保门户,提供最全面社保新政策</title>
	<meta name="keywords" content="社保政策,社保查询,社保新闻，社保新政策,社保计算器,社保查询个人账户"/>
	<meta name="description" content="社保云是一个专门针对社保信息的门户网站，为广大网民提供最新的社保政策、社保资讯、汇集全国各地的社保查询、办事指南等。"/> 
</layout:override>
<layout:override name="content">
 <header data-am-widget="header"  class="am-header am-header-default">
      <div class="am-header-left am-header-nav">
          <a href="javascript:;"  data-am-widget="navbar">
          		<img style="width: 75px;height: 25px;" src="static/images/logo.png">
          </a>
      </div>
  </header>
	<nav id="menu" data-am-widget="menu" class="am-menu  am-menu-slide1"   data-am-menu-collapse> 
	    <a href="javascript: void(0)" class="am-menu-toggle">
	          <i class="am-menu-toggle-icon am-icon-bars am-icon-sm"></i>
	    </a>
      <ul class="am-menu-nav am-avg-sm-4 am-collapse">
      	<c:forEach items="${list}" var="item">
       		<c:if test="${item.hasChild==true }">
       			<li class="am-parent">
       			 	<a href="${item.path}" class="" >${item.name }</a>
       			 	<ul class="am-menu-sub am-collapse  am-avg-sm-3 ">
	                  	<c:forEach items="${item.childList }" var="item2">
		                    <li class=""> <a href="m/${item2.path }">${item2.name }</a></li>
	                  	</c:forEach>
       			 	</ul>
       			</li>
       		</c:if>
       		<c:if test="${item.hasChild==false }">
		            <li><a href="${item.path }">${item.name }</a></li>
       		</c:if>
    	</c:forEach>
    	    <li><a href="http://m.sbyun.com" style="color: #f1c40f;font-weight: bold;">19.9买社保</a></li>
      </ul>
  </nav> 
  <div data-am-widget="slider" class="am-slider am-slider-c2" data-am-slider='{&quot;directionNav&quot;:false}' >
	  <ul class="am-slides">
	  	<c:forEach items="${bannerData }" var="item">
	        <li>
	        	<a href="article/${item.id}.html"><img class="lazy" style="height: 160px;" src="${x:getUrl(item.image_url)}"></a>
	          <div class="am-slider-desc">${item.title}</div>
	      </li>
		  </c:forEach>
	  </ul>
</div>
<div data-am-widget="list_news" class="am-list-news am-list-news-default" >
  <div class="am-list-news-bd">
  <ul class="am-list" id="content">
	     <c:forEach items="${page.list }" var="item">
    			<c:set var="articleImg" value="${x:getArticleImg2(item.image_url,item.content)}" scope="page"></c:set>
    			<c:choose>
	    				<c:when test="${articleImg!=''}">
						      <li class="am-g am-list-item-desced am-list-item-thumbed am-list-item-thumb-left">
							        <div class="am-u-sm-4 am-list-thumb">
								          <a href="article/${item.id}.html" class="">
								            <img class="lazy" data-original="${x:getArticleImg2(item.image_url,item.content)}" alt="${item.title }"/>
								          </a>
							        </div>
							        <div class=" am-u-sm-8 am-list-main">
								            <h3 class="am-list-item-hd"><a href="article/${item.id}.html"  class="">${item.title }</a></h3>
								            <div class="am-list-item-text">${x:getContent(item.summary,item.content,120) } </div>
								          <%--   <div class="am-list-item-bar">
												<span class="am-list-item-time">${x:getDateDiff(item.publish_time)}</span>
												<span class="am-list-icon"><i class="am-icon-eye"></i> ${item.count_view}</span>
												<span class="am-list-icon"><i class="am-icon-heart-o"></i>关注</span>
										</div> --%>
							        </div>
		     			      </li>
	    				</c:when>
	    				<c:otherwise>
		    				 <li class="am-g am-list-item-desced">
			      				  <div class=" am-list-main">
			           					 <h3 class="am-list-item-hd"><a href="article/${item.id}.html" class="">${item.title }</a></h3>
			          					  <div class="am-list-item-text">${x:getContent(item.summary,item.content,120) } </div>
			       				 </div>
		      				</li>
	    				</c:otherwise>
    			</c:choose>
	     </c:forEach>
    </ul>
     <a href="javascript:void(0);" data-url="indexPage/" data-pageNo="1" class="am-list-news-more next-page">
		  <span class="am-list-more-txt">点击加载更多<i class="am-icon-angle-double-down"></i></span>
	 </a>
  </div>
</div>
<script type="text/x-jsrender" id="listTemplate">
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
	 <script type="text/javascript">
		$(function(){
			$("img.lazy").lazyload({effect : 'fadeIn'});
		});
	</script>
</layout:override>
<%@ include file="/commons/layout_wap.jsp" %>