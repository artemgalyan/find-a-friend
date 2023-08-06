using Backend.Commands.Adverts.CreateAdvert;
using Backend.Commands.Adverts.DeleteAdvert;
using Backend.Commands.Adverts.UpdateAdvert;
using Backend.Dto;
using Backend.Queries.Adverts.GetAll;
using Backend.Queries.Adverts.GetById;
using Backend.Services;
using Backend.Utils;
using MediatR;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using static Backend.Entities.User;

namespace Backend.Controllers;

public class AdvertController : ApiController
{
    private readonly IMediator _mediator;

    public AdvertController(IMediator mediator)
    {
        _mediator = mediator;
    }

    [HttpGet("/adverts/getAll")]
    public async Task<ActionResult<IEnumerable<AdvertDto>>> GetAllAsync()
    {
        return Ok(await _mediator.Send(new GetAllAdvertsQuery()));
    }

    [HttpGet("/adverts/getById")]
    public async Task<ActionResult<AdvertDto?>> GetByIdAsync([FromQuery] int id)
    {
        return Ok(await _mediator.Send(new GetAdvertByIdQuery { AdvertId = id }));
    }

    [Authorize]
    [HttpPost("/adverts/create")]
    public async Task<IActionResult> CreateAsync([FromBody] CreateAdvertCommand command)
    {
        command.UserId = AuthData.UserId;
        return Ok(await _mediator.Send(command));
    }

    [Authorize]
    [HttpPut("/adverts/update")]
    public async Task<IActionResult> UpdateAsync([FromBody] UpdateAdvertCommand command)
    {
        if (string.IsNullOrEmpty(command.Title) || string.IsNullOrEmpty(command.Description))
        {
            return BadRequest();
        }

        if (AuthData.UserRole is not UserRole.Administrator and not UserRole.Moderator)
        {
            var userId = AuthData.UserId;
            if (!await Service<IAdvertService>().IsOwnerOfAdvertAsync(userId, command.AdvertId, CancellationToken))
            {
                return Unauthorized();
            }
        }
        return Ok(await _mediator.Send(command));
    }

    [Authorize]
    [HttpDelete("/adverts/delete")]
    public async Task<IActionResult> DeleteAsync([FromQuery] int id)
    {
        if (AuthData.UserRole is not UserRole.Administrator and not UserRole.Moderator)
        {
            var userId = AuthData.UserId;
            if (!await Service<IAdvertService>().IsOwnerOfAdvertAsync(userId, id, CancellationToken))
            {
                return Unauthorized();
            }
        }
        return Ok(await _mediator.Send(new DeleteAdvertCommand { AdvertId = id }));
    }
}