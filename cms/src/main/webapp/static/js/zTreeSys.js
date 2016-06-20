/*
以下js用于系统配置所有需要树形展示的数据
目前仅提供checkbox复选框,需要radio 后续定义
注意：
  1.引用时需定义隐藏文本框,和普通文本框ID属性
         隐藏文本框:存放key。
         普通文本框：展示value。
         展示数据zTree： div ul 标签必须指定的ID属性：名称自定义表示为
 	    信息来源:标示为 source
 	    ztree 的 div #id=menuContentSourceTree, ul #id=sourceTree
 	    隐藏文本框:#id=sourcezTreeIds
 	    普通文本框： #id=sourcezTreeName
 	    格式如下:
	       	展示数据zTree：<div id="menuContentSourceTree"><ul id="sourceTree"></ul></div>
	       	信息来源:<input type="text" name="source.name" id="sourcezTreeName" onclick="showSysTreeList('menuContentSourceTree',this); return false;"/>
	               <input type="hidden" name="source.id" id="sourcezTreeIds"/>
  2.文本事件： onclick="showSysTreeList('menuContentSourceTree',this); 参数必须是指定的ztree树形结构DIV#id属性(用户绑定mousedown事件)和标签对象(用于定位)。
*/
globalId="";//定义一个事件触发的传递的zTreeID属性全局对象
syscheckSetting = {
		check: {
			enable: true,
			chkboxType: {"Y":"", "N":""}
		},
		view: {
			dblClickExpand: false
		},
		data: {
			simpleData: {
			  enable: true
			}
		},
		callback: {
			beforeClick: sysbeforeClick,
			onCheck: sysonCheck
		}
};
checkboxSetting = {
		check: {
			enable: true,
			chkStyle: "checkbox",
			radioType: "all"
		},
		view: {
			dblClickExpand: false
		},
		data: {
			simpleData: {
			  enable: true
	     	}
		},
		callback: {
			onClick: sysonClick,
			onCheck: sysonCheck
		}
};
function sysbeforeClick(treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		zTree.checkNode(treeNode, !treeNode.checked, null, true);
		return false;
}
	
function sysonClick(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		zTree.checkNode(treeNode, !treeNode.checked, null, true);
		return false;
}
/*
zTree文本框单击事件
*/
function sysonCheck(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj(treeId),
		nodes = zTree.getCheckedNodes(true);
		var Property=treeId.substring(0,treeId.length-4).toString();
			var Names = "",Ids = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				var nodesId = nodes[i].id;
				if(nodesId != null ){
					if(nodesId.indexOf("_")!=0){
						Names += nodes[i].name + ",";
						Ids += nodes[i].id + ",";
					}
				}
			}
			if (Names.length > 0 ) Names = Names.substring(0, Names.length-1);
			if (Ids.length > 0 ) Ids = Ids.substring(0, Ids.length-1);
			$("#"+Property+"zTreeName").val(Names);
			$("#"+Property+"zTreeIds").val(Ids);
}
/*
显示zTree
*/
function showSysTreeList(treeId,e){
		globalId=treeId;
		var deptO = $(e);
		var deptOffset = $(e).offset();
		$("#"+treeId+"").css({left:deptOffset.left + "px", top:deptOffset.top + deptO.outerHeight() + "px"}).slideDown("fast");
		$("body").bind("mousedown", onBodyDownUser);
}
/*
zTree鼠标点击事件隐藏zTree调用函数
*/
function hideMenuUser() {
		$("#"+globalId+"").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDownUser);
}
function onBodyDownUser(event) {
	   if (!($(event.target).parents("#"+globalId+"").length>0)) {
			hideMenuUser();
	   }
}
		