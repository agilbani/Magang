package com.example.marlin.magang.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.marlin.magang.model.ListData;

import java.util.List;

@Dao
public interface ListDataDAO {

    @Query("SELECT * FROM ListtData")
    LiveData<List<ListData>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<ListData> listData);

    @Update
    void updateListData(ListData listData);

    @Delete
    void delete(ListData listData);

    @Query("DELETE FROM ListtData")
    void deleteAll();

}
