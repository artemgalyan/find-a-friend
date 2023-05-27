USE find_a_friend;

CREATE TABLE auth_invalid_tokens
(
    [id] INT PRIMARY KEY IDENTITY(1, 1),
    [token] VARCHAR(MAX)
);