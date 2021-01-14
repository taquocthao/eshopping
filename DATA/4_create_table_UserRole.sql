create table UserRole(
	UserRoleId BIGSERIAL PRIMARY KEY NOT NULL,
	UserId BIGINT NOT NULL,
	RoleId BIGINT NOT NULL,
	CreatedDate timestamp NOT NULL,
	ModifiedDate timestamp,
	UNIQUE(UserId, RoleId)
)