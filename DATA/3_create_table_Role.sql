create table Role(
	RoleId BIGSERIAL PRIMARY KEY NOT NULL,
	Code varchar(50) NOT NULL,
	Name varchar(100) NOT NULL,
	CreatedDate timestamp NOT NULL,
	ModifiedDate timestamp
);

INSERT INTO role (code, name, createddate, modifieddate) VALUES ('ADMIN', 'Administrator', now(), now());
INSERT INTO role (code, name, createddate, modifieddate) VALUES ('SHOPPER', 'Retailer', now(), now());
INSERT INTO role (code, name, createddate, modifieddate) VALUES ('EMPLOYEE', 'Employee', now(), now());