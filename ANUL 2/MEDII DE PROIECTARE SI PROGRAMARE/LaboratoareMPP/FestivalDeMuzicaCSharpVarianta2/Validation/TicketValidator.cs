using System.Security.Cryptography;
using FestivalDeMuzicaCSharp.Domain;

namespace FestivalDeMuzicaCSharp.Validation;

public class TicketValidator : IValidator<Ticket>
{
    public void Validate(Ticket entity)
    {
        string errors = "";

        if (string.IsNullOrEmpty(entity.ClientName))
            errors += "Invalid client name!\n";
        
        if (!string.IsNullOrEmpty(errors))
            throw new Exception(errors);
    }
}