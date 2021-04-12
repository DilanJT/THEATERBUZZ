package com.example.theaterbuzz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.theaterbuzz.model.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class IMDBmoviesActivity extends AppCompatActivity {

    String key = "k_1vy4d7ez";
    String searchUrl = "https://imdb-api.com/en/API/SearchMovie/";
    String ratingURL = "https://imdb-api.com/en/API/UserRatings/";

    List<String> moviesList;
    List<String> imageList;
    ListView listView;

    String mTitleFromLocalDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_m_d_bmovies);

        // customizing the tool bar which was manually setup in the xml file
        Toolbar toolbar = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            toolbar = (Toolbar) findViewById(R.id.toolbar8);
            toolbar.setTitleTextColor(getResources().getColor(R.color.black));
            setSupportActionBar(toolbar);
        }

        Intent intent = getIntent();
        if(intent != null) {
            mTitleFromLocalDB = intent.getStringExtra("title");
        }

        //initialize
        listView = (ListView) findViewById(R.id.imdbListView);
        moviesList = new ArrayList<>();
        imageList = new ArrayList<>();

        String fullURL = searchUrl+ key + "/" + mTitleFromLocalDB.trim(); // search URL
        String fullURL2 = ratingURL + key;
        getMoviesFromIMDB(fullURL, fullURL2);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!imageList.isEmpty()){
                    if(imageList.get(position) != null){
                        //TODO start a fragment
                        Bundle bundle = new Bundle();
                        bundle.putString("image", imageList.get(position));
                        bundle.putString("title", moviesList.get(position));
                        MovieImageDialog movieImageDialog = new MovieImageDialog();
                        movieImageDialog.setArguments(bundle);
                        movieImageDialog.show(getSupportFragmentManager(),"image view");
                    }else{
                        Toast.makeText(IMDBmoviesActivity.this, "Sorry! Image is not available", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void getMoviesFromIMDB(String url, String urlR){
        // starting the thread
        new Thread(new MoviesIMDBTitles(mTitleFromLocalDB, url, urlR)).start();
    }

    class MoviesIMDBTitles implements Runnable {
        String movieTitle;
        String searchUrlString;
        String rUrlString;

        MoviesIMDBTitles (String title, String urlString, String urlString2) {
            movieTitle = title;
            searchUrlString = urlString;
            rUrlString = urlString2;
        }

        public void makeString(String urlRequestString, StringBuilder mStrings){
            try{
                URL url = new URL(urlRequestString);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String line;
                while ((line = bf.readLine()) != null) {
                    mStrings.append(line);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void run() {

            StringBuilder jsonStrings = new StringBuilder(""); // movies
            StringBuilder jsonStringsRating = new StringBuilder(""); // rating of a movie

            try {
                // setup the connection
//                URL url = new URL(searchUrlString);
//                HttpURLConnection con = (HttpURLConnection) url.openConnection();
//
//                BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
//
//                String line;
//                while((line = bf.readLine()) != null){
//                    jsonStrings.append(line);
//                }

                makeString(searchUrlString, jsonStrings);

                // json parsing
                JSONObject jsonObject = new JSONObject(jsonStrings.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                // finding the
                for(int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonMovieTitle = jsonArray.getJSONObject(i);
                    String movieName = jsonMovieTitle.getString("title");
                    String movieIMDBid = jsonMovieTitle.getString("id");
                    String imagePath = jsonMovieTitle.getString("image");

                    String userRatingFullUrl  = rUrlString + "/" + movieIMDBid;
                    makeString(userRatingFullUrl, jsonStringsRating);

                    JSONObject jsonObject2 = new JSONObject(jsonStringsRating.toString());
                    String rating = jsonObject2.getString("totalRating");
                    Log.d("Movie",  movieName + " " + movieIMDBid + " " + rating);

                    String displayRating = !rating.isEmpty() ? rating : "N/A";
                    String listItemString = movieName + "\nRating :" + displayRating;

                    //adding the movies with the rating to the string
                    moviesList.add(listItemString);
                    //adding images to list
                    imageList.add(imagePath);
                }




            } catch (Exception e){
                e.printStackTrace();
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // updates the UI

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, moviesList);
                    listView.setAdapter(adapter);

                }
            });


        }



    }
}