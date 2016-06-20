/**
 * 
 */
package com.redsea.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.codecraft.webmagic.Spider;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;
import com.redsea.common.Consts;
import com.redsea.ext.base.BaseController;
import com.redsea.ext.base.IController;
import com.redsea.ext.plugin.webmagic.run.JdbcPipelineForJob;
import com.redsea.ext.plugin.webmagic.run.PaperPageProcessorForJob;
import com.redsea.interceptor.AdminInterceptor;
import com.redsea.model.SpiderJob;
import com.redsea.vo.AjaxResult;


/**
 * @author chenxiaofeng
 * @date 2016-5-13 下午5:51:17
 */
@Before(AdminInterceptor.class)
public class SpiderJobController extends BaseController implements IController {

	@Override
	public void list() {
		renderJson(SpiderJob.dao.findPageJsonByConditions(getParaMap()));
	}

	@Override
	public void add() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void edit() {
		setAttr("spiderJob", SpiderJob.dao.findById(getPara()));
		
	}

	@Override
	public void view() {
		setAttr("spiderJob", SpiderJob.dao.findById(getPara()));
	}

	@Override
	public void save() {
		renderJson(new AjaxResult(SpiderJob.dao.saveOrUpdate(getModel(SpiderJob.class,"spiderJob"))));
		
	}

	@Override
	public void delete() {
		renderJson(new AjaxResult(SpiderJob.dao.deleteById(getPara())));
		
	}

	public void run(){
		try {
			String ids = getPara("ids");
			List<Record> list=Db.find("SELECT t1.* ,t2.* from tbl_spider_job t1 INNER JOIN tbl_spider_rule t2 on t1.spide_rule_id=t2.id where t1.id IN("+ids+")");
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
					spider.addPipeline(new JdbcPipelineForJob()).thread(5);
					String uuid = spider.getUUID();
					CacheKit.put(Consts.CacheName.hour.get(), uuid, spider);
					spider.runAsync();
					long endTime=System.currentTimeMillis();
					System.out.println(record.getStr("website")+"总费用时间："+(endTime-beginTime)+"毫秒!");
				}
			}
			renderJson(new AjaxResult());
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(new AjaxResult().addError("作业启动失败"));
		}
	}
	
	public void runWithJobname(){
		try {
			String jobname = getPara();
			List<Record> list=Db.find("SELECT t1.* ,t2.* from tbl_spider_job t1 INNER JOIN tbl_spider_rule t2 on t1.spide_rule_id=t2.id where job_name='"+jobname+"'");
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
					CacheKit.put(Consts.CacheName.hour.get(), "spider", spider);
					spider.addUrl(record.getStr("website"));
					spider.addPipeline(new JdbcPipelineForJob()).thread(5).run();
					long endTime=System.currentTimeMillis();
					System.out.println(record.getStr("website")+"总费用时间："+(endTime-beginTime)+"毫秒!");
				}
			}
			renderJson(new AjaxResult());
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(new AjaxResult().addError("作业启动失败"));
		}
	}
}
