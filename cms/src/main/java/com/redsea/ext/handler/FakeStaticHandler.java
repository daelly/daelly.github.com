package com.redsea.ext.handler;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;
import com.jfinal.kit.StrKit;

/**
 * FakeStaticHandler.
 */
public class FakeStaticHandler extends Handler {
	
	private String viewPostfix;
	
	public FakeStaticHandler() {
		viewPostfix = ".html";
	}
	
	public FakeStaticHandler(String viewPostfix) {
		if (StrKit.isBlank(viewPostfix))
			throw new IllegalArgumentException("viewPostfix can not be blank.");
		this.viewPostfix = viewPostfix;
	}
	
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
		if(target.startsWith("/PtPortal.mc")||target.startsWith("/page")){
			 try {
				response.sendRedirect("http://buy.sbyun.com");
			} catch (IOException e) {
				e.printStackTrace();
			}
			 isHandled[0] = true;
			 return;
		}
		if ("/".equals(target)) {
			next.handle(target, request, response, isHandled);
			return;
		}
		
		if (target.indexOf('.') == -1||target.indexOf("static")!=-1) {
			next.handle(target, request, response, isHandled);
			return ;
		}
		
		int index = target.lastIndexOf(viewPostfix);
		if (index != -1)
			target = target.substring(0, index);
		next.handle(target, request, response, isHandled);
	}
}
