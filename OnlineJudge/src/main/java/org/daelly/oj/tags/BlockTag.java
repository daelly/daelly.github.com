package org.daelly.oj.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
/**
 * 一个block表示页面的一个块
 * @author daelly
 *
 */
public class BlockTag extends BodyTagSupport {

	private static final long serialVersionUID = 2086792765717644547L;
	//模块名称
	private String name;
	
	@Override
	public int doStartTag() throws JspException{
		return getOverriedContent() == null ? EVAL_BODY_INCLUDE : SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException{
		String overriedContent = getOverriedContent();
		if(overriedContent == null) {
			return EVAL_PAGE;
		}
		
		try {
			pageContext.getOut().write(overriedContent);
		} catch (IOException e) {
			throw new JspException("write overridedContent occer IOException,block name:" + name, e);
		}
		return EVAL_PAGE;
	}
	
	private String getOverriedContent() {
		String varName = Utils.getOverrideVariableName(name);
		return (String) pageContext.getRequest().getAttribute(varName);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
