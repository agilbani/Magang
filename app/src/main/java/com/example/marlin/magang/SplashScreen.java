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




       //sharedPreferences = getSharedPreferences("MyPrefs",MODE_PRIVATE);

       String firstTime = sharedPreferences.getString("firstTime", null);
       Log.d("cek cek", firstTime);

       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               Intent splash = new Intent(SplashScreen.this, Login.class);
               startActivity(splash);
               finish();
           }
       }, 2000);

       if(firstTime != null){
//                   SharedPreferences.Editor editor = sharedPreferences.edit();
//                   editor.putString("firstTime",firstTime);
//                   editor.apply();
                   Intent i = new Intent(SplashScreen.this, Home.class);
                   startActivity(i);
                   finish();
//
       } else{
           Intent i = new Intent(SplashScreen.this, Login.class);
           startActivity(i);
           finish();
          }



            }
        }


