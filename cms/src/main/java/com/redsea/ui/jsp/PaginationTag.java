package com.redsea.ui.jsp;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author liqingyang
 * @date 2016-5-24 下午4:16:33
 */
public class PaginationTag extends TagSupport {

	private static final long serialVersionUID = 6204436114398275157L;
	
	private String action;
	
	private String totalPage;
	
	private String pageNumber;
	
	private String separator;
	
	private boolean singlePageShow = true;
	
	@Override
	public int doStartTag() throws JspException{
		JspWriter out = this.pageContext.getOut();
		try {
			int total = Integer.parseInt(totalPage);
			int pageNo = Integer.parseInt(pageNumber);
			StringBuffer sb = new StringBuffer();
			if(total <= 0)
				total = 1;
			//如果只有一页并且指定不显示
			if(!singlePageShow && total==1){
				return SKIP_BODY;
			}
			if('/'==action.charAt(action.length()-1)){
				separator = "";
			}else{
				separator = "-";
			}
			int beginPage = pageNo-5<1?1:pageNo-5;
			int endPage = beginPage+9>total?total:beginPage+9;
			int prevPage = pageNo-1<1?1:pageNo-1;
			int nextPage = pageNo+1>total?total:pageNo+1;
			sb.append("<a href='").append(action).append(separator).append(prevPage).append(".html'><</a>\n");
			for(int i = beginPage;i<=endPage;i++){
				if(i==pageNo){
					sb.append("<a style='background-color:#bdbdbd!important;color:#fff!important;' href='").append(action).append(separator).append(i).append(".html'>").append(i).append("</a>\n");
					continue;
				}
				sb.append("<a href='").append(action).append(separator).append(i).append(".html'>").append(i).append("</a>\n");
			}
			sb.append("<a href='").append(action).append(separator).append(nextPage).append(".html'>></a>");
			out.println(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(String totalPage) {
		this.totalPage = totalPage;
	}

	public String getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}

	public boolean isSinglePageShow() {
		return singlePageShow;
	}

	public void setSinglePageShow(boolean singlePageShow) {
		this.singlePageShow = singlePageShow;
	}

}
