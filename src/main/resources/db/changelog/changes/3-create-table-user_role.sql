--liquibase formatted sql
--changeset danilgugu:3

set search_path to users_schema;

create table user_role
(
    user_id bigint not null references _user (id),
    role_id bigint not null references role (id),
    primary key (user_id, role_id)
);
