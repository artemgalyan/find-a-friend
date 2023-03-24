CREATE TABLE animal_advert(
    animal_advert_id INT PRIMARY KEY AUTO INCREMENT,
    animal_type_id INT NOT NULL,
    title VARCHAR(max) NOT NULL,
    description VARCHAR(max) NOT NULL,
    user_id INT NOT NULL FOREIGN KEY REFERENCES user(user_id) ON DELETE CASCADE,
    creation_date DATE NOT NULL,
    place_id INT NOT NULL,
    photo_id INT,
    birthday DATE,
    sex VARCHAR(1), -- F / M
    castrated INT
);