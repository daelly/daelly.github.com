package org.daelly.sandbox;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author liqingyang
 * @date 2016-5-9 下午1:08:15
 */
public class SandBoxClassLoader extends ClassLoader {
	
	private String _classPath;
	
	public SandBoxClassLoader(String classPath){
		this._classPath = classPath;
	}
	
	@Override
	protected Class<?> findClass(String className) throws ClassNotFoundException{
		return loadClass(null);
	}
	
	public Class<?> loaClass(String classPath,String className) throws ClassNotFoundException{
		if(className.indexOf('.') >= 0)
			throw new ClassNotFoundException(className);
		
		File classFile = new File(classPath + "" + className + ".class");
		byte[] mainClass = new byte[(int)classFile.length()];
		try {
			FileInputStream in = new FileInputStream(classFile);
			in.read(mainClass);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ClassNotFoundException(className);
		}		
		return super.defineClass(className, mainClass, 0, mainClass.length);
	}
	
	public String getClassPath(){
		return _classPath + "";
	}
}
