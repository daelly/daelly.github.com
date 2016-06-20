<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<layout:override name="head" >
	<title>用户管理</title>
<layout:head layer="true"></layout:head>
</layout:override>
<layout:override name="content">
<form name="searchForm" id="searchForm" method="post">
	<div class="page-container">
		<div class="cl pb-10"> 
			<span class="l">
				<span class="title"><i class="Hui-iconfont">&#xe6f3;</i>&nbsp;用户管理</span>
				<input type="text" class="input-text radius" style="width:150px" placeholder="登录账号" name="login_name">
				<a class="btn btn-primary-outline radius" href="javascript:;" onclick="loadDataList()"><i class="Hui-iconfont">&#xe709;</i>搜索</a> 
				<a class="btn btn-primary radius" href="javascript:;" onclick="fullShow('新增用户','system/people/edit')"><i class="Hui-iconfont">&#xe600;</i>新增</a>
			</span>
		 </div>
		<table class="table table-border table-bordered table-hover table-bg" id="tableHead">
			<thead>
				<tr class="text-c">
					<th>序号</th>
					<th>登录账号</th>
					<th>真实姓名</th>
					<th>真实姓名</th>
					<th>邮箱</th>
					<th>状态</th>
					<th>电话号码</th>
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
			<td>【login_name】</td>
			<td>【realname】</td>
			<td>【mobile】</td>
			<td>【email】</td>
			<td>@【statusFun(item.state)】</td>
			<td>【mobile】</td>
			<td class="f-14"><a title="编辑" href="javascript:;" onclick="fullShow('栏目编辑','system/people/edit/【id】')" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a> <a title="删除" href="javascript:;" onclick="commonDel('system/people/delete/【id】')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a></td>
		</tr>
	</script>
</layout:override>

<layout:override name="script">
	<script type="text/javascript">
		$(function(){
			loadDataList();
		});
		function loadDataList(){
			commonLoadDataList("system/people/list");
		}
		function statusFun(status){
			if(status==1){
				return "启用";
			}else{
				return "禁止";
			}
		}
	</script>
</layout:override>
<%@ include file="/commons/layout_admin.jsp" %>