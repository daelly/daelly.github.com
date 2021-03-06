<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/template/global.jsp" %>
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
	<div class="register-bd">
		<div class="register-bd-t"><em><img src="static/img/reg01.png"/></em></div>
		<div class="register-bd-b">
			<form action="user/register" method="post">
			<ul>
				<li><span>用户名：</span><em><input name="username" value="${username}" type="text" datatype="*" nullmsg="请输入用户名！" /></em><i>**</i></li>
				<li><span>密码：</span><em><input name="password" value="${password}" type="password" datatype="*6-16" nullmsg="请设置密码！" errormsg="密码范围在6~16位之间！"/></em><i>**</i></li>
				<li><span>重复密码：</span><em><input type="password" value="${password}" datatype="*" recheck="password" nullmsg="请再输入一次密码！" errormsg="您两次输入的账号密码不一致！" /></em><i>**</i></li>
				<li><span>邮箱：</span><em><input name="email" value="${email}" type="text" datatype="e" nullmsg="请输入用常用邮箱！" errormsg="请输入正确的email格式" /></em><i>**</i></li>
				<li><span>验证码：</span><em style="width: 120px;"><input type="text" name="randcode" style="width: 100px;" datatype="*"/></em>
					<b><img src="user/randcode" id="login-rand-code" /></b>
					<s style="margin-left: 20px;"><a href="javascript:void(0)" onclick="refreshRandCode()">换一张</a></s>
				</li>
				<li>
					<button type="submit" style="background-color: #f48c0f;">注册</button>
					<button type="button" style="background-color: #3487c5;">取消</button>
				</li>
			</ul>
			<div class="register-picture"></div>
			</form>
		</div>
	</div>
</layout:override>

<jsp:include page="/template/base.jsp" />
