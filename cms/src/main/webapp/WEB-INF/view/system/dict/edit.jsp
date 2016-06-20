<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<layout:override name="head">
	<title>code数据</title>
	<layout:head layer="true" validForm="true"></layout:head>
</layout:override>
<layout:override name="content">
<article class="page-container">
	<form action="" method="post" class="form form-horizontal" id="form">
		<input type="hidden" name="sysDict.id" value="${sysDict.id }"/>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>参数label：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" datatype="*" value="${sysDict.label }" placeholder=""  name="sysDict.label">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>参数值：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" datatype="*" value="${sysDict.value }" placeholder=""  name="sysDict.value">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>排序：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${sysDict.sort }" placeholder=""  name="sysDict.sort">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>是否删除：</label>
			<div class="formControls col-xs-8 col-sm-9 skin-minimal">
				<x:radio pvalue="status" name="sysDict.del_flag" value="${sysDict.del_flag }"/>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">字典类型：</label>
			<div class="formControls col-xs-4 col-sm-4">
				<input type="text" class="input-text" style="display: none;" id="type2" placeholder=""  name="">
				 <span class="select-box" id="type0">
				 	<db:select styleClass="select"  value="${sysDict.type }" id="type1" name="sysDict.type" dblabel="label" sql="select DISTINCT(type) label,type  from sys_dict" dbvalue="type"/>
				</span>
			</div>
			<div class="formControls col-xs-4 col-sm-4">
				<button class="btn" type="button" onclick="changeType()">切换</button>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">描述：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<textarea name="sysDict.description"  class="textarea"  placeholder="说点什么...最少输入10个字符" onKeyUp="textarealength(this,100)">${ sysDict.description}</textarea>
				<p class="textarea-numberbar"><em class="textarea-length">0</em>/100</p>
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<input class="btn btn-primary radius" onclick="commonSubmit('system/dict/save')" type="button" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
			</div>
		</div>
	</form>
</article>
</layout:override>

<layout:override name="script">
	<script type="text/javascript">
		$(function(){
			
		});
		function changeType(){
			$("#type2").show();
			$("#type0").hide();
			$("#type1").attr("name","");
			$("#type2").attr("name","sysDict.type");
		}
	</script>
</layout:override>
<%@ include file="/commons/layout_admin.jsp" %>