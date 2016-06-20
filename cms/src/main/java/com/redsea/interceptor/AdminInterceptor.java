package com.redsea.interceptor;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.redsea.common.Consts;
import com.redsea.common.WebUtils;
import com.redsea.model.SysUser;
import com.redsea.vo.AjaxResult;


/**
 * 后台拦截
 * @author Rocky
 * @date 2013-5-30 下午9:52:46
 */
public class AdminInterceptor implements Interceptor {
	
	@Override
	public void intercept(Invocation inv) {
		Controller controller = inv.getController();
		Object object=controller.getSession().getAttribute(Consts.SESSION_USER);
		String path=inv.getController().getRequest().getRequestURI();
		if(object==null){
			 String name=WebUtils.getCookie(controller.getRequest(),"redsea_cms_name");
			 String pwd=WebUtils.getCookie(controller.getRequest(),"redsea_cms_pwd"); 
             try {
                 if(StringUtils.isNoneBlank(name)) {
                     name= URLDecoder.decode(name,"utf-8");
                 }
              }catch(UnsupportedEncodingException e1){
                  e1.printStackTrace();
              }
             if(StrKit.notBlank(name)&&StrKit.notBlank(pwd)){
            	 List<SysUser> list=SysUser.dao.find("select * from sys_user where state=1 and username= ? and password =?",name,pwd);
            	 if(list!=null&&list.size()>0){
         			SysUser sysUser=(SysUser)list.get(0);
         			sysUser.setLastIp(WebUtils.getIP(controller.getRequest()));
         			sysUser.setLastLoginTime(new Date());
         			sysUser.setLoginCount(sysUser.getLoginCount()+1);
         			controller.getSession().setAttribute(Consts.SESSION_USER, sysUser);
         			controller.getSession().setAttribute("CKFinder_UserRole", "admin");			
        			sysUser.setSessionId(controller.getSession().getId());
        			sysUser.update();
         			inv.invoke();
         		}
             }else{
            	 String requestType = controller.getRequest().getHeader("X-Requested-With");
            	 if("XMLHttpRequest".equals(requestType))
            		 controller.renderJson(new AjaxResult().addError("用户未登录，请先登录！"));
            	 else
            		 controller.redirect("/system/sysuser/login?url="+path);
             }
		}else{
			inv.invoke();
		}
	}
}
