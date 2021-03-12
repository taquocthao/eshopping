create table Product(
    ProductId BIGSERIAL primary key not null,
    Code varchar(12) not null,
    CatGroupId BIGINT not null,
    BrandId BIGINT,
    Name varchar(255) not null,
    Image varchar(255),
    Description text,
    Status boolean,
    Top INTEGER,
    CreatedDate timestamptz not null,
    ModifiedDate timestamptz
);

alter table product add constraint fk_product_brandId foreign key (BrandId) references brand(brandid);
alter table product add constraint fk_product_catgroupId foreign key (CatGroupId) references catgroup(catgroupid);
alter table product add column ReferencePriceId bigint;