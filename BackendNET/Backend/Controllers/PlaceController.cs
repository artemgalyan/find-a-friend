using Backend.Commands.Places.CreatePlace;
using Backend.Commands.Places.DeletePlace;
using Backend.Commands.Places.UpdatePlace;
using Backend.Dto;
using Backend.Queries.Places.GetAll;
using Backend.Queries.Places.GetById;
using MediatR;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace Backend.Controllers;

public class PlaceController : ControllerBase
{
    private readonly IMediator _mediator;

    public PlaceController(IMediator mediator)
    {
        _mediator = mediator;
    }

    [HttpGet("/places/getAll")]
    public async Task<ActionResult<IEnumerable<PlaceDto>>> GetAllAsync()
    {
        return Ok(await _mediator.Send(new GetAllPlacesQuery()));
    }

    [HttpGet("/places/getById")]
    public async Task<ActionResult<PlaceDto?>> GetByIdAsync([FromQuery] int id)
    {
        return Ok(await _mediator.Send(new GetPlaceByIdQuery { PlaceId = id }));
    }

    [Authorize(Roles = "Administrator")]
    [HttpPost("/places/create")]
    public async Task<IActionResult> CreateAsync([FromBody] CreatePlaceCommand command)
    {
        return Ok(await _mediator.Send(command));
    }

    [Authorize(Roles = "Administrator")]
    [HttpPut("/places/update")]
    public async Task<IActionResult> UpdateAsync([FromBody] UpdatePlaceCommand command)
    {
        return Ok(await _mediator.Send(command));
    }

    [Authorize(Roles = "Administrator")]
    [HttpDelete("/places/delete")]
    public async Task<IActionResult> DeleteAsync([FromQuery] int id)
    {
        return Ok(await _mediator.Send(new DeletePlaceCommand { PlaceId = id }));
    }
}