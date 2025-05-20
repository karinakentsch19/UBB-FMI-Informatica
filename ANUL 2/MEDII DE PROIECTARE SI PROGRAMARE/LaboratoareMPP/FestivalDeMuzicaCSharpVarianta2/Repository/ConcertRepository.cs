using System.Data;
using System.Data.SqlClient;
using FestivalDeMuzicaCSharp.Domain;
using FestivalDeMuzicaCSharp.Utils;
using FestivalDeMuzicaCSharp.Validation;
using log4net;

namespace FestivalDeMuzicaCSharp.Repository;

public class ConcertRepository : IConcertRepository
{
    private DBUtils dbUtils;
    
    private IValidator<Concert> validator;

    private static readonly log4net.ILog logger = LogManager.GetLogger(typeof(ArtistRepository));

    public ConcertRepository(DBUtils dbUtils, IValidator<Concert> validator)
    {
        this.dbUtils = dbUtils;
        this.validator = validator;
    }

    public Concert? Add(Concert entity)
    {
        logger.InfoFormat("saving task {0}", entity);
        IDbConnection connection = dbUtils.getConnection();

        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText =
                "insert into Concerts(artistId, name, noSeats, date, startTime, endTime, address) values (@artistId, @name, @noSeats, @date, @startTime, @endTime, @address)";
            try
            {
                validator.Validate(entity);
                IDbDataParameter artistIdParameter = command.CreateParameter();
                artistIdParameter.ParameterName = "@artistId";
                artistIdParameter.Value = entity.Artist.Id;
                command.Parameters.Add(artistIdParameter);

                IDbDataParameter nameParameter = command.CreateParameter();
                nameParameter.ParameterName = "@name";
                nameParameter.Value = entity.Name;
                command.Parameters.Add(nameParameter);

                IDbDataParameter noSeatsParameter = command.CreateParameter();
                noSeatsParameter.ParameterName = "@noSeats";
                noSeatsParameter.Value = entity.NumberOfSeats;
                command.Parameters.Add(noSeatsParameter);

                IDbDataParameter dateParameter = command.CreateParameter();
                dateParameter.ParameterName = "@date";
                DateOnly date = entity.Date;
                DateTime dateTime = date.ToDateTime(TimeOnly.MinValue);
                dateParameter.Value = dateTime;
                command.Parameters.Add(dateParameter);

                IDbDataParameter startTimeParameter = command.CreateParameter();
                startTimeParameter.ParameterName = "@startTime";
                startTimeParameter.Value = entity.StartTime.ToString("HH:mm:ss");
                command.Parameters.Add(startTimeParameter);                
                
                IDbDataParameter endTimeParameter = command.CreateParameter();
                endTimeParameter.ParameterName = "@endTime";
                endTimeParameter.Value = entity.EndTime.ToString("HH:mm:ss");
                command.Parameters.Add(endTimeParameter);

                IDbDataParameter addressParameter = command.CreateParameter();
                addressParameter.ParameterName = "@address";
                addressParameter.Value = entity.Address;
                command.Parameters.Add(addressParameter);
                
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

    public Concert? Delete(long id)
    {
        logger.InfoFormat("deleting task with id {0}", id);
        IDbConnection connection = dbUtils.getConnection();

        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "delete from Concerts where concertId = @concertId";
            try
            {
                IDbDataParameter concertIdParameter = command.CreateParameter();
                concertIdParameter.ParameterName = "@concertId";
                concertIdParameter.Value = id;
                command.Parameters.Add(concertIdParameter);

                Concert concert = find(id);
                int rows = command.ExecuteNonQuery();
                logger.InfoFormat("Deleted {0} instance", rows);
                if (rows != 0)
                    return concert;
            }
            catch (SqlException e)
            {
                logger.Error(e);
                Console.Error.Write("Error DB" + e);
            }
        }

        return null;
    }

    public Concert? Update(Concert entity)
    {
        logger.InfoFormat("update task {0}", entity);
        IDbConnection connection = dbUtils.getConnection();

        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "update Concerts set artistId = @artistId, name = @name, noSeats = @noSeats, date = @date, startTime = @startTime, endTime = @endTime, address = @address where concertId = @concertId";
            try
            {
                validator.Validate(entity);
                IDbDataParameter artistIdParameter = command.CreateParameter();
                artistIdParameter.ParameterName = "@artistId";
                artistIdParameter.Value = entity.Artist.Id;
                command.Parameters.Add(artistIdParameter);

                IDbDataParameter nameParameter = command.CreateParameter();
                nameParameter.ParameterName = "@name";
                nameParameter.Value = entity.Name;
                command.Parameters.Add(nameParameter);

                IDbDataParameter noSeatsParameter = command.CreateParameter();
                noSeatsParameter.ParameterName = "@noSeats";
                noSeatsParameter.Value = entity.NumberOfSeats;
                command.Parameters.Add(noSeatsParameter);

                IDbDataParameter dateParameter = command.CreateParameter();
                dateParameter.ParameterName = "@date";
                DateOnly date = entity.Date;
                DateTime dateTime = date.ToDateTime(TimeOnly.MinValue);
                dateParameter.Value = dateTime;
                command.Parameters.Add(dateParameter);

                IDbDataParameter startTimeParameter = command.CreateParameter();
                startTimeParameter.ParameterName = "@startTime";
                startTimeParameter.Value = entity.StartTime.ToString("HH:mm:ss");
                command.Parameters.Add(startTimeParameter);                
                
                IDbDataParameter endTimeParameter = command.CreateParameter();
                endTimeParameter.ParameterName = "@endTime";
                endTimeParameter.Value = entity.EndTime.ToString("HH:mm:ss");
                command.Parameters.Add(endTimeParameter);

                IDbDataParameter addressParameter = command.CreateParameter();
                addressParameter.ParameterName = "@address";
                addressParameter.Value = entity.Address;
                command.Parameters.Add(addressParameter);

                IDbDataParameter concertIdParameter = command.CreateParameter();
                concertIdParameter.ParameterName = "@concertId";
                concertIdParameter.Value = entity.Id;
                command.Parameters.Add(concertIdParameter);

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

    public Concert? find(long id)
    {
        IDbConnection connection = dbUtils.getConnection();
        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "select * from Concerts where concertId = ?";
            try
            {
                IDbDataParameter concertIdParameter = command.CreateParameter();
                concertIdParameter.ParameterName = "@concertId";
                concertIdParameter.Value = id;
                command.Parameters.Add(concertIdParameter);

                using (IDataReader dataReader = command.ExecuteReader())
                {
                    if (dataReader.Read())
                    {
                        long concertId = dataReader.GetInt64(0);
                        long artistId = dataReader.GetInt64(1);
                        string name = dataReader.GetString(2);
                        long noSeats = dataReader.GetInt64(3);
                        TimeOnly startTime = TimeOnly.Parse(dataReader.GetString(4));
                        TimeOnly endTime = TimeOnly.Parse(dataReader.GetString(5));
                        string address = dataReader.GetString(6);
                        DateOnly date = DateOnly.FromDateTime(dataReader.GetDateTime(7));
                        Artist artist = findArtistById(artistId)!;
                        Concert concert = new Concert(artist, name, noSeats, date, startTime, endTime, address);
                        concert.Id = concertId;
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

    public IEnumerable<Concert> GetAll()
    {
        List<Concert> concerts = new List<Concert>();
        IDbConnection connection = dbUtils.getConnection();
        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "select * from Concerts";
            try
            {
                using (IDataReader dataReader = command.ExecuteReader())
                {
                    while (dataReader.Read())
                    {
                        long concertId = dataReader.GetInt64(0);
                        long artistId = dataReader.GetInt64(1);
                        string name = dataReader.GetString(2);
                        long noSeats = dataReader.GetInt64(3);
                        TimeOnly startTime = TimeOnly.Parse(dataReader.GetString(4));
                        TimeOnly endTime = TimeOnly.Parse(dataReader.GetString(5));
                        string address = dataReader.GetString(6);
                        DateOnly date = DateOnly.FromDateTime(dataReader.GetDateTime(7));
                        Artist artist = findArtistById(artistId)!;
                        Concert concert = new Concert(artist, name, noSeats, date, startTime, endTime, address);
                        concert.Id = concertId;
                        concerts.Add(concert);
                    }
                }
            }
            catch (SqlException e)
            {
                logger.Error(e);
                Console.Error.Write("Error DB" + e);
            }
        }
        return concerts;
    }

    public long Size()
    {
        IDbConnection connection = dbUtils.getConnection();
        long numberOfConcerts = 0;
        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "select count(*) from Concerts";
            try
            {
                using (IDataReader dataReader = command.ExecuteReader())
                {
                    if (dataReader.Read())
                        numberOfConcerts = dataReader.GetInt64(0);
                }
            }
            catch (SqlException e)
            {
                logger.Error(e);
                Console.Error.Write("Error DB" + e);
            }
        }

        return numberOfConcerts;
    }

    public IEnumerable<ConcertDTO> getAllConcertData()
    {
        List<ConcertDTO> concerts = new List<ConcertDTO>();
        IDbConnection connection = dbUtils.getConnection();
        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "select C.concertId, C.name, A.firstname, A.lastname, C.date, C.address, C.noSeats from Concerts C join Artists A on C.artistId = A.artistId";
            try
            {
                using (IDataReader dataReader = command.ExecuteReader())
                {
                    while (dataReader.Read())
                    {
                        long concertId = dataReader.GetInt64(0);
                        string concertName = dataReader.GetString(1);
                        string firstname = dataReader.GetString(2);
                        string lastname = dataReader.GetString(3);
                        DateOnly date = DateOnly.FromDateTime(dataReader.GetDateTime(4));
                        string address = dataReader.GetString(5);
                        long numberOfAvailableSeats = dataReader.GetInt64(6);
                        long numberOfSoldSeats = numberOfSoldTicketsForAConcert(concertId);

                        ConcertDTO concert = new ConcertDTO(firstname, lastname, concertId, concertName, date, address, numberOfAvailableSeats,
                            numberOfSoldSeats);
                        concerts.Add(concert);
                    }
                }
            }
            catch (SqlException e)
            {
                logger.Error(e);
                Console.Error.Write("Error DB" + e);
            }
        }
        return concerts;
    }

    public IEnumerable<ConcertDayDTO> getAllConcertsByDate(DateOnly date)
    {
        List<ConcertDayDTO> concerts = new List<ConcertDayDTO>();
        IDbConnection connection = dbUtils.getConnection();
        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "select A.firstname, A.lastname, C.address, C.startTime, C.endTime, C.noSeats, C.concertId, C.name from Concerts C join Artists A on C.artistId = A.artistId where date(C.date) = @date";
            try
            {
                IDbDataParameter dateParameter = command.CreateParameter();
                dateParameter.ParameterName = "@date";
                dateParameter.Value = date.ToString("yyyy-MM-dd");
                command.Parameters.Add(dateParameter);
                using (IDataReader dataReader = command.ExecuteReader())
                {
                    while (dataReader.Read())
                    {
                        string firstname = dataReader.GetString(0);
                        string lastname = dataReader.GetString(1);
                        string address = dataReader.GetString(2);
                        TimeOnly startTime = TimeOnly.Parse(dataReader.GetString(3));
                        TimeOnly endTime = TimeOnly.Parse(dataReader.GetString(4));
                        long numberOfAvailableSeats = dataReader.GetInt64(5);
                        long concertId = dataReader.GetInt64(6);
                        string concertName = dataReader.GetString(7);

                        ConcertDayDTO concert = new ConcertDayDTO(firstname, lastname, concertId, concertName, address, startTime, endTime,
                            numberOfAvailableSeats);
                        concerts.Add(concert);
                    }
                }
            }
            catch (SqlException e)
            {
                logger.Error(e);
                Console.Error.Write("Error DB" + e);
            }
        }
        return concerts;
    }

    public long numberOfSoldTicketsForAConcert(long concertId)
    {
        IDbConnection connection = dbUtils.getConnection();
        long numberOfSoldTickets = 0;
        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "select count(*) from Tickets where concertId = ?";
            try
            {
                IDbDataParameter concertIdParameter = command.CreateParameter();
                concertIdParameter.ParameterName = "@concertId";
                concertIdParameter.Value = concertId;
                command.Parameters.Add(concertIdParameter);
                using (IDataReader dataReader = command.ExecuteReader())
                {
                    if (dataReader.Read())
                        numberOfSoldTickets = dataReader.GetInt64(0);
                }
            }
            catch (SqlException e)
            {
                logger.Error(e);
                Console.Error.Write("Error DB" + e);
            }
        }

        return numberOfSoldTickets;
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