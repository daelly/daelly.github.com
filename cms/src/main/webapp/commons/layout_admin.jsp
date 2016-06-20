<%@ page language="java" session="false" contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ include file="/commons/global.jsp" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>"> 
     <meta charset="UTF-8">
     <meta http-equiv="X-UA-Compatible" content="IE=edge">
	 <meta name="renderer" content="webkit"> 
     <link rel="icon" type="image/x-icon" href="favicon.ico">
     <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
     <meta content="IE=EmulateIE8" http-equiv="X-UA-Compatible">
    <%-- 
	    <assets:assets var="href" file="/assets/assets-admin.jcss">
	        <link rel="stylesheet" type="text/css" href="${href}">
	    </assets:assets> 
    --%>
     <link rel="stylesheet" type="text/css" href="static/h-ui/css/style.css">
     <link rel="stylesheet" type="text/css" href="static/h-ui/css/H-ui.min.css">
     <link rel="stylesheet" type="text/css" href="static/h-ui/css/H-ui.admin.css">
     <link rel="stylesheet" type="text/css" href="static/lib/Hui-iconfont/1.0.7/iconfont.css">
     <script src="//cdn.bootcss.com/jquery/2.2.1/jquery.min.js"></script>
    <script src="http://code.jquery.com/jquery-migrate-1.2.1.js"></script> 
     <script type="text/javascript" src="static/js/admin/admin.js"></script>
    <script type="text/javascript" src="static/js/pubfunction.js"></script>
    <link rel="stylesheet" type="text/css" href="http://cdn.staticfile.org/font-awesome/3.2.1/css/font-awesome.min.css"/>
    <layout:block name="head"></layout:block>
</head>
<body style="border: 15px solid #f4f4f4;">
    <layout:block name="content"></layout:block>
    <script type="text/javascript">
        function getCtxPath() {
            return "${ctxPath}";
        }
    </script>
   <%--  <assets:assets var="x" file="/assets/assets-admin.jjs">
        <script src="${x}" type="text/javascript" ></script>
    </assets:assets> --%>
    <script type="text/javascript" src="static/h-ui/js/H-ui.js"></script>
    <layout:block name="script"></layout:block>
</body>
</html>