create schema if not exists stockmarket;

SET search_path TO stockmarket;

create table if not exists users (
	id bigserial primary key,
	fname varchar(50) not null,
	lname varchar(50) not null,
	email varchar(50) not null unique,
	password varchar(50) not null,
	phoneno bigint not null unique check (phoneno > 999999999 and phoneno < 10000000000)
);

create table if not exists roles (
	id bigserial primary key,
	name varchar(50) not null
);

create table if not exists role_to_user (
	id bigserial,
	role_id bigint references roles(id),
	user_id bigint references users(id)
);

create table if not exists stocks (
	id bigserial primary key,
	company_name varchar(20) not null,
	base_price decimal default 0,
	variation decimal default 0.5,
	interval smallint default 5,
	volume bigint not null
);

create table if not exists orders (
	id bigserial primary key,
	user_id bigint references users(id),
	stock_id bigint references stocks(id),
	quantity bigint not null,
	unit_price decimal not null,
	type varchar(20) not null,
	placing_on timestamp default now(),
	status varchar(20) not null

);

create table if not exists app_settings(
	id bigserial primary key,
	start_hour smallint default 8 check (start_hour>= 1 and start_hour < 24),
	start_min smallint default 0 check (start_hour>= 0 and start_hour < 60),
	end_hour smallint default 18 check (start_hour>= 1 and start_hour < 24),
	end_min smallint default 0 check (start_hour>= 0 and start_hour < 60),
	working_days int[] not null
);

create table if not exists user_funds (
	id bigserial primary key,
	user_id bigint references users(id),
	transcation_type varchar(20),
	transaction_id bigint references orders(id) null,
	transaction_date timestamp default now(),
	amount decimal not null
);


insert into roles (name) values ('ADMIN'),('USER');
insert into app_settings (start_hour,start_min,end_hour,end_min,working_days) values (8,0,15,8,'{1,2,3,4,5}');

insert into users (fname,lname,email,password,phoneno) values
('admin','admin','admin@stocks.com','admin@123',9999999999),
('user','user','user@stocks.com','user@456',9999999990);

insert into role_to_user (role_id,user_id) values 
(1,1),(2,2);

insert into stocks(company_name,base_price,volume)values('INFY',100,1000);
