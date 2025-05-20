using FestivalDeMuzicaCSharp.Domain;

namespace FestivalDeMuzicaCSharp.Validation;

public class ConcertValidator : IValidator<Concert>
{
    public void Validate(Concert entity)
    {
        string errors = "";

        if (string.IsNullOrEmpty(entity.Name))
            errors += "Invalid name!\n";
        
        if (entity.NumberOfSeats <= 0)
            errors += "No more seats available!\n";

        if (string.IsNullOrEmpty(entity.Address))
            errors += "Invalid address!\n";

        // if (entity.StartTime > entity.EndTime)
        //     errors += "Start time must be before end time!\n";

        if (!string.IsNullOrEmpty(errors))
            throw new Exception(errors);
    }
}