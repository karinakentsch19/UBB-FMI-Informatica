using FestivalDeMuzicaCSharp.Domain;

namespace FestivalDeMuzicaCSharp.Business;

public interface IService
{
    IEnumerable<ConcertDTO> getAllConcertData();

    IEnumerable<ConcertDayDTO> getAllConcertsByDate(DateOnly date);
    
    Employee findEmployeeByUsernameAndPassword(string username, string password);

    void addTicket(long employeeId, long concertId, string clientName, DateTime purchaseDateTime, long numberOfTickets);

    Concert findConcertById(long concertId);

    void updateNumberOfAvailableSeatsForAConcert(long concertId);
}