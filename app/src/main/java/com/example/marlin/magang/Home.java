package com.example.marlin.magang;

import android.content.Intent;

import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
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

    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ket = (TextView)findViewById(R.id.ketDesc);
        marlinLogo = (ImageView) findViewById(R.id.marlinlogo);
        btnChoose = (Button) findViewById(R.id.btnchoose);

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

//    public void loadImagefromGallery (View view) {
//        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
//    }
//
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode,resultCode, data);
//        try{
//            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {
//                Uri selectedImage = data.getData();
//                String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn,null,null, null);
//                cursor.moveToFirst();
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                imgDecodableString = cursor.getString(columnIndex);
//                cursor.close();
//                ImageView image_view = (ImageView)findViewById(R.id.image_view);
//                image_view.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
//            }else {
//                Toast.makeText(this, "Anda belum mengambil gambar", Toast.LENGTH_SHORT);
//            }
//        }catch (Exception e) {
//            Toast.makeText(this, "Kesalahan Terjadi", Toast.LENGTH_LONG).show();;
//        }
//    }
    }
