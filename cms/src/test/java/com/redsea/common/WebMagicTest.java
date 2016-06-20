/**
 * 
 */
package com.redsea.common;

import org.junit.Test;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author liqingyang
 * @date 2016-5-23 下午2:33:11
 */
public class WebMagicTest {

	@Test
	public void test() {
		Spider.create(new GithubRepoPageProcessor()).addUrl("http://www.cnys.com/zixun/57781.html").addPipeline(new MyPipeline()).thread(5).run();
	}

	class GithubRepoPageProcessor implements PageProcessor {
		
		private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

		/* (non-Javadoc)
		 * @see us.codecraft.webmagic.processor.PageProcessor#process(us.codecraft.webmagic.Page)
		 */
		@Override
		public void process(Page page) {
			page.addTargetRequests(page.getHtml().links().regex("(http://www\\.cnys\\.com/zixun/\\d+\\.html)").all());
			page.putField("title", page.getHtml().xpath("//h1/text()").toString());
		}

		@Override
		public Site getSite() {
//			System.out.println("getCharset():"+site.getCharset());
			return site;
		}
		
	}
	
	class MyPipeline implements Pipeline{

		@Override
		public void process(ResultItems resultItems, Task task) {
			System.out.println("get page: " + resultItems.getRequest().getUrl());
			String title = resultItems.get("title");
			System.out.println("--------------------------------Get title:"+title);
		}
		
	}
}
