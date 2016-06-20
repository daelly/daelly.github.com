<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<layout:override name="head" >
<title>栏目</title>
<layout:head layer="true"></layout:head>
</layout:override>
<layout:override name="content">
<form name="searchForm" id="searchForm" method="post">
	<input type="hidden" name="parent_id" value="${param.parent_id}" />
	<div class="page-container">
		<div class="cl pb-10"> 
			<span class="l">
				<span class="title"><i class="Hui-iconfont">&#xe6f3;</i>&nbsp;栏目管理</span>
				<input type="text" class="input-text radius" style="width:150px" placeholder="栏目名称" name="name">
				<a class="btn btn-primary-outline radius" href="javascript:;" onclick="loadDataList()"><i class="Hui-iconfont">&#xe709;</i>搜索</a> 
				<a class="btn btn-primary radius" href="javascript:;" onclick="fullShow('新增栏目','system/folder/edit')"><i class="Hui-iconfont">&#xe600;</i>新增</a>
				<a class="btn btn-link radius" href="system/folder/updateCache"  target="_blank"><i class="Hui-iconfont">&#xe68f;</i>更新头部菜单栏目缓存</a>
				<a class="btn btn-link radius" href="system/folder/updateCacheForHomeColumns"  target="_blank"><i class="Hui-iconfont">&#xe68f;</i>更新首页内容栏目缓存</a>
			</span>
		 </div>
		<table class="table table-border table-bordered table-hover table-bg" id="tableHead">
			<thead>
				<tr class="text-c">
					<th>序号</th>
					<th>名称</th>
					<th>标题</th>
					<th>父栏目</th>
					<th>排序</th>
					<th>状态</th>
					<th>跳转地址</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody id="dataList">
				
			</tbody>
			
		</table>
		<div class="pageblock">
	  		<jsp:include page="/commons/include/admin/admin_pagination.jsp" />
		</div>
	</div>
</form>
	<script type="text/template" id="listTemplate">
		<tr class="text-c">
			<td>【_i】</td>
			<td>【name】</td>
			<td>【title】</td>
			<td>【parentName】</td>
			<td>【sort】</td>
			<td>@【statusFun(item.status)】</td>
			<td>【path】</td>
			<td class="f-14"><a title="编辑" href="javascript:;" onclick="fullShow('栏目编辑','system/folder/edit/【id】')" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a> <a title="删除" href="javascript:;" onclick="commonDel('system/folder/delete/【id】')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a></td>
		</tr>
	</script>
</layout:override>

<layout:override name="script">
	<script type="text/javascript">
		$(function(){
			commonLoadDataList("system/folder/list");
			$("body").css("border-bottom","0");
			$("body").css("border-top","0");
		});
		function loadDataList(){
			commonLoadDataList("system/folder/list");
		}
		function statusFun(status){
			if(status==1){
				return "显示";
			}else{
				return "<font color='red'>隐藏</font>";
			}
		}
	</script>
</layout:override>
<%@ include file="/commons/layout_admin.jsp" %>