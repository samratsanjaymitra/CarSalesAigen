package com.example.carsales.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.carsales.Activities.CreateAdActiity;

import com.example.carsales.Adapters.HomeAdapter;
import com.example.carsales.POJO.ImagePOJO;
import com.example.carsales.R;

import java.util.ArrayList;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MyAdFragment extends Fragment {

    RecyclerView rv_List;
    private static final int PERMISSION_REQUEST_CODE = 200;
    TextView tv_msg;
    ArrayList<ImagePOJO> al_data;
    String title;
    Button btn_create;

    FrameLayout fl_msg;

    public MyAdFragment(ArrayList<ImagePOJO> al_data) {
        this.al_data = al_data;
        title = "Aigen Tech";
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_ad, container, false);

        if (!checkPermission()) {
            requestPermission();
        }
        initViews(view);
        setData(al_data);
        createAd();
        return view;
    }

    private void initViews(View view) {
        tv_msg = (TextView) view.findViewById(R.id.tv_msg);
        rv_List = (RecyclerView) view.findViewById(R.id.rv_List);
        tv_msg.setText(tv_msg.getText() + " " + title);
        btn_create = (Button) view.findViewById(R.id.btn_create);
        fl_msg = (FrameLayout) view.findViewById(R.id.fl_msg);

    }

    private void createAd() {
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateAdActiity.class);
                startActivity(intent);

            }
        });
    }

    public void setData(ArrayList<ImagePOJO> al_data) {
        try {

            if (al_data.size() != 0) {
                fl_msg.setVisibility(View.GONE);
                HomeAdapter adapter = new HomeAdapter(getActivity(), al_data, "ad");

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),
                        RecyclerView.VERTICAL, false);
                rv_List.setLayoutManager(mLayoutManager);
                rv_List.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                rv_List.setNestedScrollingEnabled(false);
            }
            else{
                fl_msg.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getActivity(), CAMERA);
        int result2 = ContextCompat.checkSelfPermission(getActivity(), READ_EXTERNAL_STORAGE);


        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(getActivity(), new String[]{WRITE_EXTERNAL_STORAGE, CAMERA, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean writeAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean readAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) {
                            showMessageOKCancel("You need to allow access to both the permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA, READ_EXTERNAL_STORAGE},
                                                        PERMISSION_REQUEST_CODE);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }


                break;
        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}
