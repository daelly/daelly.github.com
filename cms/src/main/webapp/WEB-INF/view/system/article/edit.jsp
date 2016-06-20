<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<layout:override name="head">
	<title><c:if test="${article.title==null }">新增</c:if> ${article.title } </title>
	<layout:head datePicker="true" icheck="true"  layer="true" validForm="true"></layout:head>
	<style>
		button:disabled{
				background-color: #ccc!important;
				border-color: #ccc!important;
		}
	</style>
</layout:override>
<layout:override name="content">
	<article class="page-container mb-50">
		<form id="fillContentForm" class="form form-horizontal" style="display: none;">
			<div class="row cl" style="margin: auto 95px;">
				<div><a href="javascript:void(0)" onclick="javascript:{$('#toggleLayer').toggle();}" style="color: blue;">从其他网页抓取数据</a></div>
				<div id="toggleLayer" style="display:none;width:1000px;margin-top: 10px;">
						<table>
							<tr>
								<td width="100" style="text-align: right;">内容地址：</td>
								<td><input type="text"  class="input-text radius" name="url"  style="margin-left: 26px;"/></td>
								<td style="text-align: right;">&nbsp;标题选择器：</td>
								<td><input type="text" style="width: 100px;"  class="input-text radius" name="titleSelecter" /></td>
								<td style="text-align: right;">&nbsp;内容选择器：</td>
								<td class="text-l"><input type="text" style="width: 100px;" class="input-text radius" name="contentSelecter" /></td>
								<td align="center">
									<button type="button" class="btn btn-primary" onclick="submitFillContentForm()">确认</button>
								</td>
							</tr>
						</table>
				</div>
			</div>
		</form>
		<form class="form form-horizontal" action="/system/article/save" method="post" id="form">
			<input name="article.id" type="hidden" value="${article.id}"/>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>文章标题：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text radius" style="width: 75%;" datatype="*" id="title" value="${article.title }"   name="article.title">
					<div style="margin-top: 3px;float: right;">跳转链接: <input type="checkbox" style="display: inline;" onclick="$('#link').toggle()"/>&nbsp; <button class="btn btn-primary radius" type="button" onclick="getKeyWord()"><i class="Hui-iconfont">&#xe60c;</i>提取关键字</button></div>
				</div>
			</div>
			<div class="row cl" id="link" style="display: none;">
				<label class="form-label col-xs-2 col-sm-2"><span class="c-red">*</span>外部链接：</label>
				<div class="formControls col-xs-2 col-sm-9">
					<input type="text" class="input-text radius"   name="article.link" value="${article.link }">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-2 col-sm-2"><span class="c-red">*</span>分类栏目：</label>
				<div class="formControls col-xs-2 col-sm-2"> 
					<span class="select-box">
						<db:select  styleClass="select" dblabel="name" name="article.folder_id" value="${article.folder_id}" dbvalue="id" sqlKey="folder_all"/>
					</span> 
				</div>
				<label class="form-label col-xs-2 col-sm-2"><span class="c-red">*</span>模板：</label>
				<div class="formControls col-xs-2 col-sm-2"> 
					<span class="select-box">
						<x:select styleClass="select" pvalue="article_template" value="${article.template }" name="article.template"/>
					</span> 
				 </div>
				 <label class="form-label col-xs-2 col-sm-1">审核通过：</label>
				<div class="formControls col-xs-2 col-sm-2">
					<x:radio pvalue="status" name="article.status" value="${article.status}"/>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-1 col-sm-2">排序值：</label>
				<div class="formControls col-xs-3 col-sm-2">
					<input type="text" class="input-text radius" value="${article.sort }"  name="article.sort">
				</div>
				<label class="form-label col-xs-1 col-sm-2">文章作者：</label>
				<div class="formControls col-xs-3 col-sm-2">
					<input type="text" class="input-text radius" value="${article.publish_user }"   name="article.publish_user">
				</div>
				<label class="form-label col-xs-2 col-sm-1">文章来源：</label>
				<div class="formControls col-xs-2 col-sm-2">
					<input type="text" class="input-text radius" datatype="*" value="${article.origin }"  name="article.origin">
				</div>
			</div>
			<div class="row cl" style="display: none;">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>文章类型：</label>
				<div class="formControls col-xs-8 col-sm-9"> 
					<span class="select-box">
						<x:select styleClass="select" pvalue="article_type" value="${article.type }" name="article.type"/>
					</span> 
				 </div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-2 col-sm-2">是否推荐：</label>
				<div class="formControls col-xs-3 col-sm-2">
						<x:radio pvalue="status" name="article.is_recommend" value="${article.is_recommend}"/>
				</div>
				<label class="form-label col-xs-1 col-sm-2">显示banner</label>
				<div class="formControls col-xs-3 col-sm-2">
					<x:radio pvalue="status" name="article.is_banner" value="${article.is_banner}"/>
				</div>
				<label class="form-label col-xs-2 col-sm-1">发布日期：</label>
				<div class="formControls col-xs-2 col-sm-2">
					<input type="text" name="article.publish_time" datatype="*" value="${article.publish_time }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="input-text radius Wdate">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-2 col-sm-2">关键词：</label>
				<div class="formControls col-xs-3 col-sm-2">
					<input type="text" class="input-text radius" style="width: 290px;" id="keyword" datatype="*5-100"  value="${article.keyword }"  name="article.keyword">
				</div>
				<label class="form-label col-xs-2 col-sm-2">专题：</label>
				<div class="formControls col-xs-2 col-sm-2"> 
					<span class="select-box">
						<db:select headerKey="" headerValue="" styleClass="select radius" sqlKey="topic_all" dblabel="title" name="article.topic_id" value="${article.topic_id}" dbvalue="id"/>
					</span> 
				</div>
				<label class="form-label col-xs-1 col-sm-1">来源URL：</label>
				<div class="formControls col-xs-2 col-sm-2">
					<input type="text" class="input-text radius" value="${article.origin_url}"  name="article.origin_url">
				</div>
			</div>
			<div class="row cl">
					<label class="form-label col-xs-2 col-sm-2">标签：</label>
					<div class="col-sm-2">
						<button type="button" class="btn btn-success-outline radius" onclick="addTags()">新增</button>
						<div class="selectbox ml-10" style="width: 100px;float: right;">
							<db:select dblabel="tag_name"  dbvalue="tag_name" sqlKey="tags_all" styleClass="input-text radius" name="" onchange="addTags2($(this))"/>
						</div>
					</div>
					<div id="tags">&nbsp;
						<c:forEach  items="${articleTags }" var="item">
							<input type="text" class="input-text radius" style="width: 50px;" value="${item.tag_name }">
							<button class="btn btn-primary-outline radius deleteTags" data-id="${item.id }"  style="margin-left: -5px;width: 40px;font-size: 10px;" type="button">删除</button>&nbsp;
						</c:forEach>
					</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-1 col-sm-2">address：</label>
				<div class="formControls col-xs-2 col-sm-2">
					<input type="text" class="input-text radius" value="${article.address}"  name="article.address">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">文章摘要：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<textarea name="article.summary"  class="textarea radius" datatype="*10-200"  id="summary" placeholder="说点什么...最少输入10个字符"   >${article.summary }</textarea>
					<p class="textarea-numberbar"><em class="textarea-length">10</em>/200</p>
					<button onclick="getContent()" type="button" class="btn btn-primary radius" style="float: right;margin-right: 100px;margin-top: 5px;"><i class="Hui-iconfont">&#xe60c;</i>从内容提取</button>
				</div>
			</div>
			<!-- <div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">允许评论：</label>
				<div class="formControls col-xs-8 col-sm-9 skin-minimal">
					<div class="check-box">
						<input type="checkbox" id="checkbox-pinglun">
						<label for="checkbox-pinglun">&nbsp;</label>
					</div>
				</div>
			</div> -->
			<%-- <div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">展示开始日期：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" name="article.start_time" value="${article.start_time }" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'datemax\')||\'%y-%M-%d\'}'})" id="datemin" class="input-text radius Wdate">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">展示结束日期：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" name="article.end_time" value="${article.end_time }" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'datemin\')}'})" id="datemax" class="input-text radius Wdate">
				</div>
			</div> --%>
			<!-- <div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">使用独立模版：</label>
				<div class="formControls col-xs-8 col-sm-9 skin-minimal">
					<div class="check-box">
						<input type="checkbox" id="checkbox-moban">
						<label for="checkbox-moban">&nbsp;</label>
					</div>
					<button onClick="mobanxuanze()" class="btn btn-default radius ml-10">选择模版</button>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">缩略图：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<div class="uploader-thum-container">
						<div id="fileList" class="uploader-list"></div>
						<div id="filePicker">选择图片</div>
						<button id="btn-star" class="btn btn-default btn-uploadstar radius ml-10">开始上传</button>
					</div>
				</div>
			</div> -->
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">图片上传：</label>
				<input type="hidden" name="article.image_url" value="${article.image_url}" />
				<div class="formControls col-xs-8 col-sm-9">
					<div style="width: 400px;height: 250px;" id="picker">
						<img id="upload_file_preview" style="width: 400px;height: 250px;" alt="上传图片" src="${x:getUrl(article.image_url)}" onerror="this.src='static/images/no_img.png'">
					</div>
					<div style="position: absolute;bottom: 0px;left: 450px;padding: 5px;" class="statusBar">
						<div class="pic-info info">
							图片名称：<span style="color:rgb(70, 78, 99);" id="upload_file_name">未选择任何文件</span>&nbsp;&nbsp;&nbsp;
							图片大小：<span style="color:rgb(70, 78, 99);" id="upload_file_size">0KB</span>
						</div>
						<div class="btns" style="margin-top: 10px;">
							<button class="btn-primary state-ready btn radius" type="button" onclick="commonUpload(callBack)">读取服务器</button>&nbsp;&nbsp;&nbsp;
							<button class="btn-primary state-ready btn radius" id="upload_file_btn" type="button" disabled="disabled" onclick="doUpload()">开始上传</button>&nbsp;&nbsp;&nbsp;
							<button class="btn btn-clear radius" type="button" onclick="uploadPicClear()">清除</button>
						</div>
					</div>
				</div>
			</div>
			<div class="row cl">
				<div class="col-xs-1"></div>
				<div class="formControls col-xs-10"> 
	 				  <script id="container" name="article.content" type="text/plain" style="width:100%;height:400px;">${article.content}</script>
				</div>
				<div class="col-xs-1"></div>
			</div>
			<div class="row cl">
				<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2"> 
					<button onClick="commonSubmit('system/article/save',$('#form'))" class="btn btn-primary radius" type="button"><i class="Hui-iconfont">&#xe632;</i> 提 交 </button>
					<button onClick="javascript:parent.layer.closeAll();window.close();" class="btn btn-default radius ml-50" type="button">&nbsp;&nbsp;取消&nbsp;&nbsp;</button>
				</div>
			</div>
		</form>
		<button onClick="commonSubmit('system/article/save',$('#form'))" style="top: 150px;right: 30px;position: fixed;" type="button" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe632;</i> 提交 </button>
		<button onClick="javascript:parent.layer.closeAll();window.close();" type="button" style="top: 190px;right: 30px;position: fixed;" class="btn btn-primary radius"> <i class="Hui-iconfont">&#xe6a6;</i>关闭 </button>
</article>
</layout:override>
<script type="text/html" id="propLayer">
	<div>
	
	</div>
</script>
<layout:override name="script">
	<script type="text/javascript" src="static/ckfinder/ckfinder.js"></script>
    <!-- ueditor-->
    <script type="text/javascript" charset="utf-8" src="static/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="static/ueditor/ueditor.all.js"></script>
    <link rel="stylesheet" type="text/css" href="static/lib/webuploader/0.1.5/webuploader.css">
    <script type="text/javascript" src="static/lib/webuploader/0.1.5/webuploader.min.js"></script>
    <script type="text/javascript">
    	var uploader;
    	var editor;
        $(function(){
            // ueditor
            editor = UE.getEditor('container');
            uploader = WebUploader.create({
            	
            	//禁掉图片拖拽
            	disableGlobalDnd: true,

                // swf文件路径
                swf: 'static/lib/webuploader/0.1.5/Uploader.swf',

                // 文件接收服务端。
                server: 'uploadfile/upload',

                // 选择文件的按钮。可选。
                // 内部根据当前运行是创建，可能是input元素，也可能是flash.
                pick: '#picker',

                // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
                resize: false,
                
             	// 只允许选择图片文件。
                accept: {
                    title: 'Images',
                    extensions: 'gif,jpg,jpeg,bmp,png',
                    mimeTypes: 'image/*'
                }
            });
            $(".webuploader-pick").css("padding","0px");
         	// 当有文件被添加进队列的时候
            uploader.on( 'fileQueued', function( file ) {
                $("#upload_file_name").html(file.name);
                var size = file.size;
                size = size/1024;
                size = size.toFixed(2);
                $("#upload_file_size").html(size+"KB");
                // 创建缩略图
                // 如果为非图片文件，可以不用调用此方法。
                // thumbnailWidth x thumbnailHeight 为 100 x 100
                uploader.makeThumb( file, function( error, src ) {

                    $("#upload_file_preview").attr( 'src', src );
                }, 400, 250 );
                $("#upload_file_btn").removeAttr("disabled");
            });
         	//当文件上传完成后
         	uploader.on('uploadSuccess',function(file,ret){
         		if(ret.code == "0"){
	         		$("#form input[name='article.image_url']").val(ret.data);
         			layer.msg("文件上传成功！");
         		}else{
         			layer.msg(ret.message);
         		}
         	});
        });
        
        function doUpload(){
        	if(uploader.getFiles().length > 0)
        		uploader.upload();
        }
        
        function uploadPicClear(){
        	$("#upload_file_preview").attr("src","static/images/no_img.png");
        	$("#upload_file_name").html("未选择任何文件");
        	$("#upload_file_size").html("0KB");
        	$("#upload_file_btn").attr("disabled",true);
        	$("#form input[name='article.image_url']").val("");
        	if(uploader)
        		uploader.reset();
        }
        function addTags(){
        	$("#tags").append("&nbsp;<input type='text' class='input-text radius' style='width:75px;' name='tags' value=''/> &nbsp;");
        	$("#tags").append('<button class="btn btn-primary-outline radius deleteTags" onclick="deleteTags($(this))"  style="margin-left: -5px;width: 40px;font-size: 10px;" type="button">删除</button>');
        }
        function addTags2(obj){
        	$("#tags").append("<input type='text' class='input-text radius' style='width:75px;' name='tags' value='"+obj.val()+"'/> &nbsp;");
        	$("#tags").append('<button class="btn btn-primary-outline radius deleteTags" data-id="'+obj.val()+'" onclick="deleteTags($(this))"  style="margin-left: -5px;width: 40px;font-size: 10px;" type="button">删除</button>&nbsp;');
        }
        $(function(){
	        $(".deleteTags").click(function(){
	        	var id=$(this).attr("data-id");
	        	$.ajax({url:"system/article/deleteTags/"+id,async:false});
	        	$(this).prev().remove();
	        	$(this).remove();
	        });
       });
       function deleteTags(obj){
    		//var id=obj.attr("data-id");
        	//$.ajax({url:"system/article/deleteTags/"+id,async:false});
        	obj.prev().remove();
        	obj.remove();
       }
       function submitFillContentForm(){
    	   jQuery.ajax({
				url : "ext/jsoup/spider",
				type : "post",
				dataType:"json",
				data : $("#fillContentForm").serialize(),
				success : function(result) {
					if(result.code=="0"){
						layer.msg("采集成功");
						$("#form input[name='article.title']").val(result.data.title);
						editor.setContent(result.data.content);
					}else{
						layer.msg(result.message);
					}
				},error :function(result){
					layer.msg("数据加载失败！");
				}
			});
       }
       
       function callBack(url){
    	   var pattern = /\/\d{4}\/\d{2}\/\d{2}\/.*/;
    	   var res = pattern.exec(url);
    	   $("#form input[name='article.image_url']").val(res);
    	   $("#form #upload_file_preview").attr("src",url);
    	   $("#upload_file_name").html("未选择任何文件");
	       $("#upload_file_size").html("0KB");
	       $("#upload_file_btn").attr("disabled",true);
	       if(uploader)
	           uploader.reset();
       }
       function getContent(){
    	   var ue=UE.getEditor('container');
    	   var s=ue.getPlainTxt();
    	   $.ajax({
				url: 'system/article/getSummary',
			    data: {"content":s},
			    dataType:'text',
			    type : "post",
			    success: function(result){
			    	$("#summary").val(result);
			    }
			});
       }
       function getKeyWord(){
    	   $.ajax({
				url: 'system/article/getKeyword',
			    data: {"title":$("#title").val()},
			    dataType:'text',
			    success: function(result){
			    	$("#keyword").val(result);
			    }
			});
       }
    </script>
</layout:override>

<%@ include file="/commons/layout_admin.jsp" %>