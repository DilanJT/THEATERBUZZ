package com.example.theaterbuzz.model;

import java.util.Objects;

public class Movie {
    private int movieID;
    private String movieTitle;
    private int movieYear;
    private String movieDirector;
    private String[] actors;
    private int rating;
    private String review;

    public Movie(int movieID, String movieTitle, int movieYear, String movieDirector, String[] actors, int rating, String review) {
        this.movieID = movieID;
        this.movieTitle = movieTitle;
        this.movieYear = movieYear;
        this.movieDirector = movieDirector;
        this.actors = actors;
        this.rating = rating;
        this.review = review;
    }

    public Movie(String movieTitle, int movieYear, String movieDirector, String[] actors, int rating, String review) {
        this.movieTitle = movieTitle;
        this.movieYear = movieYear;
        this.movieDirector = movieDirector;
        this.actors = actors;
        this.rating = rating;
        this.review = review;
    }

    public Movie(){}

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public int getMovieYear() {
        return movieYear;
    }

    public void setMovieYear(int movieYear) {
        this.movieYear = movieYear;
    }

    public String getMovieDirector() {
        return movieDirector;
    }

    public void setMovieDirector(String movieDirector) {
        this.movieDirector = movieDirector;
    }

    public String[] getActors() {

        // TODO convert array to string
        String actorString = "";
        return actors;
    }

    public void setActors(String[] actors) {
        this.actors = actors;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return movieYear == movie.movieYear &&
                Objects.equals(movieTitle, movie.movieTitle) &&
                Objects.equals(movieDirector, movie.movieDirector);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieTitle, movieYear, movieDirector);
    }
}
