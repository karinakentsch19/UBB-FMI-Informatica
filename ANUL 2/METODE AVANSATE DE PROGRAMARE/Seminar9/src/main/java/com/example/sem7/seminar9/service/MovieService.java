package com.example.sem7.seminar9.service;

import com.example.sem7.seminar9.DTO.MovieFilterDTO;
import com.example.sem7.seminar9.domain.Movie;
import com.example.sem7.seminar9.observer.MovieChangeEvent;
import com.example.sem7.seminar9.observer.MovieChangeEventType;
import com.example.sem7.seminar9.observer.Observable;
import com.example.sem7.seminar9.observer.Observer;
import com.example.sem7.seminar9.repository.MovieDBRepository;
import com.example.sem7.seminar9.repository.MovieRepository;
import com.example.sem7.seminar9.repository.paging.Page;
import com.example.sem7.seminar9.repository.paging.Pageable;
import com.example.sem7.seminar9.repository.paging.PagingRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieService implements Observable<MovieChangeEvent> {
//    private Repository<Long, Movie> movieRepository;

    private MovieRepository movieRepository;
    private List<Observer<MovieChangeEvent>> observers = new ArrayList<>();

    public Page<Movie> findAllOnPage(Pageable pageable) {
        return movieRepository.findAllOnPage(pageable);
    }

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Optional<Movie> findOne(Long id) {
        return movieRepository.findOne(id);
    }

    public Iterable<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Optional<Movie> delete(Long id) {
        Optional<Movie> opt = movieRepository.delete(id);

        opt.ifPresent(movie -> notifyAll(new MovieChangeEvent(MovieChangeEventType.DELETE, movie, null)));

        return opt;
    }

    public Optional<Movie> save(Movie movie) {
        Optional<Movie> opt = movieRepository.save(movie);

        if (opt.isEmpty())
            notifyAll(new MovieChangeEvent(MovieChangeEventType.ADD, null, movie));

        return opt;
    }

    public Optional<Movie> update(Movie movie) {
        Optional<Movie> optOldMovie = movieRepository.findOne(movie.getId());

        if (optOldMovie.isPresent()) {

            Optional<Movie> opt = movieRepository.update(movie);

            if (opt.isEmpty())
                notifyAll(new MovieChangeEvent(MovieChangeEventType.UPDATE, optOldMovie.get(), movie));

            return Optional.empty();
        }
        return Optional.of(movie);
    }

    @Override
    public void notifyAll(MovieChangeEvent movieChangeEvent) {
        observers.forEach(o -> o.update(movieChangeEvent));
    }

    @Override
    public void addObserver(Observer<MovieChangeEvent> obs) {
        observers.add(obs);
    }

    @Override
    public void removeObserver(Observer<MovieChangeEvent> obs) {
        observers.remove(obs);
    }

    public List<Integer> getDistinctYears(){
        return movieRepository.getYears();
    }

    public Page<Movie> findAllFilter(Pageable pageable, MovieFilterDTO movieFilterDTO){
        return movieRepository.findAllFilter(pageable, movieFilterDTO);
    }
}
