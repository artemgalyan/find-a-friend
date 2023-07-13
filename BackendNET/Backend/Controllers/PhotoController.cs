using Backend.Dto;
using Backend.Queries.Photos.GetByAdvertId;
using Backend.Queries.Photos.GetPreview;
using MediatR;
using Microsoft.AspNetCore.Mvc;

namespace Backend.Controllers;

public class PhotoController : ControllerBase
{
    private readonly IMediator _mediator;

    public PhotoController(IMediator mediator)
    {
        _mediator = mediator;
    }

    [HttpGet("/photos/getByAdvertId")]
    public async Task<ActionResult<IEnumerable<PhotoDto>>> GetByAdvertIdAsync([FromQuery] int id)
    {
        return Ok(await _mediator.Send(new GetPhotosByAnimalAdvertQuery { AdvertId = id }));
    }

    [HttpGet("/photos/getPreview")]
    public async Task<ActionResult<PhotoDto?>> GetPreviewAsync([FromQuery] int id)
    {
        return Ok(await _mediator.Send(new GetPreviewQuery { AdvertId = id }));
    }
}