CREATE TABLE "Eshopping".UserGroup(
	UserGroupId BIGSERIAL PRIMARY KEY NOT NULL,
	Code varchar(50) NOT NULL,
    CreatedDate timestamp NOT NULL,
    Name varchar(255) NOT NULL,
    ModifiedDate timestamp
)

INSERT INTO "Eshopping".usergroup (code, name, createddate, modifieddate) VALUES ('ADMIN', 'Administrator', now(), now());
INSERT INTO "Eshopping".usergroup (code, name, createddate, modifieddate) VALUES ('SHOPPER', 'Retailer', now(), now());
INSERT INTO "Eshopping".usergroup (code, name, createddate, modifieddate) VALUES ('OUTLET', 'Wholesaler', now(), now());
INSERT INTO "Eshopping".usergroup (code, name, createddate, modifieddate) VALUES ('EMPLOYEE', 'Employee', now(), now());