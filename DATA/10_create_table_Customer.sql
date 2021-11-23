drop table if exists Customer cascade;
create table Customer(
     CustomerId BIGSERIAL primary key not null,
     code varchar(20) not null unique,
     UserId varchar(255) not null,
     active boolean default true,
     birthday timestamptz,
     createdDate timestamptz default current_timestamp,
     lastLogin timestamptz,
     beforeLastLogin timestamptz
);
alter table Customer add constraint fk_customer_user_userid foreign key (UserId) references Users(userid);