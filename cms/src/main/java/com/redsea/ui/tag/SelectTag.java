/**
 * 
 */
package com.redsea.ui.tag;

import java.util.List;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.Sqls;
import com.redsea.common.Consts;
import com.redsea.ui.jsp.ParentTag;

/**
 * @author chenxiaofeng
 * @date 2016-4-12 下午7:36:04
 */
public class SelectTag extends ParentTag{
	private static final long serialVersionUID = 2251179564076088857L;
	private String sql;
	private String dblabel;
	private String dbvalue;
	private String sqlKey;
	//第一个option
	private String headerKey = "";
	private String headerValue = "";
	private boolean nullValue=true;//是否将第一个option设置成""值
	private String rules = "";//验证规则
	
	@Override
	public String processResult() {
		Object value = getExValue(getValue());
		List<Record> list=null;
		if(StrKit.notBlank(sqlKey)){
			sql=Sqls.get(sqlKey);
			list=Db.findByCache(Consts.CacheName.halfHour.get(),sqlKey,sql);
		}else{
			list=Db.find(sql);
		}
		StringBuilder sb = new StringBuilder("");
			headerValue = StrKit.isBlank(headerValue)?"----":headerValue;
			if(StrKit.notBlank(rules)){
				rules = "datatype=\""+rules+"\"";
			}
			sb.append("<select "+rules+"  "+getHtmlAttributes()+">");
			if(StrKit.isBlank(headerKey)||nullValue){
				sb.append("<option value=\"\">").append(headerValue).append("</option>");
			}else{
				sb.append("<option value=\""+headerKey+"\">").append(headerValue).append("</option>");
			}
			if(list!=null&&list.size()>0){
				for(Record record:list){
					 sb.append("<option value=\""+record.get(""+dbvalue+"")+"\"");
					if(record.get(""+dbvalue+"").toString().equals(value)){
						sb.append(" selected=\"selected\" ");
					}
					sb.append("\">"+record.get(""+dblabel+"")+"</option>");
				}
		}
			sb.append("</select>");
		return sb.toString();
	}


	public String getHeaderKey() {
		return headerKey;
	}


	public void setHeaderKey(String headerKey) {
		this.headerKey = headerKey;
	}

	public String getHeaderValue() {
		return headerValue;
	}


	public void setHeaderValue(String headerValue) {
		this.headerValue = headerValue;
	}


	/**
	 * @return the sql
	 */
	public String getSql() {
		return sql;
	}

	/**
	 * @param sql the sql to set
	 */
	public void setSql(String sql) {
		this.sql = sql;
	}

	/**
	 * @return the dblabel
	 */
	public String getDblabel() {
		return dblabel;
	}

	/**
	 * @param dblabel the dblabel to set
	 */
	public void setDblabel(String dblabel) {
		this.dblabel = dblabel;
	}

	/**
	 * @return the dbvalue
	 */
	public String getDbvalue() {
		return dbvalue;
	}

	/**
	 * @param dbvalue the dbvalue to set
	 */
	public void setDbvalue(String dbvalue) {
		this.dbvalue = dbvalue;
	}

	

	/**
	 * @return the nullValue
	 */
	public boolean isNullValue() {
		return nullValue;
	}

	/**
	 * @param nullValue the nullValue to set
	 */
	public void setNullValue(boolean nullValue) {
		this.nullValue = nullValue;
	}

	/**
	 * @return the rules
	 */
	public String getRules() {
		return rules;
	}

	/**
	 * @param rules the rules to set
	 */
	public void setRules(String rules) {
		this.rules = rules;
	}


	/**
	 * @return the sqlKey
	 */
	public String getSqlKey() {
		return sqlKey;
	}

	/**
	 * @param sqlKey the sqlKey to set
	 */
	public void setSqlKey(String sqlKey) {
		this.sqlKey = sqlKey;
	}

	


	
}
