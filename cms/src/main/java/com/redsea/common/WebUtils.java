package com.redsea.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.HashKit;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.StrKit;
import com.redsea.model.People;
import com.redsea.utils.DESUtils;

/**
 * Web相关工具类
 * @author Rocky
 * email: 464193096@qq.com
 * site:http://www.hr-soft.cn/
 */
public final class WebUtils {

	private WebUtils() {}
	public static String getAddress(HttpServletRequest request,HttpServletResponse response){
		String  city=WebUtils.getCookie(request, "sbyun_city");
		try {
			if(StrKit.isBlank(city)){
				city=WebUtils.getAddress(request);
				WebUtils.setCookie(response, "sbyun_city",URLEncoder.encode(city, "utf-8"), 5*24*60*60);
			}else{
				city= URLDecoder.decode(city,"utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
	}
		return city;
}
	public static String getAddress(HttpServletRequest request){
		String ip=WebUtils.getIP(request);
		if(StrKit.isBlank(ip)){
			return "";
		}else if(ip.startsWith("192.168.101")){
			return "广州";
		}else if(ip.contains(":")){
			return "";
		}else if(ip.contains(",")){
			ip=ip.substring(0,ip.indexOf(","));
		}
		String jsonText="";
		JSONObject json=null;
		try {
			jsonText = HttpKit.get("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip="+ip);
			json=JSONObject.parseObject(jsonText);
			if(json==null||!json.containsKey("city")){
				return "";
			}else{
				return json.getString("city");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 密码:md5(sha1(pwd))
	 * @param password
	 * @return
	 */
	public static String pwdEncode(String password) {
		return HashKit.md5(HashKit.sha1(password));
	}

	private final static String USER_COOKIE_KEY    = "uid";
	private final static String USER_COOKIE_SECRET = "&#%redcool!&*";

	/**
	 * 返回当前用户信息
	 * 
	 * @param c
	 * @return UserModel
	 */
	public static People currentUser(Controller c) {
		HttpServletRequest  request  = c.getRequest();
		HttpServletResponse response = c.getResponse();
		return currentUser(request, response);
	}

	/**
	 * 返回当前用户
	 * @param request
	 * @param response
	 * @return UserModel
	 */
	public static People currentUser(HttpServletRequest request, HttpServletResponse response) {
		String cookieKey = USER_COOKIE_KEY;
		// 获取cookie信息
		String userCookie = getCookie(request, cookieKey);
		// 1.cookie为空，直接清除
		if (StrKit.isBlank(userCookie)) {
			removeCookie(response, cookieKey);
			return null;
		}
		// 2.解密cookie
		String cookieInfo = null;
		// cookie 私钥
		String secret = USER_COOKIE_SECRET;
		try {
			cookieInfo = new DESUtils(secret).decryptString(userCookie);
		} catch (RuntimeException e) {
			// ignore
		}
		// 3.异常或解密问题，直接清除cookie信息
		if (StrKit.isBlank(cookieInfo)) {
			removeCookie(response, cookieKey);
			return null;
		}
		String[] userInfo = cookieInfo.split("~");
		// 4.规则不匹配
		if (userInfo.length < 3) {
			removeCookie(response, cookieKey);
			return null;
		}
		String userId   = userInfo[0];
		String oldTime  = userInfo[1];
		String maxAge   = userInfo[2];
		// 5.判定时间区间，超时的cookie清理掉
		if (!"-1".equals(maxAge)) {
			long now  = System.currentTimeMillis();
			long time = Long.parseLong(oldTime) + (Long.parseLong(maxAge) * 1000);
			if (time <= now) {
				removeCookie(response, cookieKey);
				return null;
			}
		}
		return People.dao.loadInSession(userId);
	}

	/**
	 * 用户登陆状态维持
	 * 
	 * cookie设计为: des(私钥).encode(userId~time~maxAge~ip)
	 * 
	 * @param c 控制器
	 * @param user  用户model
	 * @param remember   是否记住密码、此参数控制cookie的 maxAge，默认为-1（只在当前会话）<br>
	 *                   记住密码默认为一周
	 * @return void
	 */
	public static void loginUser(Controller c, People user, boolean... remember) {
		// 获取用户的id、nickName
		String uid     = user.getId() + "";
		// 当前毫秒数
		long   now      = System.currentTimeMillis();
		// 超时时间
		int    maxAge   = -1;
		if (remember.length > 0 && remember[0]) {
			maxAge      = 60 * 60 * 24 * 7;
		}
		// 用户id地址
		String ip		= getIP(c);
		// 构造cookie
		StringBuilder cookieBuilder = new StringBuilder()
			.append(uid).append("~")
			.append(now).append("~")
			.append(maxAge).append("~")
			.append(ip);

		// cookie 私钥
		String secret = USER_COOKIE_SECRET;
		// 加密cookie
		String userCookie = new DESUtils(secret).encryptString(cookieBuilder.toString());
		HttpServletResponse response = c.getResponse();

		String cookieKey = USER_COOKIE_KEY;
		// 设置用户的cookie、 -1 维持成session的状态
		setCookie(response, cookieKey, userCookie, maxAge);
	}

	/**
	 * 退出即删除用户信息
	 * @param c
	 * @return void
	 */
	public static void logoutUser(Controller c) {
		HttpServletResponse response = c.getResponse();
		removeCookie(response, USER_COOKIE_KEY);
	}

	/**
	 * 读取cookie
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getCookie(HttpServletRequest request, String key) {
		Cookie[] cookies = request.getCookies();
		if(null != cookies){
			for (Cookie cookie : cookies) {
				if (key.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	/**
	 * 清除 某个指定的cookie 
	 * @param response
	 * @param key
	 */
	public static void removeCookie(HttpServletResponse response, String key) {
		setCookie(response, key, null, 0);
	}

	/**
	 * 设置cookie
	 * @param response
	 * @param name
	 * @param value
	 * @param maxAgeInSeconds
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, int maxAgeInSeconds) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(maxAgeInSeconds);
		// 指定为httpOnly保证安全性
		//cookie.setHttpOnly(true);
		response.addCookie(cookie);
	}

	/**
	 * 获取浏览器信息
	 * @param c
	 * @return String
	 */
	public static String getUserAgent(Controller c) {
		return getUserAgent(c.getRequest());
	}

	/**
	 * 获取浏览器信息
	 * @param request
	 * @return String
	 */
	public static String getUserAgent(HttpServletRequest request) {
		return request.getHeader("User-Agent");
	}

	/**
	 * 获取ip
	 * @param c
	 * @return
	 */
	public static String getIP(Controller c) {
		HttpServletRequest request = c.getRequest();
		return getIP(request);
	}

	/**
	 * 获取ip
	 * @param request
	 * @return
	 */
	public static String getIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Requested-For");
		if (StrKit.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (StrKit.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StrKit.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StrKit.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (StrKit.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (StrKit.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/** 
     * 百度链接实时推送 
     * @param PostUrl 
     * @param Parameters 
     * @return 
     */  
    public static String Post(String PostUrl,String[] Parameters){  
        if(null == PostUrl || null == Parameters || Parameters.length ==0){  
            return null;  
        }  
        String result="";  
        PrintWriter out=null;  
        BufferedReader in=null;  
        try {  
            //建立URL之间的连接  
            URLConnection conn=new URL(PostUrl).openConnection();  
            //设置通用的请求属性  
            conn.setRequestProperty("Host","data.zz.baidu.com");  
            conn.setRequestProperty("User-Agent", "curl/7.12.1");  
            conn.setRequestProperty("Content-Length", "83");  
            conn.setRequestProperty("Content-Type", "text/plain");  
               
            //发送POST请求必须设置如下两行  
            conn.setDoInput(true);  
            conn.setDoOutput(true);  
               
            //获取conn对应的输出流  
            out=new PrintWriter(conn.getOutputStream());  
            //发送请求参数  
            String param = "";  
            for(String s : Parameters){  
                param += s+"\n";  
            }  
            out.print(param.trim());  
            //进行输出流的缓冲  
            out.flush();  
            //通过BufferedReader输入流来读取Url的响应  
            in=new BufferedReader(new InputStreamReader(conn.getInputStream()));  
            String line;  
            while((line=in.readLine())!= null){  
                result += line;  
            }  
               
        } catch (Exception e) {  
            System.out.println("发送post请求出现异常！"+e);  
            e.printStackTrace();  
        } finally{  
            try{  
                if(out != null){  
                    out.close();  
                }  
                if(in!= null){  
                    in.close();  
                }  
                   
            }catch(IOException ex){  
                ex.printStackTrace();  
            }  
        }  
        return result;  
    }  

}
