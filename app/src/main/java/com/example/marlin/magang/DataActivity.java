package com.example.marlin.magang;

import android.content.Intent;
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

        Bundle bundle = new Bundle();
        bundle.putString("image", listData.getImage());
        bundle.putString("trayekNama", listData.getTrayek_nama());
        bundle.putString("status", listData.getStatus());

        Intent intent = new Intent(DataActivity.this, DetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

        Toast.makeText(this, "CLICK " + listData.getTrayek_nama(), Toast.LENGTH_SHORT).show();
    }
}
