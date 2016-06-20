package com.redsea.ui.jsp;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

/**
 * jsp <head>标签
 * @author yeshujun 
 */
public class HeadTag extends BodyTagSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//private boolean ddaccordion=false;//
	//private boolean crud=true;//是否包含crud操作
	private boolean easyui=false;//是否加载easyui js
	private boolean icheck=false;//是否加载icheck js
	private boolean datePicker=false;//是否加载日期时间 js控件
	private boolean multiFile=false;//是否加载附件上传 js控件
	private boolean fck=false;//是否加载fck 编辑器 js控件
	private boolean tree2=false;//是否加载zTree 2.0版本的js、css
	private boolean sysTree=false;//是否加载瑞聘系统自己定义的ztree.js
	private boolean sysRadioTree=false;//是否加载瑞聘系统自己定义的ztree radio.js
	private boolean ajaxfileupload=false;//是否加载ajaxFileupload.js
	private boolean ajaxbigpage=false;//是否加载异步分页ajaxbigpage.js
	private boolean layer=false;
	private boolean validForm=false;//是否引入validForm
	
	private boolean tree3=false;//是否加载zTree 3.4版本的js、css
	private boolean textareaAutoresize=false;//textarea是否自动大小
	//private String locale="zh_CN";
	
	private StringBuilder result;
	
	public int doStartTag() throws JspException{
		
		result = new StringBuilder();
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		String ctx = request.getContextPath();
		if(easyui){
			result.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""+ctx+"/js_css_image/css/easyui/themes/default/easyui.css\"/>\n");
		}
		if(tree2){
			result.append("<link rel=\"StyleSheet\" type=\"text/css\" href=\""+ctx+"/js_css_image/css/zTree/2.0/zTreeStyle.css\"/>\n");
		}
		if(tree3){
			result.append("<link rel=\"StyleSheet\" type=\"text/css\" href=\""+ctx+"/static/lib/zTree/v3/css/zTreeStyle/zTreeStyle.css\"/>\n");
		}
		if(sysTree){
			result.append("<link rel=\"StyleSheet\" type=\"text/css\" href=\""+ctx+"/static/lib/zTree/3.4/zTreeStyle.css\"/>\n");
		}
		if(easyui){
			result.append("<!--布局 js-->\n");
			result.append("<script type=\"text/javascript\" src=\""+ctx+"/js_css_image/js/jquery/jquery.easyui.min_1.3.1.js\"></script>\n");
			//result.append("<script type=\"text/javascript\" src=\""+ctx+"/js_css_image/js/menu.js\"></script>\n");
		}
		if(icheck){
			result.append("<!--icheck js-->\n");
			result.append("<script type=\"text/javascript\" src=\""+ctx+"/static/lib/icheck/jquery.icheck.min.js\"></script>\n");
			result.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""+ctx+"/static/lib/icheck/icheck.css\"/>\n");
			result.append("<script type=\"text/javascript\">");
			result.append("$(function(){");
			result.append("$('.skin-minimal input').iCheck({checkboxClass: 'icheckbox-blue',radioClass: 'iradio-blue',increaseArea: '20%'});");
			result.append("});");
			result.append("</script>\n");
		}
		if(datePicker){
			result.append("<!--date、time js-->\n");
			result.append("<script type=\"text/javascript\" src=\""+ctx+"/static/lib/My97DatePicker/WdatePicker.js\" defer=\"defer\"></script>\n");
		}
		if(multiFile){
			result.append("<!--附件上传 js-->\n");
			result.append("<script type=\"text/javascript\" src=\""+ctx+"/js_css_image/js/jquery/jquery.MultiFile.js\" ></></script>\n");
		}
		if(fck){
			result.append("<!--富编辑器 fck js-->\n");
			result.append("<script type=\"text/javascript\"src=\""+ctx+"/js_css_image/js/fckeditor/fckeditor.js\" ></script>\n");
		}
		if(tree2){
			result.append("<!--ztree v2.0 js-->\n");
			result.append("<script type=\"text/javascript\" src=\""+ctx+"/static/lib/zTree/v3/js/jquery.ztree.all-3.5.min.js\"></script>\n");
		}
		if(tree3){
			result.append("<!--ztree v3.0 js-->\n");
			result.append("<script type=\"text/javascript\" src=\""+ctx+"/static/lib/zTree/v3/js/jquery.ztree.all-3.5.min.js\"></script>\n");
		}
		if(sysTree){
			result.append("<script type=\"text/javascript\" src=\""+ctx+"/static/lib/zTree/3.4/jquery.ztree.all-3.4.min.js\"></script>\n");
			result.append("<script type=\"text/javascript\" src=\""+ctx+"/static/js/zTreeSys.js\"></script>\n");
		}
		if (sysRadioTree){
			result.append("<script type=\"text/javascript\" src=\""+ctx+"/js_css_image/js/zTree/3.4/jquery.ztree.all-3.4.min.js\"></script>\n");
			result.append("<script type=\"text/javascript\" src=\""+ctx+"/js_css_image/js/base/zTreeSysradio.js\"></script>\n");
		}
		if (ajaxfileupload) {
			result.append("<script type=\"text/javascript\" src=\""+ctx+"/js_css_image/js/base/ajaxfileupload.js\"></script>\n");
		}
		if(textareaAutoresize){
			result.append("<!--textarea 自动大小  js-->\n");
			result.append("<script type=\"text/javascript\" src=\""+ctx+"/js_css_image/js/jquery/autoresize.jquery.min.js\"></script>\n");
		}
		if(ajaxbigpage){
			result.append("<link rel=\"StyleSheet\" type=\"text/css\" href=\""+ctx+"/js_css_image/js/ajaxbigpage/bigpage.css\"/>\n");
			result.append("<script type=\"text/javascript\" src=\""+ctx+"/js_css_image/js/ajaxbigpage/asyn_page.js\"></script>\n");
		}
		if(layer){
			result.append("<script type=\"text/javascript\" src=\""+ctx+"/static/lib/layer/2.1/layer.js\"></script>\n");
		}
		if(validForm){
			result.append("<script type=\"text/javascript\" src=\""+ctx+"/static/js/validform/Validform_v5.3.2_min.js\"></script>\n");
		}
		
		if (result != null){
			JspWriter out = pageContext.getOut();
			try {
				out.print(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return Tag.EVAL_BODY_INCLUDE;
	}

	public int doEndTag() throws JspException{
		//result.append("</head>\n");
		if (result != null){
			JspWriter out = pageContext.getOut();
			try {
				out.print("</head>\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return Tag.EVAL_PAGE;
	}

	public boolean isEasyui() {
		return easyui;
	}

	public void setEasyui(boolean easyui) {
		this.easyui = easyui;
	}

	public boolean isIcheck() {
		return icheck;
	}

	public void setIcheck(boolean icheck) {
		this.icheck = icheck;
	}

	public boolean isMultiFile() {
		return multiFile;
	}

	public void setMultiFile(boolean multiFile) {
		this.multiFile = multiFile;
	}

	public boolean isDatePicker() {
		return datePicker;
	}

	public void setDatePicker(boolean datePicker) {
		this.datePicker = datePicker;
	}

	public boolean isFck() {
		return fck;
	}

	public void setFck(boolean fck) {
		this.fck = fck;
	}

	public boolean isTree3() {
		return tree3;
	}

	public void setTree3(boolean tree3) {
		this.tree3 = tree3;
	}

	public boolean isTextareaAutoresize() {
		return textareaAutoresize;
	}

	public void setTextareaAutoresize(boolean textareaAutoresize) {
		this.textareaAutoresize = textareaAutoresize;
	}

	public boolean isTree2() {
		return tree2;
	}

	public void setTree2(boolean tree2) {
		this.tree2 = tree2;
	}
	/*public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}*/

	public boolean isAjaxfileupload() {
		return ajaxfileupload;
	}

	public void setAjaxfileupload(boolean ajaxfileupload) {
		this.ajaxfileupload = ajaxfileupload;
	}

	public boolean isSysTree() {
		return sysTree;
	}

	public void setSysTree(boolean sysTree) {
		this.sysTree = sysTree;
	}

	public boolean isAjaxbigpage() {
		return ajaxbigpage;
	}

	public void setAjaxbigpage(boolean ajaxbigpage) {
		this.ajaxbigpage = ajaxbigpage;
	}

	public boolean isSysRadioTree() {
		return sysRadioTree;
	}

	public void setSysRadioTree(boolean sysRadioTree) {
		this.sysRadioTree = sysRadioTree;
	}

	/**
	 * @return the layer
	 */
	public boolean isLayer() {
		return layer;
	}

	/**
	 * @param layer the layer to set
	 */
	public void setLayer(boolean layer) {
		this.layer = layer;
	}

	public boolean isValidForm() {
		return validForm;
	}

	public void setValidForm(boolean validForm) {
		this.validForm = validForm;
	}
	
}
