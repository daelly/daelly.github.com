package org.daelly.oj.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.daelly.oj.utils.Page;

/**
 * 创建时间：2015-2-6 下午2:26:42
 * 
 * @author daelly
 * @version 2.2
 * 
 * Dao通用接口
 */

public abstract interface IGenericDao<T, PK extends Serializable> {
	
	public abstract T load(PK id);
	
	public abstract T get(PK id);
	
	public abstract T getByCondition(String condition);
	
	public abstract List<T> loadAll();
	
	public abstract Page<T> findByPage(int start,int pageSize,Map<Object,Object> condition);
	
	public abstract Page<T> findByPage(int start,int pageSize,String condition);
	
	public abstract void persist(T entity);
	
	public abstract PK save(T entity);
	
	public abstract void saveOrUpdate(T entity);
	
	public abstract void delete(T entity);
	
	public abstract void update(T entity);
	
	public abstract void flush();
}
