<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<layout:override name="head">
	<title>全国社保公积金查询 - ${x:getValueFromCache("web_name")}</title>
	<meta name="keywords" content="社保政策,社保查询,社保新闻，社保新政策,社保计算器,社保查询个人账户"/>
	<meta name="description" content="社保云是一个专门针对社保信息的门户网站，为广大网民提供最新的社保政策、社保资讯、汇集全国各地的社保查询、办事指南等。"/> 
</layout:override>
<layout:override name="content">
  <div class="content_block">
 		<article data-am-widget="paragraph"  class="am-paragraph am-paragraph-default content_article"   data-am-paragraph="{ tableScrollable: true, pureview: true }">
		  <h2 class="article_title">全国社保公积金查询</h2>
		  <div class="article_user_time" style="line-height: 35px;">
		 		<div><span style="margin-left: 0px;">社保云 · 社保查询</span><span>2016-05-20 10:54</span></div>
		  </div> 
		  <p>
			    <c:if test="${fn:length(list2)<=0}">
					<p align="center">暂无数据</p>
				</c:if>
				<c:forEach items="${list2}" var="item">
					<c:choose>
						<c:when test="${item.hasChild==false }">
							<p>	<a style="font-weight: bold;" href="chaxun/${item.py_name }.html" target="_blank">${item.name }</a></p>
						</c:when>
						<c:otherwise>
							<a style="font-weight: bold;" href="#" target="_blank">${item.name }</a><br>
							<p style="line-height: 25px;">
								<c:forEach items="${item.childList }" var="item2">
									<a  href="chaxun/${item2.py_name }.html">${item2.name }</a>&nbsp;
								</c:forEach>
							</p>
						</c:otherwise>
					</c:choose>
				</c:forEach>
		  </p>
		</article>
  	<div class="articleWX" style="border-top: 1px solid #f2f2f2;">
  		<img src="static/images/code02.png">
  		<span style="color: #aaaaaa">单位不给缴纳公积金?无法享受住房贷款优惠?<br>扫一扫，我们帮你缴纳<br>只需19.9，冰点价格，优质服务！</span>
    </div>
     <div data-am-widget="duoshuo" class="am-duoshuo am-duoshuo-default am-paragraph-default" data-ds-short-name="${x:getValueFromCache('duoshuo.short_name')}">
		    <div class="ds-thread" data-thread-key="chaxun" data-title="全国社保公积金查询" data-url="chaxun/index.html"></div>
	</div>
</div>
</layout:override>
<layout:override name="script">
	<script type="text/javascript">
		$(function(){
			$("img.lazy").lazyload({effect : 'fadeIn'});
		});
	</script>
</layout:override>
<%@ include file="/commons/layout_head_wap.jsp" %>
