using System.Data;
using System.Data.SqlClient;
using FestivalDeMuzicaCSharp.Domain;
using FestivalDeMuzicaCSharp.Utils;
using FestivalDeMuzicaCSharp.Validation;
using log4net;
using log4net.Repository.Hierarchy;

namespace FestivalDeMuzicaCSharp.Repository;

public class ArtistRepository : IArtistRepository
{
    private DBUtils dbUtils;
    
    private IValidator<Artist> validator;

    private static readonly log4net.ILog logger = LogManager.GetLogger(typeof(ArtistRepository));

    public ArtistRepository(DBUtils dbUtils, IValidator<Artist> validator)
    {
        this.dbUtils = dbUtils;
        this.validator = validator;
    }

    public Artist? Add(Artist entity)
    {
        logger.InfoFormat("saving task {0}", entity);
        IDbConnection connection = dbUtils.getConnection();

        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "insert into Artists(cnp, firstname, lastname, birthdate) values (@cnp, @firstname, @lastname, @birthdate)";
            try
            {
                validator.Validate(entity);
                IDbDataParameter cnpParameter = command.CreateParameter();
                cnpParameter.ParameterName = "@cnp";
                cnpParameter.Value = entity.Cnp;
                command.Parameters.Add(cnpParameter);

                IDbDataParameter firstnameParameter = command.CreateParameter();
                firstnameParameter.ParameterName = "@firstname";
                firstnameParameter.Value = entity.FirstName;
                command.Parameters.Add(firstnameParameter);

                IDbDataParameter lastnameParameter = command.CreateParameter();
                lastnameParameter.ParameterName = "@lastname";
                lastnameParameter.Value = entity.LastName;
                command.Parameters.Add(lastnameParameter);

                IDbDataParameter birthdateParameter = command.CreateParameter();
                birthdateParameter.ParameterName = "@birthdate";
                DateOnly birthdate = entity.BirthDate;
                DateTime date = birthdate.ToDateTime(TimeOnly.MinValue);
                birthdateParameter.Value = date;
                command.Parameters.Add(birthdateParameter);

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

    public Artist? Delete(long id)
    {
        logger.InfoFormat("deleting task with id {0}", id);
        IDbConnection connection = dbUtils.getConnection();

        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "delete from Artists where artistId = @artistId";
            try
            {
                IDbDataParameter artistIdParameter = command.CreateParameter();
                artistIdParameter.ParameterName = "@artistId";
                artistIdParameter.Value = id;
                command.Parameters.Add(artistIdParameter);

                Artist artist = find(id);
                int rows = command.ExecuteNonQuery();
                logger.InfoFormat("Deleted {0} instance", rows);
                if (rows != 0)
                    return artist;
            }
            catch (SqlException e)
            {
                logger.Error(e);
                Console.Error.Write("Error DB" + e);
            }
        }

        return null;
    }

    public Artist? Update(Artist entity)
    {
        logger.InfoFormat("update task {0}", entity);
        IDbConnection connection = dbUtils.getConnection();

        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "update Artists set cnp = @cnp, firstname = @firstname, lastname = @lastname, birthdate = @birthdate where artistId = @artistId";
            try
            {
                validator.Validate(entity);
                IDbDataParameter cnpParameter = command.CreateParameter();
                cnpParameter.ParameterName = "@cnp";
                cnpParameter.Value = entity.Cnp;
                command.Parameters.Add(cnpParameter);

                IDbDataParameter firstnameParameter = command.CreateParameter();
                firstnameParameter.ParameterName = "@firstname";
                firstnameParameter.Value = entity.FirstName;
                command.Parameters.Add(firstnameParameter);

                IDbDataParameter lastnameParameter = command.CreateParameter();
                lastnameParameter.ParameterName = "@lastname";
                lastnameParameter.Value = entity.LastName;
                command.Parameters.Add(lastnameParameter);

                IDbDataParameter birthdateParameter = command.CreateParameter();
                birthdateParameter.ParameterName = "@birthdate";
                DateOnly birthdate = entity.BirthDate;
                DateTime date = birthdate.ToDateTime(TimeOnly.MinValue);
                birthdateParameter.Value = date;
                command.Parameters.Add(birthdateParameter);

                IDbDataParameter artistIdParameter = command.CreateParameter();
                artistIdParameter.ParameterName = "@artistId";
                artistIdParameter.Value = entity.Id;
                command.Parameters.Add(artistIdParameter);

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

    public Artist? find(long id)
    {
        IDbConnection connection = dbUtils.getConnection();
        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "select * from Artists where artistId = @artistId";
            try
            {
                IDbDataParameter artistIdParameter = command.CreateParameter();
                artistIdParameter.ParameterName = "@artistId";
                artistIdParameter.Value = id;
                command.Parameters.Add(artistIdParameter);

                using (IDataReader dataReader = command.ExecuteReader())
                {
                    if (dataReader.Read())
                    {
                        long artistId = dataReader.GetInt64(0);
                        long cnp = dataReader.GetInt64(1);
                        string firstname = dataReader.GetString(2);
                        string lastname = dataReader.GetString(3);
                        DateOnly birthdate = DateOnly.FromDateTime(dataReader.GetDateTime(4));

                        Artist artist = new Artist(cnp, firstname, lastname, birthdate);
                        artist.Id = artistId;
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

    public IEnumerable<Artist> GetAll()
    {
        List<Artist> artists = new List<Artist>();
        IDbConnection connection = dbUtils.getConnection();
        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "select * from Artists";
            try
            {
                using (IDataReader dataReader = command.ExecuteReader())
                {
                    while (dataReader.Read())
                    {
                        long artistId = dataReader.GetInt64(0);
                        long cnp = dataReader.GetInt64(1);
                        string firstname = dataReader.GetString(2);
                        string lastname = dataReader.GetString(3);
                        DateOnly birthdate = DateOnly.FromDateTime(dataReader.GetDateTime(4));

                        Artist artist = new Artist(cnp, firstname, lastname, birthdate);
                        artist.Id = artistId;
                        artists.Add(artist);
                    }
                }
            }
            catch (SqlException e)
            {
                logger.Error(e);
                Console.Error.Write("Error DB" + e);
            }
        }
        return artists;
    }

    public long Size()
    {
        IDbConnection connection = dbUtils.getConnection();
        long numberOfArtists = 0;
        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "select count(*) from Artists";
            try
            {
                using (IDataReader dataReader = command.ExecuteReader())
                {
                    if (dataReader.Read())
                        numberOfArtists = dataReader.GetInt64(0);
                }
            }
            catch (SqlException e)
            {
                logger.Error(e);
                Console.Error.Write("Error DB" + e);
            }
        }

        return numberOfArtists;
    }
}