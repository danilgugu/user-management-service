--liquibase formatted sql
--changeset danilgugu:4

set search_path to users_schema;

insert into _user (id, user_name, email, password)
values (${db.admin.user.id}, 'John Chester', 'admin@mail.com',
        '$2a$10$f..K.egFrctTsuDG8LMTQ.5NihkDVpvwtJLjNKvrI91IeUTek/LL2');
alter sequence user_id_seq restart with 51;
