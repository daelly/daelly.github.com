package com.redsea.controller.system;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PathKit;
import com.redsea.interceptor.AdminInterceptor;
import com.redsea.utils.FileUtils;
@Before(AdminInterceptor.class)
public class FileManagerController extends Controller {
	
	public void index(){
		if(getPara()!=null){
			renderJson(FileUtils.getFileList(getPara()));
		}else{
			String path1=	PathKit.getWebRootPath();
			String path2=	JFinal.me().getConstants().getBaseUploadPath();
			String path=path1+path2;
			renderJson(FileUtils.getFileList(path));
		}
	}

}
