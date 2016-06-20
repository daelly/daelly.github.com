<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<layout:override name="head">
	<title><c:if test="${article.title==null }">新增</c:if> ${article.title } </title>
	<layout:head datePicker="true" icheck="true" validForm="true" layer="true"></layout:head>
</layout:override>
<layout:override name="content">
	<article class="page-container mb-50">
		<form class="form form-horizontal" action="/system/article/save" method="post" id="form">
			<input name="article.id" type="hidden" value="${article.id}"/>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>文章标题：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text radius" value="${article.title }"   name="article.title">
				</div>
			</div>
			<div class="row cl" style="display: none;">
				<label class="form-label col-xs-4 col-sm-2">图片上传：</label>
				<input type="hidden" name="article.image_url" value="${article.image_url}" />
				<div class="formControls col-xs-8 col-sm-9">
					<div style="width: 400px;height: 250px;" id="picker">
						<img id="upload_file_preview" style="width: 400px;height: 250px;" alt="上传图片" src="${article.image_url }" onerror="this.src='static/images/no_img.png'">
					</div>
					<div style="position: absolute;bottom: 0px;left: 450px;padding: 5px;" class="statusBar">
						<div class="pic-info info">
							图片名称：<span style="color:rgb(70, 78, 99);" id="upload_file_name">未选择任何文件</span>&nbsp;&nbsp;&nbsp;
							图片大小：<span style="color:rgb(70, 78, 99);" id="upload_file_size">0KB</span>
						</div>
						<div class="btns" style="margin-top: 10px;">
							<button class="btn-primary state-ready btn" id="upload_file_btn" type="button" disabled="disabled" onclick="doUpload()">开始上传</button>&nbsp;&nbsp;&nbsp;
							<button class="btn btn-clear" type="button" onclick="uploadPicClear()">清除</button>
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
					<button onClick="commonSubmit('system/article/save')" class="btn btn-primary radius" type="button"><i class="Hui-iconfont">&#xe632;</i> 提 交 </button>
					<button onClick="javascript:parent.layer.closeAll();" class="btn btn-default radius ml-50" type="button">&nbsp;&nbsp;取消&nbsp;&nbsp;</button>
				</div>
			</div>
		</form>
</article>
</layout:override>

<layout:override name="script">
    <!-- ueditor-->
    <script type="text/javascript" charset="utf-8" src="static/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="static/ueditor/ueditor.all.js"></script>
    <link rel="stylesheet" type="text/css" href="static/lib/webuploader/0.1.5/webuploader.css">
    <script type="text/javascript" src="static/lib/webuploader/0.1.5/webuploader.min.js"></script>
    <script type="text/javascript">
    	var uploader;
        $(function(){
            // ueditor
            var editor = UE.getEditor('container');
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
        	$("#tags").append("<input type='text' class='input-text radius' style='width:75px;' name='tags' value=''/> &nbsp;");
        }
        function addTags2(obj){
        	$("#tags").append("<input type='text' class='input-text radius' style='width:75px;' name='tags' value='"+obj.val()+"'/> &nbsp;");
        }
        $(function(){
        $(".deleteTags").click(function(){
        	var id=$(this).attr("data-id");
        	$.ajax({url:"system/article/deleteTags/"+id,async:false});
        	$(this).prev().remove();
        	$(this).remove();
        });
      });
    </script>
</layout:override>

<%@ include file="/commons/layout_admin.jsp" %>