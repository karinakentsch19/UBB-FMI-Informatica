package com.example.zboruri.Repository.Paging;

import com.example.zboruri.Domain.Entity;
import com.example.zboruri.Repository.AbstractRepository;

public interface PagingRepository<ID, E extends Entity<ID>> extends AbstractRepository<ID, E> {
    Page<E> findAllOnPage(Pageable pageable);
}
