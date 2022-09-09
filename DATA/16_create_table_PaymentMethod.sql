drop table if exists PaymentMethod;
create table PaymentMethod(
    PaymentMethodId BIGSERIAL primary key not null,
    Code varchar(20) not null unique,
    Active BOOLEAN default true not null,
    Value varchar(255) not null,
    Priority INTEGER not null,
    CreateDate timestamptz not null default current_timestamp,
    ModifiedDate timestamptz
);