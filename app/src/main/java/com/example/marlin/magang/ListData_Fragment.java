package com.example.marlin.magang;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.marlin.magang.ViewModel.ListDataModel;
import com.example.marlin.magang.model.ListData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

public class ListData_Fragment extends Fragment {

    private OnListDataListener mListener;
    private ListAdapter mAdapter;
    private List<ListData> mListData;
    private RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    String token = "";
    String Id;
    SessionManager sessionManager;
    List<ListData> listData;

    public static ListData_Fragment newInstance() {
        ListData_Fragment fragment = new ListData_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        mListData = new ArrayList<>();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListDataModel listDataModel = ViewModelProviders.of(this).get(ListDataModel.class);
        listDataModel.getListitem().observe(this, new Observer<List<ListData>>() {
            @Override
            public void onChanged(@Nullable List<ListData> listData) {
                mAdapter.UpdateItem(listData);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManager = new SessionManager(getContext());
        sessionManager.checkLogin();
        sharedPreferences = getActivity().getSharedPreferences("LOGIN", MODE_PRIVATE);
        Id = sharedPreferences.getString("ID", "default_id");
        token = sharedPreferences.getString("TOKEN", "default");
        RecyclerView recyclerView = view.findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ListAdapter(getActivity(), null, mListener);
        recyclerView.setAdapter(mAdapter);

        loadData();
    }

    private void loadData() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "http://armpit.marlinbooking.co.id/api/report/" + "single?" + "id=" + Id;
        Log.d("URL", url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response ====", response);
                try {
                    JSONObject object =  new JSONObject(response);
                    String responses = object.getString("payload");
                    new ListTask().execute(responses);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "error cuy :" + error.getMessage());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json");
                headers.put("Authorization","Bearer "+token);
                return headers;
            }
        };
        stringRequest.setTag(TAG);
        queue.add(stringRequest);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_data, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ListData_Fragment.OnListDataListener){
            mListener = (ListData_Fragment.OnListDataListener) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListDataListener{
        void onListData(ListData listData);
    }


    public static class ListTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            try {
                FileApp.getInstance().getDatabase().listDataDAO().deleteAll();
                Gson gson = new Gson();
                Type listType = new TypeToken<List<ListData>>(){}.getType();
                List<ListData> listData = gson.fromJson(strings[0], listType);
                FileApp.getInstance().getDatabase().listDataDAO().insertAll(listData);

            }catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
