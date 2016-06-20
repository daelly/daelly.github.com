<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<layout:override name="head">
	<title>test</title>
	<layout:head datePicker="true" icheck="true" validForm="true"  layer="true"></layout:head>
</layout:override>
<layout:override name="content">
	<article class="page-container mb-50">
		<form class="form form-horizontal" action="/system/article/save" method="post" id="form">
			<input name="article.id" type="hidden" value="${article.id}"/>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>文章标题：</label>
				<div class="formControls col-xs-8 col-sm-9" style="display: inline;">
					<input type="text" class="input-text radius" datatype="*" id="title" value="${article.title }"   name="article.title">
					<input type="hidden" value="${article.color }" id="color" name="article.color">
					<img src="static/lib/colorpicker/colorpicker.png" id="cp3" style="cursor:pointer"/>
					<a href="javascript:;" onclick="clearColor()">清除颜色</a>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>分类栏目：</label>
				<div class="formControls col-xs-8 col-sm-9"> 
					<span class="select-box">
						<db:select rules="*" styleClass="select radius" dblabel="name" name="article.folder_id" value="${article.folder_id}" dbvalue="id" sqlKey="folder_all"/>
					</span> 
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>专题：</label>
				<div class="formControls col-xs-8 col-sm-9"> 
					<span class="select-box">
						<db:select headerKey="" headerValue="" styleClass="select radius" dblabel="title" name="article.topic_id" value="${article.topic_id}" dbvalue="id" sqlKey="topic_all"/>
					</span> 
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">审核通过：</label>
				<div class="formControls col-xs-8 col-sm-9">
						<x:radio pvalue="status" name="article.status" value="${article.status }"/>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">显示为banner：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<x:radio pvalue="status" name="article.is_banner" value="${article.is_banner }"/>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">是否推荐：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<x:radio pvalue="status" name="article.is_recommend" value="${article.is_recommend }"/>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">发布日期：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" name="article.publish_time" datatype="*" value="${article.publish_time }" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="input-text radius Wdate">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">来源城市：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<div class="select-box">
						<db:select dblabel="short_name" styleClass="select" dbvalue="short_name" value="${article.address }" name="article.address" sqlKey="article_address"/>
					</div>
					<%-- <input type="text" class="input-text radius" value="${article.address }"   name="article.address"> --%>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">文章来源：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text radius" datatype="*" value="${article.origin }"   name="article.origin">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">文章作者：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text radius" datatype="*" value="${article.publish_user }"   name="article.publish_user">
				</div>
			</div>
			<div class="row cl">
				<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2"> 
					<button onClick="commonSubmit('system/article/save')" class="btn btn-primary radius" type="button"><i class="Hui-iconfont">&#xe632;</i> 提 交 </button>
				</div>
			</div>
		</form>
</article>
</layout:override>

<layout:override name="script">
	<script type="text/javascript" src="static/lib/colorpicker/jquery.colorpicker.js"></script>
    <script type="text/javascript">
	  $(function(){
		  $("#cp3").colorpicker({
		        fillcolor:true,
		        event:'mouseover',
		        success:function(o,color){
		            $("#title").css("color",color);
		            $("#color").val(color);
		        }
		    });
	  });
	  function clearColor(){
		  $("#color").val("");
		  $("#title").css("color","");
	  }
    </script>
</layout:override>

<%@ include file="/commons/layout_admin.jsp" %>