/**
 * 
 */
package com.redsea.controller.system;

import com.jfinal.aop.Before;
import com.jfinal.plugin.ehcache.CacheKit;
import com.redsea.common.Consts;
import com.redsea.ext.base.BaseController;
import com.redsea.ext.base.IController;
import com.redsea.interceptor.AdminInterceptor;
import com.redsea.model.Topic;
import com.redsea.vo.AjaxResult;

/**
 * @author chenxiaofeng
 * @date 2016-5-3 下午7:32:06
 */
@Before(AdminInterceptor.class)
public class TopicController extends BaseController implements IController{

	@Override
	public void list() {
		renderJson(Topic.dao.findPageJsonByConditions(getParaMap()));
	}

	@Override
	public void add() {
		setAttr("flag", "add");
		render("edit.jsp");
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void edit() {
		setAttr("flag", "edit");
		setAttr("topic", Topic.dao.findById(getPara()));
	}

	@Override
	public void view() {
		// TODO Auto-generated method stub
		
	}

	public void removeCache(){
		CacheKit.remove(Consts.CacheName.halfHour.get(), "topic_all");
		renderText("删除成功!");
	}
	@Override
	public void save() {
		if("add".equals(getPara("flag"))){
			renderJson(new AjaxResult(Topic.dao.save(getModel(Topic.class,"topic"), getRequest())));
		}else{
			renderJson(new AjaxResult(Topic.dao.saveOrUpdate(getModel(Topic.class,"topic"), getRequest())));
		}
	}

	@Override
	public void delete() {
		renderJson(new AjaxResult(Topic.dao.deleteById(getPara())));
	}
	

}
