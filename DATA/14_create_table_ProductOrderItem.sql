
create table ProductOrderItem(
     ProductOrderItemId BIGSERIAL primary key not null,
     Code varchar(20) not null unique,
     Active Boolean default true,
     ProductSkuDimensionId BIGINT not null,
     OriginalPrice DOUBLE PRECISION not null,
     SalePrice DOUBLE PRECISION not null,
     DiscountPrice DOUBLE PRECISION not null,
     CreatedDate timestamptz not null default current_timestamp,
     ModifiedDate timestamptz
);
alter table ProductOrderItem add constraint fk_productorderitem_productskudimension_dimensionId foreign key (ProductSkuDimensionId) references productskudimension(productskudimensionid);