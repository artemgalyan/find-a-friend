USE find_a_friend;

CREATE TABLE valid_tokens
(
    [id] INT PRIMARY KEY IDENTITY(1, 1),
    [token] VARCHAR(MAX),
    [user_id] INT FOREIGN KEY REFERENCES [user](user_id) ON DELETE CASCADE
);