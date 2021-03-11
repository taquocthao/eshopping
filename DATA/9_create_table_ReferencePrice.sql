
create table ReferencePrice (
    ReferencePriceId  BIGSERIAL primary key not null,
    LowestPrice double precision not null ,
    HighestPrice double precision,
    CreatedDate timestamptz not null,
    ModifiedDate timestamptz
);