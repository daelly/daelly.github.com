package com.redsea.ext.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;

/**
 * 对连接地址的优化，避免报错
 * @author Rocky
 * email: 464193096@qq.com
 * site:http://www.hr-soft.cn/
 * @date 2015年1月30日下午11:52:21
 */
public class URLOptimizeHandler extends Handler {

	@Override
	public void handle(String target, HttpServletRequest request,
			HttpServletResponse response, boolean[] isHandled) {

//		对于 ///x//x/x.html 路径的优化
		target = target.replaceAll("/{2,}", "/");

		next.handle(target, request, response, isHandled);
	}

}
