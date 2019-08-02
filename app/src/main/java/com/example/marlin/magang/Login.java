package com.example.marlin.magang;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
    Toolbar toolbar;
    TextView editEmail, editPass, linkRegister;
    Button btnLogin;
    private static String URL_Login = "https://marl inbooking.co.id/api/v1/login";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imageLogo = (ImageView) findViewById(R.id.imageLogo);
        editEmail = (TextView) findViewById(R.id.editEmail);
        editPass = (TextView) findViewById(R.id.editPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        linkRegister = (TextView) findViewById(R.id.linkRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = editEmail.getText().toString().trim();
                String mPass = editPass.getText().toString().trim();



                if (!mEmail.isEmpty() || !mPass.isEmpty()) {
                     Login(mEmail, mPass);
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
                            Intent intent = new Intent(Login.this, Home.class);
                            Login.this.startActivity(intent);
                            finish();

                        } catch (JSONException e) {
                            btnLogin.setVisibility(View.VISIBLE);
                            e.printStackTrace();
                            Toast.makeText(Login.this,"Error" +e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                btnLogin.setVisibility(View.VISIBLE);
                Toast.makeText(Login.this,"Error" +error.toString(), Toast.LENGTH_SHORT).show();

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
