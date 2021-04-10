package com.example.theaterbuzz;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.theaterbuzz.model.EventListener;
import com.example.theaterbuzz.model.Movie;
import com.example.theaterbuzz.model.MovieDataHelper;

import java.util.List;

public class EditMovieDialog extends AppCompatDialogFragment {

    EditText editTitle;
    EditText editYear;
    EditText editDirector;
    EditText editActors;
    // EditText editRating; - have to implement the star rating view
    RatingBar ratingBar;
    EditText editReview;

    RadioGroup radioFavGroup;
    RadioButton rFavourite;
    RadioButton rNotFavourite;
    RadioButton radioFavButton;

    EventListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_movie_dialog, null);

        String title = "";
        int movieYear = 1895;
        String director = "";
        String actors = "";
        int rating = 1;
        String review = "";
        boolean isFavourite = false;


        // getting the parsed data from the activity from arguments
        if(getArguments() != null) {
            title = getArguments().getString("title");
            movieYear = getArguments().getInt("year");
            director = getArguments().getString("director");
            actors = getArguments().getString("actors");
            rating = getArguments().getInt("rating");
            review = getArguments().getString("review");
            isFavourite = getArguments().getBoolean("isfavourite");
        }

        editTitle = view.findViewById(R.id.registerEditTitle);
        editYear = view.findViewById(R.id.registerEditYear);
        editDirector = view.findViewById(R.id.registerEditDirector);
        editActors = view.findViewById(R.id.registerEditActors);
        ratingBar = view.findViewById(R.id.ratingBar);
        editReview = view.findViewById(R.id.registerEditreview);
        radioFavGroup = view.findViewById(R.id.is_favourite_radio);

        rFavourite = view.findViewById(R.id.favRadio);
        rNotFavourite = view.findViewById(R.id.notfavRadio);

        // setting the values from arguments
        editTitle.setText(title);
        editYear.setText(Integer.toString(movieYear));
        editDirector.setText(director);
        editActors.setText(actors);
        ratingBar.setRating(rating); // TODO: convert the int variable of rating to float type throughout the project
        editReview.setText(review);

        if(isFavourite) {
            rFavourite.setChecked(true);
        }else{
            rNotFavourite.setChecked(true);
        }

        MovieDataHelper db = new MovieDataHelper(getContext());
        List<Movie> movies = db.getAllMovies();

        builder.setView(view)
                .setTitle("Edit Movie")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // save the values to the database

                        int currentMovieID = 0;
                        if(getArguments() != null) {
                            currentMovieID = getArguments().getInt("id");
                        }

                        String mTitle = editTitle.getText().toString();
                        int mYear = Integer.parseInt(editYear.getText().toString());
                        String mDirector = editDirector.getText().toString();
                        String mActors = editActors.getText().toString();
                        int mRating = (int) ratingBar.getRating(); // TODO change this to float
                        String mReview = editReview.getText().toString();
                        boolean mFav;
                        if(rFavourite.isChecked()){
                            mFav = true;
                        }else{
                            mFav = false;
                        }

                        for(int i = 0; i < movies.size(); i++) {
                            Movie movie = movies.get(i);
                            if(currentMovieID == movie.getMovieID()) {
                                movie.setMovieTitle(mTitle);
                                movie.setMovieYear(mYear);
                                movie.setMovieDirector(mDirector);
                                movie.setStringActors(mActors);
                                movie.setRating(mRating);
                                movie.setReview(mReview);
                                movie.setFavourite(mFav);

                                db.updateMovie(movie);

                                Log.d("UPDATE :", movie.getMovieTitle() + " got successfully updated");
                                Toast.makeText(getContext(), movie.getMovieTitle() + " got successfully updated", Toast.LENGTH_SHORT).show();

                                listener.loadDataAgain();
                            }
                        }
                    }
                });



        editYear.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO check the year is greater than 1895
            }
        });



        return builder.create();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof EventListener) {
            listener = (EventListener)context;
        }else{
            Log.d("ERROR", "Havent implemented the EventListener");
        }
    }

}
