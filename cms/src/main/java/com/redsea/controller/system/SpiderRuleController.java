/**
 * 
 */
package com.redsea.controller.system;

import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.mozilla.universalchardet.UniversalDetector;

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.redsea.ext.base.BaseController;
import com.redsea.ext.base.IController;
import com.redsea.interceptor.AdminInterceptor;
import com.redsea.model.Article;
import com.redsea.model.SpiderRule;
import com.redsea.vo.AjaxResult;

/**
 * @author chenxiaofeng
 * @date 2016-5-10 下午2:22:43
 */
@Before(AdminInterceptor.class)
public class SpiderRuleController extends BaseController implements IController{

	public void list(){
		renderJson(SpiderRule.dao.findPageJsonByConditions(getParaMap()));
	}
	public void index(){
		
	}
	public void getSpiderRule() throws Exception{
		renderJson(SpiderRule.dao.findById(getPara()));
		
	}
	public void testUrl() throws Exception{
		String url=getPara("url");
		url=url.replaceAll("http://","");
		url=url.substring(0, url.indexOf("/"));
		SpiderRule spiderRule=SpiderRule.dao.findFirst("select * from tbl_spider_rule where website like '%"+url+"%'" );
		renderJson(spiderRule);
		
	}
	public void spider(){
		keepPara();
	}
	public void spiderUrlData(){
		String url = getPara("url");
		String[] urls=url.split("\r\n");
		int i=0;
		Article article2=getModel(Article.class,"article");
		for(String str:urls){
			try {
				List<Article> list = Article.dao.findByColumn("origin_url", str);
				if(list != null && list.size() > 0){
					Article.dao.deleteById(list.get(0).getId());
				}
				Connection connection = Jsoup.connect(str.trim());
				connection.data("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.124 Safari/537.36");
				connection.timeout(50000);
	    		Response response = connection.execute();
	    		String charset = response.charset();
	    		String guessCharset = guessEncoding(response.bodyAsBytes());
	    		if(!StrKit.isBlank(guessCharset) && !guessCharset.equals(charset))
	    			charset = guessCharset;
	    		Document document = null;
	    		if(!StrKit.isBlank(charset)){
	    			String body = new String(response.bodyAsBytes(), charset);
	    			document = Jsoup.parse(body);    			
	    		}else{
	    			document = response.parse();
	    		}
				SpiderRule spiderRule=SpiderRule.dao.findById(getPara("rule_id"));
				Element titleDoms = document.select(spiderRule.getTitleRule()).first();
				Element contentDoms = document.select(spiderRule.getContentRule()).first();    		
				String title = "";
				String content = "";
				Article article=new Article();
				article.setFolderId(article2.getFolderId());
				article.setOriginUrl(str.trim());
				article.setOrigin(spiderRule.getName());
				if(titleDoms!=null){
					title= titleDoms.text();
				}
				if(contentDoms!=null){
					content=contentDoms.html();
					article.setContent(content);
				}
				if(StrKit.notBlank(spiderRule.getSummaryRule())){
					Element summaryDoms = document.select(spiderRule.getSummaryRule()).first();
					if(summaryDoms!=null){
						article.setSummary(summaryDoms.text());
					}
				}
				if("".equals(title)||"".equals(content)){
					renderJson(new AjaxResult().addError("标题或者内容为空,请检查表达式!"));
					return;
				}
				article.setTitle(title);
				article.setContent(content);
				if(Article.dao.saveOrUpdate(article)){
					i++;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		renderJson(new AjaxResult().success(i+"条数据采集成功!"));
		
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
		setAttr("spiderRule", SpiderRule.dao.findById(getPara()));
		
	}
	@Override
	public void view() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void save() {
		renderJson(new AjaxResult(SpiderRule.dao.saveOrUpdate(getModel(SpiderRule.class,"spiderRule"))));
		
	}
	@Override
	public void delete() {
		renderJson(new AjaxResult(SpiderRule.dao.deleteById(getPara())));
		
	}
	
	/**
     * 根据字节数组，猜测可能的字符集，如果检测失败，返回utf-8
     * @param bytes 待检测的字节数组
     */
    public static String guessEncoding(byte[] bytes) {
        UniversalDetector detector = new UniversalDetector(null);
        detector.handleData(bytes, 0, bytes.length);
        detector.dataEnd();
        String guessEncoding = detector.getDetectedCharset();
        detector.reset();
        return guessEncoding;
    }
}
