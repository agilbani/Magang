package com.example.marlin.magang.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.marlin.magang.R;

public class AlertDialog extends AppCompatActivity {

    private Button btnOk, btnCancel;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_dialog);

        btnOk = (Button)findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v7.app.AlertDialog.Builder build = new android.support.v7.app.AlertDialog.Builder(context);
                build.setTitle("Disini ada resty");
                build.setMessage("Disini ada Lia");
                build.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        });

        btnCancel = (Button)findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v7.app.AlertDialog.Builder build = new android.support.v7.app.AlertDialog.Builder(context);
                build.setTitle("Disini ada Udee");
                build.setMessage("Aku masih ingin bertahan");

                build.setPositiveButton("ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AlertDialog.this, "Berhasil bertahan", Toast.LENGTH_SHORT).show();
                    }
                });

                build.setNegativeButton("tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                build.show();
            }
        });
    }
}
