/**
 * 
 */
package com.redsea.model;

import java.util.List;
import com.jfinal.plugin.activerecord.Db;
import com.redsea.model.base.BaseKeyword;

/**
 * @author liqingyang
 * @date 2016-5-13 上午9:27:15
 */
public class Keyword extends BaseKeyword<Keyword> {
	public static final Keyword dao = new Keyword();
	
	public void batchSaveKeywords(List<Keyword> keywords){
		for (Keyword keyword : keywords) {
			saveOrUpdate(keyword);
		}
	}
}
