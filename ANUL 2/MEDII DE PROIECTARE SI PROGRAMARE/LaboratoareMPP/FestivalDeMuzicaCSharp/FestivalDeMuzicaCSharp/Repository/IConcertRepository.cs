using FestivalDeMuzicaCSharp.Domain;
using FestivalDeMuzicaCSharp.Validation;

namespace FestivalDeMuzicaCSharp.Repository;

public interface IConcertRepository : ICrudRepository<long, Concert>
{
    IEnumerable<ConcertDTO> getAllConcertData();

    IEnumerable<ConcertDayDTO> getAllConcertsByDate(DateOnly date);

    long numberOfSoldTicketsForAConcert(long concertId);

    Artist? findArtistById(long artistId);
}