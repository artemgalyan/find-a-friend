using System.Security.Claims;
using Backend.Commands.Users.CreateUser;
using Backend.Commands.Users.DeleteUser;
using Backend.Commands.Users.SetUserRole;
using Backend.Commands.Users.UpdateUser;
using Backend.Dto;
using Backend.Queries.Users.GetAllUsers;
using Backend.Queries.Users.GetIdAndLogin;
using Backend.Queries.Users.GetUserById;
using Backend.Repository;
using Backend.Utils;
using MediatR;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace Backend.Controllers;

public class UserController : ControllerBase
{
    private readonly IMediator _mediator;

    public UserController(IMediator mediator)
    {
        _mediator = mediator;
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
        var repo = HttpContext.RequestServices.GetRequiredService<IUserRepository>();
        if (await repo.GetByLoginAsync(command.Login, HttpContext.RequestAborted) is not null)
        {
            return BadRequest("Duplicate login");
        }
        return Ok(await _mediator.Send(command));
    }

    [Authorize]
    [HttpPut("/users/update")]
    public async Task<IActionResult> UpdateAsync([FromBody] UpdateUserCommand command)
    {
        int userId = int.Parse(HttpContext.User.GetClaim("id"));
        if (userId != command.UserId && HttpContext.User.GetClaim(ClaimTypes.Role) != "Administrator")
        {
            return Unauthorized();
        }

        return Ok(await _mediator.Send(command));
    }

    [Authorize]
    [HttpPut("/users/delete")]
    public async Task<IActionResult> DeleteAsync([FromQuery] int id)
    {
        int userId = int.Parse(HttpContext.User.GetClaim("id"));
        if (userId != id && HttpContext.User.GetClaim(ClaimTypes.Role) != "Administrator")
        {
            return Unauthorized();
        }

        return Ok(await _mediator.Send(new DeleteUserCommand { UserId = id }));
    }

    [Authorize(Roles = "Administrator")]
    [HttpPut("/users/setRole")]
    public async Task<IActionResult> SetRoleAsync([FromBody] SetUserRoleCommand command)
    {
        if (command.NewRole == Entities.User.UserRole.Administrator)
        {
            return BadRequest();
        }

        return Ok(await _mediator.Send(command));
    }

    [Authorize]
    [HttpGet("/users/getId")]
    public async Task<ActionResult<GetIdAndLoginResponse>> GetIdAsync()
    {
        int userId = int.Parse(HttpContext.User.GetClaim("id"));
        return Ok(await _mediator.Send(new GetIdAndLoginQuery { UserId = userId }));
    }

    [Authorize]
    [HttpGet("/users/getSelfInfo")]
    public Task<ActionResult<UserDto?>> GetSelfInfoAsync()
    {
        int userId = int.Parse(HttpContext.User.GetClaim("id"));
        return GetByIdAsync(userId);
    } 
}