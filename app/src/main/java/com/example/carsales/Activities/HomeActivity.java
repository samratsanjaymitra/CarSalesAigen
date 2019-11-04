package com.example.carsales.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.carsales.Adapters.HomeAdapter;
import com.example.carsales.DbTables.DbCreation;
import com.example.carsales.POJO.ImagePOJO;
import com.example.carsales.R;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    LinearLayout ll_newAd;
    RecyclerView rv_List;
    private int REQUEST_CODE = 0x12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setPermissions();
        initViews();
        setData();
        createAd();
    }

    private void setPermissions() {
        String[] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE",
                "android.permission.READ_EXTERNAL_STORAGE", "android.permission.ACCESS_COARSE_LOCATION",
                "android.permission.CAMERA"};
        ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE); // without sdk version check

    }

    private void createAd() {
        ll_newAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, CreateAdActiity.class);
                startActivity(intent);

            }
        });
    }

    private void initViews() {
        ll_newAd = (LinearLayout) findViewById(R.id.ll_newAd);
        rv_List = (RecyclerView) findViewById(R.id.rv_List);
    }

    public void setData() {
        try {
            DbCreation obj = new DbCreation(HomeActivity.this);
            ArrayList<ImagePOJO> al_Image = new ArrayList<>();

            al_Image = obj.getCarDetails();

            HomeAdapter adapter = new HomeAdapter(HomeActivity.this, al_Image);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),
                    RecyclerView.VERTICAL, false);
            rv_List.setLayoutManager(mLayoutManager);
            rv_List.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            rv_List.setNestedScrollingEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
