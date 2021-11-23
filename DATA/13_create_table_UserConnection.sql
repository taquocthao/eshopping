
create table UserConnection
(
    UserId         varchar(255) not null,
    ProviderId     varchar(255) not null,
    ProviderUserId varchar(255) not null,
    Rank           INTEGER not null,
    DisplayName    VARCHAR(255),
    ProfileUrl     VARCHAR(512),
    ImageUrl       VARCHAR(512),
    AccessToken    VARCHAR(255) not null,
    Secret         VARCHAR(255),
    RefreshToken   VARCHAR(255),
    ExpireTime     TIMESTAMPTZ
);
alter table UserConnection add primary key (UserId, ProviderId, ProviderUserId);
