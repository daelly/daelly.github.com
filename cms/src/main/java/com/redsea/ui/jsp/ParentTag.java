package com.redsea.ui.jsp;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import com.jfinal.kit.StrKit;
/**
 * 
 * @author yeshujun
 */
public abstract class ParentTag extends TagSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//具体值或el表达式
	private Object value;
	
	//以下为html元素
	private String id;
	private String name;
	private String styleClass;
	private String size;
	private String style;
	private String title;
	private String disabled;
	private String multiple;
	private String onblur;
	private String onchange;
	private String onclick;
	private String onfocus;
	private String ondblclick;
	private String onmousedown;
	private String onmousemove;
	private String onmouseout;
	private String onmouseover;
	private String onmouseup;
	private String onkeydown;
	private String onkeypress;
	private String onkeyup;
	
	

	/**
	 * 具体tag类实现该方法
	 * @return
	 */
	public abstract String processResult();
	
	/**
	 * 
	 * @param value 具体值或EL表达式
	 * @return
	 * @throws JspException
	 */
	public Object getExValue(Object value){
         try {
        	if(value!=null){
        		return ExpressionEvaluatorManager.evaluate(value+"", value.toString(), Object.class, this, pageContext);
        	}
		} catch (JspException e) {
			e.printStackTrace();
		}  
		return null;
	}
	
	/**
	 *  EVAL_BODY_INCLUDE    当doStartTag()返回时，指明servlet应对标签体进行评估。
		SKIP_BODY    当doStartTag()返回时，指明servlet应忽视标签体。
		EVAL_PAGE    当doEndTag()返回时，指明页面其余部分应被评估。
		SKIP_PAGE    当doEndTag()返回时，指明页面其余部分就被跳过。
	 */
	public int doEndTag() throws JspException{
		String result = processResult();
		JspWriter out = pageContext.getOut();
		if (result != null && !result.equals("null")){
			try {
				out.print(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			try {
				out.print("");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return EVAL_PAGE;
	}
	
	public String getHtmlAttributes(){
		StringBuilder sb = new StringBuilder();
		if (StrKit.notBlank(name)){
			sb.append(" name=\"" + name + "\"");
		}
		if (StrKit.notBlank(id)){
			sb.append(" id=\"" + id + "\"");
		}
		if (StrKit.notBlank(styleClass)){
			sb.append(" class=\"" + styleClass + "\"");
		}
		if (StrKit.notBlank(disabled)){
			sb.append(" disabled=\"" + disabled + "\"");
		}
		if (StrKit.notBlank(multiple)){
			sb.append(" multiple=\"" + multiple + "\"");
		}
		if (StrKit.notBlank(onblur)){
			sb.append(" onblur=\"" + onblur + "\"");
		}
		if (StrKit.notBlank(onchange)){
			sb.append(" onchange=\"" + onchange + "\"");
		}
		if (StrKit.notBlank(onclick)){
			sb.append(" onclick=\"" + onclick + "\"");
		}
		if (StrKit.notBlank(onfocus)){
			sb.append(" onfocus=\"" + onfocus + "\"");
		}
		if (StrKit.notBlank(ondblclick)){
			sb.append(" ondblclick=\"" + ondblclick + "\"");
		}
		if (StrKit.notBlank(onmousedown)){
			sb.append(" onmousedown=\"" + onmousedown + "\"");
		}
		if (StrKit.notBlank(onmousemove)){
			sb.append(" onmousemove=\"" + onmousemove + "\"");
		}
		if (StrKit.notBlank(onmouseout)){
			sb.append(" onmouseout=\"" + onmouseout + "\"");
		}
		if (StrKit.notBlank(onmouseover)){
			sb.append(" onmouseover=\"" + onmouseover + "\"");
		}
		if (StrKit.notBlank(onmouseup)){
			sb.append(" onmouseup=\"" + onmouseup + "\"");
		}
		if (StrKit.notBlank(size)){
			sb.append(" size=\"" + size + "\"");
		}
		if (StrKit.notBlank(style)){
			sb.append(" style=\"" + style + "\"");
		}
		if (StrKit.notBlank(onkeydown)){
			sb.append(" onkeydown=\"" + onkeydown + "\"");
		}
		if (StrKit.notBlank(onkeypress)){
			sb.append(" onkeypress=\"" + onkeypress + "\"");
		}
		if (StrKit.notBlank(onkeyup)){
			sb.append(" onkeyup=\"" + onkeyup + "\"");
		}
		if (StrKit.notBlank(title)){
			sb.append(" title=\"" + title + "\"");
		}

		return sb.toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	public String getOnblur() {
		return onblur;
	}

	public void setOnblur(String onblur) {
		this.onblur = onblur;
	}

	public String getOnchange() {
		return onchange;
	}

	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getOnfocus() {
		return onfocus;
	}

	public void setOnfocus(String onfocus) {
		this.onfocus = onfocus;
	}

	public String getOndblclick() {
		return ondblclick;
	}

	public void setOndblclick(String ondblclick) {
		this.ondblclick = ondblclick;
	}

	public String getOnmousedown() {
		return onmousedown;
	}

	public void setOnmousedown(String onmousedown) {
		this.onmousedown = onmousedown;
	}

	public String getOnmousemove() {
		return onmousemove;
	}

	public void setOnmousemove(String onmousemove) {
		this.onmousemove = onmousemove;
	}

	public String getOnmouseout() {
		return onmouseout;
	}

	public void setOnmouseout(String onmouseout) {
		this.onmouseout = onmouseout;
	}

	public String getOnmouseover() {
		return onmouseover;
	}

	public void setOnmouseover(String onmouseover) {
		this.onmouseover = onmouseover;
	}

	public String getOnmouseup() {
		return onmouseup;
	}

	public void setOnmouseup(String onmouseup) {
		this.onmouseup = onmouseup;
	}

	public String getOnkeydown() {
		return onkeydown;
	}

	public void setOnkeydown(String onkeydown) {
		this.onkeydown = onkeydown;
	}

	public String getOnkeypress() {
		return onkeypress;
	}

	public void setOnkeypress(String onkeypress) {
		this.onkeypress = onkeypress;
	}

	public String getOnkeyup() {
		return onkeyup;
	}

	public void setOnkeyup(String onkeyup) {
		this.onkeyup = onkeyup;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	
}
