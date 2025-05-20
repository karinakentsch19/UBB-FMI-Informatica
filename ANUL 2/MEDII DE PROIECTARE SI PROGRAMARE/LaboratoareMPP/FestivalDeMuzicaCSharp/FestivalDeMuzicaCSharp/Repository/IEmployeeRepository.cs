using FestivalDeMuzicaCSharp.Domain;

namespace FestivalDeMuzicaCSharp.Repository;

public interface IEmployeeRepository : ICrudRepository<long, Employee>
{
    Employee? findEmployeeByUsernameAndPassword(string username, string password);
}