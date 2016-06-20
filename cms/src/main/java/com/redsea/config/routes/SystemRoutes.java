/**
 * 
 */
package com.redsea.config.routes;

import com.jfinal.config.Routes;
import com.redsea.controller.common.FileUploadController;
import com.redsea.controller.common.UeditorApiController;
import com.redsea.controller.system.ArticleController;
import com.redsea.controller.system.CacheManageController;
import com.redsea.controller.system.CodeController;
import com.redsea.controller.system.CommentController;
import com.redsea.controller.system.DictController;
import com.redsea.controller.system.FileManagerController;
import com.redsea.controller.system.FolderController;
import com.redsea.controller.system.IndexController;
import com.redsea.controller.system.KeywordController;
import com.redsea.controller.system.PeopleController;
import com.redsea.controller.system.SpiderJobController;
import com.redsea.controller.system.SpiderRuleController;
import com.redsea.controller.system.SysUserController;
import com.redsea.controller.system.TopicController;
import com.redsea.controller.weixin.WeixinApiController;
import com.redsea.controller.weixin.WeixinMsgController;
import com.redsea.ext.plugin.webmagic.controller.JsoupController;
import com.redsea.ext.plugin.webmagic.controller.SpiderController;

/**
 * @author chenxiaofeng
 * @date 2016-4-8 下午2:50:00
 */
public class SystemRoutes extends Routes {

	@Override
	public void config() {
		add("/system/index",IndexController.class,"system");
		add("/system/folder",FolderController.class);
		add("/system/keyword",KeywordController.class);
		add("/system/dict",DictController.class);
		add("/system/code",CodeController.class);
		add("/system/article",ArticleController.class);
		add("/system/comment",CommentController.class);
		add("/system/sysuser",SysUserController.class);
		add("/system/people",PeopleController.class);
		add("/system/files",FileManagerController.class);
		add("/system/topic",TopicController.class);
		add("/system/spider_rule",SpiderRuleController.class,"system/spider_rule");
		add("/system/spider_job",SpiderJobController.class,"system/spider_job");
		
		
		add("/ueditor/api", UeditorApiController.class);
		add("/uploadfile",FileUploadController.class,"system/fileManager");
		add("/weixin/msg", WeixinMsgController.class);
		add("/weixin/api", WeixinApiController.class);
		
		add("/ext/jsoup",JsoupController.class);
		add("/ext/spider",SpiderController.class);
		
		add("/system/cache",CacheManageController.class);
	}

}
