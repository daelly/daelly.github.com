<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/template/global.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>
    	<layout:block name="title">模板页的标题</layout:block>
    </title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta name="renderer" content="webkit" >
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta charset="utf-8" />
	<meta http-equiv="keywords" content='<layout:block name="keywords">在线评判,OnlineJudge</layout:block>'>
	<meta http-equiv="description" content='<layout:block name="description">个人在线评判系统</layout:block>'>
	<layout:block name="static">
		<script type="text/javascript" src="static/lib/jQuery/jquery-1.12.4.min.js"></script>
		<script type="text/javascript" src="static/lib/layer/layer.js"></script>
		<link rel="stylesheet" type="text/css" href="static/css/iconfont.css"/>
		<link rel="stylesheet" type="text/css" href="static/css/daelly.css"/>
	</layout:block>
	<layout:block name="script">
	</layout:block>
  </head>
  <body class="RedSea">
  	<layout:block name="body">
  		<layout:block name="navigation">
  			<jsp:include page="head.jsp" />
  		</layout:block>
  		<layout:block name="content">
  			<div style="line-height: 600px;text-align: center;">
	    		模板页的内容 <br>
	    	</div>
	    </layout:block>
	    <layout:block name="footer">
	    	<jsp:include page="footer.jsp" />
	    </layout:block>
  	</layout:block>
  </body>
</html>
