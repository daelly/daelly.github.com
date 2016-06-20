package org.daelly.oj.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.daelly.oj.dao.IGenericDao;
import org.daelly.oj.utils.MapUtils;
import org.daelly.oj.utils.Page;
import org.daelly.oj.utils.SqlFilter;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;


@Repository
@SuppressWarnings("all")
public class GenericDaoImpl<T, PK extends Serializable> extends HibernateDaoSupport implements
		IGenericDao<T, PK> {
	
	private static Log logger = LogFactory.getLog(GenericDaoImpl.class);
	
	protected static final String SEARCH_PREFIX = "search_";
	
	private Class<T> entityClass;
	
	private Class<PK> pkClass;
	
	public Class<T> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public T load(PK id) {
		return getHibernateTemplate().load(entityClass, id);
	}

	public T get(PK id) {
		return getHibernateTemplate().get(entityClass, id);
	}
	
	public T getByCondition(String condition){
		try {
			Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
			String hql = " from "+entityClass.getSimpleName()+" where 1=1";
			hql += condition;
			Query query = session.createQuery(hql);
			return (T) query.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<T> loadAll() {
		return getHibernateTemplate().loadAll(entityClass);
	}

	public Page<T> findByPage(int start, int pageSize, Map<Object, Object> condition) {
		StringBuffer sb = new StringBuffer(" from "+entityClass.getSimpleName()+" where 1=1");
		String cond = this.getSearchCondition(condition);
		sb.append(cond);
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(sb.toString());
		query.setFirstResult(start);
		query.setMaxResults(pageSize);
		List<T> datas = query.list();
		String countHql = "select count(*) from "+entityClass.getSimpleName()+" where 1=1";
		countHql += cond;
		Query countQuery = session.createQuery(countHql);
		Long c = (Long) countQuery.uniqueResult();
		int count = c.intValue();
		Page page = new Page<T>(start, pageSize, (int)count, datas);
		return page;
	}

	public Page<T> findByPage(int start, int pageSize, String condition) {
		String hql = " from "+entityClass.getSimpleName()+" where 1=1";
		hql += condition;
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setFirstResult(start);
		query.setMaxResults(pageSize);
		List<T> datas = query.list();
		String countHql = "select count(*) from "+entityClass.getSimpleName()+" where 1=1";
		countHql += condition;
		Query countQuery = session.createQuery(countHql);
		Long c = (Long) countQuery.uniqueResult();
		int count = c.intValue();
		Page page = new Page<T>(start, pageSize, (int)count, datas);
		return page;
	}

	public void persist(T entity) {
		getHibernateTemplate().persist(entity);
	}

	public PK save(T entity) {
		Serializable id = null;
		try {
			id = getHibernateTemplate().save(entity);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return (PK) id;
	}

	public void saveOrUpdate(T entity) {
		try {
			getHibernateTemplate().saveOrUpdate(entity);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void delete(T entity) {
		try {
			getHibernateTemplate().delete(entity);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public void update(T entity){
		try {
			getHibernateTemplate().update(entity);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void flush() {
		getHibernateTemplate().flush();
	}

	protected String getSearchCondition(Map<Object,Object> map){
		StringBuffer sb = new StringBuffer();
		if(map == null)
			return sb.toString();
		for(Entry<Object, Object> entry:map.entrySet()){
			String key = (String) entry.getKey();
			Object value = MapUtils.getValue(map, key);
			if(key.startsWith(SEARCH_PREFIX)){
				String filter = key.substring(key.indexOf(SEARCH_PREFIX)+SEARCH_PREFIX.length(), key.lastIndexOf("_"));
				String attrName = key.substring(key.lastIndexOf("_")+1);
				if(StringUtils.isEmpty(filter) || SqlFilter.EQUAL.equals(filter)){
					sb.append(" and "+attrName+"='").append(value).append("'");
				}else if(SqlFilter.NOT_EQUAL.equals(filter)){
					sb.append(" and "+attrName+"<>'").append(value).append("'");
				}else if(SqlFilter.LIKE.equals(filter)){
					sb.append(" and "+attrName+" like '").append(value).append("%'");
				}
			}
		}
		return sb.toString();
	}
}
