package com.example.marlin.magang;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import java.util.HashMap;

public class SessionManager {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE= 0;
    
    private static final String PREF_NAME="LOGIN";
    private static final String LOGIN ="IS_LOGIN";
    public static final String EMAIL ="EMAIL";
    public static final String PASSWORD ="PASSWORD";

    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("LOGIN", PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void CreateSession(String mEmail, String mPass){
        editor.putBoolean(LOGIN, true);
        editor.putString(EMAIL, mEmail);
        editor.putString(PASSWORD, mPass);
        editor.apply();
    }
    
    public boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN, false);
    }
    
    public void checkLoggin(){
        if (!this.isLoggin()) {
            Intent i = new Intent(context, Login.class);
            context.startActivity(i);
            ((Home) context).finish();
        }
    }
    
    public HashMap<String, String> getUserDetail(){
        HashMap<String, String> user = new HashMap<>();
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(PASSWORD, sharedPreferences.getString(PASSWORD, null));
        
        return user;
    }


}

