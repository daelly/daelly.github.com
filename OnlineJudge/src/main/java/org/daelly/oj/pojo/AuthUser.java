package org.daelly.oj.pojo;

import java.sql.Timestamp;
import java.util.Set;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * AuthUser entity. @author MyEclipse Persistence Tools
 */

public class AuthUser implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -895057523813152825L;
	private Integer id;
	@JSONField(serialize=false)
	private String password;
	private Timestamp lastLogin;
	private Boolean isSuperuser;
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private Boolean isStaff;
	private Boolean isActive;
	private Timestamp dateJoined;
	@JSONField(serialize=false)
	private Set<AuthGroup> authGroups;
	@JSONField(serialize=false)
	private Set<DjangoAdminLog> djangoAdminLogs;
	@JSONField(serialize=false)
	private Set<AuthPermission> authPermissions;

	// Constructors

	/** default constructor */
	public AuthUser() {
	}

	/** minimal constructor */
	public AuthUser(String password, Boolean isSuperuser, String username,
			String firstName, String lastName, String email, Boolean isStaff,
			Boolean isActive, Timestamp dateJoined) {
		this.password = password;
		this.isSuperuser = isSuperuser;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.isStaff = isStaff;
		this.isActive = isActive;
		this.dateJoined = dateJoined;
	}

	/** full constructor */
	public AuthUser(String password, Timestamp lastLogin, Boolean isSuperuser,
			String username, String firstName, String lastName, String email,
			Boolean isStaff, Boolean isActive, Timestamp dateJoined,
			Set<AuthGroup> authGroups, Set<DjangoAdminLog> djangoAdminLogs,
			Set<AuthPermission> authPermissions) {
		this.password = password;
		this.lastLogin = lastLogin;
		this.isSuperuser = isSuperuser;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.isStaff = isStaff;
		this.isActive = isActive;
		this.dateJoined = dateJoined;
		this.authGroups = authGroups;
		this.djangoAdminLogs = djangoAdminLogs;
		this.authPermissions = authPermissions;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Timestamp getLastLogin() {
		return this.lastLogin;
	}

	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Boolean getIsSuperuser() {
		return this.isSuperuser;
	}

	public void setIsSuperuser(Boolean isSuperuser) {
		this.isSuperuser = isSuperuser;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getIsStaff() {
		return this.isStaff;
	}

	public void setIsStaff(Boolean isStaff) {
		this.isStaff = isStaff;
	}

	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Timestamp getDateJoined() {
		return this.dateJoined;
	}

	public void setDateJoined(Timestamp dateJoined) {
		this.dateJoined = dateJoined;
	}

	public Set<AuthGroup> getAuthGroups() {
		return this.authGroups;
	}

	public void setAuthGroups(Set<AuthGroup> authGroups) {
		this.authGroups = authGroups;
	}

	public Set<DjangoAdminLog> getDjangoAdminLogs() {
		return this.djangoAdminLogs;
	}

	public void setDjangoAdminLogs(Set<DjangoAdminLog> djangoAdminLogs) {
		this.djangoAdminLogs = djangoAdminLogs;
	}

	public Set<AuthPermission> getAuthPermissions() {
		return this.authPermissions;
	}

	public void setAuthPermissions(Set<AuthPermission> authPermissions) {
		this.authPermissions = authPermissions;
	}

}