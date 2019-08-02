package com.example.marlin.magang;

import android.content.Intent;


import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
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


    final  int kodeGalerry = 100 ;
    Uri imageUri;


     @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == kodeGalerry && resultCode == RESULT_OK) {
            imageUri = data.getData();
            image_view.setImageURI(imageUri);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ket = (TextView)findViewById(R.id.ketDesc);
        marlinLogo = (ImageView) findViewById(R.id.marlinlogo);
        dropText = (EditText) findViewById(R.id.dropText);
        spinnerAPI = (Spinner) findViewById(R.id.spinnerAPI);
        spinnerCon = (Spinner) findViewById(R.id.spinnerCon);
        btnChoose = (Button) findViewById(R.id.btnchoose);
        image_view = (ImageView)findViewById(R.id.image_view);
        btnSend = (Button) findViewById(R.id.btnSend);

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentGallery, kodeGalerry);
            }
        });





        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Login.class));
                Toast.makeText(Home.this,"Success" , Toast.LENGTH_SHORT).show();
            }
        });

    }

//
    }
