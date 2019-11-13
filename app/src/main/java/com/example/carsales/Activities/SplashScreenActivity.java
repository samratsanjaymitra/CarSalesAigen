package com.example.carsales.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.carsales.Adapters.IntroductionAdapter;
import com.example.carsales.DbTables.DbCreation;
import com.example.carsales.R;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SplashScreenActivity extends AppCompatActivity {

    Handler handler;

    SharedPreferences sharedPreferences;

    private static final int PERMISSION_REQUEST_CODE = 200;

    String state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        if (!checkPermission()) {
            requestPermission();
        }

        handler = new Handler();
        sharedPreferences = getSharedPreferences("loginState", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        state = "0";
        DbCreation obj = new DbCreation(SplashScreenActivity.this);
        obj.tableLoginStateCreation();
        String loginState = obj.getLoginState();
        if (loginState.equalsIgnoreCase("none")) {
            editor.putString("state", "0");
            editor.commit();
            state = sharedPreferences.getString("state", "");
            obj.addLoginState(state);
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                state = sharedPreferences.getString("state", "");
                if (state.equalsIgnoreCase("1")) {
                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(intent);

                }
                else if(state.equalsIgnoreCase("2")){
                    Intent intent = new Intent(SplashScreenActivity.this, HomeNewActivity.class);
                    startActivity(intent);

                }
                else {
                    Intent intent = new Intent(SplashScreenActivity.this, IntroductionActivity.class);
                    startActivity(intent);

                }
                finish();
            }
        }, 5000);

    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);


        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED && result2==PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, CAMERA, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

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
        new AlertDialog.Builder(SplashScreenActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

}








