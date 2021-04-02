package com.example.theaterbuzz;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnRegister;
    Button btnDisplay;
    Button btnFavourites;
    Button btnEdit;
    Button btnSearch;
    Button btnRatings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // customizing the tool bar which was manually setup in the xml file
        Toolbar toolbar = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitleTextColor(getResources().getColor(R.color.black));
            setSupportActionBar(toolbar);
        }

        // initializing
        btnRegister = (Button) findViewById(R.id.btnRegisterMovie);
        btnDisplay = (Button) findViewById(R.id.btnDisplayMovies);
        btnFavourites = (Button) findViewById(R.id.btnFavorites);
        btnEdit = (Button) findViewById(R.id.btnEditMovies);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnRatings = (Button) findViewById(R.id.btnRatings);

        // functions for buttons using listeners
        btnRegister.setOnClickListener(this);
        btnDisplay.setOnClickListener(this);
        btnFavourites.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnRatings.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // navigating to the right activity after the button pressed
        switch(v.getId()) {
            case R.id.btnRegisterMovie:
                startActivity(new Intent( this,RegisterMovieActivity.class));
                break;
            case R.id.btnDisplayMovies:
                startActivity(new Intent( this,DisplayMovieActivity.class));
                break;
            case R.id.btnFavorites:
                startActivity(new Intent( this,FavouritesActivity.class));
                break;
            case R.id.btnEditMovies:
                startActivity(new Intent( this,EditMoviesActivity.class));
                break;
            case R.id.btnSearch:
                startActivity(new Intent( this,SearchActivity.class));
                break;
            case R.id.btnRatings:
                startActivity(new Intent( this,RatingsActivity.class));
                break;
        }
    }
}