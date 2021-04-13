package com.example.theaterbuzz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, moviesRatings){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                tv.setTextColor(getResources().getColor(R.color.theaterbuzz_orange));
                tv.setTypeface(Typeface.DEFAULT_BOLD);
                tv.setTextSize(20);

                return view;
            }
        };
        ratingListView.setAdapter(arrayAdapter);

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedPosition = ratingListView.getCheckedItemPosition();

                String movieString = moviesRatings.get(selectedPosition);
                String[] movieDetails = movieString.split("-");
                // separating the movie title and year
                String movieTitle = movieDetails[0].trim();
                int year = Integer.parseInt(movieDetails[1].trim());

                String movieName = movieTitle + " " + year;

                Intent intent = new Intent(getApplicationContext(), IMDBmoviesActivity.class);
                intent.putExtra("title", movieTitle);
                startActivity(intent);
            }
        });
    }

    public void loadMovies(){
        MovieDataHelper db = new MovieDataHelper(this);
        List<Movie> movies = db.getAllMovies();

        if(!movies.isEmpty()) {
            for(int i = 0; i < movies.size(); i ++) {
                String movieString = movies.get(i).getMovieTitle() + " - " + movies.get(i).getMovieYear();
                moviesRatings.add(movieString);
            }
        }
    }
}