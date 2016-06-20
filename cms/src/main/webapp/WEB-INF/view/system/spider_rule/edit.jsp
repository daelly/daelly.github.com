<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<layout:override name="head">
	<title>爬虫配置</title>
	<layout:head layer="true" validForm="true"></layout:head>
</layout:override>
<layout:override name="content">
<article class="page-container">
	<form action="" method="post" class="form form-horizontal" id="form">
		<input type="hidden" name="spiderRule.id" value="${spiderRule.id }"/>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>网站名称：</label>
			<div class="formControls col-xs-8 col-sm-7">
				<input type="text" class="input-text" value="${spiderRule.name }" placeholder=""  name="spiderRule.name">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>网站URL：</label>
			<div class="formControls col-xs-8 col-sm-7">
				<input type="text" class="input-text" value="${spiderRule.website }" placeholder=""  name="spiderRule.website">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>标题规则：</label>
			<div class="formControls col-xs-8 col-sm-7">
				<input type="text" class="input-text" value="${spiderRule.title_rule }" placeholder=""  name="spiderRule.title_rule">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>内容规则：</label>
			<div class="formControls col-xs-8 col-sm-7">
				<input type="text" class="input-text" value="${spiderRule.content_rule }" placeholder=""  name="spiderRule.content_rule">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>来源规则：</label>
			<div class="formControls col-xs-8 col-sm-7">
				<input type="text" class="input-text" value="${spiderRule.origin_rule }" placeholder=""  name="spiderRule.origin_rule">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>作者规则：</label>
			<div class="formControls col-xs-8 col-sm-7">
				<input type="text" class="input-text" value="${spiderRule.publish_user_rule }" placeholder=""  name="spiderRule.publish_user_rule">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>发布时间规则：</label>
			<div class="formControls col-xs-8 col-sm-7">
				<input type="text" class="input-text" value="${spiderRule.publish_time_rule }" placeholder=""  name="spiderRule.publish_time_rule">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>摘要规则：</label>
			<div class="formControls col-xs-8 col-sm-7">
				<input type="text" class="input-text" value="${spiderRule.summary_rule }" placeholder=""  name="spiderRule.summary_rule">
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-8 col-sm-7 col-xs-offset-4 col-sm-offset-3">
				<input class="btn btn-primary radius" onclick="commonSubmit('system/spider_rule/save')" type="button" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
			</div>
		</div>
	</form>
</article>
</layout:override>

<layout:override name="script">
	<script type="text/javascript">
		$(function(){
			
		});
	</script>
</layout:override>
<%@ include file="/commons/layout_admin.jsp" %>