package com.redsea.daelly.webmagic;

import java.util.Collection;

/**
 * @author liqingyang
 * @date 2016-6-8 下午5:08:03
 */
public interface PagedModel {
	
	public String getPageKey();
	
	public Collection<String> getOtherPages();
	
	public String getPage();
	
	public PagedModel combine(PagedModel pagedModel);
}
