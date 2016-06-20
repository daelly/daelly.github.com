package com.redsea.utils;

import javax.servlet.http.HttpServletRequest;

import com.redsea.common.Consts;
import com.redsea.model.SysUser;

/**
 * @author Rocky
 *
 */
public class SysUserUtil
{
    public static SysUser getSysUser(HttpServletRequest request){
        SysUser user=(SysUser)request.getSession().getAttribute(Consts.SESSION_USER);
        return user;
    } 
    public static String  getSysUserId(HttpServletRequest request){
    	SysUser user=(SysUser)request.getSession().getAttribute(Consts.SESSION_USER);
        return user==null?"":user.getUserid()+"";
    } 
}

