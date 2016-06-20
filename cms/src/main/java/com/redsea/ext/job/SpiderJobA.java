/**
 * 
 */
package com.redsea.ext.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import us.codecraft.webmagic.Spider;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;
import com.redsea.common.Consts;
import com.redsea.ext.plugin.webmagic.run.JdbcPipelineForJob;
import com.redsea.ext.plugin.webmagic.run.PaperPageProcessorForJob;

/**
 * @author chenxiaofeng
 * @date 2016-5-9 下午8:17:39
 */
public class SpiderJobA implements Job {

    @Override
    public void execute(JobExecutionContext arg) throws JobExecutionException {
		try {
			List<Record> list=Db.find("SELECT t1.* ,t2.* from tbl_spider_job t1 INNER JOIN tbl_spider_rule t2 on t1.spide_rule_id=t2.id where job_name='job_a'");
			if(list!=null&&list.size()>0){
				for(Record record:list){
					String[] columnNames=record.getColumnNames();
					Map<String,Object> params=new HashMap<String,Object>();
					for(String columnName:columnNames){
						params.put(columnName, record.get(columnName));
					}
					//执行爬虫
					long beginTime=System.currentTimeMillis();
				    Spider spider = Spider.create(new PaperPageProcessorForJob(params));
				    spider.addUrl(record.getStr("website"));
				    String uuid = spider.getUUID();
				    CacheKit.put(Consts.CacheName.hour.get(), uuid, spider);
			        spider.addPipeline(new JdbcPipelineForJob()).thread(5).run();
			        long endTime=System.currentTimeMillis();
			        System.out.println(record.getStr("website")+"总费用时间："+(endTime-beginTime)+"毫秒!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
