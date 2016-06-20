package org.daelly.oj.pojo;

import java.util.Set;

/**
 * AuthPermission entity. @author MyEclipse Persistence Tools
 */

public class AuthPermission implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1570416539157695209L;
	private Integer id;
	private DjangoContentType djangoContentType;
	private String name;
	private String codename;
	private Set<AuthUser> authUsers;
	private Set<AuthGroup> authGroups;

	// Constructors

	/** default constructor */
	public AuthPermission() {
	}

	/** minimal constructor */
	public AuthPermission(DjangoContentType djangoContentType, String name,
			String codename) {
		this.djangoContentType = djangoContentType;
		this.name = name;
		this.codename = codename;
	}

	/** full constructor */
	public AuthPermission(DjangoContentType djangoContentType, String name,
			String codename, Set<AuthUser> authUsers,
			Set<AuthGroup> authGroups) {
		this.djangoContentType = djangoContentType;
		this.name = name;
		this.codename = codename;
		this.authUsers = authUsers;
		this.authGroups = authGroups;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public DjangoContentType getDjangoContentType() {
		return this.djangoContentType;
	}

	public void setDjangoContentType(DjangoContentType djangoContentType) {
		this.djangoContentType = djangoContentType;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCodename() {
		return this.codename;
	}

	public void setCodename(String codename) {
		this.codename = codename;
	}

	public Set<AuthUser> getAuthUsers() {
		return this.authUsers;
	}

	public void setAuthUsers(Set<AuthUser> authUsers) {
		this.authUsers = authUsers;
	}

	public Set<AuthGroup> getAuthGroups() {
		return this.authGroups;
	}

	public void setAuthGroups(Set<AuthGroup> authGroups) {
		this.authGroups = authGroups;
	}

}