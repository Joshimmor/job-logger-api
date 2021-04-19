drop database jobtrackerdb;
drop user jobtracker;
create user jobtracker with password 'password';
create database jobtrackerdb with template=template0 owner=jobtracker;
\connect jobtrackerdb;
alter default privileges grant all on tables to jobtracker;
alter default privileges grant all on sequences to jobtracker;

create table jt_users(
user_id integer primary key not null,
first_name varchar(20) not null,
department varchar(20) not null,
last_name varchar(20) not null,
email varchar(30) not null,
password text not null
);


create table jt_transactions(
transaction_id integer primary key not null,
department varchar(20) not null,
user_id integer not null,
floor integer not null,
note varchar(50) not null,
transaction_date bigint not null,
complete BIT not null,
);
alter table jt_transactions add constraint trans_department_fk
foreign key (department) references jt_users(user_id);
alter table jt_transactions add constraint trans_users_fk
foreign key (user_id) references jt_users(user_id);

create sequence et_users_seq increment 1 start 1;
create sequence et_transactions_seq increment 1 start 1000;