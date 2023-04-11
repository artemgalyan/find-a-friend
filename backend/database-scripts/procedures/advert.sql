USE find_a_friend;
GO
CREATE PROCEDURE SelectAllAdverts
AS
BEGIN
    SELECT *
    FROM advert
             INNER JOIN [user] u on u.user_id = advert.user_id
             INNER JOIN place p on p.place_id = advert.place_id
END
GO
CREATE PROCEDURE SelectAdvertById @id int
AS
BEGIN
    SELECT *
    FROM advert
             INNER JOIN [user] u on u.user_id = advert.user_id
             INNER JOIN place p on p.place_id = advert.place_id
    WHERE advert_id = @id
END
GO
CREATE PROCEDURE InsertAdvert @type int,
                              @title VARCHAR(max),
                              @description VARCHAR(max),
                              @creation_date DATE,
                              @country VARCHAR(max),
                              @region VARCHAR(max),
                              @city VARCHAR(max),
                              @district VARCHAR(max),
                              @user_id int
AS
BEGIN
    EXECUTE AddPlaceIfNotExists @country, @region, @city, @district
    DECLARE @place_id int
    SET @place_id = (SELECT place_id
                     FROM place
                     WHERE country = @country AND region = @region AND city = @city AND district = @district)

    INSERT INTO advert(advert_type, title, description, creation_date, place_id, user_id)
    VALUES (@type, @title, @description, @creation_date, @place_id, @user_id)
END
GO
CREATE PROCEDURE UpdateAdvert @advert_id INT,
                               @title VARCHAR(max),
                               @description VARCHAR(max),
                               @creation_date DATE,
                               @country VARCHAR(max),
                               @region VARCHAR(max),
                               @city VARCHAR(max),
                               @district VARCHAR(max)
AS
BEGIN
    EXEC AddPlaceIfNotExists @country, @region, @city, @district

    DECLARE @place_id int
    SET @place_id = (SELECT place_id
                     FROM place
                     WHERE country = @country AND region = @region AND city = @city AND district = @district);

    UPDATE advert
    SET title = @title,
        description = @description,
        creation_date = @creation_date,
        place_id = @place_id
    WHERE advert_id = @advert_id;
END
GO
CREATE PROCEDURE DeleteAdvertByUserId @user_id INT
AS
BEGIN
    DELETE FROM advert WHERE user_id = @user_id;
END
GO
CREATE PROCEDURE DeleteAdvertById @advert_id INT
AS
BEGIN
    DELETE FROM advert WHERE advert_id = @advert_id;
END