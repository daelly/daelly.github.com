/**
 * 
 */
package com.redsea.controller.system;

import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.pipeline.MultiPagePipeline;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.ehcache.CacheKit;
import com.redsea.common.CacheConstants;
import com.redsea.common.Consts;
import com.redsea.common.WebUtils;
import com.redsea.ext.base.BaseController;
import com.redsea.ext.base.IController;
import com.redsea.ext.plugin.webmagic.multipage.CNYS;
import com.redsea.ext.plugin.webmagic.multipage.CNYSJdbcPipeline;
import com.redsea.interceptor.AdminInterceptor;
import com.redsea.model.Article;
import com.redsea.model.ArticleTags;
import com.redsea.model.Folder;
import com.redsea.model.Tags;
import com.redsea.ui.tag.Functions;
import com.redsea.vo.AjaxResult;

/**
 * @author chenxiaofeng
 * @date 2016-4-13 下午1:03:01
 */
@Before(AdminInterceptor.class)
public class ArticleController extends BaseController implements IController {
	
	private Logger log = LoggerFactory.getLogger(ArticleController.class);

	public void review_list(){
		
	}
	@Override
	public void list() {
		renderJson(Article.dao.findPageJsonByConditions(getParaMap()));
	}
	public void index_home(){
		
	}
	public void updateSort(){
		Integer id=getParaToInt("id");
		Integer sort=getParaToInt("sort");
		Article article=Article.dao.findById(id);
		article.setSort(sort);
		article.update();
		renderText("更新成功!");
	}

	@Clear(AdminInterceptor.class)
	@Override
	public void add() {
		if(!isPost()){
			renderJson(new AjaxResult().addError("非法请求！"));
			return;
		}
		Article article=getModel(Article.class,"article");
		String address = article.getAddress();
		if(StrKit.isBlank(address))
			article.set("address", "");
		Article a = null;
		try {
			a = Article.dao.findFirstByColumn("origin_url", article.getStr("origin_url"));
		} catch (Exception e) {
			e.printStackTrace();
		}
    	if(a!=null){
    		log.debug("地址：“"+article.getStr("")+"”已被采集过");
    		renderJson(new AjaxResult().addError("此url已被采集过！"));
    		return;
    	}
		article.setPublishTime(new Date());
		Article.dao.saveOrUpdate(article);
		renderJson(new AjaxResult().success(article));
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
	public void fieldsSelect(){
		setAttr("article",Article.dao.findById(getPara()));
		render("fields_select.jsp");
	}
	public void edit_content(){
		setAttr("article",Article.dao.findById(getPara()));
	}
	@Override
	public void edit()  {
		if(!StrKit.isBlank( getPara())){
			try {
				List articleTags=ArticleTags.dao.getArticleAllTags(getPara());
				setAttr("articleTags", articleTags);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		setAttr("article",Article.dao.findById(getPara()));

	}
	public void updateKeyWord() throws IOException{
	  List<Article> list=Article.dao.find("SELECT id,title from tbl_article where keyword is null limit 1000");
	  if(list!=null){
		  for(Article article:list){
			  String title=article.getTitle();
			  article.setKeyword(getKeyword(title));
			  article.update();
		  }
	  }
	  renderText("更新Keyword成功!");
	}
	public void updateSummary() throws IOException{
		List<Article> list=Article.dao.find("SELECT id,content from tbl_article where summary is null limit 1000");
		if(list!=null){
			for(Article article:list){
				String content=article.getContent();
				content=Functions.filterSubText(content,160);
				article.setSummary(content);
				article.update();
			}
		}
		renderText("更新Summary成功!");
	}
	public void  getSummary() throws IOException{
		String content=getPara("content");
		if(StrKit.isBlank(content)){
			renderText("");
			return;
		}
		content=Functions.filterSubText(content,160);
		renderText(content);
	}
	public String getKeyword(String title) throws IOException{
		StringReader re = new StringReader(title);
		StringBuffer sb=new StringBuffer();
		IKSegmenter ik = new IKSegmenter(re,false);
		Lexeme lex = null;
		while((lex=ik.next())!=null){
			String result=lex.getLexemeText();
			if(result.length()>1){
				sb.append(result).append(",");
			}
		}
		if(sb.length()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}
	public void getKeyword() throws IOException{
		 String title=getPara("title");
		 if(StrKit.isBlank(title)){
			 renderText("");
			 return;
		 }
		 renderText(getKeyword(title));
	}
	@Override
	public void view() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void save() {
		Article article=getModel(Article.class,"article");
		String address = article.getAddress();
		if(StrKit.isBlank(address))
			article.set("address", "");
		if(article.getStatus()!=null&&article.getStatus()==1&&article.getPublishTime()==null){
			article.setPublishTime(new Date());
		}
		article.saveOrUpdate(article,getRequest());
		String[] tagsName=getParaValues("tags");
		if(tagsName!=null&&tagsName.length>0){
			for(String name:tagsName){
				try {
					Tags tags=Tags.dao.findFirstByColumn("tag_name", name);
					if(tags==null){
						tags=new Tags();
						tags.setTagName(name);
						tags.save();
					}
					Integer tagsId=tags.getId();
					ArticleTags articleTags=new ArticleTags();
					articleTags.setTagId(tagsId);
					articleTags.setArticleId(article.getId());
					articleTags.save();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		renderJson(new AjaxResult().success(article));
	}
	public void deleteTags(){
		renderJson(new AjaxResult(ArticleTags.dao.deleteById(getPara())));
	}
	@Override
	public void delete() {
		renderJson(new AjaxResult(Article.dao.deleteById(getPara())));
	}
	public void batchDelete(){
		if(getParaValues("ids")==null||getParaValues("ids").length<=0){
			renderJson(new AjaxResult(false));
		}else{
			for(int i = 0;i<getParaValues("ids").length;i++){
				String id = getParaValues("ids")[i];
				renderJson(new AjaxResult(Article.dao.deleteById(id)));
			}
		}
	}
	public void batchReview(){
		if(getParaValues("ids")==null||getParaValues("ids").length<=0){
			renderJson(new AjaxResult(false));
		}else{
			String msg="";
			for(int i = 0;i<getParaValues("ids").length;i++){
				String id = getParaValues("ids")[i];
				Article article=Article.dao.findById(id);
				if(article.getFolderId()==null||article.getPublishTime()==null||StrKit.isBlank(article.getTitle())||StrKit.isBlank(article.getContent())){
					msg=msg+article.getTitle()+" (<font color='red'>失败</font>)，";
					continue;
				}
				msg=msg+article.getTitle()+" (<font color='green'>成功</font> )，";
				article.setStatus(1);
				article.saveOrUpdate(article, getRequest());
			}
			renderJson(new AjaxResult().success(getParaValues("ids"),msg));
		}
	}
	
	public void push_page(){
		
	}
	
	public void push(){
		try {
			String result="";
			if(StrKit.isBlank(getPara("ids"))){
				result="";
			}else{
				final String postUrl = "http://data.zz.baidu.com/urls?site=www.sbyun.com&token=X6ThJlrkI2PCcMGA";
				String[] ids = getPara("ids").split(",");
				String[] urls = new String[ids.length];
				String regex = "(http|ftp|https)://.*";
				for(int i = 0;i<ids.length;i++){
					String id = ids[i];
					if(id.matches(regex)){//如果
						urls[i] = id;
					}else
						urls[i] = "http://www.sbyun.com/article/"+id+".html";
				}
				result = WebUtils.Post(postUrl, urls);
			}
			renderJson(new AjaxResult().success(JSONObject.parse(result)));
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(new AjaxResult().addError("推送失败"));
		}
	}
	
	public void pushUrls(){
		String url = getPara("urls");
		try {
			String[] urls=url.split("\r\n");
			for (int i = 0; i < urls.length; i++) {
				urls[i] = urls[i].trim();
			}
			final String postUrl = "http://data.zz.baidu.com/urls?site=www.sbyun.com&token=X6ThJlrkI2PCcMGA";
			String result = WebUtils.Post(postUrl, urls);
			renderJson(new AjaxResult().success(JSONObject.parse(result)));
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(new AjaxResult().addError("推送失败"));
		}
		
	}
	public void updateHomeCache(){
		//String city=WebUtils.getAddress(getRequest());
		CacheKit.remove(CacheConstants.CONSTANTS_HOME_CACHE,"home_tuijian");
		CacheKit.remove(CacheConstants.CONSTANTS_HOME_CACHE,"home_banner");
		CacheKit.remove(Consts.CacheName.oneDay.get(), "folder_all_map");
		log.debug("清除缓存首页推荐缓存成功!");
		renderText("更新首页缓存成功!");
	}
	public void updateHomeCityCache(){
		List list=CacheKit.getKeys(CacheConstants.CONSTANTS_HOME_CACHE);
		Iterator iterator=list.iterator();
		while(iterator.hasNext()){
			String key=iterator.next().toString();
			if(key.startsWith("city_")){
				CacheKit.remove(CacheConstants.CONSTANTS_HOME_CACHE, key);
				Article.dao.getHomeData(key);
			}
		}
		
		log.debug("更新默认首页缓存成功!");
		renderText("更新首页缓存成功!");
	}
	public void tree() throws Exception{
		List<Folder> folders = Folder.dao.findAll();
		JSONArray treeNodes = new JSONArray();
		for (Folder folder : folders) {
			JSONObject obj = new JSONObject();
			obj.put("id", folder.get("id"));
			obj.put("parent_id", folder.get("parent_id"));
			obj.put("name", folder.get("name"));
			treeNodes.add(obj);
		}
		setAttr("treeNodes", treeNodes.toString());
	}
	
	public void cyns(){
		OOSpider.create(Site.me(), CNYS.class).addUrl("http://www.cnys.com/zixun/61602.html")
        .addPipeline(new MultiPagePipeline()).addPipeline(new CNYSJdbcPipeline()).run();
		renderJson("done!");
	}
}
