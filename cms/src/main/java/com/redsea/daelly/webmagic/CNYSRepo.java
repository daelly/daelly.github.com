package com.redsea.daelly.webmagic;

import java.util.Collection;
import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.ExtractByUrl;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.MultiPagePipeline;
import us.codecraft.webmagic.selector.Selectable;

/**
 * @author liqingyang
 * @date 2016-6-8 下午6:23:33
 */
@TargetUrl("http://www.cnys.com/zixun/\\d+(_\\d+)?.html")
@HelpUrl("http://www.cnys.com/zixun/")
public class CNYSRepo implements PagedModel, AfterExtractor {
	
	@ExtractByUrl("http://www\\.cnys\\.com/zixun/(\\d+)*\\.html")
	private String pageKey;
	
	@ExtractByUrl("http://www\\.cnys\\.com/zixun/\\d+_(\\d+)\\.html")
	private String page;
	
	private List<String> otherPages;
	
	@ExtractBy(value="//div[@class='reads']/html()")
	private String content;

	/* (non-Javadoc)
	 * @see us.codecraft.webmagic.model.AfterExtractor#afterProcess(us.codecraft.webmagic.Page)
	 */
	@Override
	public void afterProcess(Page page) {
		Selectable xpath = page.getHtml().xpath("//div[@class=\"page\"]//a/@href");
		otherPages = xpath.regex("http://www\\.cnys\\.com/zixun/\\d+_(\\d+)\\.html").all();
	}

	/* (non-Javadoc)
	 * @see com.redsea.daelly.webmagic.PagedModel#getPageKey()
	 */
	@Override
	public String getPageKey() {
		return this.pageKey;
	}

	/* (non-Javadoc)
	 * @see com.redsea.daelly.webmagic.PagedModel#getOtherPages()
	 */
	@Override
	public Collection<String> getOtherPages() {
		return otherPages;
	}

	/* (non-Javadoc)
	 * @see com.redsea.daelly.webmagic.PagedModel#getPage()
	 */
	@Override
	public String getPage() {
		if(page==null)
			return "1";
		return page;
	}

	/* (non-Javadoc)
	 * @see com.redsea.daelly.webmagic.PagedModel#combine(com.redsea.daelly.webmagic.PagedModel)
	 */
	@Override
	public PagedModel combine(PagedModel pagedModel) {
		CNYSRepo repo = new CNYSRepo();
		CNYSRepo repo1 = (CNYSRepo)pagedModel;
		repo.setContent(this.content+repo1.getContent());
		return repo;
	}
	
	@Override
	public String toString(){
		return "CNYSRepo{content='"+content+"',otherPages="+otherPages+"}";
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setPageKey(String pageKey) {
		this.pageKey = pageKey;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public static void main(String[] args){
		OOSpider.create(Site.me(), CNYSRepo.class).addUrl("http://www.cnys.com/zixun/61602_2.html")
        .addPipeline(new MultiPagePipeline()).addPipeline(new ConsolePipeline()).run();
	}
}
