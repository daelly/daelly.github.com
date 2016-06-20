/**
 * 
 */
package com.redsea.controller.system;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.weixin.sdk.utils.HttpUtils;
import com.redsea.ext.base.BaseController;
import com.redsea.ext.base.IController;
import com.redsea.ext.kit.RandomKit;
import com.redsea.interceptor.AdminInterceptor;
import com.redsea.model.Keyword;
import com.redsea.vo.AjaxResult;

/**
 * @author liqingyang
 * @date 2016-5-13 下午09:13:06
 */
@Before(AdminInterceptor.class)
public class KeywordController extends BaseController implements IController{

	@Override
	public void list() {
		renderJson(Keyword.dao.findPageJsonByConditions(getParaMap()));
	}

	@Override
	public void add() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void edit() {
	}

	@Override
	public void view() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save() {
	}

	@Override
	public void delete() {
	}
	
	/**
	 * 单个更新搜索指数
	 * @throws Exception
	 */
	public void updataIndex() throws Exception{
		int id = getParaToInt("id");
		Keyword keyword = Keyword.dao.findById(id);
		if(keyword==null){
			renderJson(new AjaxResult().addError("无法获取对应关键字"));
			return;
		}
		int index = getIndex(keyword);
		keyword.set("search_index", index);
		keyword.set("update_time", new Date());
		Keyword.dao.saveOrUpdate(keyword);
		renderJson(new AjaxResult().success(keyword));
	}
	
	/**
	 * 更新所有现有关键字的的搜索指数
	 * @throws Exception
	 */
	public void updateAllIndex() throws Exception{
		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter out = getResponse().getWriter();
		renderNull();
		final String url = "http://index.so.com/index.php?a=overviewJson&area=%E5%85%A8%E5%9B%BD&q=";
		try {
			//采集所有一级的关键字
			List<Keyword> keywords = Keyword.dao.findAll();
			List<Keyword> keys = new ArrayList<Keyword>();
			int successCount = 0;
			out.print("<font style='color:red;'>开始采集</font><br>");
			out.flush();
			for(int i=0;i<keywords.size();i++){
				Keyword keyword = keywords.get(i);
				if(keyword.getDate("update_time")!=null)
					continue;
				int radom = RandomKit.random(1, 10);
				Thread.sleep(1000*radom);
				String key = keyword.getStr("keyword");
				out.print("序号"+i+"："+key);
				key = URLEncoder.encode(key, "UTF-8");
				String reqUrl = url + key;
				out.print("-url:"+reqUrl);
				String resp = HttpUtils.get(reqUrl);
				if(StrKit.isBlank(resp)){
					out.print("采集异常，未得到响应数据</br>");
					out.flush();
					continue;
				}
				JSONObject json = JSONObject.parseObject(resp);
				Integer status = json.getInteger("status");
				if(status != 0){
					out.print("采集异常，响应状态不为0</br>");
					out.flush();
					continue;
				}
				JSONArray data = json.getJSONArray("data");
				JSONObject item = data.getJSONObject(0);
				JSONObject indexes = item.getJSONObject("data");
				String weekIndexStr = indexes.getString("week_index");
				int search_index = 0;
				try {
					search_index = Integer.parseInt(weekIndexStr);
				} catch (Exception e) {
					search_index = 0;
				}
				keyword.set("search_index", search_index);
				keyword.set("update_time", new Date());
				out.print("=>结果："+search_index+"<br>");
				out.flush();
				successCount++;
				keys.add(keyword);
				if(i%10==9){
					out.print("将以上"+keys.size()+"条数据保存数据库……<br>");
					out.print("<font style='color:red;'>已累计采集"+successCount+"条数据</font><br>");
					out.flush();
					Keyword.dao.batchSaveKeywords(keys);
					keys.clear();
				}
			}
			if(keys.size()>0)
				Keyword.dao.batchSaveKeywords(keys);
		} catch(JSONException e){
			out.print("采集出现异常，很有可能IP已被封锁！");
			e.printStackTrace();
		} catch (Exception e) {
			out.print("采集出现异常，"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void getSubKeyword() throws Exception{
		getResponse().setContentType("text/html;charset=UTF-8");
		final PrintWriter out = getResponse().getWriter();
		renderNull();
		try {
			//采集所有一级的关键字
			out.print("<font style='color:red;'>开始采集</font><br>");
			out.flush();
			List<Keyword> keywords = Keyword.dao.findByColumn("pid", 0);
			for(int i=0;i<keywords.size();i++){
				Keyword keyword = keywords.get(i);
				try {
					spiderSubKeyword(keyword,out,2);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
//			Thread oldThread = getSessionAttr("getSubKeyword");
//			if(oldThread != null)
//				oldThread.interrupt();
//			Thread thread = new Thread(new Runnable() {
//				@Override
//				public void run() {
//				}
//			});
//			thread.start();
//			thread.wait();
//			setSessionAttr("getSubKeyword", thread);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void stopUpdate(){
		String name = getPara();
		Thread thread = getSessionAttr(name);
		if(thread != null)
			thread.interrupt();
		renderJson(new AjaxResult());
	}
	
	public int getIndex(Keyword keyword) throws Exception{
		final String url = "http://index.so.com/index.php?a=overviewJson&area=%E5%85%A8%E5%9B%BD&q=";
		String key = keyword.getStr("keyword");
		key = URLEncoder.encode(key, "UTF-8");
		String reqUrl = url + key;
		try {
			String resp = HttpUtils.get(reqUrl);
			JSONObject json = JSONObject.parseObject(resp);
			Integer status = json.getInteger("status");
			if(status != 0)
				return 0;
			JSONArray data = json.getJSONArray("data");
			JSONObject item = data.getJSONObject(0);
			JSONObject indexes = item.getJSONObject("data");
			String weekIndexStr = indexes.getString("week_index");
			int search_index = 0;
			try {
				search_index = Integer.parseInt(weekIndexStr);
			} catch (Exception e) {
				System.out.println("current week_index is not a number :" + weekIndexStr);
				search_index = 0;
			}
			return search_index;
		} catch(JSONException e){
			e.printStackTrace();
			return -1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
		
	/**
	 * 查找此关键字的相关和推荐关键字
	 * @param keyword
	 * @param out
	 * @param level 当前采集到第几层，使用这个参数可以在递归采集中终止采集
	 * @throws Exception
	 */
	private void spiderSubKeyword(Keyword keyword,final PrintWriter out,int level) throws Exception{		
		if(level >= 4)
			return;
		int radom = RandomKit.random(1, 5);
		Thread.sleep(1000*radom);//随机等待1-5秒钟
		out.print("开始采集【"+keyword.getStr("keyword")+"】的下级关键字：<br>");
		out.flush();
		//获取推荐、相关关键字
		final String url = "http://www.so.com/s?ie=utf-8&shb=1&src=360sou_newhome&q=";
		//推荐关键字选择器
		final String suggestKeywordSelector = "#so-pdr-guide .so-pdr-box a[href]";
		//相关关键字选择器
		final String refKeywordSelector = ".mod-relation #rs a[href]";
		String parent_key = keyword.getStr("keyword");
		parent_key = URLEncoder.encode(parent_key, "UTF-8");
		String reqUrl = url + parent_key;
		Connection conn = Jsoup.connect(reqUrl);
		Document document = conn.get();
		//获取推荐关键字
		Elements elements = document.select(suggestKeywordSelector);
		List<Keyword> suggestKeys = new ArrayList<Keyword>();
		StringBuffer sb = new StringBuffer();
		if(elements != null && elements.size() > 0){
			for(int i=0;i<elements.size();i++){
				Element element = elements.get(i);
				String word = element.text().trim();
				List<Keyword> existKeys = Keyword.dao.findByColumn("keyword", word);
				if(existKeys!=null && existKeys.size() > 0)//当前关键字已存在
					continue;
				Keyword key = new Keyword();
				key.set("keyword", word);
				key.set("pid", keyword.getInt("id"));
				key.set("type", 2);
				suggestKeys.add(key);
				if(sb.length() > 0)
					sb.append(",");
				sb.append(word);
			}
			if(suggestKeys.size() > 0){
				Db.batchSave(suggestKeys, suggestKeys.size());
				out.print("<font style='color:red;'>采集到【<b>"+keyword.getStr("keyword")+"</b>】的推荐关键字："+sb.toString()+"</font><br>");
				out.flush();
				for (Keyword keyword2 : suggestKeys) {
					spiderSubKeyword(keyword2,out,level+1);
				}
			}
		}
		//获取相关关键字
		elements = document.select(refKeywordSelector);
		List<Keyword> refKeys = new ArrayList<Keyword>();
		sb = new StringBuffer();
		if(elements != null && elements.size() > 0){
			for(int i=0;i<elements.size();i++){
				Element element = elements.get(i);
				String word = element.text().trim();
				List<Keyword> existKeys = Keyword.dao.findByColumn("keyword", word);
				if(existKeys!=null && existKeys.size() > 0)//当前关键字已存在
					continue;
				Keyword key = new Keyword();
				key.set("keyword", word);
				key.set("pid", keyword.getInt("id"));
				key.set("type", 1);
				refKeys.add(key);
				if(sb.length() > 0)
					sb.append(",");
				sb.append(word);
			}
			if(refKeys.size() > 0){
				Db.batchSave(refKeys, refKeys.size());
				out.print("采集到【<b>"+keyword.getStr("keyword")+"</b>】的相关关键字："+sb.toString()+"<br>");
				out.flush();
				for (Keyword keyword2 : refKeys) {
					spiderSubKeyword(keyword2,out,level+1);
				}
			}
		}
	}
}
