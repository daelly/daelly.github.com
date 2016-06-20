package com.redsea.controller.system;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;
import com.redsea.common.CacheConstants;
import com.redsea.ext.base.BaseController;
import com.redsea.ext.base.IController;
import com.redsea.interceptor.AdminInterceptor;
import com.redsea.model.SysDict;
import com.redsea.vo.AjaxResult;

/**
 * @author chenxiaofeng
 * @date 2016-4-12 下午4:33:36
 */
@Before(AdminInterceptor.class)
public class DictController extends BaseController implements IController {
	 
	@Override
	public void list() {
		renderJson(SysDict.dao.findPageJsonByConditions(getParaMap()));
	}

	@Override
	public void add() {
		// TODO Auto-generated method stub
	}
	public void getType(){
		SysDict.dao.find("select DISTINCT(type) from sys_dict");
	}
	
	@Override
	public void update() {
		SysDict sysDict=getModel(SysDict.class,"");
		sysDict.saveOrUpdate(sysDict);
		renderJson(new AjaxResult().success("更新成功"));
	}

	@Override
	public void edit() {
		setAttr("sysDict", SysDict.dao.findById(getPara()));
	}

	@Override
	public void view() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save() {
		CacheKit.remove(CacheConstants.CONSTANTS_DATA_CACHE, CacheConstants.DATA_DICTIONARY_KEY);
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
		
		renderJson(new AjaxResult(SysDict.dao.saveOrUpdate(getModel(SysDict.class,"sysDict"), getRequest())));
	}

	@Override
	public void delete() {
		renderJson(new AjaxResult(SysDict.dao.deleteById(getPara())));
	}

}
