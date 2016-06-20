package com.redsea.common;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

/**
 * @author liqingyang
 * @date 2016-6-8 下午6:08:40
 */
@TargetUrl("http://www.spicezee.com/xinwen/*.html")
public class SicRepo {
	
	@ExtractBy(value="//h1[@class='pagetit']/text()")
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public static void main(String[] args){
		OOSpider.create(Site.me().setRetryTimes(3).setSleepTime(100), new ConsolePageModelPipeline(), SicRepo.class)
		.addUrl("http://www.spicezee.com/xinwen/134449.html").thread(2).start();
	}
	
	@Override
	public String toString(){
		return title;
	}
}
