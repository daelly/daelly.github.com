<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<layout:override name="head">
	<title>爬虫配置</title>
	<layout:head layer="true" validForm="true"></layout:head>
</layout:override>
<layout:override name="content">
<article class="page-container">
	<form action="" method="post" class="form form-horizontal" id="form">
		<input type="hidden" name="spiderJob.id" value="${spiderJob.id }"/>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>job_name：</label>
			<div class="formControls col-xs-8 col-sm-7">
				<input type="text" class="input-text" value="${spiderJob.job_name }" placeholder=""  name="spiderJob.job_name">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>url_page：</label>
			<div class="formControls col-xs-8 col-sm-7">
				<input type="text" class="input-text" value="${spiderJob.url_page }" placeholder=""  name="spiderJob.url_page">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>links_rule：</label>
			<div class="formControls col-xs-8 col-sm-7">
				<input type="text" class="input-text" value="${spiderJob.links_rule }" placeholder=""  name="spiderJob.links_rule">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>采集规则：</label>
			<div class="formControls col-xs-8 col-sm-7">
				<div class="select-box">
					<db:select dblabel="name" styleClass="select" dbvalue="id" sql="SELECT `name`,id from tbl_spider_rule " name="spiderJob.spide_rule_id" value="${spiderJob.spide_rule_id }"/>
				</div>
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-8 col-sm-7 col-xs-offset-4 col-sm-offset-3">
				<input class="btn btn-primary radius" onclick="commonSubmit('system/spider_job/save')" type="button" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
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