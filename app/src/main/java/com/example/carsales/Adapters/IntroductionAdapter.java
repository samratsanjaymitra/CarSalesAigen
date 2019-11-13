package com.example.carsales.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class IntroductionAdapter extends FragmentPagerAdapter {

    private final List<Fragment> frags = new ArrayList<>();

    public IntroductionAdapter(FragmentManager manager) {

        super(manager);

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return frags.get(position);
    }

    @Override
    public int getCount() {
        return frags.size();
    }

    public void addFrag(Fragment fragment){
        frags.add(fragment);
    }

}
