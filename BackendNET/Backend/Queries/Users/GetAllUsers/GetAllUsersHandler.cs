using AutoMapper;
using Backend.Dto;
using Backend.Entities;
using Backend.Repository;
using Backend.Utils;
using MediatR;

namespace Backend.Queries.Users.GetAllUsers;

public class GetAllUsersHandler : IRequestHandler<GetAllUsersQuery, IEnumerable<UserDto>>
{
    private readonly IMapper _mapper;
    private readonly IUserRepository _userRepository;

    public GetAllUsersHandler(IMapper mapper, IUserRepository userRepository)
    {
        _mapper = mapper;
        _userRepository = userRepository;
    }

    public async Task<IEnumerable<UserDto>> Handle(GetAllUsersQuery request, CancellationToken cancellationToken)
    {
        var all = await _userRepository.GetAllAsync(cancellationToken);
        return all.Map<User, UserDto>(_mapper);
    }
}