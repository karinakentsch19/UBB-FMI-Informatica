package com.example.sem7.seminar9.repository;

import com.example.sem7.seminar9.DTO.MovieFilterDTO;
import com.example.sem7.seminar9.domain.Movie;
import com.example.sem7.seminar9.repository.paging.Page;
import com.example.sem7.seminar9.repository.paging.Pageable;
import com.example.sem7.seminar9.repository.paging.PagingRepository;

import java.util.List;

public interface MovieRepository extends PagingRepository<Long, Movie> {

    List<Integer> getYears();

    Page<Movie> findAllFilter(Pageable pageable, MovieFilterDTO filter);
}
