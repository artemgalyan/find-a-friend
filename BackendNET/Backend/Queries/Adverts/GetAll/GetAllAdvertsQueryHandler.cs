using AutoMapper;
using Backend.Dto;
using Backend.Repository;
using MediatR;

namespace Backend.Queries.Adverts.GetAll;

public class GetAllAdvertsQueryHandler : IRequestHandler<GetAllAdvertsQuery, IEnumerable<AdvertDto>>
{
    private readonly IMapper _mapper;
    private readonly IAdvertRepository _advertRepository;

    public GetAllAdvertsQueryHandler(IAdvertRepository advertRepository, IMapper mapper)
    {
        _advertRepository = advertRepository;
        _mapper = mapper;
    }

    public async Task<IEnumerable<AdvertDto>> Handle(GetAllAdvertsQuery request, CancellationToken cancellationToken)
    {
        var all = await _advertRepository.GetAllAsync(cancellationToken);
        return all.Select(x => _mapper.Map<AdvertDto>(x));
    }
}