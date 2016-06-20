<%@page import="com.redsea.utils.CS"%>
<%@page import="com.jfinal.kit.PropKit"%>
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
    	  <meta charset="utf-8">
		  <meta http-equiv="X-UA-Compatible" content="IE=edge">
		  <meta name="Keywords" content="社保政策,社保查询,社保新闻，社保新政策,社保计算器,社保查询个人账户"/>
		  <meta name="Description" content="社保云是一个专门针对社保信息的门户网站，为广大网民提供最新的社保政策、社保资讯、汇集全国各地的社保查询、办事指南等。"/> 
		  <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
		  <!-- Set render engine for 360 browser -->
		  <meta name="renderer" content="webkit">
		  <!-- No Baidu Siteapp-->
		  <meta http-equiv="Cache-Control" content="no-siteapp"/>
	      <link rel="icon" type="image/x-icon" href="favicon.ico">
		  <link rel="icon" type="image/png" href="assets/i/favicon.png">
		  <!-- Add to homescreen for Chrome on Android -->
		  <meta name="mobile-web-app-capable" content="yes">
		  <link rel="icon" sizes="192x192" href="assets/i/app-icon72x72@2x.png">
		  <!-- Add to homescreen for Safari on iOS -->
		  <meta name="apple-mobile-web-app-capable" content="yes">
		  <meta name="apple-mobile-web-app-status-bar-style" content="black">
		  <meta name="apple-mobile-web-app-title" content="Amaze UI"/>
		  <link rel="apple-touch-icon-precomposed" href="assets/i/app-icon72x72@2x.png">
		  <!-- Tile icon for Win8 (144x144 + tile color) -->
		  <meta name="msapplication-TileImage" content="assets/i/app-icon72x72@2x.png">
		  <meta name="msapplication-TileColor" content="#0e90d2">
		  <link rel="stylesheet" href="static/lib/amazeui/css/amazeui.min.css">
		  <link rel="stylesheet" href="static/web/css/wap.css">
          <layout:block name="head"></layout:block>
    </head>
<body>
      <layout:block name="content"></layout:block>
	  <footer data-am-widget="footer" class="am-footer am-footer-default" style="background-color: #f4f4f4;margin-top: 10px;" data-am-footer="{  }">
	    <div class="am-footer-miscs ">
			 <p>广州红海网络科技开发有限公司</p>
	         <p>Copyright © 2012-2016 粤ICP备12081892号</p>
	    </div>
 	 </footer>
	 <div data-am-widget="gotop" class="am-gotop am-gotop-fixed" >
	    <a href="#top" title="回到顶部">
	        <span class="am-gotop-title">回到顶部</span>
	          <i class="am-gotop-icon am-icon-chevron-up"></i>
	    </a>
	  </div>
	 <%
		CS cs = new CS(1257989635);cs.setHttpServlet(request,response);
		String imgurl = cs.trackPageView();
	 %> 
	 <img style="display: none;" src="<%= imgurl %>" width="0" height="0"  />
	 <script src="//cdn.bootcss.com/jquery/2.2.1/jquery.min.js"></script>
	 <script src="static/lib/amazeui/amazeui.lazyload.min.js"></script>
	 <script src="static/lib/amazeui/js/amazeui.min.js"></script>
	 <script type="text/javascript">var basePath='<%=PropKit.get("basePath")%>';</script>
	 <layout:block name="script"></layout:block>
</body>
</html>