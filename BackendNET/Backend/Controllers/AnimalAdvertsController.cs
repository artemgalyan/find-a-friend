using Backend.Commands.AnimalAdverts.CreateAnimalAdvert;
using Backend.Commands.AnimalAdverts.DeleteAnimalAdvert;
using Backend.Dto;
using Backend.Queries.AnimalAdverts.GetAll;
using Backend.Queries.AnimalAdverts.GetById;
using Backend.Queries.AnimalAdverts.GetByShelterId;
using Backend.Queries.AnimalAdverts.GetByUserId;
using Backend.Services;
using Backend.Utils;
using MediatR;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using static Backend.Entities.User;

namespace Backend.Controllers;

public class AnimalAdvertsController : ApiController
{
    private readonly IMediator _mediator;

    public AnimalAdvertsController(IMediator mediator)
    {
        _mediator = mediator;
    }

    [HttpGet("/animalAdverts/getAll")]
    public async Task<ActionResult<IEnumerable<AnimalAdvertDto>>> GetAllAsync()
    {
        return Ok(await _mediator.Send(new GetAllAnimalAdvertsQuery()));
    }

    [HttpGet("/animalAdverts/getById")]
    public async Task<ActionResult<AnimalAdvertDto>> GetByIdAsync([FromQuery] int id)
    {
        return Ok(await _mediator.Send(new GetAnimalAdvertByIdQuery { AdvertId = id }));
    }

    [Authorize]
    [HttpDelete("/animalAdverts/delete")]
    public async Task<IActionResult> DeleteAsync([FromQuery] int id)
    {
        if (AuthData.UserRole is UserRole.Moderator or UserRole.Administrator)
        {
            return Ok(await _mediator.Send(new DeleteAnimalAdvertCommand { AdvertId = id }));
        }

        if (!await Service<IAnimalAdvertService>().ExistsAsync(id, CancellationToken))
        {
            return Ok(false);
        }
        int? shelterId = AuthData.ShelterId;
        if (await Service<IAnimalAdvertService>().CanDeleteAdvertAsync(AuthData.UserId, AuthData.UserRole, id, shelterId, CancellationToken))
        {
            return Ok(await _mediator.Send(new DeleteAnimalAdvertCommand { AdvertId = id }));
        }
        return Unauthorized();
    }

    [Authorize]
    [HttpPost("/animalAdverts/create")]
    public async Task<IActionResult> CreateAsync([FromBody] CreateAnimalAdvertCommand command)
    {
        command.UserId = AuthData.UserId;
        return Ok(await _mediator.Send(command));
    }

    [HttpGet("/animalAdverts/getByUserId")]
    public async Task<IActionResult> GetByUserIdAsync([FromQuery] int id)
    {
        return Ok(await _mediator.Send(new GetAnimalAdvertsByUserIdQuery { UserId = id }));
    }

    [HttpGet("/animalAdverts/getByShelterId")]
    public async Task<IActionResult> GetByShelterIdAsync([FromQuery] int id)
    {
        return Ok(await _mediator.Send(new GetAnimalAdvertsByShelterIdQuery { ShelterId = id }));
    }

    [Authorize]
    [HttpGet("/animalAdverts/getMine")]
    public Task<IActionResult> GetMineAsync() => GetByUserIdAsync(AuthData.UserId);
}