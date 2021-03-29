create table ShoppingCart(
    ShoppingCartId BIGSERIAL not null primary key,
    SkuId BIGINT not null references productsku(productskuid),
    Quantity INTEGER not null,
    CustomerId BIGINT not null references customer(CustomerId),
    CreatedDate timestamptz not null,
    ModifiedDate timestamptz not null,
    referenceBy Bigint not null references users(userid)
);