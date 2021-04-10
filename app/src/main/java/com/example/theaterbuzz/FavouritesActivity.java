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

public class FavouritesActivity extends AppCompatActivity {

    ListView favListView;
    List<String> favMovieList;
    SparseBooleanArray checked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        // customizing the tool bar which was manually setup in the xml file
        Toolbar toolbar = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            toolbar = (Toolbar) findViewById(R.id.toolbar4);
            toolbar.setTitleTextColor(getResources().getColor(R.color.black));
            setSupportActionBar(toolbar);
        }

        // initialize vies
        favListView = (ListView) findViewById(R.id.favouriteMoviesListView);
        favMovieList = new ArrayList<>();





        loadFavMovies();
    }

    public void saveChanges(View view) {
        MovieDataHelper db = new MovieDataHelper(this);
        List<Movie> movies = db.getAllMovies();

        // getting the checked items
        checked = favListView.getCheckedItemPositions(); // this returns all the true and false values

        if(!movies.isEmpty() && !favMovieList.isEmpty()) {
            for(int i =0; i < movies.size(); i++) {
                for(int j = 0; j < checked.size(); j++){
                    String movieString = favMovieList.get(i);
                    String[] movieDetails = movieString.split("-");
                    String movieTitle = movieDetails[0].trim();
                    int year = Integer.parseInt(movieDetails[1].trim());

                    if(movies.get(i).getMovieTitle().equals(movieTitle) && movies.get(i).getMovieYear() == year) {
                        Movie movie = movies.get(i);
                        movie.setMovieID(movies.get(i).getMovieID()); //TODO check again
                        if(checked.keyAt(j) == i){
                            movie.setFavourite(checked.valueAt(j));
                            db.updateMovie(movie);

                            Log.d("UPDATE :", movie.getMovieTitle() + " got successfully updated");
                            Toast.makeText(this, movie.getMovieTitle() + " got successfully updated", Toast.LENGTH_SHORT).show();
                        }
                    }



                }
            }
        }
    }

    public void loadFavMovies() {
        MovieDataHelper db = new MovieDataHelper(this);

        Log.d("LoadMovies: " , "Loading your favourite movies from database...");
        List<Movie> movies = db.getAllMovies();

        if(!movies.isEmpty()) {
            for (int i = 0; i < movies.size(); i++) {
                // checking for the favorite movies
                if (movies.get(i).isFavourite()) {
                    String movieString = movies.get(i).getMovieTitle() + " - " + movies.get(i).getMovieYear();
                    Log.d("Movie :", movieString);
                    favMovieList.add(movieString); // adding the movie string to the listview list
//                    favListView.setItemChecked(i, true);
                }
            }


        }

        // setting the favourite movies adapter for the listView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, favMovieList);
        favListView.setAdapter(adapter);

        // setting the item checked
        for (int i = 0; i < favListView.getCount(); i++) {
            favListView.setItemChecked(i, true);
        }
    }
}