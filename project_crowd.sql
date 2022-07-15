#CREATE DATABASE project_crowd CHARACTER SET utf8;
use project_crowd;

create table if not exists project_crowd.inner_admin_role
(
    id       int auto_increment
    primary key,
    admin_id int null,
    role_id  int null
)
    comment '用户和权限中间表';

create table if not exists project_crowd.t_admin
(
    id          int auto_increment
    primary key,
    login_acct  varchar(255) not null,
    user_pswd   char(32)     not null,
    user_name   varchar(255) not null,
    email       varchar(255) not null,
    create_time char(19)     null
    );
insert into t_admin(login_acct, user_pswd, user_name, email, create_time)
values ('eav', '123123', '伊娃', 'eva@qq.com', now());


create table if not exists project_crowd.t_menu
(
    id   int auto_increment
    primary key,
    pid  int          null,
    name varchar(200) null,
    url  varchar(200) null,
    icon varchar(200) null
    );

create table if not exists project_crowd.t_role
(
    id   int auto_increment
    primary key,
    name char(100) null
    );

create table if not exists project_crowd.t_auth
(
    id	int auto_increment primary key,
    name varchar(200) null,
    title  varchar(200) null,
    category_id  int null
)
    comment '权限表';

INSERT INTO t_auth(id, name, title, category_id) VALUES (1,'','用户模块', null);
INSERT INTO t_auth(id, name, title, category_id) VALUES (2,'user:delete','删除', 1);
INSERT INTO t_auth(id, name, title, category_id) VALUES (3,'','查询', 1);
INSERT INTO t_auth(id, name, title, category_id) VALUES (4,'','角色模块', null);
INSERT INTO t_auth(id, name, title, category_id) VALUES (5,'','删除', 4);
INSERT INTO t_auth(id, name, title, category_id) VALUES (6,'','查询', 4);
INSERT INTO t_auth(id, name, title, category_id) VALUES (7,'','新增', 4);





