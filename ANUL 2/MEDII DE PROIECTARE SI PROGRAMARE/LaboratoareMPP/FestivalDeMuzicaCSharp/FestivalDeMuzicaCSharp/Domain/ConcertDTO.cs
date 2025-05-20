namespace FestivalDeMuzicaCSharp.Domain;

public class ConcertDTO
{
    public string artistFirstname { get; set; }
    
    public string artistLastname { get; set; }
    
    public long concertId { get; set; }
    
    public string concertName { get; set; }

    public DateOnly date { get; set; }
    
    public string address { get; set; }
    
    public long numberOfAvailableSeats { get; set; }
    
    public long numberOfSoldSeats { get; set; }

    public ConcertDTO(string artistFirstname, string artistLastname, long concertId, string concertName, DateOnly date, string address, long numberOfAvailableSeats, long numberOfSoldSeats)
    {
        this.artistFirstname = artistFirstname;
        this.artistLastname = artistLastname;
        this.concertId = concertId;
        this.concertName = concertName;
        this.date = date;
        this.address = address;
        this.numberOfAvailableSeats = numberOfAvailableSeats;
        this.numberOfSoldSeats = numberOfSoldSeats;
    }

    protected bool Equals(ConcertDTO other)
    {
        return artistFirstname == other.artistFirstname && artistLastname == other.artistLastname && concertId == other.concertId && concertName == other.concertName && date.Equals(other.date) && address == other.address && numberOfAvailableSeats == other.numberOfAvailableSeats && numberOfSoldSeats == other.numberOfSoldSeats;
    }

    public override bool Equals(object? obj)
    {
        if (ReferenceEquals(null, obj)) return false;
        if (ReferenceEquals(this, obj)) return true;
        if (obj.GetType() != this.GetType()) return false;
        return Equals((ConcertDTO)obj);
    }

    public override int GetHashCode()
    {
        return HashCode.Combine(artistFirstname, artistLastname, concertId, concertName, date, address, numberOfAvailableSeats, numberOfSoldSeats);
    }

    public override string ToString()
    {
        return $"{nameof(artistFirstname)}: {artistFirstname}, {nameof(artistLastname)}: {artistLastname}, {nameof(concertId)}: {concertId}, {nameof(concertName)}: {concertName}, {nameof(date)}: {date}, {nameof(address)}: {address}, {nameof(numberOfAvailableSeats)}: {numberOfAvailableSeats}, {nameof(numberOfSoldSeats)}: {numberOfSoldSeats}";
    }
}