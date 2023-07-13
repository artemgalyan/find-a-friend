using AutoMapper;

namespace Backend.Utils;

public static class EnumerableExtensions
{
    public static IEnumerable<TDestination> Map<TSource, TDestination>(this IEnumerable<TSource> enumerable,
        IMapper mapper)
    {
        return enumerable.Select(x => mapper.Map<TDestination>(x));
    }

    public static IEnumerable<T> Then<T>(this IEnumerable<T> enumerable, Action<T> action)
    {
        return enumerable.Select(x =>
        {
            action(x);
            return x;
        });
    }
}