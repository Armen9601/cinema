package com.example.common.util;

import com.example.common.entity.Movie;

import java.util.Comparator;

public class MovieRatingComparator implements Comparator<Movie> {
    @Override
    public int compare(Movie o1, Movie o2) {
        if (o1.getRating() == o2.getRating()) {
            return 0;
        }
        return o1.getRating() > o2.getRating() ? 1 : -1;
    }
}
