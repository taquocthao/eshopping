create table ProductSku
(
    ProductSkuId  BIGSERIAL primary key not null,
    ProductId     BIGINT                not null references product(productid),
    SkuCode       varchar(20)           not null,
    Title         varchar(255)          not null,
    Image         varchar(255)          not null,
    OriginalPrice DOUBLE PRECISION      not null,
    SalePrice     DOUBLE PRECISION      not null,
    Unit          varchar(255),
    BarCode       varchar(100),
    Status        boolean,
    CreatedDate   timestamptz           not null default current_timestamp,
    ModifiedDate  timestamptz
);