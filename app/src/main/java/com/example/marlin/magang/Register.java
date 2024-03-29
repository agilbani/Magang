package com.example.marlin.magang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;

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

public class Register extends AppCompatActivity {

    EditText nama, email, password1, userName;
    ImageView imgMarlin;
    Button btnRegister;


    static String URL_REGIST = "http://armpit.marlinbooking.co.id/api/auth/register";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        nama = (EditText) findViewById(R.id.etName);
        userName = (EditText) findViewById(R.id.etUsername);
        email = (EditText) findViewById(R.id.etEmail);
        password1 = (EditText) findViewById(R.id.etPassword);
        imgMarlin = (ImageView) findViewById(R.id.imgMarlin);
        btnRegister = (Button) findViewById(R.id.btnRegister);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nama.getText().toString().trim();
                String username = userName.getText().toString().trim();
                String password = password1.getText().toString().trim();
                String mEmail = email.getText().toString().trim();

                Regist();
                finish();

            }
        });

    }

    private void Regist(){
        btnRegister.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("respon: ", response);
                        try{
                            JSONObject jsonObject = new JSONObject(response);

                            String success = jsonObject.getString("message");


                            if(success.equals("true")){
                                Toast.makeText(Register.this, "Register success !!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Register.this, Login.class);
                                startActivity(intent);
                                finish();
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(Register.this, "Data already exist", Toast.LENGTH_SHORT).show();

                            btnRegister.setVisibility(View.VISIBLE);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.d("register error: ", error.toString());
                        btnRegister.setVisibility(View.VISIBLE);
                        Toast.makeText(Register.this, "Register Error !!" , Toast.LENGTH_SHORT).show();

                    }
                })


        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email.getText().toString());
                params.put("password",password1.getText().toString());
                params.put("username", userName.getText().toString());
                params.put("name", nama.getText().toString());

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }



}
