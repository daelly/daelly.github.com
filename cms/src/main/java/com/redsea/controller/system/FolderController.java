/**
 * 
 */
package com.redsea.controller.system;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.ehcache.CacheKit;
import com.redsea.common.CacheConstants;
import com.redsea.common.Consts;
import com.redsea.ext.base.BaseController;
import com.redsea.ext.base.IController;
import com.redsea.interceptor.AdminInterceptor;
import com.redsea.model.Folder;
import com.redsea.vo.AjaxResult;

/**
 * @author chenxiaofeng
 * @date 2016-4-12 上午11:37:52
 */
@Before(AdminInterceptor.class)
public class FolderController extends BaseController implements IController {

	@Override
	public void list() {
		renderJson(Folder.dao.findPageJsonByConditions(getParaMap()));
	}

	@Override
	public void add() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.redsea.ext.base.IController#edit()
	 */
	@Override
	public void edit() {
		String parent_id = getPara("parent_id");
		if(StrKit.isBlank(parent_id))
			setAttr("folder",Folder.dao.findById(getPara()));
	}

	/* (non-Javadoc)
	 * @see com.redsea.ext.base.IController#view()
	 */
	@Override
	public void view() {
		// TODO Auto-generated method stub
		
	}
	public void updateCache(){
		CacheKit.remove(Consts.CacheName.oneDay.get(),"head_folder_list");
		CacheKit.remove(Consts.CacheName.oneDay.get(),"folder_foldEnName_foldId");
		CacheKit.remove(Consts.CacheName.oneDay.get(),"folder_foldId_foldName");
		Folder.dao.getFolderByCache();
		renderText("更新缓存成功!");
	}
	public void updateCacheForHomeColumns(){
		 CacheKit.remove(CacheConstants.CONSTANTS_HOME_CACHE,"home_columns");
		 String sql="SELECT id,name,path from tbl_folder  where display_home=1  ORDER BY sort DESC";
		 Db.findByCache(CacheConstants.CONSTANTS_HOME_CACHE, "home_columns",sql);
		 renderText("更新缓存成功!");
	}
	@Override
	public void save() {
		renderJson(new AjaxResult(Folder.dao.saveOrUpdate(getModel(Folder.class,"folder"),getRequest())));
	}

	@Override
	public void delete() {
		Folder folder = Folder.dao.findById(getPara());
		folder.set("status", 0);
		renderJson(new AjaxResult(folder.save()));
	}
	
	public void tree(){
		List<Folder> folders = Folder.dao.findAll();
		JSONArray treeNodes = new JSONArray();
		for (Folder folder : folders) {
			JSONObject obj = new JSONObject();
			obj.put("id", folder.get("id"));
			obj.put("parent_id", folder.get("parent_id"));
			obj.put("name", folder.get("name"));
			obj.put("description", folder.get("description"));
			treeNodes.add(obj);
		}
		setAttr("folders", treeNodes.toString());
	}

}




