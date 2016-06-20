<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<layout:override name="head">
<title>专题管理</title>
<style type="text/css">
 .table td{font-size: 12px!important;}
</style>
<layout:head layer="true"></layout:head>
</layout:override>
<layout:override name="content">
<form name="searchForm" id="searchForm" method="post">
	<input type="hidden" name="order_publish_time" value="desc"/>
	<input type="hidden" name="order_create_time" value="desc"/>
	<div class="page-container">
		<div class="cl pb-10"> 
			<span class="l">
				<span class="title"><i class="Hui-iconfont">&#xe6f3;</i>&nbsp;专题管理</span>
				<input type="text" class="input-text radius" style="width:150px" placeholder="标题"" name="search_LIKE_title">
				&nbsp;&nbsp;审核：<div class="select-box radius" style="width: 100px">
					<select class="select" name="status">
						<option value="">全部</option>
						<option value="0">否</option>
						<option value="1">是</option>
					</select>
				</div>
				<a class="btn btn-primary-outline radius" href="javascript:loadDataList();" onclick=""><i class="Hui-iconfont">&#xe709;</i>搜索</a> 
				<a class="btn btn-primary radius" href="javascript:;" onclick="fullShow('新增','system/topic/add','1001')"><i class="Hui-iconfont">&#xe600;</i>新增</a>
				<a class="btn btn-link radius" href="system/topic/removeCache" target="_blank">清除缓存</a>
			</span>
		 </div>
		<table class="table table-border table-bordered table-hover table-bg" id="tableHead">
			<thead>
				<tr class="text-c">
					<th width="20"><input type="checkbox"  value="" name=""></th>
					<th width="80">ID</th>
					<th>标题</th>
					<th width="120">创建时间</th>
					<th width="120">发布时间</th>
					<th width="50">状态</th>
					<th width="120">操作</th>
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
			<td><input type="checkbox" name="ids" value="【id】"></td>
			<td><a target="_blank" href="system/topic/edit/【id】">【id】</a></td>
			<td  style="text-align: left;">
				<a style="color:#5a98de" target="_blank" href="topic/【id】"><b>[【template】]</b>【title】</a>
			</td>
			<td>@【timeFormatter(item.create_time,'yyyy-MM-dd')】</td>
			<td>@【timeFormatter(item.publish_time,'yyyy-MM-dd')】</td>
			<td>@【statusFun(item.status)】</td>
			<td class="f-14"><a title="编辑" target="_blank" href="system/topic/edit/【id】" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a> <a title="删除" href="javascript:;" onclick="commonDel('system/topic/delete/【id】')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a></td>
		</tr>
	</script>
</layout:override>

<layout:override name="script">
	<script type="text/javascript">
		$(function(){
			commonLoadDataList("system/topic/list");
		});
		function loadDataList(){
			commonLoadDataList("system/topic/list");
		}
		function folderNameFun(name){
			if(name==null||name==''){
				return '<font color="red">修改</font>';
			}else{
				return name;
			}
		}
		function statusFun(status){
			if(status==1){
				return "显示";
			}else{
				return "<font color='red'>隐藏</font>";
			}
		}
		function recommendFun(status){
			if(status==1){
				return "<font color='red'>是</font>";
			}else{
				return "否";
			}
		}
	</script>
</layout:override>
<%@ include file="/commons/layout_admin.jsp" %>