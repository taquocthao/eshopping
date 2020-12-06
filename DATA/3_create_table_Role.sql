create table "Eshopping".Role(
	RoleId BIGSERIAL PRIMARY KEY NOT NULL,
	Code varchar(50) NOT NULL,
	Name varchar(100) NOT NULL,
	CreatedDate timestamp NOT NULL,
	ModifiedDate timestamp
);

INSERT INTO "Eshopping".role (code, name, createddate, modifieddate) VALUES ('ADMIN', 'Administrator', now(), now());
INSERT INTO "Eshopping".role (code, name, createddate, modifieddate) VALUES ('SHOPPER', 'Retailer', now(), now());
INSERT INTO "Eshopping".role (code, name, createddate, modifieddate) VALUES ('EMPLOYEE', 'Employee', now(), now());