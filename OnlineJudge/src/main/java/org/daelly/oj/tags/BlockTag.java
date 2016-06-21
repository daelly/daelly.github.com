package org.daelly.oj.tags;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.springframework.util.StringUtils;
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
		return super.doStartTag();
	}

	@Override
	public int doEndTag() throws JspException{
		ServletRequest request = pageContext.getRequest();
		//标签中的默认值
		String defaultContent = getBodyContent()==null?"":getBodyContent().toString();
		String bodyContent = (String) request.getAttribute(OverwriteTag.PREFIX+name);
		//如果页面没有重写该模块，则显示默认内容
		bodyContent = StringUtils.isEmpty(bodyContent)?defaultContent:bodyContent;
		try {
			pageContext.getOut().write(bodyContent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.doEndTag();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
