--liquibase formatted sql
--changeset danilgugu:5

set search_path to users_schema;

insert into role (id, name, scope)
values (${db.default.role.id}, 'ROLE_DEFAULT', 'NONE'),
       (${db.admin.role.id}, 'ROLE_ADMIN', 'USER.DELETE');
alter sequence role_id_seq restart with 52;
