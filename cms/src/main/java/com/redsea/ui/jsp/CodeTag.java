package com.redsea.ui.jsp;

import java.util.Map;

import com.jfinal.plugin.ehcache.CacheKit;
import com.redsea.common.CacheConstants;


/**
 * 查询代码表信息
 * @author Tolk
 * @date Mar 31, 2013
 */
public class CodeTag extends DictionaryParentTag{

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
        	return null;
        }
		StringBuilder sb = new StringBuilder("");
		Map<String,String> map = (Map<String,String>)CacheKit.get(CacheConstants.CONSTANTS_CODE_CACHE, this.getPvalue());
		if(map!=null && map.size()>0){
			String separator = "";
			for(int i=0;i<Integer.parseInt(nbspnum);i++){
				separator += "&nbsp;";
			}
			String[] valueArr =(value.toString()).split(splitregex);
			for(String va : valueArr){
				if(valueArr.length>1){
					sb.append(map.get(va)+separator);
				}else{
					if(va.matches("\\d+")){
						sb.append(map.get(Integer.valueOf(va)));
					}else{
						sb.append(map.get(va));
					}
				}
			}
		}
		return sb.toString();
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
