package com.example.common.util;

import com.example.common.entity.Movie;

import java.util.Comparator;

public class MoviePopularityComparator implements Comparator<Movie> {
    @Override
    public int compare(Movie o1, Movie o2) {
        if (o1.getPopularity() == o2.getPopularity()) {
            return 0;
        }
        return o1.getPopularity() > o2.getPopularity() ? 1 : -1;
    }
}
