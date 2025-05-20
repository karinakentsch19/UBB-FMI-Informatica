using FestivalDeMuzicaCSharp.Domain;

namespace FestivalDeMuzicaCSharp.Repository;

public interface ITicketRepository : ICrudRepository<long, Ticket>
{
    Employee? findEmployeeById(long employeeId);

    Concert? findConcertId(long concertId);

    Artist? findArtistById(long artistId);
}