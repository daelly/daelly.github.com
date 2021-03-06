package com.redsea.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Page;
import com.redsea.common.CacheConstants;
import com.redsea.model.base.BaseTopic;


/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Topic extends BaseTopic<Topic> {
	public static final Topic dao = new Topic();
	
	 public Page getTopicPage(String pageNo){
		 if(pageNo==null){
			 pageNo="1";
		 }else if("index".equals(pageNo)){
			 pageNo="1";
		 }
		  Integer pageSize=15;//默认分页记录数
		  StringBuffer sql =new StringBuffer(" from tbl_topic   where 1=1 ");
		  sql.append(" and status=1 order by publish_time desc");
		  return  paginate(Integer.parseInt(pageNo), pageSize, "select *", sql.toString()); 
	 }
	 public List<Topic> getRecentTopic(){
		 String sql="SELECT id,title,titleImg from tbl_topic where `status`=1 ORDER BY publish_time DESC limit 5";
		 return findByCache(CacheConstants.CONSTANTS_TOPIC_CACHE, "Topic_Recent_List", sql);
	 }
	
}
