using AutoMapper;
using Backend.Dto;
using Backend.Repository;
using MediatR;

namespace Backend.Queries.Adverts.GetById;

public class GetAdvertByIdHandler : IRequestHandler<GetAdvertByIdQuery, AdvertDto?>
{
    private readonly IMapper _mapper;
    private readonly IAdvertRepository _repository;

    public GetAdvertByIdHandler(IMapper mapper, IAdvertRepository repository)
    {
        _mapper = mapper;
        _repository = repository;
    }

    public async Task<AdvertDto?> Handle(GetAdvertByIdQuery request, CancellationToken cancellationToken)
    {
        return _mapper.Map<AdvertDto>(await _repository.GetByIdAsync(request.AdvertId, cancellationToken));
    }
}