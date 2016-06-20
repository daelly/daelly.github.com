package com.redsea.ext.plugin.webmagic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.ehcache.CacheKit;
import com.redsea.model.Article;

/**
 * 搜索结果处理，将搜索结果保存到数据库
 * 
 * @author yinjunfeng
 * @date 2016-03-30
 *
 */
public class JdbcPipeline implements Pipeline{

    private Logger log = LoggerFactory.getLogger(JdbcPipeline.class);
    private List<Map<String, Object>> dataList = null;
    
    @Override
    public void process(ResultItems resultItems, Task task){
        log.debug("get page: " + resultItems.getRequest().getUrl());
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page_url", resultItems.getRequest().getUrl());
        dataList = CacheKit.get("halfHour", "spiderData");
        if(null == dataList) {
        	dataList = new ArrayList<Map<String,Object>>();
        	dataList.add(map);
        	CacheKit.put("halfHour", "spiderData", dataList);
        }else {
        	dataList.add(map);
        	CacheKit.put("halfHour", "spiderData", dataList);
        }
        
        try {
        	Article a1=Article.dao.findFirstByColumn("origin_url", resultItems.getRequest().getUrl());
        	if(a1!=null){
        		log.debug("get page: " + resultItems.getRequest().getUrl()+"数据库已存在!");
        		return;
        	}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        //遍历所有结果保存到数据库
        Article article = new Article();
        article.set("origin_url",resultItems.getRequest().getUrl());
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            if("title".equals(entry.getKey())) {
                article.set("title", entry.getValue());
            }else if("summary".equals(entry.getKey())) {
                article.set("summary", entry.getValue());
            }else if("content".equals(entry.getKey())) {
                article.set("content", entry.getValue());
            }else if("origin".equals(entry.getKey())) {
                article.set("origin", entry.getValue());
            }else if("publish_user".equals(entry.getKey())) {
                article.set("publish_user", entry.getValue());
            }else if("publish_time".equals(entry.getKey())) {
            	//处理发布时间
				article.set("publish_time_remark", entry.getValue());
            }
        }
        if(StrKit.isBlank(article.getStr("title"))){
        	System.out.println("title为空");
        }
        if(StrKit.isBlank(article.getStr("content"))){
        	System.out.println("content为空");
        }
        article.set("address", "");
        //保存有效数据
        if(StringUtils.isNoneBlank(article.getStr("title")) && StringUtils.isNoneBlank(article.getStr("content"))) {
           // article.set("page_url",resultItems.getRequest().getUrl());
            article.saveOrUpdate(article,null);
        }
    }
}

