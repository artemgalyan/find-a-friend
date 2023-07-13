using Backend.Commands.Shelters.CreateShelter;
using Backend.Commands.Shelters.DeleteShelter;
using Backend.Commands.Shelters.UpdateShelter;
using Backend.Dto;
using Backend.Queries.Shelters.GetAllShelters;
using Backend.Queries.Shelters.GetShelterById;
using MediatR;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace Backend.Controllers;

public class ShelterController : ControllerBase
{
    private readonly IMediator _mediator;

    public ShelterController(IMediator mediator)
    {
        _mediator = mediator;
    }

    [HttpGet("/shelters/getAll")]
    public async Task<ActionResult<IEnumerable<ShelterDto>>> GetAllAsync()
    {
        return Ok(await _mediator.Send(new GetAllSheltersQuery()));
    }

    [HttpGet("/shelters/getById")]
    public async Task<ActionResult<ShelterDto?>> GetByIdAsync([FromQuery] int id)
    {
        return Ok(await _mediator.Send(new GetShelterByIdQuery { ShelterId = id }));
    }

    [Authorize(Roles = "Administrator")]
    [HttpPost("/shelters/create")]
    public async Task<IActionResult> CreateAsync([FromBody] CreateShelterCommand command)
    {
        return Ok(await _mediator.Send(command));
    }
    
    [Authorize(Roles = "Administrator")]
    [HttpPost("/shelters/update")]
    public async Task<IActionResult> UpdateAsync([FromBody] UpdateShelterCommand command)
    {
        return Ok(await _mediator.Send(command));
    }
    
    [Authorize(Roles = "Administrator")]
    [HttpPost("/shelters/delete")]
    public async Task<IActionResult> UpdateAsync([FromQuery] int id)
    {
        return Ok(await _mediator.Send(new DeleteShelterCommand { ShelterId = id }));
    }
}