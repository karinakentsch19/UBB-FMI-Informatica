using FestivalDeMuzicaCSharp.Domain;

namespace FestivalDeMuzicaCSharp.Validation;

public class EmployeeValidator : IValidator<Employee>
{
    public void Validate(Employee entity)
    {
        string errors = "";

        if (entity.Cnp.ToString().Length != 13)
            errors += "Invalid cnp!\n";

        if (string.IsNullOrEmpty(entity.FirstName))
            errors += "Invalid first name!\n";

        if (string.IsNullOrEmpty(entity.LastName))
            errors += "Invalid last name!\n";
        
        if (string.IsNullOrEmpty(entity.Username))
            errors += "Invalid username!\n";
        
        if (string.IsNullOrEmpty(entity.Password))
            errors += "Invalid password!\n";

        if (!string.IsNullOrEmpty(errors))
            throw new Exception(errors);
    }
}