namespace Seminar11Ex.Domain;

public class TaskAssignment : Entity<Tuple<string,string>>
{
    public Employee Employee
    {
        get;
        set;
    }

    public Task Task
    {
        get;
        set;
    }

    public DateTime Date
    {
        get;
        set;
    }

    public override string ToString()
    {
        return $"{Employee} {Task} {Date:d/M/yyyy}";
    }
}