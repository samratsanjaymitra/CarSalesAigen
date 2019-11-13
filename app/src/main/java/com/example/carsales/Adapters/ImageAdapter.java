package com.example.carsales.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.carsales.R;

import java.util.ArrayList;

public class ImageAdapter extends PagerAdapter {

    private Context ctx;
    ArrayList<Uri> mArrayUri;
    String id;

    public ImageAdapter(Context ctx, ArrayList<Uri> mArrayUri, String id) {
        this.ctx = ctx;
        this.mArrayUri = mArrayUri;
        this.id = id;
    }

    @Override
    public int getCount() {

        if (id.equalsIgnoreCase("101")) {
            return 1;
        } else if (id.equalsIgnoreCase("102")) {
            return 2;
        } else {
            return mArrayUri.size();
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView iv = new ImageView(ctx);

        if (id.equalsIgnoreCase("101")) {
            iv.setImageResource(R.drawable.car1);
        } else if (id.equalsIgnoreCase("102")) {
            if (position == 0)
                iv.setImageResource(R.drawable.car2);
            if (position == 1)
                iv.setImageResource(R.drawable.car3);

        } else {
            try {
                iv.setImageURI(mArrayUri.get(position));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        ((ViewPager) container).addView(iv, 0);
        return iv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }

}
