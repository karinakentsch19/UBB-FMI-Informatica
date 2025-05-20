package ro.ubbcluj.cs.map.seminar7.service;

import ro.ubbcluj.cs.map.seminar7.domain.Movie;
import ro.ubbcluj.cs.map.seminar7.observer.MovieChangeEvent;
import ro.ubbcluj.cs.map.seminar7.observer.MovieChangeEventType;
import ro.ubbcluj.cs.map.seminar7.observer.Observable;
import ro.ubbcluj.cs.map.seminar7.observer.Observer;
import ro.ubbcluj.cs.map.seminar7.repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieService implements Observable<MovieChangeEvent> {
    private Repository<Long, Movie> movieRepository;

    private List<Observer<MovieChangeEvent>> observerList = new ArrayList<>();

    public Optional<Movie> findOne(Long id){
        return movieRepository.findOne(id);
    }

    public Iterable<Movie> findAll(){
        return movieRepository.findAll();
    }

    public Optional<Movie> delete(Long id){
        Optional<Movie> opt =  movieRepository.delete(id);

        opt.ifPresent(movie -> notifyAll(new MovieChangeEvent(MovieChangeEventType.DELETE, movie, null))));
        return opt;
    }

    public Optional<Movie> save(Movie movie){
        return movieRepository.save(movie);
    }

    public Optional<Movie> update(Movie movie){
        Optional<Movie> opt =  movieRepository.update(movie);

        opt.ifPresent(m -> notifyAll(new MovieChangeEvent(MovieChangeEventType.DELETE, , m)));
        return opt;
    }

    public MovieService(Repository<Long, Movie> movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void notifyAll(MovieChangeEvent movieChangeEvent) {
        observerList.forEach(o -> o.update(movieChangeEvent));
    }

    @Override
    public void addObserver(Observer<MovieChangeEvent> observer) {
        observerList.add(observer);
    }

    @Override
    public void removeObserver(Observer<MovieChangeEvent> observer) {
        observerList.remove(observer);
    }
}
