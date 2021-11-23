package com.tathao.eshopping.model.entity;

import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name = "UserRole")
public class UserRoleEntity {

	private Long userRoleId;
	private UserEntity user;
	private RoleEntity role;
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	
	@Id
	@Column(name = "UserRoleId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getUserRoleId() {
		return userRoleId;
	}
	
	public void setUserRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
	}
	
	@ManyToOne
	@JoinColumn(name = "UserId", referencedColumnName = "UserId")
	public UserEntity getUser() {
		return user;
	}
	
	public void setUser(UserEntity user) {
		this.user = user;
	}

	@ManyToOne
	@JoinColumn(name="RoleId", referencedColumnName = "RoleId")
	public RoleEntity getRole() {
		return role;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
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
