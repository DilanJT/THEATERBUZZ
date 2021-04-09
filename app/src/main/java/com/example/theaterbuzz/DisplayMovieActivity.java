package com.example.theaterbuzz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.theaterbuzz.model.Movie;
import com.example.theaterbuzz.model.MovieDataHelper;

import java.util.ArrayList;
import java.util.List;

public class DisplayMovieActivity extends AppCompatActivity {

    ListView listView;
    List<String> moviesList;
    SparseBooleanArray checked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movie);

        // customizing the tool bar which was manually setup in the xml file
        Toolbar toolbar = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            toolbar = (Toolbar) findViewById(R.id.toolbar3);
            toolbar.setTitleTextColor(getResources().getColor(R.color.black));
            setSupportActionBar(toolbar);
        }

        // initialize
        listView = (ListView) findViewById(R.id.displayMoviesListView);
        moviesList = new ArrayList<>();

        // loading the movies as string value each to the moviesList array
        loadMovies();

        // setting the movies adapter for the listView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, moviesList);
        listView.setAdapter(adapter);


    }

    public void saveFavourites(View view) {

        MovieDataHelper db = new MovieDataHelper(this);
        List<Movie> movies = db.getAllMovies();

        // getting the checked items
        checked = listView.getCheckedItemPositions(); // this returns all the true and false values

        if(movies != null && moviesList != null) {
            for(int i =0; i < movies.size(); i++) {
                for(int j = 0; j < checked.size(); j++){
                    String movieString = moviesList.get(i);
                    String[] movieDetails = movieString.split("-");
                    String movieTitle = movieDetails[0].trim();
                    int year = Integer.parseInt(movieDetails[1].trim());

                    if(movies.get(i).getMovieTitle().equals(movieTitle) && movies.get(i).getMovieYear() == year) {
                        Movie movie = movies.get(i);
                        if(checked.keyAt(j) == i){
                            db.updateMovie(new Movie(movie.getMovieTitle(),
                                    movie.getMovieYear(),
                                    movie.getMovieDirector(),
                                    movie.getStringActors(),
                                    movie.getRating(),
                                    movie.getReview(),
                                    checked.valueAt(j)));

                            Log.d("UPDATE :", movie.getMovieTitle() + " got successfully updated");
                            Toast.makeText(this, movie.getMovieTitle() + " got successfully updated", Toast.LENGTH_SHORT).show();
                        }
                    }



                }
            }
        }
    }

    public void loadMovies(){
        MovieDataHelper db = new MovieDataHelper(this);

        Log.d("LoadMovies: " , "Loading movies from database...");
        List<Movie> movies = db.getAllMovies();

        for(int i = 0; i < movies.size(); i++) {
            String movieString = movies.get(i).getMovieTitle() + " - " + movies.get(i).getMovieYear();
            Log.d("Movie :", movieString);
            moviesList.add(movieString); // adding the movie string to the listview list
            listView.setItemChecked(i, movies.get(i).isFavourite());
        }
    }
}