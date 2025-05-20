namespace FestivalDeMuzicaCSharp.Validation;

public interface IValidator<E>
{
    void Validate(E entity);
}