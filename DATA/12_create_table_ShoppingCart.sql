drop table if exists ShoppingCart;
create table ShoppingCart(
     ShoppingCartId BIGSERIAL not null primary key,
     SkuId BIGINT not null references productsku(productskuid),
     Quantity INTEGER not null,
     CustomerId BIGINT not null references customer(CustomerId),
     CreatedDate timestamptz not null,
     ModifiedDate timestamptz not null,
     referenceBy varchar(255) not null references users(userid)
);

alter table ShoppingCart drop constraint shoppingcart_skuid_fkey;
alter table ShoppingCart add column skuDimensionId bigint not null references productskudimension(productskudimensionid);