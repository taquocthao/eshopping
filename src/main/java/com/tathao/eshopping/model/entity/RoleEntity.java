package com.tathao.eshopping.model.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "Role")
public class RoleEntity {

	private Long roleId;
	private List<UserEntity> user;
	private String code;
	private String name;
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	
	@Id
	@Column(name = "RoleId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getRoleId() {
		return roleId;
	}
	
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	@ManyToMany(mappedBy = "roles")
	public List<UserEntity> getUser() {
		return user;
	}

	public void setUser(List<UserEntity> user) {
		this.user = user;
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
