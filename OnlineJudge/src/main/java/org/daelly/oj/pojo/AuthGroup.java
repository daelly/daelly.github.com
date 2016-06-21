package org.daelly.oj.pojo;

import java.util.Set;

/**
 * AuthGroup entity. @author MyEclipse Persistence Tools
 */

public class AuthGroup implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5791060265832656489L;
	private Integer id;
	private String name;
	private Set<AuthPermission> authPermissions;
	private Set<AuthUser> authUsers;

	// Constructors

	/** default constructor */
	public AuthGroup() {
	}

	/** minimal constructor */
	public AuthGroup(String name) {
		this.name = name;
	}

	/** full constructor */
	public AuthGroup(String name, Set<AuthPermission> authPermissions,
			Set<AuthUser> authUsers) {
		this.name = name;
		this.authPermissions = authPermissions;
		this.authUsers = authUsers;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<AuthPermission> getAuthPermissions() {
		return this.authPermissions;
	}

	public void setAuthPermissions(Set<AuthPermission> authPermissions) {
		this.authPermissions = authPermissions;
	}

	public Set<AuthUser> getAuthUsers() {
		return this.authUsers;
	}

	public void setAuthUsers(Set<AuthUser> authUsers) {
		this.authUsers = authUsers;
	}

}