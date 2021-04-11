package com.example.theaterbuzz.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static com.example.theaterbuzz.model.Constants.ACTORS;
import static com.example.theaterbuzz.model.Constants.DIRECTOR;
import static com.example.theaterbuzz.model.Constants.ISFAVOURITE;
import static com.example.theaterbuzz.model.Constants.RATING;
import static com.example.theaterbuzz.model.Constants.REVIEW;
import static com.example.theaterbuzz.model.Constants.TABLE_NAME;
import static com.example.theaterbuzz.model.Constants.TITLE;
import static com.example.theaterbuzz.model.Constants.YEAR;
import static java.sql.Types.BOOLEAN;
import static java.sql.Types.INTEGER;


public class MovieDataHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "moviesfav.db";
    private static final int DATABASE_VERSION = 1;

    public MovieDataHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TITLE + " TEXT NOT NULL, "
            + YEAR + " INTEGER NOT NULL, "
            + DIRECTOR + " TEXT NOT NULL, "
            + ACTORS + " TEXT, "
            + RATING + " INTEGER, "
            + REVIEW + " TEXT, "
            + ISFAVOURITE + " BOOLEAN); ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // function to add a watched movie
    public void addMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE, movie.getMovieTitle());
        values.put(YEAR, movie.getMovieYear());
        values.put(DIRECTOR, movie.getMovieDirector());
        values.put(ACTORS, movie.getStringActors());
        values.put(RATING, movie.getRating());
        values.put(REVIEW, movie.getReview());
        values.put(ISFAVOURITE, movie.isFavourite());

        db.insertOrThrow(TABLE_NAME, null, values);
        db.close();
    }

    // get single movie watched
    public Movie getMovie(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] {
                _ID, TITLE, YEAR, DIRECTOR, ACTORS, RATING, REVIEW, ISFAVOURITE}, _ID + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);
        if(cursor != null)
            cursor.moveToFirst();

        Movie movie = new Movie(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                Integer.parseInt(cursor.getString(2)),
                cursor.getString(3),
                cursor.getString(4),
                Integer.parseInt(cursor.getString(5)),
                cursor.getString(6),
                cursor.getString(7).equals("1") ? true : false
        );
        return movie;
    }

    // get all the movies watched as a list
    public List<Movie> getAllMovies(){
        List<Movie> movies = new ArrayList<>();

        String selectStm = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectStm, null);

        if(cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.setMovieID(Integer.parseInt(cursor.getString(0)));
                movie.setMovieTitle(cursor.getString(1));
                movie.setMovieYear(Integer.parseInt(cursor.getString(2)));
                movie.setMovieDirector(cursor.getString(3));
                movie.setStringActors(cursor.getString(4));
                movie.setRating(Integer.parseInt(cursor.getString(5)));
                movie.setReview(cursor.getString(6));
                movie.setFavourite(cursor.getString(7).equals("1") ? true : false); // if 1 ? true : false

                movies.add(movie);
            }while (cursor.moveToNext());
        }

        return movies;
    }

    // deleting single employee
    public void deleteMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, _ID + " = ?",
                new String[] {String.valueOf(movie.getMovieID())});
        db.close();
    }

    // update contact
    public int updateMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TITLE, movie.getMovieTitle());
        values.put(YEAR, movie.getMovieYear());
        values.put(DIRECTOR, movie.getMovieDirector());
        values.put(ACTORS, movie.getStringActors());
        values.put(RATING, movie.getRating());
        values.put(REVIEW, movie.getReview());
        values.put(ISFAVOURITE, movie.isFavourite());

        // updating the specific record
        return db.update(TABLE_NAME, values, _ID + " = ?",
                new String[] {String.valueOf(movie.getMovieID())});
    }

    // returns the movies for the search string
    public List<Movie> searchMovies(String movieText) {
        List<Movie> movies = new ArrayList<>();

        String selectStm = "SELECT * FROM " + TABLE_NAME + " WHERE "+ TITLE +" LIKE '%"+ movieText + "%' OR "
                + DIRECTOR +" LIKE '%"+ movieText + "%' OR "
                + ACTORS +" LIKE '%"+ movieText + "%'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectStm, null);

        if(cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.setMovieID(Integer.parseInt(cursor.getString(0)));
                movie.setMovieTitle(cursor.getString(1));
                movie.setMovieYear(Integer.parseInt(cursor.getString(2)));
                movie.setMovieDirector(cursor.getString(3));
                movie.setStringActors(cursor.getString(4));
                movie.setRating(Integer.parseInt(cursor.getString(5)));
                movie.setReview(cursor.getString(6));
                movie.setFavourite(cursor.getString(7).equals("1") ? true : false); // if 1 ? true : false

                movies.add(movie);
            }while (cursor.moveToNext());
        }

        return movies;
    }

}
