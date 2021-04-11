package com.example.theaterbuzz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.theaterbuzz.model.Movie;
import com.example.theaterbuzz.model.MovieDataHelper;

import java.util.ArrayList;
import java.util.List;

public class RatingsActivity extends AppCompatActivity {

    ListView ratingListView;
    List<String> moviesRatings;
    Button btnFind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);

        // customizing the tool bar which was manually setup in the xml file
        Toolbar toolbar = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            toolbar = (Toolbar) findViewById(R.id.toolbar7);
            toolbar.setTitleTextColor(getResources().getColor(R.color.black));
            setSupportActionBar(toolbar);
        }

        // initialize
        ratingListView = (ListView) findViewById(R.id.rating_listView);
        moviesRatings = new ArrayList<>();
        btnFind = (Button) findViewById(R.id.btnFindIMDB);

        loadMovies();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, moviesRatings);
        ratingListView.setAdapter(arrayAdapter);

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedPosition = ratingListView.getCheckedItemPosition();
            }
        });
    }

    public void loadMovies(){
        MovieDataHelper db = new MovieDataHelper(this);
        List<Movie> movies = db.getAllMovies();

        if(!movies.isEmpty()) {
            for(int i = 0; i < movies.size(); i ++) {
                String movieString = movies.get(i).getMovieTitle() + " - " + movies.get(i).getRating();
                moviesRatings.add(movieString);
            }
        }
    }
}