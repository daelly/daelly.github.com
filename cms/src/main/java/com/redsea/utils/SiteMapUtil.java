/**
 * 
 */
package com.redsea.utils;

import java.io.File;
import java.net.MalformedURLException;

import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Page;
import com.redfin.sitemapgenerator.ChangeFreq;
import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;
import com.redsea.model.Article;

/**
 * @author chenxiaofeng
 * @date 2016-5-25 下午6:18:53
 */
public class SiteMapUtil {
	
	 public static void build() {
		 	File file=new File(PathKit.getWebRootPath()+"\\sitemap.xml");
			file.deleteOnExit();
		    try {
				WebSitemapGenerator sitemapGenerator = WebSitemapGenerator.builder("http://www.sbyun.com", new File(PathKit.getWebRootPath())).gzip(false).build();
				WebSitemapUrl sitemapUrl =null;
				for(int i=1;i<=50;i++){
					Page<Article> page=Article.dao.paginate(i, 100, "select id,update_time", " from tbl_article  where status=1 order by update_time desc");
						page=Article.dao.paginate(i, 100, "select id,update_time", " from tbl_article  where status=1 order by update_time");
						for(Article article:page.getList()){
							sitemapUrl = new WebSitemapUrl.Options("http://www.sbyun.com/article/"+article.getId()+".html").lastMod(article.getUpdateTime()).priority(1.0).changeFreq(ChangeFreq.DAILY).build();
							sitemapGenerator.addUrl(sitemapUrl);
						}

				}
				sitemapGenerator.write();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		 } 
}
