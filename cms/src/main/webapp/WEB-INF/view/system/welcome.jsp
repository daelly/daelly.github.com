<%@page import="java.util.Date"%>
<%@page import="com.jfinal.core.JFinal"%>
<%@page import="com.jfinal.config.JFinalConfig"%>
<%@page import="com.jfinal.kit.PathKit"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<layout:override name="head">
	<title>code数据</title>
	<layout:head layer="true"></layout:head>
</layout:override>
<layout:override name="content">
</layout:override>
<div class="page-container">
	<p class="f-14 text-success"><b></b>欢迎<span class="f-14">${session_user.realname }</span> 登录 红海科技信息发布系统 <span class="f-14">v1.0</span></p>
	<p>登录次数：   ${session_user.login_count }  &nbsp;&nbsp;上次登录IP：${session_user.last_ip }  </p>
	<p> 上次登录时间 ：${session_user.last_login_time} </p>
	<table class="table table-border table-bordered table-bg">
		<thead>
			<tr>
				<th colspan="7" scope="col">信息统计</th>
			</tr>
			<tr class="text-c">
				<th>统计</th>
				<th>资讯库</th>
				<th>图片库</th>
				<th>产品库</th>
				<th>用户</th>
				<th>管理员</th>
			</tr>
		</thead>
		<tbody>
			<tr class="text-c">
				<td>总数</td>
				<td>92</td>
				<td>9</td>
				<td>0</td>
				<td>8</td>
				<td>20</td>
			</tr>
			<tr class="text-c">
				<td>今日</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
			</tr>
			<tr class="text-c">
				<td>昨日</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
			</tr>
			<tr class="text-c">
				<td>本周</td>
				<td>2</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
			</tr>
			<tr class="text-c">
				<td>本月</td>
				<td>2</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
			</tr>
		</tbody>
	</table>
	<table class="table table-border table-bordered table-bg mt-20">
		<thead>
			<tr>
				<th colspan="2" scope="col">服务器信息</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th width="30%">服务器计算机名</th>
				<td><span id="lbServerName"><%=System.getenv().get("COMPUTERNAME") %></span></td>
			</tr>
			<tr>
				<td>服务器IP地址</td>
				<td><%=request.getRemoteAddr() %></td>
			</tr>
			<tr>
				<td>服务器域名</td>
				<td>www.sbyun.com</td>
			</tr>
			<tr>
				<td>服务器端口 </td>
				<td><%=request.getLocalPort() %></td>
			</tr>
			<tr>
				<td>服务器IIS版本 </td>
				<td><%=JFinal.me().getContextPath() %></td>
			</tr>
			<tr>
				<td>本文件所在文件夹 </td>
				<td><%=PathKit.getRootClassPath() %></td>
			</tr>
			<tr>
				<td>系统所在文件夹 </td>
				<td><%=PathKit.getWebRootPath() %></td>
			</tr>
			<tr>
				<td>服务器的语言种类 </td>
				<td><%=request.getLocale() %></td>
			</tr>
			<tr>
				<td>.JDK 版本 </td>
				<td><%=System.getProperty("java.home") %></td>
			</tr>
			<tr>
				<td>服务器当前时间 </td>
				<td><%=new Date() %></td>
			</tr>
			<tr>
				<td>服务器操作系统 </td>
				<td><%=System.getProperty("os.name") %> <%=System.getProperty("java.specification.name") %></td>
			</tr>
			<tr>
				<td>临时目录 </td>
				<td><%=System.getProperty("java.io.tmpdir") %></td>
			</tr>
			<tr>
				<td>CPU 类型 </td>
				<td>x86 Family 6 Model 42 Stepping 1, GenuineIntel</td>
			</tr>
			<tr>
				<td>虚拟内存 </td>
				<td>52480M</td>
			</tr>
			<tr>
				<td>当前程序占用内存 </td>
				<td>3.29M</td>
			</tr>
			<tr>
				<td>当前Session数量 </td>
				<td><%=request.getSession().getValueNames().length %></td>
			</tr>
			<tr>
				<td>当前SessionID </td>
				<td><%=request.getSession().getId() %></td>
			</tr>
			<tr>
				<td>当前系统用户名 </td>
				<td><%=System.getProperty("user.name") %></td>
			</tr>
		</tbody>
	</table>
</div>
<layout:override name="script">
	<script type="text/javascript">
	</script>
</layout:override>
<%@ include file="/commons/layout_admin.jsp" %>