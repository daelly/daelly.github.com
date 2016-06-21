package org.daelly.oj.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.daelly.oj.dao.IGenericDao;
import org.daelly.oj.pojo.AuthGroup;
import org.daelly.oj.pojo.AuthUser;
import org.daelly.oj.service.impl.AuthUserService;
import org.daelly.oj.utils.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-hibernate.xml"})
public class TestAuthUserService {
	private static final Logger logger = Logger.getLogger(TestAuthUserService.class);
	
	@Autowired
	AuthUserService authUserService;
	
	@Test
	public void testLoad(){
		Map<Object,Object> condition = new HashMap<Object, Object>();
		condition.put("start", new Object[]{0});
		condition.put("pageSize", new Object[]{2});
		condition.put("search_like_lastName", new Object[]{"lee"});
		Page<AuthUser> page = authUserService.findByPage(0, 2, condition);
		System.out.println(page);
	}
	
}
