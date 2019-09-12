package com.example.marlin.magang;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.marlin.magang.model.ListData;

public class DataActivity extends AppCompatActivity implements ListData_Fragment.OnListDataListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.dataActivity, ListData_Fragment.newInstance(), "").commit();

    }

    @Override
    public void onListData(ListData listData) {
        Toast.makeText(this, "CLICK" + listData.getTrayek_id(), Toast.LENGTH_SHORT).show();
    }
}
