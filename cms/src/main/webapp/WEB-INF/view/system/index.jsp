<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>"> 
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit"> 
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="icon" type="image/x-icon" href="favicon.ico">
<LINK rel="Bookmark" href="/favicon.ico" >
<LINK rel="Shortcut Icon" href="/favicon.ico" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<script type="text/javascript" src="lib/PIE_IE678.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="static/h-ui/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="static/lib/Hui-iconfont/1.0.7/iconfont.css" />
<link rel="stylesheet" type="text/css" href="static/lib/icheck/icheck.css" />
<link rel="stylesheet" type="text/css" href="static/h-ui/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="static/h-ui/css/style.css" />
<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>后台管理 - ${x:getValueFromCache("web_name")}</title>
<script language="javascript" type="text/javascript">
	if(top.location!=self.location)top.location=self.location;
</script>
</head>
<body>
<header class="navbar-wrapper">
	<div class="navbar navbar-fixed-top">
		<div class="container-fluid cl"> <a class="logo navbar-logo f-l mr-10 hidden-xs" target="_blank" href="<%=request.getContextPath()%>">红海科技</a> <a class="logo navbar-logo-m f-l mr-10 visible-xs" href="/aboutHui.shtml">cms</a> <span class="logo navbar-slogan f-l mr-10 hidden-xs">v1.0</span> <a aria-hidden="false" class="nav-toggle Hui-iconfont visible-xs" href="javascript:;">&#xe667;</a>
			<nav class="nav navbar-nav">
				<ul class="cl">
					<li class="dropDown dropDown_hover"><a href="javascript:;" class="dropDown_A"><i class="Hui-iconfont">&#xe600;</i> 快速入口 <i class="Hui-iconfont">&#xe6d5;</i></a>
						<ul class="dropDown-menu menu radius box-shadow">
							<li><a href="javascript:;" onclick="showFull('内容管理','system/article/index')"><i class="Hui-iconfont">&#xe616;</i>内容管理</a></li>
							<li><a href="javascript:;" onclick="showFull('资讯新增','system/article/edit')"><i class="Hui-iconfont">&#xe616;</i>资讯新增</a></li>
							<li><a href="javascript:;" onclick="showFull('专题管理','system/topic/index')"><i class="Hui-iconfont">&#xe616;</i>专题管理</a></li>
							<li><a href="javascript:;" onclick="showFull('栏目','system/folder/tree')"><i class="Hui-iconfont">&#xe613;</i>栏目</a></li>
							<li><a href="javascript:;" onclick="showFull('评论','${x:getValueFromCache('duoshuo.admin') }')"><i class="Hui-iconfont">&#xe620;</i>评论</a></li>
							<li><a href="javascript:;" onclick="showFull('用户','system/people/index')"><i class="Hui-iconfont">&#xe60d;</i> 用户</a></li>
						</ul>
					</li>
				</ul>
			</nav>
			<nav id="Hui-userbar" class="nav navbar-nav navbar-userbar hidden-xs">
				<ul class="cl">
					<li>超级管理员</li>
					<li class="dropDown dropDown_hover"> <a href="#" class="dropDown_A">${session_user.username } <i class="Hui-iconfont">&#xe6d5;</i></a>
						<ul class="dropDown-menu menu radius box-shadow">
							<li><a href="#">个人信息</a></li>
							<li><a href="#">切换账户</a></li>
							<li><a href="system/sysuser/logout">退出</a></li>
						</ul>
					</li>
					<li id="Hui-msg"> <a href="#" title="消息"><span class="badge badge-danger">1</span><i class="Hui-iconfont" style="font-size:18px">&#xe68a;</i></a> </li>
					<li id="Hui-skin" class="dropDown right dropDown_hover"> <a href="javascript:;" class="dropDown_A" title="换肤"><i class="Hui-iconfont" style="font-size:18px">&#xe62a;</i></a>
						<ul class="dropDown-menu menu radius box-shadow">
							<li><a href="javascript:;" data-val="blue" title="默认（蓝色）">默认（蓝色）</a></li>
							<li><a href="javascript:;" data-val="default" title="黑色">黑色</a></li>
							<li><a href="javascript:;" data-val="green" title="绿色">绿色</a></li>
							<li><a href="javascript:;" data-val="red" title="红色">红色</a></li>
							<li><a href="javascript:;" data-val="yellow" title="黄色">黄色</a></li>
							<li><a href="javascript:;" data-val="orange" title="绿色">橙色</a></li>
						</ul>
					</li>
				</ul>
			</nav>
		</div>
	</div>
</header>
<aside class="Hui-aside">
	<input runat="server" id="divScrollValue" type="hidden" value="" />
	<div class="menu_dropdown bk_2">
		<dl id="menu-article">
			<dt><i class="Hui-iconfont">&#xe616;</i> 内容管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd style="display: block;">
				<ul>
 					<li><a _href="system/folder/tree" data-title="栏目管理" href="javascript:void(0)">栏目管理</a></li>
					<li><a oncontextmenu ="newOpen($(this))"  _href="system/article/tree" data-title="内容管理" href="javascript:void(0);" onclick="displaynavbar($('.pngfix'))">文章管理</a></li>
					<li><a  _href="system/article/review_list" data-title="内容审核" href="javascript:void(0);">内容审核</a></li>
					<li><a  _href="system/topic/index" data-title="专题管理" href="javascript:void(0)">专题管理</a></li>
					<li><a  _href="system/article/index_home" data-title="首页内容管理" href="javascript:void(0);">首页内容管理</a></li>
				</ul>
			</dd>
		</dl>
		<dl id="menu-spider">
			<dt><i class="Hui-iconfont">&#xe616;</i> 数据采集<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a  _href="ext/spider/index" data-title="内容采集" href="javascript:void(0)">内容采集</a></li>
					<li><a  _href="system/spider_rule/index" data-title="内容采集" href="javascript:void(0)">采集规则</a></li>
					<li><a  _href="system/keyword/index" data-title="指数采集" href="javascript:void(0)">指数采集</a></li>
					<li><a  _href="system/spider_job/index" data-title="指数采集" href="javascript:void(0)">定时采集</a></li>
					<li><a  _href="system/article/push_page" data-title="百度推送" href="javascript:void(0)">百度推送</a></li>
					<li><a  _href="http://seo.chinaz.com/?q=www.sbyun.com" data-title="站长工具" href="javascript:void(0)">SEO查询</a></li>
					<li><a  _href="http://tongji.cnzz.com/main.php?c=site&a=frame&siteid=1257989635" data-title="CNZZ统计" href="javascript:void(0)">CNZZ报表</a></li>
				</ul>
			</dd>
		</dl>
		<dl id="menu-picture">
			<dt><i class="Hui-iconfont">&#xe613;</i> 素材管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a _href="uploadfile/file_manager" data-title="图片管理" href="javascript:void(0);">图片管理</a></li>
					<li><a _href="http://www.baidu.com" data-title="图片管理" href="javascript:void(0)">视频管理</a></li>
				</ul>
			</dd>
		</dl>
		<dl id="menu-comments">
			<dt><i class="Hui-iconfont">&#xe622;</i> 评论管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a _href="${x:getValueFromCache('duoshuo.admin') }" data-title="评论列表" href="javascript:;">评论列表</a></li>
				</ul>
			</dd>
		</dl>
		<dl id="menu-member">
			<dt><i class="Hui-iconfont">&#xe60d;</i> 会员管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a _href="system/people/index" data-title="用户列表" href="javascript:void(0)">用户列表</a></li>
				</ul>
			</dd>
		</dl>
		<dl id="menu-admin">
			<dt><i class="Hui-iconfont">&#xe62d;</i> 管理员管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a _href="system/sysuser/index" data-title="管理员管理" href="javascript:void(0)">管理员管理</a></li>
				</ul>
			</dd>
		</dl>
		<dl id="menu-system">
			<dt><i class="Hui-iconfont">&#xe62e;</i> 系统管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a _href="system/cache" data-title="缓存管理" href="javascript:void(0)">缓存管理</a></li>
					<li><a _href="system/code/index" data-title="系统常量" href="javascript:void(0)">系统常量</a></li>
					<li><a _href="system/dict/index" data-title="数据字典" href="javascript:void(0)">数据字典</a></li>
					<li><a _href="admin/druid/index.html" data-title="数据库连接池" href="javascript:void(0)">数据库连接池</a></li>
				</ul>
			</dd>
		</dl>
	</div>
</aside>
<div class="dislpayArrow hidden-xs"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a></div>
<section class="Hui-article-box">
	<div id="Hui-tabNav" class="Hui-tabNav hidden-xs">
		<div class="Hui-tabNav-wp">
			<ul id="min_title_list" class="acrossTab cl">
				<li class="active"><span title="我的桌面" data-href="system/index/welcome">我的桌面</span><em></em></li>
			</ul>
		</div>
		<div class="Hui-tabNav-more btn-group"><a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a><a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a></div>
	</div>
	<div id="iframe_box" class="Hui-article">
		<div class="show_iframe">
			<div style="display:none" class="loading"></div>
			<iframe scrolling="yes" frameborder="0" src="system/index/welcome"></iframe>
		</div>
	</div>
		<div  id="div_content" style="padding: 20px;background-color: #fff;border: 20px solid #f4f4f4;min-height: 90%"></div>
</section>
<script type="text/javascript" src="static/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="static/lib/layer/2.1/layer.js"></script> 
<script type="text/javascript" src="static/h-ui/js/H-ui.js"></script> 
<script type="text/javascript" src="static/h-ui/js/H-ui.admin.js"></script> 
<script type="text/javascript">
function loadPage(url){
	$("#iframe_box").hide();
	$("#Hui-tabNav").hide();
	$("#div_content").load(url,function(){});
}
/*资讯-添加*/
function showFull(title,url){
	var index = layer.open({
		type: 2,
		title: title,
		content: url
	});
	layer.full(index);
}
function newOpen(obj){
	window.open(obj.attr("_href"),"_blank");
}
</script> 
</body>
</html>