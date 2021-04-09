package com.example.theaterbuzz;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class EditMovieDialog extends AppCompatDialogFragment {

    EditText editTitle;
    EditText editYear;
    EditText editDirector;
    EditText editActors;
    // EditText editRating; - have to implement the star rating view
    EditText editReview;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_movie_dialog, null);

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
                    }
                });

        editTitle = view.findViewById(R.id.registerEditTitle);
        editYear = view.findViewById(R.id.registerEditYear);
        editDirector = view.findViewById(R.id.registerEditDirector);
        editActors = view.findViewById(R.id.registerEditActors);
        editReview = view.findViewById(R.id.registerEditreview);

        return builder.create();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

}
