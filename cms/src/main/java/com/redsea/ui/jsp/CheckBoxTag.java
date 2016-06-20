package com.redsea.ui.jsp;

import java.util.List;

import com.jfinal.kit.StrKit;
import com.redsea.model.SysDict;
/**
 * 自定义checkbox标签
 * @author yeshujun
 */
public class CheckBoxTag extends DictionaryParentTag{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nbspnum="3";//空格&nbsp;的数量，默认是3个
	private String splitregex=",";//多个值时的分隔符，默认是,
	private String checked="";//默认是否选中
	private boolean br=false;
	private String scope="";//范围

	@Override
	public String processResult() {
		
		Object value = getExValue(getValue());
		
		StringBuilder sb = new StringBuilder("");
		
		List<SysDict> list = this.getSubList(getPvalue());
		
		if(list!=null && list.size()>0){
			
			String separator = "";
			if(br){
				separator = "<br/>";
			}else{
				for(int i=0;i<Integer.parseInt(nbspnum);i++){
					separator += "&nbsp;";
				}
			}
			for(SysDict dd : list){
				if(StrKit.notBlank(scope)){
					String[] scopes = scope.split(",");
					for(String sc : scopes){
						 if(dd.getValue().equals(sc.trim())){
							 generateCheckBoxs(sb,value,dd,separator);
						}
					}
				}else{
					generateCheckBoxs(sb,value,dd,separator);
				}
				
			}
		}
		return sb.toString();
	}
	
	private StringBuilder generateCheckBoxs(StringBuilder sb,Object value,SysDict dd,String separator){
		sb.append("<input type=\"checkbox\"");
		if(value!=null){
		   String[] valueArr =(value.toString()).split(splitregex);
		   for(String va : valueArr){
			   if(dd.getValue().equals(va.trim())){
					sb.append(" checked=\"checked\" ");
					break;
				}
		   }
		}else{
			if("checked".equals(checked)){
				sb.append(" checked=\"checked\" ");
			}
		}
		sb.append(" value=\""+dd.getValue()+"\" "+ getHtmlAttributes() + " />" + dd.getLabel()+ separator);
		
		
		return sb;
	}

	public void setNbspnum(String nbspnum) {
		this.nbspnum = nbspnum;
	}

	public String getSplitregex() {
		return splitregex;
	}

	public void setSplitregex(String splitregex) {
		this.splitregex = splitregex;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public boolean isBr() {
		return br;
	}

	public void setBr(boolean br) {
		this.br = br;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

}
