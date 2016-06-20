package com.redsea.controller.web;

import java.io.UnsupportedEncodingException;

import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.jfinal.ext.render.StaticHtmlRender;
import com.jfinal.kit.StrKit;
import com.redsea.common.WebUtils;
import com.redsea.ext.base.BaseController;
import com.redsea.interceptor.AdminInterceptor;
import com.redsea.model.Article;
import com.redsea.model.ArticleTags;
import com.redsea.model.Folder;
import com.redsea.model.Tags;
import com.redsea.ui.tag.Functions;
import com.redsea.utils.CheckMobileUtils;
import com.redsea.utils.URLUtils;

/**
 * @author chenxiaofeng
 * @date 2016-4-14 下午4:38:32
 */
public class IndexController extends BaseController {
	
	public void index(){
		if(CheckMobileUtils.check(getRequest())){
			forwardAction("/m");
			return;
		}
		String city="";
		if(StrKit.notBlank(getPara("city"))){
			 city=getPara("city");
		}else{
			city=WebUtils.getAddress(getRequest(), getResponse());
		}
		if("1".equals(getPara("type"))){
			common();
			setAttr("bannerData", Article.dao.getBannerData());
			setAttr("page",Article.dao.getArticlePageByFolderId(null,null,getPara()));
			render("index.jsp");
			return;
		}
		setAttr("data",Article.dao.getHomeData(city));
		render("index-02.jsp");
	}
	public void getHomeData(){
		String address=WebUtils.getAddress(getRequest());
		renderJson(Article.dao.getHomeData(address));
	}
	
	public void indexPage(){
		String city="";
		if(StrKit.notBlank(getPara("city"))){
			 city=getPara("city");
		}else{
			city=WebUtils.getAddress(getRequest(), getResponse());
		}
		renderJson(Article.dao.getArticlePageByFolderId(city,null,getPara()));
	}
	public void search() throws UnsupportedEncodingException{
		String address = WebUtils.getAddress(getRequest());
		if(StrKit.isBlank(getPara(0))){
			alertAndGoback("请输入关键字!", -1);
			return;
		}
		common();
		String keyword=URLUtils.decode(getPara(0));
		if("1".equals(Functions.getValueFromCache("search_type"))){
			setAttr("page",Article.dao.findPageBySolr(keyword,getPara(1),address));
		}else{
			setAttr("page", Article.dao.searchtArticlePage(keyword,getPara(1)));
		}
		setAttr("keyword", keyword);
		setAttr("folderName","\""+keyword+"\"搜索结果");
		setAttr("action", "search/"+keyword);
		render("search.jsp");
	}
	
	@ActionKey("/p")
	public void p() throws Exception{
		if(CheckMobileUtils.check(getRequest())){
			forwardAction("/m/p/"+getPara());
			return;
		}
		if(StrKit.notBlank(getPara())){
			String folderEnName=getPara(0);
			Folder folder=Folder.dao.findFirstByColumn("en_name", folderEnName);
			if(folder==null){
				renderError(404);
				return;
			}else{
				try {
					setAttr("page", Article.dao.getArticlePageByFolderId(null,folder.getId(),getPara(1)));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			setAttr("folder", folder);
			//setAttr("folderName", folder.getTitle());
			setAttr("action", "p/"+folderEnName);
			common();
			render("list.jsp");
		}else{
			index();
		}
	}
	public void tags(){
		if(CheckMobileUtils.check(getRequest())){
			forwardAction("/m/tags/"+getPara());
			return;
		}
		common();
		Tags tags=Tags.dao.findById(getPara());
		setAttr("folderName", tags.getTagName());
		setAttr("action", "tags/"+getPara(0));
		setAttr("page",Article.dao.getArticlePageByTagsId(getPara(),getPara(1)));
		render("list.jsp");
	}
	public void common(){
		setAttr("list_tuijian",Article.dao.getListByCache(1));
		setAttr("list_hot",Article.dao.getListByCache(2));
		setAttr("allTags", Tags.dao.getAllByCache());
		setAttr("list_new",Article.dao.getListByCache(3));
	}
	
	public void article(){
		String articleId=getPara();
		if(CheckMobileUtils.check(getRequest())){
			forwardAction("/m/article/"+articleId);
			return;
		}
		Article article= Article.dao.findById(articleId);
		if(article==null){
			renderError(404);
			return;
		}else if(article.getStatus()==0){
			renderText("禁止访问,此文章暂未审核通过!");
			return;
		}
		if(StrKit.notBlank(article.getLink())){
			forwardAction(article.getLink());
			return;
		}
		String template=article.getTemplate();
		if(StrKit.isBlank(template)){
			template="template.jsp";
		}else{
			template=template+".jsp";
		}
		article.setCountView(article.getCountView()+1);
		article.update();
		common();
		
		setAttr("article",article);
		setAttr("tags", ArticleTags.dao.getArticleTags(articleId));
		render("article/"+template);
	}
	
	@Before(AdminInterceptor.class)
	public void view(){
		String articleId=getPara();
		Article article= Article.dao.findById(articleId);
		if(article==null){
			renderError(404);
			return;
		}
		if(StrKit.notBlank(article.getLink())){
			forwardAction(article.getLink());
			return;
		}
		String template=article.getTemplate();
		if(StrKit.isBlank(template)){
			template="template.jsp";
		}else{
			template=template+".jsp";
		}
		setAttr("article",article);
		
		common();
		setAttr("tags", ArticleTags.dao.getArticleTags(articleId));
		render("article/"+template);
	}
	
	public void channel(){
		String folderEnName=getPara(0);
		if(CheckMobileUtils.check(getRequest())){
			forwardAction("/m/p/"+folderEnName);
			return;
		}
		Integer foldId=Folder.dao.getFoldIdByEnName(folderEnName);
		if(foldId==null){
			Folder folder=Folder.dao.findFirstByColumn("en_name", folderEnName);
			if(folder==null){
				renderError(404);
				return;
			}else{
				foldId=folder.getId();
			}
		}
		setAttr("allTags", Tags.dao.getAllByCache());
		setAttr("folder", Folder.dao.findCacheById(foldId));
		setAttr("article", Article.dao.getChannelArticleByFoldId(foldId));
		setAttr("readingRanking", Article.dao.getChannelReadingRankingByFoldId(foldId));
		setAttr("banner",Article.dao.getChannelBannerDataByFoldId(foldId));
		setAttr("recommendArticleList", Article.dao.getChannelRecommenddArticleByFoldId(foldId));
		setAttr("page", Article.dao.getArticlePageByFolderId(null,foldId,getPara(1)));
		setAttr("action", "channel/"+folderEnName);
		render("list-02.jsp");
	}
	
}



