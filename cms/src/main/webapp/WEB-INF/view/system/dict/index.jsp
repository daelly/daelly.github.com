<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<layout:override name="head">
<title>数据字典</title>
<layout:head sysTree="true" layer="true"/>
</layout:override>
<layout:override name="content">
<form name="searchForm" id="searchForm" method="post">
	<input type="hidden" name="order_create_time" value="desc"/>
	<div class="page-container">
		<div class="cl pb-10"> 
			<span class="l">
				<span class="title"><i class="Hui-iconfont">&#xe6f3;</i>&nbsp;数据字典</span>
				<input type="text" class="input-text radius" style="width:150px" placeholder="名称"" name="label">
				<input type="text" class="input-text radius" style="width:150px" placeholder="编号"  name="value">
				 <span class="select-box radius" style="width:150px">
					<db:select nullValue="false" styleClass="select" onchange="loadDataList()" name="type" dblabel="label" sql="select DISTINCT(type) label,type  from sys_dict" dbvalue="type"/>
				</span>
				 <%--
				  <x:dicttree inputTextName="typeName" treeId="type_id" pvalue="cms_theme" inputValueName="typeValue"/>
				  <span class="select-box radius" style="width:150px">
				 	<x:select pvalue="gen_java_type" firstoption="--请选择--"  name="type"/>
				 </span> --%>
				<a class="btn btn-primary-outline radius" href="javascript:loadDataList();" onclick=""><i class="Hui-iconfont">&#xe709;</i>搜索</a> 
				<a class="btn btn-primary radius" href="javascript:;" onclick="fullShow('编辑','system/dict/edit')"><i class="Hui-iconfont">&#xe600;</i>新增</a>
			</span>
		 </div>
		<table class="table table-border table-bordered table-hover table-bg" id="tableHead">
			<thead>
				<tr class="text-c">
					<th>序号</th>
					<th>名称</th>
					<th width="250">值</th>
					<th>类型名称</th>
					<th>排序</th>
					<th>备注</th>
					<th>创建时间</th>
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
			<td><span @【status(item.del_flag)】>【label】</span></td>
			<td>【value】</td>
			<td>【type】</td>
			<td>【sort】</td>
			<td>【description】</td>
			<td>【create_time】</td>
			<td class="f-14"><a title="编辑" href="javascript:;" onclick="fullShow('数据字典','system/dict/edit/【id】')" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a> <a title="删除" href="javascript:;" onclick="commonDel('system/dict/delete/【id】')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a></td>
		</tr>
	</script>
</layout:override>

<layout:override name="script">
	<script type="text/javascript">
		$(function(){
			commonLoadDataList("system/dict/list");
		});
		function loadDataList(){
			commonLoadDataList("system/dict/list");
		}
		function status(v){
			if(v==0){
				return "";
			}else{
				return "style='color:red'";
			}
		}
	</script>
</layout:override>
<%@ include file="/commons/layout_admin.jsp" %>