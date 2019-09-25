package com.example.marlin.magang;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.marlin.magang.model.Trayek;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TrayekAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    private List<SpinnerModel> spinnerModels = null;
    private ArrayList<SpinnerModel> arraylist;

    public TrayekAdapter(Context context, List<SpinnerModel> spinnerModels) {
        mContext = context;
        this.spinnerModels = spinnerModels;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<SpinnerModel>();
        this.arraylist.addAll(spinnerModels);
    }

    public class ViewHolder{
        TextView name;
    }

    @Override
    public int getCount() {
        return spinnerModels.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listview_item, null);
            holder.name = (TextView)convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.name.setText(spinnerModels.get(position).getNama());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String Nama = spinnerModels.get(position).getNama();
                String Trayek_id = spinnerModels.get(position).getId();
                String Company_id = spinnerModels.get(position).getCompany_id();

                Intent intent = new Intent(mContext, Home.class);
                Bundle bundle = new Bundle();
                bundle.putString("Nama", Nama);
                bundle.putString("Trayek_id", Trayek_id);
                Log.d("Trayek", "Ada data" + Trayek_id);
                bundle.putString("Company_id", Company_id);
                intent.putExtras(bundle);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);


               // mContext.startActivity(new Intent (mContext, Home.class));

            }
        });
        return convertView;
    }

    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        spinnerModels.clear();
        if(charText.length() == 0){
            spinnerModels.addAll(arraylist);
        }else {
            for(SpinnerModel wp: arraylist){
                if(wp.getNama().toLowerCase(Locale.getDefault()).contains(charText)){
                    spinnerModels.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
