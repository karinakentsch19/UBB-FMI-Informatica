package ubb.ro.socialnetworkgui.Repository;

import ubb.ro.socialnetworkgui.Domain.Entity;
import ubb.ro.socialnetworkgui.Exceptions.ExistingEntityException;
import ubb.ro.socialnetworkgui.Exceptions.InexistingEntityException;
import ubb.ro.socialnetworkgui.Validator.AbstractValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class InMemoryRepository<ID, E extends Entity<ID>> implements AbstractRepository<ID, E>{
    Map<ID,E> entityList;
    AbstractValidator<E> validator;


    public InMemoryRepository(AbstractValidator<E> validator)  {
        this.entityList = new HashMap<>();
        this.validator = validator;
    }

    /**
     * Add an entity
     *
     * @param entity E
     * @return
     * @throws IllegalArgumentException if the entity is null
     * @throws ExistingEntityException  if the entity already exists among enitites
     */
    @Override
    public Optional<E> add(E entity) {
        //element -> element == null
        Predicate<E> entityIsNull = Objects::isNull;

//        if (entity == null)
        if (entityIsNull.test(entity))
            throw new IllegalArgumentException("Argument can't be null\n");
        validator.validate(entity);


//        if (entityList.get(entity.getId()) != null)
        if (!entityIsNull.test(entityList.get(entity.getId()))) {
//            throw new ExistingEntityException("Entity exists already\n");
            return Optional.of(entityList.get(entity.getId()));
        }
        entityList.put(entity.getId(), entity);
        return Optional.empty();
    }

    /**
     * Remove an entity
     *
     * @param id ID
     * @return the removed entity
     * @throws IllegalArgumentException  if the id is null
     * @throws InexistingEntityException if the entity with the given id doesn't exist
     */
    @Override
    public Optional<E> remove(ID id) {
        Predicate<ID> idIsNull = Id -> Id == null;
        Predicate<E> entityIsNull = Objects::isNull;

        if (idIsNull.test(id))
            throw new IllegalArgumentException("Argument can't be null\n");
        E entity = entityList.get(id);
        if (entityIsNull.test(entity)) {
//            throw new InexistingEntityException("Entity doesn't exist\n");
            return Optional.empty();
        }
        entityList.remove(id);
        return Optional.of(entity);
    }

    /**
     * Updates an entity
     *
     * @param entity E
     * @return
     * @throws IllegalArgumentException  if the entity is null
     * @throws InexistingEntityException if the entity doesn't exist
     */
    @Override
    public Optional<E> update(E entity) {
        Predicate<E> entityIsNull = Objects::isNull;

        if (entityIsNull.test(entity))
            throw new IllegalArgumentException("Argument can't be null\n");
        validator.validate(entity);
        if (entityIsNull.test(entityList.get(entity.getId()))) {
//            throw new InexistingEntityException("Entity doesn't exist\n");
            return Optional.of(entity);
        }
        entityList.put(entity.getId(), entity);
        return Optional.empty();
    }

    /**
     * Gets all of the entities
     * @return entities
     */
    @Override
    public Iterable<E> getAll() {
        return entityList.values();
    }

    /**
     * Searches for an entity with the given id
     *
     * @param id ID
     * @return an entity
     * @throws IllegalArgumentException  if the id is null
     * @throws InexistingEntityException if the entity with the given id doesn't exist
     */
    @Override
    public Optional<E> find(ID id) {
        Predicate<ID> idIsNull = Objects::isNull;
        Predicate<E> entityIsNull = Objects::isNull;

        if (idIsNull.test(id))
            throw new IllegalArgumentException("Argument can't be null\n");
        if (entityIsNull.test(entityList.get(id))) {
//            throw new InexistingEntityException("Entity doesn't exist\n");
            return Optional.empty();
        }
        return Optional.of(entityList.get(id));
    }

    /**
     * Return the size of the entity list
     * @return size of entity list
     */
    @Override
    public Long size() {
        Function<Integer, Long> IntegerToLong = Long::valueOf;
        return IntegerToLong.apply(entityList.size());
    }
}
