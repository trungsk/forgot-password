# forgot-password

-- SQL Script
-- insert 1 cột chứa email của bạn vào, password thì insert gì cũng được vì mình sẽ đổi nó, otp và otplife luôn khởi tạo là null
-- otplife: mốc thời gian mà otp được sinh ra

drop database if exists Shop;
create database Shop; 
use Shop;

drop table if exists Customer;
create table Customer(
	id int primary key auto_increment,
    email varchar(30) not null,
    pass varchar(200) not null,
    otp varchar(6),
    otplife datetime 
    );
    
insert into Customer values (null, 'thanhtrung2500@gmail.com', '$2a$10$uvbSIdtJ3iiUTTB3hbF7PeqNSttS9PvT7u2tIVENuUlofGXfZTVkW',null,null),
							(null, 'amikard.gma@gmail.com', '$2a$10$uvbSIdtJ3iiUTTB3hbF7PeqNSttS9PvT7u2tIVENuUlofGXfZTVkW',null,null);
