namespace FestivalDeMuzicaCSharp.Domain;

public class Artist : Entity<long>
{
    public long Cnp { get; set; }

    public string FirstName { get; set; }

    public string LastName { get; set; }

    public DateOnly BirthDate { get; set; }

    public Artist(long cnp, string firstName, string lastName, DateOnly birthDate)
    {
        Cnp = cnp;
        FirstName = firstName;
        LastName = lastName;
        BirthDate = birthDate;
    }

    protected bool Equals(Artist other)
    {
        return Cnp == other.Cnp && FirstName == other.FirstName && LastName == other.LastName && BirthDate.Equals(other.BirthDate);
    }

    public override bool Equals(object? obj)
    {
        if (ReferenceEquals(null, obj)) return false;
        if (ReferenceEquals(this, obj)) return true;
        if (obj.GetType() != this.GetType()) return false;
        return Equals((Artist)obj);
    }

    public override int GetHashCode()
    {
        return HashCode.Combine(Cnp, FirstName, LastName, BirthDate);
    }

    public override string ToString()
    {
        return $"{nameof(Cnp)}: {Cnp}, {nameof(FirstName)}: {FirstName}, {nameof(LastName)}: {LastName}, {nameof(BirthDate)}: {BirthDate}";
    }
}