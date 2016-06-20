<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<layout:override name="head">
	<title>爬虫配置</title>
	<layout:head layer="true" validForm="true"></layout:head>
</layout:override>
<layout:override name="content">
<article class="page-container">
	<form action="" method="post" class="form form-horizontal" id="form">
		<input type="hidden" name="rule_id" value="${rule_id}"/>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>URL：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<textarea  class="input-text"  name="url" style="width:700px;height: 200px;"></textarea>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>栏目：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<span class="select-box">
						<db:select rules="*" styleClass="select radius" dblabel="name" name="article.folder_id" value="" dbvalue="id" sql="SELECT * from tbl_folder where `status`=1"/>
				</span>
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<input class="btn btn-primary radius" onclick="commonSubmit('system/spider_rule/spiderUrlData')" type="button" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
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