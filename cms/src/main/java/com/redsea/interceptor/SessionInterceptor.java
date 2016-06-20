package com.redsea.interceptor;


import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.redsea.common.Consts;
import com.redsea.vo.AjaxResult;


/**
 * 后台拦截
 * @author Rocky
 * @date 2013-5-30 下午9:52:46
 */
public class SessionInterceptor implements Interceptor {
	
	@Override
	public void intercept(Invocation inv) {
		Controller controller = inv.getController();
		Object object=controller.getSession().getAttribute(Consts.SESSION_USER);
			if(object==null){
				String requestType = controller.getRequest().getHeader("X-Requested-With");
				if(/*StrKit.notBlank(requestType)||*/"XMLHttpRequest".equals(requestType))
					controller.renderJson(new AjaxResult().addError("用户未登录，请先登录！"));
				else
					controller.redirect("/system/sysuser/login");
			}else{
				inv.invoke();
		}
	}
}
