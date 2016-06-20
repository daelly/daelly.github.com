package com.redsea.common;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;

import org.apache.http.client.ClientProtocolException;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.mozilla.universalchardet.UniversalDetector;

/**
 * @author liqingyang
 * @date 2016-5-13 上午8:44:05
 */
public class CommonTest {
	
//	@Test
	public void test1() throws ClientProtocolException, IOException{
//		String url = "http://index.so.com/index.php?a=overviewJson&area=%E5%85%A8%E5%9B%BD&q=%E7%A4%BE%E4%BF%9D";
//		String res = HttpUtils.get(url);
//		System.out.println(res);
		String url = "http://www.spicezee.com/xinwen/81476.html";
		String selector = "h1.pagetit";
		Connection connection = Jsoup.connect(url);
		connection.data("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.124 Safari/537.36");
		connection.data("Host","news.baidu.com");
		connection.data("Accept","news.baidu.com");
		connection.cookie("session","2ce6f1773759890787ee573457a86319a3c532fd");
		connection.timeout(50000);
		Response response = connection.execute();
		String charset = response.charset();
		System.out.println(charset);
		String guessCharset = guessEncoding(response.bodyAsBytes());
		System.out.println(guessCharset);
		String body;
		body = new String(response.bodyAsBytes(), guessCharset);
        System.out.println(body);
        Document doc = Jsoup.parse(body);
        String text = doc.select("h1.pagetit").text();
        System.out.println(text);
	}
	
	/**
     * 根据字节数组，猜测可能的字符集，如果检测失败，返回utf-8
     * @param bytes 待检测的字节数组
     */
    public static String guessEncoding(byte[] bytes) {
        String default_encoding = "UTF-8";
        UniversalDetector detector = new UniversalDetector(null);
        detector.handleData(bytes, 0, bytes.length);
        detector.dataEnd();
        String guessEncoding = detector.getDetectedCharset();
        detector.reset();
        if (guessEncoding != null) {
            return guessEncoding;
        }
        return default_encoding;
    }
    
//    @Test
    public void test2(){
    	String regex = "(http|ftp|https)://.*";
    	String url = "113950";
    	System.out.println(url.matches(regex));
    }
    
//    @Test
    public void test3(){
    	System.setProperty("http.proxyHost", "");
    	System.setProperty("http.proxyPort", "");
    	System.setProperty("https.proxyHost", "");
    	System.setProperty("https.proxyPort", "");
    	HttpURLConnection conn = null;
    }
    
    @Test
    public void test4(){
    	String pt = "http://www\\.cnys\\.com/zixun/61602_?(\\d+)?\\.html";
    	System.out.println("http://www.cnys.com/zixun/61602.html".matches(pt));
    }
}
