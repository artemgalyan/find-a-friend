using AutoMapper;
using Backend.Dto;
using Backend.Repository;
using MediatR;

namespace Backend.Queries.Users.GetUserById;

public class GetUserByIdHandler : IRequestHandler<GetUserByIdQuery, UserDto?>
{
    private readonly IUserRepository _userRepository;
    private readonly IMapper _mapper;

    public GetUserByIdHandler(IUserRepository userRepository, IMapper mapper)
    {
        _userRepository = userRepository;
        _mapper = mapper;
    }

    public async Task<UserDto?> Handle(GetUserByIdQuery request, CancellationToken cancellationToken)
    {
        return _mapper.Map<UserDto>(
            await _userRepository.GetByIdAsync(request.UserId, cancellationToken)
        );
    }
}