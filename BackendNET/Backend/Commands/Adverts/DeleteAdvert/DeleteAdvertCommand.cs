using MediatR;

namespace Backend.Commands.Adverts.DeleteAdvert;

public class DeleteAdvertCommand : IRequest<bool>
{
    public int AdvertId { get; set; }
}