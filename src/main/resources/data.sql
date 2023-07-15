create table if not exists customer
(
    id      bigserial primary key,
    name    text    not null,
    balance decimal not null
);

create table if not exists purchase
(
    id          bigserial primary key,
    customer_id bigint  not null references customer (id) on delete cascade,
    price       decimal not null
);

insert into customer (id, name, balance)
values (1, 'ned stark', 14000)
on conflict do nothing;