<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<layout:override name="head">
<title>数据字典</title>
<layout:head sysTree="true" layer="true"/>
<style type="text/css">
 .table td{font-size: 12px!important;}
</style>
</layout:override>
<layout:override name="content">
<form name="searchForm" id="searchForm" method="post">
	<div class="page-container">
		<div class="cl pb-10"> 
			<span class="l">
				<span class="title"><i class="Hui-iconfont">&#xe6f3;</i>&nbsp;爬虫配置</span>
				<input type="text" class="input-text radius" style="width:150px" placeholder="名称"" name="search_LIKE_name">
				<input type="text" class="input-text radius" style="width:150px" placeholder="网址"  name="search_LIKE_website">
				<a class="btn btn-primary-outline radius" href="javascript:loadDataList();" onclick=""><i class="Hui-iconfont">&#xe709;</i>搜索</a> 
				<a class="btn btn-primary radius" href="javascript:;" onclick="fullShow('编辑','system/spider_rule/edit')"><i class="Hui-iconfont">&#xe600;</i>新增</a>
			</span>
		 </div>
		<table class="table table-border table-bordered table-hover table-bg" id="tableHead">
			<thead>
				<tr class="text-c">
					<th width="30">序号</th>
					<th>名称</th>
					<th>网址</th>
					<th>标题规则</th>
					<th>内容规则</th>
					<th>来源规则</th>
					<th>作者规则</th>
					<th>发布时间规则</th>
					<th>摘要规则</th>
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
			<td>【name】</td>
			<td>【website】</td>
			<td>【title_rule】</td>
			<td>【content_rule】</td>
			<td>【origin_rule】</td>
			<td>【publish_user_rule】</td>
			<td>【publish_time_rule】</td>
			<td>【summary_rule】</td>
			<td class="f-14"><a title="爬数据" href="javascript:;" onclick="fullShow('数据字典','system/spider_rule/spider?rule_id=【id】')"><i class="Hui-iconfont">&#xe604;</i></a> <a title="编辑" href="javascript:;" onclick="fullShow('数据字典','system/spider_rule/edit/【id】')" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a> <a title="删除" href="javascript:;" onclick="commonDel('system/dict/delete/【id】')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a></td>
		</tr>
	</script>
</layout:override>

<layout:override name="script">
	<script type="text/javascript">
		$(function(){
			loadDataList();
		});
		function loadDataList(){
			commonLoadDataList("system/spider_rule/list");
		}
	</script>
</layout:override>
<%@ include file="/commons/layout_admin.jsp" %>