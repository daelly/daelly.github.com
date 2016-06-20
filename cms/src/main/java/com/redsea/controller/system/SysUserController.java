/**
 * 
 */
package com.redsea.controller.system;

import java.util.Date;
import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.kit.HashKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.ehcache.CacheKit;
import com.redsea.common.Consts;
import com.redsea.common.WebUtils;
import com.redsea.ext.base.BaseController;
import com.redsea.ext.base.IController;
import com.redsea.interceptor.AdminInterceptor;
import com.redsea.model.SysUser;
import com.redsea.vo.AjaxResult;

/**
 * @author chenxiaofeng
 * @date 2016-4-13 下午6:07:59
 */
@Before(AdminInterceptor.class)
public class SysUserController extends BaseController implements IController {
	@Clear
	public void login(){
		keepPara();
	}
	@Clear
	public void dologin(){
		/*if(!validateCaptcha("randcode")){
			alertAndGoback("验证码错误",-1);
			return;
		}*/
		if(StrKit.isBlank(getPara("password"))||StrKit.isBlank(getPara("username"))){
			alertAndGoback("账号或密码为空!",-1);
			return;
		}
		String pwd=HashKit.md5(getPara("password"));
		List<SysUser> list=SysUser.dao.find("select * from sys_user where state=1 and username= ? and password =?",getPara("username"),pwd);
		if(list!=null&&list.size()>0){
			SysUser sysUser=(SysUser)list.get(0);
			sysUser.setLastIp(WebUtils.getIP(getRequest()));
			sysUser.setLastLoginTime(new Date());
			sysUser.setLoginCount(sysUser.getLoginCount()+1);
			setSessionAttr(Consts.SESSION_USER, sysUser);
			if("1".equals(getPara("online"))){
                WebUtils.setCookie(getResponse(),"redsea_cms_name",getPara("username"),1*24*60*60);
                WebUtils.setCookie(getResponse(),"redsea_cms_pwd",pwd,1*24*60*60);
            }else{
                WebUtils.removeCookie(getResponse(),"redsea_cms_name");
            }
			//CKFinder添加一个access control Session
			setSessionAttr("CKFinder_UserRole", "admin");			
			sysUser.setSessionId(getSession().getId());
			sysUser.update();
			setAttr("url", getPara("url"));
			redirect("/system/index",true);
		}else{
			alertAndGoback("账号或密码错误",-1);
			return;
		}
	}
	@Clear
	public void randCode(){
		renderCaptcha();
	}
	public void logout(){
        getSession().removeAttribute(Consts.SESSION_USER);
        render("login.jsp");
    }
	@Override
	public void list() {
		renderJson(SysUser.dao.findPageJsonByConditions(getParaMap()));
	}

	@Override
	public void add() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void edit() {
		setAttr("sysUser", SysUser.dao.findById(getPara()));
	}

	@Override
	public void view() {
		// TODO Auto-generated method stub

	}

	@Override
	public void save() {
		SysUser sysUser=getModel(SysUser.class,"sysUser");
		if(StrKit.notBlank(sysUser.getPassword())&&sysUser.getPassword().length()<20){
			String pwd=HashKit.md5(sysUser.getPassword());
			sysUser.setPassword(pwd);
		}
		renderJson(new AjaxResult(SysUser.dao.saveOrUpdate(sysUser)));

	}

	@Override
	public void delete() {
		renderJson(new AjaxResult(SysUser.dao.deleteById(getPara())));
	}

}
