--liquibase formatted sql
--changeset danilgugu:6

set search_path to users_schema;

insert into user_role (user_id, role_id)
values (${db.admin.user.id}, ${db.admin.role.id});
