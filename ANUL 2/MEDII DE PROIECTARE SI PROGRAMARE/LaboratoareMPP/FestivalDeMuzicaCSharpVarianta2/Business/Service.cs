using FestivalDeMuzicaCSharp.Domain;
using FestivalDeMuzicaCSharp.Repository;

namespace FestivalDeMuzicaCSharp.Business;

public class Service : IService
{
    private IArtistRepository artistRepository;
    private IConcertRepository concertRepository;
    private IEmployeeRepository employeeRepository;
    private ITicketRepository ticketRepository;

    public Service(IArtistRepository artistRepository, IConcertRepository concertRepository, IEmployeeRepository employeeRepository, ITicketRepository ticketRepository)
    {
        this.artistRepository = artistRepository;
        this.concertRepository = concertRepository;
        this.employeeRepository = employeeRepository;
        this.ticketRepository = ticketRepository;
    }

    public IEnumerable<ConcertDTO> getAllConcertData()
    {
        return concertRepository.getAllConcertData();
    }

    public IEnumerable<ConcertDayDTO> getAllConcertsByDate(DateOnly date)
    {
        return concertRepository.getAllConcertsByDate(date);
    }

    public Employee findEmployeeByUsernameAndPassword(string username, string password)
    {
        return employeeRepository.findEmployeeByUsernameAndPassword(username, password) ?? throw new InvalidOperationException();
    }

    public Concert findConcertById(long concertId)
    {
        return concertRepository.find(concertId) ?? throw new InvalidOperationException();
    }

    public void updateNumberOfAvailableSeatsForAConcert(long concertId)
    {
        Concert concert = findConcertById(concertId);
        long numberOfAvailableSeats = concert.NumberOfSeats;
        Concert newConcert = new Concert(concert.Artist, concert.Name, concert.NumberOfSeats - 1, concert.Date,
            concert.StartTime, concert.EndTime, concert.Address);
        newConcert.Id = concertId;
        concertRepository.Update(newConcert);
    }

    public void addTicket(long employeeId, long concertId, string clientName, DateTime purchaseDateTime, long numberOfTickets)
    {
        Employee employee = employeeRepository.find(employeeId)!;
        Concert concert = concertRepository.find(concertId)!;
        Ticket ticket = new Ticket(employee, concert, clientName, purchaseDateTime);
        for (int i = 1; i <= numberOfTickets; i++)
        {
            ticketRepository.Add(ticket);
            updateNumberOfAvailableSeatsForAConcert(concertId);
        }
    }
}