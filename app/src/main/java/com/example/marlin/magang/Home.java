package com.example.marlin.magang;

import android.app.Dialog;
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

    TextView  ket;
    ImageView marlinLogo, image_view;
    Spinner spinnerAPI, spinnerCon;
    Button btnChoose, btnSend;
    EditText dropText;

    Dialog alertDialog;
    TextView tvRoute, tvIsiRoute, tvCondition, tvIsiCondition, tvDescription, tvIsiDescription;
    Button btnOk, btnCancel;


    final  int kodeGalerry = 100 ;
    Uri imageUri;

//    private static int RESULT_LOAD_IMG = 1;
//    String imgDecodableString;

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

        alertDialog = new Dialog(this);

        ket = (TextView)findViewById(R.id.ketDesc);
        marlinLogo = (ImageView) findViewById(R.id.imgMarlin);
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

//                Toast.makeText(Home.this,"Success" , Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("dataRoute", spinnerAPI.getSelectedItem().toString());
                bundle.putString("dataConditional", spinnerCon.getSelectedItem().toString());
                bundle.putString("dataDescription", dropText.getText().toString());
//                Intent intent = new Intent(Home.this, Home.class );
//                intent.putExtras(bundle);
//                startActivity(intent);
                ShowSendPopup();
            }
        });

    }

    public void ShowSendPopup(){


        alertDialog.setContentView(R.layout.activity_alert_dialog);
        tvRoute = (TextView) alertDialog.findViewById(R.id.tvRoute);
        tvIsiRoute = (TextView) alertDialog.findViewById(R.id.tvIsiRoute);
        tvCondition = (TextView) alertDialog.findViewById(R.id.tvCondition);
        tvIsiCondition = (TextView) alertDialog.findViewById(R.id.tvIsiCondition);
        tvDescription = (TextView) alertDialog.findViewById(R.id.tvDescription);
        tvIsiDescription = (TextView) alertDialog.findViewById(R.id.tvIsiDescription);
        btnOk = (Button) alertDialog.findViewById(R.id.btnOk);
        btnCancel = (Button) alertDialog.findViewById(R.id.btnCancel);

       if(getIntent().getExtras()!=null){
            Bundle bundle = getIntent().getExtras();
            tvIsiRoute.setText(bundle.getString("dataRoute"));
            tvIsiCondition.setText(bundle.getString("dataConditional"));
            tvIsiDescription.setText(bundle.getString("dataDescription"));
        }
       else{
            tvIsiRoute.setText(getIntent().getStringExtra("dataRoute"));
            tvIsiCondition.setText(getIntent().getStringExtra("dataConditional"));
            tvIsiDescription.setText(getIntent().getStringExtra("dataDescription"));
          }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(Home.this, Home.class));
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Home.this, "Button Cancel", Toast.LENGTH_SHORT).show();

            }
        });

        alertDialog.show();
    }

    }
