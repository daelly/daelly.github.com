<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<layout:override name="head">
<title>首页内容数据</title>
<style type="text/css">
 .table td{font-size: 12px!important;}
</style>
<layout:head layer="true" icheck="true"></layout:head>
</layout:override>
<layout:override name="content">
<form name="searchForm" id="searchForm" method="post">
	<input type="hidden" name="order_sort" value="desc"/>
	<!-- <input type="hidden" name="order_create_time" value="desc"/>  -->
	<%-- <input type="hidden" name="folder_id" value="${param.folder_id}" />  --%>
	<div class="page-container">
		<div class="cl pb-10"> 
			<span class="l">
				<span class="title"><i class="Hui-iconfont">&#xe6f3;</i>&nbsp;内容管理</span>
				<input type="text" class="input-text radius" style="width:150px" placeholder="标题"" name="search_LIKE_title">
				&nbsp;&nbsp;
				<input type="hidden"name="status" value="1"/>
				<input type="hidden"name="is_recommend" value="1"/>
				<input type="checkbox" onclick="loadDataList()" name="is_banner" value="1"/>焦点&nbsp;
				&nbsp;&nbsp;
				<a class="btn btn-primary-outline radius" href="javascript:loadDataList();" onclick=""><i class="Hui-iconfont">&#xe709;</i>搜索</a> 
				<a class="btn btn-primary radius" href="javascript:;" onclick="fullShow('新增','system/article/edit','1001')"><i class="Hui-iconfont">&#xe600;</i>新增</a>
				<button class="btn btn-danger-outline radius" type="button" onclick="commonBatchDel('system/article/batchDelete')">批量删除</button>
				<a class="btn btn-link radius" href="system/article/updateHomeCache"  target="_blank"><i class="Hui-iconfont">&#xe68f;</i>更新首页推荐和banner缓存</a> 
				<a class="btn btn-link radius" href="system/article/updateHomeCityCache"  target="_blank"><i class="Hui-iconfont">&#xe68f;</i>更新首页栏目缓存(各地区)</a> 
			</span>
		 </div>
		<table class="table table-border table-bordered table-hover table-bg" id="tableHead">
			<thead>
				<tr class="text-c">
					<th width="20"><input type="checkbox"  value="" name=""></th>
					<th width="50">排序</th>
					<th width="45">ID</th>
					<th>标题</th>
					<th width="50">发布者</th>
					<th width="30">点击</th>
					<th width="50">评论量</th>
					<th width="70">创建时间</th>
					<th width="107">发布时间</th>
					<th width="30">状态</th>
					<th width="80">操作</th>
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
			<td><input type="text" name="sort_【id】" id="sort_【id】" onchange="sort(【id】)" value='【sort】' class="input-text radius"/></td>
			<td><a title="编辑" target="_blank" href="system/article/edit/【id】" style="text-decoration:none">【id】</a></td>
			<td  style="text-align: left;">
				<b>[<a href="javascript:;" onclick="fullShow('编辑','system/article/fieldsSelect/【id】')">&nbsp;@【folderNameFun(item.folderName)】</a>]</b>
				@【recommendFun(item.is_recommend)】@【bannerFun(item.is_banner)】 <a style="color:#5a98de" target="_blank" href="view/【id】">【title】</a>
			</td>
			<td>【publish_user】</td>
			<td>【count_view】</td>
			<td>【count_comment】</td>
			<td>@【timeFormatter(item.create_time,'yyyy-MM-dd')】</td>
			<td>@【timeFormatter(item.publish_time,'yyyy-MM-dd HH:mm')】</td>
			<td>@【statusFun(item.status)】</td>
			<td class="f-14"><a title="编辑" target="_blank" href="system/article/edit_content/【id】" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a>&nbsp;&nbsp;<a title="编辑" href="javascript:;" onclick="fullShow('编辑','system/article/edit/【id】')" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a> <a title="删除" href="javascript:;" onclick="commonDel('system/article/delete/【id】')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a></td>
		</tr>
	</script>
</layout:override>

<layout:override name="script">
	<script type="text/javascript">
		$(function(){
			$("body").css("border-bottom","0");
			$("body").css("border-top","0");
			commonLoadDataList("system/article/list");
		});
		function loadDataList(){
			commonLoadDataList("system/article/list");
		}
		function sort(id){
			$.get("system/article/updateSort?id="+id+"&sort="+$("#sort_"+id).val()+"",function(result){
				layer.msg("更新成功!");
				loadDataList();
			});
			
		}
		function bannerFun(banner){
			if(banner==1){
				return '<i class="Hui-iconfont" style="color:red;font-size:16px;">&#xe613;</i>';
			}else{
				return "";
			}
		}
		function recommendFun(status){
			if(status==1){
				return '<i class="Hui-iconfont" style="color:red;font-size:16px;">&#xe6c1;</i>';
			}else{
				return "";
			}
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
		
		function queryOrderBy(o){
		    var flag = o.val();
		    switch (flag) {
			case "0":
				$("#orderInput").attr("name","order_id");
				$("#orderInput").attr("value","desc");
				break;
			case "1":
				$("#orderInput").attr("name","order_id");
				$("#orderInput").attr("value","asc");
				break;
			case "2":
				$("#orderInput").attr("name","order_create_time");
				$("#orderInput").attr("value","desc");
				break;
			case "3":
				$("#orderInput").attr("name","order_create_time");
				$("#orderInput").attr("value","asc");
				break;
			case "4":
				$("#orderInput").attr("name","order_publish_time");
				$("#orderInput").attr("value","desc");
				break;
			case "5":
				$("#orderInput").attr("name","order_publish_time");
				$("#orderInput").attr("value","asc");
				break;
			case "6":
				$("#orderInput").attr("name","order_order");
				$("#orderInput").attr("value","desc");
				break;
			case "7":
				$("#orderInput").attr("name","order_order");
				$("#orderInput").attr("value","asc");
				break;
			case "8":
				$("#orderInput").attr("name","order_count_view");
				$("#orderInput").attr("value","desc");
				break;
			case "9":
				$("#orderInput").attr("name","order_count_view");
				$("#orderInput").attr("value","asc");
				break;
			default:
				$("#orderInput").attr("name","order_publish_time");
				$("#orderInput").attr("value","desc");
				break;
			}
		    loadDataList();
		}
	</script>
</layout:override>
<%@ include file="/commons/layout_admin.jsp" %>