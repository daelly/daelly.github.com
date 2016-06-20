package com.redsea.controller.system;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.StrKit;
import com.redsea.ext.base.BaseController;
import com.redsea.ext.base.IController;
import com.redsea.interceptor.AdminInterceptor;
import com.redsea.model.Article;
import com.redsea.model.Comment;
import com.redsea.ui.tag.Functions;
import com.redsea.utils.DateUtil;

/**
 * @author liqingyang
 * @date 2016-4-18 下午7:47:29
 */
@Before(AdminInterceptor.class)
public class CommentController extends BaseController implements IController {

	/* (non-Javadoc)
	 * @see com.redsea.ext.base.IController#list()
	 */
	@Override
	public void list() {
	}

	/* (non-Javadoc)
	 * @see com.redsea.ext.base.IController#add()
	 */
	@Override
	public void add() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redsea.ext.base.IController#update()
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redsea.ext.base.IController#edit()
	 */
	@Override
	public void edit() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redsea.ext.base.IController#view()
	 */
	@Override
	public void view() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redsea.ext.base.IController#save()
	 */
	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.redsea.ext.base.IController#delete()
	 */
	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}
	//http://www.sbyun/system/comment/comment
	@Clear
	public void comment(){
		Long maxLogId = Comment.dao.getMaxLogId();
		Map<String, String> queryParas = new HashMap<String, String>();
		queryParas.put("short_name",Functions.getValueFromCache("duoshuo.short_name"));//PropKit.get("duoshuo.short_name")
		queryParas.put("secret",Functions.getValueFromCache("duoshuo.secret"));//PropKit.get("duoshuo.secret")
		queryParas.put("since_id", maxLogId.toString());
		String resp = HttpKit.get("http://api.duoshuo.com/log/list.json", queryParas);
		JSONObject res = JSONObject.parseObject(resp);
		if(res.getIntValue("code")==0){
			JSONArray comments = res.getJSONArray("response");
			for (Object object : comments) {
				JSONObject comment = (JSONObject)object;
				Long logId = comment.getLong("log_id");
				String action = comment.getString("action");
				String userId = comment.getString("user_id");
				if("create".equals(action)){
					JSONObject meta = comment.getJSONObject("meta");
					Comment comt = new Comment();
					String threadKeyStr = meta.getString("thread_key");
					Integer article_id = null;
					try {
						article_id = Integer.parseInt(threadKeyStr);
					} catch (Exception e) {
						e.printStackTrace();
						logger.info("当前获取到的thread key不是整形");
						continue;
					}
					Article article = Article.dao.findById(article_id);
					if(article == null){
						logger.info("当前获取到的thread key无法找到对应的文章");
						continue;
					}
					comt.setPostId(meta.getLong("post_id"));
					comt.setParentId(meta.getLong("parent_id"));
					comt.setAuthorId(meta.getString("author_id"));
					comt.setAuthorKey(meta.getString("author_key"));
					comt.setAuthorName(meta.getString("author_name"));
					comt.setAuthorEmail(meta.getString("author_email"));
					comt.setAuthorUrl(meta.getString("author_url"));
					comt.setIp(meta.getString("ip"));
					comt.setAgent(meta.getString("agent"));
					String createdAtStr = meta.getString("created_at");
					if(StrKit.notBlank(createdAtStr)){
						createdAtStr = createdAtStr.replace("T", " ").replace("\\+.*", "");
						Date createAt = DateUtil.parse(createdAtStr, "yyyy-MM-dd HH:mm:ss");
						comt.setCreateAt(createAt);						
					}
					comt.setMessage(meta.getString("message"));
					String status = meta.getString("status");
					comt.setStatus(status);
					comt.setType(meta.getString("type"));
					comt.setThreadKey(article_id);
					comt.setUserId(userId);
					comt.setLogId(logId);
					comt.save();
					//更新文章评论数量
					if("approved".equals(status))
						Article.dao.updateCommentCount(article_id, 1);
				}else if("delete-forever".equals(action)){
					JSONArray postIdArr = comment.getJSONArray("meta");
					Long[] postIds = postIdArr.toArray(new Long[0]);
					Comment.batchDeleteComment(postIds,logId);
				}else{//执行更新
					JSONArray postIdArr = comment.getJSONArray("meta");
					Long[] postIds =getPostIds(postIdArr);
					String status = null;
					if("approve".equals(action)){
						status = "approved";
					} else if("spam".equals(action)){
						status = "spam";
					} else if("delete".equals(action)){
						status = "deleted";
					}
					Comment.batchUpdateComment(postIds, status,logId);
				}
			}
		}
		renderJson(res);
	}
	
	private Long[] getPostIds(JSONArray jsonArray){
		Object[] objects=jsonArray.toArray();
		Long[] a=new Long[objects.length];
		for(int i=0;i<objects.length;i++){
			a[i]=Long.parseLong(objects[i].toString());
		}
		return a;
//		Long[] postIds = new Long[jsonArray.size()];
//		for(int i=0;i<jsonArray.size();i++){
//			postIds[i] = Long.parseLong(jsonArray.getString(i));
//		}
//		return postIds;
	}

}
