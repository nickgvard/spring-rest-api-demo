create table events (
    id int primary key auto_increment,
    user_id int not null,
    file_id int not null,
    name varchar(30),
    constraint events_user_fkey foreign key (user_id) references users(id) on delete cascade on update cascade,
    constraint event_file_fkey foreign key (file_id) references files(id) on delete cascade on update cascade
)