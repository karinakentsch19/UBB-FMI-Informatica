namespace FestivalDeMuzicaCSharp.Domain;

public class Ticket : Entity<long>
{
    public Employee Employee { get; set; }

    public Concert Concert { get; set; }

    public string ClientName { get; set; }

    public DateTime PurchaseTime { get; set; }

    public Ticket(Employee employee, Concert concert, string clientName, DateTime purchaseTime)
    {
        Employee = employee;
        Concert = concert;
        ClientName = clientName;
        PurchaseTime = purchaseTime;
    }

    protected bool Equals(Ticket other)
    {
        return Employee.Equals(other.Employee) && Concert.Equals(other.Concert) && ClientName == other.ClientName && PurchaseTime.Equals(other.PurchaseTime);
    }

    public override bool Equals(object? obj)
    {
        if (ReferenceEquals(null, obj)) return false;
        if (ReferenceEquals(this, obj)) return true;
        if (obj.GetType() != this.GetType()) return false;
        return Equals((Ticket)obj);
    }

    public override int GetHashCode()
    {
        return HashCode.Combine(Employee, Concert, ClientName, PurchaseTime);
    }

    public override string ToString()
    {
        return $"{nameof(Employee)}: {Employee}, {nameof(Concert)}: {Concert}, {nameof(ClientName)}: {ClientName}, {nameof(PurchaseTime)}: {PurchaseTime}";
    }
}