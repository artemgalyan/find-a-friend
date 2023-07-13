using MediatR;

namespace Backend.Commands.AnimalAdverts.DeleteAnimalAdvert;

public class DeleteAnimalAdvertCommand : IRequest<bool>
{
    public int AdvertId { get; set; }
}