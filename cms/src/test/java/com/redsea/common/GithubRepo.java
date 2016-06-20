package com.redsea.common;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

/**
 * @author liqingyang
 * @date 2016-6-8 下午5:25:59
 */
@TargetUrl("http://www.cnys.com/zixun/\\d+(_\\d+)?.html")
@HelpUrl("http://www\\.cnys\\.com/zixun/")
public class GithubRepo {
	
	@ExtractBy(value="//h1/text()",notNull=true)
	private String title;
	
	public static void main(String[] args){
		OOSpider.create(Site.me().setSleepTime(1000), new ConsolePageModelPipeline(), GithubRepo.class)
		.addUrl("http://www.cnys.com/zixun/61602_2.html").thread(5).start();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String toString(){
		return title;
	}
	
}

class MyPageModelPipeline extends ConsolePageModelPipeline{
	
	@Override
	public void process(Object o, Task task) {
		System.out.println(o.toString());
	}
}