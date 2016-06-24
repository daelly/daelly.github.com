<%@page import="org.daelly.oj.utils.Const"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%
	Object sessionObj = session.getAttribute(Const.SESSION_KEY);
	request.setAttribute("user", sessionObj);
 %>
<div class="jingyi-head">
	
	<div class="jingyi-head-L">
		<a href="index"></a>
	</div>
	<div class="jingyi-head-R">
		<c:if test="${empty user}">
		<a href="user/register" class="jinyi-but"><i>注册</i></a>
		<a href="user/login" class="jinyi-but"><i>登录</i></a>
		</c:if>
		<c:if test="${not empty user}">
			<span>${user}</span>
			<a href="user/logout" class="jinyi-but"><i>注销</i></a>
		</c:if>
	</div>
	<div class="jingyi-head-C">
		<a href="index" class="active">首页</a>
		<a href="page-01.html">题目</a>
		<a href="page-02.html">比赛</a>
		<a href="page-03.html">排名</a>
		<a href="page-04.html">论坛</a>
	</div>
	
</div>