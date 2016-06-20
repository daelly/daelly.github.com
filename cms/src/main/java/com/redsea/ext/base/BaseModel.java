package com.redsea.ext.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.jfinal.core.JFinal;
import com.jfinal.kit.HashKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.ActiveRecordException;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Table;
import com.jfinal.plugin.activerecord.TableMapping;
import com.redsea.common.Filter;
import com.redsea.ext.kit.RandomKit;
import com.redsea.utils.SysUserUtil;

@SuppressWarnings("rawtypes")
public class BaseModel<M extends Model> extends Model<M> {
	
	private static final long serialVersionUID = -6215428115177000482L;
	 static boolean devMode = JFinal.me().getConstants().getDevMode();
	    private String deleteColumnLabel = "in_use";//1 删除   0 未删除
	    public final static String SEARCH_PREFIX = "search_";
	    private Integer pageNo=1;//默认第几页
	    private Integer pageSize=15;//默认分页记录数
	    private Class<? extends Model> clazz;
	    public Class<M> getClazz() {
	        Type t = getClass().getGenericSuperclass();
	        Type[] params = ((ParameterizedType) t).getActualTypeArguments();
	        return (Class<M>) params[0];
	    }
	    public Map<String, Object> getAttrs() {
	        return super.getAttrs();
	    }
	    public Table getTableInfo(){
	        return TableMapping.me().getTable(this.getClass());
	    }
	    public String getTableName(){
	        return getTableInfo().getName();
	    }
	    public boolean exists(String whereSql,Object... args){
	        return exists("id",whereSql,args);
	    }
	    public boolean exists(String pk,String whereSql,Object... args){
	        Long count = Db.queryLong("select count("+ pk+") from "+getTableName() +" where "+whereSql,args);
	        if (count==0)
	            return false;
	        else
	            return true;
	    }
	    public Map findMap(String key ,String value,String sqlWhere ){
	        List<M> dataList=this.find("select "+key+","+value+" from "+getTableName()+" where "+sqlWhere);
	        Iterator<M> it = dataList.iterator();
	        Map<Object,Object> map = new HashMap<Object, Object>();
	        while (it.hasNext()) {
	            M obj = (M) it.next();
	            if(obj.get(key)==null){
	                continue;
	            }
	            map.put(obj.get(key), obj.get(value));
	        }
	        return map;
	    }
	    public List<M> findAll(){
	        return find("select * from "+getTableName());
	    }
	    public Integer deleteAll(){     
	        return Db.update("delete from "+ getTableName());
	    }
	    public Integer deleteAll(String sqlWhere){      
	        return Db.update("delete from "+ getTableName()+" where " + sqlWhere);
	    }
	    public Integer deleteAll(String sqlWhere, Object... paras){     
	        return Db.update("delete from "+ getTableName()+" where " + sqlWhere,paras);
	    }
	    public List<M> where(String sqlWhere){
	        String sql = "select * from "+getTableName();
	        if (!StrKit.isBlank(sqlWhere)) 
	            sql+=" where "+sqlWhere;
	        return find(sql);
	    }
	    public List<M> where(String sqlWhere, Object... paras){
	        return find("select * from "+getTableName()+" where "+sqlWhere,paras);
	    }
	    /**
	     * 根据当前模型字段属性检索数据库
	     * @return 
	     */
	    public List<M> findByModel(String order){
	        Map<String, Object> attrs = getAttrs();
	        ArrayList<Object> vals = new ArrayList<Object>();
	        StringBuilder sql = new StringBuilder();
	        sql.append("select * from "+getTableName()+" where 1=1");
	        for(Map.Entry<String, Object> attr: attrs.entrySet()) {
	             sql.append(" and "+attr.getKey()+"=?");
	             vals.add(attr.getValue());
	        }
	        sql.append(" order by "+order);
	        return find(sql.toString(), vals.toArray());        
	    }
	    public List<M> findByModel(){
	        return findByModel("id asc");
	    }

	    public M findFirstByModel(){
	        return findFirstByModel("id asc");
	    }
	    public M findFirstByModel(String order){
	        Map<String, Object> attrs = getAttrs();
	        ArrayList<Object> vals = new ArrayList<Object>();
	        StringBuilder sql = new StringBuilder();
	        sql.append("select * from "+getTableName()+" where 1=1");
	        for(Map.Entry<String, Object> attr: attrs.entrySet()) {
	             sql.append(" and "+attr.getKey()+"=?");
	             vals.add(attr.getValue());
	        }
	        sql.append(" order by "+order);
	        List<M> result = find(sql.toString(), vals.toArray());      
	        return result.size() > 0 ? result.get(0) : null;
	    }
	    public Page<M> paginate(int pageNumber, int pageSize) {
	        return paginate(pageNumber, pageSize, "select *", "from "+getTableName()+" order by id asc");
	    }
	    private String tableName() {
	        return TableMapping.me().getTable(getClazz()).getName();
	    }
	    
	    @SuppressWarnings("unchecked")
	    public List<M> findByColumns(List<String> columns, List<Object> values) {
	            try {
					Preconditions.checkArgument(columns.size() > 0, "columns is empty");
					Preconditions.checkArgument(values.size() > 0, "values is empty");
					Preconditions.checkArgument(values.size() == columns.size(), "column size != values size");
					String sql = "select * from " + tableName() + " where 1=1";
					  if(getTableInfo().hasColumnLabel(deleteColumnLabel)){
					      sql += " and "+deleteColumnLabel+" is not null or "+deleteColumnLabel+" !=1 ";
					  }  
					for (String column : columns) {
					    sql += " and " + column + " = ?";
					}
					return getClazz().newInstance().find(sql, values.toArray());
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
	      }
	    @SuppressWarnings("unchecked")
	    public List<M> findListByConditions(Map<String, String[]> parasMap){
	        try {
	            StringBuffer sql =new StringBuffer("select * from " + tableName() + " where 1=1");
	            if(getTableInfo().hasColumnLabel(deleteColumnLabel)){
	                sql.append(" and "+deleteColumnLabel+" is not null or "+deleteColumnLabel+" !=1 ");
	            }
	               for (Entry<String, String[]> e : parasMap.entrySet()) {
	                       String paraKey = e.getKey();
	                       if(getTableInfo().hasColumnLabel(paraKey)){
	                           String[] paraValue = e.getValue();
	                           String value = paraValue[0] != null ? paraValue[0] + "" : null;
	                           sql.append(" and " + paraKey + " = '"+value+"'");
	                       } else if (paraKey.startsWith(SEARCH_PREFIX)) {
	                          String[] paraValue = e.getValue();
	                          String value = paraValue[0] != null ? paraValue[0] + "" : null;
	                          String filter=paraKey.substring(paraKey.indexOf(SEARCH_PREFIX)+SEARCH_PREFIX.length(),paraKey.lastIndexOf("_"));
	                          String attrName=paraKey.substring(paraKey.lastIndexOf("_")+1);
	                          if(filter.equals(Filter.OPERATOR_LIKE)){
	                              sql.append(" and " + attrName + " = '%"+value+"%'");
	                         }else if(filter.equals(Filter.OPERATOR_NULL)){
	                              sql.append(" and " + attrName + " is null");
	                         }else if(filter.equals(Filter.OPERATOR_NOT_NULL)){
	                              sql.append(" and " + attrName + "  is not null");
	                         }else if(filter.equals(Filter.RELATION_AND)){
	                             sql.append(" and " + attrName + " = '"+value+"'");
	                         }else{
	                             sql.append("  and " + attrName + " "+filter+" "+value+"");
	                         }
	                  }
	              }
	              return getClazz().newInstance().find(sql.toString());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null; 
	    }
	    public Page<M> findPageJsonByConditions(Map<String, String[]> parasMap){
	        StringBuffer sql =new StringBuffer("from " + tableName() + " where 1=1");
	        if(getTableInfo().hasColumnLabel(deleteColumnLabel)){
	            sql.append(" and ("+deleteColumnLabel+" is not null or "+deleteColumnLabel+" !=1 )");
	        }
	           for (Entry<String, String[]> e : parasMap.entrySet()) {
	                   String paraKey = e.getKey();
	                   String[] paraValue = e.getValue();
	                   String value = paraValue[0] != null ? paraValue[0] + "" : null;
	                   if(StrKit.isBlank(value)){
	                       continue;
	                   }
	                   if(getTableInfo().hasColumnLabel(paraKey)){
	                	   if(Integer.class.getName().equals(getTableInfo().getColumnType(paraKey).getName())){
	                		   sql.append(" and " + paraKey + " = "+value+"");
	                	   }else{
	                		   sql.append(" and " + paraKey + " = '"+value+"'");
	                	   }
	                   }else if (paraKey.startsWith(SEARCH_PREFIX)) {
	                      String filter=paraKey.substring(paraKey.indexOf(SEARCH_PREFIX)+SEARCH_PREFIX.length(),paraKey.lastIndexOf("_"));
	                      String attrName=paraKey.substring(paraKey.lastIndexOf("_")+1);
	                      if(!StrKit.isBlank(value)){
	                          if(filter.equals(Filter.OPERATOR_LIKE)){
	                              sql.append(" and " + attrName + " like '%"+value+"%'");
	                          }else  if(filter.equals(Filter.OPERATOR_NOT_LIKE)){
	                              sql.append(" and " + attrName + " not like '%"+value+"%'");
	                          }else if(filter.equals(Filter.OPERATOR_NULL)){
	                              sql.append(" and " + attrName + " is null");
	                          }else if(filter.equals(Filter.OPERATOR_NOT_NULL)){
	                              sql.append(" and " + attrName + "  is not null");
	                          }else if(filter.equals(Filter.RELATION_AND)){
	                              sql.append(" and " + attrName + " = '"+value+"'");
	                          }else{
	                              sql.append("  and " + attrName + " "+filter+" "+value+"");
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
	                  sql.append(attrName+" "+value+",");
	                }
	           }
	           if(!flag){
	               sql.deleteCharAt(sql.length()-1);  
	           }
	         return  paginate(pageNo, pageSize, "select *  ", sql.toString()); 
	    }
	    public int deleteByColumns(List<String> columns, List<Object> values) {
	            String sql="";
	            Table tableInfo = TableMapping.me().getTable(getClazz());
	            if (!tableInfo.hasColumnLabel(deleteColumnLabel)) {
	                    throw new ActiveRecordException("The deleteColumnLabel (" + deleteColumnLabel + ") is not exist");
	                }
	                String pKey = tableInfo.getPrimaryKey()[0];
	                sql+= "update "+tableInfo.getName()+" set "+deleteColumnLabel+" = 1";
	            sql+=" where 1=1";
	            for (String column : columns) {
	                sql += " and " + column + " = ?";
	            }
	            return Db.update(sql, values.toArray());
	        }
	    public boolean saveOrUpdate(Model<M> model){
	    	boolean result=true;
	    	try
	    	{
	    		Table table=getTableInfo();
	    		if(model!=null&&table.hasColumnLabel("create_time")&&model.get("create_time")==null){
	    			model.set("create_time", new Date());
	    		}
	    		if(model!=null&&table.hasColumnLabel("update_time")){
	    			model.set("update_time", new Date());
	    		}
	    		if(model!=null&&table.hasColumnLabel("create_time")&&model.get("create_time")==null){
	    			model.set("create_time", new Date());
	    		}
	    		if(model!=null&&table.hasColumnLabel("update_time")){
	    			model.set("update_time", new Date());
	    		}
	    		if(table.hasColumnLabel("uuid")&&model.get("uuid")!=null){
	    			model.set("uuid",RandomKit.randomStr());
	    		}
	    		if(table.hasColumnLabel(deleteColumnLabel)&&model.get(deleteColumnLabel)==null){
	    			model.set(deleteColumnLabel, 1);
	    		}
	    		if (table.getPrimaryKey()==null){
	    			throw new IllegalArgumentException("id values error, need " + table.getPrimaryKey().length + " id value");
	    		}
	    		String primaryKey=table.getPrimaryKey()[0];
	    		if(model.get(primaryKey)==null){
	    			if(table.getPrimaryKey()!=null&&table.getPrimaryKey().length>0){
	    				if(model.get(primaryKey)==null&&"String".equals(table.getColumnType(primaryKey).getSimpleName())){
	    					model.set(table.getPrimaryKey()[0], RandomKit.randomStr());
	    				}
	    			}
	    			result=model.save();
	    		}else{
	    			result=model.update();
	    		}
	    	}catch(Exception e)
	    	{
	    		e.printStackTrace();
	    		return false;
	    	}
	    	return result;
	    }
	    public boolean saveOrUpdate(Model<M> model,HttpServletRequest request){
	        boolean result=true;
	        try
	        {
	            Table table=getTableInfo();
	            if(model!=null&&table.hasColumnLabel("create_time")&&model.get("create_time")==null){
	                model.set("create_time", new Date());
	            }
	            if(model!=null&&table.hasColumnLabel("update_time")){
	                model.set("update_time", new Date());
	            }
	            if(model!=null&&table.hasColumnLabel("create_time")&&model.get("create_time")==null){
	                model.set("create_time", new Date());
	            }
	            if(model!=null&&table.hasColumnLabel("update_time")){
	                model.set("update_time", new Date());
	            }
	            if(table.hasColumnLabel("uuid")&&model.get("uuid")!=null){
	                model.set("uuid",RandomKit.randomStr());
	            }
	            if(request!=null){
	                if(table.hasColumnLabel("create_by")&&model.get("create_by")==null){
	                    model.set("create_by",SysUserUtil.getSysUserId(request));
	                }
	                if(table.hasColumnLabel("update_by")){
	                    model.set("update_by",SysUserUtil.getSysUserId(request));
	                }
	            }
	            if(table.hasColumnLabel(deleteColumnLabel)&&model.get(deleteColumnLabel)==null){
	                model.set(deleteColumnLabel, 1);
	            }
	            if (table.getPrimaryKey()==null){
	                  throw new IllegalArgumentException("id values error, need " + table.getPrimaryKey().length + " id value");
	              }
	            String primaryKey=table.getPrimaryKey()[0];
	            if(model.get(primaryKey)==null){
	                if(table.getPrimaryKey()!=null&&table.getPrimaryKey().length>0){
	                    if(model.get(primaryKey)==null&&"String".equals(table.getColumnType(primaryKey).getSimpleName())){
	                        model.set(table.getPrimaryKey()[0], RandomKit.randomStr());
	                    }
	                }
	                result=model.save();
	            }else{
	                result=model.update();
	            }
	        }catch(Exception e)
	        {
	            e.printStackTrace();
	            return false;
	        }
	        return result;
	      }
	    public boolean save(Model<M> model,HttpServletRequest request){
	    	boolean result=true;
	    	try
	    	{
	    		Table table=getTableInfo();
	    		if(model!=null&&table.hasColumnLabel("create_time")&&model.get("create_time")==null){
	    			model.set("create_time", new Date());
	    		}
	    		if(model!=null&&table.hasColumnLabel("update_time")){
	    			model.set("update_time", new Date());
	    		}
	    		if(model!=null&&table.hasColumnLabel("create_time")&&model.get("create_time")==null){
	    			model.set("create_time", new Date());
	    		}
	    		if(model!=null&&table.hasColumnLabel("update_time")){
	    			model.set("update_time", new Date());
	    		}
	    		if(table.hasColumnLabel("uuid")&&model.get("uuid")!=null){
	    			model.set("uuid",RandomKit.randomStr());
	    		}
	    		if(request!=null){
	    			if(table.hasColumnLabel("create_by")&&model.get("create_by")==null){
	    				model.set("create_by",SysUserUtil.getSysUserId(request));
	    			}
	    			if(table.hasColumnLabel("update_by")){
	    				model.set("update_by",SysUserUtil.getSysUserId(request));
	    			}
	    		}
	    		if(table.hasColumnLabel(deleteColumnLabel)&&model.get(deleteColumnLabel)==null){
	    			model.set(deleteColumnLabel, 1);
	    		}
	    		if (table.getPrimaryKey()==null){
	    			throw new IllegalArgumentException("id values error, need " + table.getPrimaryKey().length + " id value");
	    		}
    			result=model.save();
	    	}catch(Exception e)
	    	{
	    		e.printStackTrace();
	    		return false;
	    	}
	    	return result;
	    }
	    public List<M> findByColumn(String column, Object value){
	        return  findByColumns(Lists.newArrayList(column), Lists.newArrayList(value));
	    }
	    public M findFirstByColumn(String column, Object value){
	        List<M> result = findByColumns(Lists.newArrayList(column), Lists.newArrayList(value));
	        return result.size() > 0 ? result.get(0) : null;
	    }
	    public M findFirstByColumns(List<String> columns, List<Object> values) throws Exception {
	        List<M> result = findByColumns(columns, values);
	        return result.size() > 0 ? result.get(0) : null;
	    }
	    public int deleteAll2() {
	        String primaryKey = TableMapping.me().getTable(clazz).getPrimaryKey()[0];
	        return Db.update("delete from " + tableName() + " where " + primaryKey+ "=?");
	    }

	/**
	 * Find first model by cache. I recommend add "limit 1" in your sql.
	 * @see #findFirst(String, Object...)
	 * @param cacheName the cache name
	 * @param sql an SQL statement that may contain one or more '?' IN parameter placeholders
	 * @param paras the parameters of sql
	 */
	public M findFirstByCache(String cacheName, String sql, Object... paras) {
		String key = initCache(null, null, sql, null, paras);
		return findFirstByCache(cacheName, key, sql, paras);
	}
	
	/**
	 * @see #findFirstByCache(String, Object, String, Object...)
	 */
	public M findFirstByCache(String cacheName, String sql) {
		String key = initCache(null, null, sql, null);
		return findFirstByCache(cacheName, key, sql);
	}
	
	/**
	 * Find model by cache.
	 * @see #find(String, Object...)
	 * @param cacheName the cache name
	 * @param key the key used to get date from cache
	 * @return the list of Model
	 */
	public List<M> findByCache(String cacheName, String sql, Object... paras) {
		String key = initCache(null, null, sql, null, paras);
		return findByCache(cacheName, key, sql, paras);
	}
	
	/**
	 * Paginate by cache.
	 * @see #paginate(int, int, String, String, Object...)
	 * @param cacheName the cache name
	 * @return Page
	 */
	public Page<M> paginateByCache(String cacheName, int pageNumber, int pageSize, String select, String sqlExceptSelect, Object... paras) {
		
		String key = initCache(pageNumber, pageSize, select, sqlExceptSelect, paras);
		Page<M> result = null;
		if (devMode) {
			result = paginate(pageNumber, pageSize, select, sqlExceptSelect, paras);
		} else {
			result = paginateByCache(cacheName, key, pageNumber, pageSize, select, sqlExceptSelect);
		}
		return result;
	}
	
	/**
	 * memcache key 使用sql和paras
	 * @param pageNumber
	 * @param pageSize
	 * @param select
	 * @param sqlExceptSelect
	 * @param paras
	 * @return	设定文件
	 * @return String	返回类型
	 * @throws
	 */
	private String initCache(Integer pageNumber, Integer pageSize, String select, String sqlExceptSelect, Object... paras) {
		StringBuilder key = new StringBuilder(select);
		if (null != pageNumber) {
			key.append(pageNumber);
		}
		if (null != pageSize) {
			key.append(pageSize);
		}
		if (null != sqlExceptSelect) {
			key.append(sqlExceptSelect);
		}
		if (null != paras) {
			for (Object object : paras) {
				key.append(object);
			}
		}
		return HashKit.md5(key.toString());
	}
}
