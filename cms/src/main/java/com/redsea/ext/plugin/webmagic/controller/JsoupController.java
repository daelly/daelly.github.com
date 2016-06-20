package com.redsea.ext.plugin.webmagic.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.redsea.interceptor.AdminInterceptor;
import com.redsea.model.Article;
import com.redsea.vo.AjaxResult;
@Before(AdminInterceptor.class)
public class JsoupController extends Controller{

    /**
     * 社保新闻数据采集
     * 
     * @throws IOException
     */
    public void shebao() throws IOException{
        for(int i=0;i<500;i++){
        	int pn = i*20;
            try{
                System.out.println("http://news.baidu.com/ns?word=%E7%A4%BE%E4%BF%9D&pn=0&cl=2&ct=1&tn=news&rn=20&ie=utf-8&bt=0&et=0"+(i+1)+".html");
                Connection connection = Jsoup.connect("http://news.baidu.com/ns?word=%E7%A4%BE%E4%BF%9D&pn="+pn+"&cl=2&ct=1&tn=news&rn=20&ie=utf-8&bt=0&et=0");
                connection.data("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.124 Safari/537.36");
                connection.data("Host","news.baidu.com");
                connection.data("Accept","news.baidu.com");
                connection.cookie("session","2ce6f1773759890787ee573457a86319a3c532fd");
                connection.timeout(50000);
                
                Document document = connection.get();
                if(null == document) {
                    continue;
                }
                SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
                Elements postList = document.select("#content_left .result");
                if(null != postList) {
                    for(int j=0; j<postList.size()-1; j++) {
                        Element post =  postList.get(j); 
                        Article article = new Article();
                        article.set("title",post.select(".c-title a").first().text());
                        article.set("content",post.select(".c-summary").text());
                        String author = post.select(".c-summary .c-author").text();
                        //author = author.substring(0,author.indexOf(" "));
                        //String str[]=author.split("/n/n");
                        article.set("publish_user",author);
                        article.set("publish_time",sdf.format(new Date()));
                        article.set("folder_id","2");
                        article.saveOrUpdate(article,getRequest());
                    }
                }
            }catch(Exception e){
            	e.printStackTrace();
                continue;
           }
        } 
        renderText("ok");
     }
    
    public void spider() throws IOException{
    	String url = getPara("url");
    	String titleSelecter = getPara("titleSelecter");
    	String contentSelecter = getPara("contentSelecter");
    	try {
    		Connection connection = Jsoup.connect(url);
    		connection.data("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.124 Safari/537.36");
    		//connection.data("Host","news.baidu.com");
    		connection.data("Accept","news.baidu.com");
    		connection.cookie("session","2ce6f1773759890787ee573457a86319a3c532fd");
    		connection.timeout(50000);
    		Document document = connection.get();
    		Elements titleDoms = document.select(titleSelecter);
    		Elements contentDoms = document.select(contentSelecter);    		
    		String title = "";
    		String content = "";
    		if(titleDoms!=null&&titleDoms.size()>0)
    			title = titleDoms.first().text();
    		if(contentDoms!=null&&contentDoms.size()>0)
    			content = contentDoms.first().html();
    		JSONObject json = new JSONObject();
    		json.put("title", title);
    		json.put("content", content);
    		renderJson(new AjaxResult().success(json));
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(new AjaxResult().addError("采集信息失败"));
		}
    }
}

