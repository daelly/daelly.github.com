<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
	var action='${action}';
	if(action!=null&&action!=''){
		var totalPage=${page.totalPage};
		totalPage = totalPage<1?1:totalPage;
		var pageNumber=${page.pageNumber};
		var beginPage = pageNumber-5<1?1:pageNumber-5;
		var endPage = beginPage+9>totalPage?totalPage:beginPage+9;
		var prevPage = pageNumber-1<1?1:pageNumber-1;
		var nextPage = pageNumber+1>totalPage?totalPage:pageNumber+1;
		$(".index-list-page").append("<a href='${action}-"+prevPage+".html'><</a>\n");
		var str=action.charAt(action.length -1);
		if(str=='/'){
			str="";
		}else{
		 	str="-";
		}
		for(var i=beginPage;i<=endPage;i++){
			if(i==pageNumber){
				$(".index-list-page").append("<a style='background-color:#bdbdbd!important;color:#fff!important;' href="+action+""+str+""+i+".html>"+i+"</a>\n");
				continue;
			}
			$(".index-list-page").append("<a href="+action+""+str+""+i+".html>"+i+"</a>\n");
		}
		$(".index-list-page").append("<a href='${action}"+str+""+nextPage+".html'>></a>");
		
	}

</script>
