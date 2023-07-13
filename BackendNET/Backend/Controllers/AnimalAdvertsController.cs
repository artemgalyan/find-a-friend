using System.Security.Claims;
using Backend.Commands.AnimalAdverts.CreateAnimalAdvert;
using Backend.Commands.AnimalAdverts.DeleteAnimalAdvert;
using Backend.Dto;
using Backend.Queries.AnimalAdverts.GetAll;
using Backend.Queries.AnimalAdverts.GetById;
using Backend.Queries.AnimalAdverts.GetByShelterId;
using Backend.Queries.AnimalAdverts.GetByUserId;
using Backend.Repository;
using Backend.Utils;
using MediatR;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace Backend.Controllers;

public class AnimalAdvertsController : Controller
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
        string userRole = HttpContext.User.GetClaim(ClaimTypes.Role);
        int userId = int.Parse(HttpContext.User.GetClaim("id"));

        if (userRole is "Moderator" or "Administrator")
        {
            return Ok(await _mediator.Send(new DeleteAnimalAdvertCommand { AdvertId = id }));
        }

        var repo = HttpContext.RequestServices.GetRequiredService<IAnimalAdvertRepository>();
        var instance = await repo.GetByIdAsync(id, HttpContext.RequestAborted);
        if (instance is null)
        {
            return Ok(false);
        }
        if (userRole is "User")
        {
            if (instance.OwnerId != userId)
            {
                return Unauthorized();
            }
            return Ok(await _mediator.Send(new DeleteAnimalAdvertCommand { AdvertId = id }));
        }
        
        // userRole is "ShelterAdministrator"
        var userShelter = HttpContext.RequestServices.GetRequiredService<IUserShelterRepository>();
        var shelterId = await userShelter.GetShelterByUserId(instance.OwnerId, HttpContext.RequestAborted);
        if (shelterId is null || shelterId.Value.ToString() != HttpContext.User.GetClaim("shelter_id"))
        {
            return Unauthorized();
        }
        return Ok(await _mediator.Send(new DeleteAnimalAdvertCommand { AdvertId = id }));
    }

    [Authorize]
    [HttpPost("/animalAdverts/create")]
    public async Task<IActionResult> CreateAsync([FromBody] CreateAnimalAdvertCommand command)
    {
        command.UserId = int.Parse(HttpContext.User.GetClaim("id"));
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
    public Task<IActionResult> GetMineAsync()
    {
        var id = int.Parse(HttpContext.User.GetClaim("id"));
        return GetByUserIdAsync(id);
    }
}