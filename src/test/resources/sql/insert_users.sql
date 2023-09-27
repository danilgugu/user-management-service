set search_path to users_schema;

insert into _user (id, user_name, email, password)
values (-1, 'Nick Fisher', 'example@mail.com', 'pass'),
       (-2, 'Brad Greedy', 'updated@mail.com', 'fizzbuzz');
