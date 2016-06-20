<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<layout:override name="head">
	<title><c:if test="${topic.title==null}">新增</c:if> ${topic.title} </title>
	<layout:head datePicker="true" icheck="true" validForm="true" layer="true"></layout:head>
	<link rel="stylesheet" type="text/css" href="static/js/jcrop/css/jquery.Jcrop.min.css"/>
	<script src="static/js/jcrop/js/jquery.Jcrop.min.js" type="text/javascript"></script>
	<style>
		button:disabled{
				background-color: #ccc!important;
				border-color: #ccc!important;
		}
	</style>
</layout:override>
<layout:override name="content">
	<topic class="page-container mb-50">
		<form class="form form-horizontal" action="/system/topic/save" method="post" id="form">
			 <input name="flag" type="hidden" value="${flag}"/> 
			<div class="row cl">
				<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>ID：</label>
				<div class="formControls col-xs-3 col-sm-2">
					<input type="text" class="input-text radius" datatype="*" <c:if test="${flag=='edit' }">readonly="readonly"</c:if>  onchange="validationId()" value="${topic.id}"  name="topic.id">
				</div>
				<label class="form-label col-xs-3 col-sm-2"><span class="c-red">*</span>专题名称：</label>
				<div class="formControls col-xs-3 col-sm-4">
					<input type="text" class="input-text radius" value="${topic.title }" datatype="*"  name="topic.title">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-2 col-sm-2"><span class="c-red">*</span>模板：</label>
				<div class="formControls col-xs-2 col-sm-2"> 
					<span class="select-box">
						<x:select styleClass="select" pvalue="topic_template" value="${topic.template }" name="topic.template"/>
					</span> 
				 </div>
				 <label class="form-label col-xs-2 col-sm-1">显示：</label>
				<div class="formControls col-xs-2 col-sm-2">
					<x:radio pvalue="status" name="topic.status" value="${topic.status}"/>
				</div>
				<label class="form-label col-xs-2 col-sm-1">发布日期：</label>
				<div class="formControls col-xs-1 col-sm-2">
					<input type="text" name="topic.publish_time" datatype="*" value="${topic.publish_time }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="input-text radius Wdate">
				</div>
			</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>关键字：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text radius" value="${topic.keywords }" datatype="*5-100" name="topic.keywords">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">文章摘要：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<textarea name="topic.description"  class="textarea radius"  placeholder="说点什么...最少输入10个字符" datatype="*10-200" dragonfly="true" nullmsg="备注不能为空！" onKeyUp="textarealength(this,200)">${topic.description }</textarea>
					<p class="textarea-numberbar"><em class="textarea-length">0</em>/200</p>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">标题图片：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<div style="width: 400px;height: 250px;" id="picker">
						<img id="upload_file_preview" style="width: 400px;height: 250px;" alt="上传图片" src="${x:getUrl(topic.titleImg) }" onerror="this.src='static/images/no_img.png'">
					</div>
					<input type="hidden" datatype="*" name="topic.titleImg" value="${topic.titleImg}" />
					<div style="position: absolute;bottom: 0px;left: 450px;padding: 5px;" class="statusBar">
						<div class="pic-info info">
							图片名称：<span style="color:rgb(70, 78, 99);" id="upload_file_name">未选择任何文件</span>&nbsp;&nbsp;&nbsp;
							图片大小：<span style="color:rgb(70, 78, 99);" id="upload_file_size">0KB</span>
						</div>
						<div class="btns" style="margin-top: 10px;">
							<button class="btn-primary state-ready btn" type="button" onclick="commonUpload(callBack1)">读取服务器</button>&nbsp;&nbsp;&nbsp;
							<button class="btn-primary state-ready btn" id="upload_file_btn" type="button" disabled="disabled" onclick="doUpload()">开始上传</button>&nbsp;&nbsp;&nbsp;
							<button class="btn btn-clear" type="button" onclick="uploadPicClear()">清除</button>
						</div>
					</div>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">内容图片：</label>
				<input type="hidden" name="topic.contentImg" value="${topic.contentImg}" />
				<div class="formControls col-xs-8 col-sm-9">
					<div style="width: 400px;height: 250px;" id="picker2">
						<img id="upload_file_preview2" style="width: 700px;height: 250px;" alt="上传图片" src="${x:getUrl(topic.contentImg) }" onerror="this.src='static/images/no_img.png'">
					</div>
					<div style="position: absolute;bottom: 0px;left: 720px;padding: 5px;" class="statusBar">
						<div class="pic-info info">
							图片名称：<span style="color:rgb(70, 78, 99);" id="upload_file_name2">未选择任何文件</span>&nbsp;&nbsp;&nbsp;
							图片大小：<span style="color:rgb(70, 78, 99);" id="upload_file_size2">0KB</span>
						</div>
						<div class="btns" style="margin-top: 10px;">
							<button class="btn-primary state-ready btn" type="button" onclick="commonUpload(callBack2)">读取服务器</button>&nbsp;&nbsp;&nbsp;
							<button class="btn-primary state-ready btn" id="upload_file_btn2" type="button" disabled="disabled" onclick="doUpload(2)">开始上传</button>&nbsp;&nbsp;&nbsp;
							<button class="btn btn-clear" type="button" onclick="uploadPicClear(2)">清除</button>
						</div>
					</div>
				</div>
			</div>
			<div class="row cl">
				<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2"> 
					<button onClick="commonSubmit('system/topic/save')" class="btn btn-primary radius" type="button"><i class="Hui-iconfont">&#xe632;</i> 提 交 </button>
					<button onClick="javascript:parent.layer.closeAll();" class="btn btn-default radius ml-50" type="button">&nbsp;&nbsp;取消&nbsp;&nbsp;</button>
				</div>
			</div>
		</form>
</topic>
</layout:override>

<layout:override name="script">
    <!-- ueditor-->
    <script type="text/javascript" src="static/ckfinder/ckfinder.js"></script>
    <link rel="stylesheet" type="text/css" href="static/lib/webuploader/0.1.5/webuploader.css">
    <script type="text/javascript" src="static/lib/webuploader/0.1.5/webuploader.min.js"></script>
    <script type="text/javascript">
    	var uploader,uploader2;
        $(function(){
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
	         		$("#form input[name='topic.titleImg']").val(ret.data);
         			layer.msg("文件上传成功！");
         		}else{
         			layer.msg(ret.message);
         		}
         	});
         	uploader2 = WebUploader.create({
            	
            	//禁掉图片拖拽
            	disableGlobalDnd: true,

                // swf文件路径
                swf: 'static/lib/webuploader/0.1.5/Uploader.swf',

                // 文件接收服务端。
                server: 'uploadfile/upload',

                // 选择文件的按钮。可选。
                // 内部根据当前运行是创建，可能是input元素，也可能是flash.
                pick: '#picker2',
                
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
         	uploader2.on( 'fileQueued', function( file ) {
                $("#upload_file_name2").html(file.name);
                var size = file.size;
                size = size/1024;
                size = size.toFixed(2);
                $("#upload_file_size2").html(size+"KB");
                // 创建缩略图
                // 如果为非图片文件，可以不用调用此方法。
                // thumbnailWidth x thumbnailHeight 为 100 x 100
                uploader.makeThumb( file, function( error, src ) {

                    $("#upload_file_preview2").attr( 'src', src );
                }, 400, 250 );
                $("#upload_file_btn2").removeAttr("disabled");
            });
         	
         	uploader2.on('uploadSuccess',function(file,ret){
         		if(ret.code == "0"){
	         		$("#form input[name='topic.contentImg']").val(ret.data);
         			layer.msg("文件上传成功！");
         		}else{
         			layer.msg(ret.message);
         		}
         	});
        });
        
        function doUpload(arg){
        	if(arg=="2"){
	        	if(uploader2.getFiles().length > 0)
	        		uploader2.upload();
        	}else{
        		if(uploader.getFiles().length > 0)
	        		uploader.upload();
        	}
        }
        
        function uploadPicClear(arg){
        	arg=arg||"";
        	$("#upload_file_preview"+arg).attr("src","static/images/no_img.png");
        	$("#upload_file_name"+arg).html("未选择任何文件");
        	$("#upload_file_size"+arg).html("0KB");
        	$("#upload_file_btn"+arg).attr("disabled",true);
        	if(arg==""){
	        	$("#form input[name='topic.titleImg']").val("");
	        	if(uploader)
	        		uploader.reset();
        	}else{
        		$("#form input[name='topic.contentImg']").val("");
        		if(uploader2)
        			uploader2.reset();
        	}
        }
        
        function callBack1(url){
     	   var pattern = /\/\d{4}\/\d{2}\/\d{2}\/.*/;
     	   var res = pattern.exec(url);
     	   $("#form input[name='topic.titleImg']").val(res);
     	   $("#form #upload_file_preview").attr("src",url);
     	   $("#upload_file_name").html("未选择任何文件");
 	       $("#upload_file_size").html("0KB");
 	       $("#upload_file_btn").attr("disabled",true);
 	       if(uploader)
 	           uploader.reset();
        }
        function callBack2(url){
     	   var pattern = /\/\d{4}\/\d{2}\/\d{2}\/.*/;
     	   var res = pattern.exec(url);
     	   $("#form input[name='topic.contentImg']").val(res);
     	   $("#form #upload_file_preview2").attr("src",url);
     	   $("#upload_file_name2").html("未选择任何文件");
 	       $("#upload_file_size2").html("0KB");
 	       $("#upload_file_btn2").attr("disabled",true);
 	       if(uploader2)
 	           uploader2.reset();
        }
        function validationId(){
        	
        }
    </script>
</layout:override>

<%@ include file="/commons/layout_admin.jsp" %>