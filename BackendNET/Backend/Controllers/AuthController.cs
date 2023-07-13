using Backend.Commands.Auth;
using MediatR;
using Microsoft.AspNetCore.Mvc;

namespace Backend.Controllers;

public class AuthController : ControllerBase
{
    private readonly IMediator _mediator;

    public AuthController(IMediator mediator)
    {
        _mediator = mediator;
    }

    [HttpPost("/auth/signIn")]
    public async Task<IActionResult> SignInAsync([FromQuery] string login, [FromQuery] string password)
    {
        return Ok(await _mediator.Send(new SignInCommand { Login = login, Password = password }));
    }
}