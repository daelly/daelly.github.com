package org.daelly.oj.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.daelly.oj.pojo.AuthUser;
import org.daelly.oj.service.impl.AuthUserService;
import org.daelly.oj.utils.Const;
import org.daelly.oj.utils.MapUtils;
import org.daelly.oj.utils.RandCodeCreater;
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
		if("GET".equalsIgnoreCase(req.getMethod())){
			req.getSession().setAttribute("user-rand-code", "1234");
			return new ModelAndView(getViewPrefix()+"/login");
		}else{
			String randCode = req.getParameter("randcode");
			String username = req.getParameter("username");
			HttpSession session = req.getSession();
			String code = (String) session.getAttribute("user-rand-code");
			session.removeAttribute("user-rand-code");
			if(code == null){
				req.setAttribute("username", username);
				return new ModelAndView(getViewPrefix()+"/login","error","验证码错误");
			}else if(!code.equals(randCode)){
				req.setAttribute("username", username);
				return new ModelAndView(getViewPrefix()+"/login","error","验证码错误");
			}
			
			String password = req.getParameter("password");
			boolean valid = service.checkLoginUser(username, password);
			if(!valid){
				req.setAttribute("username", username);
				return new ModelAndView(getViewPrefix()+"/login","error","用户名或密码错误");
			}
			session.setAttribute(Const.SESSION_KEY, username);
			String redirect = req.getParameter("redirect");
			if(StringUtils.isEmpty(redirect))
				redirect = "/index";
			return new ModelAndView("redirect:" + redirect);
		}
	}
	
	@RequestMapping(value="/register")
	public ModelAndView register(HttpServletRequest req,HttpServletResponse resp) throws IOException{
		if("GET".equalsIgnoreCase(req.getMethod())){
			return new ModelAndView(getViewPrefix()+"/register");
		}else{
			String randCode = req.getParameter("randcode");
			Map<String,String> map = MapUtils.getSampleMap(req.getParameterMap());
			HttpSession session = req.getSession();
			String code = (String) session.getAttribute("user-rand-code");
			session.removeAttribute("user-rand-code");
			if(code == null){
				map.put("error", "验证码错误");
				return new ModelAndView(getViewPrefix()+"/register",map);
			}else if(!code.equalsIgnoreCase(randCode)){
				map.put("error", "验证码错误");
				return new ModelAndView(getViewPrefix()+"/register",map);
			}
			try {
				boolean success = service.insertRegisterUser(req.getParameterMap());
				if(success){
					String redirect = req.getParameter("redirect");
					if(StringUtils.isEmpty(redirect))
						redirect = "/" + getViewPrefix()+"/login";
					return new ModelAndView("redirect:" + redirect);					
				}else{
					map.put("error", "此用户名已被注册！");
					return new ModelAndView(getViewPrefix()+"/register",map);
				}
			} catch (Exception e) {
				e.printStackTrace();
				map.put("error", "出现错误了，请联系程序员！");
				return new ModelAndView(getViewPrefix()+"/register",map);
			}
		}
	}
	
	@RequestMapping(value="/randcode")
	public void randcode(HttpServletResponse response,HttpSession session) throws IOException{
		String randCode = RandCodeCreater.getRandStr();
		session.setAttribute("user-rand-code", randCode);
		BufferedImage image = RandCodeCreater.getImage(randCode);
		response.setContentType("image/png");
		OutputStream os = response.getOutputStream();
		ImageIO.write(image, "png", os);
	}
	
	@RequestMapping(value="/logout")
	public ModelAndView logout(HttpSession session){
		session.removeAttribute(Const.SESSION_KEY);
		return new ModelAndView("redirect:/index");
	}
}
