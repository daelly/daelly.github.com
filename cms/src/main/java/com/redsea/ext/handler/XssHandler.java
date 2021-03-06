package com.redsea.ext.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.jfinal.handler.Handler;
import com.jfinal.kit.StrKit;
import com.redsea.servlet.HttpServletRequestWrapper;

/**
 * 统一XSS处理
 * @author Rocky
 * email: 464193096@qq.com
 * site:  http://www.hr-soft.cn/
 * @date 2014-5-5 上午9:11:10
 */
public class XssHandler extends Handler {
	
	// 排除的url，使用的target.startsWith匹配的
	private String exclude;
	
	public XssHandler(String exclude) {
		this.exclude = exclude;
	}

	@Override
	public void handle(String target, HttpServletRequest request,
			HttpServletResponse response, boolean[] isHandled) {
		// 对于非静态文件，和非指定排除的url实现过滤
		if (target.indexOf('.') == -1 && StrKit.notBlank(exclude) && !target.startsWith(exclude)){
			request = new HttpServletRequestWrapper(request);
		}
		next.handle(target, request, response, isHandled);
	}
}
