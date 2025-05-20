namespace FestivalDeMuzicaCSharp.Domain;

public class Concert : Entity<long>
{
    public Artist Artist { get; set; }

    public string Name { get; set; }

    public long NumberOfSeats { get; set; }

    public DateOnly Date { get; set; }

    public TimeOnly StartTime { get; set; }

    public TimeOnly EndTime { get; set; }

    public string Address { get; set; }

    public Concert(Artist artist, string name, long numberOfSeats, DateOnly date, TimeOnly startTime, TimeOnly endTime, string address)
    {
        Artist = artist;
        Name = name;
        NumberOfSeats = numberOfSeats;
        Date = date;
        StartTime = startTime;
        EndTime = endTime;
        Address = address;
    }

    protected bool Equals(Concert other)
    {
        return Artist.Equals(other.Artist) && Name == other.Name && NumberOfSeats == other.NumberOfSeats && Date.Equals(other.Date) && StartTime.Equals(other.StartTime) && EndTime.Equals(other.EndTime) && Address == other.Address;
    }

    public override bool Equals(object? obj)
    {
        if (ReferenceEquals(null, obj)) return false;
        if (ReferenceEquals(this, obj)) return true;
        if (obj.GetType() != this.GetType()) return false;
        return Equals((Concert)obj);
    }

    public override int GetHashCode()
    {
        return HashCode.Combine(Artist, Name, NumberOfSeats, Date, StartTime, EndTime, Address);
    }

    public override string ToString()
    {
        return $"{nameof(Artist)}: {Artist}, {nameof(Name)}: {Name}, {nameof(NumberOfSeats)}: {NumberOfSeats}, {nameof(Date)}: {Date}, {nameof(StartTime)}: {StartTime}, {nameof(EndTime)}: {EndTime}, {nameof(Address)}: {Address}";
    }
}