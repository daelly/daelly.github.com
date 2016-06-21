package org.daelly.oj.config;

import org.daelly.oj.dao.IGenericDao;
import org.daelly.oj.dao.impl.GenericDaoImpl;
import org.daelly.oj.pojo.AuthGroup;
import org.daelly.oj.pojo.AuthUser;
import org.daelly.oj.service.IGenericService;
import org.daelly.oj.service.impl.GenericServiceImpl;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
	
	@Autowired
	SessionFactory sessionFactory;

	/***********************************************************************
	 * 配置dao                                                              *
	 *                                                                     *
	 ***********************************************************************/
	
	@Bean
	public IGenericDao<AuthUser,Integer> authUserDao(){
		GenericDaoImpl<AuthUser,Integer> dao = new GenericDaoImpl<AuthUser, Integer>();
		dao.setEntityClass(AuthUser.class);
		dao.setSessionFactory(sessionFactory);
		return dao;
	}
	
	@Bean
	public IGenericDao<AuthGroup,Integer> authGroupDao(){
		GenericDaoImpl<AuthGroup, Integer> dao = new GenericDaoImpl<AuthGroup, Integer>();
		dao.setEntityClass(AuthGroup.class);
		dao.setSessionFactory(sessionFactory);
		return dao;
	}
	
	/***********************************************************************
	 * 配置service                                                          *
	 *                                                                     *
	 ***********************************************************************/
	
//	@Bean
//	public IGenericService<AuthUser, Integer> authUserService(@Qualifier("authUserDao")IGenericDao<AuthUser,Integer> dao ){
//		GenericServiceImpl<AuthUser, Integer> service = new GenericServiceImpl<AuthUser, Integer>();
//		service.setDao(dao);
//		return service;
//	}
//	
//	@Bean
//	public IGenericService<AuthGroup, Integer> authGroupService(@Qualifier("authGroupDao")IGenericDao<AuthGroup,Integer> dao ){
//		GenericServiceImpl<AuthGroup, Integer> service = new GenericServiceImpl<AuthGroup, Integer>();
//		service.setDao(dao);
//		return service;
//	}
	
}
