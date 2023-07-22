using Backend.Commands.Users.CreateUser;
using Backend.Commands.Users.DeleteUser;
using Backend.Commands.Users.SetUserRole;
using Backend.Commands.Users.UpdateUser;
using Backend.Dto;
using Backend.Queries.Users.GetAllUsers;
using Backend.Queries.Users.GetIdAndLogin;
using Backend.Queries.Users.GetUserById;
using Backend.Services;
using Backend.Utils;
using MediatR;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using static Backend.Entities.User;

namespace Backend.Controllers;

public class UserController : ApiController
{
    private readonly IMediator _mediator;
    private readonly IUserService _userService;

    public UserController(IMediator mediator, IUserService userService)
    {
        _mediator = mediator;
        _userService = userService;
    }

    [HttpGet("/users/getAll")]
    public async Task<ActionResult<IEnumerable<UserDto>>> GetAllAsync()
    {
        return Ok(await _mediator.Send(new GetAllUsersQuery()));
    }

    [Authorize]
    [HttpGet("/users/getById")]
    public async Task<ActionResult<UserDto?>> GetByIdAsync([FromQuery] int id)
    {
        return Ok(await _mediator.Send(new GetUserByIdQuery { UserId = id }));
    }

    [HttpPost("/users/create")]
    public async Task<IActionResult> CreateUserAsync([FromBody] CreateUserCommand command)
    {
        if (command.Password.Length < 8)
        {
            return BadRequest("Too short password");
        }
        if (!EmailVerifier.IsValidEmail(command.Email))
        {
            return BadRequest("Bad email");
        }
        if (!PhoneNumberVerifier.IsValidPhoneNumber(command.PhoneNumber))
        {
            return BadRequest("Bad phone number");
        }
        if (await _userService.IsDuplicateLoginAsync(command.Login, CancellationToken))
        {
            return BadRequest("Duplicate login");
        }
        return Ok(await _mediator.Send(command));
    }

    [Authorize]
    [HttpPut("/users/update")]
    public async Task<IActionResult> UpdateAsync([FromBody] UpdateUserCommand command)
    {
        if (AuthData.UserId != command.UserId && AuthData.UserRole is not UserRole.Administrator)
        {
            return Unauthorized();
        }

        return Ok(await _mediator.Send(command));
    }

    [Authorize]
    [HttpPut("/users/delete")]
    public async Task<IActionResult> DeleteAsync([FromQuery] int id)
    {
        if (AuthData.UserId != id && AuthData.UserRole is not UserRole.Administrator)
        {
            return Unauthorized();
        }

        return Ok(await _mediator.Send(new DeleteUserCommand { UserId = id }));
    }

    [Authorize(Roles = "Administrator")]
    [HttpPut("/users/setRole")]
    public async Task<IActionResult> SetRoleAsync([FromBody] SetUserRoleCommand command)
    {
        if (command.NewRole is UserRole.Administrator)
        {
            return Unauthorized();
        }

        return Ok(await _mediator.Send(command));
    }

    [Authorize]
    [HttpGet("/users/getId")]
    public async Task<ActionResult<GetIdAndLoginResponse>> GetIdAsync()
    {
        return Ok(await _mediator.Send(new GetIdAndLoginQuery { UserId = AuthData.UserId }));
    }

    [Authorize]
    [HttpGet("/users/getSelfInfo")]
    public Task<ActionResult<UserDto?>> GetSelfInfoAsync() => GetByIdAsync(AuthData.UserId);
}