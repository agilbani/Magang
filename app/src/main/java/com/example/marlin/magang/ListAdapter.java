package com.example.marlin.magang;


import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marlin.magang.model.ListData;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;

import java.io.File;
import java.util.List;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private Context context;
    private List<ListData> listData;
    private ListData_Fragment.OnListDataListener mListener;

    String image;

    public ListAdapter(Context context, List<ListData> listData, ListData_Fragment.OnListDataListener mListener){
        this.context = context;
        this.listData = listData;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listdata_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Picasso.get().load(listData.get(i).getImage()).into(viewHolder.mImage);

       // viewHolder.mImage.setImageResource(R.drawable.background);
        viewHolder.mTrayekid.setText(listData.get(i).getTrayek_id());
        viewHolder.mStatus.setText(listData.get(i).getStatus());

        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onListData(listData.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listData == null){
         return 0;
        }else {
            return listData.size();
        }

    }

    public void UpdateItem(List<ListData>listData){
        this.listData = listData;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView mImage;
        private TextView mTrayekid;
        private TextView mStatus;
        private View mView;

        public ViewHolder (View itemView){
            super(itemView);
            mView = itemView;
            mImage = itemView.findViewById(R.id.img);
            mTrayekid = itemView.findViewById(R.id.list_trayek);
            mStatus = itemView.findViewById(R.id.list_status);

        }
    }

//    private Context context;
//    private List<ListData> mlistData;
//    private ListData_Fragment.OnListDataListener mListener;
//
//    public ListAdapter(Context mcontext, List<ListData> mlistData, ListData_Fragment.OnListDataListener mListener) {
//        this.context = mcontext;
//        this.mlistData = mlistData;
//        this.mListener = mListener;
//    }

//    @NonNull
//    @Override
//    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listdata_item, parent, false);
//        return new ViewHolder(v);
//    }
//
////    @Override
////    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
////
////    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, final int position) {
//
//        holder.Img.setImageResource(R.drawable.background);
//        holder.trayekId.setText(mlistData.get(position).getTrayek_id());
//        holder.status.setText(mlistData.get(position).getStatus());
//
//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mListener.onListData(mlistData.get(position));
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        if (mlistData == null){
//            return 0;
//        } else {
//            return mlistData.size();
//        }
//    }
//
//    public void updateItem(List<ListData> ListData){
//        this.mlistData = ListData;
//        notifyDataSetChanged();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//        public ImageView Img;
//        public TextView trayekId;
//        public TextView status;
//        public View mView;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            mView = itemView;
//            Img = itemView.findViewById(R.id.img);
//            trayekId = itemView.findViewById(R.id.list_trayek);
//            status = itemView.findViewById(R.id.list_status);
//
//        }
//    }
//

}

