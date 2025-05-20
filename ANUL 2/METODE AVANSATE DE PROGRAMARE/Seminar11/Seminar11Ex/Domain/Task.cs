namespace Seminar11Ex.Domain;

public enum Complexity
{
    Low, Medium, High
}

public class Task : Entity<string>
{
    public Complexity Complexity{
        get;
        set;
    }

    public int EstimatedHours
    {
        get;
        set;
    }

    public override string ToString()
    {
        return Id + ' ' + Complexity + ' ' + EstimatedHours;
    }
}