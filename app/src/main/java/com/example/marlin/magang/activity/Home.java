package com.example.marlin.magang.activity;

import android.content.Intent;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marlin.magang.R;
import com.example.marlin.magang.fragment.Dialog;

public class Home extends AppCompatActivity {

    private String dataRoute, dataCond, dataDesc;

    TextView  ket;
    ImageView marlinLogo, image_view;
    Spinner spinnerAPI, spinnerCon;
    Button btnChoose, btnSend;
    EditText dropText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        dataRoute = getIntent().getStringExtra("data");
        Log.d("Home", "data= "+ dataRoute);
        dataCond = getIntent().getStringExtra("data1");
        dataDesc = getIntent().getStringExtra("data2");

        ket = (TextView)findViewById(R.id.ketDesc);
        marlinLogo = (ImageView) findViewById(R.id.marlinlogo);
        btnChoose = (Button) findViewById(R.id.btnchoose);
        image_view = (ImageView) findViewById(R.id.image_view);
        btnSend = (Button) findViewById(R.id.btnSend);
        dropText = (EditText) findViewById(R.id.dropText);
        spinnerAPI = (Spinner) findViewById(R.id.spinnerAPI);
        spinnerCon = (Spinner) findViewById(R.id.spinnerCon);

       // getSupportFragmentManager().
         //       beginTransaction().replace(R.id.ketRoute, Dialog.newInstance(),"Dialog");


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startActivity(new Intent(Home.this, Register.class));
               // Toast.makeText(Home.this,"Success" , Toast.LENGTH_SHORT).show();

                if (view == btnSend) {
                    String route = spinnerAPI.getSelectedItem().toString();
                    String cond = spinnerCon.getSelectedItem().toString();
                    String desc = dropText.getText().toString();

                    Intent intent = new Intent(Home.this, Dialog.class);
                    intent.putExtra("data", route);
                    intent.putExtra("data1", cond);
                    intent.putExtra("data2", desc);
                    startActivity(intent);


                }
            }
        });



    }
}
