using System.Data;
using System.Data.SqlClient;
using FestivalDeMuzicaCSharp.Domain;
using FestivalDeMuzicaCSharp.Utils;
using FestivalDeMuzicaCSharp.Validation;
using log4net;

namespace FestivalDeMuzicaCSharp.Repository;

public class EmployeeRepository : IEmployeeRepository
{
    private DBUtils dbUtils;
    
    private IValidator<Employee> validator;

    private static readonly log4net.ILog logger = LogManager.GetLogger(typeof(ArtistRepository));

    public EmployeeRepository(DBUtils dbUtils, IValidator<Employee> validator)
    {
        this.dbUtils = dbUtils;
        this.validator = validator;
    }

    public Employee? Add(Employee entity)
    {
        logger.InfoFormat("saving task {0}", entity);
        IDbConnection connection = dbUtils.getConnection();

        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "insert into Employees(cnp, firstname, lastname, birthdate, username, password) values (@cnp, @firstname, @lastname, @birthdate, @username, @password)";
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

                IDbDataParameter usernameParameter = command.CreateParameter();
                usernameParameter.ParameterName = "@username";
                usernameParameter.Value = entity.Username;
                command.Parameters.Add(usernameParameter);

                IDbDataParameter passwordParameter = command.CreateParameter();
                passwordParameter.ParameterName = "@password";
                passwordParameter.Value = entity.Password;
                command.Parameters.Add(passwordParameter);

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

    public Employee? Delete(long id)
    {
        logger.InfoFormat("deleting task with id {0}", id);
        IDbConnection connection = dbUtils.getConnection();

        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "delete from Employees where employeeId = ?";
            try
            {
                IDbDataParameter employeeIdParameter = command.CreateParameter();
                employeeIdParameter.ParameterName = "@employeeId";
                employeeIdParameter.Value = id;
                command.Parameters.Add(employeeIdParameter);

                Employee employee = find(id);
                int rows = command.ExecuteNonQuery();
                logger.InfoFormat("Deleted {0} instance", rows);
                if (rows != 0)
                    return employee;
            }
            catch (SqlException e)
            {
                logger.Error(e);
                Console.Error.Write("Error DB" + e);
            }
        }

        return null;
    }

    public Employee? Update(Employee entity)
    {
        logger.InfoFormat("update task {0}", entity);
        IDbConnection connection = dbUtils.getConnection();

        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "update Employees set cnp = @cnp, firstname = @firstname , lastname = @lastname, birthdate = @birthdate, username = @username, password = @password where employeeId = @employeeId";
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

                IDbDataParameter usernameParameter = command.CreateParameter();
                usernameParameter.ParameterName = "@username";
                usernameParameter.Value = entity.Username;
                command.Parameters.Add(usernameParameter);

                IDbDataParameter passwordParameter = command.CreateParameter();
                passwordParameter.ParameterName = "@password";
                passwordParameter.Value = entity.Password;
                command.Parameters.Add(passwordParameter);

                IDbDataParameter employeeIdParameter = command.CreateParameter();
                employeeIdParameter.ParameterName = "@employeeId";
                employeeIdParameter.Value = entity.Id;
                command.Parameters.Add(employeeIdParameter);

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

    public Employee? find(long id)
    {
        IDbConnection connection = dbUtils.getConnection();
        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "select * from Employees where employeeId = ?";
            try
            {
                IDbDataParameter employeeIdParameter = command.CreateParameter();
                employeeIdParameter.ParameterName = "@employeeId";
                employeeIdParameter.Value = id;
                command.Parameters.Add(employeeIdParameter);

                using (IDataReader dataReader = command.ExecuteReader())
                {
                    if (dataReader.Read())
                    {
                        long employeeId = dataReader.GetInt64(0);
                        long cnp = dataReader.GetInt64(1);
                        string firstname = dataReader.GetString(2);
                        string lastname = dataReader.GetString(3);
                        string username = dataReader.GetString(4);
                        string password = dataReader.GetString(5);
                        DateOnly birthdate = DateOnly.FromDateTime(dataReader.GetDateTime(6));

                        Employee employee = new Employee(cnp, firstname, lastname, birthdate, username, password);
                        employee.Id = employeeId;
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

    public IEnumerable<Employee> GetAll()
    {
        List<Employee> employees = new List<Employee>();
        IDbConnection connection = dbUtils.getConnection();
        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "select * from Employees";
            try
            {
                using (IDataReader dataReader = command.ExecuteReader())
                {
                    while (dataReader.Read())
                    {
                        long employeeId = dataReader.GetInt64(0);
                        long cnp = dataReader.GetInt64(1);
                        string firstname = dataReader.GetString(2);
                        string lastname = dataReader.GetString(3);
                        string username = dataReader.GetString(4);
                        string password = dataReader.GetString(5);
                        DateOnly birthdate = DateOnly.FromDateTime(dataReader.GetDateTime(6));

                        Employee employee = new Employee(cnp, firstname, lastname, birthdate, username, password);
                        employee.Id = employeeId;
                        employees.Add(employee);
                    }
                }
            }
            catch (SqlException e)
            {
                logger.Error(e);
                Console.Error.Write("Error DB" + e);
            }
        }
        return employees;
    }

    public long Size()
    {
        IDbConnection connection = dbUtils.getConnection();
        long numberOfEmployees = 0;
        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "select count(*) from Employees";
            try
            {
                using (IDataReader dataReader = command.ExecuteReader())
                {
                    if (dataReader.Read())
                        numberOfEmployees = dataReader.GetInt64(0);
                }
            }
            catch (SqlException e)
            {
                logger.Error(e);
                Console.Error.Write("Error DB" + e);
            }
        }

        return numberOfEmployees;
    }

    public Employee? findEmployeeByUsernameAndPassword(string username, string password)
    {
        IDbConnection connection = dbUtils.getConnection();
        using (IDbCommand command = connection.CreateCommand())
        {
            command.CommandText = "select * from Employees where username = ? and password = ?";
            try
            {
                IDbDataParameter usernameParameter = command.CreateParameter();
                usernameParameter.ParameterName = "@username";
                usernameParameter.Value = username;
                command.Parameters.Add(usernameParameter);

                IDbDataParameter passwordParameter = command.CreateParameter();
                passwordParameter.ParameterName = "@password";
                passwordParameter.Value = password;
                command.Parameters.Add(passwordParameter);

                using (IDataReader dataReader = command.ExecuteReader())
                {
                    if (dataReader.Read())
                    {
                        long employeeId = dataReader.GetInt64(0);
                        long cnp = dataReader.GetInt64(1);
                        string firstname = dataReader.GetString(2);
                        string lastname = dataReader.GetString(3);
                        string username1 = dataReader.GetString(4);
                        string password1 = dataReader.GetString(5);
                        DateOnly birthdate = DateOnly.FromDateTime(dataReader.GetDateTime(6));

                        Employee employee = new Employee(cnp, firstname, lastname, birthdate, username1, password1);
                        employee.Id = employeeId;
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
}