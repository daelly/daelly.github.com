package org.daelly.oj.tags;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.springframework.util.StringUtils;

public class OverwriteTag extends BodyTagSupport {

	private static final long serialVersionUID = 2006140050632449976L;
	
	public static final String PREFIX = "JspTemplateBlockName_";
	
	private String name;
	
	@Override
	public int doStartTag() throws JspException{
		return super.doStartTag();
	}
	
	@Override
	public int doEndTag() throws JspException{
		ServletRequest request = pageContext.getRequest();
		//将标签内容放入request中
		BodyContent bodyContent = getBodyContent();
		request.setAttribute(PREFIX+name, StringUtils.trimWhitespace(bodyContent.getString()));
		return super.doEndTag();
	}
}
