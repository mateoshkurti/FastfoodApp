create table SEC_USER
(
  userId            BIGINT NOT NULL Primary Key AUTO_INCREMENT,
  email             VARCHAR(75) NOT NULL UNIQUE,
  encryptedPassword VARCHAR(128) NOT NULL,
  enabled           BIT NOT NULL 
) ;

create table SEC_ROLE
(
  roleId   BIGINT NOT NULL Primary Key AUTO_INCREMENT,
  roleName VARCHAR(30) NOT NULL UNIQUE
) ;

create table USER_ROLE
(
  ID     BIGINT NOT NULL Primary Key AUTO_INCREMENT,
  userId BIGINT NOT NULL,
  roleId BIGINT NOT NULL
);

create table Menu
(
menuID BIGINT NOT NULL Primary Key AUTO_INCREMENT,
menuName VARCHAR(255),
price int
);

create table CustomerOrder
(
orderID BIGINT NOT NULL Primary Key AUTO_INCREMENT,
menuID BIGINT NOT NULL,
userId BIGINT NOT NULL
);

alter table CustomerOrder
add constraint Order_FK1 foreign key (userId)
  references SEC_USER (userId);

  alter table CustomerOrder
add constraint Order_FK2 foreign key (menuID)
  references Menu (menuID);
  



alter table USER_ROLE
  add constraint USER_ROLE_UK unique (userId, roleId);

alter table USER_ROLE
  add constraint USER_ROLE_FK1 foreign key (userId)
  references SEC_USER (userId);
 
alter table USER_ROLE
  add constraint USER_ROLE_FK2 foreign key (roleId)
  references SEC_ROLE (roleId);
