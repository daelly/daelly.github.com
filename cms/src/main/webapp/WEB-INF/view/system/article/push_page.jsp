<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<layout:override name="head">
	<title>百度推送</title>
	<layout:head layer="true" validForm="true" />
	<style>
		button:disabled{
			background-color: #ccc!important;
			border-color: #ccc!important;
		}
	</style>
</layout:override>
<layout:override name="content">
	<article class="page-container">
		<form action="" method="post" class="form form-horizontal" id="form">
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>URL：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<textarea  class="input-text"  name="urls" style="width:700px;height: 200px;"></textarea>
				</div>
			</div>
			<div class="row cl">
				<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
					<input class="btn btn-primary radius" onclick="submitPush()" type="button" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3">推送结果</label>
				<div class="formControls col-xs-8 col-sm-9">
					<div id="result" style="width: 100%;min-height: 50px;">
					</div>
				</div>
			</div>
		</form>
	</article>
</layout:override>
<layout:override name="script">
    <script type="text/javascript">
    	function submitPush(){
    		$.ajax({
    			url: "system/article/pushUrls",
    			type : "post",
    			dataType : "json",
    			data: $("#form").serialize(),
    			success: function(result){
    				if(result.code==0){
    					var rs = result.data;
    					if(rs.error){
    						layer.msg(rs.message);
    					}else{
    						var not_same_site = rs.not_same_site;
    						var not_valid = rs.not_valid;
    						var success = rs.success;
    						var remain = rs.remain;
    						var msg = "";
    						if(success > 0){
    							msg += "成功推送"+success+"条<br>";
    						}
    						if(not_same_site && not_same_site.length>0){
    							msg += "<font style='color:red;'>"
    							msg += not_same_site.length+"条非本站url导致失败：<br>";
    							for(i in not_same_site){
    								msg += not_same_site[i]+"<br>";
    							}
    							msg += "</font>";
    						}
    						if(not_valid && not_valid.length>0){
    							msg += "<font style='color:red;'>"
    							msg += not_valid.length+"条非法url导致失败：<br>";
    							for(i in not_valid){
    								msg += not_valid[i]+"<br>";
    							}
    							msg += "</font>";
    						}
    						msg += "今日剩余："+remain+"条可推送";
    						$("#result").html(msg);
    					}
    				}else{
    					layer.msg(result.message);
    				}
    			},error:function(){
    				layer.msg("系统繁忙");
    			}
    		});
    	}
    </script>
</layout:override>

<%@ include file="/commons/layout_admin.jsp" %>