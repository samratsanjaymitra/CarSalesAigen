package com.example.carsales.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.carsales.Adapters.HomeAdapter;
import com.example.carsales.Adapters.IntroductionAdapter;
import com.example.carsales.DbTables.DbCreation;
import com.example.carsales.Fragments.AdFragment;
import com.example.carsales.Fragments.HomeFragment;
import com.example.carsales.Fragments.MyAdFragment;
import com.example.carsales.Fragments.ProfileFragment;
import com.example.carsales.POJO.ImagePOJO;
import com.example.carsales.R;

import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.List;

public class HomeNewActivity extends AppCompatActivity {

    ViewPager vp_mainFrame;

    TextView tv_home, tv_myAd;

    ArrayList<ImagePOJO> al_dataHome;
    ArrayList<ImagePOJO> al_dataAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_new);
        initViews();
        al_dataHome=getData();
        addtabs();
        initFragment();
    }

    private void initViews() {
        vp_mainFrame = (ViewPager) findViewById(R.id.vp_mainFrame);
        tv_home = (TextView) findViewById(R.id.tv_home);
        tv_myAd = (TextView) findViewById(R.id.tv_myAd);

    }

    private void addtabs() {
        IntroductionAdapter introductionAdapter = new IntroductionAdapter(getSupportFragmentManager());

        introductionAdapter.addFrag(new HomeFragment(al_dataHome));
        al_dataAd=new ArrayList<>();
        for (int i = 2; i <al_dataHome.size() ; i++) {
            ImagePOJO imagePOJO = al_dataHome.get(i);
            al_dataAd.add(imagePOJO);
        }
        introductionAdapter.addFrag(new MyAdFragment(al_dataAd));
        vp_mainFrame.setAdapter(introductionAdapter);


    }


    private void initFragment() {
        tv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vp_mainFrame.setCurrentItem(0);
            }
        });
        tv_myAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vp_mainFrame.setCurrentItem(1);
            }
        });


        vp_mainFrame.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    tv_home.setBackgroundColor(getColor(R.color.background));
                    tv_myAd.setBackgroundColor(getColor(R.color.turquoise));


                }

                if (position == 1) {
                    tv_myAd.setBackgroundColor(getColor(R.color.background));
                    tv_home.setBackgroundColor(getColor(R.color.turquoise));

                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        vp_mainFrame.setCurrentItem(0);
    }

    public ArrayList<ImagePOJO> getData() {
        ArrayList<ImagePOJO> al_Image = new ArrayList<>();
        try {
            DbCreation obj = new DbCreation(HomeNewActivity.this);

            al_Image = obj.getCarDetails();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return al_Image;
    }


}

