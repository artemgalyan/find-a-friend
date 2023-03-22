CREATE TABLE role (
	role_id INT NOT NULL PRIMARY KEY AUTO INCREMENT,
	name VARCHAR(50) NOT NULL
);
INSERT INTO role(type_user_id, name) VALUES
   (1, 'Администратор сайта'),
   (2, 'Модератор сайта'),
   (3, 'Администратор приюта'),
   (4, 'Пользователь сайта');
