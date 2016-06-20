package org.daelly.oj.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.daelly.oj.dao.IGenericDao;
import org.daelly.oj.service.IGenericService;
import org.daelly.oj.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class GenericServiceImpl<T, PK extends Serializable> implements IGenericService<T, PK> {
	
	private IGenericDao<T, PK> dao;

	public IGenericDao<T, PK> getDao() {
		return dao;
	}

	public void setDao(IGenericDao<T, PK> dao) {
		this.dao = dao;
	}

	public T get(PK id) {
		return dao.get(id);
	}
	
	public T getByCondition(String condition){
		return dao.getByCondition(condition);
	}

	public T load(PK id) {
		return dao.load(id);
	}

	public List<T> loadAll() {
		return dao.loadAll();
	}

	public void update(T entity) {
		dao.update(entity);
	}

	public void save(T entity) {
		dao.save(entity);
	}

	public void saveOrUpdate(T entity) {
		dao.saveOrUpdate(entity);
	}

	public void delete(T entity) {
		dao.delete(entity);
	}

	public Page<T> findByPage(int start, int pageSize, Map<Object, Object> condition) {
		return dao.findByPage(start, pageSize, condition);
	}

	public Page<T> findByPage(int start, int pageSize, String condition) {
		return dao.findByPage(start, pageSize, condition);
	}

}
