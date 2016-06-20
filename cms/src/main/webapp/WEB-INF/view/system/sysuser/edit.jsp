<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<layout:override name="head">
	<title>code数据</title>
	<layout:head layer="true" validForm="true"></layout:head>
</layout:override>
<layout:override name="content">
<article class="page-container">
	<form action="" method="post" class="form form-horizontal" id="form">
		<input type="hidden" name="sysUser.userid" value="${sysUser.userid }"/>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>登录名：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" datatype="*" value="${sysUser.username }"   name="sysUser.username">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">新密码：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="password" class="input-text" maxlength="18"  name="sysUser.password">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>真实名字：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${sysUser.realname }" datatype="*"   name="sysUser.realname">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>email：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${sysUser.email }"   name="sysUser.email">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>tel：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${sysUser.tel }"   name="sysUser.tel">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>address：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${sysUser.address }"   name="sysUser.address">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">备注：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<textarea name="sysUser.remark"  class="textarea"  placeholder="说点什么...最少输入10个字符" onKeyUp="textarealength(this,100)">${ sysUser.remark}</textarea>
				<p class="textarea-numberbar"><em class="textarea-length">0</em>/100</p>
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<input class="btn btn-primary radius" onclick="commonSubmit('system/sysuser/save')"  type="button" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
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