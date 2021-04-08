package com.example.theaterbuzz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.theaterbuzz.model.MinMaxFilter;

import java.util.Date;

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

        // setting rating filter to allow only input between 1 to 10
        rating.setFilters(new InputFilter[] { new MinMaxFilter("1", "10")});

        // setting the year filter to allow only to inputs year greater than 1895
        Date d = new Date();
        int currentYear = d.getYear();
        currentYear = currentYear + 1900;
        Log.d("YEAR", "Current year is " + currentYear);
        year.setFilters(new InputFilter[] { new MinMaxFilter(1895, currentYear)});
    }

    // function to check user have input text in the correct way
    public void checkTextBoxValidity(){

    }

    public void registerMovie(View view) {
        if(!title.getText().toString().isEmpty() &&
            !year.getText().toString().isEmpty() &&
            !director.getText().toString().isEmpty() &&
            !actors.getText().toString().isEmpty() &&
            !rating.getText().toString().isEmpty() &&
            !review.getText().toString().isEmpty()){

            // save the details to the database


        }else {
            Toast.makeText(this, "Not saved! Please fill all the details", Toast.LENGTH_SHORT).show();
        }
    }
}