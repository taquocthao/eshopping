package com.tathao.eshopping.model.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "Users")
public class UserEntity {
	
	private Long userId;
	private UserGroupEntity userGroup;
    private String username;
    private String code;
    private String password;
    private String fullName;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Boolean status;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private List<RoleEntity> roles;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId")
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	@ManyToOne
	@JoinColumn(name = "UserGroupId", referencedColumnName = "UserGroupId")
	public UserGroupEntity getUserGroup() {
		return userGroup;
	}
	
	public void setUserGroup(UserGroupEntity userGroup) {
		this.userGroup = userGroup;
	}
	
	@Column(name="UserName")
	@Basic
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "Code")
	@Basic
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name = "PassWord")
	@Basic
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "FullName")
	@Basic
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	@Column(name = "FirstName")
	@Basic
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Column(name = "LastName")
	@Basic
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Column(name = "Email")
	@Basic
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "PhoneNumber")
	@Basic
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@Column(name = "Status")
	@Basic
	public Boolean getStatus() {
		return status;
	}
	
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	@Column(name = "CreatedDate")
	@Basic
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name = "ModifiedDate")
	@Basic
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "UserRole",
			joinColumns = {@JoinColumn(name = "UserId")},
			inverseJoinColumns ={@JoinColumn(name = "RoleId")})
	public List<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleEntity> roles) {
		this.roles = roles;
	}
}
