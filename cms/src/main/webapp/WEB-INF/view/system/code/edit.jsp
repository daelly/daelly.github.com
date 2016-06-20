<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<layout:override name="head">
	<title>code数据</title>
	<layout:head layer="true" validForm="true"></layout:head>
</layout:override>
<layout:override name="content">
<article class="page-container">
	<form action="" method="post" class="form form-horizontal" id="form">
		<input type="hidden" name="sysCode.id" value="${sysCode.id }"/>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>参数Key：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" datatype="*" value="${sysCode.paramname }" placeholder=""  name="sysCode.paramname">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>参数值：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" datatype="*" value="${sysCode.paramvalue }" placeholder=""  name="sysCode.paramvalue">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>是否删除：</label>
			<div class="formControls col-xs-8 col-sm-9 skin-minimal">
				<div class="radio-box">
					<input name="sysCode.isdelete" type="radio" id="sex-1" value="1">
					<label for="sex-1">是</label>
				</div>
				<div class="radio-box">
					<input type="radio" id="sex-2" value="0" name="sysCode.isdelete" checked="checked">
					<label for="sex-2">否</label>
				</div>
			</div>
		</div>
		<!-- <div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">数据类型：</label>
			<div class="formControls col-xs-8 col-sm-9">
				 <span class="select-box">
					<select class="select" size="1" name="sysCode.paramtype">
						<option value="" selected>请选择</option>
						<option value="String">String</option>
						<option value="Int">Int</option>
					</select>
				</span> 
			</div>
		</div> -->
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">备注：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<textarea name="sysCode.remark"  class="textarea"  placeholder="说点什么...最少输入10个字符" onKeyUp="textarealength(this,100)">${ sysCode.remark}</textarea>
				<p class="textarea-numberbar"><em class="textarea-length">0</em>/100</p>
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<input class="btn btn-primary radius" onclick="commonSubmit('system/code/save')"  type="button" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
			</div>
		</div>
	</form>
</article>
</layout:override>

<layout:override name="script">
	<script type="text/javascript">
	</script>
</layout:override>
<%@ include file="/commons/layout_admin.jsp" %>