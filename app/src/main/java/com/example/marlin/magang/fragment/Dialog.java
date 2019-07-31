package com.example.marlin.magang.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.marlin.magang.R;


public class Dialog extends Fragment {


    private String paramRoute, paramCond, paramDesc;
    private TextView tvIsiRoute, tvIsiCond, tvIsiDesc;
    Button btnSend;

    public Dialog() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Dialog newInstance(String route, String cond, String desc) {
        Dialog fragment = new Dialog();
        Bundle args = new Bundle();
        args.putString("data", route);
        args.putString("data1", cond);
        args.putString("data2", desc);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if (getArguments() != null) {
            paramRoute = getArguments().getString("data");
            paramCond = getArguments().getString("data1");
            paramDesc = getArguments().getString("data2");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvIsiRoute = view.findViewById(R.id.tvIsiRoute);
        tvIsiCond = view.findViewById(R.id.tvIsiCondition);
        tvIsiDesc = view.findViewById(R.id.tvIsiDescription);

        tvIsiRoute.setText(paramRoute);
        tvIsiCond.setText(paramCond);
        tvIsiDesc.setText(paramDesc);
    }
}

