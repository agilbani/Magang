package com.example.marlin.magang.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.marlin.magang.FileApp;
import com.example.marlin.magang.model.ListData;

import java.util.List;

public class ListDataModel extends ViewModel {
    private LiveData<List<ListData>> listitem;

    public ListDataModel(){
        listitem = FileApp.getInstance().getDatabase().listDataDAO().getAll();
    }
    public LiveData<List<ListData>>getListitem() {
        return listitem;
    }
}
