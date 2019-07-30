package com.example.marlin.magang;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends AppCompatActivity {

    TextView tb_text , ket;
    ImageView marlinLogo, image_view;
    Spinner spinnerAPI, spinnerCon;
    Button btnChoose, btnSend;
    EditText dropText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ket = (TextView)findViewById(R.id.ketDesc);
        marlinLogo = (ImageView) findViewById(R.id.marlinlogo);
        btnChoose = (Button) findViewById(R.id.btnchoose);
        image_view = (ImageView) findViewById(R.id.image_view);
        btnSend = (Button) findViewById(R.id.btnSend);
        dropText = (EditText) findViewById(R.id.dropText);
        spinnerAPI = (Spinner) findViewById(R.id.spinnerAPI);
        spinnerCon = (Spinner) findViewById(R.id.spinnerCon);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Register.class));
                Toast.makeText(Home.this,"Success" , Toast.LENGTH_SHORT).show();
            }
        });



    }
}
