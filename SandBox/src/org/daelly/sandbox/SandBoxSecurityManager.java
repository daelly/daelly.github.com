package org.daelly.sandbox;

import java.io.FilePermission;
import java.lang.reflect.ReflectPermission;
import java.security.Permission;
import java.security.SecurityPermission;
import java.util.PropertyPermission;

/**
 * 覆盖写安全管理器
 * @author liqingyang
 * @date 2016-5-9 下午1:20:32
 */
public class SandBoxSecurityManager extends SecurityManager {
	
	public static final int EXIT = 123;
	
	public void checkExit(int status){
		if(status != EXIT)
			throw new SecurityException("Exit On Client Is Not Allowed!");
	}
	
	private void sandBoxCheck(Permission permission) throws SecurityException{
		if(permission instanceof SecurityPermission){
			if(permission.getName().startsWith("getP"))
				return;
		}else if(permission instanceof PropertyPermission){
			if("read".equals(permission.getActions()))
				return;
		}else if(permission instanceof FilePermission){
			if("read".equals(permission.getActions()))
				return;
		}else if(permission instanceof RuntimePermission || permission instanceof ReflectPermission){
			return;
		}
		throw new SecurityException(permission.toString());
	}
	
	@Override
	public void checkPermission(Permission permission){
		this.sandBoxCheck(permission);
	}
	
	@Override
	public void checkPermission(Permission permission,Object context){
		this.sandBoxCheck(permission);
	}
}
