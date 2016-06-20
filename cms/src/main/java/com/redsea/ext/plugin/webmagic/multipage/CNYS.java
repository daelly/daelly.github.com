package com.redsea.ext.plugin.webmagic.multipage;

import us.codecraft.webmagic.MultiPageModel;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.ExtractByUrl;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import us.codecraft.webmagic.pipeline.MultiPagePipeline;
import java.util.Collection;
import java.util.List;

/**
 * @author code4crafter@gmail.com <br>
 */
//这里是示例只采集一篇文章，实际采集的时候换成对应的文章的正则表达式,下面的几个域也换相应的xpath表达式
@TargetUrl("http://www.cnys.com/zixun/61602(_\\d+)?.html")
@HelpUrl("http://www.cnys.com/zixun/")
public class CNYS implements MultiPageModel {

	//这篇文章的id，这个字段可以将几个分页的内容最终归并到一起，一篇文章的不同页的pageKey是相同的
    @ExtractByUrl("http://www\\.cnys\\.com/zixun/([^_]*).*\\.html")
    private String pageKey;

    //这是文章的第几页
    @ExtractByUrl(value = "http://www\\.cnys\\.com/zixun/61602_(\\d+)\\.html", notNull = false)
    private String page;

    //文章还有几页
    @ExtractBy(value = "//div[@class=\"page\"]//a/regex('http://www\\.cnys\\.com/zixun/61602_(\\d+)\\.html',1)"
            , multi = true, notNull = false)
    private List<String> otherPage;

    //再次之后都是页面上要采集的字段，想在只有标题和内容，可以将摘要和其他添加进来。
    @ExtractBy("//h1/text()")
    private String title;

    @ExtractBy("//div[@class=\"reads\"]")
    private String content;

    @Override
    public String getPageKey() {
        return pageKey;
    }

    @Override
    public Collection<String> getOtherPages() {
        return otherPage;
    }

    @Override
    public String getPage() {
        if (page == null) {
            return "1";
        }
        return page;
    }

    @Override
    public MultiPageModel combine(MultiPageModel multiPageModel) {
        CNYS news163 = new CNYS();
        news163.title = this.title;
        CNYS pagedModel1 = (CNYS) multiPageModel;
        news163.content = this.content + pagedModel1.content;
        return news163;
    }

    @Override
    public String toString() {
        return "News163{" +
                "content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", otherPage=" + otherPage +
                '}';
    }

    public static void main(String[] args) {
    	//注意这里设置起始抓取页面的时候，一定要设置文章的第一页，否则，会出现第一页无法被抓取的情况
    	//使用两个pipeline，第一个做处理，如果发现此文章的其他页面没有被抓取，当前抓到的page将被remove掉不处理，下一个我们自定义
    	//处理的pipeline中将直接跳过
        OOSpider.create(Site.me(), CNYS.class).addUrl("http://www.cnys.com/zixun/61602.html")
                .addPipeline(new MultiPagePipeline()).addPipeline(new CNYSJdbcPipeline()).run();
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
