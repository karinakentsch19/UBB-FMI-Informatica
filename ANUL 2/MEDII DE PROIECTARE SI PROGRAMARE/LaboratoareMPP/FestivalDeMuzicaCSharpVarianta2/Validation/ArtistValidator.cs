using FestivalDeMuzicaCSharp.Domain;

namespace FestivalDeMuzicaCSharp.Validation;

public class ArtistValidator : IValidator<Artist>
{
    public void Validate(Artist entity)
    {
        string errors = "";

        if (entity.Cnp.ToString().Length != 13)
            errors += "Invalid cnp!\n";

        if (string.IsNullOrEmpty(entity.FirstName))
            errors += "Invalid first name!\n";

        if (string.IsNullOrEmpty(entity.LastName))
            errors += "Invalid last name!\n";

        if (!string.IsNullOrEmpty(errors))
            throw new Exception(errors);
    }
}