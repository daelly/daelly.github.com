<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-CN" class="no-js">
    <head>
    	<base href="<%=basePath%>"> 
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="renderer" content="webkit"> 
        <link rel="icon" type="image/x-icon" href="favicon.ico">
         <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
        <meta content="IE=EmulateIE8" http-equiv="X-UA-Compatible">
        <meta content="yes" name="apple-mobile-web-app-capable">
        <meta content="black" name="apple-mobile-web-app-status-bar-style">
         <%-- <assets:assets var="href" file="/assets/assets-web.jcss">
            <link rel="stylesheet" type="text/css" href="${href}" media="all">
        </assets:assets> --%>
        <script src="//cdn.bootcss.com/jquery/2.2.1/jquery.min.js"></script>
     	<link rel="stylesheet" type="text/css" href="static/web/css/news.css"/>
	<link rel="stylesheet" type="text/css" href="static/web/css/iconfont/iconfont.css"/>
        <layout:block name="head"></layout:block>
    </head>
<body class="RedSea">
      <%@ include file="/commons/include/web/header.jsp"%> 
      <layout:block name="content"></layout:block>
      <%@ include file="/commons/include/web/footer.jsp" %> 
	  <layout:block name="script"></layout:block>
</body>
</html>