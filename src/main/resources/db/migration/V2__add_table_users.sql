create table users
(
    id         int primary key auto_increment,
    email      varchar(255),
    first_name varchar(50),
    last_name  varchar(100),
    password   varchar(255),
    status     varchar(50) default 'ACTIVE',
    name       varchar(30)
)