package com.redsea.ui.jsp;

import java.util.List;

import com.jfinal.kit.StrKit;
import com.redsea.model.SysDict;
/**
 * 自定义radio标签
 * @author yeshujun
 */
public class RadioTag extends DictionaryParentTag{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//空格&nbsp;的数量，默认是3个
	private String nbspnum = "";
	private boolean br=false;

	@Override
	public String processResult() {
		
		Object value = getExValue(getValue());
		
		StringBuilder sb = new StringBuilder("");
		
		List<SysDict> list = this.getSubList(getPvalue());
		
		if(list!=null && list.size()>0){
			
			nbspnum = StrKit.isBlank(nbspnum)?"3":nbspnum;
			String separator = "";
			if(br){
				separator = "<br/>";
			}else{
				for(int i=0;i<Integer.parseInt(nbspnum);i++){
					separator += "&nbsp;";
				}
			}
			for(SysDict dd : list){
				sb.append("<input type=\"radio\"");
				if(dd.getValue().equals(value)){
					sb.append(" checked=\"checked\" ");
				}
				sb.append(" value=\""+dd.getValue()+"\" "+ getHtmlAttributes() + " />" + dd.getLabel()+ separator);
				
			}
		}
		return sb.toString();
	}

	public void setNbspnum(String nbspnum) {
		this.nbspnum = nbspnum;
	}

	public boolean isBr() {
		return br;
	}

	public void setBr(boolean br) {
		this.br = br;
	}
}
