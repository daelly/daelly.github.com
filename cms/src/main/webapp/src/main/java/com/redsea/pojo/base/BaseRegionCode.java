package com.redsea.pojo.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseRegionCode<M extends BaseRegionCode<M>> extends Model<M> implements IBean {

	public void setRegionCode(java.lang.String regionCode) {
		set("region_code", regionCode);
	}

	public java.lang.String getRegionCode() {
		return get("region_code");
	}

	public void setParentRegionCode(java.lang.String parentRegionCode) {
		set("parent_region_code", parentRegionCode);
	}

	public java.lang.String getParentRegionCode() {
		return get("parent_region_code");
	}

	public void setName(java.lang.String name) {
		set("name", name);
	}

	public java.lang.String getName() {
		return get("name");
	}

	public void setPyName(java.lang.String pyName) {
		set("py_name", pyName);
	}

	public java.lang.String getPyName() {
		return get("py_name");
	}

	public void setShortName(java.lang.String shortName) {
		set("short_name", shortName);
	}

	public java.lang.String getShortName() {
		return get("short_name");
	}

	public void setSName(java.lang.String sName) {
		set("s_name", sName);
	}

	public java.lang.String getSName() {
		return get("s_name");
	}

	public void setSort(java.lang.Integer sort) {
		set("sort", sort);
	}

	public java.lang.Integer getSort() {
		return get("sort");
	}

	public void setArticleId(java.lang.Integer articleId) {
		set("article_id", articleId);
	}

	public java.lang.Integer getArticleId() {
		return get("article_id");
	}

	public void setIsHot(java.lang.Integer isHot) {
		set("is_hot", isHot);
	}

	public java.lang.Integer getIsHot() {
		return get("is_hot");
	}

}