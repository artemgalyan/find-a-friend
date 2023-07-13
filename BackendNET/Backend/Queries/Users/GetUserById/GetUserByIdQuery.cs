using Backend.Dto;
using MediatR;

namespace Backend.Queries.Users.GetUserById;

public class GetUserByIdQuery : IRequest<UserDto?>
{
    public int UserId { get; set; }
}