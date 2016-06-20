package com.redsea.ui.jsp;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;
import com.redsea.common.CacheConstants;
import com.redsea.model.SysDict;

public abstract class DictionaryParentTag extends ParentTag{
	
	private static final long serialVersionUID = 1L;
	//数据字典父项名称
	private String pvalue;
	
	/**
	 * 根据数据字典父项取出所有的下级
	 * @param parentdictionary 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysDict> getSubList(String parentdictionary){
		Map<String,List<SysDict>> parentMap =getParentMap();
		List<SysDict> subList =  parentMap.get(parentdictionary);
		return subList;
	}
	private Map<String,List<SysDict>> getParentMap(){
		Map<String,List<SysDict>> parentMap = (Map<String, List<SysDict>>) CacheKit.get(CacheConstants.CONSTANTS_DATA_CACHE, CacheConstants.DATA_DICTIONARY_KEY);
		if(parentMap==null){
    		String sql="select DISTINCT(type) from sys_dict";
			List<Record> list= Db.find(sql);
			Map<String,List<SysDict>> map=Maps.newHashMap();
			for(Record record:list){
				try {
					map.put(record.getStr("type"), SysDict.dao.getSysDictListByType(record.getStr("type")));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			CacheKit.put(CacheConstants.CONSTANTS_DATA_CACHE, CacheConstants.DATA_DICTIONARY_KEY, map);
			parentMap=map;
     }
		return parentMap;
	}
	/**
	 * 获得一个数据字典名称
	 * @param parentdictionary 父项数据字典
	 * @param value 子项id
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getSubName(String parentdictionary,String value){
		Map<String,List<SysDict>> parentMap = getParentMap();
		List<SysDict> subList =  parentMap.get(parentdictionary);
		String subName = "";
		if(subList!=null && subList.size()>0){
			for(SysDict dd : subList){
				if(dd.getValue().equals(value)){
					subName = dd.getLabel();
					break;
				}
			}
		}
		return subName;
	}
	public String getPvalue() {
		return pvalue;
	}
	public void setPvalue(String pvalue) {
		this.pvalue = pvalue;
	}
}
