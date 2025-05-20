namespace FestivalDeMuzicaCSharp.Domain;

public class Employee : Entity<long>
{
    public long Cnp { get; set; }
    
    public string FirstName { get; set; }
    
    public string LastName { get; set; }
    
    public DateOnly BirthDate { get; set; }
    
    public string Username { get; set; }
    
    public string Password { get; set; }

    public Employee(long cnp, string firstName, string lastName, DateOnly birthDate, string username, string password)
    {
        Cnp = cnp;
        FirstName = firstName;
        LastName = lastName;
        BirthDate = birthDate;
        Username = username;
        Password = password;
    }

    protected bool Equals(Employee other)
    {
        return Cnp == other.Cnp && FirstName == other.FirstName && LastName == other.LastName && BirthDate.Equals(other.BirthDate) && Username == other.Username && Password == other.Password;
    }

    public override bool Equals(object? obj)
    {
        if (ReferenceEquals(null, obj)) return false;
        if (ReferenceEquals(this, obj)) return true;
        if (obj.GetType() != this.GetType()) return false;
        return Equals((Employee)obj);
    }

    public override int GetHashCode()
    {
        return HashCode.Combine(Cnp, FirstName, LastName, BirthDate, Username, Password);
    }

    public override string ToString()
    {
        return $"{nameof(Cnp)}: {Cnp}, {nameof(FirstName)}: {FirstName}, {nameof(LastName)}: {LastName}, {nameof(BirthDate)}: {BirthDate}, {nameof(Username)}: {Username}, {nameof(Password)}: {Password}";
    }
}