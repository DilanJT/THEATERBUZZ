package com.example.theaterbuzz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RegisterMovieActivity extends AppCompatActivity {

    EditText title;
    EditText year;
    EditText director;
    EditText actors;
    EditText rating;
    EditText review;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_movie);

        // customizing the tool bar which was manually setup in the xml file
        Toolbar toolbar = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            toolbar = (Toolbar) findViewById(R.id.toolbar2);
            toolbar.setTitleTextColor(getResources().getColor(R.color.black));
            setSupportActionBar(toolbar);
        }

        //initializing the views
        title = (EditText) findViewById(R.id.registerTitle);
        year = (EditText) findViewById(R.id.registerYear);
        director = (EditText) findViewById(R.id.registerDirector);
        actors = (EditText) findViewById(R.id.registerActors);
        rating = (EditText) findViewById(R.id.registerRating);
        review = (EditText) findViewById(R.id.registerreview);
        btnSave = (Button) findViewById(R.id.btnRegisterSave);
    }

    // function to check user have input text in the correct way
    public void checkTextBoxValidity(){

    }
}