package com.example.marlin.magang.DAO;

import android.arch.persistence.room.RoomDatabase;

import com.example.marlin.magang.model.ListData;

@android.arch.persistence.room.Database(entities = ListData.class, version = 2)
public abstract class Database extends RoomDatabase  {
    public abstract ListDataDAO listDataDAO();
}
