<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>登陆</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="static/lib/jQuery/jquery-1.12.4.min.js"></script>
	<script type="text/javascript" src="static/lib/layer/layer.js"></script>
	<script type="text/javascript">
		$(function(){
			<%if(request.getAttribute("error") != null){%>
				layer.msg("<%=request.getAttribute("error")%>");
			<%}%>
		});
		function register(){
			 layer.open({
		        type: 1,
		        area: ['600px', '360px'],
		        shadeClose: true, //点击遮罩关闭
		        content: $("#register_html").html()
		    });
		}
		function submitReg(){
			$.ajax({
				url:"user/register",
				data:$("#regForm").serialize(),
				dataType:"json",
				success:function(result){
					if(result.state == 0){
						layer.msg("注册成功！");
						layer.closeAll();
					}else{
						layer.msg(result.msg);
					}
				}
			});
		}
	</script>
  </head>
  
  <body>
  	<form action="user/login" method="post">
    <table>
    	<tr>
    		<td>姓名</td>
    		<td><input type="text" name="username" /></td>
    	</tr>
    	<tr>
    		<td>密码</td>
    		<td><input type="password" name="password" /></td>
    	</tr>
    	<tr>
    		<td colspan="2" align="center">
    			<input type="submit" />
    			<input type="button" value="注册" onclick="register()" />
    		</td>
    	</tr>
    </table>
    </form>
    <script type="text/html" id="register_html">
		<form id="regForm">
		<table>
    		<tr>
    			<td>姓名</td>
    			<td><input type="text" name="username" /></td>
    		</tr>
    		<tr>
    			<td>密码</td>
    			<td><input type="password" name="password" /></td>
    		</tr>
    		<tr>
    			<td>邮箱</td>
    			<td><input type="text" name="email" /></td>
    		</tr>
    		<tr>
    			<td colspan="2" align="center">
    				<input type="button" value="提交" onclick="submitReg()" />
    			</td>
    		</tr>
    	</table>
		</form>
    </script>
  </body>
</html>
