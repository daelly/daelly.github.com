﻿<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<layout:override name="head">
	<title>爬虫管理</title>
	<script type="text/javascript" src="static/lib/base64.js"></script>
	<layout:head layer="true" validForm="true"></layout:head>
</layout:override>
<layout:override name="script">
	<script type="text/javascript">
		/** 
		 * @version 1.0 
		 * 用于实现页面 Map 对象，Key只能是String，对象随意 
		 */  
		var Map = function(){  
		    this._entrys = new Array();  
		      
		    this.put = function(key, value){  
		        if (key == null || key == undefined) {  
		            return;  
		        }  
		        var index = this._getIndex(key);  
		        if (index == -1) {  
		            var entry = new Object();  
		            entry.key = key;  
		            entry.value = value;  
		            this._entrys[this._entrys.length] = entry;  
		        }else{  
		            this._entrys[index].value = value;  
		        }          
		    };  
		    this.get = function(key){  
		        var index = this._getIndex(key);  
		        return (index != -1) ? this._entrys[index].value : null;  
		    };  
		    this.remove = function(key){  
		        var index = this._getIndex(key);  
		        if (index != -1) {  
		            this._entrys.splice(index, 1);  
		        }  
		    };  
		    this.clear = function(){  
		        this._entrys.length = 0;;  
		    };  
		    this.contains = function(key){  
		        var index = this._getIndex(key);  
		        return (index != -1) ? true : false;  
		    };  
		    this.length = function(){  
		        return this._entrys.length;  
		    };  
		    this.getEntrys =  function(){  
		        return this._entrys;  
		    };  
		   this._getIndex = function(key){  
		        if (key == null || key == undefined) {  
		            return -1;  
		        }  
		        var _length = this._entrys.length;  
		        for (var i = 0; i < _length; i++) {  
		            var entry = this._entrys[i];  
		            if (entry == null || entry == undefined) {  
		                continue;  
		            }  
		            if (entry.key === key) {//equal  
		                return i;  
		            }  
		        }  
		        return -1;  
		    };  
		} 
	
		var quneryInter;//查询定时器
		var showInter;//显示定时器
		
		function runSpider() {
			$('#stopBtn').show();
			$('#runBtn').hide();
			var base = new Base64();
			var replaceStr = base.encode('redsea');
			
			var url = $('#url').val();
			var links = $('#links').val();
			var title = $('#title').val();
			var summary = $("#summary").val();
			var content = $('#content').val();
			var origin = $('#origin').val();
			var publish_user = $('#publish_user').val();
			var publish_time = $('#publish_time').val();
			
			url = replaceAll(url, '\\\\', replaceStr);
			links = replaceAll(links, '\\\\', replaceStr);
			title = replaceAll(title, '\\\\', replaceStr);
// 			summary = replaceAll(summary, '\\\\', replaceStr);
			content = replaceAll(content, '\\\\', replaceStr); 
			origin = replaceAll(origin, '\\\\', replaceStr); 
			publish_user = replaceAll(publish_user, '\\\\', replaceStr); 
 			publish_time = replaceAll(publish_time, '\\\\', replaceStr); 
			
			if(url == '') {
				layer.msg('url不能为空，请填写');
				return;
			}else {
				$('input[name="url"]').val(url);
			}
			
			if(links == '') {
				layer.msg('links不能为空，请填写');
				return;
			}else {
				$('input[name="links"]').val(links);
			}
			
			if(title == '') {
				layer.msg('title不能为空，请填写');
				return;
			}else {
				$('input[name="title"]').val(title);
			}
			
			if(content == '') {
				layer.msg('content不能为空，请填写');
				return;
			}else {
				$('input[name="content"]').val(content);
			}
			$('input[name="origin"]').val(origin);
			$('input[name="publish_user"]').val(publish_user);
			$('input[name="publish_time"]').val(publish_time);
			$('input[name="summary"]').val(summary);
			
			quneryInter = window.setInterval(getNewestArticle,1000);
			
			$.ajax({
				type: 'post',
				url: "ext/spider/run",
				data: $('form').serialize(),
			    success: function(result){
			    	layer.msg(result);
			    	window.clearInterval(quneryInter);
			    	window.clearInterval(showInter);
			    	index = 0;
					articles = new Map();
					$('#stopBtn').hide();
					$('#runBtn').show();
					$('#showDiv').hide();
			    }
			});
		}
		function stopSpider() {
			$('#stopBtn').hide();
			$('#runBtn').show();
			$.ajax({
				type: 'post',
				url: "ext/spider/stop",
			    success: function(result){
			    	layer.msg(result);
			    	window.clearInterval(quneryInter);
			    	window.clearInterval(showInter);
			    	index = 0;
			    	articles = new Map();
			    	$('#showDiv').hide();
			    }
			});
		}
		
		//全部替换字符串
		function replaceAll(str,s1,s2){ 
			while( str.indexOf( s1 ) != -1 ) {
				str = str.replace(s1 ,s2); 
			}

			return str; 
		}
		
		var articles = new Map();
		var index = 0;
		//滚动查询搜索到的内容
		function getNewestArticle() {
			$.ajax({
				type: 'post',
				url: "ext/spider/list",
				data: 'is_new=1',
			    success: function(result){
			    	if(null != result && result != '0' && result.length > 0) {
			    		$.each(result, function(index, article) {
			    			articles.put(article.page_url, article.page_url);
				    	});
			    		if(index == 0) {
				    		showInter = window.setInterval(showPageUrl,300);
				    	}
			    		index ++;
			    		$('#showDiv').show();
			    	}
			    }
			});
		}
		
		//滚动显示搜索路径
		function showPageUrl() {
			if(articles && articles.length() > 0) {
				var entries = articles.getEntrys();
				var showPage = entries[0].value;
				$('#pageUrl').html(showPage+"\n"+$('#pageUrl').html());
				articles.remove(entries[0].key);
			}
		}

		function showLine(o){
			if("checked" == o.attr("checked")){
				$("#replace_line").show();
			}else{
				$('input[name="replaceStr"]').val("");
				$('input[name="str"]').val("");
				$("#replace_line").hide();
			}
		}
		
		$(function(){
			$("input[name='spiderType']").on("click",function(){
				if($(this).val()=="1"){
					$("#form1Container").show();
					$("#form2Container").hide();
				}else{
					$("#form1Container").hide();
					$("#form2Container").show();			
				}
			});
			
		});
		
		function submitSpiderForm2(){
			var valid = $("#fillContentForm").Validform({
				tiptype:4,
				callback:function(result){
					if(result.code=="0"){
						var actile = result.data;
						layer.confirm("文章采集成功，是否前往编辑或预览？",{
							btn:["编辑","预览","取消"]},
							function(i){
								window.open("system/article/edit/"+actile.id,"_blank");
								layer.close(i);
							},function(i){
								window.open("view/"+actile.id,"_blank");
								layer.close(i);
						});
					}else{
						layer.msg(result.message);
					}
				}
			});
			valid.ajaxPost(false,true,"ext/spider/singlePage");
		}
	  function getSpiderRule(obj){
			$.ajax({
				url: 'system/spider_rule/getSpiderRule/'+obj.val(),
			    data: null,
			    success: function(result){
			    	$("#title").val(result.title_rule);
			    	$("#content").val(result.content_rule);
			    	$("#publish_user").val(result.publish_user_rule);
			    	$("#origin").val(result.origin_rule);
			    	$("#summary").val(result.summary_rule);
			    	$("#publish_time").val(result.publish_time_rule);
			    }
			});
	  }
	  function getSpiderRule2(obj){
			$.ajax({
				url: 'system/spider_rule/getSpiderRule/'+obj.val(),
			    data: null,
			    success: function(result){
			    	$("input[name='article.title']").val(result.title_rule);
			    	$("input[name='article.content']").val(result.content_rule);
			    	$("input[name='article.publish_user']").val(result.publish_user_rule);
			    	$("input[name='article.origin']").val(result.origin_rule);
			    	$("input[name='article.summary']").val(result.summary_rule);
			    	$("input[name='article.publish_time_remark']").val(result.publish_time_rule);
			    }
			});
	  }
	</script>
</layout:override>
<layout:override name="content">
	<div class="row pt-20 pl-20 ml-50">
		<div style="height: 30px;">
			<span>
				采集类型
				<input type="radio" name="spiderType" value="1" checked  />多页面采集
				<input type="radio" name="spiderType" value="2" />单页面采集				
			</span>
		</div>
		<span style="width: 600px;" id="form1Container">
			<form action="${contextPath }/spider/run" style="width: 600px;float: left;">
				<input type="hidden" name="url" value=""/>
				<input type="hidden" name="links" value=""/>
				<input type="hidden" name="title" value=""/>
				<input type="hidden" name="summary" value=""/>
				<input type="hidden" name="content" value=""/>
				<input type="hidden" name="origin" value=""/>
				<input type="hidden" name="publish_user" value=""/>
				<input type="hidden" name="publish_time" value=""/>
				<label>选择规则：</label>
				<div class="select-box radius" style="width: 62%;margin-bottom: 4px;margin-left: 18px">
					<db:select styleClass="select radius" onchange="getSpiderRule($(this))" dblabel="name" dbvalue="id" sql="SELECT id,name from tbl_spider_rule" name="type"/>
				</div>
				<br>
				<label style="width: 15%;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;URL：</label>
				<input type="text" class="input-text radius" id="url" placeholder="URL" style="width:62.5%;margin-bottom: 4px;"/>
				<br/>
				<label style="width: 15%;">地址表达式：</label>
				<select name="links_pattern" id="links_pattern" class="select radius" size="1.5" style="width: 15%;height: 31px;">
					<option value="regular">正则表达式</option>
					<option value="xpath">Xpath表达式</option>
					<option value="css">CSS选择器</option>
				</select>
				<input type="text" class="input-text radius" id="links" placeholder="links" style="width: 47%;margin-bottom: 4px;"/>
				
				<br/>
				<label style="width: 15%;">标题表达式：</label>
				<select name="title_pattern" id="title_pattern" class="select radius" size="1.5" style="width: 16%;height: 31px;">
					<option value="xpath">Xpath表达式</option>
					<option value="regular">正则表达式</option>
					<option value="css">CSS选择器</option>
				</select>
				<input type="text" class="input-text radius"  id="title" placeholder="title" style="width: 46%;margin-bottom: 4px;"/>
				
				<br/>
				
				<label style="width: 15%;">摘要表达式：</label>
				<select name="summary_pattern" id="summary_pattern" class="select" size="1.5" style="width: 16%;height: 31px;">
					<option value="xpath">Xpath表达式</option>
					<option value="regular">正则表达式</option>
					<option value="css">CSS选择器</option>
				</select>
				<input type="text" class="input-text"  id="summary" placeholder="summary" style="width: 46%;margin-bottom: 4px;"/>
				
				<br/>
				
				<label style="width: 15%;">内容表达式：</label>
				<select name="content_pattern" id="content_pattern" class="select radius" size="1.5" style="width: 16%;height: 31px;">
					<option value="xpath">Xpath表达式</option>
					<option value="regular">正则表达式</option>
					<option value="css">CSS选择器</option>
				</select>
				<input type="text" class="input-text radius"  id="content" placeholder="content" style="width: 46%;margin-bottom: 4px;"/>
				
				<br/>
				<label style="width: 15%;">来源表达式：</label>
				<select name="origin_pattern" id="origin_pattern" class="select radius" size="1.5" style="width: 16%;height: 31px;">
					<option value="xpath">Xpath表达式</option>
					<option value="regular">正则表达式</option>
					<option value="css">CSS选择器</option>
				</select>
				<input type="text" class="input-text radius"  id="origin" placeholder="origin" style="width: 46%;margin-bottom: 4px;"/>
				
				<br/>
				<label style="width: 15%;">作者表达式：</label>
				<select name="publish_user_pattern" id="publish_user_pattern" class="select radius" size="1.5" style="width: 16%;height: 31px;">
					<option value="xpath">Xpath表达式</option>
					<option value="regular">正则表达式</option>
					<option value="css">CSS选择器</option>
				</select>
				 <input type="text" class="input-text radius"  id="publish_user" placeholder="publish_user" style="width: 46%;margin-bottom: 4px;"/>
				<!-- 替换内容<input type="checkbox" onclick="showLine($(this))" > -->
				<br/>
				<span id="replace_line" style="display: none">
				<label style="width: 15%;">被替换字符：</label><input type="text" class="input-text radius" name="replaceStr" id="replaceStr" placeholder="" style="width: 30%;margin-bottom: 4px;"/>
				<label style="width: 15%;">替换字符：</label><input type="text" class="input-text radius"  name="str" id="str" placeholder="" style="width: 30%;margin-bottom: 4px;"/>
				<br/>				
				</span>
			 <label style="width: 15%;">时间表达式：</label>
				<select name="publish_time_pattern" id="publish_time_pattern" class="select radius" size="1.5" style="width: 16%;height: 31px;">
					<option value="xpath">Xpath表达式</option>
					<option value="regular">正则表达式</option>
					<option value="css">CSS选择器</option>
				</select>
				<input type="text" class="input-text radius"  id="publish_time" placeholder="publish_time" style="width: 46%;margin-bottom: 4px;"/>
				<br/> 
				<input type="button" id="stopBtn" class="btn r ml-10 mr-20 btn-primary radius" value="停 止" onclick="stopSpider();" style="display: none;margin-right: 140px;"/>
				<input type="button" id="runBtn" class="btn r mr-20 btn-primary radius" style="margin-right: 140px;" value="开 始" onclick="runSpider();"/>
			</form>
		</span>
		<span style="width: 600px;display:none;" id="form2Container">
			<form id="fillContentForm" class="form form-horizontal" style="width: 450px;float: left;">
				<div class="row cl" style="margin: auto 10px;">
					<div id="toggleLayer" style="width:450px;">
							<table cellspacing="10" cellpadding="10">
								<tr>
									<td style="text-align: right;">选择规则：</td>
									<td>
										<div class="select-box radius">
											<db:select styleClass="select radius" onchange="getSpiderRule2($(this))" dblabel="name" dbvalue="id" sql="SELECT id,name from tbl_spider_rule" name="type"/>
										</div>
									</td>
								</tr>
								<tr>
									<td style="text-align: right;">请求地址：</td>
									<td><input type="text" placeholder="url" style="margin-top: 10px;" class="input-text radius" name="url" datatype="*" errormsg="请输入采集页面url" nullmsg="请输入采集页面url" /></td>
								</tr>
								<tr>
									<td style="text-align: right;">标题CSS表达式：</td>
									<td><input type="text" placeholder="title" style="margin-top: 10px;" class="input-text radius" name="article.title" datatype="*" errormsg="请输入标题选择器" nullmsg="请输入标题选择器" /></td>
								</tr>
								<tr>
									<td style="text-align: right;">摘要CSS表达式：</td>
									<td><input type="text" placeholder="summary" style="margin-top: 10px;" class="input-text radius" name="article.summary"  /></td>
								</tr>
								<tr>
									<td style="text-align: right;">内容CSS表达式：</td>
									<td><input type="text" placeholder="content" style="margin-top: 10px;" class="input-text radius" name="article.content" datatype="*" errormsg="请输入内容选择器" nullmsg="请输入内容选择器" /></td>
								</tr>
								<tr>
									<td style="text-align: right;">来源CSS表达式：</td>
									<td><input type="text" placeholder="origin" style="margin-top: 10px;" class="input-text radius" name="article.origin" /></td>
								</tr>
								<tr>
									<td style="text-align: right;">作者CSS表达式：</td>
									<td><input type="text" placeholder="publish_user" style="margin-top: 10px;" class="input-text radius" name="article.publish_user" /></td>
								</tr>
								<tr>
									<td style="text-align: right;">时间CSS表达式：</td>
									<td><input type="text" placeholder="publish_time_remark" style="margin-top: 10px;" class="input-text radius" name="article.publish_time_remark" /></td>
								</tr>
								<tr>
									<td style="text-align: right;">关键字CSS表达式：</td>
									<td><input type="text" placeholder="keyword" style="margin-top: 10px;" class="input-text radius" name="article.keyword" /></td>
								</tr>
								<tr>
									<td colspan="2" align="right">
										<button type="button" style="margin-top: 10px;" class="btn btn-primary radius" onclick="submitSpiderForm2()">确认</button>
									</td>
								</tr>
							</table>
					</div>
				</div>
			</form>
		</span>
		<span style="width: 400px;">
			<div id="showDiv" style="display: none;">
				<div>正在搜索页面：</div>
				<div id="pageUrl" style="overflow-y:scroll;height: 440px;width: 380px;" ></div>
			</div>
		</span>
	</div>
</layout:override>
<%@ include file="/commons/layout_admin.jsp" %>