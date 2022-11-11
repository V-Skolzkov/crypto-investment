create table if not exists t_crypto_currency
(
    id bigint auto_increment,
    time_stamp bigint not null,
    symbol varchar(100) not null,
    price decimal not null,
    constraint t_crypto_currency_pk primary key (id)
);
