package com.redsea.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.redsea.ext.base.BaseModel;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseSpiderJob<M extends BaseSpiderJob<M>> extends BaseModel<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setJobName(java.lang.String jobName) {
		set("job_name", jobName);
	}

	public java.lang.String getJobName() {
		return get("job_name");
	}

	public void setUrlPage(java.lang.String urlPage) {
		set("url_page", urlPage);
	}

	public java.lang.String getUrlPage() {
		return get("url_page");
	}

	public void setLinksRule(java.lang.String linksRule) {
		set("links_rule", linksRule);
	}

	public java.lang.String getLinksRule() {
		return get("links_rule");
	}

	public void setSpideRuleId(java.lang.Integer spideRuleId) {
		set("spide_rule_id", spideRuleId);
	}

	public java.lang.Integer getSpideRuleId() {
		return get("spide_rule_id");
	}

}
