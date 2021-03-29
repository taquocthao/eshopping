create table Customer(
    CustomerId BIGSERIAL primary key not null,
    code varchar(20) not null unique,
    UserId BIGINT not null,
    active boolean default true,
    birthday timestamptz,
    createdDate timestamptz default current_timestamp,
    lastLogin timestamptz,
    beforeLastLogin timestamptz
);
