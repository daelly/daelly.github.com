package com.redsea.ext.plugin.webmagic.run;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 爬虫定义
 * 
 * @author chenxiaofeng
 * @date 2016-05-13
 *
 */
public class PaperPageProcessorForJob implements PageProcessor {
    
    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site page = Site.me().setRetryTimes(3).setSleepTime(1000);
    
    private String link;//网站地址
    private String title;//标题
    private String summary;//文章摘要
    private String content;//内容
    private String origin;//来源
    private String publish_user;//发布者
    private String publish_time;//发布时间
    
    public PaperPageProcessorForJob(Map<String,Object> params) {
        this.link = params.get("links_rule").toString();
        this.title = params.get("title_rule").toString();
        this.summary = (String) (params.get("summary_rule") == null?"":params.get("summary_rule"));
        this.content = params.get("content_rule").toString();
        this.origin = (String) (params.get("origin_rule") == null?"":params.get("origin_rule"));
        this.publish_user = (String) (params.get("publish_user_rule") == null?"":params.get("publish_user_rule"));
        this.publish_time = (String) (params.get("publish_time_rule") == null?"":params.get("publish_time_rule"));
       
    }
    
    public Site getSite() {
        return page;
    }

    /**
     * process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
     */
    @Override
    public void process(Page page) {
        List<String> links = removeDuplicate(page.getHtml().links().regex(link).all());
        page.putField("title", page.getHtml().xpath( title ).toString());
        page.putField("content",page.getHtml().xpath( content ).toString());
        if(null != origin && "" != origin){
        	page.putField("origin", page.getHtml().xpath( origin ).toString());
        }
        if(null != publish_user && "" != publish_user){
        	page.putField("publish_user", page.getHtml().xpath( publish_user ).toString());
        }
        if(null != summary && "" != summary){
        	page.putField("summary",  page.getHtml().xpath( summary ).toString());
        }
        if(null != publish_time && "" != publish_time){
        	page.putField("publish_time", page.getHtml().xpath( publish_time ).toString());        	
        }
        page.addTargetRequests(links);
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<String> removeDuplicate(List list) {
            HashSet hs = new HashSet(list);
            list.clear();
            list.addAll(hs);
            return list;
    }
}
