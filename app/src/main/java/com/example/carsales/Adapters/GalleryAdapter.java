package com.example.carsales.Adapters;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.carsales.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static androidx.core.app.ActivityCompat.requestPermissions;

public class GalleryAdapter extends BaseAdapter {

    private Context ctx;
    private int pos;
    private LayoutInflater inflater;
    private ImageView ivGallery;
    ArrayList<Uri> mArrayUri;
    Boolean isFromHome;
    String carId;
    private static final int MY_READ_PERMISSION_CODE = 100;

    public GalleryAdapter(Context ctx, ArrayList<Uri> mArrayUri, Boolean isFromHome, String carId) {

        this.ctx = ctx;
        this.mArrayUri = mArrayUri;
        this.isFromHome = isFromHome;
        this.carId = carId;
    }

    @Override
    public int getCount() {
        return mArrayUri.size();
    }

    @Override
    public Object getItem(int position) {
        return mArrayUri.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        pos = position;
        inflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.gv_item, parent, false);

        ivGallery = (ImageView) itemView.findViewById(R.id.ivGallery);

        if (isFromHome) {
            if (carId.equalsIgnoreCase("101")) {
                ivGallery.setImageResource(R.drawable.car1);
            } else if (carId.equalsIgnoreCase("102")) {
                ivGallery.setImageResource(R.drawable.car2);
            } else {
                try {
                    ivGallery.setImageURI(mArrayUri.get(position));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            ivGallery.setImageURI(mArrayUri.get(position));
        }
        return itemView;
    }


}
