package org.daelly.oj.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import org.daelly.oj.pojo.AuthUser;
import org.daelly.oj.utils.MapUtils;
import org.daelly.oj.utils.PasswordUtils;

public class AuthUserService extends GenericServiceImpl<AuthUser, Integer> {
	
	public boolean insertRegisterUser(Map<String,String[]> map) throws Exception{
		String username = MapUtils.getValue(map, "username");
		String password = MapUtils.getValue(map, "password");
		String email = MapUtils.getValue(map, "email");
		AuthUser existUser = this.getByCondition(" and username='"+username+"'");
		if(existUser != null){
			return false;
		}
		AuthUser user = new AuthUser();
		user.setUsername(username);
		String encryptPw = PasswordUtils.createHash(password);
		user.setPassword(encryptPw);
		user.setEmail(email);
		user.setDateJoined(new Timestamp(new Date().getTime()));
		user.setIsActive(true);
		user.setIsSuperuser(false);
		user.setFirstName("");
		user.setLastName("");
		user.setIsStaff(true);
//		Set<AuthGroup> authGroups = new HashSet<AuthGroup>();
//		AuthGroup group = null;
//		authGroups.add(group);
//		user.setAuthGroups(authGroups);
		this.save(user);
		return true;
	}
	
	public boolean checkLoginUser(String username,String password) throws Exception{
		AuthUser user = this.getByCondition(" and username='"+username+"'");
		if(user == null)
			return false;
		return PasswordUtils.validatePassword(password, user.getPassword());
	}
}
