/**
 * 
 */
package com.redsea.controller.system;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.redsea.ext.base.BaseController;
import com.redsea.ext.base.IController;
import com.redsea.interceptor.AdminInterceptor;
import com.redsea.model.People;
import com.redsea.vo.AjaxResult;

/**
 * @author chenxiaofeng
 * @date 2016-4-18 下午3:00:18
 */
@Before(AdminInterceptor.class)
public class PeopleController extends BaseController implements IController {

	
	@Override
	public void list() {
		renderJson(People.dao.findPageJsonByConditions(getParaMap()));
	}

	@Override
	public void add() {
		// TODO Auto-generated method stub
		
	}

	@Clear
	public void login(){
		
	}
	@Clear
	public void doloign(){
		
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void edit() {
		setAttr("people", People.dao.findById(getPara()));
		
	}

	
	@Override
	public void view() {
		setAttr("people", People.dao.findById(getPara()));
	}

	
	@Override
	public void save() {
		renderJson(new AjaxResult(People.dao.saveOrUpdate(getModel(People.class,"people"), getRequest())));
	}

	@Override
	public void delete() {
		renderJson(new AjaxResult(People.dao.deleteById(getPara())));
		
	}

}
