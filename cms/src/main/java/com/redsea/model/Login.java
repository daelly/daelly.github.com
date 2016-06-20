package com.redsea.model;

import com.redsea.model.base.BaseLogin;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Login extends BaseLogin<Login> {
	public static final Login dao = new Login();
	/**
	 * 根据openid查找
	 * @param openid
	 * @return
	 */
	public Login findByOpenID(String openid, String type) {
		String sql = "SELECT wb.* FROM wb_login wb WHERE wb.open_id = ? AND wb.type = ? limit 1";
		return this.findFirst(sql, openid, type);
	}
}