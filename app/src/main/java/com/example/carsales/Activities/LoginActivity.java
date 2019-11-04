package com.example.carsales.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.carsales.DbTables.DbCreation;
import com.example.carsales.POJO.LoginPOJO;
import com.example.carsales.R;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    EditText et_Name, et_Password;
    ProgressBar pb_progress;
    Button btn_Login;
    ArrayList<LoginPOJO> loginDetails;
    String userName = "";
    String password = "";
    private int REQUEST_CODE = 0x12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setPermissions();
        if (ActivityCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

        }
        initViews();
        userLogin();

    }

    private void setPermissions() {
        String[] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE",
                "android.permission.READ_EXTERNAL_STORAGE", "android.permission.ACCESS_COARSE_LOCATION",
                "android.permission.CAMERA"};
        ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE); // without sdk version check

    }


    private void userLogin() {
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Boolean loginFlag = false;
                DbCreation obj = new DbCreation(LoginActivity.this);
                obj.tableCarDetailsCreation();
                loginDetails = obj.getLoginDetails();
                getLoginValues();
                for (int i = 0; i < loginDetails.size(); i++) {
                    if (userName.equalsIgnoreCase(loginDetails.get(i).getEmail()) && password.equalsIgnoreCase(loginDetails.get(i).getPassword())) {
                        loginFlag = true;
                    }

                }
                if (loginFlag == true) {
                    obj.addCarDetails(101, "Car1", "R.drawable.car1");
                    obj.addCarDetails(102, "Car2", "R.drawable.car2");
                    obj.addCarDetails(102, "Car2", "R.drawable.car1");
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);


                } else {
                    Toast.makeText(LoginActivity.this, "Credentials are incorrect", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void getLoginValues() {
        userName = et_Name.getText().toString();
        password = et_Password.getText().toString();
    }

    private void initViews() {
        et_Name = (EditText) findViewById(R.id.et_UserName);
        et_Password = (EditText) findViewById(R.id.et_UserPassword);
        pb_progress = (ProgressBar) findViewById(R.id.pb_progress);
        btn_Login = (Button) findViewById(R.id.btn_Login);

    }
}
