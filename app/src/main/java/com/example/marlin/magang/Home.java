package com.example.marlin.magang;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;


import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import android.os.Handler;
import android.provider.MediaStore;
import android.service.notification.Condition;
import android.support.annotation.Nullable;
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
import java.util.Map;

import okhttp3.Route;

import static android.R.layout.simple_spinner_item;


public class Home extends AppCompatActivity {

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

    String Token, Trayek_Id, Company_Id, NamaRoute, NamaKondisi, Id, base64;

    SharedPreferences sharedPreferences;

    Toolbar toolbar;

    SessionManager sessionManager;

    final int kodeGalerry = 100;
    Uri imageUri;
    String TrayekURL = "http://armpit.marlinbooking.co.id/api/trayek";

    String ReportURL = "http://armpit.marlinbooking.co.id/api/report";


    public static final  String STATUS = "status";
    public static final String ID = "id";
    public static final String TOKEN = "token";
    public static final String COMMENT = "comment";
    public  static  final  String TRAYEK_ID = "trayek_id";
    public  static final String COMPANY_ID = "company_id";
    public static final String IMAGE_STRING = "image";


    private ArrayList<SpinnerModel> SpinnerModelArrayList;
    private ArrayList<String> trayekName = new ArrayList<String>();
    private ArrayList<String> trayekID = new ArrayList<String>();
    private ArrayList<String> trayekCompanyID = new ArrayList<String>();
    private ArrayList<String> trayekLokasi = new ArrayList<String>();

    public boolean doubleTapParam = false;

    public Home() throws FileNotFoundException {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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
            Toast.makeText(Home.this, "Image Saved", Toast.LENGTH_LONG).show();
            String base64 = convert(bitmap);

            byte[] decodeByteArray = Base64.decode(base64, Base64.NO_WRAP);
            Bitmap decodeBitmap = BitmapFactory.decodeByteArray(decodeByteArray, 0, decodeByteArray.length);
            Log.d("===IMAGE===", base64);

            image_view.setImageBitmap(decodeBitmap);

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
//
//                String nama = spinnerRoute.getItemAtPosition(spinnerRoute.getSelectedItemPosition()).toString();
//                Toast.makeText(getApplicationContext(),nama, Toast.LENGTH_SHORT).show();
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

//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background);
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] imageBytes = baos.toByteArray();
//        String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
//
//        imageBytes = Base64.decode(imageString, Base64.DEFAULT);
//        Bitmap decodeImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//        image_view.setImageBitmap(decodeImage);


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
                    Log.d("coba", ">>" + response);
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
//                                trayekLokasi.add(SpinnerModelArrayList.get(i).getLokasi().toString());
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
                    headers.put("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjE3MTVmNTczYjJjNjQzNTg4NDFhNDI3ZTcxMjIzNWYzZTYwYzdjYzg5ODAyNTU2NGIyM2Q4ZDczZTEzNDNhYjAzNzljYmY0MTk1ZjMwMDUxIn0.eyJhdWQiOiI1IiwianRpIjoiMTcxNWY1NzNiMmM2NDM1ODg0MWE0MjdlNzEyMjM1ZjNlNjBjN2NjODk4MDI1NTY0YjIzZDhkNzNlMTM0M2FiMDM3OWNiZjQxOTVmMzAwNTEiLCJpYXQiOjE1NjY3OTU5MjMsIm5iZiI6MTU2Njc5NTkyMywiZXhwIjoxNTk4NDE4MzIzLCJzdWIiOiIzMzNhNzViOC1jNGFhLTExZTktYWQzOC0wMjQyYTM5ZWEyMTQiLCJzY29wZXMiOltdfQ.Nk7KpS-cNL5O1zbO2BBsyHQFDLsy-1V7JR1GfAuGG_Ja6ms9iYzVeE_2WMF92F7eHZHr38ed-pOiD0efCtcAqBzSBcUQrc8IhKQTewuK8R32EQiCzs8otLBitUQHpFRzK0eUDhKMMy9WH6kFJjjlt3iW7Dp3F8h5SkPDgRatNVzx-Hhi7ITV-eyj7SG1u0FUc-xsHVjwT7dKt2zelsKdqzdvevigEh5pT2VmPC80TLvkQPdqA6P1EMvfLxrbNLkjMNDeJQZrn6rQA1YcQqGiYAKTrT2l7DL6b0lg9EAbTLhZDV_ur-UDt-ttZyJmaBESETT68e8yKM-V71TidZVkCc_3NIjqJKCNVmKbvyQJ-3c2ivI7iGCLygF13d5RauhuQQ-tDFZUNz1aiWvImuSwwKByv9Mlc8ntJLSLPyrMrjyDMXeKEw9vrktfkdj-rZjwO2_eEcDAnHlYUPXUou26gjES-HiH-slnMgVNlZmURcRJgMYsfI9arulSiYedHsFjKzbX3BEq-OzPbVqzn8X2M9BP4hpt4GEi1NnRVA0hpgl5ivnOx5bVsyRVYgwGZNYbLNf4QCjQk8-V0NrKnovrch7mhB47AZCTElwPzT2P6wReEMcZ3AqH_uIYkq1d1kU_xB_tu6oYYi9X8qwF3ZSyavrBGN3Bl2aFr0IkgnMiUpw");
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
                params.put("company_id", Company_Id);
                params.put("checker", Id);
                params.put("status", NamaKondisi);
                params.put("comments", etDescription.getText().toString());
                params.put("image", base64);

                Log.d("COBAAA", params.toString());
                return params;
            }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
             //   headers.put("Content-Type", "application/json");
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
        }
