USE find_a_friend;
GO
CREATE PROCEDURE AddPlace
    @country VARCHAR(max),
    @region VARCHAR(max),
    @city VARCHAR(max),
    @district VARCHAR(max)
AS
BEGIN
    INSERT INTO place(country, region, city, district) VALUES (@country, @region, @city, @district)
END;
GO
CREATE PROCEDURE AddPlaceIfNotExists
    @country VARCHAR(max),
    @region VARCHAR(max),
    @city VARCHAR(max),
    @district VARCHAR(max)
AS
BEGIN
    IF NOT EXISTS (SELECT * FROM place
                            WHERE country = @country AND
                                  region = @region AND
                                  city = @city AND
                                  district = @district)
    BEGIN
        EXECUTE AddPlace @country, @region, @city, @district
    END
END;