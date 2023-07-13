using Microsoft.EntityFrameworkCore.Storage.ValueConversion;

namespace Backend.Database;

public class DateOnlyConverter : ValueConverter<DateOnly, DateTime>
{
    public DateOnlyConverter() : base(
        d => d.ToDateTime(TimeOnly.MinValue),
        d => DateOnly.FromDateTime(d))
    {}
}