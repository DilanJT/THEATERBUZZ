package com.example.theaterbuzz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.io.InputStream;


public class MovieImageDialog extends AppCompatDialogFragment {

    String imageURL;
    String movieName;
    ImageView imageView;



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.movie_image_dialog, null);

        if(getArguments() != null) {
            imageURL = getArguments().getString("image");
            movieName = getArguments().getString("title");
        }

        builder.setView(view)
                .setTitle(movieName)
                .setNegativeButton("close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        imageView = view.findViewById(R.id.imgMovie);

        loadImage(imageURL);

        return builder.create();
    }

    public void loadImage(String url){
        new Thread(new MovieImage(url)).start();
    }


    class MovieImage implements Runnable{

        String imageUrlString;

        MovieImage(String urlString) {
            imageUrlString = urlString;
        }

        @Override
        public void run() {

            Bitmap bitmap = null;
            try{
                InputStream inputStream = new java.net.URL(imageUrlString).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);

            }catch(Exception e) {
                e.printStackTrace();
            }

            Bitmap finalBitmap = bitmap;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(finalBitmap);
                }
            });

        }


    }



}
