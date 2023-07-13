using Backend.Commands.UserShelter.AddUserToShelter;
using Backend.Commands.UserShelter.RemoveUserFromShelter;
using MediatR;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace Backend.Controllers;

[Authorize(Roles = "Administrator,Moderator")]
public class UserShelterController : ControllerBase
{
    private readonly IMediator _mediator;

    public UserShelterController(IMediator mediator)
    {
        _mediator = mediator;
    }

    [HttpPost("/userShelter/addUserToShelter")]
    public async Task<IActionResult> AddUserToShelterAsync([FromQuery] int shelterId, [FromQuery] int userId)
    {
        return Ok(await _mediator.Send(new AddUserToShelterCommand { UserId = userId, ShelterId = shelterId }));
    }

    [HttpPost("/userShelter/removeUserFromShelter")]
    public async Task<IActionResult> RemoveFromShelterAsync([FromQuery] int userId)
    {
        return Ok(await _mediator.Send(new RemoveUserFromShelterCommand { UserId = userId }));
    }
}