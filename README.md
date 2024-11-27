# Test-task-Sber-
CRUD Application


Код создания БД:

create database crud_task;


Код создания таблицы в БД:

create table phone(
id int not null primary key auto_increment,
brand varchar(256) not null,
model varchar(256) not null,
price double not null
);
