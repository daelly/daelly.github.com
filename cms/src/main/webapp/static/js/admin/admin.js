function commonDel(url){
			layer.confirm('删除须谨慎，确认要删除吗？',function(index){
				$.ajax({
					url: url,
				    data: null,
				    success: function(result){
				    	if(result.code==0){
				    		layer.msg('删除成功');
				    		loadDataList();
				    	}else{
				    		layer.msg('删除失败');
				    	}
				    }
				});
			});
 }
function commonBatchDel(url){
	layer.confirm('删除须谨慎，确认要删除选中的吗？',function(index){
		$.ajax({
			url: url,
			type : "post",
			data: $("#searchForm").serialize(),
			success: function(result){
				if(result.code==0){
					layer.msg('删除成功');
					loadDataList();
				}else{
					layer.msg('删除失败');
				}
			}
		});
	});
}
function commonLoadDataList(url){
	jQuery.ajax({
		url : url ,
		type : "post",
		dataType:"json",
		data : $("#searchForm").serialize(),
		success : function(result) {
			$("#searchForm #dataList").empty();
			rendListByTemplate($("#listTemplate"), $("#searchForm #dataList"), result.list);
			Page.createPage("searchForm",result,function(){loadDataList();});
			fillListTable("tableHead");
		},error :function(result){
			
		}
	});
}
/*function commonSubmit(url){
		$.ajax({
			   url: url,
			   type : "post",
			   data: $('form').serialize(),
			   dataType:'json',
			   success: function(result){
				   layer.msg("保存成功",{time:1500},function(){
					   parent.loadDataList();
					   parent.layer.closeAll();
				   });
			   },error:function(){
				   alert("发生错误,稍后再试!");
			   }
	});
}*/

/*function commonSubmit(url,callback){
	if(!callback)
		callback = function(result){
		    layer.msg("保存成功",{time:1500},function(){
		       if(parent.loadDataList)
		    	   parent.loadDataList();
			   parent.layer.closeAll();
		    });
		};
	var valid = $("form").Validform({
		ajaxPost:true,
		tiptype:4,
		callback:callback
	});
	valid.ajaxPost(false,true,url);
}*/
function commonSubmit(url,form,callback){
	if(!callback)
		callback = function(result){
		layer.msg("保存成功",{time:1500},function(){
			if(parent.loadDataList)
				parent.loadDataList();
			parent.layer.closeAll();
		});
	};
	if(!form){
		form=$("form");
	}
	var valid = form.Validform({
		ajaxPost:true,
		tiptype:4,
		callback:callback
	});
	valid.ajaxPost(false,true,url);
}
function fullShow(title,url){
	var index = layer.open({
		title:title,
		type: 2,
		content: url,
		maxmin: true
	});
	layer.full(index);
}
function getStr(state,state1,state2,str1,str2,defaultStr){
	if(state==state1){
		return str1;
	}else if(state==state2){
		return str2;
	}
	 return defaultStr;
}

/**
 * 调用CKFinder上传文件
 * @param callback 回调函数，第一个参数为图片所在服务器访问路径
 */
function commonUpload(callback){
	var finder = new CKFinder();
//	finder.basePath = '../';	// The path for the installation of CKFinder (default = "/ckfinder/").
	finder.selectActionFunction = function(fileUrl,data){
		callback(fileUrl,data);
	};
	finder.popup();
}
