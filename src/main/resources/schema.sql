drop table if exists task;

create table task(
    id integer primary key AUTO_INCREMENT,
    subject varchar(255),
    deadLine date,
    hasDone boolean
);