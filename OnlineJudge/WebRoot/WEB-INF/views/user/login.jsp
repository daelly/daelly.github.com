<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/template/global.jsp" %>
<layout:override name="title">OnlineJudge登录</layout:override>
<layout:override name="static">
	<script type="text/javascript" src="static/lib/jQuery/jquery-1.12.4.min.js"></script>
	<script type="text/javascript" src="static/lib/layer/layer.js"></script>
	<script type="text/javascript" src="static/lib/Validform/5.3.2/Validform.min.js"></script>
	<link rel="stylesheet" type="text/css" href="static/css/daelly.css"/>
	<link rel="stylesheet" type="text/css" href="static/css/login.css"/>
	<script type="text/javascript" src="static/lib/bootstrap-3.3.0/js/bootstrap.min.js"></script>
	<link rel="stylesheet" type="text/css" href="static/lib/bootstrap-3.3.0/css/bootstrap.min.css"/>
</layout:override>
<layout:override name="script">
	<script type="text/javascript">
		$(function(){
			<c:if test="${not empty error}">
				layer.msg("${error}");
				var randNum = parseInt(100*Math.random());
				$("#login-rand-code").attr("src","user/randcode?_t="+randNum);
				$("form input[name='randcode']").val("");
				$(".hrsoft-login-bd-L2 .Ver-code").show();
			</c:if>
			$("form").Validform({tiptype:3});
		});
		
		function refreshRandCode(){
			var randNum = parseInt(100*Math.random());
			$("#login-rand-code").attr("src","user/randcode?_t="+randNum);
		}
	</script>
</layout:override>
<layout:override name="content">
<div class="hrsoft-login-bd">
	<div class="hrsoft-login-bd-L1"></div>
	<div class="hrsoft-login-bd-L2">
		<dl>
			<dt><em id="apk_code"><img src="static/img/outwork-login2.png"/></em><span>扫描二维码下载外勤宝APP</span></dt>
			<dd>
				<form action="user/login" method="post">
				<ul>
					<li><input type="text" name="username" placeholder="请输入你的账号" value="${username}" datatype="*" nullmsg="用户名不能为空" errormsg="" /></li>
					<li><input type="password" name="password" placeholder="请输入你的密码" datatype="*" nullmsg="密码不能为空" errormsg="" /></li>
					<li class="Ver-code" style="display: none;">
						<span><input type="text" placeholder="" name="randcode" value="1234" style="width: 100px;" datatype="*" nullmsg="验证码不能为空" errormsg=""/></span>
						<img id="login-rand-code" src=""/>
						<span><a href="javascript:void(0)" onclick="refreshRandCode()">看不清楚换一张</a></span>
					</li>
					<li><button type="submit">登录</button></li>
					<li class="hrsoft-login-findpass" style="margin-top: -10px;">
						<em><i><input type="checkbox" /></i><b>自动登录</b></em>
						<s><a>找回密码</a></s>
					</li>
				</ul>
				</form>
			</dd>
		</dl>
	</div>
</div>

<div id="enlarge_images" style="position: absolute;"></div>
</layout:override>
<jsp:include page="/template/base.jsp" />
<%-- <%
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
</html> --%>
