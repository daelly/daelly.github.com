<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<layout:override name="head">
	 <title>${article.title }-${x:getValueFromCache("web_name")}</title>
	 <meta name="keywords" content="${article.keyword }"/>
	 <meta name="description" content="${article.summary}">
</layout:override>
<layout:override name="content">
<div class="index-bd">			
			<div class="index-R">
				<div class="index-R-list-1">
					<h1 class="index-list-t"><s><i class="iconfont icon-shanhuo"></i><span>热文榜单</span></s><em></em></h1>
					<ul>
						<c:forEach items="${list_hot }" var="item">
							<li><a href="article/${item.id }.html">${item.title }</a></li>
						</c:forEach>
					</ul>					
				</div>
				<div class="index-R-list-1" style="margin-top: 10px;">
					<h1 class="index-list-t"><s><i class="iconfont icon-zuixingonggao "></i><span>最新快报</span></s><em></em></h1>
					<ul>
						<c:forEach items="${list_new }" var="item">
							<li><a href="article/${item.id }.html">${item.title }</a></li>
						</c:forEach>
					</ul>					
				</div>
				
				<div class="index-R-list-2" style="margin-top: 10px;">
					<h1 class="index-list-t"><s><i class="iconfont icon-zuixingonggao "></i><span>热门话题</span></s><em></em></h1>
					<ul>
						<c:forEach items="${allTags }" var="item">
							<li><a href="tags/${item.id }.html">${ item.tag_name}</a></li>
						</c:forEach>
					</ul>					
				</div>
			</div>
			<div class="index-L">
				<div class="detail-L1">
					${article.title }<c:if test="${!empty article.folder_id && article.folder_id!=0}"><em>${x:getFolderName(article.folder_id)}</em></c:if> 
				</div>
				<div class="detail-L2">
					${x:format(article.publish_time,'yyyy-MM-dd HH:mm')} <%-- ${x:getDateDiff(article.publish_time)} --%>  &nbsp; ${article.publish_user } 
					<c:if test="${article.origin!=null&&article.origin!='' }">&nbsp;&nbsp;&nbsp;来源：${article.origin}</c:if>
				</div>
				<div class="detail-L3">
					${article.summary }
				</div>
				<div class="detail-L4">
					<!-- <p style="text-align: center;"><img src="static/web/img/1460961194418.png" width="90%"/></p><br/> -->
					 	${article.content } 　
					<br/>
				</div>
				<div class="detail-L5">
					<div class="detail-L5-l">
						<ul>
						<c:forEach items="${tags }" var="item">
							<li><a href="tags/${item.tagId }.html">${item.tag_name }</a></li>
						</c:forEach>
						</ul>	
					</div>
					<div class="detail-L5-r" style="padding-top: 24px;">
					<!-- JiaThis Button BEGIN -->
						<div class="jiathis_style">
							<span class="jiathis_txt">分享到：</span>
							<a class="jiathis_button_tools_1"></a>
							<a class="jiathis_button_tools_2"></a>
							<a class="jiathis_button_tools_3"></a>
							<a class="jiathis_button_tools_4"></a>
							<a href="http://www.jiathis.com/share?uid=1818986" class="jiathis jiathis_txt jiathis_separator jtico jtico_jiathis" target="_blank">更多</a>
							<a class="jiathis_counter_style"></a>
						</div>
						<script type="text/javascript">
						var jiathis_config = {data_track_clickback:'true'};
						</script>
						<script type="text/javascript" src="http://v3.jiathis.com/code/jia.js?uid=1374765224003468" charset="utf-8"></script>
						<!-- JiaThis Button END -->					
					</div>

				</div>
				<div class="detail-L6">				
					<!-- 多说评论框 start -->
					<div class="ds-thread" data-thread-key="${article.id }" data-title="${article.title}" data-url="<%= request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"%>article/${article.id }"></div>
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
<%@ include file="/commons/layout_web.jsp" %>