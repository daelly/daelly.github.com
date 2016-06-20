package com.redsea.ext.plugin.solr.utils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.FieldAnalysisRequest;
import org.apache.solr.client.solrj.response.FieldAnalysisResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.AnalysisResponseBase.AnalysisPhase;
import org.apache.solr.client.solrj.response.AnalysisResponseBase.TokenInfo;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.CommonParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.redsea.ui.tag.Functions;

/**
 * @author liqingyang
 * @date 2016-6-1 上午8:56:26
 */
public class SolrUtils {
	
	private static Logger logger = LoggerFactory.getLogger(SolrUtils.class);
	
	private static final String server = "http://localhost:8080/solr/sb_core";

	public static void fullImport(){
		final String param = "/select?qt=/dataimport&command=full-import&clean=true&commit=true";
		String url = null;
		if(!StrKit.isBlank(server))
			url = server + param;
		else
			url = "http://192.168.101.186:8400/solr/sb_core" + param;
		try {
			URL turl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) turl.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("type", "submit");
			conn.setDoOutput(true);
			conn.connect();
			logger.info("index update process:FULL URL\t\t"+conn.getURL());
			logger.info("index update process:Response message\t\t"+conn.getResponseMessage());
			logger.info("index update process:Response code\t\t"+conn.getResponseCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void deltaImport(){
		final String param = "/select?qt=/dataimport&command=delta-import&clean=false&commit=true";
		String url = null;
		if(!StrKit.isBlank(server))
			url = server + param;
		else
			url = "http://192.168.101.186:8400/solr/sb_core" + param;
		try {
			URL turl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) turl.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("type", "submit");
			conn.setDoOutput(true);
			conn.connect();
			logger.info("index update process:FULL URL\t\t"+conn.getURL());
			logger.info("index update process:Response message\t\t"+conn.getResponseMessage());
			logger.info("index update process:Response code\t\t"+conn.getResponseCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询一页数据
	 * @param keyword
	 * @param pageNo
	 * @return
	 */
	public static Page findPageBySolr(String keyword,String pageNo){
		 if(pageNo==null){
			 pageNo="1";
		 }
		 int pageno = Integer.parseInt(pageNo);
		 Integer pageSize=15;//默认分页记录数
		 Page page = new Page(null, pageno, pageSize, 0, 0);
		 try {
			 HttpSolrServer httpSolrServer = null;
			 //先对关键字分词
			 if(!StrKit.isBlank(server))
				 httpSolrServer = new HttpSolrServer(server);
			 else
				 httpSolrServer = new HttpSolrServer("http://192.168.101.186:8400/solr/sb_core");
			 Set<String> results = null;
			 try {
				 FieldAnalysisRequest analysisRequest = new FieldAnalysisRequest("/analysis/field");
				 analysisRequest.addFieldName("title");
				 analysisRequest.setFieldValue("");
				 analysisRequest.setQuery(keyword);
				 FieldAnalysisResponse analysisResponse = analysisRequest.process(httpSolrServer);
				 Iterator<AnalysisPhase> it = analysisResponse.getFieldNameAnalysis("title").getQueryPhases().iterator();
				 results = new HashSet<String>();
				 results.add(keyword);
				 while(it.hasNext()){
					 AnalysisPhase phase = it.next();
					 List<TokenInfo> list = phase.getTokens();
					 for (TokenInfo tokenInfo : list) {
						 results.add(tokenInfo.getText());
					}
				 }
			 }catch (Exception e) {
				e.printStackTrace();
			 }
			 SolrQuery solrQuery = new SolrQuery();
			 //权重处理
			 solrQuery.set(CommonParams.Q, StringUtils.join(results, ' '));
			 solrQuery.set("qf", "title^2 content^0.5");
			 solrQuery.set("pf", "title^10 content^2.5");//短语匹配
			 solrQuery.set("defType", "edismax");//
			 //添加发布时间和自定义权重字段值的权重
			 solrQuery.set("bf", "sum(recip(rord(publish_time),1,1000,1000),product(weight,10))");//排序
			 solrQuery.set("bq","address:广州^5");
			 //分页处理
			 solrQuery.setStart((pageno-1)*pageSize);
			 solrQuery.setRows(pageSize);
			 //高亮处理
			 solrQuery.setHighlight(true);
			 solrQuery.setHighlightFragsize(150);
			 solrQuery.setHighlightRequireFieldMatch(true);
			 solrQuery.setHighlightSimplePre("<span highlight style='color:#c00;'>");
			 solrQuery.setHighlightSimplePost("</span>");
			 solrQuery.setParam("hl.fl", "title","content");
			 QueryResponse response = httpSolrServer.query(solrQuery);
			 Map<String, Map<String, List<String>>> highlight = response.getHighlighting();
			 
			 SolrDocumentList sd = response.getResults();
			 List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();
			 for (SolrDocument solrDocument : sd) {
				String id = solrDocument.get("id").toString();
				String title = null,content = null;
				if(highlight.get(id)!=null && highlight.get(id).get("title")!=null){
					title = highlight.get(id).get("title").get(0);
				} 
				if(highlight.get(id)!=null && highlight.get(id).get("content")!=null){
					content = highlight.get(id).get("content").get(0);
				} 
				Set<Entry<String, Object>> entrySet = solrDocument.entrySet();
				Map<String,Object> m = new HashMap<String, Object>();
				for (Entry<String, Object> entry : entrySet) {
					m.put(entry.getKey(), entry.getValue());
				}
				if(!StrKit.isBlank(title))
					m.put("title", title);
				if(!StrKit.isBlank(content))
					m.put("content", content);
				datas.add(m);
			 }
			 int totalCount = (int) sd.getNumFound();
			 int totalPage = totalCount / pageSize;
			 page = new Page(datas, pageno, pageSize, totalPage, totalCount);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return page;
	 }
}
