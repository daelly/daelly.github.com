<%@page import="com.redsea.model.Folder"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/global.jsp" %>
<layout:override name="head">
	 <title>史上最完整全国338个地区的社保查询网址【珍藏版】</title>
	<meta name="keywords" content="社保政策,社保查询,社保新闻，社保新政策,社保计算器,社保查询个人账户"/>
	<meta name="description" content="社保云是一个专门针对社保信息的门户网站，为广大网民提供最新的社保政策、社保资讯、汇集全国各地的社保查询、办事指南等。"/> 
</layout:override>
<layout:override name="content">
  <form class="am-form" style="text-align: center;margin: 0 auto;">
  	<div class="am-form-group" style="text-align: center;margin-top: 30px;">
		<p>	<img src="static/images/sb.png" style="width: 180px;height: 150px;"/></p>
		<select id="type" onchange="opType()" style="width: 80%;margin-top:30px;margin:0 auto;border-radius: 4px;">
			<option value="1">养老保险</option>
			<option value="2">医疗保险</option>
			<option value="3">公积金查询</option>
		</select>
  	</div>
  	<div class="am-form-group">
		<db:select headerKey="" headerValue="请选择省份" nullValue="true" style="width: 80%;margin-top:10px;margin:0 auto;border-radius: 4px;" id="p" dblabel="code_name" onchange="getCityData($(this))" dbvalue="region_code" name="province" sqlKey="province_all"/>
  		<span class="am-form-caret"></span>
  	</div>
  	<div class="am-form-group">
		<select style="width: 80%;margin-top:10px;margin:0 auto;border-radius: 4px;" id="city" onchange="getCityDetailData($(this))"><option value="">请选择城市</option></select>
  	</div>
	<div class="am-form-group">
		<p id="content" style="text-align: left;width: 80%;margin: 0 auto;margin-top: 10px;display: none;margin-bottom: 15px;">
			<span  style="font-weight: bold;">机构名称: </span><span id="name1"></span><br>
			<span  style="font-weight: bold;">地址: </span><span  id="name2"></span><br>
			<span  style="font-weight: bold;">联系电话: </span><span  id="name3"></span><br>
			<span  style="font-weight: bold;">查询网址: </span><span  id="name4"></span><br>
			<span  style="font-weight: bold;" id="name5_2">查询注意: </span><span  id="name5"></span><br>
		</p>
		<button type="button" class="am-btn am-btn-default" style="width: 80%;border-radius: 4px;" id="cx">我要查询</button>
		<p id="desc" style="text-align: left;width: 80%;margin: 0 auto;margin-top: 10px;color: #c00">注:如查询数据与实际有出入，数据更新滞后导致，请以官方最新数据为准!  <a href="m/chaxun.html"> &nbsp;更多详细数据查询</a></p>
 	</div>
  </form>
  <br>
  <br>
  <br>
</layout:override>
<layout:override name="script">
 	<script type="text/javascript">
	 	function opType(){
	 		if($('#city').val()!=null&&$('#city').val()!=''){
	 			$('#city option:selected').change();
	 		}
	 	}
		function getCityData(obj){
			 $.ajax( {  
			    url:'chaxun/getCityData/'+obj.val(),  
			    data:{},  
			    type:'post',  
			    cache:false,  
			    dataType:'json',  
			    success:function(data) {  
			    	if(data==null||data.length==0){
			    		$("#city").hide();
			    	}else{
			    		$("#cx").hide();
			    		$("#city").show();
				    	$("#city").html("");
				    	$(data).each(function(){
				    		$("#city").append("<option value="+this.region_code+">"+this.code_name+"</option>");
				    	});
				    	if(data!=null&&data.length >= 1){
					    	$("#content").show();
				    		$("#city")[0].onchange();
				    	}
			    	}
			   },error : function() {  
			          alert("异常！");  
			     }  
			});
		}
		function getCityDetailData(obj){
			var type=$("#type").val();
			 $.ajax( {  
			    url:'chaxun/getCitDetailyData?type='+type+"&regionCode="+obj.val(),  
			    data:{},  
			    type:'post',  
			    cache:false,  
			    dataType:'json',  
			    success:function(data) { 
			    	$("#content").show();
			    	$("#name1").text(data.org_name);
			    	$("#name2").text(data.address);
			    	$("#name3").text(data.telephone);
			    	$("#name4").html("<a target='_blank' href='"+data.url+"'>"+data.url+"</a>");
			    	if(data.query_desc==null||data.query_desc==''){
			    		$("#name5_2").hide();
			    	}else{
			    		$("#name5_2").show();
				    	$("#name5").html(data.query_desc);
			    	}
			     },  
			     error : function() {  
			          alert("异常！");  
			     }  
			});
			
		}
 	</script>
</layout:override>
<%@ include file="/commons/layout_head_wap.jsp" %>