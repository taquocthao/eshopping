CREATE USER eshopper WITH CREATEDB REPLICATION PASSWORD '123456';
ALTER USER eshopper WITH superuser;
CREATE DATABASE "EShopping"  WITH OWNER = eshopper  ENCODING = 'UTF8' TABLESPACE = pg_default CONNECTION LIMIT = -1;
create schema EShopping;
create extension if not exists "uuid-ossp";