package ro.iss.biblioteca.Repository;

import java.util.Optional;

public interface IRepository<ID, Entity> {
    /**
     * Add a new entity
     * @param entity E
     * @return entity if it can't be added
     */
    public Optional<Entity> add(Entity entity);

    /**
     * Removes an entity with the given id
     * @param id ID
     * @return entity if it can be removed
     */
    public Optional<Entity> delete(ID id);

    /**
     * Updates the entity's attributes with the new entity's attributes which has a matching id
     * @param entity E
     * @return entity if it doesn't have a match so we can update it
     */
    public Optional<Entity> update(Entity entity);

    /**
     * Searches for an entity with a given id
     * @param id ID
     * @return entity if found
     */
    public Optional<Entity> find(ID id);

    /**
     * Returns all entities
     * @return entities
     */
    public Iterable<Entity> getAll();

    /**
     * Return the number of entities
     * @return number of entities
     */
    public Long size();
}
