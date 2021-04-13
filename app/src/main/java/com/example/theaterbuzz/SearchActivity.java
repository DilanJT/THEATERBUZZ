package com.example.theaterbuzz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.theaterbuzz.model.Movie;
import com.example.theaterbuzz.model.MovieDataHelper;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    EditText searchBox;
    Button btnLookup;
    ListView listView;
    List<String> searchedMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // customizing the tool bar which was manually setup in the xml file
        Toolbar toolbar = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            toolbar = (Toolbar) findViewById(R.id.toolbar6);

            toolbar.setTitleTextColor(getResources().getColor(R.color.black));
            setSupportActionBar(toolbar);
        }

        // initializing views
        searchBox = (EditText) findViewById(R.id.searchBox);
        btnLookup = (Button) findViewById(R.id.btnLookup);
        listView = (ListView) findViewById(R.id.searchListView);
        searchedMovies = new ArrayList<>();

        onSearch();

        btnLookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearch();
            }
        });
    }

    public void onSearch() {

        String searchString;

        MovieDataHelper db = new MovieDataHelper(this);
        if(!searchBox.getText().toString().isEmpty()) {
            searchedMovies.clear();
            searchString = searchBox.getText().toString();
            List<Movie> movies = db.searchMovies(searchString);

            addMovies(movies);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, searchedMovies){
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                    View view = super.getView(position, convertView, parent);
                    TextView tv = (TextView) view.findViewById(android.R.id.text1);
                    tv.setTextColor(getResources().getColor(R.color.theaterbuzz_orange));
                    tv.setTypeface(Typeface.DEFAULT_BOLD);
                    tv.setTextSize(18);

                    return view;
                }
            };
            listView.setAdapter(adapter);

        }else {
            searchedMovies.clear();
            List<Movie> movies = db.getAllMovies();

            addMovies(movies);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, searchedMovies){
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                    View view = super.getView(position, convertView, parent);
                    TextView tv = (TextView) view.findViewById(android.R.id.text1);
                    tv.setTextColor(getResources().getColor(R.color.theaterbuzz_orange));
                    tv.setTypeface(Typeface.DEFAULT_BOLD);
                    tv.setTextSize(18);

                    return view;
                }
            };
            listView.setAdapter(adapter);
        }


    }

    public void addMovies(List<Movie> films) {
        if(!films.isEmpty()) {
            for (int i = 0; i < films.size(); i++) {
                String movieString = "\n"+ films.get(i).getMovieTitle() + " - " + films.get(i).getMovieYear() +
                        "\nDirector: " + films.get(i).getMovieDirector() +
                        "\nActors: " + films.get(i).getStringActors() + "\n";
                Log.d("Movie :", movieString);
                searchedMovies.add(movieString);

            }
        }
    }

}