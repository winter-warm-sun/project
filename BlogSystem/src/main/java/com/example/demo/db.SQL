-- 创建数据库
drop database if exists mycnblog;
create database mycnblog DEFAULT CHARACTER SET utf8mb4;

-- 使用数据数据
use mycnblog;

-- 创建表[用户表]
drop table if exists  userinfo;
create table userinfo(
                         id int primary key auto_increment,
                         username varchar(100) unique not null,
                         password varchar(64) not null,
                         photo varchar(500) default '',
                         `state` int default 1
) default charset 'utf8mb4';

-- 创建文章表
drop table if exists  articleinfo;
create table articleinfo(
                            id int primary key auto_increment,
                            title varchar(100) not null,
                            content text not null,
                            createtime datetime,
                            uid int not null,
                            rcount int not null default 1,
                            `state` int default 1
)default charset 'utf8mb4';

