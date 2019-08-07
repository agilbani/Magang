package com.example.marlin.magang;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;


import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class Home extends AppCompatActivity {

    TextView  ket;
    ImageView marlinLogo, image_view;
    Spinner spinnerAPI, spinnerCon;
    Button btnChoose, btnSend;
    EditText dropText;

    Dialog alertDialog;
    TextView tvRoute, tvIsiRoute, tvCondition, tvIsiCondition, tvDescription, tvIsiDescription;
    Button btnOk, btnCancel;

    SharedPreferences.Editor editor;

    Toolbar toolbar;

    SessionManager sessionManager;

    final  int kodeGalerry = 100 ;
    Uri imageUri;

     @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == kodeGalerry && resultCode == RESULT_OK) {
            imageUri = data.getData();
            image_view.setImageURI(imageUri);
            image_view.setVisibility(image_view.VISIBLE);

        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {

         sessionManager = new SessionManager(this);
         sessionManager.checkLogin();

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // sessionManager.logout();
//                Intent logoutintent = new Intent(this, Login.class);
//                startActivity(logoutintent);
//                SharedPreferences loginSharedPreferences;
//                //loginSharedPreferences = getSharedPreferences( Login.MyPREFERENCES, Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = loginSharedPreferences.edit();
//                editor.putString("UniqueId", "");
//                editor.commit(); finish();
            }
        });

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
//                Bundle bundle = new Bundle();
//                bundle.putString("dataRoute", spinnerAPI.getSelectedItem().toString());
//                bundle.putString("dataConditional", spinnerCon.getSelectedItem().toString());
//                bundle.putString("dataDescription", dropText.getText().toString());
//                Intent intent = new Intent(Home.this, Home.class);
//                intent.putExtras(bundle);
//                startActivity(intent);

//                String SpinnerApi = spinnerAPI.getSelectedItem().toString().trim();
//                String SpinnerCond = spinnerCon.getSelectedItem().toString().trim();
//                String DropText = dropText.getText().toString().trim();



                ShowSendPopup();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.menu, menu);

//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         int id = item.getItemId();

         if (id == R.id.app_bar) {

         }else if (id == R.id.action_settings){
             sessionManager.logout();
             Intent intent = new Intent(getApplicationContext(),Login.class);
             startActivity(intent);
             finish();
             return true;
         }


        return super.onOptionsItemSelected(item);
    }
//        private void logout() {
//                sessionManager.logout();
//                startActivity(new Intent(this, Login.class));
//         finish();
//        }
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



//        if(getIntent().getExtras()!=null){
//            Bundle bundle = getIntent().getExtras();
//            tvIsiRoute.setText(bundle.getString("dataRoute"));
//            tvIsiCondition.setText(bundle.getString("dataConditional"));
//            tvIsiDescription.setText(bundle.getString("dataDescription"));
//        }
//        else{
//            tvIsiRoute.setText(getIntent().getStringExtra("dataRoute"));
//            tvIsiCondition.setText(getIntent().getStringExtra("dataConditional"));
//            tvIsiDescription.setText(getIntent().getStringExtra("dataDescription"));
//          }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(Home.this, Home.class));
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               alertDialog.cancel();

            }
        });

        alertDialog.show();
    }

    }
