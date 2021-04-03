package com.example.theaterbuzz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class IntroScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_screen);

//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        startActivity(new Intent( this,MainActivity.class));
//        finish();

        Thread background = new Thread() {
            public void run() {
                try {
                    // Thread will sleep for 1.2 seconds
                    sleep(1200);
                    Intent i=new Intent(getBaseContext(),MainActivity.class);
                    startActivity(i);
                    // setting the activity to remove so when back pressed it will prevent accessing to the intro screen
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        // start thread
        background.start();
    }
}