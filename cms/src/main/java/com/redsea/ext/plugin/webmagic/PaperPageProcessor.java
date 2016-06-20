package com.redsea.ext.plugin.webmagic;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 爬虫定义
 * 
 * @author yinjunfeng
 * @date 2016-03-30
 *
 */
public class PaperPageProcessor implements PageProcessor {
    
    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site page = Site.me().setRetryTimes(3).setSleepTime(1000);
    
    private String link;//网站地址
    private String title;//标题
    private String summary;//文章摘要
    private String content;//内容
    private String origin;//来源
    private String publish_user;//发布者
    private String publish_time;//发布时间
    private String links_pattern; //链接表达式
    private String title_pattern; //标题表达式
    private String summary_pattern; //摘要表达式
    private String content_pattern;//内容表达式
    private String origin_pattern; //文章来源表达式
    private String publish_user_pattern; //发布者表达式
    private String publish_time_pattern;//发布时间表达式
    private String replaceStr;//被替换字符串
    private String str;//替换字符串
    
    public PaperPageProcessor(Map<String,Object> params) {
        this.link = params.get("links").toString();
        this.title = params.get("title").toString();
        this.summary = (String) (params.get("summary") == null?"":params.get("summary"));
        this.content = params.get("content").toString();
        this.origin = (String) (params.get("origin") == null?"":params.get("origin"));
        this.publish_user = (String) (params.get("publish_user") == null?"":params.get("publish_user"));
        this.publish_time = (String) (params.get("publish_time") == null?"":params.get("publish_time"));
        this.links_pattern = params.get("links_pattern").toString();
        this.title_pattern = params.get("title_pattern").toString();
        this.summary_pattern = params.get("summary_pattern").toString();
        this.content_pattern = params.get("content_pattern").toString();
        this.origin_pattern = params.get("origin_pattern").toString();
        this.publish_user_pattern = params.get("publish_user_pattern").toString();
        this.publish_time_pattern = params.get("publish_time_pattern").toString();
        this.replaceStr = params.get("replaceStr") == null?"":params.get("replaceStr").toString();
        this.str = params.get("str") == null?"":params.get("str").toString();
        
        String replaceStr = new String(Base64.encodeBase64("redsea".getBytes()));
        
        this.link = this.link.replaceAll(replaceStr,"\\\\");
        this.title = this.title.replaceAll(replaceStr,"\\\\");
        this.summary = this.summary.replaceAll(replaceStr,"\\\\");
        this.content = this.content.replaceAll(replaceStr,"\\\\");
        this.origin = this.origin.replaceAll(replaceStr,"\\\\");
        this.publish_user = this.publish_user.replaceAll(replaceStr,"\\\\");
        this.publish_time = this.publish_time.replaceAll(replaceStr,"\\\\");
    }
    
    public Site getSite() {
        return page;
    }

    /**
     * process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
     */
    @Override
    public void process(Page page) {
        List<String> links = getLinks(page);
        
        page.putField("title", getTitle(page));
        page.putField("content", getContent(page));
        if(null != origin && "" != origin){
        	
        	page.putField("origin", getOrigin(page));
        }
        if(null != publish_user && "" != publish_user){
        	page.putField("publish_user", getPublish_user(page));
        	
        }
        if(null != summary && "" != summary){
        	page.putField("summary", getSummary(page));
        	
        }
        if(null != publish_time && "" != publish_time){
        	page.putField("publish_time", getPublish_time(page));        	
        }
        page.addTargetRequests(links);
    }

    @SuppressWarnings("unchecked")
    private List<String> getLinks(Page page) {
        List<String> links = null;
        if(links_pattern.equals("xpath")) {
            links = page.getHtml().links().xpath(link).all();//如:
        }else if(links_pattern.equals("regular")) {
            links = page.getHtml().links().regex(link).all();//如:"http://shebao.southmoney.com/dongtai/sbxw/201603/\\d+.html"
        }else if(links_pattern.equals("css")) {
            links = page.getHtml().links().css(link).all();//如：
        }
       
        links = removeDuplicate(links);
        return links;
    }
    
    private Object getTitle(Page page) {
        String result = "";
        if(title_pattern.equals("xpath")) {
            result = page.getHtml().xpath( title ).toString();//如："//h1[@id='articleTitle']/text()"
        }else if(title_pattern.equals("regular")) {
            result = page.getHtml().regex( title ).toString();//如：
        }else if(title_pattern.equals("css")) {
        	result = page.getHtml().css( title ,"text").toString();
           // result = page.getHtml().css( title ).toString();//如：
        }
       
        return result;
    }
    
    private Object getSummary(Page page) {
        String result = "";
        if(summary_pattern.equals("xpath")) {
            result = page.getHtml().xpath( summary ).toString();//如："//h1[@id='articleTitle']/text()"
        }else if(summary_pattern.equals("regular")) {
            result = page.getHtml().regex( summary ).toString();//如：
        }else if(summary_pattern.equals("css")) {
            result = page.getHtml().css( summary,"text" ).toString();//如：
        }
       
        return result;
    }
    
    private Object getContent(Page page) {
        String result = "";
        if(content_pattern.equals("xpath")) {
            result = page.getHtml().xpath( content ).toString();//如："//div[@id='articleText']/html()"
        }else if(content_pattern.equals("regular")) {
            result = page.getHtml().regex( content ).toString();//如：
        }else if(content_pattern.equals("css")) {
            result = page.getHtml().css( content ).toString();//如：
        }
       
        return result;
    }
    
    private Object getOrigin(Page page) {
        String result = "";
        try {
        	if(origin_pattern.equals("xpath")) {
        		result = page.getHtml().xpath( origin ).toString();//如："//div[@id='articleText']/html()"
        	}else if(origin_pattern.equals("regular")) {
        		result = page.getHtml().regex( origin ).toString();//如：
        	}else if(origin_pattern.equals("css")) {
        		result = page.getHtml().css( origin ).toString();//如：
        	}
        	
        	return result;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
    
    private Object getPublish_user(Page page) {
        String result = "";
        if(publish_user_pattern.equals("xpath")) {
            result = page.getHtml().xpath( publish_user ).toString();//如："//div[@id='articleText']/html()"
        }else if(publish_user_pattern.equals("regular")) {
            result = page.getHtml().regex( publish_user ).toString();//如：
        }else if(publish_user_pattern.equals("css")) {
            result = page.getHtml().css( publish_user ).toString();//如：
        }
       if(replaceStr == null || replaceStr == "" || result == null){
    	   return result;    	   
       }else{
    	   return result.replace(replaceStr, str);
       }
    }
    
    private Object getPublish_time(Page page) {
        String result = "";
        if(publish_time_pattern.equals("xpath")) {
            result = page.getHtml().xpath( publish_time ).toString();//如："//div[@id='articleText']/html()"
        }else if(publish_time_pattern.equals("regular")) {
            result = page.getHtml().regex( publish_time ).toString();//如：
        }else if(publish_time_pattern.equals("css")) {
            result = page.getHtml().css( publish_time ).toString();//如：
        }
       
        return result;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List removeDuplicate(List list) {
            HashSet hs = new HashSet(list);
            list.clear();
            list.addAll(hs);
            return list;
    }
}
