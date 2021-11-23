drop table if exists UserRole;
create table UserRole(
     UserRoleId BIGSERIAL PRIMARY KEY NOT NULL,
     UserId varchar(255) NOT NULL references Users(userid),
     RoleId BIGINT NOT NULL,
     CreatedDate timestamp NOT NULL default current_timestamp(),
     ModifiedDate timestamp,
     UNIQUE(UserId, RoleId)
);