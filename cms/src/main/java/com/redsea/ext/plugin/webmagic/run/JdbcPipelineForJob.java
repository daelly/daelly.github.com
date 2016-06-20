package com.redsea.ext.plugin.webmagic.run;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.ehcache.CacheKit;
import com.redsea.common.Consts;
import com.redsea.model.Article;

/**
 * 搜索结果处理，将搜索结果保存到数据库
 * 
 * @author yinjunfeng
 * @date 2016-03-30
 *
 */
public class JdbcPipelineForJob implements Pipeline{

    private Logger log = LoggerFactory.getLogger(JdbcPipelineForJob.class);
    private List<Map<String, Object>> dataList = null;
    int i=0;
    
    @Override
    public void process(ResultItems resultItems, Task task){
        log.debug("get page: " + resultItems.getRequest().getUrl());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page_url", resultItems.getRequest().getUrl());
        dataList = CacheKit.get("halfHour", "spiderData_job");
        if(null == dataList) {
        	dataList = new ArrayList<Map<String,Object>>();
        	dataList.add(map);
        	CacheKit.put("halfHour", "spiderData_job", dataList);
        }else {
        	dataList.add(map);
        	CacheKit.put("halfHour", "spiderData_job", dataList);
        }
        
        try {
        	if(i>=10){
        		String uuid = task.getUUID();
        		Spider spider=CacheKit.get(Consts.CacheName.hour.get(), uuid);
        		if(spider!=null){
        			spider.stop();
        		}
        		CacheKit.remove(Consts.CacheName.hour.get(), "spider");
        		System.out.println("超过十次采集重复~spider停止！");
        		resultItems.setSkip(true);
        		i=0;
        		return;
        	}
        	Article a1=Article.dao.findFirstByColumn("origin_url", resultItems.getRequest().getUrl());
        	if(a1!=null){
        		i++;
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
        if(StrKit.isBlank(article.getStr("title"))||StrKit.isBlank(article.getStr("content"))){
        	System.out.println(resultItems.getRequest().getUrl()+"title或者content为空");
        }
        //保存有效数据
        if(StringUtils.isNoneBlank(article.getStr("title")) && StringUtils.isNoneBlank(article.getStr("content"))) {
           // article.set("page_url",resultItems.getRequest().getUrl());
        	if(StrKit.isBlank(article.getAddress()))
        		article.set("address", "");
            article.saveOrUpdate(article,null);
        }
    }
}

