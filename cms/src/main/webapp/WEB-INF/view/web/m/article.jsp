<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<layout:override name="head">
	<title>${article.title } - ${x:getValueFromCache("web_name")}</title>
	<meta name="keywords" content="${article.keyword }"/>
	<meta name="description" content="${article.summary}">
</layout:override>
<layout:override name="content">
  <div class="content_block">
 		<article data-am-widget="paragraph"  class="am-paragraph am-paragraph-default content_article"   data-am-paragraph="{ tableScrollable: true, pureview: true }">
		  <h2 class="article_title">${article.title }</h2>
		  <div class="article_user_time" style="line-height: 35px;">
		 		<div><span style="margin-left: 0px;">${article.origin } · ${x:getFolderName(article.folder_id)}</span><span >${x:format(article.publish_time,'yyyy-MM-dd HH:mm')}</span></div>
		  </div> 
		  <p>${article.content }</p>
		  <div>
			  <span class="l">
					<c:forEach items="${tags }" var="item">
						<a class="am-badge am-badge-primary am-round" style="color: #ffffff" href="tags/${item.tagId }.html">${item.tag_name }</a>
					</c:forEach>
			  </span>
		  </div>
		</article>
  	<div class="articleWX" style="border-top: 1px solid #f2f2f2;">
  		<img src="static/images/code02.png">
  		<span style="color: #aaaaaa">单位不给缴纳公积金?无法享受住房贷款优惠?<br>扫一扫，我们帮你缴纳<br>只需19.9，冰点价格，优质服务！</span>
    </div>
    <div data-am-widget="duoshuo" class="am-duoshuo am-duoshuo-default am-paragraph-default" data-ds-short-name="${x:getValueFromCache('duoshuo.short_name')}">
		    <div class="ds-thread" data-thread-key="${article.id }" data-title="${article.title }" data-url="article/${article.id }.html"></div>
	</div>
</div>
</layout:override>
<layout:override name="script">

</layout:override>
<%@ include file="/commons/layout_head_wap.jsp" %>
