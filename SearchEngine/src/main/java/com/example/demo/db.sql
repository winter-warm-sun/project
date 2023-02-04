-- 创建数据库
drop database if exists search_engine;
create database search_engine DEFAULT CHARACTER SET utf8mb4;

-- 使用数据数据
use search_engine;

-- 创建表[用户表]
drop table if exists  forward_index;
create table forward_index(
                         docid int primary key ,
                         title varchar(100) not null ,
                         url varchar(200) not null ,
                         content longtext not null
) default charset 'utf8mb4';

create table inverted_index(
                        id int primary key,
                        word varchar(50) not null ,
                        docid int not null  ,
                        weight int not null
) default charset 'utf8mb4';


show tables ;

select * from forward_index;
select * from inverted_index;
