package com.example.marlin.magang;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;


import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;

import android.os.AsyncTask;
import android.os.Handler;
import android.provider.MediaStore;
import android.service.notification.Condition;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.EventLogTags;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.marlin.magang.model.ListData;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Route;

import static android.R.layout.simple_spinner_item;


public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ListData_Fragment.OnListDataListener {

    TextView ket;
    ImageView marlinLogo, image_view;
    Spinner spinnerRoute, spinnerCond;
    Button btnChooseImage, btnSend;
    EditText etDescription;

    AlertDialog.Builder alertDialog;
    LayoutInflater inflater;
    View dialogView;
    TextView tvRoute, tvIsiRoute, tvCondition, tvIsiCondition, tvDescription, tvIsiDescription;
    Button btnOk, btnCancel;
    ProgressDialog progressDialog;

    SharedPreferences.Editor editor;

    String Token, Trayek_Id, Company_Id, NamaRoute, NamaKondisi, Id;

    String base64 = null;

    SharedPreferences sharedPreferences;

    Toolbar toolbar;

    SessionManager sessionManager;

    final int kodeGalerry = 100;
    Uri imageUri;
    String TrayekURL = "http://armpit.marlinbooking.co.id/api/trayek";

    String ReportURL = "http://armpit.marlinbooking.co.id/api/report";

    private ArrayList<SpinnerModel> SpinnerModelArrayList;
    private ArrayList<String> trayekName = new ArrayList<String>();
    private ArrayList<String> trayekID = new ArrayList<String>();
    private ArrayList<String> trayekCompanyID = new ArrayList<String>();

    public boolean doubleTapParam = false;

    public Home() throws FileNotFoundException {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == kodeGalerry && resultCode == RESULT_OK) {
//            imageUri = data.getData();
//            image_view.setImageURI(imageUri);
//            image_view.setVisibility(image_view.VISIBLE);
//
//        }

        Uri imageUri = data.getData();
        try{
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            image_view.setImageURI(imageUri);
            image_view.setVisibility(image_view.VISIBLE);
            Toast.makeText(Home.this, "Image Selected ", Toast.LENGTH_LONG).show();
            base64 = convert(bitmap);

            byte[] decodeByteArray = Base64.decode(base64, Base64.NO_WRAP);
            Bitmap decodeBitmap = BitmapFactory.decodeByteArray(decodeByteArray, 0, decodeByteArray.length);
            Log.d("===IMAGE===", base64);

            image_view.setImageBitmap(decodeBitmap);

            sendData();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(Home.this, "Failed to get image from gallery", Toast.LENGTH_LONG).show();
        }
    }

    private String convert(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        progressDialog = new ProgressDialog(Home.this);
        ket = (TextView) findViewById(R.id.ketDesc);
        marlinLogo = (ImageView) findViewById(R.id.imgMarlin);
        etDescription = (EditText) findViewById(R.id.etDescription);

        spinnerRoute = (Spinner) findViewById(R.id.spinnerRoute);

        loadSpinnerData();

        sharedPreferences = getSharedPreferences("LOGIN",MODE_PRIVATE);
        Id = sharedPreferences.getString("ID", "default_id");
        Token = sharedPreferences.getString("TOKEN", "default_token");
        Log.d("Token", Token);

        spinnerRoute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                NamaRoute = SpinnerModelArrayList.get(position).getNama();
                Trayek_Id = SpinnerModelArrayList.get(position).getId();
                Company_Id = SpinnerModelArrayList.get(position).getCompany_id();
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerCond = (Spinner) findViewById(R.id.spinnerCond);
        btnChooseImage = (Button) findViewById(R.id.btnChooseImg);
        image_view = (ImageView) findViewById(R.id.image_view);
        btnSend = (Button) findViewById(R.id.btnSend);


        spinnerCond.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (
                        parent.getItemAtPosition(position).equals("--Select Condition--")) {
                }else {
                    String item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(),"Selected: " +item, Toast.LENGTH_SHORT).show();
                }

                 NamaKondisi = spinnerCond.getItemAtPosition(spinnerCond.getSelectedItemPosition()).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentGallery, kodeGalerry);
            }
        });


        btnSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Validasi();

            }
        });



    }

    private void insertData() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                List<ListData> listData= new ArrayList<>();

                FileApp.getInstance().getDatabase().listDataDAO().insertAll(listData);
                return null;
            }
        }.execute();
    }

    private void Validasi() {
        if(NamaKondisi.equals("--Select Condition--")){
            Toast.makeText(this, "Silahkan Pilih Kondisi Terlebih dahulu", Toast.LENGTH_SHORT).show();

        }else{
            String route = spinnerRoute.getSelectedItem().toString().trim();
            Log.d("cek1", route);
            String condition = spinnerCond.getSelectedItem().toString().trim();
            Log.d("cek2", condition);
            String description = etDescription.getText().toString().trim();
            Log.d("cek", description);

            ShowSendPopup(route, condition, description);

        }
    }

    private void loadSpinnerData() {
        Log.d("======================", ">>" + tvRoute);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, TrayekURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("RESPONNNNNNNNNNN", ">>" + response);
                    try {
                        JSONObject obj = new JSONObject(response);
                        if (obj.optString("success").equals("true")) {
                            SpinnerModelArrayList = new ArrayList<>();
                            JSONArray dataArray = obj.getJSONArray("payload");
                            for (int i = 0; i < dataArray.length(); i++) {
                                SpinnerModel spinnerModel = new SpinnerModel();
                                JSONObject dataobj = dataArray.getJSONObject(i);

                                spinnerModel.setId(dataobj.getString("id"));
                                spinnerModel.setCompany_id(dataobj.getString("company_id"));
                                spinnerModel.setNama(dataobj.getString("nama"));
                                spinnerModel.setLokasi(dataobj.getString("lokasi"));

                                SpinnerModelArrayList.add(spinnerModel);
                            }

                            for (int i = 0; i < SpinnerModelArrayList.size(); i++) {
                                trayekName.add(SpinnerModelArrayList.get(i).getNama().toString());
                                trayekID.add(SpinnerModelArrayList.get(i).getId().toString());
                                trayekCompanyID.add(SpinnerModelArrayList.get(i).getCompany_id().toString());
                            }

                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(Home.this, simple_spinner_item, trayekName);
                            spinnerArrayAdapter.setDropDownViewResource(simple_spinner_item);
                            spinnerRoute.setAdapter(spinnerArrayAdapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }

    })
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap headers = new HashMap();
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", "Bearer " + Token);
                    return headers;
                }
            };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.app_bar) {

        } else if (id == R.id.action_settings) {
            sessionManager.logout();
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
            return true;
        }else if (id == R.id.actionList) {
            Intent intent = new Intent(getApplicationContext(), DataActivity.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    public void ShowSendPopup(String route, String condition, String description) {
        alertDialog = new AlertDialog.Builder(this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.activity_alert_dialog, null);
        alertDialog.setView(dialogView);
        alertDialog.setCancelable(true);

        final TextView tvIsiRoute = (TextView) dialogView.findViewById(R.id.tvIsiRoute);
        final TextView tvIsiCondition = (TextView) dialogView.findViewById(R.id.tvIsiCondition);
        final TextView tvIsiDescription = (TextView) dialogView.findViewById(R.id.tvIsiDescription);

        tvIsiRoute.setText(NamaRoute);
        tvIsiCondition.setText(condition);
        tvIsiDescription.setText(description);

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                sendData();


                progressDialog.setMessage("Please wait...");
                progressDialog.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent dialogg = new Intent(Home.this, Home.class);
                        startActivity(dialogg);
                    }
                }, 500);


                etDescription.setText("");

                dialog.dismiss();

//                spinnerRoute.setAdapter(null);
//                spinnerCond.setAdapter(null);



            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();

    }



    private void sendData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ReportURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("oke", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String success = jsonObject.getString("message");

                            if (jsonObject.optString("success").equals("true")) {
                                Toast.makeText(Home.this, "Send Data Success", Toast.LENGTH_SHORT).show();
                              // finish();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Home.this, "Send Data Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Kenapaaaa: ", error.toString());
                        error.printStackTrace();
                        Toast.makeText(Home.this, "send data error", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("trayek_id", Trayek_Id);
                Log.d("COBAAA1", params.toString());
                params.put("company_id", Company_Id);
                Log.d("COBAAA2", params.toString());
                params.put("checker", Id);
                Log.d("COBAAA3", params.toString());
                params.put("status", NamaKondisi);
                Log.d("COBAAA4", params.toString());
                params.put("comments", etDescription.getText().toString());
                Log.d("COBAAA5", params.toString());
                params.put("image", base64);
                Log.d("COBAAA6", params.toString());
                return params;
            }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
//                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                headers.put("Authorization","Bearer " + Token);
                return headers;
        }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        }
@Override
public void onBackPressed() {
        if (doubleTapParam) {
        super.onBackPressed();
        return;
        }

        this.doubleTapParam = true;
        Toast.makeText(this, "Ketuk sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();

        }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public void onListData(ListData listData) {

    }
}
