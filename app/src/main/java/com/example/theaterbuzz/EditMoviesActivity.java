package com.example.theaterbuzz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.theaterbuzz.model.Movie;
import com.example.theaterbuzz.model.MovieDataHelper;

import java.util.ArrayList;
import java.util.List;

public class EditMoviesActivity extends AppCompatActivity {

    ListView moviesListView;
    List<String> moviesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movies);

        // customizing the tool bar which was manually setup in the xml file
        Toolbar toolbar = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            toolbar = (Toolbar) findViewById(R.id.toolbar5);
            toolbar.setTitleTextColor(getResources().getColor(R.color.black));
            setSupportActionBar(toolbar);
        }

        moviesList = new ArrayList<>();
        moviesListView = (ListView) findViewById(R.id.editMoviesListView);

        loadMovies();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, moviesList);
        moviesListView.setAdapter(arrayAdapter);

        MovieDataHelper db = new MovieDataHelper(this);
        List<Movie> movies = db.getAllMovies();

        moviesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String movieString = moviesList.get(position);
                String[] movieDetails = movieString.split("-");
                // separating the movie title and year
                String movieTitle = movieDetails[0].trim();
                int year = Integer.parseInt(movieDetails[1].trim());

                String title = "";
                int movieYear = 1895;
                String director = "";
                String actors = "";
                int rating = 1;
                String review = "";
                boolean isFavourite = false;
                int movieID = 0;

                // iterating over all the movies
                for(int i = 0; i < movies.size(); i++) {
                    Movie movie = movies.get(i);
                    if(movie.getMovieTitle().equalsIgnoreCase(movieTitle) && movie.getMovieYear() == year) {
                        movieID = movie.getMovieID();
                        title = movie.getMovieTitle();
                        movieYear = movie.getMovieYear();
                        director = movie.getMovieDirector();
                        actors = movie.getStringActors();
                        rating = movie.getRating();
                        review = movie.getReview();
                        isFavourite = movie.isFavourite();
                    }
                }

                // send the movie object by loading from the database
                Bundle bundle = new Bundle();
                bundle.putInt("id", movieID);
                bundle.putString("title", title);
                bundle.putInt("year", movieYear);
                bundle.putString("director", director);
                bundle.putString("actors", actors);
                bundle.putInt("rating", rating);
                bundle.putString("review", review);
                bundle.putBoolean("isfavourite", isFavourite);

                EditMovieDialog editMovieDialog = new EditMovieDialog();
                editMovieDialog.setArguments(bundle);
                editMovieDialog.show(getSupportFragmentManager(), "edit dialog");
            }
        });

    }

    public void loadMovies(){
        MovieDataHelper db = new MovieDataHelper(this);

        Log.d("LoadMovies: " , "Loading movies from database...");
        List<Movie> movies = db.getAllMovies();

        if(!movies.isEmpty()) {
            for (int i = 0; i < movies.size(); i++) {
                String movieString = movies.get(i).getMovieTitle() + " - " + movies.get(i).getMovieYear();
                Log.d("Movie :", movieString);
                moviesList.add(movieString); // adding the movie string to the listview list
            }
        }
    }
}