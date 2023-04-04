USE find_a_friend;

CREATE LOGIN [TestUser] WITH PASSWORD = 'password';

CREATE USER [TestUser] FOR LOGIN [TestUser];