package com.example.marlin.magang;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URL;

public class DetailActivity extends AppCompatActivity {

    TextView tvTrayek;
    TextView tvStatus;
    ImageView gambar;

    String trayek_id, status, image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        gambar = (ImageView) findViewById(R.id.gambar1);
        tvTrayek = (TextView)findViewById(R.id.detail_trayek);
        tvStatus = (TextView)findViewById(R.id.detail_status);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();


        assert bundle != null;
        image = bundle.getString("image");
        Log.d("cuma1", image);
        trayek_id = bundle.getString("trayekId");
        Log.d("cuma2" , trayek_id);
        status = bundle.getString("status");
        Log.d("cuma3", status);


            tvTrayek.setText(trayek_id);
            tvStatus.setText(status);
            Picasso.get().load(image).into(gambar);
//            gambar.setImageURI(image);



    }
}
