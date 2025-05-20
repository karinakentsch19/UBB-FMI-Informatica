namespace FestivalDeMuzicaCSharp.Domain;

public class ConcertDayDTO
{
    public string artistFirstname { get; set; }
    
    public string artistLastname { get; set; }
    
    public long concertId { get; set; }
    
    public string concertName { get; set; }
    
    public string address { get; set; }
    
    public TimeOnly startTime { get; set; }
    
    public TimeOnly endTime { get; set; }
    
    public long numberOfAvailableSeats { get; set; }

    public ConcertDayDTO(string artistFirstname, string artistLastname, long concertId, string concertName, string address, TimeOnly startTime, TimeOnly endTime, long numberOfAvailableSeats)
    {
        this.artistFirstname = artistFirstname;
        this.artistLastname = artistLastname;
        this.concertId = concertId;
        this.concertName = concertName;
        this.address = address;
        this.startTime = startTime;
        this.endTime = endTime;
        this.numberOfAvailableSeats = numberOfAvailableSeats;
    }

    protected bool Equals(ConcertDayDTO other)
    {
        return artistFirstname == other.artistFirstname && artistLastname == other.artistLastname && concertId == other.concertId && concertName == other.concertName && address == other.address && startTime.Equals(other.startTime) && endTime.Equals(other.endTime) && numberOfAvailableSeats == other.numberOfAvailableSeats;
    }

    public override bool Equals(object? obj)
    {
        if (ReferenceEquals(null, obj)) return false;
        if (ReferenceEquals(this, obj)) return true;
        if (obj.GetType() != this.GetType()) return false;
        return Equals((ConcertDayDTO)obj);
    }

    public override int GetHashCode()
    {
        return HashCode.Combine(artistFirstname, artistLastname, concertId, concertName, address, startTime, endTime, numberOfAvailableSeats);
    }

    public override string ToString()
    {
        return $"{nameof(artistFirstname)}: {artistFirstname}, {nameof(artistLastname)}: {artistLastname}, {nameof(concertId)}: {concertId}, {nameof(concertName)}: {concertName}, {nameof(address)}: {address}, {nameof(startTime)}: {startTime}, {nameof(endTime)}: {endTime}, {nameof(numberOfAvailableSeats)}: {numberOfAvailableSeats}";
    }
}