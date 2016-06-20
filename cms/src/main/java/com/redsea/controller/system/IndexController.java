/**
 * 
 */
package com.redsea.controller.system;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.redsea.common.Consts;
import com.redsea.interceptor.AdminInterceptor;

/**
 * @author chenxiaofeng
 * @date 2016-4-8 下午2:48:08
 */
@Before(AdminInterceptor.class)
public class IndexController extends Controller {

	public void index(){
		setAttr(Consts.SESSION_USER, getSession().getAttribute(Consts.SESSION_USER));
	}
	
	public void form(){
		
	}
	public void div(){
		
	}
	public void welcome(){
		setAttr(Consts.SESSION_USER, getSession().getAttribute(Consts.SESSION_USER));
	}
	
	
}
