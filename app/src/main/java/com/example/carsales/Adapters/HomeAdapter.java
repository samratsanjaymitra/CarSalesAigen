package com.example.carsales.Adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carsales.Activities.CarDetails;
import com.example.carsales.POJO.ImagePOJO;
import com.example.carsales.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


import static androidx.core.app.ActivityCompat.requestPermissions;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    Context context;
    ArrayList<ImagePOJO> al_Image;
    private static final int MY_READ_PERMISSION_CODE = 100;


    public HomeAdapter(Context context, ArrayList<ImagePOJO> al_Image) {
        this.context = context;
        this.al_Image = al_Image;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(context).inflate(R.layout.ad_items, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (al_Image.size() != 0) {
            final ImagePOJO pojo = al_Image.get(position);
            holder.tv_carName.setText(pojo.getCarName());

            holder.ll_carDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, CarDetails.class);
                    intent.putExtra("id", pojo.getId());
                    intent.putExtra("name", pojo.getCarName());
                    context.startActivity(intent);
                }
            });

            if (pojo.getId().equalsIgnoreCase("101")) {
                holder.iv_carImg.setImageResource(R.drawable.car1);
            } else if (pojo.getId().equalsIgnoreCase("102")) {
                holder.iv_carImg.setImageResource(R.drawable.car2);
            } else {
                try {
                    if (context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_READ_PERMISSION_CODE);
                    } else {
                        Picasso.with(context).load(pojo.getImageURL()).into(holder.iv_carImg);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }
    }

    @Override
    public int getItemCount() {
        return al_Image.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout ll_carDetails;
        ImageView iv_carImg;
        TextView tv_carName;


        public ViewHolder(View itemView) {
            super(itemView);
            ll_carDetails = (LinearLayout) itemView.findViewById(R.id.ll_carDetails);
            iv_carImg = (ImageView) itemView.findViewById(R.id.iv_carImg);
            tv_carName = (TextView) itemView.findViewById(R.id.tv_carName);
        }

    }
}

