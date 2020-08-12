insert into SEC_User (email, encryptedPassword, ENABLED)
values ('shkurtim@sheridancollege.ca', '$2a$10$1ltibqiyyBJMJQ4hqM7f0OusP6np/IHshkYc4TjedwHnwwNChQZCy', 1);

insert into SEC_User (email, encryptedPassword, ENABLED)
values ('jonathan.penava@sheridancollege.ca', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);

 
insert into sec_role (roleName)
values ('ROLE_ADMIN');
 
insert into sec_role (roleName)
values ('ROLE_USER');
 

 
insert into user_role (userId, roleId)
values (1, 1);
 
insert into user_role (userId, roleId)
values (1, 2);
 
insert into user_role (userId, roleId)
values (2, 2);

insert into Menu (menuName, price)
values ('Burger', 6), ('Sandwich', 6), ('Fish&Chips', 10),('Hot Dog', 5), ('Coke', 2), ('Sprite', 2), ('Ice Tea', 2), ('Draft Beer',5);