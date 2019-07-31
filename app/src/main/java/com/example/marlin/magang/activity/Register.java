package com.example.marlin.magang.activity;

import android.content.Intent;
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
import com.example.marlin.magang.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText nama, email, password, password2, namaLast, phone;
    ImageView imgMarlin;
    Button btnRegister;

    static String URL_REGIST = "https://marlinbooking.co.id/api/v1/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        nama = (EditText) findViewById(R.id.etName);
        email = (EditText) findViewById(R.id.etEmail);
        password = (EditText) findViewById(R.id.etPassword);
        password2 = (EditText) findViewById(R.id.etPassword2);
        namaLast = (EditText) findViewById(R.id.etNameLast);
        phone = (EditText) findViewById(R.id.etPhone);
        imgMarlin = (ImageView) findViewById(R.id.imgMarlin);
        btnRegister = (Button) findViewById(R.id.btnRegister);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String FirstName = nama.getText().toString().trim();
                String LastName = namaLast.getText().toString().trim();
                String mPassword2 = password2.getText().toString().trim();
                String mPassword1 = password.getText().toString().trim();
                String mPhone = phone.getText().toString().trim();
                String mEmail = email.getText().toString().trim();

                Regist();
                Intent intent = new Intent(Register.this, Login.class);
                Register.this.startActivity(intent);
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


                            if(success.equals("1")){
                                Toast.makeText(Register.this, "Register success !!", Toast.LENGTH_SHORT).show();
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(Register.this, "Register Error !!"+ e.toString(), Toast.LENGTH_SHORT).show();
                            btnRegister.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("register error: ", error.toString());
                        btnRegister.setVisibility(View.VISIBLE);
                        Toast.makeText(Register.this, "Register Error !!" +error.toString(), Toast.LENGTH_SHORT).show();

                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("first_name", nama.getText().toString() );
                params.put("last_name", namaLast.getText().toString());
                params.put("email", email.getText().toString());
                params.put("phone_number", phone.getText().toString());
                params.put("password",password.getText().toString());
                params.put("password_confirmation", password2.getText().toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}
