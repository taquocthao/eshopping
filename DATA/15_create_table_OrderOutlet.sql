drop table if exists orderoutlet;
create table OrderOutlet(
                            OrderOutletId BIGSERIAL primary key not null,
                            Code varchar(20) not null unique,
                            Status varchar(50) default true,
                            CustomerId BIGINT not null,
                            CreatedBy varchar(255) not null,
                            CreatedDate timestamptz not null default current_timestamp,
                            ModifiedDate timestamptz,
                            totalPrice DOUBLE PRECISION not null,
                            totalOriginalPrice DOUBLE PRECISION not null,
                            totalDiscountPrice DOUBLE PRECISION not null,
                            totalStoreDiscount DOUBLE PRECISION not null,
                            totalLoyaltyDiscount DOUBLE PRECISION not null,
                            totalPromotionDiscount DOUBLE PRECISION not null
);
alter table OrderOutlet add constraint fk_orderoutlet_customer_customerid foreign key (CustomerId) references customer(customerid);
alter table OrderOutlet add constraint fk_orderoutlet_user_userid foreign key (CreatedBy) references users(userid);
