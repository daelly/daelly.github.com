<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<layout:override name="head">
<title>数据字典</title>
<layout:head sysTree="true" layer="true"/>
</layout:override>
<layout:override name="content">
<form name="searchForm" id="searchForm" method="post">
	<div class="page-container">
		<div class="cl pb-10"> 
			<span class="l">
				<span class="title"><i class="Hui-iconfont">&#xe6f3;</i>&nbsp;爬虫Job配置</span>
				<input type="text" class="input-text radius" style="width:150px" placeholder="名称"" name="search_LIKE_name">
				<input type="text" class="input-text radius" style="width:150px" placeholder="网址"  name="search_LIKE_website">
				<a class="btn btn-primary-outline radius" href="javascript:loadDataList();" onclick=""><i class="Hui-iconfont">&#xe709;</i> 搜索</a> 
				<a class="btn btn-primary radius" href="javascript:;" onclick="fullShow('编辑','system/spider_job/edit')"><i class="Hui-iconfont">&#xe600;</i> 新增</a>
				<a class="btn btn-primary radius" href="javascript:;" onclick="runSelected()"><i class="Hui-iconfont">&#xe6e6;</i> 执行选择</a>
			</span>
		 </div>
		<table class="table table-border table-bordered table-hover table-bg" id="tableHead">
			<thead>
				<tr class="text-c">
					<th><input type="checkbox" id="select_all" /></th>
					<th width="30">序号</th>
					<th>名称</th>
					<th>网址</th>
					<th>标题规则</th>
					<th>内容规则</th>
					<th width="90">操作</th>
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
			<td><input type="checkbox" name="sub" value="【id】" /></td>
			<td>【_i】</td>
			<td>【job_name】</td>
			<td>【url_page】</td>
			<td>【links_rule】</td>
			<td><button type="button" onclick="fullShow('数据字典','system/spider_rule/edit/【spide_rule_id】')" class="btn">查看规则</button></td>
			<td class="f-14">
				<a title="编辑" href="javascript:;" onclick="fullShow('数据字典','system/spider_job/edit/【id】')" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a> 
				<a title="删除" href="javascript:;" onclick="commonDel('system/dict/delete/【id】')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a>
				<a title="执行" href="javascript:void(0)" onclick="runJob('【id】')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e6;</i></a>
			</td>
		</tr>
	</script>
</layout:override>

<layout:override name="script">
	<script type="text/javascript">
		$(function(){
			loadDataList();
			$("#select_all").on("click",function(){
				if($(this).is(":checked")){
					$("#dataList input[name='sub']").attr("checked",true);
				}else{
					$("#dataList input[name='sub']").removeAttr("checked");
				}
			});
		});
		function loadDataList(){
			commonLoadDataList("system/spider_job/list");
		}
		
		function runJob(id){
			$.ajax({
				url: "system/spider_job/run/"+id,
				type : "post",
				data: {ids:id},
				dataType:"json",
				success: function(result){
					if(result.code==0){
						layer.msg('作业启动成功');
					}else{
						layer.msg('作业启动失败');
					}
				},error:function(){
					layer.msg("网络繁忙，请稍后再试");
				}
			});
		}
		
		function runSelected(){
			var ids = "";
			$("#dataList input[name='sub']").each(function(i){
				if($(this).is(":checked")){
					if(ids.length>0)
						ids += ",";
					ids += $(this).val();
				}
			});
			if(ids.length > 0){
				$.ajax({
					url: "system/spider_job/run/",
					type : "post",
					data: {ids:ids},
					dataType:"json",
					success: function(result){
						if(result.code==0){
							layer.msg('作业启动成功');
						}else{
							layer.msg('作业启动失败');
						}
					},error:function(){
						layer.msg("网络繁忙，请稍后再试");
					}
				});
			}else{
				layer.msg("请选择要执行的作业");
			}
		}
	</script>
</layout:override>
<%@ include file="/commons/layout_admin.jsp" %>