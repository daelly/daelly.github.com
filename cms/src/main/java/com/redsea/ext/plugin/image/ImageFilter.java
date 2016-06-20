package com.redsea.ext.plugin.image;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.redsea.ext.plugin.image.handler.ImageHandler;
import com.redsea.ext.plugin.image.util.ImageUtil;



/**
 * 图片处理核心过滤器
 * 
 * @author Rocky
 * path?imageView/s/300x230  300x230是缩放后的大小
 * path?imageView/p/50  50是缩放为原图的50%
 * path?imageView/r/180 旋转180°
 * path?imageView/s/300x200/q/90 缩放类型/类型参数/压缩图片比例/比例参数
 * imageView变成了imageDown 为下载
 * r：旋转角度（单位 角度数字）
   q：图片质量（单位%）
   p：按比例缩放（单位%）
   s：按大小缩放（单位px）   
   c：裁剪（单位px）
   f：转换图片格式（单位 图片格式）
 */
@WebFilter(urlPatterns = "/upload/*", asyncSupported = true)
public class ImageFilter implements Filter {

	private int contextPathLength;
	
	private ImageHandler imageHandler;
	
	/**
	 * init
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		String contextPath = config.getServletContext().getContextPath();
		contextPathLength = (contextPath == null || "/".equals(contextPath) ? 0
				: contextPath.length());
		imageHandler = ImageHandler.single();
	}

	/**
	 * dofilter
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("qqqq");
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		String target = request.getRequestURI().replaceFirst(
				request.getContextPath(), "");
		if (contextPathLength != 0) {
			target = request.getRequestURI().substring(contextPathLength);
		}
		String fileName = target.substring(target.lastIndexOf("/") + 1);
		if (ImageUtil.isImg(fileName) && null != request.getQueryString()) {
			imageHandler.handler(target, request, response);
		}
		//chain.doFilter(request, response);
	}

	/**
	 * @see Filter#destroy()
	 */
	@Override
	public void destroy() {
		imageHandler = null;
	}

}
