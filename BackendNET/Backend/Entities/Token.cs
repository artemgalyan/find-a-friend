﻿namespace Backend.Entities;

public class AuthToken
{
    public int Id { get; set; }
    public string Token { get; set; }
    public int UserId { get; set; }
}