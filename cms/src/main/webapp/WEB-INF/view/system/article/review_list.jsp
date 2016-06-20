<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<layout:override name="head">
<title>内容数据</title>
<style type="text/css">
 .table td{font-size: 12px!important;}
</style>
<layout:head layer="true"></layout:head>
</layout:override>
<layout:override name="content">
<form name="searchForm" id="searchForm" method="post">
	<input type="hidden" id="orderInput" name="order_create_time" value="desc"/>
	<div class="page-container">
		<div class="cl pb-10"> 
			<span class="l">
				<span class="title"><i class="Hui-iconfont">&#xe6f3;</i>&nbsp;内容审核</span>
				<input type="text" class="input-text radius" style="width:150px" placeholder="标题"" name="search_LIKE_title">
				&nbsp;&nbsp;推荐：<div class="select-box radius" style="width: 100px">
					<select class="select" name="is_recommend">
						<option value="">全部</option>
						<option value="0">否</option>
						<option value="1">是</option>
					</select>
				</div>
				&nbsp;&nbsp;排序：<div class="select-box radius" style="width: 120px">
					<select class="select" onchange="queryOrderBy($(this));">
						<option value="2">创建时间降</option>
						<option value="3">创建时间升</option>
					</select>
				</div>
				<input type="hidden" name="status" value="0"/>
				<a class="btn btn-primary-outline radius" href="javascript:loadDataList();" onclick=""><i class="Hui-iconfont">&#xe709;</i>搜索</a> 
					<a class="btn btn-primary radius" href="javascript:;" onclick="fullShow('新增','system/article/edit','1001')"><i class="Hui-iconfont">&#xe600;</i>新增</a>
					<button class="btn btn-danger-outline radius" type="button" onclick="commonBatchDel('system/article/batchDelete')">批量删除</button>
					<button class="btn btn-secondary radius" type="button" onclick="batchReview('system/article/batchReview')"><i class="Hui-iconfont">&#xe6a7;</i>批量审核</button>
			</span>
		 </div>
		<table class="table table-border table-bordered table-hover table-bg" id="tableHead">
			<thead>
				<tr class="text-c">
					<th width="20"><input type="checkbox"  value=""></th>
					<th width="45">序号</th>
					<th>标题</th>
					<th>发布者</th>
					<td>创建时间</td>
					<th>发布时间</th>
					<th>排序</th>
					<th>状态</th>
					<th>阅读量</th>
					<th>推荐</th>
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
			<td><a title="编辑" target="_blank" href="system/article/edit/【id】" style="text-decoration:none">【id】</a></td>
			<td style="text-align: left;"><a href="javascript:;" onclick="fullShow('编辑','system/article/fieldsSelect/【id】')">[<b>@【folderNameFun(item.folderName)】</b>]</a><a style="color:#5a98de" target="_blank" href="view/【id】">【title】</a></td>
			<td>【publish_user】</td>
			<td>@【timeFormatter(item.create_time,'yyyy-MM-dd HH:mm')】</td>
			<td>@【timeFormatter(item.publish_time,'yyyy-MM-dd')】</td>
			<td>【sort】</td>
			<td>@【statusFun(item.status)】</td>
			<td>【count_view】</td>
			<td>@【recommendFun(item.is_recommend)】</td>
			<td class="f-14"><a title="编辑" href="javascript:;" onclick="fullShow('编辑','system/article/edit/【id】')" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a> <a title="删除" href="javascript:;" onclick="commonDel('system/article/delete/【id】')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a></td>
		</tr>
	</script>
</layout:override>

<layout:override name="script">
	<script type="text/javascript">
		$(function(){
			commonLoadDataList("system/article/list");
		});
		function loadDataList(){
			commonLoadDataList("system/article/list");
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
		
		function queryOrderBy(o){
		    var flag = o.val();
		    switch (flag) {
			case "2":
				$("#orderInput").attr("name","order_create_time");
				$("#orderInput").attr("value","desc");
				break;
			case "3":
				$("#orderInput").attr("name","order_create_time");
				$("#orderInput").attr("value","asc");
				break;
			default:
				$("#orderInput").attr("name","order_publish_time");
				$("#orderInput").attr("value","desc");
				break;
			}
		    loadDataList();
		}
		function batchReview(){
			layer.confirm('确认要审核通过所有选中的吗？',function(index){
				$.ajax({
					url: 'system/article/batchReview',
					type : "post",
					data: $("#searchForm").serialize(),
					success: function(result){
						if(result.code==0){
							layer.alert(result.message);
							loadDataList();
						}else{
							layer.msg('审核失败!');
						}
					}
				});
			});
		}
	</script>
</layout:override>
<%@ include file="/commons/layout_admin.jsp" %>