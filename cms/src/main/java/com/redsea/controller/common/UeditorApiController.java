/**
 * 
 */
package com.redsea.controller.common;

import com.jfinal.core.Controller;
import com.redsea.ext.plugin.baidu.ueditor.ActionEnter;

/**
 * @author chenxiaofeng
 * @date 2016-4-28 下午8:27:46
 */
public class UeditorApiController extends Controller {

	public void index() {
		String outText = ActionEnter.me().exec(getRequest());
		renderHtml(outText);
	}

}
