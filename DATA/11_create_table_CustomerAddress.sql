
create table CustomerAddress(
     customerAddressId BIGSERIAL primary key not null,
     CustomerId BIGINT not null references customer(customerid),
     StreetAddress varchar(255),
     City varchar(255),
     District varchar(255),
     Provincial varchar(255),
     Longitude decimal,
     Latitude decimal,
     CreatedDate timestamptz,
     ModifiedDate timestamptz default current_timestamp,
     DefaultAddress boolean default false
);
