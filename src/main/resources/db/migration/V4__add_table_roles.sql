create table roles
(
    id     int primary key auto_increment,
    name   varchar(50),
    status varchar(50) default 'ACTIVE'
)