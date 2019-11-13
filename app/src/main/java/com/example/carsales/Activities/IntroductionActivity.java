package com.example.carsales.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.carsales.Adapters.IntroductionAdapter;
import com.example.carsales.Fragments.AdFragment;
import com.example.carsales.Fragments.ProfileFragment;
import com.example.carsales.R;

public class IntroductionActivity extends AppCompatActivity {

    ViewPager vp_slide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        vp_slide = (ViewPager) findViewById(R.id.vp_slide);
        addtabs(vp_slide);
    }

    private void addtabs(ViewPager vp_slide) {
        IntroductionAdapter introductionAdapter = new IntroductionAdapter(getSupportFragmentManager());
        introductionAdapter.addFrag(new AdFragment());
        introductionAdapter.addFrag(new ProfileFragment());
        vp_slide.setAdapter(introductionAdapter);
    }
}
