create table Brand(
    BrandId BIGSERIAL primary key not null,
    Name varchar(255) not null,
    Image varchar(255),
    Description text,
    Status boolean,
    Top INTEGER,
    CreatedDate timestamptz not null,
    ModifiedDate timestamptz
);