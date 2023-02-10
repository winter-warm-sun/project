-- 创建数据库
drop database if exists mycnblog;
create database mycnblog DEFAULT CHARACTER SET utf8mb4;

-- 使用数据数据
use mycnblog;

-- 创建表[用户表]
drop table if exists  userinfo;
CREATE TABLE userinfo(
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(64) NOT NULL,
    photo VARCHAR(500) DEFAULT '',
    createtime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `state` INT DEFAULT 1
) DEFAULT CHARSET 'utf8mb4';


-- 创建文章表
drop table if exists  articleinfo;

CREATE TABLE articleinfo(
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    createtime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    uid INT NOT NULL,
    rcount INT NOT NULL DEFAULT 1,
    `state` INT DEFAULT 1
) DEFAULT CHARSET 'utf8mb4';


-- 添加一个用户信息
INSERT INTO `mycnblog` ,`userinfo` (`id`, `username`, `password`, `photo`, `createtime`, `updatetime`, `state`) VALUES
    (1, 'admin', 'admin', '', '2021-12-06 17:10:48', '2021-12-06 17:10:48', 1);

-- 文章添加测试数据
insert into articleinfo(title,content,uid)
values('Java','Java正文',1);


