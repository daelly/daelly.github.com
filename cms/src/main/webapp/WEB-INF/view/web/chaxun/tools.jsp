<%@page import="com.jfinal.kit.PropKit"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<layout:override name="head">
	<title>全国社会保险个人账户查询  - ${x:getValueFromCache("web_name")}</title>
	<meta name="keywords" content="社保政策,社保查询,社保新闻，社保新政策,社保计算器,社保查询个人账户"/>
	<meta name="description" content="社保云是一个专门针对社保信息的门户网站，为广大网民提供最新的社保政策、社保资讯、汇集全国各地的社保查询、办事指南等。"/>
	<style type="text/css">
		#content table{border:solid 1px #eee;border-spacing:0;border-collapse: collapse;width: 680px;margin-left: 60px;margin-top: 10px;}
		#content table th,td{border:solid 1px #eee;padding: 0px;margin: 0px;padding: 10px;font-weight: normal;font-size: 14px;}
		#content table th{text-align: right;padding-right: 30px;width: 100px;background-color: #f7f7f7;}
		#content table td span{word-break: break-all;}
	</style>
</layout:override>
<layout:override name="content">
	<div class="index-bd">			
			<div class="index-R">
				<div class="index-R-list-1">
					<h1 class="index-list-t"><s><i class="iconfont icon-shanhuo"></i><span>最新推荐</span></s><em></em></h1>
					<ul>
						<c:forEach items="${list_tuijian }" var="item">
							<li><a title="${item.title }" href="article/${item.id}.html">${item.title}</a></li>
						</c:forEach>
					</ul>					
				</div>
				<div class="index-R-list-1">
					<h1 class="index-list-t"><s><i class="iconfont icon-shanhuo"></i><span>热文榜单</span></s><em></em></h1>
					<ul>
						<c:forEach items="${list_hot }" var="item">
							<li><a title="${item.title }" href="article/${item.id}.html">${item.title}</a></li>
						</c:forEach>
					</ul>					
				</div>
				<div class="index-R-list-1" style="margin-top: 10px;">
					<h1 class="index-list-t"><s><i class="iconfont icon-zuixingonggao "></i><span>最新快报</span></s><em></em></h1>
					<ul>
						<c:forEach items="${list_new }" var="item">
							<li><a title="${item.title }" href="article/${item.id }.html">${item.title }</a></li>
						</c:forEach>
					</ul>					
				</div>
				
				<div class="index-R-list-2" style="margin-top: 10px;">
					<h1 class="index-list-t"><s><i class="iconfont icon-zuixingonggao "></i><span>热门话题</span></s><em></em></h1>
					<ul>
						<c:forEach items="${allTags }" var="item">
							<li><a title="${ item.tag_name}" href="tags/${item.id }.html">${ item.tag_name}</a></li>
						</c:forEach>
					</ul>					
				</div>
			</div>
			<div class="index-L">
				<div class="index-list-3" style="margin-top: 10px;">
					<h1 class="index-list-t"><s><i class="iconfont icon-yijianfankui"></i><span>全国社会保险个人账户查询 </span></s><em></em></h1>
						<div style="padding: 10px;">
						<select id="type" onchange="opType()" style="width: 500px;padding: .5em;margin-top: 20px;margin-left:50px;border: 1px solid #ccc;color: #555;font-size: 1.1rem;">
							<option value="1">养老保险</option>
							<option value="2">医疗保险</option>
							<option value="3">公积金查询</option>
						</select>
						<br>
						<db:select headerKey="" headerValue="请选择省份" style="width: 500px;padding: .5em;margin-left:50px;margin-top: 20px;border: 1px solid #ccc;color: #555;font-size: 1.1rem;" dblabel="code_name" onchange="getCityData($(this))" dbvalue="region_code" name="province" sqlKey="province_all"/>
						<br>
						<select style="width: 500px;padding: .5em;margin-top: 20px;margin-left:50px;border: 1px solid #ccc;color: #555;font-size: 1.1rem;" id="city" onchange="getCityDetailData($(this))">
							<option value="">请选择城市</option>
						</select>
						</div>
						<div id="content" style="display: none;">
							<table >
								<tr><th>机构名称:</th><td><span id="name1"></span></td></tr>
								<tr><th>地址:</th><td><span id="name2"></span></td></tr>
								<tr><th>联系电话:</th><td><span id="name3"></span></td></tr>
								<tr><th>查询地址:</th><td><span id="name4"></span></td></tr>
								<tr><th>查询注意:</th><td><span id="name5"></span></td></tr>							
							</table>						
						</div>
					</div>
				</div>
			</div>
		</div>
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
			    	$("#city").html("");
			    	$(data).each(function(){
			    		$("#city").append("<option value="+this.region_code+">"+this.code_name+"</option>");
			    	});
			    	if(data!=null&&data.length >= 1){
				    	$("#content").show();
			    		$("#city")[0].onchange();
			    	}
			     },  
			     error : function() {  
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
				    	$("#name5").html(data.query_desc);
			     },  
			     error : function() {  
			          alert("异常！");  
			     }  
			});
			
		}
 	</script>
</layout:override>
<%@ include file="/commons/layout_web.jsp" %>