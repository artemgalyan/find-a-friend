using AutoMapper;
using Backend.Dto;
using Backend.Entities;

namespace Backend;

public class MappingProfile : Profile
{
    public MappingProfile()
    {
        CreateMap<Advert, AdvertDto>();
        CreateMap<AnimalAdvert, AnimalAdvertDto>();
        CreateMap<Photo, PhotoDto>();
        CreateMap<Place, PlaceDto>();
        CreateMap<Shelter, ShelterDto>();
        CreateMap<User, UserDto>();
    }
}