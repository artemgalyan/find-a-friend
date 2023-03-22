CREATE TABLE user (
	user_id INT NOT NULL PRIMARY KEY AUTO INCREMENT,
	name VARCHAR(50) NOT NULL,
	surname VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	phone_number VARCHAR(50),
	login VARCHAR(50) NOT NULL,
	password VARCHAR(max) NOT NULL,
	role_id INT NOT NULL
);

--- insert into user (user_id, name) values (1, 'Ангелина', 'Варвашевич', 'vrv@gmail.com', 'vrv12', '12345edcb', 1);
--- insert into user (user_id, name) values (2, 'Артём', 'Галян', 'gln@gmail.com', 'artem', '\][poiuyt', 2);
--- insert into user (user_id, name) values (3, 'Татьяна', 'Шестак', 'shst@gmail.com', 'tattish', 'asdfghjuytre345678ik', 1);
--- insert into user (user_id, name) values (4, 'Антон', 'Губко', 'gbk@gmail.com', 'Anton', '/.awkop347-', 3);
--- insert into user (user_id, name) values (5, 'Виктория', 'Бурак', 'brk@gmail.com', '34235dv', '231456278394oekd,c.v', 3);
