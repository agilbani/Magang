package com.example.marlin.magang;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.marlin.magang.DAO.Database;

import static okhttp3.internal.Internal.instance;

public class FileApp extends Application {
    private final String TableName = "db_List";
    private static Database database;
    private static FileApp fileApp;

    public static FileApp getInstance() {

        return fileApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        fileApp = this;
    }

    public Database getDatabase() {
        if(database == null) {
            instanceDB();
        }
        return database;
    }

    private void instanceDB() {
        database = Room.databaseBuilder(getApplicationContext(),Database.class,TableName).fallbackToDestructiveMigration().build();
    }
}
