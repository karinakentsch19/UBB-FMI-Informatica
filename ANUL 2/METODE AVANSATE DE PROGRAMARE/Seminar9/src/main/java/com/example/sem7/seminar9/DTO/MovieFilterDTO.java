package com.example.sem7.seminar9.DTO;

import java.util.Optional;

public class MovieFilterDTO {
    private Optional<Integer> yearFilter = Optional.empty();
    private Optional<String> titleFilter = Optional.empty();
    private Optional<String> directorFilter = Optional.empty();



    public Optional<Integer> getYearFilter() {
        return yearFilter;
    }

    public void setYearFilter(Optional<Integer> yearFilter) {
        this.yearFilter = yearFilter;
    }

    public Optional<String> getTitleFilter() {
        return titleFilter;
    }

    public void setTitleFilter(Optional<String> titleFilter) {
        this.titleFilter = titleFilter;
    }

    public Optional<String> getDirectorFilter() {
        return directorFilter;
    }

    public void setDirectorFilter(Optional<String> directorFilter) {
        this.directorFilter = directorFilter;
    }
}
