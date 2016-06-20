package com.redsea.ext.plugin.webmagic.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.JMException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.mozilla.universalchardet.UniversalDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Spider;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.ehcache.CacheKit;
import com.redsea.ext.plugin.webmagic.JdbcPipeline;
import com.redsea.ext.plugin.webmagic.PaperPageProcessor;
import com.redsea.interceptor.SessionInterceptor;
import com.redsea.model.Article;
import com.redsea.vo.AjaxResult;

/**
 * @author Rocky
 */
@Before(SessionInterceptor.class)
public class SpiderController extends Controller{
	
	private Logger log = LoggerFactory.getLogger(SpiderController.class);
    
    public void index(){
    }
    
    public void run() throws JMException{
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("links",getPara("links"));
        params.put("title",getPara("title"));
        params.put("content",getPara("content"));
        if(null != getPara("origin") && "" != getPara("origin")){
        	params.put("origin",getPara("origin"));        	
        }
        if(null != getPara("publish_user") && "" != getPara("publish_user")){
        	params.put("publish_user",getPara("publish_user"));        	
        }
        if(null != getPara("publish_time") && "" != null){
        	params.put("publish_time",getPara("publish_time"));
        }
        if(!StringUtils.isEmpty(getPara("summary"))){
        	params.put("summary",getPara("summary"));
        }
        params.put("links_pattern",getPara("links_pattern"));
        params.put("replaceStr",getPara("replaceStr"));
        params.put("str",getPara("str"));
        params.put("links_pattern",getPara("links_pattern"));
        params.put("title_pattern",getPara("title_pattern"));
        params.put("summary_pattern",getPara("summary_pattern"));
        params.put("content_pattern",getPara("content_pattern"));
        params.put("origin_pattern",getPara("origin_pattern"));
        params.put("publish_user_pattern",getPara("publish_user_pattern"));
        params.put("publish_time_pattern",getPara("publish_time_pattern"));
        
        //定义从哪个网址开始抓
        Spider spider = Spider.create(new PaperPageProcessor(params));
        setSessionAttr("spider",spider);
        
        String replaceStr = new String(Base64.encodeBase64("redsea".getBytes()));
        String url = getPara("url");
        url = url.replaceAll(replaceStr,"\\");
        
        spider.addUrl(url);//如：http://shebao.southmoney.com/dongtai/sbxw
        spider.addPipeline(new JdbcPipeline()).thread(5);
        spider.run();
        renderText("爬虫搜索完毕");
    }
    
    public void singlePage() throws Exception{
    	String url = getPara("url");
    	Article a = Article.dao.findFirstByColumn("origin_url", url);
    	if(a!=null){
    		log.debug("地址：“"+url+"”已被采集过");
    		renderJson(new AjaxResult().addError("此url已被采集过！"));
    		return;
    	}
    	Enumeration<String> paramNames = getParaNames();
    	try {
    		Connection connection = Jsoup.connect(url);
    		connection.data("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.124 Safari/537.36");
    		connection.data("Host","news.baidu.com");
    		connection.data("Accept","news.baidu.com");
    		connection.cookie("session","2ce6f1773759890787ee573457a86319a3c532fd");
    		connection.timeout(50000);
    		Response response = connection.execute();
    		String charset = response.charset();
    		String guessCharset = guessEncoding(response.bodyAsBytes());
    		if(!StrKit.isBlank(guessCharset) && !guessCharset.equals(charset))
    			charset = guessCharset;
    		Document document = null;
    		if(!StrKit.isBlank(charset)){
    			String body = new String(response.bodyAsBytes(), charset);
    			document = Jsoup.parse(body);    			
    		}else{
    			document = response.parse();
    		}
    		Article article = new Article();
    	    article.set("origin_url", url);
    		while(paramNames.hasMoreElements()){
    			String thisName = paramNames.nextElement().toString();
    			if(!thisName.startsWith("article."))
    				continue;
    			String selector = getPara(thisName);
    			if(StrKit.isBlank(selector))
    				continue;
    			String column = thisName.replace("article.", "");
    			Elements doms = document.select(selector);
    			if(doms!=null&&doms.size()>0){
    				String content = doms.first().text();
    				if("content".equals(column))
    					content = doms.first().html();
    				article.set(column, content);
    			}else{
    				log.info("字段："+column+",选择器："+selector+"并未采集到内容");
    			}
    		}
    		if(StrKit.notBlank(article.getStr("title")) && StrKit.notBlank(article.getStr("content"))) {
    			if(StrKit.isBlank(article.getAddress()))
    				article.set("address", "");
    			try {
					article.saveOrUpdate(article,null);
					renderJson(new AjaxResult().success(article));
				} catch (Exception e) {
					e.printStackTrace();
					renderJson(new AjaxResult().addError("发生错误！"));
				}
    		}else{
    		}
    		
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(new AjaxResult().addError("采集信息失败"+e.getMessage()));
		}
    }
    
    public void stop(){
        Spider spider = (Spider)getSessionAttr("spider");
        spider.stop();
        getSession().removeAttribute("spider");
        renderText("爬虫关闭成功");
    }
    
    public void list() {
    	List<Article> dataList = CacheKit.get("halfHour", "spiderData");
    	if(null != dataList) {
    		CacheKit.remove("halfHour", "spiderData");
    		renderJson(dataList);
    	}else {
    		renderText("0");
    	}
    }
    
    /**
     * 根据字节数组，猜测可能的字符集，如果检测失败，返回utf-8
     * @param bytes 待检测的字节数组
     */
    public static String guessEncoding(byte[] bytes) {
        UniversalDetector detector = new UniversalDetector(null);
        detector.handleData(bytes, 0, bytes.length);
        detector.dataEnd();
        String guessEncoding = detector.getDetectedCharset();
        detector.reset();
        return guessEncoding;
    }
}

