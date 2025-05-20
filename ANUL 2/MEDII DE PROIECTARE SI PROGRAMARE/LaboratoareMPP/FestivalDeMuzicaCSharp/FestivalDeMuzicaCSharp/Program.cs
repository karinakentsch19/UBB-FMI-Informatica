// See https://aka.ms/new-console-template for more information

using System.Configuration;
using System.Runtime.InteropServices.JavaScript;
using FestivalDeMuzicaCSharp.Domain;
using FestivalDeMuzicaCSharp.Repository;
using FestivalDeMuzicaCSharp.Utils;
using FestivalDeMuzicaCSharp.Validation;
using log4net.Config;

class Program
{
    static void Main(string[] args)
    {
        XmlConfigurator.Configure();
        IDictionary<string, string> props = new SortedDictionary<string, string>();
        props.Add("connectionString", ConfigurationManager.AppSettings["connectionString"]);

        DBUtils dbUtils = new DBUtils(props);
        
        IValidator<Artist> artistValidator = new ArtistValidator();
        IValidator<Employee> employeeValidator = new EmployeeValidator();
        IValidator<Concert> concertValidator = new ConcertValidator();
        IValidator<Ticket> ticketValidator = new TicketValidator();
        
        IArtistRepository artistRepository = new ArtistRepository(dbUtils, artistValidator);
        IEmployeeRepository employeeRepository = new EmployeeRepository(dbUtils, employeeValidator);
        IConcertRepository concertRepository = new ConcertRepository(dbUtils, concertValidator);
        ITicketRepository ticketRepository = new TicketRepository(dbUtils, ticketValidator);

        
    }
}