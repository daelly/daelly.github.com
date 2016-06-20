<%@page import="java.util.List"%>
<%@page import="com.redsea.model.Folder"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%
 	List<Folder> list=Folder.dao.getFolderByCache();
	request.setAttribute("list", list);
%>
<div class="head">
			<div class="w1242">
				<div class="logo"><a href="<%=request.getContextPath()%>"><img style="border: none;" src="static/web/img/logo.png"/></a></div>
				<div class="search">
					<em><i class="iconfont icon-sousuo"></i><input type="text" onkeypress="if(event.keyCode==13){javascript: window.location.href='search/'+this.value;return false;}" value="${keyword }" placeholder="输入关键词搜索"/></em>
				</div>
				<div class="menu">
					<ul>
						<c:forEach items="${list}" var="item">
			        		<c:if test="${item.hasChild==true }">
			        			<li>
				                   <a href="${item.path}">${item.name } <i class="iconfont icon-xiangxiajiantou"></i></a>
		        					<em>
					                  	<c:forEach items="${item.childList }" var="item2">
						                    <a href="${item2.path }">${item2.name }</a>
					                  	</c:forEach>
				                  	</em>
			        			</li>
			        		</c:if>
			        		<c:if test="${item.hasChild==false }">
						            <li><a href="${item.path }">${item.name }</a></li>
			        		</c:if>
	        			</c:forEach>
			        		<li><a href="http://buy.sbyun.com" style="color: #f1c40f;font-weight: bold;" target="_blank">19.9买社保<img style="padding-left: 3px;" src="static/images/hot1.gif"/></a></li>
					</ul>
				</div>
				
			</div>
		</div>