/**
 * 
 */
package com.redsea.controller.web;

import com.jfinal.kit.StrKit;
import com.redsea.ext.base.BaseController;
import com.redsea.model.Article;
import com.redsea.model.RegionCode;
import com.redsea.model.RegionUrl;
import com.redsea.model.Tags;
import com.redsea.utils.CheckMobileUtils;

/**
 * @author chenxiaofeng
 * @date 2016-5-16 上午10:23:52
 */
public class ChaXunController extends BaseController {
	public void common(){
		setAttr("list_tuijian",Article.dao.getListByCache(1));
		setAttr("list_hot",Article.dao.getListByCache(2));
		setAttr("allTags", Tags.dao.getAllByCache());
		setAttr("list_new",Article.dao.getListByCache(3));
	}
	public void tools(){
		if(CheckMobileUtils.check(getRequest())){
			forwardAction("/m/tools");
			return;
		}else{
			common();
		}
		//setAttr("data", RegionUrl.dao.getAllParentData());
	}
	public void getCityData(){
		renderJson(RegionUrl.dao.getCityData(getPara()));
	}
	public void getCitDetailyData(){
		renderJson(RegionUrl.dao.getCityDetailData(getParaToInt("type"),getPara("regionCode")));
	}
	public void index(){
		String str=getPara();
		if(StrKit.notBlank(str)&&!"index".equals(str)){
			RegionCode regionCode=RegionCode.dao.findFirstByColumn("py_name", str);
				forwardAction("/article/"+regionCode.getArticleId());
				return;
		}else{
			if(CheckMobileUtils.check(getRequest())){
				forwardAction("/m/chaxun");
				return;
			}
			setAttr("list", RegionCode.dao.getRegionCodeFromCache());
			common();
		}
	}
}
