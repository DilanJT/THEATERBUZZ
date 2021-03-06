package com.example.theaterbuzz.model;

import android.provider.BaseColumns;

public interface Constants extends BaseColumns {

    // name constants which will be used for the local database

    public static final String TABLE_NAME = "watchedmovies";
    public static final String TITLE = "title";
    public static final String YEAR = "year";
    public static final String DIRECTOR = "director";
    public static final String ACTORS = "actors";
    public static final String RATING = "rating";
    public static final String REVIEW = "review";
    public static final String ISFAVOURITE = "isfavourite";

}

