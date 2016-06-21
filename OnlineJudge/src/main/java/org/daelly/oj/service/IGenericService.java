package org.daelly.oj.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.daelly.oj.utils.Page;

public interface IGenericService<T, PK extends Serializable> {

	public abstract T get(PK id);
	
	public abstract T getByCondition(String condition);
	
	public abstract T load(PK id);
	
	public abstract List<T> loadAll();
	
	public abstract void update(T entity);
	
	public abstract void save(T entity);
	
	public abstract void saveOrUpdate(T entity);
	
	public abstract void delete(T entity);
	
	public abstract Page<T> findByPage(int start,int pageSize,Map<Object,Object> condition);
	
	public abstract Page<T> findByPage(int start,int pageSize,String condition);
}
