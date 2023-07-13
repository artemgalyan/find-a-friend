using Backend.Dto;
using MediatR;

namespace Backend.Queries.Users.GetAllUsers;

public class GetAllUsersQuery : IRequest<IEnumerable<UserDto>>
{
    
}