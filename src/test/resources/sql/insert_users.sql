set search_path to users_schema;

insert into _user (id, user_name, email, password)
values (-1, 'Nick Fisher', 'example@mail.com', '$2a$10$f..K.egFrctTsuDG8LMTQ.5NihkDVpvwtJLjNKvrI91IeUTek/LL2'),
       (-2, 'Brad Greedy', 'updated@mail.com', '$2a$10$/iS1ifiyTHY7OKW4tg5jy.5XTM1hvbdhXMa2zcF1H9kByUvn26Z/6');
