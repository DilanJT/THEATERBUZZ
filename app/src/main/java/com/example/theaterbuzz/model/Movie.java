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
    private boolean isFavourite;



    public Movie(int movieID, String movieTitle, int movieYear, String movieDirector,
                 String actors, int rating, String review, boolean isFavourite) {
        this.movieID = movieID;
        this.movieTitle = movieTitle;
        this.movieYear = movieYear;
        this.movieDirector = movieDirector;
        //this.actors = actors;
        setStringActors(actors);
        this.rating = rating;
        this.review = review;
        this.isFavourite = isFavourite;
    }

    public Movie(String movieTitle, int movieYear, String movieDirector,
                 String actors, int rating, String review, boolean isFavourite) {
        this.movieTitle = movieTitle;
        this.movieYear = movieYear;
        this.movieDirector = movieDirector;
        // this.actors = actors;
        setStringActors(actors);
        this.rating = rating;
        this.review = review;
        this.isFavourite = isFavourite;
    }

    public Movie(){
        isFavourite = false;
    }

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

    // to get the array of actors
    public String[] getArrayActors() {
        return actors;
    }

    //to get the string of actors
    public String getStringActors(){
        return convertStringArrayToString(actors, ",");
    }

    // https://www.journaldev.com/773/java-string-array-to-string
    public String convertStringArrayToString(String[] array, String sep) {
        StringBuilder builder = new StringBuilder();
        for(String str : array) {
            builder.append(str).append(sep);
        }
        return builder.substring(0, builder.length() - 1);
    }

    public void setActors(String[] actors) {
        this.actors = actors;
    }

    public void setStringActors(String strActors){
        // converting the string to an array
        this.actors = strActors.split(",");
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

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return movieYear == movie.movieYear &&
                Objects.equals(movieTitle, movie.movieTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieTitle, movieYear);
    }
}
