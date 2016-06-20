package com.redsea.ui.jsp;

import java.util.List;

import com.redsea.model.SysDict;


/**
 * 查看数据字典名称
 * @author yeshujun
 */
public class ViewTag extends DictionaryParentTag{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nbspnum="3";//空格&nbsp;的数量，默认是3个
	private String splitregex=",";//多个值时的分隔符，默认是,

	@Override
	public String processResult() {
		
        Object value = getExValue(getValue());
        if(this.getValue() == null || "".equals(this.getValue())){
        	return "";
        }
		
		StringBuilder sb = new StringBuilder("");
		
		List<SysDict> list = this.getSubList(getPvalue());
		
		if(list!=null && list.size()>0){
			String separator = "";
			for(int i=0;i<Integer.parseInt(nbspnum);i++){
				separator += "&nbsp;";
			}
			for(SysDict dd : list){
				if(value!=null){
				   String[] valueArr =(value.toString()).split(splitregex);
				   for(String va : valueArr){
					   if(dd.getValue().equals(va.trim())){
						   String ddName = dd.getLabel();
						   if(valueArr.length>1){
							   sb.append(ddName+ separator);
						   }else{
							   sb.append(ddName);
						   }
						  
							break;
						}
				   }
				}
			}
		}
		return sb.toString()== null ? "":sb.toString();
		/*if(getPvalue()!=null && getValue()!=null){
			return getSubName(getExValue(getPvalue()).toString().trim(), getExValue(getValue()).toString().trim());
		}
		return "";*/
	}

	public String getSplitregex() {
		return splitregex;
	}

	public void setSplitregex(String splitregex) {
		this.splitregex = splitregex;
	}

	public String getNbspnum() {
		return nbspnum;
	}

	public void setNbspnum(String nbspnum) {
		this.nbspnum = nbspnum;
	}

}
