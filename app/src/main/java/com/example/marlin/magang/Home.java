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
                params.put("image"," data:image/png;base64," + base64);
                Log.d("COBAAA6", params.toString());
//                params.put("image", " data:image/png;base64,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");
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
