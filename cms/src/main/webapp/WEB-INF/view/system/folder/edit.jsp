<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<layout:override name="head">
	<title>栏目数据</title>
	<layout:head layer="true" validForm="true"></layout:head>
</layout:override>
<layout:override name="content">
<article class="page-container">
	<form action="" method="post" class="form form-horizontal" id="form">
		<input type="hidden" name="folder.id" value="${folder.id }"/>
		<input type="hidden" name="order_create_time" value="desc"/>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>栏目名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" datatype="*"  value="${folder.name }"   name="folder.name">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>栏目英文名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" datatype="*"  value="${folder.en_name }"   name="folder.en_name">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>排序：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" datatype="*"  value="${folder.sort }"   name="folder.sort">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">路径：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${folder.path }"   name="folder.path">
			</div>
		</div>
		<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>父栏目：</label>
				<div class="formControls col-xs-8 col-sm-9"> 
					<span class="select-box">
						<db:select headerKey="0" nullValue="false"  headerValue="--请选择--"  styleClass="select"  dblabel="name" name="folder.parent_id" value="${folder.parent_id}${param.parent_id}" dbvalue="id" sql="SELECT * from tbl_folder where `status`=1 and parent_id=0"/>
					</span> 
				</div>
			</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>头部是否显示：</label>
			<div class="formControls col-xs-8 col-sm-9">
				 	<x:radio pvalue="status" name="folder.status" value="${folder.status}"/>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">数据类型：</label>
			<div class="formControls col-xs-8 col-sm-9">
				 <span class="select-box">
					<select class="select" size="1" name="folder.type" value="${folder.type}">
						<option value="1">普通目录</option>
						<option value="2">a标签</option>
						<option value="3">a标签_blank</option>
						<option value="4">直接加载url信息</option>
					</select>
				</span> 
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">首页显示：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<x:radio pvalue="status" name="folder.display_home" value="${folder.display_home }"/>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">标题：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" datatype="*" value="${folder.title }"   name="folder.title">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">关键字：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" datatype="*" value="${folder.keywords }"   name="folder.keywords">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">描述：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<textarea name="folder.description" datatype="*"  class="textarea"  placeholder="说点什么...最少输入10个字符" onKeyUp="textarealength(this,200)">${ folder.description}</textarea>
				<p class="textarea-numberbar"><em class="textarea-length">0</em>/200</p>
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<input class="btn btn-primary radius" onclick="commonSubmit('system/folder/save')" type="button" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
			</div>
		</div>
	</form>
</article>
</layout:override>

<layout:override name="script">
	<script type="text/javascript">
		$(function(){
			/* $('input[name="folder.status"]').val("${folder.status}");
			$('input[name="folder.type"]').val("${folder.type}"); */
			$("body").css("border-bottom","100");
			$("body").css("border-top","0");
		});
	</script>
</layout:override>
<%@ include file="/commons/layout_admin.jsp" %>