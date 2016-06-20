<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<layout:override name="head">
<title>内容数据</title>
<style>
	html body {
		height: 100%;
	}
	div#rMenu {position:absolute; visibility:hidden; top:0; background-color: #555;text-align: left;padding: 1px;}
	div#rMenu ul li{
		margin: 1px 0;
		padding: 0 5px;
		cursor: pointer;
		list-style: none outside none;
		background-color: rgba(228, 243, 249, 0.97);
	}
</style>
<layout:head layer="true" icheck="true" tree3="true"></layout:head>
</layout:override>
<layout:override name="content">
	<div style="float: left;height: 900px;">
		<ul id="tree" class="ztree" data-toggle="context" data-target="#context-menu"  style="background:#fff;"></ul>
	</div>
	<div id="rMenu">
		<ul>
			<li id="m_add" onclick="addChild();">增加子栏目</li>
			<li id="m_del" onclick="deleteFolder();">删除本栏目</li>
			<!-- <li id="m_check" onclick="alert(1);">Check节点</li>
			<li id="m_unCheck" onclick="alert(1);">unCheck节点</li>
			<li id="m_reset" onclick="alert(1);">恢复zTree</li> -->
		</ul>
	</div>
	<div style="float:right;width:87%;height: 900px;">
		<iframe id="folderIframe" frameborder="0" scrolling="no" width="100%" height="100%" src="system/article/index?folder_id=">
		</iframe>
	</div>
</layout:override>

<layout:override name="script">
<script type="text/javascript">
	var zTree;
	var selectedNode;
	var setting = {
		data:{
			simpleData: {
				enable:true,
				idKey: "id",//分公司id作为节点id
				pIdKey: "parent_id",//片区id作为父节点id
				rootPId: 0  //根节点id默认null
			},key:{
				name:"name",  //公司全名作为节点名称
				children:"children",
				title:"description"
			}
		},
		view: {
			fontCss: {"font-weight":"normal","font-family":'arial,microsoft Yahei'},
			showLine: true
		},
		edit:{
			enable:false,
			drag:{
				prev: false,
				next:true
			}
		},
		//事件
		callback:{
			//点击事件回调函数
			beforeClick: function(treeId, treeNode) {
				$("#folderIframe").attr("src","system/article/index?folder_id="+treeNode.id);
				/* if(treeNode.isParent == false){//叶节点
				}else{
					$("#folderIframe").attr("src","system/folder/index?parent_id="+treeNode.id);
				} */
			},
			//双击展开子节点
			beforeDblClick:function(treeId,treeNode){
				zTree.expandNode(treeNode);
			}
		}
	};
	var zTreeNodes = ${treeNodes};
	$(function(){
		zTree = $.fn.zTree.init($("#tree"), setting, zTreeNodes);
		zTree.expandAll(true);
	});
</script>
</layout:override>
<%@ include file="/commons/layout_admin.jsp" %>