/**
 * 
 */
package com.redsea.controller.system;

import java.util.Iterator;
import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.plugin.ehcache.CacheKit;
import com.redsea.ext.base.BaseController;
import com.redsea.interceptor.AdminInterceptor;
import com.redsea.utils.SiteMapUtil;
import com.redsea.utils.URLUtils;

/**
 * @author chenxiaofeng
 * @date 2016-4-25 下午8:03:59
 */
@Before(AdminInterceptor.class)
public class CacheManageController extends BaseController {

	public void index(){
		String[] arrays=CacheKit.getCacheManager().getCacheNames();
		StringBuilder sb=new StringBuilder();
		for(String str:arrays){
			sb.append("<tr class=\"text-c\">");
			sb.append("<td>");
			sb.append(str+"(<a style='decoration:none' target='_blank' href='"+getRequest().getContextPath()+"/system/cache/deleteCacheByCacheName/"+str+"'>删除</a>)&nbsp;");
			sb.append("</td>");
			sb.append("<td class=\"text-l\">");
			List list=CacheKit.getKeys(str);
			Iterator iterator=list.iterator();
			while(iterator.hasNext()){
				String key=iterator.next().toString();
				sb.append(""+key+"(<a style='decoration:none' target='_blank' href='"+getRequest().getContextPath()+"/system/cache/deleteCacheByKey/"+str+"-"+URLUtils.decode(key)+"'>删除</a>，<a style='decoration:none' target='_blank' href='"+getRequest().getContextPath()+"/system/cache/cacheDataDetail/"+str+"-"+URLUtils.decode(key)+"'>查看数据</a>)&nbsp;|&nbsp;");
			}
			sb.append("</td>");
			sb.append("</tr>");
		}
		setAttr("html", sb.toString());
	}
	public void deleteCacheByCacheName(){
		String cacheName=getPara();
		CacheKit.removeAll(cacheName);
		renderText("删除成功");
	}
	
	public void deleteAllCache(){
		CacheKit.getCacheManager().clearAll();
		renderText("删除成功");
	}
	public void deleteCacheByKey(){
		CacheKit.remove(getPara(0), URLUtils.decode(getPara(1)));
		renderText("删除成功");
	}
	public void cacheDataDetail(){
		renderJson(CacheKit.get(getPara(0), URLUtils.decode(getPara(1))));
	}
	public void cacheList(){
		String[] arrays=CacheKit.getCacheManager().getCacheNames();
		StringBuilder sb=new StringBuilder();
		for(String str:arrays){
			sb.append("<a style='decoration:none' target='_blank' href='"+getRequest().getContextPath()+"/system/cache/deleteCacheByCacheName/"+str+"'><b>"+str+"</b></a>&nbsp;<br><br>");
			List list=CacheKit.getKeys(str);
			Iterator iterator=list.iterator();
			while(iterator.hasNext()){
				String key=iterator.next().toString();
				sb.append(""+key+"(<a style='decoration:none' target='_blank' href='"+getRequest().getContextPath()+"/system/cache/deleteCacheByKey/"+str+"-"+URLUtils.decode(key)+"'>删除</a>，<a style='decoration:none' target='_blank' href='"+getRequest().getContextPath()+"/system/cache/cacheDataDetail/"+str+"-"+URLUtils.decode(key)+"'>查看数据</a>)&nbsp;");
			}
			sb.append("<br><hr>");
		}
		
		renderHtml(sb.toString());
	}
	
	public void batchDeleteCacheByCacheName(){
		String[] cacheNames=getParaValues("cacheNames");
		if(cacheNames!=null&&cacheNames.length>0){
			for(String cacheName:cacheNames){
				CacheKit.removeAll(cacheName);
			}
		}
		renderText("删除成功");
	}
	public void buildSiteMap(){
		SiteMapUtil.build();
		renderText("siteMap更新成功!");
	}
}
