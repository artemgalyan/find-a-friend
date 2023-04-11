USE find_a_friend;
GO
CREATE PROCEDURE SelectAllAnimalAdverts
AS
SELECT *
FROM animal_advert
         LEFT JOIN animal_type a on a.animal_type_id = animal_advert.animal_type_id
         INNER JOIN [user] u on u.user_id = animal_advert.user_id
         INNER JOIN place p on animal_advert.place_id = p.place_id;
GO

CREATE PROCEDURE SelectAnimalAdvertById @id INT
AS
BEGIN
    SELECT *
    FROM animal_advert
             LEFT JOIN animal_type a on a.animal_type_id = animal_advert.animal_type_id
             INNER JOIN [user] u on u.user_id = animal_advert.user_id
             INNER JOIN place p on animal_advert.place_id = p.place_id
    WHERE animal_advert_id = @id
END;
GO
CREATE PROCEDURE InsertAnimalAdvert @title VARCHAR(max),
                                    @description VARCHAR(max),
                                    @creation_date DATE,
                                    @birthday DATE,
                                    @sex CHAR,
                                    @castrated CHAR,
                                    @animal_type VARCHAR(max),
                                    @user_id INT,
                                    @country VARCHAR(max),
                                    @region VARCHAR(max),
                                    @city VARCHAR(max),
                                    @district VARCHAR(max)
AS
BEGIN
    EXECUTE AddPlaceIfNotExists @country, @region, @city, @district
    DECLARE @place_id int
    SET @place_id = (SELECT place_id
                     FROM place
                     WHERE country = @country
                       AND region = @region
                       AND city = @city
                       AND district = @district)

    DECLARE @animal_type_id INT
    IF NOT EXISTS(SELECT *
                  FROM animal_type
                  WHERE name = @animal_type)
        BEGIN
            INSERT INTO animal_type(name) VALUES (@animal_type)
        END

    SET @animal_type_id = (SELECT animal_type_id FROM animal_type WHERE name = @animal_type)
    INSERT INTO animal_advert(title, description, user_id, creation_date, place_id, birthday, sex, castrated,
                              animal_type_id)
    VALUES (@title, @description, @user_id, @creation_date, @place_id, @birthday, @sex, @castrated, 0);
END
GO
CREATE PROCEDURE DeleteAnimalAdvertById @id INT
AS
DELETE
FROM animal_advert
WHERE animal_advert_id = @id;
GO
CREATE PROCEDURE DeleteAnimalAdvertByUserId @id INT
AS
DELETE
FROM animal_advert
WHERE user_id = @id;
GO
CREATE PROCEDURE UpdateAnimalAdvert @advert_id INT,
                                    @title VARCHAR(max),
                                    @description VARCHAR(max),
                                    @creation_date DATE,
                                    @birthday DATE,
                                    @sex CHAR,
                                    @castrated CHAR,
                                    @animal_type VARCHAR(max),
                                    @country VARCHAR(max),
                                    @region VARCHAR(max),
                                    @city VARCHAR(max),
                                    @district VARCHAR(max)
AS
BEGIN
    EXECUTE AddPlaceIfNotExists @country, @region, @city, @district
    DECLARE @place_id int
    SET @place_id = (SELECT place_id
                     FROM place
                     WHERE country = @country
                       AND region = @region
                       AND city = @city
                       AND district = @district)
    DECLARE @animal_type_id INT
    IF NOT EXISTS(SELECT *
                  FROM animal_type
                  WHERE name = @animal_type)
        BEGIN
            INSERT INTO animal_type(name) VALUES (@animal_type)
        END

    SET @animal_type_id = (SELECT animal_type_id FROM animal_type WHERE name = @animal_type)

    UPDATE animal_advert
    SET title = @title,
        description = @description,
        creation_date = @creation_date,
        birthday = @birthday,
        sex = @sex,
        castrated = @castrated,
        animal_type_id = @animal_type_id,
        place_id = @place_id
    WHERE animal_advert_id = @advert_id;
END
