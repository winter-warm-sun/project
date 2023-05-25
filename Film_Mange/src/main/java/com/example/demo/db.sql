drop database if exists film_mange;
create database film_mange DEFAULT CHARACTER SET utf8mb4;
use film_mange;

-- 创建用户表
drop table if exists userinfo;
create table userinfo(
                         id int primary key auto_increment,
                         username varchar(100) unique not null ,
                         password varchar(64) not null
)default charset 'utf8mb4';

-- 创建类型表
drop table if exists typeinfo;
create table typeinfo(
                         id int primary key auto_increment,
                         type varchar(32)
)default charset 'utf8mb4';

-- 创建新闻表
drop table if exists newsinfo;
create table newsinfo(
                         id int primary key auto_increment,
                         title varchar(32),
                         releasetime datetime,
                         content text not null
)default charset 'utf8mb4';

-- 创建电影表
drop table if exists filminfo;
create table filminfo(
                         id int primary key auto_increment,
                         name varchar(64),
                         director varchar(64),
                         type varchar(32),
                         img varchar(32),
                         description varchar(255),
                         path varchar(64)
)default charset 'utf8mb4';
