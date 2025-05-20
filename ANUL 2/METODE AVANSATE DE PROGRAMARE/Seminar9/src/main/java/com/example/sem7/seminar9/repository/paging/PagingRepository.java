package com.example.sem7.seminar9.repository.paging;

import com.example.sem7.seminar9.domain.Entity;
import com.example.sem7.seminar9.repository.Repository;

public interface PagingRepository<ID, E extends Entity<ID>> extends Repository<ID, E> {
    Page<E> findAllOnPage(Pageable pageable);
}
