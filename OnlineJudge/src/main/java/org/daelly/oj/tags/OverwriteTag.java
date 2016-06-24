package org.daelly.oj.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class OverwriteTag extends BodyTagSupport {

	private static final long serialVersionUID = 2006140050632449976L;
	
	private String name;
	
	@Override
	public int doStartTag() throws JspException{
		return isOverrided() ? SKIP_BODY : EVAL_BODY_BUFFERED;
	}
	
	@Override
	public int doEndTag() throws JspException{
		if(isOverrided()) {
			return EVAL_PAGE;
		}
		BodyContent b = getBodyContent();
		String varName = Utils.getOverrideVariableName(name);
		pageContext.getRequest().setAttribute(varName, b.getString());
		return EVAL_PAGE;
	}
	
	private boolean isOverrided() {
		String varName = Utils.getOverrideVariableName(name);
		return pageContext.getRequest().getAttribute(varName) != null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}