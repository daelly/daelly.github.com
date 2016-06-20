/**
 * 
 */
package com.redsea.controller.web;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.redsea.model.Article;
import com.redsea.model.Tags;
import com.redsea.model.Topic;

/**
 * @author chenxiaofeng
 * @date 2016-5-4 下午5:26:09
 */
public class TopicController extends Controller {
	public void common(){
		setAttr("list_tuijian",Article.dao.getListByCache(1));
		setAttr("list_hot",Article.dao.getListByCache(2));
		setAttr("allTags", Tags.dao.getAllByCache());
		setAttr("list_new",Article.dao.getListByCache(3));
	}
	
	public void index() throws Exception{
		/*if(CheckMobileUtils.check(getRequest())){
			redirect("/m/p/"+getPara());
			return;
		}*/
		if(StrKit.isBlank(getPara(0))||"index".equals(getPara(0))){
			setAttr("page", Topic.dao.getTopicPage(getPara(1)));
			setAttr("action", "topic/index");
			common();
		}else{
			Topic topic=Topic.dao.findById(getPara());
			if(topic==null){
				renderError(404);
				return;
			}
			String path="detail.jsp";
			String template=topic.getTemplate();
			if(StrKit.notBlank(template)){
				path="template/"+topic.getTemplate()+".jsp";
			}
			if(StrKit.notBlank(template)){
				if("template".equals(template)){
					setAttr("bannerData", Article.dao.getTopicBannerData(topic.getId()));
					setAttr("page", Article.dao.gettArticlePageByTopicId2(getPara(0),getPara(1)));
					setAttr("topicList", Topic.dao.getRecentTopic());
				}else if("template2".equals(template)){
					setAttr("bannerData", Article.dao.getTopicBannerData(topic.getId()));
					setAttr("page", Article.dao.gettArticlePageByTopicId2(getPara(0),getPara(1)));
					setAttr("topicList", Topic.dao.getRecentTopic());
				}
			}else{
				setAttr("page", Article.dao.gettArticlePageByTopicId(getPara(0),getPara(1)));
			}
			setAttr("topic", topic);
			common();
			setAttr("action", "topic/d/"+getPara(0));
			render(path);
		}
	}
	public void d(){
		Topic topic=Topic.dao.findById(getPara());
		String path="detail.jsp";
		String template=topic.getTemplate();
		if(StrKit.notBlank(template)){
			path="template/"+topic.getTemplate()+".jsp";
		}
		if(StrKit.notBlank(template)){
			if("template".equals(template)){
				setAttr("bannerData", Article.dao.getTopicBannerData(topic.getId()));
				setAttr("page", Article.dao.gettArticlePageByTopicId2(getPara(0),getPara(1)));
				setAttr("topicList", Topic.dao.getRecentTopic());
			}else if("template2".equals(template)){
				setAttr("bannerData", Article.dao.getTopicBannerData(topic.getId()));
				setAttr("page", Article.dao.gettArticlePageByTopicId2(getPara(0),getPara(1)));
				setAttr("topicList", Topic.dao.getRecentTopic());
			}
		}else{
			setAttr("page", Article.dao.gettArticlePageByTopicId(getPara(0),getPara(1)));
		}
		setAttr("topic", topic);
		common();
		setAttr("action", "topic/d/"+getPara(0));
		render(path);
	}
}
