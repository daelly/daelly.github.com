package com.redsea.ui.tag;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.ehcache.CacheKit;
import com.redsea.common.CacheConstants;
import com.redsea.common.WebUtils;
import com.redsea.model.ArticleTags;
import com.redsea.model.Folder;
import com.redsea.model.People;
import com.redsea.model.SysCode;
import com.redsea.utils.DateUtil;
import com.redsea.utils.HtmlFilter;
import com.redsea.utils.StringUtils;

/**
 * beetl 自定义函数
 * @author Rocky
 * email: 464193096@qq.com
 * site:http://www.hr-soft.cn/
 */
public class Functions {

	static	String basePath="";
	static{
		basePath=PropKit.get("basePath");
	}
	/**
	 * 继续encode URL (url,传参tomcat会自动解码)
	 * 要作为参数传递的话，需要再次encode
	 * @param url
	 * @return String
	 */
	public String encodeUrl(String url) {
		try {
			url = URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// ignore
		}
		return url;
	}
	public static String getFolderName(Integer id){
		return	Folder.dao.getFoldNameByFoldId(id);
	}
	/**
	 * 获取登录的用户
	 * @return
	 */
	public People currentUser(HttpServletRequest request, HttpServletResponse response) {
		return WebUtils.currentUser(request, response);
	}
	
	public static String format(Date date, String pattern) {
		return DateUtil.format(date, pattern);
	}
	public static String getDateDiff(Date date){
		if(date==null){
			return "";
		}
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");
        long between = 0;
        try {
            java.util.Date begin =date;
             java.util.Date end = new Date();
             between = (end.getTime() - begin.getTime());// 得到两者的毫秒数
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        long day = between / (24 * 60 * 60 * 1000);
        long hour = (between / (60 * 60 * 1000) - day * 24);
        long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
        //long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
       // long ms = (between - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
        if(day==0&&hour>1){
        	return hour+"小时前";
        }else if(day==0&&hour==0){
        	return min+"分钟前";
        }else if(day>0&&day<=6){
        	return day+"天前";
        }else{
        	return dfs.format(date);
        }
	}
	public static String getValueFromCache(String key){
		Map map=CacheKit.get(CacheConstants.CONSTANTS_CODE_CACHE,CacheConstants.CODE_TABLE_KEY);
		if(map==null){
			map=SysCode.dao.findMap("paramname", "paramvalue", " isdelete = 0");
			CacheKit.put(CacheConstants.CONSTANTS_CODE_CACHE,CacheConstants.CODE_TABLE_KEY,map);
		}
		Object str=map.get(key);
		return str==null?"":str.toString();
	}
	// 清除字符串中的html标签
	public static String filterText(String string) {
		return HtmlFilter.getText(string);
	}

	// 清除rss字符串中的html标签
	public static String filterRss(String string) {
		string = HtmlFilter.getText(string);
		return string.replaceAll("\\s*|\t|\r|\n","").replaceAll("&nbsp;", "");
	}

	// 清除字符串中的html标签并截取
	public static String filterSubText(String string, Integer length) {
		//string = string.replaceAll("&nbsp;", "");
		if(StrKit.isBlank(string))
			return "";
		return StringUtils.subCn(HtmlFilter.getText(string), length, "...");
	}
	
	// 清除字符串中的html标签并截取
	public static String filterText(String str, Integer length) {
		if(StrKit.isBlank(str))
			return "";
//		Document doc = Jsoup.parse(str);
//		Elements elements = doc.select("span[highlight]");
//		Set<String> keywords = new HashSet<String>();
//		for(Element element:elements){
//			keywords.add(element.text());
//		}
//		String text = doc.text();
//		text = StringUtils.subCn(text, length, "...");
//		for (String keyword : keywords) {
//			text = text.replaceAll(keyword, "<span style='color:#c00;'>"+keyword+"</span>");
//		}
		
		return StringUtils.subCn(str, length, "...");
	}
	
	
	public static String getContent(String summary,String content, Integer length){
		if(StrKit.notBlank(summary)){
			return filterSubText(summary,length);
		}
		if(StrKit.notBlank(content)){
			return filterSubText(content,length);
		}
		return "";
	}
	// 页面description，seo
	public static String description(String string) {
		string = string.replaceAll("\\s*|\t|\r|\n","").replaceAll("&nbsp;", "");
		return StringUtils.subCn(HtmlFilter.getText(string), 90, "...");
	}
	
	// 页面keyWords，seo，分词
	public static String keyWords(Integer articleId) {
		List<ArticleTags> list = ArticleTags.dao.findByCache(articleId);
		Set<String> tags = new HashSet<String>();
		for (ArticleTags blogTag : list) {
			tags.add(blogTag.getStr("tag_name"));
		}
		return StringUtils.join(tags, ',');
	}
	
	// 标记关键字
	public static String markKeywords(String string, Integer length, String keywords) {
		if (StrKit.notBlank(keywords)) {
			return HtmlFilter.markKeywods(keywords, filterSubText(string, length));
		} else {
			return filterSubText(string, length);
		}
	}
	
	// 标签帮助，根据博文查找该博文的标签集合
	public static String bTags(Integer articleId) {
		StringBuilder html = new StringBuilder();
		List<ArticleTags> list =ArticleTags.dao.findByCache(articleId);
		if(null != list && list.size() > 0) {
			html.append("<span class=\"mt_icon\"></span>");
		}
		String temp = "<a title=\"{}\" href=\"/tags/{}\">{}</a>";
		for (int i = 0; i < list.size(); i++) {
			ArticleTags articleTags = list.get(i);
			html.append(temp.replace("{}", articleTags.getStr("tag_name")));
			if (i != list.size() - 1 ) {
				html.append(',');
			}
		}
		return html.toString();
	}

	/**
	 * 从文章中获取一张图片
	 * @param content
	 * @return url
	 */
	public static String getArticleImg(String content) {
		String url=HtmlFilter.getImgSrc(content);
		return getUrl(url);
	}
	public static String getUrl(String url){
		if(StrKit.isBlank(url)){
			return "";
		}else if(url.startsWith("http://")){
			return url;
		}
		return basePath+url;
	}
	/**
	 * 从文章中获取一张图片
	 * @param content
	 * @return url
	 */
	public static String getArticleImg2(String img,String content) {
		if(StrKit.notBlank(img)){
			return getUrl(img);
		}
		String url=HtmlFilter.getImgSrc(content);
		return getUrl(url);
	}
}
