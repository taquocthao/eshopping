package com.tathao.eshopping.model.entity;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UserGroup")
public class UserGroupEntity {

	private Long userGroupId;
	private String code;
	private String name;
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	
	@Id
	@Column(name = "UserGroupId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getUserGroupId() {
		return userGroupId;
	}
	
	public void setUserGroupId(Long userGroupId) {
		this.userGroupId = userGroupId;
	}
	
	@Basic
	@Column(name = "Code")
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	@Basic
	@Column(name = "Name")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Basic
	@Column(name = "CreatedDate")
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	
	@Basic
	@Column(name = "ModifiedDate")
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}	
	
	
}
