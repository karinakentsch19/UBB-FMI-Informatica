namespace Seminar11Ex.Domain;

public enum KnowledgeLevel{Junior, Medium, Senior}

public class Employee: Entity<string>
{
    public string Name { get; set; }
    public double RatePerHour { get; set; }
    public KnowledgeLevel KnowledgeLevel { get; set; }

    public override string ToString()
    {
        return Id + "," + Name + "," + RatePerHour + "," + KnowledgeLevel;
    }
}