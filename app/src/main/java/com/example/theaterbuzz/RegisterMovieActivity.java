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
import com.example.theaterbuzz.model.Movie;
import com.example.theaterbuzz.model.MovieDataHelper;

import java.util.Date;

public class RegisterMovieActivity extends AppCompatActivity{

    EditText title;
    EditText year;
    EditText director;
    EditText actors;
    EditText rating;
    EditText review;
    Button btnSave;

    String yearText;
    int minYear = 1895;
//    String[] yearTextArray;

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

        //initializing the viewsOn
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
        int cYear = d.getYear(); // getting the current date as the max value filter.
        int currentYear = cYear + 1900;
        Log.d("YEAR", "Current year is " + currentYear);
        //year.setFilters(new InputFilter[] { new MinMaxFilter("1895", Integer.toString(currentYear))});

        // There is a problem when setting a bigger amount as the min filter
        // so have to use onFocusChangeListener to check the min value
        // problem was found and understood using the below link,
        // https://stackoverflow.com/questions/14212518/is-there-a-way-to-define-a-min-and-max-value-for-edittext-in-android/28743065#28743065
        year.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(!year.getText().toString().isEmpty()){
                        if(Integer.parseInt(year.getText().toString()) < minYear){
                            Toast.makeText(RegisterMovieActivity.this, "Year should be greater than or equal 1895", Toast.LENGTH_SHORT).show();
                            year.setText(null);
                        }
                    }

                    if(!year.getText().toString().isEmpty()) {
                        if(Integer.parseInt(year.getText().toString()) > currentYear){
                            Toast.makeText(RegisterMovieActivity.this, "Year should be less than or equal" + currentYear, Toast.LENGTH_SHORT).show();
                            year.setText(null);
                        }
                    }
                }
            }
        });

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

//            if(Integer.parseInt(year.getText().toString()) > )

            // save the details to the database

            MovieDataHelper db = new MovieDataHelper(this);
            db.addMovie(new Movie(
                    title.getText().toString(),
                    Integer.parseInt(year.getText().toString()),
                    director.getText().toString(),
                    actors.getText().toString(),
                    Integer.parseInt(rating.getText().toString()),
                    review.getText().toString(), false));// setting the default value of the isFavourite false.
            Toast.makeText(this, "Movie " + title.getText().toString() + " successfully added", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Not saved! Please fill all the details", Toast.LENGTH_SHORT).show();
        }
    }


}