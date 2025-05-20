using FestivalDeMuzicaCSharp.Domain;

namespace FestivalDeMuzicaCSharp.Repository;

public interface IArtistRepository : ICrudRepository<long, Artist>
{
    
}