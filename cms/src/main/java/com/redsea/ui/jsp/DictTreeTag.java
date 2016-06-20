package com.redsea.ui.jsp;

import java.util.List;

import com.redsea.model.SysDict;
import com.redsea.utils.StringListUtil;


/**
 * 树标签
 * @author Tolk
 * @date Apr 15, 2013
 */
public class DictTreeTag extends DictionaryParentTag{

	private static final long serialVersionUID = 1L;
	private String treeId;
	private String inputTextName;
	private String inputValueName;

	@Override
	public String processResult() {
		Object input = getExValue("${"+this.getInputTextName()+"}");
		String textValue = input == null ? "" : input.toString();
		input = getExValue("${"+this.getInputValueName()+"}");
		String valueValue = input == null ? "" : input.toString();
		List<String> valueList =  StringListUtil.strSplitToStringList(valueValue);
		
		String treeID = this.getTreeId() + "Tree";
		List<SysDict> list = this.getSubList(getPvalue());
		
		StringBuilder sb = new StringBuilder("");
		sb.append("<input type=\"text\" class=\"input-text radius\" style=\"max-width:150px\" name=\""+this.getInputTextName()+"\" id=\""+this.getTreeId()+"zTreeName\" value=\""+textValue+"\" onclick=\"showSysTreeList('menuContent"+this.getTreeId()+"',this); return false;\"/>");
		sb.append("<input type=\"hidden\"  name=\""+this.getInputValueName()+"\" id=\""+this.getTreeId()+"zTreeIds\" value=\""+valueValue+"\">");
		sb.append("<div id=\"menuContent"+this.getTreeId()+"\" class=\"menuContent"+this.getTreeId()+"\" style=\"display:none; position: absolute;\">");
		sb.append("<ul id="+treeID+" class=\"ztree\" style=\"margin-top:0;border: 1px solid #617775;background: #f0f6e4;width:180px;height:250px;overflow-y:scroll;overflow-x:auto;\"></ul></div>");
		sb.append("<script type=\"text/javascript\">");
		sb.append("var treeNodeArray"+this.getTreeId()+" = new Array();");
		if(list!=null && list.size()>0){
			for(SysDict dd : list){
				boolean checked = false;
				boolean open = false;
				if(valueList != null && valueList.contains(dd.getValue())){
					checked = true;
					open = true;
				}
				String name = dd.getLabel();
				sb.append("var oneTreeNode='{id:\""+dd.getValue()+"\",pId:\""+0+"\",name:\""+name+"\",checked:"+checked+",open:"+open+"}';");
				sb.append("treeNodeArray"+this.getTreeId()+".push(oneTreeNode);");
			}
		}
		sb.append(" var zNodeStr"+this.getTreeId()+" = '['+treeNodeArray"+this.getTreeId()+".join(\",\")+']';");
		sb.append(" var zNodes"+this.getTreeId()+" = (new Function(\"return \" + zNodeStr"+this.getTreeId()+"))();");
		sb.append("$(function() {$.fn.zTree.init($(\"#"+treeID+"\"), checkboxSetting, zNodes"+this.getTreeId()+");});");
		sb.append("</script>");
		return sb.toString()== null ? "":sb.toString();
	}
	
	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public String getInputTextName() {
		return inputTextName;
	}

	public void setInputTextName(String inputTextName) {
		this.inputTextName = inputTextName;
	}

	public String getInputValueName() {
		return inputValueName;
	}

	public void setInputValueName(String inputValueName) {
		this.inputValueName = inputValueName;
	}
}
