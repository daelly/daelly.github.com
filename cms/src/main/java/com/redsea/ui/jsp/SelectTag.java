package com.redsea.ui.jsp;

import java.util.List;

import com.jfinal.kit.StrKit;
import com.redsea.model.SysDict;
/**
 * 自定义select标签
 * @author yeshujun
 */
public class SelectTag extends DictionaryParentTag{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//第一个option
	private String headerValue = "";
	private boolean nullValue=true;//是否将第一个option设置成""值
	
	private String rules = "";//验证规则
	@Override
	public String processResult() {
		
		Object value = getExValue(getValue());
		
		StringBuilder sb = new StringBuilder("");
		
		List<SysDict> list = this.getSubList(getPvalue());
		
		if(list!=null && list.size()>0){
			
			headerValue = StrKit.isBlank(headerValue)?"----":headerValue;
			if(StrKit.notBlank(rules)){
				rules = "datatype=\""+rules+"\"";
			}
			sb.append("<select class=\"select\" "+rules+" " + getHtmlAttributes() + ">");
			if(nullValue){
				sb.append("<option value=\"\">").append(headerValue).append("</option>");
			}
			for(SysDict dd : list){
				
				sb.append("<option value=\""+dd.getValue()+"\"");
				
				if(dd.getValue().equals(value)){
					sb.append(" selected=\"selected\" ");
				}
				sb.append(">").append(dd.getLabel()).append("</option>");
			}
			
			sb.append("</select>");
			
		}
		
		return sb.toString();
	}


	public String getHeaderValue() {
		return headerValue;
	}


	public void setHeaderValue(String headerValue) {
		this.headerValue = headerValue;
	}


	public boolean isNullValue() {
		return nullValue;
	}

	public void setNullValue(boolean nullValue) {
		this.nullValue = nullValue;
	}

	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

}
