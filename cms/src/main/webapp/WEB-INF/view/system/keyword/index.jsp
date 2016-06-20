<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<layout:override name="head" >
<title>关键字</title>
<layout:head layer="true"></layout:head>
</layout:override>
<layout:override name="content">
<form name="searchForm" id="searchForm" method="post">
	<input type="hidden" name="order_search_index" value="desc" />
	<div class="page-container">
		<div class="cl pb-10"> 
			<span class="l">
				<span class="title"><i class="Hui-iconfont">&#xe6f3;</i>&nbsp;关键字管理</span>
				<input type="text" class="input-text radius" style="width:150px" placeholder="关键字名称" name="keyword">
				<a class="btn btn-primary-outline radius" href="javascript:;" onclick="loadDataList()"><i class="Hui-iconfont">&#xe709;</i>搜索</a> 
				<!-- <a class="btn btn-primary radius" href="javascript:;" onclick="fullShow('新增关键字','system/folder/edit')"><i class="Hui-iconfont">&#xe600;</i>新增</a> -->
				<a class="btn btn-primary radius" href="system/keyword/updateAllIndex" target="_blank"><i class="Hui-iconfont">&#xe68f;</i>更新所有指数</a>
				<a class="btn btn-primary radius" href="system/keyword/getSubKeyword" target="_blank" title="获取一级关键字的推荐、相关关键字"><i class="Hui-iconfont">&#xe68f;</i>获取子关键字</a>
			</span>
		 </div>
		<table class="table table-border table-bordered table-hover table-bg" id="tableHead">
			<thead>
				<tr class="text-c">
					<th>序号</th>
					<th>关键字名称</th>
					<th>搜索指数</th>
					<th>媒体关注度</th>
					<th>最后更新时间</th>
					<th>类型</th>
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
			<td>【keyword】</td>
			<td>【search_index】</td>
			<td>【media_index】</td>
			<td>【update_time】</td>
			<td>【type】</td>
			<td class="f-14">
				<%--<a title="编辑" href="javascript:;" onclick="fullShow('栏目编辑','system/folder/edit/【id】')" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a>--%> 
				<a title="删除" href="javascript:;" onclick="commonDel('system/folder/delete/【id】')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a>
				<a title="更新指数" href="javascript:;" onclick="updateIndex('【id】')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe68f;</i></a>				
			</td>
		</tr>
	</script>
</layout:override>

<layout:override name="script">
	<script type="text/javascript">
		$(function(){
			commonLoadDataList("system/keyword/list");
		});
		function loadDataList(){
			commonLoadDataList("system/keyword/list");
		}
		
		function updateAllIndexes(){
			layer.msg("关键字数量较多,你可能需要耐心等待些时间");
			$.ajax({
				   type: "POST",
				   dataType:"json",
				   url: "system/keyword/updateIndexes",
				   success: function(rs){
						if(rs.code == "0"){
							layer.msg("更新成功");
							commonLoadDataList("system/keyword/list");
						}else{
							layer.msg(rs.message);
						}
				   },
				   error: function(){
					   layer.msg("系统繁忙，请联系客服");
				   }
					   
				});
		}
		
		function updateIndex(id){
			$.ajax({
				   type: "POST",
				   dataType:"json",
				   url: "system/keyword/updataIndex",
				   data: {id:id},
				   success: function(rs){
						if(rs.code == "0"){
							layer.msg("更新成功");
							commonLoadDataList("system/keyword/list");
						}else{
							layer.msg(rs.message);
						}
				   },
				   error: function(){
					   layer.msg("系统繁忙，请联系客服");
				   }
					   
				});
		}
	</script>
</layout:override>
<%@ include file="/commons/layout_admin.jsp" %>