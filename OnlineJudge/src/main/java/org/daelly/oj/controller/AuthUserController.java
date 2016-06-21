package org.daelly.oj.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.daelly.oj.pojo.AuthUser;
import org.daelly.oj.service.impl.AuthUserService;
import org.daelly.oj.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("user")
public class AuthUserController extends BaseController<AuthUser,Integer> {
	
	@Autowired
	AuthUserService service;
	
	@RequestMapping(value="/login")
	public ModelAndView loginPage(HttpServletRequest req,HttpServletResponse resp) throws Exception{
		if("GET".equalsIgnoreCase(req.getMethod()))
			return new ModelAndView(getViewPrefix()+"/login");
		else{
//			String randCode = req.getParameter("randCode");
//			HttpSession session = req.getSession();
//			String code = (String) session.getAttribute("randCode");
//			if(code == null){
//				req.setAttribute("error", "验证码已过期");
//				return getViewPrefix() + "/login";
//			}else if(!code.equals(randCode)){
//				req.setAttribute("error", "验证码错误");
//				return getViewPrefix() + "/login";
//			}
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			boolean valid = service.checkLoginUser(username, password);
			if(!valid){
				return  new ModelAndView(getViewPrefix()+"/login","error","用户名或密码错误");
			}
			String redirect = req.getParameter("redc");
			if(StringUtils.isEmpty(redirect))
				redirect = "/index";
			return  new ModelAndView("redirect:" + redirect);
		}
	}
	
	@RequestMapping(value="/register")
	public void register(HttpServletRequest req,HttpServletResponse resp) throws IOException{
		resp.setContentType("text/html;charset=UTF-8");
		AjaxResult ajaxResult = new AjaxResult();
		try {
			String username = req.getParameter("username");
			AuthUser existUser = service.getByCondition(" and username='"+username+"'");
			if(existUser != null){
				ajaxResult.setState("1");
				ajaxResult.setMsg("注册失败,用户已存在!");
			}
			boolean result = service.insertRegisterUser(req.getParameterMap());
			if(!result){
				ajaxResult.setState("1");
				ajaxResult.setMsg("注册失败！");
			}
			resp.getWriter().print(ajaxResult);
		} catch (Exception e) {
			e.printStackTrace();
			resp.getWriter().print(new AjaxResult().error("注册失败!"));
		}
	}
}
