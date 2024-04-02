-- DDL for creating clients table
use TESTDB;

create table clients (
id INT IDENTITY PRIMARY KEY,
username VARCHAR(50) NOT NULL,
name VARCHAR(100) NOT NULL,
area VARCHAR(100) NOT NULL
);

-- DML for inserting dummy data into clients table
insert into clients (username, name, area) values('abc', 'nameABC', 'Kuala Lumpur');
insert into clients (username, name, area) values('ali', 'Ali', 'Selangor');
insert into clients (username, name, area) values('ahkao', 'Ah Kao', 'Johor');
insert into clients (username, name, area) values('muthu', 'Muthu', 'Perak');
