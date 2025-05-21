package com.example.faptebune.Repository;

import com.example.faptebune.Domain.Entity;

import java.util.Optional;

public interface AbstractRepository<ID, E extends Entity<ID>> {
    public Optional<E> add(E entity);

    public Optional<E> remove(ID id);
    public Optional<E> update(E entity);

    public Iterable<E> getAll();

    public
    Optional<E> find(ID id);

    public Long size();
}
