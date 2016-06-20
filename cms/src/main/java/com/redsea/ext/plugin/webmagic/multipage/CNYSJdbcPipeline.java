/**
 * 
 */
package com.redsea.ext.plugin.webmagic.multipage;

import java.util.Iterator;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfinal.kit.StrKit;
import com.redsea.model.Article;

import us.codecraft.webmagic.MultiPageModel;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @author liqingyang
 * @date 2016-6-12 下午1:56:25
 */
public class CNYSJdbcPipeline implements Pipeline {
	
	private Logger logger = LoggerFactory.getLogger(CNYSJdbcPipeline.class);

	/* (non-Javadoc)
	 * @see us.codecraft.webmagic.pipeline.Pipeline#process(us.codecraft.webmagic.ResultItems, us.codecraft.webmagic.Task)
	 */
	@Override
	public void process(ResultItems resultItems, Task task) {
		 Map<String, Object> resultItemsAll = resultItems.getAll();
	     Iterator<Map.Entry<String, Object>> iterator = resultItemsAll.entrySet().iterator();
	     while (iterator.hasNext()) {
	         Map.Entry<String, Object> objectEntry = iterator.next();
	         Object o = objectEntry.getValue();
	         if(o instanceof MultiPageModel){
            	 CNYS cnys = (CNYS) o;
            	 String url = resultItems.getRequest().getUrl();
            	 url = url.replace("_\\d+", "");//将分页的部分去掉，取第一页的url
            	 Article a1=Article.dao.findFirstByColumn("origin_url", url);
            	 if(a1 != null){
            		 logger.info("此url已被采集");
            		 continue;
            	 }
            	 Article article = new Article();
            	 article.setTitle(cnys.getTitle());
            	 article.setContent(cnys.getContent());
            	 if(StrKit.notBlank(article.getTitle()) && StrKit.notBlank(article.getContent())){
            		 article.saveOrUpdate(article);
            	 }else{
            		 logger.info("采集到标题或内容为空，跳过保存");
            	 }
            		 
	          }
	     }
	}

}
