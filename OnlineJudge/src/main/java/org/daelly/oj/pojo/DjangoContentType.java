package org.daelly.oj.pojo;

import java.util.Set;

/**
 * DjangoContentType entity. @author MyEclipse Persistence Tools
 */

public class DjangoContentType implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -718636641934202400L;
	private Integer id;
	private String appLabel;
	private String model;
	private Set<AuthPermission> authPermissions;
	private Set<DjangoAdminLog> djangoAdminLogs;

	// Constructors

	/** default constructor */
	public DjangoContentType() {
	}

	/** minimal constructor */
	public DjangoContentType(String appLabel, String model) {
		this.appLabel = appLabel;
		this.model = model;
	}

	/** full constructor */
	public DjangoContentType(String appLabel, String model,
			Set<AuthPermission> authPermissions, Set<DjangoAdminLog> djangoAdminLogs) {
		this.appLabel = appLabel;
		this.model = model;
		this.authPermissions = authPermissions;
		this.djangoAdminLogs = djangoAdminLogs;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAppLabel() {
		return this.appLabel;
	}

	public void setAppLabel(String appLabel) {
		this.appLabel = appLabel;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Set<AuthPermission> getAuthPermissions() {
		return this.authPermissions;
	}

	public void setAuthPermissions(Set<AuthPermission> authPermissions) {
		this.authPermissions = authPermissions;
	}

	public Set<DjangoAdminLog> getDjangoAdminLogs() {
		return this.djangoAdminLogs;
	}

	public void setDjangoAdminLogs(Set<DjangoAdminLog> djangoAdminLogs) {
		this.djangoAdminLogs = djangoAdminLogs;
	}

}