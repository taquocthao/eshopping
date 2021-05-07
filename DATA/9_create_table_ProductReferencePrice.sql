
create table ProductReferencePrice (
    ProductReferencePriceId  BIGSERIAL primary key not null,
    LowestPrice double precision not null ,
    HighestPrice double precision,
    CreatedDate timestamptz not null,
    ModifiedDate timestamptz
);

alter table productreferenceprice add column ProductId bigint not null references product(productid);