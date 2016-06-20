/**
 * 
 */
package com.redsea.controller.system;

import com.jfinal.aop.Before;
import com.jfinal.plugin.ehcache.CacheKit;
import com.redsea.common.CacheConstants;
import com.redsea.ext.base.BaseController;
import com.redsea.ext.base.IController;
import com.redsea.interceptor.AdminInterceptor;
import com.redsea.model.SysCode;
import com.redsea.vo.AjaxResult;

/**
 * @author chenxiaofeng
 * @date 2016-4-12 下午8:29:21
 */
@Before(AdminInterceptor.class)
public class CodeController extends BaseController implements IController {

	@Override
	public void list() {
		renderJson(SysCode.dao.findPageJsonByConditions(getParaMap()));
	}

	@Override
	public void add() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void edit() {
		setAttr("sysCode",SysCode.dao.findById(getPara()));
	}

	@Override
	public void view() {
		// TODO Auto-generated method stub

	}

	@Override
	public void save() {
		CacheKit.remove(CacheConstants.CONSTANTS_CODE_CACHE,CacheConstants.CODE_TABLE_KEY);
		CacheKit.put(CacheConstants.CONSTANTS_CODE_CACHE,CacheConstants.CODE_TABLE_KEY,SysCode.dao.findMap("paramname", "paramvalue", " isdelete = 0"));
		renderJson(new AjaxResult(SysCode.dao.saveOrUpdate(getModel(SysCode.class,"sysCode"),getRequest())));
	}

	@Override
	public void delete() {
		CacheKit.remove(CacheConstants.CONSTANTS_CODE_CACHE,CacheConstants.CODE_TABLE_KEY);
		CacheKit.put(CacheConstants.CONSTANTS_CODE_CACHE,CacheConstants.CODE_TABLE_KEY,SysCode.dao.findMap("paramname", "paramvalue", " isdelete = 0"));
		renderJson(new AjaxResult(SysCode.dao.deleteById(getPara())));
	}

}
