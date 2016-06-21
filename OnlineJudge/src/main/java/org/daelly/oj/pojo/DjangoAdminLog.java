package org.daelly.oj.pojo;

import java.sql.Timestamp;

/**
 * DjangoAdminLog entity. @author MyEclipse Persistence Tools
 */

public class DjangoAdminLog implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 2599148660582107792L;
	private Integer id;
	private AuthUser authUser;
	private DjangoContentType djangoContentType;
	private Timestamp actionTime;
	private String objectId;
	private String objectRepr;
	private Short actionFlag;
	private String changeMessage;

	// Constructors

	/** default constructor */
	public DjangoAdminLog() {
	}

	/** minimal constructor */
	public DjangoAdminLog(AuthUser authUser, Timestamp actionTime,
			String objectRepr, Short actionFlag, String changeMessage) {
		this.authUser = authUser;
		this.actionTime = actionTime;
		this.objectRepr = objectRepr;
		this.actionFlag = actionFlag;
		this.changeMessage = changeMessage;
	}

	/** full constructor */
	public DjangoAdminLog(AuthUser authUser,
			DjangoContentType djangoContentType, Timestamp actionTime,
			String objectId, String objectRepr, Short actionFlag,
			String changeMessage) {
		this.authUser = authUser;
		this.djangoContentType = djangoContentType;
		this.actionTime = actionTime;
		this.objectId = objectId;
		this.objectRepr = objectRepr;
		this.actionFlag = actionFlag;
		this.changeMessage = changeMessage;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AuthUser getAuthUser() {
		return this.authUser;
	}

	public void setAuthUser(AuthUser authUser) {
		this.authUser = authUser;
	}

	public DjangoContentType getDjangoContentType() {
		return this.djangoContentType;
	}

	public void setDjangoContentType(DjangoContentType djangoContentType) {
		this.djangoContentType = djangoContentType;
	}

	public Timestamp getActionTime() {
		return this.actionTime;
	}

	public void setActionTime(Timestamp actionTime) {
		this.actionTime = actionTime;
	}

	public String getObjectId() {
		return this.objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectRepr() {
		return this.objectRepr;
	}

	public void setObjectRepr(String objectRepr) {
		this.objectRepr = objectRepr;
	}

	public Short getActionFlag() {
		return this.actionFlag;
	}

	public void setActionFlag(Short actionFlag) {
		this.actionFlag = actionFlag;
	}

	public String getChangeMessage() {
		return this.changeMessage;
	}

	public void setChangeMessage(String changeMessage) {
		this.changeMessage = changeMessage;
	}

}