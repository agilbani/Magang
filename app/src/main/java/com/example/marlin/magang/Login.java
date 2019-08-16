package com.example.marlin.magang;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    ImageView imageLogo;
    TextView editEmail, editPass, linkRegister;
    Button btnLogin;
    ProgressDialog progressDialog;
    //ProgressBar loading;
    private static String URL_Login = "https://marlinbooking.co.id/api/v1/login";

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        sessionManager = new SessionManager(this);


        imageLogo = (ImageView) findViewById(R.id.imageLogo);
        editEmail = (TextView) findViewById(R.id.editEmail);
        editPass = (TextView) findViewById(R.id.editPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        linkRegister = (TextView) findViewById(R.id.linkRegister);
        progressDialog = new ProgressDialog(Login.this);
      //  loading = (ProgressBar) findViewById(R.id.loading);

        progressDialog.setMessage("please wait..");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = editEmail.getText().toString().trim();
                String mPass = editPass.getText().toString().trim();


                if (!mEmail.isEmpty() || !mPass.isEmpty()) {
                     Login(mEmail, mPass);
        //             loading.setVisibility(View.VISIBLE);


                } else {
                    editEmail.setError("Please Insert Email");
                    editPass.setError("Please Insert Password");
                }
            }

        });
        linkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));

            }
        });
    }



    public void Login(final String mEmail, final String mPass) {
        btnLogin.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_Login,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String mNama = jsonObject.getJSONObject("payload").getString("name");
                            String mEmail = jsonObject.getJSONObject("payload").getString("email");

                            sessionManager.createSession(mEmail, mNama);

                            Log.d("cek", jsonObject.getString("payload"));
                            progressDialog.show();
                            Intent intent = new Intent(Login.this, Home.class);
                            Login.this.startActivity(intent);
                            finish();

                        } catch (JSONException e) {
                            btnLogin.setVisibility(View.VISIBLE);
                            e.printStackTrace();
                            Toast.makeText(Login.this,"Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this,"email atau password salah", Toast.LENGTH_SHORT).show();
                btnLogin.setVisibility(View.VISIBLE);
          //      loading.setVisibility(View.GONE);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
               Map<String, String> params = new HashMap<>();
               params.put("email", mEmail);
               params.put("password", mPass);
               return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue( this);
        requestQueue.add(stringRequest);

    }}
