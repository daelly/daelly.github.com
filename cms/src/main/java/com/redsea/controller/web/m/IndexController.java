/**
 * 
 */
package com.redsea.controller.web.m;

import java.util.List;

import com.jfinal.kit.StrKit;
import com.redsea.common.WebUtils;
import com.redsea.ext.base.BaseController;
import com.redsea.model.Article;
import com.redsea.model.ArticleTags;
import com.redsea.model.Folder;
import com.redsea.model.RegionCode;
import com.redsea.model.Tags;
import com.redsea.model.Topic;

/**
 * @author chenxiaofeng
 * @date 2016-4-27 下午6:42:44
 */
public class IndexController extends BaseController {

	public void index(){
		setAttr("bannerData", Article.dao.getBannerData());
		String city="";
		if(StrKit.notBlank(getPara("city"))){
			 city=getPara("city");
		}else{
			city=WebUtils.getAddress(getRequest(), getResponse());
		}
		setAttr("city", city);
		setAttr("page",Article.dao.getArticlePageByFolderId(city,null,getPara()));
	}
	public void channel(){
		String folderEnName=getPara(0);
		forwardAction("/m/p/"+folderEnName);
	}
	public void chaxun(){
		String str=getPara();
		if(StrKit.notBlank(str)&&!"index".equals(str)){
			RegionCode regionCode=RegionCode.dao.findFirstByColumn("py_name", str);
			redirect("/article/"+regionCode.getArticleId());
			return;
		}else{
			setAttr("list2", RegionCode.dao.getRegionCodeFromCache());
		}
		render("chaxun/index.jsp");
	}
	public void topic(){
		Topic topic=Topic.dao.findById(getPara());
		if(topic==null){
			renderError(404);
			return;
		}
		setAttr("page", Article.dao.gettArticlePageByTopicId2(getPara(0),getPara(1)));
		setAttr("topic", topic);
		setAttr("action", "m/topicData/"+topic.getId()+"-");
	}
	public void topicData(){
		renderJson(Article.dao.gettArticlePageByTopicId2(getPara(0),getPara(1)));
	}
	public void p() throws Exception{
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
			setAttr("folderName", folder.getName());
			setAttr("action", "m/pPage/"+folderEnName+"-");
			render("list.jsp");
		}else{
			renderError(404);
		}
	}
	public void pPage() throws Exception{
		String folderEnName=getPara(0);
		Folder folder=Folder.dao.findFirstByColumn("en_name", folderEnName);
		renderJson( Article.dao.getArticlePageByFolderId(null,folder.getId(),getPara(1)));
	}
	public void tools(){
		
	}
	public void tags(){
		Tags tags=Tags.dao.findById(getPara());
		setAttr("folderName", tags.getTagName());
		setAttr("action", "m/tagsPage/"+getPara()+"-");
		setAttr("page",Article.dao.getArticlePageByTagsId(getPara(),null));
		render("list.jsp");
	}
	public void tagsPage(){
		renderJson(Article.dao.getArticlePageByTagsId(getPara(),getPara(1)));
	}
	public void article(){
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
		List articleTags=ArticleTags.dao.getArticleTags(articleId);
		setAttr("tags", articleTags);
		
		article.setCountView(article.getCountView()+1);
		article.update();
		setAttr("article",article);
	}
}
