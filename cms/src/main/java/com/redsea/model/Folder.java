package com.redsea.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.Sqls;
import com.jfinal.plugin.ehcache.CacheKit;
import com.redsea.common.Consts;
import com.redsea.common.Filter;
import com.redsea.model.base.BaseFolder;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Folder extends BaseFolder<Folder> {
	
	public static final Folder dao = new Folder();
	
	public Folder findCacheById(Object foldId){
		Map<Integer,Folder> map=CacheKit.get(Consts.CacheName.oneDay.get(), "folder_list_all");
		if(map==null){
			map=new HashMap<Integer, Folder>();
			List<Folder> list=findAll();
			for(Folder folder:list){
				map.put(folder.getId(), folder);
			}
			CacheKit.put(Consts.CacheName.oneDay.get(), "folder_list_all",map);
		}
		return map.get(foldId);
	}
	//头部菜单
	public List getFolderByCache(){
		List<Folder> list=CacheKit.get(Consts.CacheName.oneDay.get(),"head_folder_list");
		if(list==null||list.size()==0){
			String sql="select * from tbl_folder where parent_id=0 and `status`=1 ORDER BY sort DESC";
			list=Folder.dao.find(sql);
			for(Folder folder:list){
				List list2=Folder.dao.find("SELECT * from tbl_folder WHERE `status`=1 and parent_id="+folder.getId()+"");
				folder.put("hasChild", false);
				if(list2!=null&&list2.size()>0){
					folder.put("hasChild", true);
					folder.put("childList",list2);
				}
			}
			CacheKit.put(Consts.CacheName.oneDay.get(),"head_folder_list" , list);
		}
		return list;
	}
	
	//根据id取得中文名称
	public String getFoldNameByFoldId(Integer id){
		if(id==0||id==null){
			return "";
		}
		Map<Integer,String> map=CacheKit.get(Consts.CacheName.oneDay.get(), "folder_foldId_foldName");
		if(map==null){
			String sql = Sqls.get("folder_all");
		    List<Record> list=Db.find(sql);
			if(list!=null){
				map=new HashMap<Integer, String>();
				for(Record record:list){
					map.put(record.getInt("id"), record.getStr("name"));
				}
				CacheKit.put(Consts.CacheName.oneDay.get(), "folder_foldId_foldName", map);
			}else{
				return "";
			}
		}
		return map.get(id);
	}
	//根据id取得中文名称
	public Integer getFoldIdByEnName(String enName){
		if(StrKit.isBlank(enName)){
			return null;
		}
		Map<String,Integer> map=CacheKit.get(Consts.CacheName.oneDay.get(), "folder_foldEnName_foldId");
		if(map==null){
			String sql = Sqls.get("folder_all");
			List<Record> list=Db.find(sql);
			if(list!=null){
				map=new HashMap<String,Integer>();
				for(Record record:list){
					map.put(record.getStr("en_name"), record.getInt("id"));
				}
				CacheKit.put(Consts.CacheName.oneDay.get(), "folder_foldEnName_foldId", map);
			}else{
				return null;
			}
		}
		return map.get(enName);
	}
	 public Page findPageJsonByConditions(Map<String, String[]> parasMap){
		   Integer pageNo=1;//默认第几页
		    Integer pageSize=15;//默认分页记录数
	        StringBuffer sql =new StringBuffer(" FROM tbl_folder t1 LEFT JOIN tbl_folder tf ON t1.parent_id=tf.id where 1=1 ");
	           for (Entry<String, String[]> e : parasMap.entrySet()) {
	                   String paraKey = e.getKey();
	                   String[] paraValue = e.getValue();
	                   String value = paraValue[0] != null ? paraValue[0] + "" : null;
	                   if(StrKit.isBlank(value)){
	                       continue;
	                   }
	                   if(getTableInfo().hasColumnLabel(paraKey)){
	                       sql.append(" and t1." + paraKey + " = '"+value+"'");
	                   }else if (paraKey.startsWith(SEARCH_PREFIX)) {
	                      String filter=paraKey.substring(paraKey.indexOf(SEARCH_PREFIX)+SEARCH_PREFIX.length(),paraKey.lastIndexOf("_"));
	                      String attrName=paraKey.substring(paraKey.lastIndexOf("_")+1);
	                      if(!StrKit.isBlank(value)){
	                          if(filter.equals(Filter.OPERATOR_LIKE)){
	                              sql.append(" and t1." + attrName + " like '%"+value+"%'");
	                          }else  if(filter.equals(Filter.OPERATOR_NOT_LIKE)){
	                              sql.append(" and t1." + attrName + " not like '%"+value+"%'");
	                          }else if(filter.equals(Filter.OPERATOR_NULL)){
	                              sql.append(" and t1." + attrName + " is null");
	                          }else if(filter.equals(Filter.OPERATOR_NOT_NULL)){
	                              sql.append(" and t1." + attrName + "  is not null");
	                          }else if(filter.equals(Filter.RELATION_AND)){
	                              sql.append(" and t1." + attrName + " = '"+value+"'");
	                          }else{
	                              sql.append("  and t1." + attrName + " "+filter+" "+value+"");
	                          }
	                      }
	              }else if(paraKey.startsWith("page.")){
	                   value = paraValue[0] != null ? paraValue[0] + "" : "1";
	                   if("page.pageNo".equals(paraKey)){
	                       pageNo=Integer.parseInt(value);
	                   }else if("page.pageSize".equals(paraKey)){
	                       pageSize=Integer.parseInt(value);
	                   }
	              }
	          }
	            boolean flag=true;
	           for (Entry<String, String[]> e : parasMap.entrySet()) {
	               String paraKey = e.getKey();
	               if(paraKey.startsWith("order_")){
	                   if(flag){
	                       sql.append(" order by ");
	                       flag=false;
	                   }
	                   String[] paraValue = e.getValue();
	                   String value = paraValue[0] != null ? paraValue[0] + "" : null;
	                   String attrName=paraKey.replace("order_","");
	                   sql.append("t1."+attrName+" "+value+",");
	                }
	           }
	           if(!flag){
	               sql.deleteCharAt(sql.length()-1);  
	           }
	         return  paginate(pageNo, pageSize, "select t1.*,tf.name as parentName ", sql.toString()); 
	    }
	
	
	
	
	
	
}
