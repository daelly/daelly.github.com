package com.redsea.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.redsea.ext.base.BaseModel;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseArticleTags<M extends BaseArticleTags<M>> extends BaseModel<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setArticleId(java.lang.Integer articleId) {
		set("article_id", articleId);
	}

	public java.lang.Integer getArticleId() {
		return get("article_id");
	}

	public void setTagId(java.lang.Integer tagId) {
		set("tag_id", tagId);
	}

	public java.lang.Integer getTagId() {
		return get("tag_id");
	}

}
