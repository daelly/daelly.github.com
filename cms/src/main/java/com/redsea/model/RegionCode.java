package com.redsea.model;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.ehcache.CacheKit;
import com.redsea.common.Consts;
import com.redsea.model.base.BaseRegionCode;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class RegionCode extends BaseRegionCode<RegionCode> {
	public static final RegionCode dao = new RegionCode();
	
	public List<RegionCode> findRegionCodeByParentId(String parentId){
		String sql="select * from tbl_region_code where  parent_region_code= ? order by sort desc ";
		return find(sql,parentId);
	} 
	public List<RegionCode> getRegionCodeFromCache(){
		List list2=CacheKit.get(Consts.CacheName.oneDay.get(), "cx_city");
		if(CacheKit.get(Consts.CacheName.oneDay.get(), "cx_city")==null){
			list2=new ArrayList();
			List<RegionCode> list=RegionCode.dao.findRegionCodeByParentId("china");
			for(RegionCode code:list){
				if(code.getArticleId()!=0){
					code.put("hasChild", false);
				}else{
					code.put("hasChild", true);
					code.put("childList", RegionCode.dao.findRegionCodeByParentId(code.getRegionCode()));
				}
				list2.add(code);
			}
			CacheKit.put(Consts.CacheName.oneDay.get(), "cx_city",list2);
		}
		return list2;
	}
	public List<String> getHotCity(){
		List<String> list=CacheKit.get(Consts.CacheName.oneDay.get(), "hot_city");
		if(list==null){
			String sql="SELECT short_name from tbl_region_code where is_hot=1";
			list=Db.query(sql);
		}
		return list;
	}
	
	
}
