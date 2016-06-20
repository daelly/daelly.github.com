package com.redsea.ui.tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.jstl.core.LoopTag;
import javax.servlet.jsp.tagext.IterationTag;

import org.apache.taglibs.standard.tag.common.core.ForEachSupport;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Sqls;
import com.redsea.common.Consts;


public class SqlsTag extends ForEachSupport implements LoopTag, IterationTag {

	private static final long serialVersionUID = 6139426859096232015L;

	private String sqlKey;
	private String parameters;
	private Integer limit;
	
	public SqlsTag() {
		super();
		init();
	}
	
	private void init() {
		sqlKey = null;
		parameters = null;
		limit = null;
	}
	
	@Override
	public int doStartTag() throws JspException {
		if (StrKit.isBlank(sqlKey)) {
			throw new JspException("sqls tag attribute sqlKey can not be blank!");
		}
		String sql = Sqls.get(sqlKey);
		final List<Object> parameterList = new ArrayList<Object>();
		
		String cacheKey = sqlKey;
		if (StrKit.notBlank(parameters)) {
			cacheKey += parameters;
			Object[] paras = parameters.split(";");
			parameterList.addAll(Arrays.asList(paras));
		}
		if (null != limit) {
			cacheKey += limit;
			parameterList.add(limit);
		}
		rawItems = Db.findByCache(Consts.CacheName.halfHour.get(), cacheKey, sql, parameterList.toArray());
		return super.doStartTag();
	}

	@Override
	public void release() {
		super.release();
		init();
	}
	
	public void setSqlKey(String sqlKey) {
		this.sqlKey = sqlKey;
	}
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	
}
