package com.example.marlin.magang;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;
import android.widget.SearchView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataTrayek extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ListView list;
    TrayekAdapter adapter;
    SearchView editsearch;
    ArrayList<SpinnerModel> spinnerModels = new ArrayList<SpinnerModel>();

    private final String TAG = getClass().getSimpleName();

    String Token;

    String TrayekURL = "http://armpit.marlinbooking.co.id/api/trayek";

    SharedPreferences sharedPreferences;
    SessionManager sessionManager;

    public static DataTrayek newInstance() {
        DataTrayek datatrayek = new DataTrayek();
        Bundle args = new Bundle();
        return datatrayek;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_trayek);

        sessionManager = new SessionManager(this);

        sharedPreferences = getSharedPreferences("LOGIN",MODE_PRIVATE);
        Token = sharedPreferences.getString("TOKEN", "default_token");

        list = (ListView) findViewById(R.id.listview);
        editsearch = (SearchView) findViewById(R.id.searchView);
        editsearch.setOnQueryTextListener(this);
        loadData();

    }

    private void loadData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, TrayekURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "response : "+response);
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.optString("success").equals("true")){
                        JSONArray jsonArray = jsonObject.getJSONArray("payload");

                        for (int i = 0; i < jsonArray.length(); i++){
                            SpinnerModel trayek = new SpinnerModel();
                            JSONObject obj = jsonArray.getJSONObject(i);

                            trayek.setId(obj.getString("id"));
                            trayek.setCompany_id(obj.getString("company_id"));
                            trayek.setNama(obj.getString("nama"));

                            spinnerModels.add(trayek);

                        }
                        adapter = new TrayekAdapter(getApplicationContext(), spinnerModels);
                        list.setAdapter(adapter);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if(response != null && response.data != null){
                    String errorString = new String(response.data);
                    Log.i("log error", errorString);

                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + Token );
                return headers;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
    }
}
