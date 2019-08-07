package com.example.marlin.magang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class SplashScreen extends AppCompatActivity {

   SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

       sharedPreferences = getSharedPreferences("LOGIN", MODE_PRIVATE);

       String nama = sharedPreferences.getString("NAME", "default_name");

       if(nama == "default_name"){
           new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {
                   Intent splash = new Intent(SplashScreen.this, Login.class);
                   startActivity(splash);
                   finish();
               }
           }, 2000);

       } else {
           new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {
                   Intent splash = new Intent(SplashScreen.this, Home.class);
                   startActivity(splash);
                   finish();
               }
           }, 2000);
       }


           }
       }


