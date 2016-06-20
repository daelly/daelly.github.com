<%@page import="com.jfinal.kit.PropKit"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<layout:override name="head">
	<title>社保查询|公积金查询 - ${x:getValueFromCache("web_name")}</title>
	<meta name="keywords" content="社保政策,社保查询,社保新闻，社保新政策,社保计算器,社保查询个人账户"/>
	<meta name="description" content="社保云是一个专门针对社保信息的门户网站，为广大网民提供最新的社保政策、社保资讯、汇集全国各地的社保查询、办事指南等。"/>
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
					<h1 class="index-list-t"><s><i class="iconfont icon-yijianfankui"></i><span>社保公积金查询</span></s><em></em></h1>
					<c:if test="${fn:length(list)<=0}">
						<p align="center">暂无数据</p>
					</c:if>
					<br>
					<c:forEach items="${list}" var="item">
						<c:choose>
							<c:when test="${item.hasChild==false }">
								<p style="margin-left: 20px;font-size: 14px">	<a style="font-weight: bold;" href="chaxun/${item.py_name }.html" target="_blank">${item.name }</a></p>
							</c:when>
							<c:otherwise>
								<p style="line-height: 25px;margin-left: 20px;">
								<a style="font-weight: bold;font-size: 14px;" href="#" target="_blank">${item.name }</a>
									<c:forEach items="${item.childList }" var="item2">
										<a target="_blank" style="font-size: 14px;color: #252525;margin-left: 10px;" href="chaxun/${item2.py_name }.html">${item2.name }</a>&nbsp;
									</c:forEach>
								</p>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</div>
			</div>
		</div>
</layout:override>
<layout:override name="script">
 	<script type="text/javascript">

 	</script>
</layout:override>
<%@ include file="/commons/layout_web.jsp" %>