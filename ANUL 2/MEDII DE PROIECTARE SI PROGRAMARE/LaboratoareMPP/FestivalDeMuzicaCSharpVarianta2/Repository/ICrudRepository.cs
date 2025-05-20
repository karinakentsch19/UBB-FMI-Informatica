using FestivalDeMuzicaCSharp.Domain;

namespace FestivalDeMuzicaCSharp.Repository;

public interface ICrudRepository<ID, E> where E: Entity<ID>
{
    /// <summary>
    ///Adds a new entity
    /// </summary>
    /// <param name="entity"></param> an entity
    /// <returns></returns> entity if it can't be added
    E? Add(E entity);

    /// <summary>
    /// Removes an entity with the given id
    /// </summary>
    /// <param name="id"></param> ID
    /// <returns></returns> entity if it can be removed
    E? Delete(ID id);

    /// <summary>
    /// Updates the entity's attributes with the new entity's attributes which has a matching id
    /// </summary>
    /// <param name="entity"></param> an entity
    /// <returns></returns> entity if it doesn't have a match so we can update it
    E? Update(E entity);

    /// <summary>
    /// Searches for an entity with a given id
    /// </summary>
    /// <param name="id"></param> ID
    /// <returns></returns> entity if found
    E? find(ID id);
    
    /// <summary>
    /// Returns all entities
    /// </summary>
    /// <returns></returns> entities
    IEnumerable<E> GetAll();

    /// <summary>
    /// Return the number of entitie
    /// </summary>
    /// <returns></returns> number of entities
    long Size();
}