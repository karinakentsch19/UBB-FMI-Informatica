using FestivalDeMuzicaCSharp.Business;
using FestivalDeMuzicaCSharp.Domain;
using FestivalDeMuzicaCSharp.Repository;
using FestivalDeMuzicaCSharp.Utils;
using FestivalDeMuzicaCSharp.Validation;
using log4net.Config;
using System.Configuration;

namespace FestivalDeMuzicaCSharpVarianta2
{
    internal static class Program
    {
        /// <summary>
        ///  The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            // To customize application configuration such as set high DPI settings or default font,
            // see https://aka.ms/applicationconfiguration.
            ApplicationConfiguration.Initialize();

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

            IService service = new Service(artistRepository, concertRepository, employeeRepository, ticketRepository);

            Application.Run(new Form1(service));
        }
    }
}