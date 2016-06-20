<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<script type="text/javascript">
	var Page = {};
	
	/**
	 * 分页处理，页面需要include分页jsp
	 * @param id 分页所在的form的id，分页要在一个form内
	 * @param result 列表数据
	 * @param fn 回调方法，获取数据、分页、显示的方法，用在切换页数时调用
	 * @param show 暂时无用
	 */
	 Page.createPage = function (id,result,fn,show){
		var show = show||false;
		var count = result.totalRow;
		var pageNo = result.pageNumber;
		var pageSize = result.pageSize;
		var pageCount = result.totalPage;
		
		$("#"+id+" input[name='page.pageNo']").val(pageNo);
		$("#"+id+" #totalPage").html(count);
		$("#"+id+" #pageCount").html(pageCount);
		$("#"+id+" #pageSize").val(pageSize);
		
		var html = "";
		if (pageNo == 1) {
			html = "<a class='paginate_button previous disabled' style='color: #777;cursor: not-allowed;background-color: #fff;border-color: #ddd;'>上一页</a>";
		} else {
			html = "<a href='javascript:void(0)' pageNum='"+ (pageNo-1) +"' class='paginate_button previous disabled'>上一页</a>";
		}
		
		var start = pageNo - 2 >= 1 ? pageNo -2 : 1;
		html += "<span>";
		for (var i = start; i <= pageCount && i - start < 10; i++) {
			if ( i == pageNo) {
				html += "<a href='javascript:void(0)' class='paginate_button current' pageNum='" + i + "'>" + i + "</a>";
			} else {
				html += "<a  href='javascript:void(0)' class='paginate_button' pageNum='" + i + "'>" + i + "</a>";
			}
		}
		html += "</span>";
		
		if (pageNo == pageCount || pageCount < 1 || pageNo < 1) {
			html += "<a href='javascript:void(0)' class='paginate_button next disabled' style='color: #777;cursor: not-allowed;background-color: #fff;border-color: #ddd;'>下一页</a>";
		} else {
			html += "<a href='javascript:void(0)' class='paginate_button next disabled' pageNum='"+ (pageNo+1) +"'>下一页</a>";
		}
	    
	    $("#"+id+" #pageNum").html(html);
	    
	    $("#"+id+" #pageNum a[pageNum]").click(function(){
	    	$("#"+id+" #pageNum a[pageNum]").removeClass('current');
	   		$(this).addClass('current');
	    	var pageNo = $(this).attr("pageNum");
	    	$("#"+id+" #pageNo").val(pageNo);
	    	fn();
	    });
	    
	    //防止chang事件递归调用：2的n次方
	    $("#"+id+" #pageSize").unbind("change");
	    
	    $("#"+id+" #pageSize").on("change",function(){
	    	$("#"+id+" #pageNo").val(1);
	    	fn();
	    });
	};
</script>
<div class="dataTables_wrapper">
	<input type="hidden" name="page.pageNo" id="pageNo" value="1">
	<div class="dataTables_info" role="status" aria-live="polite" style="display:inline-block">显示 
		<select name="page.pageSize" id="pageSize" class="select radius " style="width: 50px;">
			<option value="5">5</option>
			<option value="10"  selected="selected">10</option>
			<option value="15">15</option>
			<option value="25">25</option>
			<option value="50">50</option>
			<option value="100">100</option>
		</select>&nbsp;条
	 共 <b id="totalPage"></b>&nbsp;条记录 | &nbsp;<b id="pageCount"></b>页</div>
	<div class="dataTables_paginate paging_simple_numbers" id="pageNum">
	</div>
</div>