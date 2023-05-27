CREATE DATABASE find_a_friend;

USE find_a_friend;

CREATE TABLE role
(
    role_id INT          NOT NULL PRIMARY KEY IDENTITY (1, 1),
    name    VARCHAR(max) NOT NULL
);

CREATE TABLE place
(
    place_id INT          NOT NULL PRIMARY KEY IDENTITY (1, 1),
    country  VARCHAR(max) NOT NULL,
    region   VARCHAR(max) NOT NULL,
    city     VARCHAR(max) NOT NULL,
    district VARCHAR(max) NOT NULL
);


CREATE TABLE [user]
(
    user_id      INT          NOT NULL PRIMARY KEY IDENTITY (1, 1),
    name         VARCHAR(max) NOT NULL,
    surname      VARCHAR(max) NOT NULL,
    email        VARCHAR(max) NOT NULL,
    phone_number VARCHAR(max),
    login        VARCHAR(max) NOT NULL UNIQUE,
    password     VARCHAR(max) NOT NULL,
    role_id      INT          NOT NULL REFERENCES role (role_id)
);

CREATE TABLE shelter
(
    shelter_id INT PRIMARY KEY IDENTITY (1, 1),
    place_id   INT          NOT NULL,
    name       VARCHAR(max) NOT NULL,
    FOREIGN KEY (place_id) REFERENCES place (place_id)
);

CREATE TABLE user_shelter
(
    user_id    INT REFERENCES [user] (user_id) ON DELETE CASCADE,
    shelter_id INT REFERENCES shelter (shelter_id) ON DELETE CASCADE,
);

CREATE TABLE animal_type
(
    animal_type_id INT PRIMARY KEY IDENTITY (1, 1),
    name           VARCHAR(max) NOT NULL
);

CREATE TABLE advert
(
    advert_id     INT PRIMARY KEY IDENTITY (1, 1),
    advert_type   VARCHAR(max) NOT NULL,
    title         VARCHAR(max) NOT NULL,
    description   VARCHAR(max) NOT NULL,
    user_id       INT          NOT NULL,
    creation_date DATE         NOT NULL,
    place_id      INT          NOT NULL,
    FOREIGN KEY (user_id) REFERENCES [user] (user_id) ON DELETE CASCADE,
    FOREIGN KEY (place_id) REFERENCES place (place_id)
);

CREATE TABLE animal_advert
(
    animal_advert_id INT PRIMARY KEY IDENTITY (1, 1),
    animal_type_id   INT          NOT NULL,
    title            VARCHAR(max) NOT NULL,
    description      VARCHAR(max) NOT NULL,
    user_id          INT          NOT NULL,
    creation_date    DATE         NOT NULL,
    place_id         INT          NOT NULL,
    birthday         DATE,
    sex              VARCHAR(1), -- F / M
    castrated        VARCHAR(1), -- T / F
    FOREIGN KEY (user_id) REFERENCES [user] (user_id) ON DELETE CASCADE,
    FOREIGN KEY (place_id) REFERENCES place (place_id),
    FOREIGN KEY (animal_type_id) REFERENCES animal_type (animal_type_id)
);

CREATE TABLE photo
(
    photo_id         INT PRIMARY KEY IDENTITY (1, 1),
    data             VARBINARY(max) NOT NULL,
    animal_advert_id INT,
    FOREIGN KEY (animal_advert_id) REFERENCES animal_advert (animal_advert_id) ON DELETE CASCADE
);

/*INSERT INTO photo(animal_advert_id, data)
SELECT 6, CONVERT(varbinary(MAX), 'D:\Subsidiary\sun.jpg');

INSERT INTO photo(animal_advert_id, data)
SELECT 6, CONVERT(varbinary(MAX), 'D:\Subsidiary\sun.jpg');

INSERT INTO photo(animal_advert_id, data)
SELECT 7, CONVERT(varbinary(MAX), 'D:\Subsidiary\sun.jpg');

INSERT INTO photo(animal_advert_id, data)
SELECT 8, CONVERT(varbinary(MAX), 'D:\Subsidiary\sun.jpg');*/
