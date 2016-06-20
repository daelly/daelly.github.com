<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<layout:override name="head">
<title>code数据</title>
<style type="text/css">
 .table td{font-size: 12px!important;}
</style>
<layout:head layer="true" validForm="true" />
</layout:override>
<layout:override name="content">
<form name="searchForm" id="searchForm" method="post">
	<input type="hidden" name="order_create_time" value="desc"/>
	<div class="page-container">
		<div class="cl pb-10"> 
			<span class="l">
				<span class="title"><i class="Hui-iconfont">&#xe6f3;</i>&nbsp;code数据</span>
				<input type="text" class="input-text radius" style="width:150px" placeholder="名称"" name="label">
				<input type="text" class="input-text radius" style="width:150px" placeholder="编号"  name="value">
				<a class="btn btn-primary-outline radius" href="javascript:loadDataList();" onclick=""><i class="Hui-iconfont">&#xe709;</i>搜索</a> 
				<a class="btn btn-primary radius" href="javascript:;" onclick="fullShow('新增Code','system/code/edit')"><i class="Hui-iconfont">&#xe600;</i>新增</a>
				<a class="btn btn-link radius" href="system/cache/deleteCacheByKey/constantsCodeCache-codeTableKey" target="_blank">更新缓存</a>
			</span>
		 </div>
		<table class="table table-border table-bordered table-hover table-bg" id="tableHead">
			<thead>
				<tr class="text-c">
					<th width="30">序号</th>
					<th>参数key</th>
					<th width="50">类型</th>
					<th style="max-width: 300px">参数value</th>
					<th>备注</th>
					<th width="120">创建时间</th>
					<th width="60">操作</th>
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
			<td>【paramname】</td>
			<td>【paramtype】</td>
			<td>【paramvalue】</td>
			<td>【remark】</td>
			<td>【create_time】</td>
			<td class="f-14"><a title="编辑" href="javascript:;" onclick="fullShow('Code编辑','system/code/edit/【id】')" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a> <a title="删除" href="javascript:;" onclick="commonDel('system/code/delete/【id】')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a></td>
		</tr>
	</script>
</layout:override>

<layout:override name="script">
	<script type="text/javascript">
		$(function(){
			loadDataList();
		});
		function loadDataList(){
			commonLoadDataList("system/code/list");
		}
	</script>
</layout:override>
<%@ include file="/commons/layout_admin.jsp" %>