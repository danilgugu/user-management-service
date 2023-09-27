--liquibase formatted sql
--changeset danilgugu:1

set search_path to users_schema;

create sequence user_id_seq increment by 50;

create table _user
(
    id        bigint  not null primary key default nextval('user_id_seq'),
    user_name varchar not null,
    email     varchar not null unique,
    password  varchar not null
);
alter sequence user_id_seq owned by _user.id;
