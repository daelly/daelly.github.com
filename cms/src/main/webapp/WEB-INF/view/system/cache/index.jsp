<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<layout:override name="head" >
	<title>缓存管理</title>
	<layout:head layer="true"></layout:head>
</layout:override>
<layout:override name="content">
<form name="searchForm" id="searchForm" method="post">
	<div class="page-container">
		<table class="table table-border table-bordered table-hover table-bg" id="tableHead">
			<thead>
				<tr>
					<td colspan="4">
					<!-- <button class="btn btn-danger-outline radius" type="button" onclick="batchDeleteCacheByCacheName()">选中删除</button> -->
					<a class="btn btn-link radius" href="system/cache/deleteAllCache" target="_blank">清除所有缓存</a>
					<a class="btn btn-link radius" href="system/cache/cacheList" target="_blank">缓存明细</a>
					<a class="btn btn-link radius" href="system/cache/buildSiteMap" target="_blank">更新sitemap</a>
					</td>
				</tr>
				<tr class="text-c">
					<th width="200">缓存名称</th>
					<th>缓存明细</th>
				</tr>
			</thead>
			<tbody id="dataList">
				${html}
			</tbody>
			
		</table>
	</div>
</form>
</layout:override>

<layout:override name="script">
	<script type="text/javascript">
		function statusFun(status){
			if(status==1){
				return "启用";
			}else{
				return "禁止";
			}
		}
		function batchDeleteCacheByCacheName(){
			layer.confirm('删除须谨慎，确认要删除选中的吗？',function(index){
				$.ajax({
					url: 'system/cache/batchDeleteCacheByCacheName',
					type : "post",
					dataType:'text',
					data: $("#searchForm").serialize(),
					success: function(result){
						layer.msg(result);
					}
				});
			});
		}
	</script>
</layout:override>
<%@ include file="/commons/layout_admin.jsp" %>