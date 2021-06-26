
create table CatGroup(
     CatGroupId BIGSERIAL primary key not null,
     ParentId BIGINT,
     Code varchar(12) not null,
     Name varchar(255) not null,
     Image varchar(255),
     Description text,
     Status boolean,
     CreatedDate timestamptz not null,
     CreatedBy BIGINT not null,
     ModifiedDate timestamptz not null
);

create unique index concurrently catgroup_code on catgroup(code);

alter table catgroup add constraint unique_code unique using index catgroup_code;

