using System.Security.Claims;
using Backend.Commands.Adverts.CreateAdvert;
using Backend.Commands.Adverts.DeleteAdvert;
using Backend.Commands.Adverts.UpdateAdvert;
using Backend.Dto;
using Backend.Queries.Adverts.GetAll;
using Backend.Queries.Adverts.GetById;
using Backend.Repository;
using Backend.Utils;
using MediatR;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace Backend.Controllers;

public class AdvertController : ControllerBase
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
        command.UserId = int.Parse(HttpContext.User.GetClaim("id"));
        return Ok(await _mediator.Send(command));
    }

    [Authorize]
    [HttpPut("/adverts/update")]
    public async Task<IActionResult> UpdateAsync([FromBody] UpdateAdvertCommand command)
    {
        var principal = HttpContext.User;
        if (string.IsNullOrEmpty(command.Title) || string.IsNullOrEmpty(command.Description))
        {
            return BadRequest();
        }

        if (principal.GetClaim(ClaimTypes.Role) is not "Administrator" and "Moderator")
        {
            var repo = HttpContext.RequestServices.GetRequiredService<IAdvertRepository>();
            var advert = await repo.GetByIdAsync(command.AdvertId, HttpContext.RequestAborted);
            if (advert is null)
            {
                return BadRequest();
            }

            if (advert.OwnerId.ToString() != principal.GetClaim("id"))
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
        var principal = HttpContext.User;
        if (principal.GetClaim(ClaimTypes.Role) is not "Administrator" and "Moderator")
        {
            var repo = HttpContext.RequestServices.GetRequiredService<IAdvertRepository>();
            var advert = await repo.GetByIdAsync(id, HttpContext.RequestAborted);
            if (advert is null)
            {
                return BadRequest();
            }

            if (advert.OwnerId.ToString() != principal.GetClaim("id"))
            {
                return Unauthorized();
            }
        }
        return Ok(await _mediator.Send(new DeleteAdvertCommand { AdvertId = id }));
    }
}