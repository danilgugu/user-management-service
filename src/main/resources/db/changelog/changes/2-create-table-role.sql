--liquibase formatted sql
--changeset danilgugu:2

set search_path to users_schema;

create sequence role_id_seq increment by 50;

create table role
(
    id    bigint  not null primary key default nextval('role_id_seq'),
    name  varchar not null,
    scope varchar not null
);
alter sequence role_id_seq owned by role.id;
