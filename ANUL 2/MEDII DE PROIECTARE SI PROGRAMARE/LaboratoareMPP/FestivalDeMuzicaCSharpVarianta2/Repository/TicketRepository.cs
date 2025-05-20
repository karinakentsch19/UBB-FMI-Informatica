using System.Data;
using System.Data.SqlClient;
using FestivalDeMuzicaCSharp.Domain;
using FestivalDeMuzicaCSharp.Utils;
using FestivalDeMuzicaCSharp.Validation;
using log4net;

namespace FestivalDeMuzicaCSharp.Repository;

public class TicketRepository : ITicketRepository
{
    private DBUtils dbUtils;

    private IValidator<Ticket> validator;

    private static readonly log4net.ILog logger = LogManager.GetLogger(typeof(ArtistRepository));

    public TicketRepository(DBUtils dbUtils, IValidator<Ticket> validator)
    {
        this.dbUtils = dbUtils;
        this.validator = validator;
    }

    public Ticket? Add(Ticket entity)
    {
        logger.InfoFormat("saving task {0}", entity);
        IDbConnection connection = dbUtils.getConnection();

        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText =
                "insert into Tickets(employeeId, concertId, clientName, purchaseDateTime) values (@employeeId, @concertId, @clientName, @purchaseDateTime)";
            try
            {
                validator.Validate(entity);

                IDbDataParameter employeeIdParameter = command.CreateParameter();
                employeeIdParameter.ParameterName = "@employeeId";
                employeeIdParameter.Value = entity.Employee.Id;
                command.Parameters.Add(employeeIdParameter);

                IDbDataParameter concertIdParameter = command.CreateParameter();
                concertIdParameter.ParameterName = "@concertId";
                concertIdParameter.Value = entity.Concert.Id;
                command.Parameters.Add(concertIdParameter);

                IDbDataParameter clientNameParameter = command.CreateParameter();
                clientNameParameter.ParameterName = "@clientName";
                clientNameParameter.Value = entity.ClientName;
                command.Parameters.Add(clientNameParameter);

                IDbDataParameter purchaseDateTimeParameter = command.CreateParameter();
                purchaseDateTimeParameter.ParameterName = "@purchaseDateTime";
                purchaseDateTimeParameter.Value = entity.PurchaseTime;
                command.Parameters.Add(purchaseDateTimeParameter);

                int rows = command.ExecuteNonQuery();
                logger.InfoFormat("Saved {0} instances", rows);
                if (rows == 0)
                    return entity;
            }
            catch (SqlException e)
            {
                logger.Error(e);
                Console.Error.Write("Error DB" + e);
            }
        }

        return null;
    }

    public Ticket? Delete(long id)
    {
        logger.InfoFormat("deleting task with id {0}", id);
        IDbConnection connection = dbUtils.getConnection();

        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "delete from Tickets where ticketId = @ticketId";
            try
            {
                IDbDataParameter ticketIdParameter = command.CreateParameter();
                ticketIdParameter.ParameterName = "@ticketId";
                ticketIdParameter.Value = id;
                command.Parameters.Add(ticketIdParameter);

                Ticket ticket = find(id);
                int rows = command.ExecuteNonQuery();
                logger.InfoFormat("Deleted {0} instance", rows);
                if (rows != 0)
                    return ticket;
            }
            catch (SqlException e)
            {
                logger.Error(e);
                Console.Error.Write("Error DB" + e);
            }
        }

        return null;
    }

    public Ticket? Update(Ticket entity)
    {
        logger.InfoFormat("update task {0}", entity);
        IDbConnection connection = dbUtils.getConnection();

        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText =
                "update Tickets set employeeId = @employeeId, concertId = @concertId, clientName = @clientName, purchaseDateTime = @purchaseDateTime where ticketId = @ticketId";
            try
            {
                validator.Validate(entity);

                IDbDataParameter employeeIdParameter = command.CreateParameter();
                employeeIdParameter.ParameterName = "@employeeId";
                employeeIdParameter.Value = entity.Employee.Id;
                command.Parameters.Add(employeeIdParameter);

                IDbDataParameter concertIdParameter = command.CreateParameter();
                concertIdParameter.ParameterName = "@concertId";
                concertIdParameter.Value = entity.Concert.Id;
                command.Parameters.Add(concertIdParameter);

                IDbDataParameter clientNameParameter = command.CreateParameter();
                clientNameParameter.ParameterName = "@clientName";
                clientNameParameter.Value = entity.ClientName;
                command.Parameters.Add(clientNameParameter);

                IDbDataParameter purchaseDateTimeParameter = command.CreateParameter();
                purchaseDateTimeParameter.ParameterName = "@purchaseDateTime";
                purchaseDateTimeParameter.Value = entity.PurchaseTime;
                command.Parameters.Add(purchaseDateTimeParameter);

                IDbDataParameter ticketIdParameter = command.CreateParameter();
                ticketIdParameter.ParameterName = "@ticketId";
                ticketIdParameter.Value = entity.Id;
                command.Parameters.Add(ticketIdParameter);

                int rows = command.ExecuteNonQuery();
                logger.InfoFormat("Updated {0} instances", rows);
                if (rows == 0)
                    return entity;
            }
            catch (SqlException e)
            {
                logger.Error(e);
                Console.Error.Write("Error DB" + e);
            }
        }

        return null;
    }

    public Ticket? find(long id)
    {
        IDbConnection connection = dbUtils.getConnection();
        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "select * from Tickets where ticketId = @ticketId";
            try
            {
                IDbDataParameter ticketIdParameter = command.CreateParameter();
                ticketIdParameter.ParameterName = "@ticketId";
                ticketIdParameter.Value = id;
                command.Parameters.Add(ticketIdParameter);

                using (IDataReader dataReader = command.ExecuteReader())
                {
                    if (dataReader.Read())
                    {
                        long ticketId = dataReader.GetInt64(0);
                        long employeeId = dataReader.GetInt64(1);
                        long concertId = dataReader.GetInt64(2);
                        string clientName = dataReader.GetString(3);
                        DateTime purchaseDateTime = dataReader.GetDateTime(4);
                        Employee employee = findEmployeeById(employeeId)!;
                        Concert concert = findConcertId(concertId)!;
                        Ticket ticket = new Ticket(employee, concert, clientName, purchaseDateTime);
                        ticket.Id = ticketId;
                        return ticket;

                    }
                }
            }
            catch (SqlException e)
            {
                logger.Error(e);
                Console.Error.Write("Error DB" + e);
            }
        }
        return null;
    }

    public IEnumerable<Ticket> GetAll()
    {
        List<Ticket> tickets = new List<Ticket>();
        IDbConnection connection = dbUtils.getConnection();
        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "select * from Tickets";
            try
            {
                using (IDataReader dataReader = command.ExecuteReader())
                {
                    while (dataReader.Read())
                    {
                        long ticketId = dataReader.GetInt64(0);
                        long employeeId = dataReader.GetInt64(1);
                        long concertId = dataReader.GetInt64(2);
                        string clientName = dataReader.GetString(3);
                        DateTime purchaseDateTime = dataReader.GetDateTime(4);
                        Employee employee = findEmployeeById(employeeId)!;
                        Concert concert = findConcertId(concertId)!;
                        Ticket ticket = new Ticket(employee, concert, clientName, purchaseDateTime);
                        ticket.Id = ticketId;
                        tickets.Add(ticket);
                    }
                }
            }
            catch (SqlException e)
            {
                logger.Error(e);
                Console.Error.Write("Error DB" + e);
            }
        }
        return tickets;
    }

    public long Size()
    {
        IDbConnection connection = dbUtils.getConnection();
        long numberOfTickets = 0;
        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "select count(*) from Tickets";
            try
            {
                using (IDataReader dataReader = command.ExecuteReader())
                {
                    if (dataReader.Read())
                        numberOfTickets = dataReader.GetInt64(0);
                }
            }
            catch (SqlException e)
            {
                logger.Error(e);
                Console.Error.Write("Error DB" + e);
            }
        }

        return numberOfTickets;
    }

    public Employee? findEmployeeById(long employeeId)
    {
        IDbConnection connection = dbUtils.getConnection();
        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "select * from Employees where employeeId = ?";
            try
            {
                IDbDataParameter employeeIdParameter = command.CreateParameter();
                employeeIdParameter.ParameterName = "@employeeId";
                employeeIdParameter.Value = employeeId;
                command.Parameters.Add(employeeIdParameter);

                using (IDataReader dataReader = command.ExecuteReader())
                {
                    if (dataReader.Read())
                    {
                        long employeeId1 = dataReader.GetInt64(0);
                        long cnp = dataReader.GetInt64(1);
                        string firstname = dataReader.GetString(2);
                        string lastname = dataReader.GetString(3);
                        string username = dataReader.GetString(4);
                        string password = dataReader.GetString(5);
                        DateOnly birthdate = DateOnly.FromDateTime(dataReader.GetDateTime(6));

                        Employee employee = new Employee(cnp, firstname, lastname, birthdate, username, password);
                        employee.Id = employeeId1;
                        return employee;
                    }
                }
            }
            catch (SqlException e)
            {
                logger.Error(e);
                Console.Error.Write("Error DB" + e);
            }
        }
        return null;
    }

    public Concert? findConcertId(long concertId)
    {
        IDbConnection connection = dbUtils.getConnection();
        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "select * from Concerts where concertId = ?";
            try
            {
                IDbDataParameter concertIdParameter = command.CreateParameter();
                concertIdParameter.ParameterName = "@concertId";
                concertIdParameter.Value = concertId;
                command.Parameters.Add(concertIdParameter);

                using (IDataReader dataReader = command.ExecuteReader())
                {
                    if (dataReader.Read())
                    {
                        long concertId1 = dataReader.GetInt64(0);
                        long artistId = dataReader.GetInt64(1);
                        string name = dataReader.GetString(2);
                        long noSeats = dataReader.GetInt64(3);
                        TimeOnly startTime = TimeOnly.Parse(dataReader.GetString(4));
                        TimeOnly endTime = TimeOnly.Parse(dataReader.GetString(5));
                        string address = dataReader.GetString(6);
                        DateOnly date = DateOnly.FromDateTime(dataReader.GetDateTime(7));
                        Artist artist = findArtistById(artistId)!;
                        Concert concert = new Concert(artist, name, noSeats, date, startTime, endTime, address);
                        concert.Id = concertId1;
                        return concert;
                    }
                }
            }
            catch (SqlException e)
            {
                logger.Error(e);
                Console.Error.Write("Error DB" + e);
            }
        }
        return null;
    }

    public Artist? findArtistById(long artistId)
    {
        IDbConnection connection = dbUtils.getConnection();
        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "select * from Artists where artistId = @artistId";
            try
            {
                IDbDataParameter artistIdParameter = command.CreateParameter();
                artistIdParameter.ParameterName = "@artistId";
                artistIdParameter.Value = artistId;
                command.Parameters.Add(artistIdParameter);

                using (IDataReader dataReader = command.ExecuteReader())
                {
                    if (dataReader.Read())
                    {
                        long artistId1 = dataReader.GetInt64(0);
                        long cnp = dataReader.GetInt64(1);
                        string firstname = dataReader.GetString(2);
                        string lastname = dataReader.GetString(3);
                        DateOnly birthdate = DateOnly.FromDateTime(dataReader.GetDateTime(4));

                        Artist artist = new Artist(cnp, firstname, lastname, birthdate);
                        artist.Id = artistId1;
                        return artist;
                    }
                }
            }
            catch (SqlException e)
            {
                logger.Error(e);
                Console.Error.Write("Error DB" + e);
            }
        }
        return null;
    }
}