package com.redsea.config.routes;

import com.jfinal.config.Routes;
import com.redsea.controller.web.ChaXunController;
import com.redsea.controller.web.IndexController;
import com.redsea.controller.web.TopicController;


/**
 * 前台页面路由
 * @author Rocky
 * email: 464193096@qq.com
 * site:http://www.hr-soft.cn/
 */
public class WebRoutes extends Routes {

	@Override
	public void config() {
		add("/",IndexController.class,"web");
		add("/m",com.redsea.controller.web.m.IndexController.class,"web/m");
		add("/chaxun",ChaXunController.class,"web/chaxun");
		add("/topic",TopicController.class,"web/topic");
	}
	
	

}
