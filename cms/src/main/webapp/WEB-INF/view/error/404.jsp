<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%
String path=request.getContextPath();
if("".equals(path)){
	path="/";
}

%>
<!DOCTYPE html>
<html>
	<head>
		<title>页面找不到鸟 -404 </title>
		<link rel="icon" type="image/x-icon" href="favicon.ico">
		<meta http-equiv="refresh" content="15;url=<%=path%>"> 
		<style type="text/css">
			body {
			  color: #666;
			  text-align: center;
			  font-family: Helvetica, 'microsoft yahei', Arial, sans-serif;
			  margin:0;
			  width: 800px;
			  margin: auto;
			  font-size: 14px;
			}
			h1 {
			  font-size: 56px;
			  line-height: 100px;
			  font-weight: normal;
			  color: #456;
			}
			h2 { font-size: 24px; color: #666; line-height: 1.5em; }
			
			h3 {
			  color: #456;
			  font-size: 20px;
			  font-weight: normal;
			  line-height: 28px;
			}
			
			hr {
			  margin: 18px 0;
			  border: 0;
			  border-top: 1px solid #EEE;
			  border-bottom: 1px solid white;
			}
			
			a{
			    color: #17bc9b;
			    text-decoration: none;
			}
		</style>
	</head>
<body>
 <h1>404</h1>
  <h3>您所访问的页面不存在.</h3>
  <hr/>
  <p>请确认您输入的网址是否正确，如果问题持续存在，请发邮件至service@sbyun.com与我们联系。 <br><br><a href="<%=path%>">返回首页</a></p>
	
	<%-- <script type="text/javascript" src="http://www.qq.com/404/search_children.js" charset="utf-8" homePageUrl="<%=path%>" homePageName="返回首页"></script> --%>
</body>
</html>