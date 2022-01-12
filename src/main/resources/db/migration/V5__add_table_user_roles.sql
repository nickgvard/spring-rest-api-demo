create table user_roles
(
    id      int primary key auto_increment,
    user_id int,
    role_id int,
    constraint user_roles_user_fkey foreign key (user_id) references users (id) on delete cascade,
    constraint user_roles_role_fkey foreign key (role_id) references roles (id) on delete cascade
)