package com.redsea.ext.base;

import com.jfinal.core.Controller;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Page;
import com.redsea.vo.DataTables;


public class BaseController extends Controller {
	
	protected final Log logger = Log.getLog(this.getClass());
	
	// index
	public void index(){}
	
	/**
	 * DataTable渲染
	 * @param page
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void renderDataTable(Page page) {
		int draw = getParaToInt("draw", 0);
		renderJson(new DataTables(draw, page));
	}
	  protected boolean isPost(){     
	        return "post".equals(getRequest().getMethod().toLowerCase());
	    }
	  protected void alertAndGoback(String msg,int step) {
	        renderJS("alert('"+msg+"');history.go(" + step + ")");
	    }
	  protected void renderJS(String jsContent){
	        renderHtml("<script type=\"text/javascript\">"+jsContent+"</script>");
	   }
	  @Override
	  public void forwardAction(String actionUrl){
		  if(actionUrl.startsWith("http")){
			  redirect(actionUrl);
		  }else{
			  super.forwardAction(actionUrl);
		  }
	  }
	
}
