<%@page import="com.redsea.model.SysUser"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<layout:override name="head">
<title>内容数据</title>
<style type="text/css">
 .table td{font-size: 12px!important;}
</style>
<layout:head layer="true" icheck="true"></layout:head>
</layout:override>
<layout:override name="content">
<form name="searchForm" id="searchForm" method="post">
	<input type="hidden" id="orderInput" name="order_publish_time" value="desc"/>
	<input type="hidden" name="order_create_time" value="desc"/>
	<%-- <input type="hidden" name="folder_id" value="${param.folder_id}" /> --%>
	<div class="page-container">
		<div class="cl pb-10"> 
			<span class="l">
				<span class="title"><i class="Hui-iconfont">&#xe6f3;</i>&nbsp;内容管理</span>
				<input type="text" class="input-text radius" style="width:120px" placeholder="标题" name="search_LIKE_title">
				<input type="text" class="input-text radius" style="width:50px" placeholder="ID" name="id">
				&nbsp;&nbsp;
				<input type="checkbox"name="is_recommend" value="1"/>推荐&nbsp;&nbsp;
				<input type="checkbox" onclick="loadDataList()" name="is_banner" value="1"/>焦点&nbsp;&nbsp;
				<input type="checkbox" onclick="loadDataList()" name="create_by" value="<%=((SysUser)request.getSession().getAttribute("session_user")).getUserid() %>"/>创建人&nbsp;
				<input type="checkbox" onclick="loadDataList()" name="update_by" value="<%=((SysUser)request.getSession().getAttribute("session_user")).getUserid() %>"/>修改人&nbsp;
				<input type="checkbox"name="status" value="1" onclick="loadDataList()" checked="checked"/>已审核
				<span class="select-box" style="width: 100px;display: none;">
						<db:select onchange="loadDataList()"  styleClass="select" dblabel="name" name="folder_id" value="${param.folder_id}" dbvalue="id" sqlKey="folder_all"/>
					</span> 
				&nbsp;&nbsp;排序：<div class="select-box radius" style="width: 100px">
					<select class="select" name="status" onchange="queryOrderBy($(this));">
						<option value="0">ID降序</option>
						<option value="1">ID升序</option>
						<option value="2">创建时间降</option>
						<option value="3">创建时间升</option>
						<option value="4">发布时间降</option>
						<option value="5">发布时间升</option>
						<option value="6">排序值降</option>
						<option value="7">排序值升</option>
						<option value="8">点击量降</option>
						<option value="9">点击量升</option>
						<option value="10">评论量降</option>
						<option value="11">评论量升</option>
					</select>
				</div>
				<a class="btn btn-primary-outline radius" href="javascript:loadDataList();" onclick=""><i class="Hui-iconfont">&#xe709;</i>搜索</a> 
				<a class="btn btn-primary radius" target="_blank" href="system/article/edit"><i class="Hui-iconfont">&#xe600;</i>新增</a>
				<button class="btn btn-danger-outline radius" type="button" onclick="commonBatchDel('system/article/batchDelete')">批量删除</button>
				<button class="btn btn-primary-outline radius" type="button" onclick="push()">百度推送</button>
			</span>
		 </div>
		<table class="table table-border table-bordered table-hover table-bg" id="tableHead">
			<thead>
				<tr class="text-c">
					<th width="20"><input type="checkbox"  value="" name=""></th>
					<th width="43">ID</th>
					<th>标题</th>
					<th width="50">发布者</th>
					<th width="30">点击</th>
					<th width="50">评论量</th>
					<th width="70">创建时间</th>
					<th width="110">发布时间</th>
					<th width="30">排序</th>
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
			<td><a target="_blank" style="color:#5a98de" href="system/article/edit/【id】">【id】</a></td>
			<td  style="text-align: left;">
				<b>[<a href="javascript:;" onclick="fullShow('编辑','system/article/fieldsSelect/【id】')">&nbsp;@【folderNameFun(item.folderName)】</a>]</b>
				@【recommendFun(item.is_recommend)】@【bannerFun(item.is_banner)】 <a style="color:#5a98de" target="_blank" href="view/【id】">【title】</a>
			</td>
			<td>【publish_user】</td>
			<td>【count_view】</td>
			<td>【count_comment】</td>
			<td>@【timeFormatter(item.create_time,'yyyy-MM-dd')】</td>
			<td>@【timeFormatter(item.publish_time,'yyyy-MM-dd HH:mm')】</td>
			<td>【sort】</td>
			<td>@【statusFun(item.status)】</td>
			<td class="f-14"><a title="只编辑标题和内容" target="_blank" href="system/article/edit_content/【id】" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a>&nbsp;&nbsp;<a title="内部打开编辑" href="javascript:;" onclick="fullShow('编辑','system/article/edit/【id】')" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a> <a title="删除" href="javascript:;" onclick="commonDel('system/article/delete/【id】')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a></td>
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
			case "10":
				$("#orderInput").attr("name","order_count_comment");
				$("#orderInput").attr("value","desc");
				break;
			case "11":
				$("#orderInput").attr("name","order_count_comment");
				$("#orderInput").attr("value","asc");
				break;
			default:
				$("#orderInput").attr("name","order_publish_time");
				$("#orderInput").attr("value","desc");
				break;
			}
		    loadDataList();
		}
		
		function push(){
			var ids = [];
			$("#dataList input[name='ids']").each(function(i){
				if($(this).is(":checked")){
					ids.push($(this).val());
				}
			});
			if(ids.length<=0){
				layer.msg("请选择要推送的文章");
				return;
			}
    		$.ajax({
    			url: "system/article/push",
    			type : "post",
    			dataType : "json",
    			data: {ids:ids.join()},
    			success: function(result){
    				if(result.code==0){
    					var rs = result.data;
    					if(rs.error){
    						layer.msg(rs.message);
    					}else{
    						var not_same_site = rs.not_same_site;
    						var not_valid = rs.not_valid;
    						var success = rs.success;
    						var remain = rs.remain;
    						var msg = "<div style='margin-left:20px;margin-top:20px;'>";
    						if(success > 0){
    							msg += "成功推送"+success+"条<br>";
    						}
    						if(not_same_site && not_same_site.length>0){
    							msg += "<font style='color:red;'>"
    							msg += not_same_site.length+"条非本站url导致失败：<br>";
    							for(i in not_same_site){
    								msg += not_same_site[i]+"<br>";
    							}
    							msg += "</font>";
    						}
    						if(not_valid && not_valid.length>0){
    							msg += "<font style='color:red;'>"
    							msg += not_valid.length+"条非法url导致失败：<br>";
    							for(i in not_valid){
    								msg += not_valid[i]+"<br>";
    							}
    							msg += "</font>";
    						}
    						msg += "今日剩余："+remain+"条可推送";
    						msg += "</div>";
    						layer.open({
   							  type: 1,
   							  title: "推送结果",
   							  closeBtn: 1,
   							  shadeClose: true,
   							  area: ['420px', '240px'], //宽高
   							  offset: '100px',
   							  skin: 'layui-layer-rim', //加上边框
   							  content: msg
   							});
    					}
    				}else{
    					layer.msg(result.message);
    				}
    			},error:function(){
    				layer.msg("系统繁忙");
    			}
    		});
		}
	</script>
</layout:override>
<%@ include file="/commons/layout_admin.jsp" %>