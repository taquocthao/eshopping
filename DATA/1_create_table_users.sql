
Drop table If Exists Users;
create table "Eshopping".Users(
	UserId BIGSERIAL PRIMARY KEY not null,
	UserGroupId BIGINT not null,
	Username varchar(255) not null,
	Code varchar(50) not null,
	Password varchar(255) not null,
	FullName varchar(100) not null,
	FirstName varchar(50),
	LastName varchar(50),
	Email varchar(100),
	PhoneNumber varchar(10) not null,
	Status Boolean default true,
	CreatedDate timestamp not null,
	ModifiedDate timestamp,
	UNIQUE(UserGroupId, Username)
);

alter table "Eshopping".Users ADD constraint fk_user_usergroupid FOREIGN KEY(usergroupid) REFERENCES "Eshopping".UserGroup(usergroupid);

INSERT INTO "Eshopping".users (usergroupid, username, code, password, fullname, email, phonenumber, status, createddate, modifieddate) 
VALUES (1, 'admin', 'ADMIN', '$2a$10$Mp7trdcVsKg.KSfTJeJL/OdyxlZ1xOe2nxI23LcSIer1DOVINTkeu', 'System Administrator', null, '123456789', true, now(), now())
