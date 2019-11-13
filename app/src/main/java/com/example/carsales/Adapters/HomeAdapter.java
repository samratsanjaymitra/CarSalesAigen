package com.example.carsales.Adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carsales.Activities.CarDetails;
import com.example.carsales.Activities.CreateAdActiity;
import com.example.carsales.POJO.ImagePOJO;
import com.example.carsales.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;


import static androidx.core.app.ActivityCompat.requestPermissions;
import static androidx.core.content.ContextCompat.isDeviceProtectedStorage;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    Context context;
    ArrayList<ImagePOJO> al_Image;
    private static final int MY_READ_PERMISSION_CODE = 100;
    String isFrom;


    public HomeAdapter(Context context, ArrayList<ImagePOJO> al_Image, String isFrom) {
        this.context = context;
        this.al_Image = al_Image;
        this.isFrom = isFrom;
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

            holder.tv_carName.setText("Name : " + pojo.getCarName());
            holder.tv_carPrice.setText("Price : " + pojo.getPrice());
            holder.tv_carYear.setText("Year : " + pojo.getYear());


            holder.ll_carDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, CarDetails.class);
                    intent.putExtra("id", pojo.getId());
                    intent.putExtra("name", pojo.getCarName());
                    intent.putExtra("type","");
                    context.startActivity(intent);
                }
            });

            holder.btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, CarDetails.class);
                    intent.putExtra("id", pojo.getId());
                    intent.putExtra("name", pojo.getCarName());
                    intent.putExtra("type","edit");

                    context.startActivity(intent);
                }
            });


                if (pojo.getId().equalsIgnoreCase("101")) {
                    holder.iv_carImg.setImageResource(R.drawable.car1);
                } else if (pojo.getId().equalsIgnoreCase("102")) {
                    holder.iv_carImg.setImageResource(R.drawable.car2);
                } else {
                    try {
                        //Picasso.with(context).load(pojo.getImageURL()).into(holder.iv_carImg);
                        /*ContextWrapper cw = new ContextWrapper(context);
                        File file = new File(pojo.getImageURL());
                        holder.iv_carImg.setImageDrawable(Drawable.createFromPath(file.toString()));
                        */
                        Uri uri = Uri.parse(pojo.getImageURL());
                        holder.iv_carImg.setImageURI(uri);


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
        TextView tv_carName,tv_carPrice,tv_carYear;
        Button btn_edit;


        public ViewHolder(View itemView) {
            super(itemView);
            ll_carDetails = (LinearLayout) itemView.findViewById(R.id.ll_carDetails);
            iv_carImg = (ImageView) itemView.findViewById(R.id.iv_carImg);
            tv_carName = (TextView) itemView.findViewById(R.id.tv_carName);
            tv_carPrice = (TextView) itemView.findViewById(R.id.tv_carPrice);
            tv_carYear = (TextView) itemView.findViewById(R.id.tv_carYear);
            btn_edit=(Button) itemView.findViewById(R.id.btn_edit);

            if(isFrom.equalsIgnoreCase("ad")){
                btn_edit.setVisibility(View.VISIBLE);
            }
            else{
                btn_edit.setVisibility(View.GONE);
            }


        }

    }
}

