package com.redsea.ext.plugin.html.generator;


/**
 * @author liqingyang
 * @date 2016-6-2 下午2:57:31
 */
import java.io.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfinal.kit.HttpKit;

/**
 * 静态页面引擎技术（突乱了乱码问题UTF-8）
 * @author redsea
 *
 */
public class HtmlGenerator{
	
	private Logger logger = LoggerFactory.getLogger(HtmlGenerator.class);
	
	BufferedWriter fw = null;
	String page = null;
	String webappname = null;
	BufferedReader br = null;
	InputStream in = null;
	//构造方法
	public HtmlGenerator(String webappname){
		this.webappname = webappname;
		
	}
	
	/** 根据模版及参数产生静态页面 */
	public boolean createHtmlPage(String url,String htmlFileName){
		boolean status = false;	
		try{
			//创建一个HttpClient实例充当模拟浏览器
			page = HttpKit.get(url);
			page = formatPage(page);
			writeHtml(htmlFileName, page);
			status = true;
		}catch(Exception ex){
			logger.error("静态页面引擎在解析"+url+"产生静态页面"+htmlFileName+"时出错:"+ex.getMessage());			
        }
		return status;
	}
	
	//将解析结果写入指定的静态HTML文件中
	private synchronized void writeHtml(String htmlFileName,String content) throws Exception{
		fw = new BufferedWriter(new FileWriter(htmlFileName));
		OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(htmlFileName),"UTF-8");
		fw.write(page);	
		if(fw!=null)fw.close();		
	}
	
	//将页面中的相对路径替换成绝对路径，以确保页面资源正常访问
	private String formatPage(String page){		
		page = page.replaceAll("\\.\\./\\.\\./\\.\\./", webappname+"/");
		page = page.replaceAll("\\.\\./\\.\\./", webappname+"/");
		page = page.replaceAll("\\.\\./", webappname+"/");			
		return page;
	}
	
	//测试方法
	public static void main(String[] args){
		HtmlGenerator h = new HtmlGenerator("");
		h.createHtmlPage("http://www.sbyun.com/","f:/a.html");
		System.out.println("静态页面已经生成到f:/a.html");
		
	}

}